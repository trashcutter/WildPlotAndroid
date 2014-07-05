package com.wildPlot.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;


/**
 * Created by Michael Goldbach
 */
public class RegressionSettingsDialog extends DialogFragment implements View.OnClickListener{
    private GlobalDataUnified mGlobalDataUnified;

    RegressionSettingsDialog(GlobalDataUnified globalDataUnified){
        mGlobalDataUnified = globalDataUnified;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.regression, null);

        final CheckBox regressionCheckBox = (CheckBox) rootView.findViewById(R.id.regressionCheckBox);

        final TableRow tableRowRegressionM = (TableRow)rootView.findViewById(R.id.tableRowRegressionM);
        final TableRow tableRowRegressionLambda = (TableRow)rootView.findViewById(R.id.tableRowRegressionLambda);


        final EditText editTextK = (EditText) rootView.findViewById(R.id.editTextK);
        final EditText editTextRegressionM = (EditText) rootView.findViewById(R.id.editTextRegressionM);
        final EditText editTextRegressionLambda = (EditText) rootView.findViewById(R.id.editTextRegressionLambda);

        final ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.progressBarRegression);

        progressBar.setVisibility(View.GONE);
        tableRowRegressionM.setVisibility(View.GONE);
        tableRowRegressionLambda.setVisibility(View.GONE);

        editTextK.setText("5"+ "");
        editTextRegressionM.setText("");
        editTextRegressionLambda.setText("");






        builder.setView(rootView);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
//                mGlobalDataUnified.setWidthX(Double.parseDouble(widthX.getText().toString().trim()));
//                mGlobalDataUnified.setWidthY(Double.parseDouble(widthY.getText().toString().trim()));
//                mGlobalDataUnified.setOriginX(Double.parseDouble(originX.getText().toString().trim()));
//                mGlobalDataUnified.setOriginY(Double.parseDouble(originY.getText().toString().trim()));
//                mGlobalDataUnified.sethX(Integer.parseInt(mX.getText().toString().trim()));
//                mGlobalDataUnified.sethY(Integer.parseInt(mX.getText().toString().trim()));
//                mGlobalDataUnified.setKernel((GlobalDataUnified.Kernel)kernelSpinner.getSelectedItem());


                //FunctionNameDialog.this.getDialog().dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                RegressionSettingsDialog.this.getDialog().cancel();
            }
        });



        return builder.create();
    }



    @Override
    public void onClick(View v) {

    }
//    final class MyWorker extends AsyncTask<Void, Integer, Void> {
//
//
//        private int index;
//
//        private final Activity parent;
//        private final ProgressBar progress;
//        private final TextView textview;
//
//        public MyWorker(final Activity parent, final ProgressBar progress, final TextView textview) {
//            this.parent = parent;
//            this.progress = progress;
//            this.textview = textview;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            int max = 0;
//            for (final int p : progr) {
//                max += p;
//            }
//            progress.setMax(max);
//            index = 0;
//        }
//
//        @SuppressWarnings("unchecked")
//        @Override
//        protected Void doInBackground(final Void... params) {
//
//
//            return null;
//        }
//
//        @Override
//        protected void onProgressUpdate(final Integer... values) {
//            textview.setText(titles[index]);
//            progress.incrementProgressBy(progr[index]);
//            ++index;
//        }
//
//        @Override
//        protected void onPostExecute(final Void result) {
//            parent.finish();
//        }
//    }
}
