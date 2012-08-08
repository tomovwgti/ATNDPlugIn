
package com.tomovwgti.atnd.json;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

/**
 * USer情報
 * 
 * @author tomo
 */
@JsonModel
public class Users {
    static final String TAG = Users.class.getSimpleName();

    @JsonKey("twitter_id")
    public String twitterId;
    @JsonKey
    public String nickname;
    @JsonKey("twitter_img")
    public String twitterImg;

    public String getTwitterId() {
        return twitterId;
    }

    public void setTwitterId(String twitterId) {
        this.twitterId = twitterId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTwitterImg() {
        return twitterImg;
    }

    public void setTwitterImg(String twitterImg) {
        this.twitterImg = twitterImg;
    }

    // Twitterフラグ
    public boolean IsTwitter = true;
}
