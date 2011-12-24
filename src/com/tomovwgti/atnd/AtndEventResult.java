
package com.tomovwgti.atnd;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ATNDのイベントを持つクラス
 */
public class AtndEventResult implements Parcelable {

    public static final String TITLE = "タイトル";
    public static final String DESC = "概要";
    public static final String OWNER = "主催者";
    public static final String ADDRESS = "場所";
    public static final String PLACE = "会場";
    public static final String START = "開始時間";
    public static final String END = "終了時間";
    public static final String MAP = "地図";

    public String event_id;
    public String title;
    public String description;
    public String owner;
    public String icon;
    public String start;
    public String end;
    public String address;
    public String place;
    public String lat;
    public String lon;

    // オーナーがTwitterIDならtrue;
    public boolean IsOwner = true;

    public AtndEventResult() {
    }

    public String getItem(int id) {
        String item = null;
        switch (id) {
            case 0:
                item = title;
                break;
            case 1:
                item = description;
                break;
            case 2:
                item = owner;
                break;
            case 3:
                item = address;
                break;
            case 4:
                item = place;
                break;
            case 5:
                item = start;
                break;
            case 6:
                item = end;
                break;
            case 7:
                item = "geo:" + lat + "," + lon;
                break;
        }
        return item;
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(title);
        out.writeString(description);
        out.writeString(owner);
        out.writeString(icon);
        out.writeString(start);
        out.writeString(end);
        out.writeString(address);
        out.writeString(place);
        out.writeString(lat);
        out.writeString(lon);
        out.writeString(event_id);
    }

    public static final Parcelable.Creator<AtndEventResult> CREATOR = new Parcelable.Creator<AtndEventResult>() {
        public AtndEventResult createFromParcel(Parcel in) {
            return new AtndEventResult(in);
        }

        public AtndEventResult[] newArray(int size) {
            return new AtndEventResult[size];
        }
    };

    private AtndEventResult(Parcel in) {
        title = in.readString();
        description = in.readString();
        owner = in.readString();
        icon = in.readString();
        start = in.readString();
        end = in.readString();
        address = in.readString();
        place = in.readString();
        lat = in.readString();
        lon = in.readString();
        event_id = in.readString();
    }
}
