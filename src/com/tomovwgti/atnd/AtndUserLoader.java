
package com.tomovwgti.atnd;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.tomovwgti.atnd.json.AtndUserResponse;
import com.tomovwgti.atnd.json.AtndUserResponseGen;
import com.tomovwgti.atnd.json.Users;

/**
 * イベント参加者の一覧をダウンロード
 * 
 * @author tomo
 */
public class AtndUserLoader extends AsyncTaskLoader<List<Users>> {
    static final String TAG = AtndUserLoader.class.getSimpleName();

    // ATND URI
    private static final String ATND_URI = "api.atnd.org";
    private final DefaultHttpClient httpClient;
    private int eventId;

    public AtndUserLoader(Context context, int id) {
        super(context);

        this.eventId = id;
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
    public List<Users> loadInBackground() {
        // URIを設定
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.path("/events/users/");
        // event_id
        uriBuilder.appendQueryParameter("event_id", String.valueOf(eventId));
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
        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            Log.i("ERROR", "Invalid Response code " + response.getStatusLine().getStatusCode());
            return null;
        }

        // JSON解析
        AtndUserResponse userResult = null;
        try {
            HttpEntity entity = response.getEntity();
            InputStream input = entity.getContent();
            userResult = AtndUserResponseGen.get(input);
        } catch (IOException e) {
            Log.i("ERROR", "Read Buffer error");
            return null;
        } catch (JsonFormatException e) {
            Log.i("ERROR", "JSON Parse error");
            return null;
        }

        return userResult.getEvents().get(0).getUsers();
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
