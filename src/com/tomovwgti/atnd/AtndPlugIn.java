
package com.tomovwgti.atnd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.tomovwgti.atnd.json.AtndEventResult;
import com.tomovwgti.atnd.lib.ConnectionStatus;

/**
 * ATNDプラグインのメインクラス twiccaから呼び出される
 */
public class AtndPlugIn extends FragmentActivity implements
        LoaderCallbacks<Map<String, AtndEventResult>> {

    // プログレスバー
    private ProgressDialog progress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // プラグイン起動
        Intent intent = getIntent();
        // String name = intent.getStringExtra(Intent.EXTRA_TEXT);
        String name = "tomo_watanabe";

        // ネットワーク接続の確認
        if (!ConnectionStatus.isConnected(this)) {
            Toast.makeText(this, "ネットワークに接続できません", Toast.LENGTH_LONG).show();
            return;
        }
        setTitle("ATND登録リスト");

        // 参加イベントの取得
        Bundle bundle = new Bundle();
        bundle.putString("id", name);
        getSupportLoaderManager().initLoader(0, bundle, this);
    }

    /**
     * ローダーを初期化した際に呼ばれる
     */
    @Override
    public Loader<Map<String, AtndEventResult>> onCreateLoader(int id, Bundle bundle) {
        // プログレスバーを出す
        progress = new ProgressDialog(this);
        progress.setMessage("読み込み中...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();

        // オブジェクト作って返すだけ
        AtndEventLoader loader = new AtndEventLoader(getApplication(),
                bundle.getString("id"));
        loader.forceLoad(); // これでロードが始まる。AsyncTaskLoader#onStartLoading内に実装するのも可。
        return loader;
    }

    /**
     * データ読み込みが完了したときに呼ばれる
     * 
     * @param data AsyncTaskLoader#loadInBackgroundで返した値
     */
    @Override
    public void onLoadFinished(Loader<Map<String, AtndEventResult>> loader,
            Map<String, AtndEventResult> result) {
        // プログレスバーを消去
        progress.dismiss();
        if (result == null) {
            ViewToast();
            return;
        }

        // イベント取得
        List<AtndEventResult> events = new ArrayList<AtndEventResult>();
        List<String> list = new ArrayList<String>();
        for (String key : result.keySet()) {
            // 各イベントのタイトルをリスト化
            list.add(result.get(key).title);
            // イベントを格納
            events.add(result.get(key));
        }
        // リストビューに表示
        setItems(events, list);
    }

    @Override
    public void onLoaderReset(Loader<Map<String, AtndEventResult>> arg0) {
        // TODO Auto-generated method stub

    }

    /**
     * 参加しているイベントをリスト表示する
     */
    private void setItems(final List<AtndEventResult> events, List<String> list) {
        ListView listView = (ListView) findViewById(R.id.listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AtndPlugIn.this,
                android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        // リストが空のときに表示されるViewを指定
        View emptyView = findViewById(R.id.empty);
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
                Intent intent = new Intent(AtndPlugIn.this, AtndEventInfo.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("EVENT", eventInfo);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void ViewToast() {
        Toast.makeText(this, "通信に失敗しました", Toast.LENGTH_LONG).show();
    }

}
