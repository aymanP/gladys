package com.example.user.myapplication;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TextView;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;

import com.example.user.adapters.MyFragmentPagerAdapter;
import com.example.user.fragment.Fragment1;
import com.example.user.fragment.Fragment2;
import com.example.user.fragment.Fragment3;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.os.Build.VERSION_CODES.LOLLIPOP;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, TabHost.OnTabChangeListener{

	
    ViewPager viewPager;
    TabHost tabHost;
	
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
           TabHost viewTest = (TabHost) findViewById(android.R.id.tabhost);
//            item.getItemId().getBackground.setAlpha(80);
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewTest.setVisibility(View.VISIBLE);

                    return true;
                case R.id.navigation_dashboard:
                  //  mTextMessage.setText(R.string.title_dashboard);
                    viewTest.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_notifications:
                    viewTest.setVisibility(View.GONE);
                //    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View backgroundImage = findViewById(R.id.container);
//
//        Toolbar myToolbar = (Toolbar) findViewById(R.id.myToolbar);
//        setSupportActionBar(myToolbar);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        if (Build.VERSION.SDK_INT >= LOLLIPOP) {
//            getSupportActionBar().setIcon(getDrawable(R.drawable.ic_action_name));
//        }

        Drawable background = backgroundImage.getBackground();
        background.setAlpha(80);
//        getActionBar().setIcon(R.mipmap.logo_headmenu);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
		
		initViewPager();

        initTabHost();

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        List<Fragment> listFragments = new ArrayList<Fragment>();
        listFragments.add(new Fragment1());
        listFragments.add(new Fragment2());
        listFragments.add(new Fragment3());

        listFragments.add(new Fragment1());
        listFragments.add(new Fragment2());
        listFragments.add(new Fragment3());

        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(
                getSupportFragmentManager(), listFragments);
        viewPager.setAdapter(myFragmentPagerAdapter);
        TextView tx = (TextView)findViewById(R.id.test);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/learning_curve_regular_ot_ps.otf");
//        Log.d("HERE", String.valueOf(getAssets()));
//        tx.setTypeface(custom_font);
	}

    private String getDay(int nDay){
        Calendar cal = Calendar.getInstance();
        cal.add( Calendar.DAY_OF_YEAR, nDay);
        Date tenDaysAgo = cal.getTime();
        SimpleDateFormat format_ = new SimpleDateFormat("EE", Locale.US);
        return format_.format(tenDaysAgo);
    }

    private String completeDay(String day){
        String result = "Undefined";
        switch(day){
            case "Mon" : result = "Lundi";
                break;
            case "Tue" : result = "Mardi";
                break;
            case "Wed" : result = "Mercredi";
                break;
            case "Thu" : result = "Jeudi";
                break;
            case "Fri" : result = "Vendredi";
                break;
            case "Sat" : result = "Samedi";
                break;
            case "Sun" : result = "Dimanche";
                break;
            default : result = "Undefined" ;
        }
        return result;
    }
    private ArrayList<String> returnDays(){
        ArrayList<String> days = new ArrayList<String>();
        String day = "";
        for(int i = 0 ; i > -15 ; i--){

            if(i == 0 ) day = "Aujourd'hui";
            else if ( i == -1) day = "Hier";
            else if (i == -2) day = "Avant hier";
            else day = completeDay(getDay(i));

            days.add(day);
        }

        return days;
    }
    private void initTabHost() {

        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();
        String[] tabNames = new String[ returnDays().size() ];
        returnDays().toArray( tabNames );

//        String [] tabNames = {};

        for(int i=0; i < tabNames.length ; i++){
            TabHost.TabSpec tabSpec;
            tabSpec = tabHost.newTabSpec(tabNames[i]);
            tabSpec.setIndicator(tabNames[i]);
            tabSpec.setContent(new FakeContent(getApplicationContext()));
            tabHost.addTab(tabSpec);
        }

        tabHost.setOnTabChangedListener(this);

    }
	
	 @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int selectedItem) {
        tabHost.setCurrentTab(selectedItem);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabChanged(String tabId) {

        int selectedItem = tabHost.getCurrentTab();
        viewPager.setCurrentItem(selectedItem);
        HorizontalScrollView hScrollView = (HorizontalScrollView) findViewById(R.id.h_scroll_view);
        View tabView = tabHost.getCurrentTabView();
        int scrollPos = tabView.getLeft() - (hScrollView.getWidth() - tabView.getWidth())/2 ;
        hScrollView.smoothScrollTo(scrollPos, 0);

    }

    public  class FakeContent implements TabHost.TabContentFactory {

        Context context;

        public FakeContent(Context context){

            this.context = context;
        }

        @Override
        public View createTabContent(String tag) {
            View fakeView = new View(this.context);
            fakeView.setMinimumHeight(0);
            fakeView.setMinimumWidth(0);

            return fakeView;
        }
    }

    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        List<Fragment> listFragments = new ArrayList<Fragment>();
        listFragments.add(new Fragment1());
        listFragments.add(new Fragment2());
        listFragments.add(new Fragment3());

        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(
                getSupportFragmentManager(), listFragments);
        viewPager.setAdapter(myFragmentPagerAdapter);
        viewPager.setOnPageChangeListener(this);

    }

}
