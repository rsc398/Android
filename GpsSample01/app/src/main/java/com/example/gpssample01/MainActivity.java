package com.example.gpssample01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LocationListener {

    LocationManager locationManager;
    String[] gpsPermission = {Manifest.permission.ACCESS_FINE_LOCATION,}; //箱作成

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //サービスの取得
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //権限（パーミッション）のチェック GRANTED は｛許可｝DE
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            //許可されていないのでリクエスト
            ActivityCompat.requestPermissions(this,gpsPermission,1000);
        }else{
            //許可されているのでセットする
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,3,this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //リクエストコードが一致したらOK
        if(requestCode == 1000){
            //権限を付与して開始する
            locationStart();
        }
    }
    private void locationStart() {
        //パーミッションのチェック後更新処理を呼び出す
        //権限（パーミッション）のチェック GRANTED は｛許可｝DE
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //許可されていないのでリクエスト
            ActivityCompat.requestPermissions(this, gpsPermission, 1000);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 3, this);
        //位置情報取得の中止
        //locationManager.removeUpdates();
    }
    //情報(Location)に変化があったときに呼び出されるメソッド
    @Override
    public void onLocationChanged(@NonNull Location location) {
        //TextViewに情報を表示する
        //緯度／軽度を表示するため、textViewを準備する（2つ）
        //緯度の取得（ともにDouble）

        //location.getLatitude();  //緯度の取得
        //location.getLongitude(); //軽度の取得

        setContentView(R.layout.activity_main);

        // text_view： activity_main.xml の TextView の id
        TextView textView = findViewById(R.id.textView);
        TextView textView2 = findViewById(R.id.textView2);
        // テキストを設定
        // R.string.textは"Test TextView"のこと
        String a = String.format("%.3f", location.getLatitude());
        String b = String.format("%.3f", location.getLongitude());
        textView.setText(a);
        textView2.setText(b);
    }
}