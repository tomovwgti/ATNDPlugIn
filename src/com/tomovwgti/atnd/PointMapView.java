
package com.tomovwgti.atnd;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * 地図上に位置を表示をする
 * 
 * @author tomo
 */
public class PointMapView extends FragmentActivity {
    static final String TAG = PointMapView.class.getSimpleName();

    private String mPlaceName;
    private LatLng mLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_view);

        String latlon = getIntent().getStringExtra("LATLON");
        mPlaceName = getIntent().getStringExtra("PLACE");
        // Lat Lonを取り出す(geo:を取り除く)
        String lat = latlon.substring(4, latlon.lastIndexOf(","));
        String lon = latlon.substring(latlon.lastIndexOf(",") + 1);
        // ポイント位置
        mLatLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
        // Map表示
        GoogleMap map = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map)).getMap();
        try {
            MapsInitializer.initialize(this);
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.d(TAG, "You must update Google Maps.");
            finish();
        }
        CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(mLatLng, 18);
        map.moveCamera(cu);

        // マーカー
        map.addMarker(new MarkerOptions().position(mLatLng).title(mPlaceName)
                .snippet("Clickしてルートを検索"));
        map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                final String transport[] = {
                        "電車", "車", "徒歩"
                };
                final AlertDialog.Builder select = new AlertDialog.Builder(PointMapView.this);
                select.setTitle("どうやって行く？");
                select.setItems(transport, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        doRoute(item);
                    }
                });
                select.create().show();
            }

            /**
             * @param item
             */
            private void doRoute(int item) {
                String dirflg = "&dirflg=";
                switch (item) {
                    case 0: // 電車
                        dirflg = dirflg + "r";
                        break;
                    case 1: // 車
                        dirflg = dirflg + "d";
                        break;
                    case 2: // 徒歩
                        dirflg = dirflg + "w";
                        break;
                }

                // URIを作成
                String lat = String.valueOf(mLatLng.latitude);
                String lon = String.valueOf(mLatLng.longitude);
                String uri = "http://maps.google.com/maps?myl=saddr&daddr=" + lat + "," + lon
                        + dirflg;
                Log.i("MAP", uri);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setClassName("com.google.android.apps.maps",
                        "com.google.android.maps.MapsActivity");
                intent.setData(Uri.parse(uri));
                startActivity(intent);
            }
        });
    }
}
