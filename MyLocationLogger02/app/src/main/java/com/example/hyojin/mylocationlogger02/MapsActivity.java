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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        db.getResult(locationSets);

        for (int i = 0 ; i < locationSets.size() ; i++)
        {
            //showMarker(locationSets.get(i).latitude, locationSets.get(i).longitude);
            MarkerOptions marker = new MarkerOptions();
            marker.position(new LatLng(locationSets.get(i).latitude, locationSets.get(i).longitude));
            marker.title(locationSets.get(i).whatdo);
            marker.snippet(locationSets.get(i).whatdo);
            marker.draggable(true);

            mMap.addMarker(marker);
        }
    }

    private void showMarker(Double latitude, Double longitude) {
        MarkerOptions marker = new MarkerOptions();
        marker.position(new LatLng(latitude, longitude));
        marker.title("국민대");
        marker.snippet("이상욱");
        marker.draggable(true);

        mMap.addMarker(marker);
    }
}
