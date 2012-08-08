
package com.tomovwgti.atnd.json;

import java.util.List;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

/**
 * イベントのUserレスポンス
 * 
 * @author tomo
 */
@JsonModel
public class AtndUserResult {
    static final String TAG = AtndUserResult.class.getSimpleName();

    public static final String TWITTER_ID = "Twitter ID";
    public static final String NICKNAME = "ニックネーム";

    @JsonKey
    public List<Users> users;

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }
}
