
package com.tomovwgti.eventatnd;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.tomovwgti.atnd.ImageDownloader;
import com.tomovwgti.atnd.PointMapView;
import com.tomovwgti.atnd.R;
import com.tomovwgti.atnd.json.AtndEventResult;
import com.tomovwgti.atnd.lib.Iso8601;
import com.tomovwgti.eventatnd.json.EventAtndEventResult;

/**
 * event ATNDのイベント取得
 * 
 * @author tomo
 */
public class EventAtndEventInfo extends Activity {
    static final String TAG = EventAtndEventInfo.class.getSimpleName();

    private EventAtndEventResult event = null;
    private TextView eventTitle;
    private ViewGroup viewOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventatndinfo);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        event = bundle.getParcelable("EVENT");

        eventTitle = (TextView) findViewById(R.id.eventatndtitle);
        eventTitle.setText(event.title);
        eventTitle.setTextColor(Color.GREEN);
        eventTitle.setBackgroundColor(Color.DKGRAY);

        final AlertDialog.Builder builder = new AlertDialog.Builder(EventAtndEventInfo.this);

        ListView listView = (ListView) findViewById(R.id.eventatndinfo);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, eventInfolist);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String item = event.getItem(position + 1);
                switch (position) {
                    case 0: // 概要
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(event.url));
                        startActivity(intent);
                        return;
                    case 1: // 主催者
                        ownerInfo(item);
                        v = viewOwner;
                        break;
                    case 2: // 場所
                        v = addressInfo(item);
                        break;
                    case 3: // 会場
                        v = venueInfo(item);
                        break;
                    case 4: // 開始時間
                    case 5: // 終了時間
                        v = daytimeInfo(item);
                        break;
                    case 6: // 地図
                        mapInfo(item);
                        return;
                }
                builder.setView(v).create().show();
            }
        });
    }

    /**
     * 主催者
     * 
     * @param item
     * @return
     */
    private void ownerInfo(String item) {
        // 主催者のみ特別に呼ばれたときに生成しておく
        // 主催者のTwitterアイコンを読み込んでおく
        final ImageDownloader imageDownloader = new ImageDownloader();
        // ContextからLayoutInflaterを取得
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewOwner = (ViewGroup) inflater.inflate(R.layout.listview_item, null);
        // アイコンにTwitter imgを設定
        final ImageView imageView = (ImageView) viewOwner.findViewWithTag("icon");
        // 主催者イメージをダウンロード
        if (event.icon == null) {
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.mogu));
        } else {
            imageDownloader.download(event.icon, imageView);
        }
        // テキストにTwitter idを設定
        TextView textView = (TextView) viewOwner.findViewWithTag("text");
        textView.setText(event.getOwner());
        if (event.IsOwner) {
            textView = viewId(textView);
        }
    }

    /**
     * 場所
     * 
     * @param item
     * @return
     */
    private View addressInfo(String item) {
        TextView tv = new TextView(EventAtndEventInfo.this);
        tv.setText(item);
        tv.setTextSize(20);
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        ScrollView sv = new ScrollView(EventAtndEventInfo.this);
        sv.addView(tv);
        searchWeb(tv, item);
        return sv;
    }

    /**
     * 会場
     * 
     * @param item
     * @return
     */
    private View venueInfo(String item) {
        TextView tv = new TextView(EventAtndEventInfo.this);
        tv.setText(item);
        tv.setTextSize(20);
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        ScrollView sv = new ScrollView(EventAtndEventInfo.this);
        sv.addView(tv);
        searchWeb(tv, item);
        return sv;
    }

    /**
     * 開始時間・終了時間
     * 
     * @param item
     * @return
     */
    private View daytimeInfo(String item) {
        TextView tv = new TextView(EventAtndEventInfo.this);
        String daytime = Iso8601.getString(item);
        tv.setText(daytime);
        tv.setTextSize(24);
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        ScrollView sv = new ScrollView(EventAtndEventInfo.this);
        sv.addView(tv);
        return sv;
    }

    /**
     * 地図表示
     * 
     * @param item
     */
    private void mapInfo(String item) {
        final String maps = item;
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("地図を表示");
        // 表示項目の配列
        final CharSequence[] which = {
                "ピンポイント地図", "Google Maps"
        };
        dialog.setItems(which, new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent mapIntent = null;
                switch (which) {
                    case 0:
                        // ピンポイント地図
                        mapIntent = new Intent();
                        mapIntent.setClass(EventAtndEventInfo.this, PointMapView.class);
                        mapIntent.putExtra("LATLON", maps);
                        mapIntent.putExtra("PLACE", event.getItem(4)); // 会場の名称
                        break;
                    case 1:
                        // Google Maps
                        mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(maps + "?z=18"));
                        break;
                }
                startActivity(mapIntent);
            }
        });

        // ダイアログを表示
        dialog.create().show();
    }

    private TextView viewId(TextView tv) {
        // LinkMovementMethod のインスタンスを取得します
        MovementMethod movementmethod = LinkMovementMethod.getInstance();
        // TextView に LinkMovementMethod を登録します
        tv.setMovementMethod(movementmethod);
        // <a>タグを含めたテキストを用意します
        String html = "<a href=\"http://twitter.com/" + tv.getText().toString() + "\">"
                + tv.getText().toString() + "</a>";
        // URLSpan をテキストにを組み込みます
        CharSequence spanned = Html.fromHtml(html);
        tv.setText(spanned);
        return tv;
    }

    /**
     * Webでサーチする
     * 
     * @param v
     * @param search
     */
    private void searchWeb(View v, final String search) {
        v.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(EventAtndEventInfo.this);
                alert.setMessage("Webで\"" + search + "\"を検索します");
                // Positiveボタンとリスナを設定
                alert.setPositiveButton("OK",
                        new android.content.DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri
                                        .parse("http://www.google.co.jp/search?q=" + search));
                                startActivity(intent);
                            }
                        });
                alert.setNegativeButton("CANCEL",
                        new android.content.DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alert.show();
            }
        });
    }

    private static final String eventInfolist[] = {
            AtndEventResult.DESC, AtndEventResult.OWNER, AtndEventResult.ADDRESS,
            AtndEventResult.PLACE, AtndEventResult.START, AtndEventResult.END, AtndEventResult.MAP
    };
}
