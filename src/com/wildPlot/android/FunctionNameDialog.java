package com.wildPlot.android;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by mig on 29.05.13.
 */
public class FunctionNameDialog extends DialogFragment{
    private String functionName = "";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.function_name_dialog, null);
        final EditText textBox = (EditText) rootView.findViewById(R.id.functionname);

        builder.setView(rootView);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                functionName = textBox.getText().toString().trim();
                //FunctionNameDialog.this.getDialog().dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                FunctionNameDialog.this.getDialog().cancel();
            }
        });



        return builder.create();
    }

    public String getFunctionName() {
        return functionName;
    }
}
