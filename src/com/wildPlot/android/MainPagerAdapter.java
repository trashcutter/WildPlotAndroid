/**
 * 
 */
package com.wildplot.android;

import java.util.HashMap;
import java.util.Locale;
import java.util.Vector;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author mig
 *
 */
public class MainPagerAdapter extends FragmentPagerAdapter {

    HashMap<String, Fragment> fragmentMap = new HashMap<String, Fragment>();
    HashMap<String, Integer> fragmentPositionMap = new HashMap<String, Integer>();
    Vector<String> pageTitleVector = new Vector<String>();
    WildAndroidActivity wildAndroidActivity;
    
    public MainPagerAdapter(FragmentManager fm, WildAndroidActivity wildAndroidActivity) {
        super(fm);
        this.wildAndroidActivity = wildAndroidActivity;
    }

    @Override
    public Fragment getItem(int arg0) {
        String fragmentName = pageTitleVector.get(arg0);
        Fragment fragment = fragmentMap.get(fragmentName);
        if(fragment != null)
            return fragment;
        
        return createFragment(fragmentName);
    }

    private Fragment createFragment(String fragmentName){
        //TODO create and put in hashMap
        Fragment fragment = null;
        
        if(fragmentName.equals(Settings.FRAGMENT_NAME)){
            fragment = new Settings();
        }else if(fragmentName.equals(Plot.FRAGMENT_NAME)){
            fragment = new Plot();
        }else if(fragmentName.equals(Calc.FRAGMENT_NAME)){
            fragment = new Calc();
        }else if(fragmentName.equals(Ode.FRAGMENT_NAME)){
            fragment = new Ode();
        }else if(fragmentName.equals(Integrator.FRAGMENT_NAME)){
            fragment = new Integrator();
        } else {
            fragment = new DummySectionFragment();
        }
        fragmentMap.put(fragmentName, fragment);
        
        return fragment;
    }
    
    @Override
    public int getItemPosition(Object object) {
        //TODO custom item positioning must be returned here
        return super.getItemPosition(object);
    }
    
    @Override
    public int getCount() {
        return pageTitleVector.size();
    }
    
    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        if(position<pageTitleVector.size()){
            String pageTitle = pageTitleVector.elementAt(position);
            
            if(pageTitle.equals(Calc.FRAGMENT_NAME))
                pageTitle = wildAndroidActivity.getString(R.string.calc_fragment);
            
            if(pageTitle.equals(Settings.FRAGMENT_NAME))
                pageTitle = wildAndroidActivity.getString(R.string.settings_fragment);
            
            if(pageTitle.equals(Plot.FRAGMENT_NAME))
                pageTitle = wildAndroidActivity.getString(R.string.plot_fragment);
            
            if(pageTitle.equals(Ode.FRAGMENT_NAME))
                pageTitle = wildAndroidActivity.getString(R.string.ode_fragment);
            
            if(pageTitle.equals(Integrator.FRAGMENT_NAME))
                pageTitle = wildAndroidActivity.getString(R.string.integrator_fragment);
            
            return pageTitle.toUpperCase(l);
        }
        return null;
    }

    public Vector<String> getPageTitleVector() {
        return pageTitleVector;
    }
   
    public void addPageTitle(String title){
        fragmentPositionMap.put(title, pageTitleVector.size());
        pageTitleVector.add(title);
        
    }
    
    public int getFragmentPostion(String fragmentTitle){
        return fragmentPositionMap.get(fragmentTitle);
    }
    
    /**
     * A dummy fragment representing a section of the app, but that simply displays dummy text.
     */
    public static class DummySectionFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this fragment.
         */
        public static final String ARG_SECTION_NUMBER = "section_number";


        public DummySectionFragment() {
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_dummy, container, false);
            TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
            dummyTextView.setText("Error: Selected Page is not available, please contact developer");
            return rootView;
        }
    }
 }
