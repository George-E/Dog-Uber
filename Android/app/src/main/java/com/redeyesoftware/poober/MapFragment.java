package com.redeyesoftware.poober;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment  {


    public static MapFragment me;

    MapView mMapView;
    private GoogleMap googleMap;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    //returns what goes inside the fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        me = this;

        Log.v("DEBUG:","Map Frag Started");

        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;



                // For showing a move to my location button
                //googleMap.setMyLocationEnabled(true);



                // For dropping a marker at a point on the Map
                LatLng shopify = new LatLng(43.463968, -80.525226);

                Log.d("DEBUG","request sent");
                NetworkingUtility.getPoops("/poomaster", new String[]{"time","price","description","longitude","latitude","picture"}, "fillMap");

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(shopify).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                //googleMap.setOnMarkerClickListener(new MapMarkerClickListener());
                googleMap.setOnInfoWindowClickListener(new MapMarkerClickListener());
            }
        });

        return rootView;
    }

   @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


    public void addMapMarkers() {

        Log.d("DEBUG","request finished");

        for (int i = 0; i < NetworkingUtility.comments.length; i++) {
            //Log.d("long timsetamp",NetworkingUtility.comments[i][3]);

            //"time","price","description","longitude","latitude","picture"

            String time = NetworkingUtility.comments[i][0];
            double lat = Double.parseDouble(NetworkingUtility.comments[i][4]);
            double lon = Double.parseDouble(NetworkingUtility.comments[i][3]);
            String desc = NetworkingUtility.comments[i][2];
            String money = NetworkingUtility.comments[i][1];
            String pic = NetworkingUtility.comments[i][5];

            LatLng pos = new LatLng(lat, lon);

            Marker m;
            if (pic.equals("")) {
                m = googleMap.addMarker(new MarkerOptions()
                        .position(pos)
                        .title(money)
                        .snippet("Click to Pick!")
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.poop_emoji))
                );
            } else {
                m = googleMap.addMarker(new MarkerOptions()
                        .position(pos)
                        //.title(money)
                        .snippet("Click to Verify!")
                        .icon(BitmapDescriptorFactory.fromBitmap(StringToBitMap(pic)))
                );
            }
            m.setTag(new PooPostData(time,lat,lon,desc,money,pic));


           // CameraPosition cameraPosition = new CameraPosition.Builder().target(pos).zoom(12).build();
           // googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }

    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }


}
