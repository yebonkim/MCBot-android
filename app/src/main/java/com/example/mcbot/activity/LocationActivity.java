package com.example.mcbot.activity;

// NMapActivity를 활용해 간단히 지도를 전체화면으로 표시하는 예제
// 본 예제는 1개의 파일 MainActivity.java 로 구성되어 있습니다.
//package com.example.okgosu.mynavermap;
//package com.nhn.android.mapviewer;
// (중요) 패키지명은 애플리케이션 설정의 Android 패키지명과 반드시 일치해야 함
import android.os.Bundle;


import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mcbot.R;
import com.example.mcbot.util.SharedPreferencesManager;
import com.google.firebase.database.DatabaseReference;
import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapOverlay;
import com.nhn.android.maps.NMapOverlayItem;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.nmapmodel.NMapPlacemark;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPOIitem;
import com.nhn.android.mapviewer.overlay.NMapCalloutCustomOverlay;
import com.nhn.android.mapviewer.overlay.NMapCalloutOverlay;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;

import java.util.ArrayList;




import com.google.firebase.database.FirebaseDatabase;

import android.util.Log;
import android.widget.Button;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


//kwon8999.tistory.com/entry/네이버맵-API-사용하기1네이버앱-등록-및-기본-맵-띄우기 [Kwon's developer]
//yq6QuSlK_N : 비번

public class LocationActivity extends NMapActivity {

    @BindView(R.id.mapView)
    NMapView mMapView;// 지도 화면 View
    private final String CLIENT_ID = "LVSBH7HiciBcd5AJ2f2v";// 애플리케이션 클라이언트 아이디 값

    private NMapController mMapController;

    FirebaseDatabase database;
    DatabaseReference addressDB;

    double lat;
    double lan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        ButterKnife.bind(this);

        mMapView.setClientId(CLIENT_ID); // 클라이언트 아이디 값 설정
        mMapView.setClickable(true);
        mMapView.setEnabled(true);
        mMapView.setFocusable(true);
        mMapView.setFocusableInTouchMode(true);
        mMapView.requestFocus();

        getIntentData();

        mMapController = mMapView.getMapController();
        mMapController.setMapCenter(lan, lat);

    }

    protected void getIntentData() {
        lat = getIntent().getDoubleExtra("lat", 37.56399272515767);
        lan = getIntent().getDoubleExtra("lan", 126.98022574522575);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}