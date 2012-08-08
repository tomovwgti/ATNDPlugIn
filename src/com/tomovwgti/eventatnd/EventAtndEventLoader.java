
package com.tomovwgti.eventatnd;

import java.io.IOException;
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
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.tomovwgti.atnd.AtndEventLoader;
import com.tomovwgti.atnd.lib.Iso8601;
import com.tomovwgti.eventatnd.json.EventAtndEventResponse;
import com.tomovwgti.eventatnd.json.EventAtndEventResponseGen;
import com.tomovwgti.eventatnd.json.EventAtndEventResult;

/**
 * event ATNDのイベントを検索
 * 
 * @author tomo
 */
public class EventAtndEventLoader extends AsyncTaskLoader<Map<String, EventAtndEventResult>> {
    static final String TAG = AtndEventLoader.class.getSimpleName();

    // ATND URI
    private static final String ATND_URI = "api.atnd.org";
    private final DefaultHttpClient httpClient;
    private Map<String, EventAtndEventResult> map;
    private String twitterId;

    public EventAtndEventLoader(Context context, String name) {
        super(context);

        this.twitterId = name;
        map = new TreeMap<String, EventAtndEventResult>(new DescComparator());
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
    public Map<String, EventAtndEventResult> loadInBackground() {
        // URIを設定
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.path("/eventatnd/event/");
        // event_id
        uriBuilder.appendQueryParameter("twitter_id", twitterId);
        // format
        uriBuilder.appendQueryParameter("format", "json");

        // HTTP Request送信
        HttpResponse response = null;
        try {
            response = httpClient.execute(new HttpHost(ATND_URI), new HttpGet(uriBuilder.build()
                    .toString()));
        } catch (Exception e) {
            Log.i("ERROR", "HTTP Request error");
            return null;
        }

        // レスポンスコードを確認
        try {
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                Log.i("ERROR", "Invalid Response code " + response.getStatusLine().getStatusCode());
                return null;
            }
        } catch (Exception e) {
            Log.e("ERROR", "Connection Error");
            return null;
        }

        // JSON解析
        EventAtndEventResponse eventResult = null;
        try {
            HttpEntity entity = response.getEntity();
            String json = EntityUtils.toString(entity);
            eventResult = EventAtndEventResponseGen.get(json);
        } catch (IOException e) {
            Log.i("ERROR", "Read Buffer error");
            return null;
        } catch (JsonFormatException e) {
            Log.i("ERROR", "JSON Parse error");
            return null;
        }

        // 件数が１件も見つからない場合
        if (eventResult.resultsReturned == 0) {
            // 空のMAPを返す
            return map;
        }

        // 日付確認
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        List<EventAtndEventResult> eventlist = eventResult.getEvents().get(0).getEvent();
        for (EventAtndEventResult list : eventlist) {
            if (list.owner == null || list.owner.equals("null") || list.owner.equals("")) {
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
        return map;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
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
}
