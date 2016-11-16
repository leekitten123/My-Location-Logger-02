package com.example.hyojin.mylocationlogger02;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Intent intent ;

    Button btnSave, btnMap ;
    EditText editText ;

    Double latitude = 0.0;
    Double longitude = 0.0;

    static int numSpinner = 0 ;

    MyDB db = new MyDB(this) ;
    SQLiteDatabase sqLiteDatabase ;

    PieChart pieChart ;
    MyPieChart mypieChart ;

    ArrayList<LocationSet> locationSets = new ArrayList<LocationSet>() ;

    int[] numForGraph = new int[4] ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSave = (Button) findViewById(R.id.btnSave) ;
        btnMap = (Button) findViewById(R.id.btnMap) ;

        Spinner s = (Spinner)findViewById(R.id.spinner1);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                numSpinner = position ;
            }

            public void onNothingSelected(AdapterView<?> parent) {}
        });

        checkDangerousPermissions() ;
    }

    /** 위치 및 내역 저장 **/
    void onClickSave(View view) {
        editText = (EditText) findViewById(R.id.editText);

        startLocationService() ;

        db.insert(latitude, longitude, numSpinner, editText.getText().toString());

        editText.setText("");
    }

    /** DB 내용을 지도에 표시 **/
    void onClickMap(View view) {
        intent = new Intent(getApplicationContext(), MapsActivity.class) ;
        startActivity(intent);
    }

    /** 버튼을 통한 차트 표시 **/
    void onClickChart(View view) {
        locationSets.clear();
        db.getResult(locationSets);

        for (int i = 0 ; i < numForGraph.length ; i++) {
            numForGraph[i] = 0 ;
        }

        for (int i = 0 ; i < locationSets.size() ; i++) {
            ++numForGraph[locationSets.get(i).category] ;
            numForGraph[locationSets.get(i).category] *= 10 ;
        }

        pieChart = (PieChart) findViewById(R.id.pie_Chart) ;

        Description desc = new Description();
        desc.setText("Category Stats");
        pieChart.setDescription(desc);

        mypieChart = new MyPieChart(pieChart) ;
        mypieChart.setyData(numForGraph);
        mypieChart.addData();
    }

    /** 버튼을 통한 데이터베이스 초기화 **/
    void onClickReset(View view) {
        sqLiteDatabase = db.getWritableDatabase() ;
        db.onUpgrade(sqLiteDatabase,1,2);
        db.close();
    }

    /** 현재 위치 정보를 받는 함수 **/
    void startLocationService() {
        /** 위치 관리자 객체 참조 **/
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        /** 위치 정보를 받을 리스너 생성 **/
        GPSListener gpsListener = new GPSListener();

        try {
            /** GPS를 이용한 위치 요청 **/
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, gpsListener);

            /** 네트워크를 이용한 위치 요청 **/
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, gpsListener);
        } catch(SecurityException ex) {
            ex.printStackTrace();
        }

        Toast.makeText(getApplicationContext(), "위치 확인 완료", Toast.LENGTH_SHORT).show();
    }

    /** My LocationListener **/
    private class GPSListener implements LocationListener {
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }

        public void onProviderDisabled(String provider) {}
        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    }

    /** Permisson **/
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    /** Permission **/
    private void checkDangerousPermissions() {
        String[] permissions = {
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

}
