
package com.tomovwgti.atnd.lib;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tomovwgti.atnd.ImageDownloader;
import com.tomovwgti.atnd.R;

/**
 * アイコンとテキストを表示するためのアダプタ.
 */
public class UserArrayAdapter extends ArrayAdapter<ListViewItem> {

    /** XMLからViewを生成 */
    private LayoutInflater inflater;

    /** リストアイテムのレイアウト */
    private int textViewResourceId;

    /** 表示するアイテム */
    private List<ListViewItem> items;

    /** イメージダウンローダ */
    private final ImageDownloader imageDownloader = new ImageDownloader();

    /**
     * コンストラクタ.
     */
    public UserArrayAdapter(Context context, int textViewResourceId, List<ListViewItem> items) {
        super(context, textViewResourceId, items);

        // リソースIDと表示アイテムを保持っておく
        this.textViewResourceId = textViewResourceId;
        this.items = items;

        // ContextからLayoutInflaterを取得
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return items.size();
    }

    public ListViewItem getItem(int position) {
        return items.get(position);
    }

    public long getItemId(int position) {
        return items.get(position).hashCode();
    }

    /**
     * 1アイテム分のビューを取得.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        // convertViewなんか入ってたら、それを使う
        if (convertView != null) {
            view = convertView;
        }
        // convertViewがnullなら新規作成
        else {
            view = inflater.inflate(textViewResourceId, null);
        }

        // 対象のアイテムを取得
        ListViewItem item = items.get(position);

        // アイコンにアレを設定
        ImageView imageView = (ImageView) view.findViewWithTag("icon");
        if (item.url == null) {
            imageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.mogu));
        } else {
            imageDownloader.download(item.url, imageView);
        }

        // テキストにソレを設定
        TextView textView = (TextView) view.findViewWithTag("text");
        textView.setText(item.id);

        return view;
    }
}
