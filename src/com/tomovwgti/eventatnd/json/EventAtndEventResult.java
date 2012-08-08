
package com.tomovwgti.eventatnd.json;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;
import android.os.Parcel;
import android.os.Parcelable;

@JsonModel
public class EventAtndEventResult implements Parcelable {
    static final String TAG = EventAtndEventResult.class.getSimpleName();

    public static final String TITLE = "タイトル";
    public static final String DESC = "ブラウザで見る";
    public static final String OWNER = "主催者";
    public static final String ADDRESS = "場所";
    public static final String PLACE = "会場";
    public static final String START = "開始時間";
    public static final String END = "終了時間";
    public static final String MAP = "地図";

    @JsonKey("event_id")
    public String eventId;
    @JsonKey
    public String title;
    public String description; // 未使用
    @JsonKey("event_url")
    public String url;
    @JsonKey("started_at")
    public String start;
    @JsonKey("ended_at")
    public String end;
    @JsonKey
    public String address;
    @JsonKey
    public String place;
    @JsonKey
    public String lat;
    @JsonKey
    public String lon;
    @JsonKey("owner_nickname")
    public String ownerNickname;
    @JsonKey("owner_twitter_id")
    public String owner;
    @JsonKey("owner_twitter_img")
    public String icon;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getOwnerNickname() {
        return ownerNickname;
    }

    public void setOwnerNickname(String ownerNickname) {
        this.ownerNickname = ownerNickname;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    // オーナーがTwitterIDならtrue;
    public boolean IsOwner = true;

    public EventAtndEventResult() {
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
        out.writeString(owner);
        out.writeString(icon);
        out.writeString(start);
        out.writeString(end);
        out.writeString(address);
        out.writeString(place);
        out.writeString(lat);
        out.writeString(lon);
        out.writeString(eventId);
        out.writeString(url);
    }

    public static final Parcelable.Creator<EventAtndEventResult> CREATOR = new Parcelable.Creator<EventAtndEventResult>() {
        public EventAtndEventResult createFromParcel(Parcel in) {
            return new EventAtndEventResult(in);
        }

        public EventAtndEventResult[] newArray(int size) {
            return new EventAtndEventResult[size];
        }
    };

    private EventAtndEventResult(Parcel in) {
        title = in.readString();
        owner = in.readString();
        icon = in.readString();
        start = in.readString();
        end = in.readString();
        address = in.readString();
        place = in.readString();
        lat = in.readString();
        lon = in.readString();
        eventId = in.readString();
        url = in.readString();
    }
}
