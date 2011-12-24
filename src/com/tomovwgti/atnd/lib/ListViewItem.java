
package com.tomovwgti.atnd.lib;

/**
 * アイコンとテキストを表示するためのリストアイテム.
 */
public class ListViewItem {

    /** ImageのURL */
    public String url;
    /** 表示するテキスト. */
    public String id;

    public ListViewItem(String text, String url) {
        this.id = text;
        this.url = url;
    }
}
