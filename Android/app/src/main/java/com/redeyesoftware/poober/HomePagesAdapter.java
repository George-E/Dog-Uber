package com.redeyesoftware.poober;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

/**
 * Created by George on 19/11/2016.
 */

public class HomePagesAdapter extends FragmentPagerAdapter {


    public HomePagesAdapter(FragmentManager fm) {
        super(fm);
    }


    //doesnt need to be overridden, using to always update fragment
    //This method is called when you call notifyDataSetChanged()
    @Override
    public int getItemPosition(Object object) {
        Log.d("Debug","updating");
        /*Implicitly this method returns POSITION_UNCHANGED value that means something like this: "Fragment is where it should be so change anything."
        So if you need to update Fragment you can do it with one of teh following:
        -Always return POSITION_NONE from getItemPosition() method. It which means: "Fragment must be always recreated"
        -You can create some update() method that will update your Fragment(fragment will handle updates itself)*/
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        Log.d("DEBUG:","page adaptor providing frag num "+position);
        switch (position) {
            case 0:
                return new MapFragment();
            case 1:
                return new PooSelfiesFragment();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Map";
            case 1:
                return "Poo Selfies";
            default:
                return null;
        }
    }

}
