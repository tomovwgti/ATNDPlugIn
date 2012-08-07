
package com.tomovwgti.atnd;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.vvakame.util.jsonpullparser.JsonFormatException;

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

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.tomovwgti.atnd.json.AtndEventResponse;
import com.tomovwgti.atnd.json.AtndEventResponseGen;
import com.tomovwgti.atnd.json.AtndEventResult;
import com.tomovwgti.atnd.lib.Iso8601;

/**
 * ATNDの登録イベント一覧をネットワークから取得
 */
public class AtndEventTask extends AsyncTask<String, Void, Boolean> {
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
        schReg.register(new Scheme(HttpHost.DEFAULT_SCHEME_NAME, PlainSocketFactory
                .getSocketFactory(), 80));

        // HTTPパラメータ設定
        HttpParams httpParams;
        httpParams = new BasicHttpParams();
        HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(httpParams, HTTP.UTF_8);

        // HTTPクライアント生成
        httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(httpParams, schReg),
                httpParams);
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
    protected Boolean doInBackground(String... twitter_id) {
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
            response = httpClient.execute(new HttpHost(ATND_URI), new HttpGet(uriBuilder.build()
                    .toString()));
        } catch (Exception e) {
            Log.i("ERROR", "HTTP Request error");
            return false;
        }

        // レスポンスコードを確認
        try {
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                Log.i("ERROR", "Invalid Response code " + response.getStatusLine().getStatusCode());
                return false;
            }
        } catch (Exception e) {
            Log.e("ERROR", "Connection Error");
            return false;
        }

        // JSON解析
        AtndEventResponse eventResult = null;
        try {
            HttpEntity entity = response.getEntity();
            InputStream input = entity.getContent();
            eventResult = AtndEventResponseGen.get(input);
        } catch (IOException e) {
            Log.i("ERROR", "Read Buffer error");
            return false;
        } catch (JsonFormatException e) {
            Log.i("ERROR", "JSON Parse error");
            return false;
        }

        // 日付確認
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        List<AtndEventResult> eventlist = eventResult.getEvents();
        for (AtndEventResult list : eventlist) {
            if (list.owner == null || list.owner.equals("null")) {
                // オーナーがTwitterIDではない
                list.owner = list.getOwnerNickname();
                list.icon = null; // icon
                list.IsOwner = false;
            }
            Calendar calendar = Iso8601.getCalendar(list.start);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            if (!calendar.before(today)) {
                // 今日を含む未来の予定のみ登録
                String event_day = (calendar.get(Calendar.MONTH) + 1) + "/"
                        + calendar.get(Calendar.DAY_OF_MONTH);
                list.setTitle(event_day + "  " + list.getTitle());
                map.put(list.start, list);
            }
        }
        return true;
    }

    /**
     * ネットワークからデータ受信後の処理
     */
    @Override
    protected void onPostExecute(Boolean flag) {
        // プログレスバーを消去
        progress.dismiss();
        if (!flag) {
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
     * 参加しているイベントをリスト表示する
     */
    private void setItems() {
        ListView listView = (ListView) activity.findViewById(R.id.listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
                android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        // リストが空のときに表示されるViewを指定
        View emptyView = activity.findViewById(R.id.empty);
        listView.setEmptyView(emptyView);
        if (list.size() == 0) {
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
            return ((Comparable) s1).compareTo(s2) * -1;
        }
    }

    private void ViewToast() {
        Toast.makeText(activity, "通信に失敗しました", Toast.LENGTH_LONG).show();
    }
}
