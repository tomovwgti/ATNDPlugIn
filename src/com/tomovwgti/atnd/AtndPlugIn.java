
package com.tomovwgti.atnd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.tomovwgti.atnd.lib.ConnectionStatus;

/**
 * ATNDプラグインのメインクラス twiccaから呼び出される
 */
public class AtndPlugIn extends Activity {

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
        AtndEventTask event = new AtndEventTask(this);
        event.execute(name);
    }
}
