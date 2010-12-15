package com.tomovwgti.atnd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tomovwgti.atnd.lib.Iso8601;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * ATNDの登録イベント一覧をネットワークから取得
 */
public class AtndEventTask extends AsyncTask<String, Void, HttpResponse> {
	// ATND URI
	private static final String ATND_URI = "api.atnd.org";
	// プログレスバー
	private ProgressDialog progress;
	private Activity activity = null;
	private final DefaultHttpClient httpClient;
	// 検索結果
	private List<AtndEventResult> events;
	// リストビュー用
	private List<String> list;
    private Map<String, AtndEventResult> map;

	public AtndEventTask(Activity activity) {
		this.activity = activity;
    	map = new TreeMap<String, AtndEventResult>(new DescComparator());
        // スキーマ登録
        SchemeRegistry schReg = new SchemeRegistry();
        schReg.register(new Scheme(HttpHost.DEFAULT_SCHEME_NAME, PlainSocketFactory.getSocketFactory(), 80));

        // HTTPパラメータ設定
        HttpParams httpParams;
        httpParams= new BasicHttpParams();
        HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(httpParams, HTTP.UTF_8);

        // HTTPクライアント生成
        httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(httpParams, schReg), httpParams);
	}

	@Override
	protected void onPreExecute() {
		// プログレスバーを出す
		progress = new ProgressDialog(activity);
		progress.setMessage("読み込み中...");
		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progress.show();
	}

	/**
	 * バックグランドでデータ取得を行う
	 */
	@Override
	protected HttpResponse doInBackground(String... twitter_id) {
        // URIを設定
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.path("/events/");        
        // event_id
        uriBuilder.appendQueryParameter("twitter_id", twitter_id[0]);
        // format
        uriBuilder.appendQueryParameter("format", "json");
        
        // HTTP Request送信
        HttpResponse response = null;
        try {
			response = httpClient.execute(new HttpHost(ATND_URI), new HttpGet(uriBuilder.build().toString()));
		} catch (Exception e) {
            Log.i("ERROR", "HTTP Request error");
            ViewToast();
			return null;
		}
        return response;
	}
	
	/**
	 * ネットワークからデータ受信後の処理
	 */
	@Override
	protected void onPostExecute(HttpResponse response) {
		// プログレスバーを消去
		progress.dismiss();
		if ( response == null ) {
			return;
		}

        // レスポンスコードを確認
		try {
	        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
	            Log.i("ERROR", "Invalid Response code " + response.getStatusLine().getStatusCode());
	            ViewToast();
	            return;
	        }
		} catch (Exception e) {
            Log.e("ERROR", "Connection Error");
            ViewToast();
            return;
		}

        // レスポンスをStringに変換
        StringBuilder json = new StringBuilder();
        try{
            HttpEntity entity = response.getEntity();
            InputStream input = entity.getContent();
            InputStreamReader reader = new InputStreamReader(input);
            BufferedReader bufReader = new BufferedReader(reader);
            String line;
            while((line = bufReader.readLine()) != null){
                json.append(line);
            }
        }
        catch (IOException e) {
            Log.i("ERROR", "Read Buffer error");
            ViewToast();
            return;
        }

        // JSON解析
        try{
        	// 取得するデータ
        	final String EVENTS  = "events";
        	final String EVENTID = "event_id";
        	final String TITLE   = "title";
        	final String DESC    = "description";
        	final String OWNER   = "owner_twitter_id";
        	final String ICON    = "owner_twitter_img";
        	final String START   = "started_at";
        	final String END     = "ended_at";
        	final String ADDRESS = "address";
        	final String PLACE   = "place";
        	final String LAT     = "lat";
        	final String LON     = "lon";
        	
            JSONObject jsonRoot = new JSONObject(json.toString());
            JSONArray jsonRslts = jsonRoot.getJSONArray(EVENTS);

        	Calendar today = Calendar.getInstance();
        	today.set(Calendar.HOUR_OF_DAY, 0);
        	today.set(Calendar.MINUTE, 0);
        	today.set(Calendar.SECOND, 0);
        	today.set(Calendar.MILLISECOND, 0);

            for ( int i = 0; i < jsonRslts.length(); i++ ) {
                AtndEventResult tmp = new AtndEventResult();
            	JSONObject jsonRslt = jsonRslts.getJSONObject(i);
            	tmp.event_id = jsonRslt.getString(EVENTID);
            	tmp.description = jsonRslt.getString(DESC);
            	tmp.icon = jsonRslt.getString(ICON);
            	tmp.owner = jsonRslt.getString(OWNER);
            	if ( tmp.owner.equals("null") ) {
            		// オーナーがTwitterIDではない
            		tmp.owner = jsonRslt.getString("owner_nickname");
            		tmp.icon = null; // icon
            		tmp.IsOwner = false;
            	}
            	tmp.start = jsonRslt.getString(START);
            	tmp.end = jsonRslt.getString(END);
            	tmp.address = jsonRslt.getString(ADDRESS);
            	tmp.place = jsonRslt.getString(PLACE);
            	tmp.lat = jsonRslt.getString(LAT);
            	tmp.lon = jsonRslt.getString(LON);
            	Calendar calendar = Iso8601.getCalendar(tmp.start);
            	calendar.set(Calendar.HOUR_OF_DAY, 0);
            	calendar.set(Calendar.MINUTE, 0);
            	calendar.set(Calendar.SECOND, 0);
            	calendar.set(Calendar.MILLISECOND, 0);
            	if ( !calendar.before(today) ) {
            		// 今日を含む未来の予定のみ登録
                	String event_day = (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH);
                	tmp.title = event_day + "  " + jsonRslt.getString(TITLE);
                	map.put(tmp.start, tmp);
            	}      
            }
        }
        catch(JSONException e){
            ViewToast();
            return;
        }

        // イベント取得
    	events = new ArrayList<AtndEventResult>();
    	list = new ArrayList<String>();
    	for (String key : map.keySet()) {
    		// 各イベントのタイトルをリスト化
        	list.add(map.get(key).title);
        	// イベントを格納
        	events.add(map.get(key));
    	}
    	// リストビューに表示
    	setItems();
	}

	/**
	 *  参加しているイベントをリスト表示する
	 */
    private void setItems() {
    	ListView listView = (ListView)activity.findViewById(R.id.listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
		//リストが空のときに表示されるViewを指定
        View emptyView = activity.findViewById(R.id.empty);
		listView.setEmptyView(emptyView);
		if ( list.size() == 0 ) {
			// 参加イベント無しの場合
			emptyView.setVisibility(View.VISIBLE);
		}
        listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {				
				// 表示するデータを設定する
	            AtndEventResult eventInfo = events.get(position);
	            Intent intent = new Intent(activity, AtndEventInfo.class);
	            Bundle bundle = new Bundle();
	            bundle.putParcelable("EVENT", eventInfo);
	            intent.putExtras(bundle);
	            activity.startActivity(intent);
			}
        });
    }

    /**
     * 降順用コンパレータ 
     */ 
    private class DescComparator implements Comparator { 
		@Override
		public int compare(Object s1, Object s2) {
    		return ( (Comparable)s1 ).compareTo( s2 ) * -1; 
		}
    }
    
    private void ViewToast() {
        Toast.makeText(activity, "通信に失敗しました", Toast.LENGTH_LONG).show();
    }
}
