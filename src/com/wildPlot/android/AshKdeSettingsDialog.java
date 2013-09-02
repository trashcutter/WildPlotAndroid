package com.wildPlot.android;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.wildPlot.MyMath.Polynomial;
import com.wildPlot.system.ODE;

/**
 * Created by Michael Goldbach
 */
public class AshKdeSettingsDialog extends DialogFragment implements View.OnClickListener{
    private GlobalDataUnified mGlobalDataUnified;

    AshKdeSettingsDialog(GlobalDataUnified globalDataUnified){
        mGlobalDataUnified = globalDataUnified;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.kde_ash, null);

        final Button resetButton = (Button) rootView.findViewById(R.id.button_update_points);
        resetButton.setOnClickListener(this);

        final EditText widthX = (EditText) rootView.findViewById(R.id.ashWidthX);
        final EditText widthY = (EditText) rootView.findViewById(R.id.ashWidthY);
        final EditText originX = (EditText) rootView.findViewById(R.id.ashOriginX);
        final EditText originY = (EditText) rootView.findViewById(R.id.ashOriginY);
        final EditText mX = (EditText) rootView.findViewById(R.id.editTextMx);
        final EditText mY = (EditText) rootView.findViewById(R.id.editTextMy);

        widthX.setText(mGlobalDataUnified.getWidthX()+ "");
        widthY.setText(mGlobalDataUnified.getWidthY()+ "");
        originX.setText(mGlobalDataUnified.getOriginX() + "");
        originY.setText(mGlobalDataUnified.getOriginY() + "");
        mX.setText(mGlobalDataUnified.gethX()+"");
        mY.setText(mGlobalDataUnified.gethY()+"");

        final Spinner kernelSpinner = (Spinner) rootView.findViewById(R.id.spinner_kernel);
        kernelSpinner.setAdapter(new ArrayAdapter<GlobalDataUnified.Kernel>(getActivity(), android.R.layout.simple_spinner_item, GlobalDataUnified.Kernel.values()));


        kernelSpinner.setSelection(mGlobalDataUnified.getKernel().ordinal());




        builder.setView(rootView);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                mGlobalDataUnified.setWidthX(Double.parseDouble(widthX.getText().toString().trim()));
                mGlobalDataUnified.setWidthY(Double.parseDouble(widthY.getText().toString().trim()));
                mGlobalDataUnified.setOriginX(Double.parseDouble(originX.getText().toString().trim()));
                mGlobalDataUnified.setOriginY(Double.parseDouble(originY.getText().toString().trim()));
                mGlobalDataUnified.sethX(Integer.parseInt(mX.getText().toString().trim()));
                mGlobalDataUnified.sethY(Integer.parseInt(mX.getText().toString().trim()));
                mGlobalDataUnified.setKernel((GlobalDataUnified.Kernel)kernelSpinner.getSelectedItem());


                //FunctionNameDialog.this.getDialog().dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                AshKdeSettingsDialog.this.getDialog().cancel();
            }
        });



        return builder.create();
    }


    private void update(View v){
        View rootView = v.getRootView();
        final EditText widthX = (EditText) rootView.findViewById(R.id.ashWidthX);
        final EditText widthY = (EditText) rootView.findViewById(R.id.ashWidthY);
        try{
            double[][] points = mGlobalDataUnified.getPointsOfAssignment();
            if(points[0].length == 0)
                return;


            double sigmaX = calcSigma(points[0]);
            System.err.println("sigma x-values: " + sigmaX);
            double heightX = Math.pow(  (4*sigmaX*sigmaX*sigmaX*sigmaX*sigmaX)/(3.0*points[0].length)    , 1.0/5.0);
            double sigmaY = calcSigma(points[1]);
            System.err.println("sigma y-values: " + sigmaY);
            double heightY = Math.pow(  (4*sigmaY*sigmaY*sigmaY*sigmaY*sigmaY)/(3.0*points[1].length)    , 1.0/5.0);
            widthX.setText(""+heightX);
            widthY.setText(""+heightY);

        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
    }
    private double calcSigma(double[] points){
        double mean = 0;
        for (double value : points){
            mean +=value;
        }
        mean /= points.length;

        double sigma = 0;
        for (double value : points){
            sigma += (value-mean)*(value-mean);
        }

        sigma /= points.length - 1;

        sigma = Math.sqrt(sigma);
        return sigma;

    }


    public static double calcError(Polynomial omega, double[][] points){
        double error = 0;
        for(int n = 0; n<points[0].length; n++){
            error+= (omega.get(points[0][n]) - points[1][n])*(omega.get(points[0][n]) - points[1][n]);
        }
        return error/2.0;
    }
    @Override
    public void onClick(View v) {
        update(v);
    }
}
