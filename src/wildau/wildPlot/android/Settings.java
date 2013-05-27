package wildau.wildPlot.android;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
		
		RadioGroup radio = (RadioGroup) rootView.findViewById(R.id.radioGroup2);
		radio.check(R.id.radio0);
        setListeners(rootView);
        updateButtonStates(rootView);
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

        leftX.addTextChangedListener(this);
        rightX.addTextChangedListener(this);
        leftY.addTextChangedListener(this);
        rightY.addTextChangedListener(this);
        frameCheckBox.setOnClickListener(this);
        gridCheckBox.setOnClickListener(this);
        logXCheckBox.setOnClickListener(this);
        logYCheckBox.setOnClickListener(this);
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
		RadioGroup radio = (RadioGroup) rootView.findViewById(R.id.radioGroup2);

		final EditText leftX = (EditText) rootView.findViewById(R.id.leftXRange);
		final EditText rightX = (EditText) rootView.findViewById(R.id.rightXRange);
		final EditText leftY = (EditText) rootView.findViewById(R.id.leftYRange);
		final EditText rightY = (EditText) rootView.findViewById(R.id.RightYRange);

		final CheckBox frameCheckBox = (CheckBox) rootView.findViewById(R.id.framedCheckBox);
		final CheckBox gridCheckBox = (CheckBox) rootView.findViewById(R.id.gridCheckBox);

		final CheckBox logXCheckBox = (CheckBox) rootView.findViewById(R.id.logXCheckBox);
		final CheckBox logYCheckBox = (CheckBox) rootView.findViewById(R.id.logYCheckBox);

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
			if(radioButton.getText().toString().equals("points")) {
				if(gs.getTouchPointType() != 0)
					gs.setTouchPointType(0);
			} else if(radioButton.getText().toString().equals("linespoints")) {
				if(gs.getTouchPointType() != 1)
					gs.setTouchPointType(1);
			} else if(radioButton.getText().toString().equals("splines")) {
				if(gs.getTouchPointType() != 2)
					gs.setTouchPointType(2);
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
