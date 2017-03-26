package com.redeyesoftware.poober;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by georgeeisa on 2017-03-25.
 */

public class MapMarkerClickListener implements GoogleMap.OnInfoWindowClickListener {

    public static PooPostData poo;

    @Override
    public void onInfoWindowClick(Marker marker) {
        poo = (PooPostData) marker.getTag();
        Log.d("DEBUG:", poo.getMoney());

        if (poo.getPic().equals("")) {
            Intent newActivity = new Intent(MainActivity.me, PickPooActivity.class);
            MainActivity.me.startActivity(newActivity);
        } else {
            Intent newActivity = new Intent(MainActivity.me, PickerActivity.class);
            MainActivity.me.startActivity(newActivity);
        }
    }
}
