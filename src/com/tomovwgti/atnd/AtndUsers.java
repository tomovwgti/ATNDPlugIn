package com.tomovwgti.atnd;

import com.tomovwgti.atnd.lib.ConnectionStatus;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * イベントの参加者一覧を表示する
 */
public class AtndUsers extends Activity {

	private AtndUserTask users;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userinfo);

		// イベントIDを取得
        final String eventId = getIntent().getStringExtra("EVENTID");
        // イベントの名を取得
        final String event = getIntent().getStringExtra("EVENT");

        TextView eventTitle2 = (TextView)findViewById(R.id.eventtitle2);
		eventTitle2.setText(event);
		eventTitle2.setTextColor(Color.GREEN);
		eventTitle2.setBackgroundColor(Color.DKGRAY);
        // 詳細ページへジャンプ
        eventTitle2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://atnd.org/events/" + eventId));
				startActivity(intent);
			}
        });

        // ネットワーク接続の確認
		if ( !ConnectionStatus.isConnected(this) ) {
			Toast.makeText(this, "ネットワークに接続できません", Toast.LENGTH_LONG).show();
			return;
		}

        // イベント参加者の取得
        users = new AtndUserTask(this);        
        users.execute(eventId);
	}
}
