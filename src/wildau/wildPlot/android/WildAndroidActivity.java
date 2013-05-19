package wildau.wildPlot.android;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;

public class WildAndroidActivity extends FragmentActivity {
    
    MainPagerAdapter mSectionsPagerAdapter;
    
    ViewPager mViewPager;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	
        setContentView(R.layout.fragment_main);
        
        
        mSectionsPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), this);
        mSectionsPagerAdapter.addPageTitle(Calc.FRAGMENT_NAME);
        mSectionsPagerAdapter.addPageTitle(Plot.FRAGMENT_NAME);
        mSectionsPagerAdapter.addPageTitle(Settings.FRAGMENT_NAME);
        mSectionsPagerAdapter.addPageTitle(Ode.FRAGMENT_NAME);
        mSectionsPagerAdapter.addPageTitle(Integrator.FRAGMENT_NAME);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setOnPageChangeListener(new OnPageChangeListener(){

            public void onPageSelected(int position) {
                if(position==mSectionsPagerAdapter.getFragmentPostion(Plot.FRAGMENT_NAME)){
                    int settingsFragmentPosition = mSectionsPagerAdapter.getFragmentPostion(Settings.FRAGMENT_NAME);
                    Settings settings  = (Settings)mSectionsPagerAdapter.getItem(settingsFragmentPosition);
                    if(settings != null && settings.viewIsCreated())
                        settings.updateSettings();
                }
                if(position==mSectionsPagerAdapter.getFragmentPostion(Settings.FRAGMENT_NAME)){
                    int settingsFragmentPosition = mSectionsPagerAdapter.getFragmentPostion(Settings.FRAGMENT_NAME);
                    Settings settings  = (Settings)mSectionsPagerAdapter.getItem(settingsFragmentPosition);
                    if(settings != null)
                        settings.updateButtonStates();
                }
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
                
            }

        });
        mViewPager.setAdapter(mSectionsPagerAdapter);


    }
    public void reset(View view){
        int settingsFragmentPosition = mSectionsPagerAdapter.getFragmentPostion(Settings.FRAGMENT_NAME);
        Settings settings  = (Settings)mSectionsPagerAdapter.getItem(settingsFragmentPosition);
        settings.reset(view);
    }
    public void buttonClick(View view) {
        int settingsFragmentPosition = mSectionsPagerAdapter.getFragmentPostion(Calc.FRAGMENT_NAME);
        Calc calc  = (Calc)mSectionsPagerAdapter.getItem(settingsFragmentPosition);
        calc.buttonClick(view);
    }
    public void odeClick(View view) {
        int odeFragmentPosition = mSectionsPagerAdapter.getFragmentPostion(Ode.FRAGMENT_NAME);
        Ode ode  = (Ode)mSectionsPagerAdapter.getItem(odeFragmentPosition);
        ode.buttonClick(view);
    }
    public void integratorClick(View view) {
        int integratorFragmentPosition = mSectionsPagerAdapter.getFragmentPostion(Integrator.FRAGMENT_NAME);
        Integrator integrator  = (Integrator)mSectionsPagerAdapter.getItem(integratorFragmentPosition);
        integrator.buttonClick(view);
    }
    
}