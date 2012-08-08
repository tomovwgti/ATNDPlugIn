
package com.tomovwgti.atnd;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tomovwgti.atnd.json.Users;
import com.tomovwgti.atnd.lib.ConnectionStatus;
import com.tomovwgti.atnd.lib.ListViewItem;
import com.tomovwgti.atnd.lib.UserArrayAdapter;

/**
 * イベントの参加者一覧を表示する
 */
public class AtndUsers extends FragmentActivity implements LoaderCallbacks<List<Users>> {
    static final String TAG = AtndUsers.class.getSimpleName();

    // プログレスバー
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo);

        // イベントIDを取得
        final int eventId = getIntent().getIntExtra("EVENTID", 0);
        // イベントの名を取得
        final String event = getIntent().getStringExtra("EVENT");

        TextView eventTitle2 = (TextView) findViewById(R.id.eventtitle2);
        eventTitle2.setText(event);
        eventTitle2.setTextColor(Color.GREEN);
        eventTitle2.setBackgroundColor(Color.DKGRAY);
        // 詳細ページへジャンプ
        eventTitle2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://atnd.org/events/"
                        + eventId));
                startActivity(intent);
            }
        });

        // ネットワーク接続の確認
        if (!ConnectionStatus.isConnected(this)) {
            Toast.makeText(this, "ネットワークに接続できません", Toast.LENGTH_LONG).show();
            return;
        }

        // イベント参加者の取得
        Bundle bundle = new Bundle();
        bundle.putInt("id", eventId);
        getSupportLoaderManager().initLoader(0, bundle, this);
    }

    /**
     * ローダーを初期化した際に呼ばれる
     */
    @Override
    public Loader<List<Users>> onCreateLoader(int id, Bundle bundle) {
        // プログレスバーを出す
        progress = new ProgressDialog(this);
        progress.setMessage("読み込み中...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();

        // オブジェクト作って返すだけ
        AtndUserLoader loader = new AtndUserLoader(getApplication(), bundle.getInt("id"));
        loader.forceLoad(); // これでロードが始まる。AsyncTaskLoader#onStartLoading内に実装するのも可。
        return loader;
    }

    /**
     * データ読み込みが完了したときに呼ばれる
     * 
     * @param data AsyncTaskLoader#loadInBackgroundで返した値
     */
    @Override
    public void onLoadFinished(Loader<List<Users>> loader, List<Users> result) {
        // プログレスバーを消去
        progress.dismiss();
        if (result == null) {
            ViewToast();
            return;
        }

        List<Users> users = new ArrayList<Users>();
        List<String> userlist = new ArrayList<String>();
        List<ListViewItem> items = new ArrayList<ListViewItem>();

        for (Users list : result) {
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

        // リストビューに表示
        setItems(items, users);
    }

    @Override
    public void onLoaderReset(Loader<List<Users>> arg0) {
        // TODO Auto-generated method stub

    }

    /**
     * 参加しているイベントをリスト表示する
     */
    private void setItems(List<ListViewItem> items, final List<Users> users) {

        ListView listView = (ListView) findViewById(R.id.userinfo);
        UserArrayAdapter adapter = new UserArrayAdapter(this, R.layout.listview_item, items);
        listView.setAdapter(adapter);

        // リストが空のときに表示されるViewを指定
        View emptyView = findViewById(R.id.empty);
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
                    startActivity(intent);
                }
            }
        });
    }

    private void ViewToast() {
        Toast.makeText(this, "通信に失敗しました", Toast.LENGTH_LONG).show();
    }
}
