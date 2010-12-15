package com.tomovwgti.atnd;

/**
 * 参加者のデータクラス
 */
public class AtndUserResult {

	public static final String TWITTER_ID = "Twitter ID";
	public static final String NICKNAME   = "ニックネーム";

	public String twitter_id;
	public String nickname;
	public String twitter_img;
	
	// Twitterフラグ
	public boolean IsTwitter = true;
}
