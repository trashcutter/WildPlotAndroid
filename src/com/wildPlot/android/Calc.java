package com.wildPlot.android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Calc extends Fragment {
    
    public static final String FRAGMENT_NAME = "calc_fragment";
    
	CalcInputContainer inputContainter = new CalcInputContainer();
	
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

//        TextView textview = new TextView(this);
//        textview.setText("This is the Calc tab");
        View rootView = inflater.inflate(R.layout.calc, container, false);
        final EditText textBox = (EditText) rootView.findViewById(R.id.editText1);
        
        inputContainter.setGlobalData((GlobalDataUnified) getActivity().getApplication());
        
        textBox.setText("Test:\n" + inputContainter.toString());
        return rootView;
    }
	
	public void buttonClick(View view) {
	    View rootView = getView();
		Button button = (Button) view;
		
		CharSequence text = button.getText();
		final EditText textBox = (EditText) rootView.findViewById(R.id.editText1);

		if(text.toString().equals("DEL")) {
			inputContainter.deleteLast();
			
		} else if(text.toString().equals("AC")) {
			inputContainter.deleteAll();
		} else if(text.toString().equals("=")) {
			inputContainter.calc();
		} else if(text.toString().equals("Plot")) {
            FunctionNameDialog dialog = new FunctionNameDialog(inputContainter);
            dialog.show(getActivity().getSupportFragmentManager(), "FunctionNameDialog");
            String funcName = dialog.getFunctionName();

//            System.err.println(funcName + "was choosen as a name");
//            if(funcName != null && funcName.length() > 0)
//                inputContainter.plot(funcName);
//            else
//                inputContainter.plot();
        }else if(text.toString().equals("Splot")) {
            inputContainter.splot();
        } else {
			inputContainter.add(new CalcInput(text.toString()));
		}
		
		
		textBox.setText("Test:\n" + inputContainter.toString());
	 }

}
