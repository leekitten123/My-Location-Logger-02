package com.example.hyojin.mylocationlogger02;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    final MyDB db = new MyDB(this) ;

    ArrayList<LocationSet> locationSets = new ArrayList<LocationSet>() ;

    String[] categoryName = {"공부", "산책", "식사", "축구"} ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        locationSets.clear();
        db.getResult(locationSets);

        for (int i = 0 ; i < locationSets.size() ; i++)
        {
            MarkerOptions marker = new MarkerOptions();
            marker.position(new LatLng(locationSets.get(i).latitude, locationSets.get(i).longitude));
            marker.title(locationSets.get(i).whatdo);
            marker.snippet(categoryName[locationSets.get(i).category]);

            mMap.addMarker(marker);
        }
        
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.544912, 126.986319), 15));
    }
}
