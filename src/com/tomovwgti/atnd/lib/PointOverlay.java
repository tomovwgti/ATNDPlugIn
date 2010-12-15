package com.tomovwgti.atnd.lib;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

/**
 * イベント会場のマーカー表示
 */
public class PointOverlay extends ItemizedOverlay<OverlayItem> {

	private Context context;
	// マーカー
	private OverlayItem item;
	// 目的地
	private GeoPoint mGeo;

	public PointOverlay(Context context, Drawable marker, GeoPoint geo, String place) {
		super(boundCenterBottom(marker));
		this.context = context;
		this.mGeo = geo;
		item = new OverlayItem(geo, place, null);
		populate();
	}

	@Override
	protected OverlayItem createItem(int index) {
		return item;
	}

	@Override
	public int size() {
		return 1;
	}

   @Override
   protected boolean onTap(int index) {
	   String place = item.getTitle();
	   final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
	   dialog.setTitle("Event場所");
	   dialog.setMessage(place);
	   dialog.setPositiveButton("ルートを見る", new DialogInterface.OnClickListener() {
		   public void onClick(DialogInterface dialog, int id) {
			   final String transport[] = { "電車", "車", "徒歩" };
			   dialog.dismiss(); // 前のダイアログを閉じる
			   final AlertDialog.Builder select = new AlertDialog.Builder(context);
			   select.setTitle("どうやって行く？");
			   select.setItems(transport, new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int item) {
				    	doRoute(item);
				    }
				});
			   select.create().show();
		   }
	   });
	   // ダイアログを表示
	   dialog.create().show();
	   return true;
   }
   
   /**
    * @param item
    */
   private void doRoute(int item) {
	   String dirflg = "&dirflg=";
	   switch(item) {
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
	   String lat = String.valueOf((double)(mGeo.getLatitudeE6() / 1E6));
	   String lon = String.valueOf((double)(mGeo.getLongitudeE6() / 1E6));
	   String uri = "http://maps.google.com/maps?myl=saddr&daddr=" + lat + "," + lon + dirflg;
	   Log.i("MAP", uri);
       Intent intent = new Intent(); 
       intent.setAction(Intent.ACTION_VIEW); 
       intent.setClassName("com.google.android.apps.maps","com.google.android.maps.MapsActivity"); 
       intent.setData(Uri.parse(uri)); 
       context.startActivity(intent); 
   }
}