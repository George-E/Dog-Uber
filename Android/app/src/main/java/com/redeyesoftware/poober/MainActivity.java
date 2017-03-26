package com.redeyesoftware.poober;

import android.content.Intent;
import android.location.Location;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationServices;


public class MainActivity extends AppCompatActivity {


    public static MainActivity  me;

    private static HomePagesAdapter mFragPagerAdapter;
    private static ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        me = this;
        NetworkingUtility.setUpRequestQueue(this);


        FloatingActionButton makepoo =  (FloatingActionButton) findViewById(R.id.floatingActionButton);

        makepoo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                //Intent newActivity = new Intent(MainActivity.this, MapsActivity.class);
                //startActivity(newActivity);
                NetworkingUtility.post("/addpoo", new String[]{"time","price","description","longitude","latitude","picture"}, new String[]{"555","$5.00","Yolo","40","50",""});

            }
        });


        mFragPagerAdapter = new HomePagesAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mFragPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int index) {
                //updateFragments();
                //getActionBar().setSelectedNavigationItem(index);
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {}
            @Override
            public void onPageScrollStateChanged(int arg0) {}
        });

        //the below wouldnt be necessary if tablayout was defined inside the view pager in the xml
        //in this case, instead, it is part of the toolbar to create  cleaner app bar
        TabLayout tablayout = (TabLayout)findViewById(R.id.tab_layout);
        tablayout.setupWithViewPager(mViewPager);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.animate().translationY(-myToolbar.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
        //This method sets the toolbar as the app bar for the activity
        //By default, the action bar contains just the name of the app and an overflow menu.
        // The options menu initially contains just the Settings item.


    }

    public static void updateFragments() {
        mFragPagerAdapter.notifyDataSetChanged();
    }



}
