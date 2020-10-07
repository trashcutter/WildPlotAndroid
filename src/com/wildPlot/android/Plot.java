package com.wildPlot.android;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Plot extends Fragment {
    public static final String FRAGMENT_NAME = "plot_fragment";
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        
        View rootView = inflater.inflate(R.layout.plot, container, false);

//        TextView textview = new TextView(this);
//        textview.setText("This is the Plot tab");
//        setContentView(textview);
        
        GlobalDataUnified gs = (GlobalDataUnified) getActivity().getApplication();
        PlotView plotView = (PlotView) rootView.findViewById(R.id.plot_view);
        plotView.setGlobalData(gs);
        plotView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);  //no hardware acceleration
        //setContentView(new PlotView(this, gs));
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); 
        return rootView;
	}
	
	public void onResume(){
		super.onResume();
		
//		TabActivity tabAct = (TabActivity)this.getParent();
//		tabAct.getTabWidget().setOrientation(LinearLayout.VERTICAL);
//		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); 
	}
	
	public void onPause(){
		super.onPause();
//		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); 
//		TabActivity tabAct = (TabActivity)this.getParent();
//		tabAct.getTabWidget().setOrientation(LinearLayout.HORIZONTAL);
	}
}
