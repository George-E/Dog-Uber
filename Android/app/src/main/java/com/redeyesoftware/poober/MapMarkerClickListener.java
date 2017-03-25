package com.redeyesoftware.poober;

import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by georgeeisa on 2017-03-25.
 */

public class MapMarkerClickListener implements GoogleMap.OnInfoWindowClickListener {

    @Override
    public void onInfoWindowClick(Marker marker) {
        PooPostData data = (PooPostData) marker.getTag();
        Log.d("DEBUG:", data.getMoney());
        
    }
}
