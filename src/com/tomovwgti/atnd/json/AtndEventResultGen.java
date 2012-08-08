package com.tomovwgti.atnd.json;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.JsonPullParser.State;
import net.vvakame.util.jsonpullparser.util.JsonUtil;
import net.vvakame.util.jsonpullparser.util.OnJsonObjectAddListener;


/**
 * JSONを {@link JsonPullParser} を用いて {@link AtndEventResult} に変換するクラスです.<br>
 * APTにより自動生成されています.
 */
public class AtndEventResultGen {

	/**
	 * JSONを {@link AtndEventResult} の {@link List} に変換します.
	 * 
	 * @param json JSONによる {@link AtndEventResult} の表現
	 * @return {@link AtndEventResult} の {@link List}
	 * @throws IOException 
	 * @throws JsonFormatException JSONとして正しくない形式、もしくは期待しない形式だった場合に投げられます
	 */
	public static List<AtndEventResult> getList(String json) throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser(json);
		return getList(parser, null);
	}

	/**
	 * JSONを {@link AtndEventResult} の {@link List} に変換します.<br>
	 * {@link OnJsonObjectAddListener} のサブクラスを渡すことにより、生成中に発生する各種インスタンスを逐次うけとることが可能です.
	 * 
	 * @param json JSONによる {@link AtndEventResult} の表現
	 * @param listener {@link AtndEventResult} 生成中に発生するインスタンスを逐次受け取る {@link OnJsonObjectAddListener} のサブクラス
	 * @return {@link AtndEventResult} の {@link List}
	 * @throws IOException 
	 * @throws JsonFormatException JSONとして正しくない形式、もしくは期待しない形式だった場合に投げられます
	 */
	public static List<AtndEventResult> getList(String json, OnJsonObjectAddListener listener) throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser(json);
		return getList(parser, listener);
	}

	/**
	 * JSONを {@link AtndEventResult} の {@link List} に変換します.
	 * 
	 * @param stream JSONによる {@link AtndEventResult} の表現
	 * @return {@link AtndEventResult} の {@link List}
	 * @throws IOException 
	 * @throws JsonFormatException JSONとして正しくない形式、もしくは期待しない形式だった場合に投げられます
	 */
	public static List<AtndEventResult> getList(InputStream stream) throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser(stream);
		return getList(parser, null);
	}

	/**
	 * JSONを {@link AtndEventResult} の {@link List} に変換します.<br>
	 * {@link OnJsonObjectAddListener} のサブクラスを渡すことにより、生成中に発生する各種インスタンスを逐次うけとることが可能です.
	 * 
	 * @param stream JSONによる {@link AtndEventResult} の表現
	 * @param listener {@link AtndEventResult} 生成中に発生するインスタンスを逐次受け取る {@link OnJsonObjectAddListener} のサブクラス
	 * @return {@link AtndEventResult} の {@link List}
	 * @throws IOException 
	 * @throws JsonFormatException JSONとして正しくない形式、もしくは期待しない形式だった場合に投げられます
	 */
	public static List<AtndEventResult> getList(InputStream stream, OnJsonObjectAddListener listener) throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser(stream);
		return getList(parser, listener);
	}

	/**
	 * JSONを {@link AtndEventResult} の {@link List} に変換します.
	 * 
	 * @param parser {@link AtndEventResult} の表現をセットされた {@link JsonPullParser}
	 * @return {@link AtndEventResult} の {@link List}
	 * @throws IOException 
	 * @throws JsonFormatException JSONとして正しくない形式、もしくは期待しない形式だった場合に投げられます
	 */
	public static List<AtndEventResult> getList(JsonPullParser parser) throws IOException, JsonFormatException {
		return getList(parser, null);
	}

	/**
	 * JSONを {@link AtndEventResult} の {@link List} に変換します.<br>
	 * {@link OnJsonObjectAddListener} のサブクラスを渡すことにより、生成中に発生する各種インスタンスを逐次うけとることが可能です.
	 * 
	 * @param parser {@link AtndEventResult} の表現をセットされた {@link JsonPullParser}
	 * @param listener {@link AtndEventResult} 生成中に発生するインスタンスを逐次受け取る {@link OnJsonObjectAddListener} のサブクラス
	 * @return {@link AtndEventResult} の {@link List}
	 * @throws IOException 
	 * @throws JsonFormatException JSONとして正しくない形式、期待しない形式だった場合に投げられます
	 */
	public static List<AtndEventResult> getList(JsonPullParser parser, OnJsonObjectAddListener listener) throws IOException, JsonFormatException {
		List<AtndEventResult> list = new ArrayList<AtndEventResult>();
		State eventType = parser.getEventType();
		if (eventType == State.VALUE_NULL) {
			if (listener != null) {
				listener.onAdd(null);
			}
			return null;
		}
		if (eventType != State.START_ARRAY) {
			if(eventType == State.START_HASH){
				throw new JsonFormatException("not started '('!, Do you want the json hash?");
			} else {
				throw new JsonFormatException("not started '('!");
			}
		}
		while (parser.lookAhead() != State.END_ARRAY) {
			AtndEventResult tmp = get(parser, listener);
			list.add(tmp);
		}
		parser.getEventType();
		return list;
	}

	/**
	 * JSONを {@link AtndEventResult} に変換します.
	 * 
	 * @param json JSONによる {@link AtndEventResult} の表現
	 * @return {@link AtndEventResult}
	 * @throws IOException 
	 * @throws JsonFormatException JSONとして正しくない形式、もしくは期待しない形式だった場合に投げられます
	 */
	public static AtndEventResult get(String json) throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser(json);
		return get(parser, null);
	}

	/**
	 * JSONを {@link AtndEventResult} に変換します.<br>
	 * {@link OnJsonObjectAddListener} のサブクラスを渡すことにより、生成中に発生する各種インスタンスを逐次うけとることが可能です.
	 * 
	 * @param json JSONによる {@link AtndEventResult} の表現
	 * @param listener {@link AtndEventResult} 生成中に発生するインスタンスを逐次受け取る {@link OnJsonObjectAddListener} のサブクラス
	 * @return {@link AtndEventResult}
	 * @throws IOException 
	 * @throws JsonFormatException JSONとして正しくない形式、もしくは期待しない形式だった場合に投げられます
	 */
	public static AtndEventResult get(String json, OnJsonObjectAddListener listener) throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser(json);
		return get(parser, listener);
	}

	/**
	 * JSONを {@link AtndEventResult} に変換します.
	 * 
	 * @param stream JSONによる {@link AtndEventResult} の表現
	 * @return {@link AtndEventResult}
	 * @throws IOException 
	 * @throws JsonFormatException JSONとして正しくない形式、もしくは期待しない形式だった場合に投げられます
	 */
	public static AtndEventResult get(InputStream stream) throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser(stream);
		return get(parser, null);
	}

	/**
	 * JSONを {@link AtndEventResult} に変換します.<br>
	 * {@link OnJsonObjectAddListener} のサブクラスを渡すことにより、生成中に発生する各種インスタンスを逐次うけとることが可能です.
	 * 
	 * @param stream JSONによる {@link AtndEventResult} の表現
	 * @param listener {@link AtndEventResult} 生成中に発生するインスタンスを逐次受け取る {@link OnJsonObjectAddListener} のサブクラス
	 * @return {@link AtndEventResult}
	 * @throws IOException 
	 * @throws JsonFormatException JSONとして正しくない形式、もしくは期待しない形式だった場合に投げられます
	 */
	public static AtndEventResult get(InputStream stream, OnJsonObjectAddListener listener) throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser(stream);
		return get(parser, listener);
	}

	/**
	 * JSONを {@link AtndEventResult} に変換します.
	 * 
	 * @param parser {@link AtndEventResult} の表現をセットされた {@link JsonPullParser}
	 * @return {@link AtndEventResult}
	 * @throws IOException 
	 * @throws JsonFormatException JSONとして正しくない形式、もしくは期待しない形式だった場合に投げられます
	 */
	public static AtndEventResult get(JsonPullParser parser) throws IOException, JsonFormatException {
		return get(parser, null);
	}

	/**
	 * JSONを {@link AtndEventResult} に変換します.<br>
	 * {@link OnJsonObjectAddListener} のサブクラスを渡すことにより、生成中に発生する各種インスタンスを逐次うけとることが可能です.
	 * 
	 * @param parser {@link AtndEventResult} の表現をセットされた {@link JsonPullParser}
	 * @param listener {@link AtndEventResult} 生成中に発生するインスタンスを逐次受け取る {@link OnJsonObjectAddListener} のサブクラス
	 * @return {@link AtndEventResult}
	 * @throws IOException 
	 * @throws IllegalStateException @SaveOrigin ありにも関わらず {@link JsonPullParser#setLogEnable()} が呼ばれていない場合.
	 * @throws JsonFormatException JSONとして正しくない形式、もしくは期待しない形式だった場合に投げられます
	 */
	public static AtndEventResult get(JsonPullParser parser, OnJsonObjectAddListener listener) throws IOException, IllegalStateException, JsonFormatException {

		AtndEventResult obj = new AtndEventResult();
		State eventType = parser.getEventType();
		if (eventType == State.VALUE_NULL) {
			if (listener != null) {
				listener.onAdd(null);
			}
			return null;
		}
		if (eventType != State.START_HASH) {
			if (eventType == State.START_ARRAY) {
				throw new JsonFormatException("not started '{'! Do you want the json array?");
			} else {
				throw new JsonFormatException("not started '{'!");
			}
		}
		while ((eventType = parser.getEventType()) != State.END_HASH) {
			if (eventType != State.KEY) {
				throw new JsonFormatException("expect KEY. we got unexpected value. " + eventType);
			}
			String key = parser.getValueString();
			
			if(parseValue(parser, listener, key, obj)){
				continue;

			} else {
				parser.discardValue();
			}

		}



		if (listener != null) {
			listener.onAdd(obj);
		}

		return obj;
	}

	/**
	 * ※ このメソッドを呼び出さないでください ※.<br>
	 * 生成クラスの内部から呼び出すためのメソッドです.他パッケージにある生成クラスからアクセス出来るようにするため、publicになっています.
	 * @param parser 利用途中の {@link JsonPullParser}
	 * @param listener インスタンスが生成した場合に通知する {@link OnJsonObjectAddListener}
	 * @param key 処理途中のJSONのkey
	 * @param obj 組み立て途中の {@link AtndEventResult} もしくはそのサブクラスのインスタンス
	 * @return keyの処理に成功したかどうか
	 * @throws IOException 
	 * @throws JsonFormatException JSONとして正しくない形式、もしくは期待しない形式だった場合に投げられます
	 * @author vvakame
	 */
	public static boolean parseValue(JsonPullParser parser, OnJsonObjectAddListener listener, String key, AtndEventResult obj) throws IOException, JsonFormatException {

		if ("address".equals(key)) {
	
			parser.getEventType();
			obj.setAddress(parser.getValueString());
	
		} else 
		if ("description".equals(key)) {
	
			parser.getEventType();
			obj.setDescription(parser.getValueString());
	
		} else 
		if ("ended_at".equals(key)) {
	
			parser.getEventType();
			obj.setEnd(parser.getValueString());
	
		} else 
		if ("event_id".equals(key)) {
	
			parser.getEventType();
			obj.setEventId((int)parser.getValueLong());
	
		} else 
		if ("owner_twitter_img".equals(key)) {
	
			parser.getEventType();
			obj.setIcon(parser.getValueString());
	
		} else 
		if ("lat".equals(key)) {
	
			parser.getEventType();
			obj.setLat(parser.getValueDouble());
	
		} else 
		if ("lon".equals(key)) {
	
			parser.getEventType();
			obj.setLon(parser.getValueDouble());
	
		} else 
		if ("owner_twitter_id".equals(key)) {
	
			parser.getEventType();
			obj.setOwner(parser.getValueString());
	
		} else 
		if ("owner_nickname".equals(key)) {
	
			parser.getEventType();
			obj.setOwnerNickname(parser.getValueString());
	
		} else 
		if ("place".equals(key)) {
	
			parser.getEventType();
			obj.setPlace(parser.getValueString());
	
		} else 
		if ("started_at".equals(key)) {
	
			parser.getEventType();
			obj.setStart(parser.getValueString());
	
		} else 
		if ("title".equals(key)) {
	
			parser.getEventType();
			obj.setTitle(parser.getValueString());
	


		} else {
			return false;
		}
		return true;
	}

	/**
	 * {@link AtndEventResult} の {@link List} のJSON表現を生成します.<br>
	 * out は {@link JsonPullParser#DEFAULT_CHARSET} で処理されます.<br>
	 * このメソッドは{@link #encodeListNullToBlank(Writer, List)} へのaliasです.
	 * 
	 * @param out JSONを追記する {@link OutputStream}
	 * @param list JSON変換したい {@link AtndEventResult} の {@link List}
	 * @throws IOException 
	 */
	public static void encodeList(OutputStream out, List<? extends AtndEventResult> list) throws IOException {
		OutputStreamWriter writer = new OutputStreamWriter(out, JsonPullParser.DEFAULT_CHARSET);
		encodeListNullToBlank(writer, list);
	}

	/**
	 * {@link AtndEventResult} の {@link List} のJSON表現を生成します.<br>
	 * このメソッドは{@link #encodeListNullToBlank(Writer, List)} へのaliasです.
	 * 
	 * @param writer JSONを追記する {@link Writer}
	 * @param list JSON変換したい {@link AtndEventResult} の {@link List}
	 * @throws IOException 
	 */
	public static void encodeList(Writer writer, List<? extends AtndEventResult> list) throws IOException {
		encodeListNullToBlank(writer, list);
	}

	/**
	 * {@link AtndEventResult} の {@link List} のJSON表現を生成します.
	 * 
	 * @param writer JSONを追記する {@link Writer}
	 * @param list JSON変換したい {@link AtndEventResult} の {@link List}
	 * @throws IOException 
	 */
	public static void encodeListNullToBlank(Writer writer, List<? extends AtndEventResult> list) throws IOException {
		if (list == null) {
			writer.write("[]");
			writer.flush();
			return;
		}
		
		encodeListNullToNull(writer, list);
	}

	/**
	 * {@link AtndEventResult} の {@link List} のJSON表現を生成します.
	 * 
	 * @param writer JSONを追記する {@link Writer}
	 * @param list JSON変換したい {@link AtndEventResult} の {@link List}
	 * @throws IOException 
	 */
	public static void encodeListNullToNull(Writer writer, List<? extends AtndEventResult> list) throws IOException {
		if (list == null) {
			writer.write("null");
			writer.flush();
			return;
		}
		JsonUtil.startArray(writer);

		int size = list.size();
		for (int i = 0; i < size; i++) {

			encodeNullToNull(writer, list.get(i));

			if (i + 1 < size) {
				JsonUtil.addSeparator(writer);
			}
		}

		JsonUtil.endArray(writer);

		writer.flush();
	}

	/**
	 * {@link AtndEventResult} のJSON表現を生成します.<br>
	 * out は {@link JsonPullParser#DEFAULT_CHARSET} で処理されます.<br>
	 * このメソッドは{@link #encodeNullToBlank(Writer, AtndEventResult)} へのaliasです.
	 * 
	 * @param out JSONを追記する {@link OutputStream}
	 * @param obj JSON変換したい {@link AtndEventResult}
	 * @throws IOException 
	 */
	public static void encode(OutputStream out, AtndEventResult obj) throws IOException {
		OutputStreamWriter writer = new OutputStreamWriter(out, JsonPullParser.DEFAULT_CHARSET);
		encodeNullToBlank(writer, obj);
	}
	
	/**
	 * {@link AtndEventResult} のJSON表現を生成します.<br>
	 * このメソッドは{@link #encodeNullToBlank(Writer, AtndEventResult)} へのaliasです.
	 * 
	 * @param writer JSONを追記する {@link Writer}
	 * @param obj JSON変換したい {@link AtndEventResult}
	 * @throws IOException 
	 */
	public static void encode(Writer writer, AtndEventResult obj) throws IOException {
		encodeNullToBlank(writer, obj);
	}

	/**
	 * {@link AtndEventResult} のJSON表現を生成します.<br>
	 * もし、受け取った obj が null だった場合、{} を出力します.
	 * 
	 * @param writer JSONを追記する {@link Writer}
	 * @param obj JSON変換したい {@link AtndEventResult}
	 * @throws IOException 
	 */
	public static void encodeNullToBlank(Writer writer, AtndEventResult obj) throws IOException {
		if (obj == null) {
			writer.write("{}");
			writer.flush();
			return;
		}

		encodeNullToNull(writer, obj);
	}
	
	/**
	 * {@link AtndEventResult} のJSON表現を生成します.<br>
	 * もし、受け取った obj が null だった場合、{@code "null"} を出力します.
	 * 
	 * @param writer JSONを追記する {@link Writer}
	 * @param obj JSON変換したい {@link AtndEventResult}
	 * @throws IOException 
	 */
	public static void encodeNullToNull(Writer writer, AtndEventResult obj) throws IOException {
		if (obj == null) {
			writer.write("null");
			return;
		}

		JsonUtil.startHash(writer);

		encodeValue(writer, obj);

		JsonUtil.endHash(writer);
		
		writer.flush();
	}

	/**
	 * ※ このメソッドを呼び出さないでください ※.<br>
	 * 生成クラスの内部から呼び出すためのメソッドです.他パッケージにある生成クラスからアクセス出来るようにするため、publicになっています.
	 * @param writer 出力先
	 * @param obj データ元
	 * @throws IOException 
	 * @author vvakame
	 */
	public static void encodeValue(Writer writer, AtndEventResult obj) throws IOException {


		JsonUtil.putKey(writer, "address");
	
		JsonUtil.put(writer, obj.getAddress());
	
		JsonUtil.addSeparator(writer);
		JsonUtil.putKey(writer, "description");
	
		JsonUtil.put(writer, obj.getDescription());
	
		JsonUtil.addSeparator(writer);
		JsonUtil.putKey(writer, "ended_at");
	
		JsonUtil.put(writer, obj.getEnd());
	
		JsonUtil.addSeparator(writer);
		JsonUtil.putKey(writer, "event_id");
	
		JsonUtil.put(writer, obj.getEventId());
	
		JsonUtil.addSeparator(writer);
		JsonUtil.putKey(writer, "owner_twitter_img");
	
		JsonUtil.put(writer, obj.getIcon());
	
		JsonUtil.addSeparator(writer);
		JsonUtil.putKey(writer, "lat");
	
		JsonUtil.put(writer, obj.getLat());
	
		JsonUtil.addSeparator(writer);
		JsonUtil.putKey(writer, "lon");
	
		JsonUtil.put(writer, obj.getLon());
	
		JsonUtil.addSeparator(writer);
		JsonUtil.putKey(writer, "owner_twitter_id");
	
		JsonUtil.put(writer, obj.getOwner());
	
		JsonUtil.addSeparator(writer);
		JsonUtil.putKey(writer, "owner_nickname");
	
		JsonUtil.put(writer, obj.getOwnerNickname());
	
		JsonUtil.addSeparator(writer);
		JsonUtil.putKey(writer, "place");
	
		JsonUtil.put(writer, obj.getPlace());
	
		JsonUtil.addSeparator(writer);
		JsonUtil.putKey(writer, "started_at");
	
		JsonUtil.put(writer, obj.getStart());
	
		JsonUtil.addSeparator(writer);
		JsonUtil.putKey(writer, "title");
	
		JsonUtil.put(writer, obj.getTitle());
	

	}
}
