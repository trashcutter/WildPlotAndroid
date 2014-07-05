package com.wildPlot.android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;


public class Settings extends Fragment implements View.OnClickListener, TextWatcher {

    public static final String FRAGMENT_NAME = "settings_fragment";
    GlobalDataUnified gs;
	private boolean viewIsCreated = false;
    private boolean onUpdate = true;

	@Override
	public void onResume(){
		super.onResume();
		updateButtonStates();
	}
	
	public void updateButtonStates(){
	    View rootView = getView();
        updateButtonStates(rootView);
	}

    private void updateButtonStates(View rootView){
        onUpdate = true;
        final EditText leftX = (EditText) rootView.findViewById(R.id.leftXRange);
        final EditText rightX = (EditText) rootView.findViewById(R.id.rightXRange);
        final EditText leftY = (EditText) rootView.findViewById(R.id.leftYRange);
        final EditText rightY = (EditText) rootView.findViewById(R.id.RightYRange);

        CheckBox checkBox = (CheckBox) rootView.findViewById(R.id.framedCheckBox);
        checkBox.setChecked(gs.isHasFrame());

        checkBox = (CheckBox) rootView.findViewById(R.id.gridCheckBox);
        checkBox.setChecked(gs.isHasGrid());

        checkBox = (CheckBox) rootView.findViewById(R.id.logXCheckBox);
        checkBox.setChecked(gs.isLogX());

        checkBox = (CheckBox) rootView.findViewById(R.id.logYCheckBox);
        checkBox.setChecked(gs.isLogY());

        //System.err.println(gs.isLogY() + "!!!!");

        leftX.setText(gs.getXstart()+"");
        rightX.setText(gs.getXend()+"");
        leftY.setText(gs.getYstart()+"");
        rightY.setText(gs.getYend()+"");
        onUpdate = false;
        //checkBox.setChecked(gs.isHasFrame());
    }

	public void reset(View view){
        onUpdate = true;
        System.err.println("reset request!!!!!!!!!!!!!!!!!!!!!");
        gs.reset();
		View rootView = getView();
		final EditText leftX 	= (EditText) rootView.findViewById(R.id.leftXRange);
		final EditText rightX 	= (EditText) rootView.findViewById(R.id.rightXRange);
		final EditText leftY 	= (EditText) rootView.findViewById(R.id.leftYRange);
		final EditText rightY 	= (EditText) rootView.findViewById(R.id.RightYRange);

        final CheckBox histoActivateCheckBox = (CheckBox)rootView.findViewById(R.id.check_box_histo_activator);
        histoActivateCheckBox.setChecked(gs.isKdeActivated());

        final RadioButton pointsRadioButton = (RadioButton)rootView.findViewById(R.id.radio_settings_points);
        final RadioButton linespointsRadioButton = (RadioButton)rootView.findViewById(R.id.radio_settings_linespoints);
        final RadioButton splinesRadioButton = (RadioButton)rootView.findViewById(R.id.radio_settings_splines);

        pointsRadioButton.setChecked(true);
        linespointsRadioButton.setChecked(false);
        splinesRadioButton.setChecked(false);

		leftX.setText(gs.getXstart()+"");
		rightX.setText(gs.getXend()+"");
		leftY.setText(gs.getYstart()+"");
		rightY.setText(gs.getYend()+"");
		
		CheckBox checkBox = (CheckBox) rootView.findViewById(R.id.framedCheckBox);
		checkBox.setChecked(gs.isHasFrame());
		
		checkBox = (CheckBox) rootView.findViewById(R.id.gridCheckBox);
		checkBox.setChecked(gs.isHasGrid());
		
		checkBox = (CheckBox) rootView.findViewById(R.id.logXCheckBox);
		checkBox.setChecked(gs.isLogX());
		
		checkBox = (CheckBox) rootView.findViewById(R.id.logYCheckBox);
		checkBox.setChecked(gs.isLogY());
        onUpdate = false;
	}
	public void onPause(){
		super.onPause();
		//updateSettings();
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		View rootView = inflater.inflate(R.layout.settings, container, false);
		

		//        TextView textview = new TextView(this);
		//        textview.setText("This is the Calc tab");
		//setContentView(R.layout.settings);
		//final EditText textBox = (EditText) findViewById(R.id.editText1);

		//inputContainter.setGlobalData((GlobalData) getApplication());

		//textBox.setText("Test:\n" + inputContainter.toString());
		gs = (GlobalDataUnified) getActivity().getApplication();
		
		RadioGroup radio = (RadioGroup) rootView.findViewById(R.id.radio_group_point_settings);
		radio.check(R.id.radio_settings_points);
        setListeners(rootView);
        updateButtonStates(rootView);
        final Button setupKdeButton = (Button) rootView.findViewById(R.id.button_setup_kde);
        setupKdeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AshKdeSettingsDialog dialog = new AshKdeSettingsDialog(gs);
                dialog.show(getActivity().getSupportFragmentManager(), "AshKdeSettingsDialog");
            }
        });

        final Button setupRegressionButton = (Button) rootView.findViewById(R.id.button_setup_regression);
        setupRegressionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegressionSettingsDialog dialog = new RegressionSettingsDialog(gs);
                dialog.show(getActivity().getSupportFragmentManager(), "RegressionSettingsDialog");
            }
        });

        viewIsCreated = true;
		return rootView;
	}

    private void setListeners(View rootView){

        final EditText leftX = (EditText) rootView.findViewById(R.id.leftXRange);
        final EditText rightX = (EditText) rootView.findViewById(R.id.rightXRange);
        final EditText leftY = (EditText) rootView.findViewById(R.id.leftYRange);
        final EditText rightY = (EditText) rootView.findViewById(R.id.RightYRange);

        final CheckBox frameCheckBox = (CheckBox) rootView.findViewById(R.id.framedCheckBox);
        final CheckBox gridCheckBox = (CheckBox) rootView.findViewById(R.id.gridCheckBox);

        final CheckBox logXCheckBox = (CheckBox) rootView.findViewById(R.id.logXCheckBox);
        final CheckBox logYCheckBox = (CheckBox) rootView.findViewById(R.id.logYCheckBox);
        final RadioButton pointsRadioButton = (RadioButton)rootView.findViewById(R.id.radio_settings_points);
        final RadioButton linespointsRadioButton = (RadioButton)rootView.findViewById(R.id.radio_settings_linespoints);
        final RadioButton splinesRadioButton = (RadioButton)rootView.findViewById(R.id.radio_settings_splines);
        final CheckBox histoActivateCheckBox = (CheckBox)rootView.findViewById(R.id.check_box_histo_activator);

        leftX.addTextChangedListener(this);
        rightX.addTextChangedListener(this);
        leftY.addTextChangedListener(this);
        rightY.addTextChangedListener(this);
        frameCheckBox.setOnClickListener(this);
        gridCheckBox.setOnClickListener(this);
        logXCheckBox.setOnClickListener(this);
        logYCheckBox.setOnClickListener(this);
        pointsRadioButton.setOnClickListener(this);
        linespointsRadioButton.setOnClickListener(this);
        splinesRadioButton.setOnClickListener(this);
        histoActivateCheckBox.setOnClickListener(this);
    }

