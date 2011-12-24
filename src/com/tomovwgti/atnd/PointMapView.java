
package com.tomovwgti.atnd;

import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.tomovwgti.atnd.lib.PointOverlay;

/**
 * 地図上に位置を表示をする
 * 
 * @author tomo_bg5
 */
public class PointMapView extends MapActivity {

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.map);

        String latlon = getIntent().getStringExtra("LATLON");
        String placeName = getIntent().getStringExtra("PLACE");
        // Lat Lonを取り出す(geo:を取り除く)
        String lat = latlon.substring(4, latlon.lastIndexOf(","));
        String lon = latlon.substring(latlon.lastIndexOf(",") + 1);
        // ポイント位置
        GeoPoint geo = new GeoPoint((int) (Double.parseDouble(lat) * 1E6),
                (int) (Double.parseDouble(lon) * 1E6));
        // Map表示
        MapView map = (MapView) findViewById(R.id.mapview);
        map.setClickable(true);
        map.getController().setZoom(18);
        map.getController().setCenter(geo);
        map.setBuiltInZoomControls(true);
        PointOverlay marker = new PointOverlay(this, getResources().getDrawable(R.drawable.strike),
                geo, placeName);
        map.getOverlays().add(marker);
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}
