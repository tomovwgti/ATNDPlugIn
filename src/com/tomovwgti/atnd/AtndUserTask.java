
package com.tomovwgti.atnd;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.tomovwgti.atnd.json.AtndUserResponse;
import com.tomovwgti.atnd.json.AtndUserResponseGen;
import com.tomovwgti.atnd.json.Users;
import com.tomovwgti.atnd.lib.ListViewItem;
import com.tomovwgti.atnd.lib.UserArrayAdapter;

/**
 * イベントの参加者一覧をネットワークから取得する
 */
public class AtndUserTask extends AsyncTask<String, Void, Boolean> {
    // ATND URI
    private static final String ATND_URI = "api.atnd.org";
    // プログレスバー
    private ProgressDialog progress;
    private Activity activity = null;
    private final DefaultHttpClient httpClient;
    // 検索結果
    private List<Users> users;
    // リストビュー用
    private List<String> userlist;

    private List<ListViewItem> items;

    public AtndUserTask(Activity activity) {
        this.activity = activity;

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
    protected Boolean doInBackground(String... event_id) {
        // URIを設定
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.path("/events/users/");
        // event_id
        uriBuilder.appendQueryParameter("event_id", event_id[0]);
        // format
        uriBuilder.appendQueryParameter("format", "json");

        // HTTP Request送信
        HttpResponse response = null;
        try {
            response = httpClient.execute(new HttpHost(ATND_URI), new HttpGet(uriBuilder.build()
                    .toString()));
        } catch (Exception e) {
            Log.i("ERROR", "HTTP Request error");
            ViewToast();
            return false;
        }

        // レスポンスコードを確認
        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            Log.i("ERROR", "Invalid Response code " + response.getStatusLine().getStatusCode());
            ViewToast();
            return false;
        }

        // JSON解析
        AtndUserResponse userResult = null;
        try {
            HttpEntity entity = response.getEntity();
            InputStream input = entity.getContent();
            userResult = AtndUserResponseGen.get(input);
        } catch (IOException e) {
            Log.i("ERROR", "Read Buffer error");
            return false;
        } catch (JsonFormatException e) {
            Log.i("ERROR", "JSON Parse error");
            return false;
        }

        users = new ArrayList<Users>();
        userlist = new ArrayList<String>();
        items = new ArrayList<ListViewItem>();

        List<Users> user = userResult.getEvents().get(0).getUsers();
        for (Users list : user) {
            String imageUrl;
            String idText;
            if (list.twitterId == null || list.twitterId.equals("null")) {
                list.IsTwitter = false;
                userlist.add(list.nickname);
                // ListView
                idText = list.nickname;
                imageUrl = null;
            } else {
                userlist.add(list.twitterId);
                userlist.add(list.nickname);
                // ListView
                idText = list.twitterId;
                imageUrl = list.twitterImg;
            }
            users.add(list);
            // ListViewのパーツ
            ListViewItem item = new ListViewItem(idText, imageUrl);
            items.add(item);
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
            return;
        }
        // リストビューに表示
        setItems();
    }

    /**
     * 参加しているイベントをリスト表示する
     */
    private void setItems() {

        ListView listView = (ListView) activity.findViewById(R.id.userinfo);
        UserArrayAdapter adapter = new UserArrayAdapter(activity, R.layout.listview_item, items);
        listView.setAdapter(adapter);

        // リストが空のときに表示されるViewを指定
        View emptyView = activity.findViewById(R.id.empty);
        listView.setEmptyView(emptyView);
        if (items.size() == 0) {
            // 参加イベント無しの場合
            emptyView.setVisibility(View.VISIBLE);
        }
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // String item = userlist.get(position);
                Users user = users.get(position);
                // Twitter IDかどうか調べる
                if (user.IsTwitter) {
                    String url = "http://twitter.com/" + user.twitterId;
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    activity.startActivity(intent);
                }
            }
        });
    }

    private void ViewToast() {
        Toast.makeText(activity, "通信に失敗しました", Toast.LENGTH_LONG).show();
    }
}