//	@Override
//	public void setUserVisibleHint(boolean isVisibleToUser) {
//	super.setUserVisibleHint(isVisibleToUser);
//
//	if (!isVisibleToUser) {
//	    updateSettings();
//	}
//
//	}
	public void updateSettings(){
	    if(this.isHidden() || !this.isResumed() || !this.viewIsCreated || onUpdate){
	        //System.err.println("hidden");
	        return;
	        
	    }
	    System.err.println("unhidden");
	    View rootView = getView();
		RadioGroup radio = (RadioGroup) rootView.findViewById(R.id.radio_group_point_settings);

		final EditText leftX = (EditText) rootView.findViewById(R.id.leftXRange);
		final EditText rightX = (EditText) rootView.findViewById(R.id.rightXRange);
		final EditText leftY = (EditText) rootView.findViewById(R.id.leftYRange);
		final EditText rightY = (EditText) rootView.findViewById(R.id.RightYRange);

		final CheckBox frameCheckBox = (CheckBox) rootView.findViewById(R.id.framedCheckBox);
		final CheckBox gridCheckBox = (CheckBox) rootView.findViewById(R.id.gridCheckBox);

		final CheckBox logXCheckBox = (CheckBox) rootView.findViewById(R.id.logXCheckBox);
		final CheckBox logYCheckBox = (CheckBox) rootView.findViewById(R.id.logYCheckBox);
        final CheckBox histoActivateCheckBox = (CheckBox)rootView.findViewById(R.id.check_box_histo_activator);

        if(histoActivateCheckBox.isChecked()){
            gs.activateKde();
        } else {
            gs.deactivateKde();
        }

		gs.setXrange(Double.parseDouble(leftX.getText().toString() ), Double.parseDouble(rightX.getText().toString()));

		gs.setYrange(Double.parseDouble(leftY.getText().toString() ), Double.parseDouble(rightY.getText().toString()));

		if(frameCheckBox.isChecked()) {
			gs.setFrame();
			gs.setAxisOnFrame();
		} else {
			gs.unsetFrame();
			gs.unsetAxisOnFrame();
		}

		if(gridCheckBox.isChecked()){
			gs.setGrid();
		} else {
			gs.unsetGrid();
		}
		int selected = radio.getCheckedRadioButtonId();


		RadioButton radioButton = (RadioButton)radio.findViewById(selected);
		if(selected != -1) {
			//				System.err.println("!!!!!!Text of radioButton: " + radioButton.getText().toString());
			if(radioButton.getText().toString().equals(getString(R.string.points_radio_option))) {
				if(gs.getTouchPointType() != GlobalDataUnified.TouchPointType.points)
					gs.setTouchPointType(GlobalDataUnified.TouchPointType.points);
			} else if(radioButton.getText().toString().equals(getString(R.string.linespoints_radio_option))) {
				if(gs.getTouchPointType() != GlobalDataUnified.TouchPointType.linespoints)
					gs.setTouchPointType(GlobalDataUnified.TouchPointType.linespoints);
			} else if(radioButton.getText().toString().equals(getString(R.string.splines_radio_option))) {
				if(gs.getTouchPointType() != GlobalDataUnified.TouchPointType.spline)
					gs.setTouchPointType(GlobalDataUnified.TouchPointType.spline);
			}
		}

		if(logXCheckBox.isChecked()){
			gs.setLogX();
		} else {
			gs.unsetLogX();
		}

		if(logYCheckBox.isChecked()) {
			gs.setLogY();
		}else {
			gs.unsetLogY();
		}
		
	}

    public boolean viewIsCreated() {
        return viewIsCreated;
    }

    @Override
    public void onClick(View v) {
        updateSettings();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        updateSettings();
    }
}
