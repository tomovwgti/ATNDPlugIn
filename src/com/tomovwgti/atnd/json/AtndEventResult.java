
package com.tomovwgti.atnd.json;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * ATNDのイベントを持つクラス
 */
@JsonModel
public class AtndEventResult implements Parcelable {
    static final String TAG = AtndEventResult.class.getSimpleName();

    public static final String TITLE = "タイトル";
    public static final String DESC = "ブラウザで見る";
    public static final String OWNER = "主催者";
    public static final String ADDRESS = "場所";
    public static final String PLACE = "会場";
    public static final String START = "開始時間";
    public static final String END = "終了時間";
    public static final String MAP = "地図";

    @JsonKey("event_id")
    public int eventId;
    @JsonKey
    public String title;
    @JsonKey
    public String description;
    @JsonKey("owner_twitter_id")
    public String owner;
    @JsonKey("owner_nickname")
    public String ownerNickname;
    @JsonKey("owner_twitter_img")
    public String icon;
    @JsonKey("started_at")
    public String start;
    @JsonKey("ended_at")
    public String end;
    @JsonKey
    public String address;
    @JsonKey
    public String place;
    @JsonKey
    public double lat;
    @JsonKey
    public double lon;

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwnerNickname() {
        return ownerNickname;
    }

    public void setOwnerNickname(String ownerNickname) {
        this.ownerNickname = ownerNickname;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

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
        out.writeDouble(lat);
        out.writeDouble(lon);
        out.writeInt(eventId);
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
        lat = in.readDouble();
        lon = in.readDouble();
        eventId = in.readInt();
    }

    public void LogPrint() {
        Log.i(TAG, "title: " + title);
        Log.i(TAG, "description: " + description);
        Log.i(TAG, "owner: " + owner);
        Log.i(TAG, "start: " + start);
        Log.i(TAG, "end: " + end);
        Log.i(TAG, "address: " + address);
        Log.i(TAG, "place: " + place);
        Log.i(TAG, "lat: " + lat);
        Log.i(TAG, "lon: " + lon);
        Log.i(TAG, "eventId: " + eventId);
    }
}
