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
 * JSONを {@link JsonPullParser} を用いて {@link AtndUserResult} に変換するクラスです.<br>
 * APTにより自動生成されています.
 */
public class AtndUserResultGen {

	/**
	 * JSONを {@link AtndUserResult} の {@link List} に変換します.
	 * 
	 * @param json JSONによる {@link AtndUserResult} の表現
	 * @return {@link AtndUserResult} の {@link List}
	 * @throws IOException 
	 * @throws JsonFormatException JSONとして正しくない形式、もしくは期待しない形式だった場合に投げられます
	 */
	public static List<AtndUserResult> getList(String json) throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser(json);
		return getList(parser, null);
	}

	/**
	 * JSONを {@link AtndUserResult} の {@link List} に変換します.<br>
	 * {@link OnJsonObjectAddListener} のサブクラスを渡すことにより、生成中に発生する各種インスタンスを逐次うけとることが可能です.
	 * 
	 * @param json JSONによる {@link AtndUserResult} の表現
	 * @param listener {@link AtndUserResult} 生成中に発生するインスタンスを逐次受け取る {@link OnJsonObjectAddListener} のサブクラス
	 * @return {@link AtndUserResult} の {@link List}
	 * @throws IOException 
	 * @throws JsonFormatException JSONとして正しくない形式、もしくは期待しない形式だった場合に投げられます
	 */
	public static List<AtndUserResult> getList(String json, OnJsonObjectAddListener listener) throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser(json);
		return getList(parser, listener);
	}

	/**
	 * JSONを {@link AtndUserResult} の {@link List} に変換します.
	 * 
	 * @param stream JSONによる {@link AtndUserResult} の表現
	 * @return {@link AtndUserResult} の {@link List}
	 * @throws IOException 
	 * @throws JsonFormatException JSONとして正しくない形式、もしくは期待しない形式だった場合に投げられます
	 */
	public static List<AtndUserResult> getList(InputStream stream) throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser(stream);
		return getList(parser, null);
	}

	/**
	 * JSONを {@link AtndUserResult} の {@link List} に変換します.<br>
	 * {@link OnJsonObjectAddListener} のサブクラスを渡すことにより、生成中に発生する各種インスタンスを逐次うけとることが可能です.
	 * 
	 * @param stream JSONによる {@link AtndUserResult} の表現
	 * @param listener {@link AtndUserResult} 生成中に発生するインスタンスを逐次受け取る {@link OnJsonObjectAddListener} のサブクラス
	 * @return {@link AtndUserResult} の {@link List}
	 * @throws IOException 
	 * @throws JsonFormatException JSONとして正しくない形式、もしくは期待しない形式だった場合に投げられます
	 */
	public static List<AtndUserResult> getList(InputStream stream, OnJsonObjectAddListener listener) throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser(stream);
		return getList(parser, listener);
	}

	/**
	 * JSONを {@link AtndUserResult} の {@link List} に変換します.
	 * 
	 * @param parser {@link AtndUserResult} の表現をセットされた {@link JsonPullParser}
	 * @return {@link AtndUserResult} の {@link List}
	 * @throws IOException 
	 * @throws JsonFormatException JSONとして正しくない形式、もしくは期待しない形式だった場合に投げられます
	 */
	public static List<AtndUserResult> getList(JsonPullParser parser) throws IOException, JsonFormatException {
		return getList(parser, null);
	}

	/**
	 * JSONを {@link AtndUserResult} の {@link List} に変換します.<br>
	 * {@link OnJsonObjectAddListener} のサブクラスを渡すことにより、生成中に発生する各種インスタンスを逐次うけとることが可能です.
	 * 
	 * @param parser {@link AtndUserResult} の表現をセットされた {@link JsonPullParser}
	 * @param listener {@link AtndUserResult} 生成中に発生するインスタンスを逐次受け取る {@link OnJsonObjectAddListener} のサブクラス
	 * @return {@link AtndUserResult} の {@link List}
	 * @throws IOException 
	 * @throws JsonFormatException JSONとして正しくない形式、期待しない形式だった場合に投げられます
	 */
	public static List<AtndUserResult> getList(JsonPullParser parser, OnJsonObjectAddListener listener) throws IOException, JsonFormatException {
		List<AtndUserResult> list = new ArrayList<AtndUserResult>();
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
			AtndUserResult tmp = get(parser, listener);
			list.add(tmp);
		}
		parser.getEventType();
		return list;
	}

	/**
	 * JSONを {@link AtndUserResult} に変換します.
	 * 
	 * @param json JSONによる {@link AtndUserResult} の表現
	 * @return {@link AtndUserResult}
	 * @throws IOException 
	 * @throws JsonFormatException JSONとして正しくない形式、もしくは期待しない形式だった場合に投げられます
	 */
	public static AtndUserResult get(String json) throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser(json);
		return get(parser, null);
	}

	/**
	 * JSONを {@link AtndUserResult} に変換します.<br>
	 * {@link OnJsonObjectAddListener} のサブクラスを渡すことにより、生成中に発生する各種インスタンスを逐次うけとることが可能です.
	 * 
	 * @param json JSONによる {@link AtndUserResult} の表現
	 * @param listener {@link AtndUserResult} 生成中に発生するインスタンスを逐次受け取る {@link OnJsonObjectAddListener} のサブクラス
	 * @return {@link AtndUserResult}
	 * @throws IOException 
	 * @throws JsonFormatException JSONとして正しくない形式、もしくは期待しない形式だった場合に投げられます
	 */
	public static AtndUserResult get(String json, OnJsonObjectAddListener listener) throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser(json);
		return get(parser, listener);
	}

	/**
	 * JSONを {@link AtndUserResult} に変換します.
	 * 
	 * @param stream JSONによる {@link AtndUserResult} の表現
	 * @return {@link AtndUserResult}
	 * @throws IOException 
	 * @throws JsonFormatException JSONとして正しくない形式、もしくは期待しない形式だった場合に投げられます
	 */
	public static AtndUserResult get(InputStream stream) throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser(stream);
		return get(parser, null);
	}

	/**
	 * JSONを {@link AtndUserResult} に変換します.<br>
	 * {@link OnJsonObjectAddListener} のサブクラスを渡すことにより、生成中に発生する各種インスタンスを逐次うけとることが可能です.
	 * 
	 * @param stream JSONによる {@link AtndUserResult} の表現
	 * @param listener {@link AtndUserResult} 生成中に発生するインスタンスを逐次受け取る {@link OnJsonObjectAddListener} のサブクラス
	 * @return {@link AtndUserResult}
	 * @throws IOException 
	 * @throws JsonFormatException JSONとして正しくない形式、もしくは期待しない形式だった場合に投げられます
	 */
	public static AtndUserResult get(InputStream stream, OnJsonObjectAddListener listener) throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser(stream);
		return get(parser, listener);
	}

	/**
	 * JSONを {@link AtndUserResult} に変換します.
	 * 
	 * @param parser {@link AtndUserResult} の表現をセットされた {@link JsonPullParser}
	 * @return {@link AtndUserResult}
	 * @throws IOException 
	 * @throws JsonFormatException JSONとして正しくない形式、もしくは期待しない形式だった場合に投げられます
	 */
	public static AtndUserResult get(JsonPullParser parser) throws IOException, JsonFormatException {
		return get(parser, null);
	}

	/**
	 * JSONを {@link AtndUserResult} に変換します.<br>
	 * {@link OnJsonObjectAddListener} のサブクラスを渡すことにより、生成中に発生する各種インスタンスを逐次うけとることが可能です.
	 * 
	 * @param parser {@link AtndUserResult} の表現をセットされた {@link JsonPullParser}
	 * @param listener {@link AtndUserResult} 生成中に発生するインスタンスを逐次受け取る {@link OnJsonObjectAddListener} のサブクラス
	 * @return {@link AtndUserResult}
	 * @throws IOException 
	 * @throws IllegalStateException @SaveOrigin ありにも関わらず {@link JsonPullParser#setLogEnable()} が呼ばれていない場合.
	 * @throws JsonFormatException JSONとして正しくない形式、もしくは期待しない形式だった場合に投げられます
	 */
	public static AtndUserResult get(JsonPullParser parser, OnJsonObjectAddListener listener) throws IOException, IllegalStateException, JsonFormatException {

		AtndUserResult obj = new AtndUserResult();
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
	 * @param obj 組み立て途中の {@link AtndUserResult} もしくはそのサブクラスのインスタンス
	 * @return keyの処理に成功したかどうか
	 * @throws IOException 
	 * @throws JsonFormatException JSONとして正しくない形式、もしくは期待しない形式だった場合に投げられます
	 * @author vvakame
	 */
	public static boolean parseValue(JsonPullParser parser, OnJsonObjectAddListener listener, String key, AtndUserResult obj) throws IOException, JsonFormatException {

		if ("users".equals(key)) {
	
		
			obj.setUsers(com.tomovwgti.atnd.json.UsersGen.getList(parser, listener));
		
	


		} else {
			return false;
		}
		return true;
	}

	/**
	 * {@link AtndUserResult} の {@link List} のJSON表現を生成します.<br>
	 * out は {@link JsonPullParser#DEFAULT_CHARSET} で処理されます.<br>
	 * このメソッドは{@link #encodeListNullToBlank(Writer, List)} へのaliasです.
	 * 
	 * @param out JSONを追記する {@link OutputStream}
	 * @param list JSON変換したい {@link AtndUserResult} の {@link List}
	 * @throws IOException 
	 */
	public static void encodeList(OutputStream out, List<? extends AtndUserResult> list) throws IOException {
		OutputStreamWriter writer = new OutputStreamWriter(out, JsonPullParser.DEFAULT_CHARSET);
		encodeListNullToBlank(writer, list);
	}

	/**
	 * {@link AtndUserResult} の {@link List} のJSON表現を生成します.<br>
	 * このメソッドは{@link #encodeListNullToBlank(Writer, List)} へのaliasです.
	 * 
	 * @param writer JSONを追記する {@link Writer}
	 * @param list JSON変換したい {@link AtndUserResult} の {@link List}
	 * @throws IOException 
	 */
	public static void encodeList(Writer writer, List<? extends AtndUserResult> list) throws IOException {
		encodeListNullToBlank(writer, list);
	}

	/**
	 * {@link AtndUserResult} の {@link List} のJSON表現を生成します.
	 * 
	 * @param writer JSONを追記する {@link Writer}
	 * @param list JSON変換したい {@link AtndUserResult} の {@link List}
	 * @throws IOException 
	 */
	public static void encodeListNullToBlank(Writer writer, List<? extends AtndUserResult> list) throws IOException {
		if (list == null) {
			writer.write("[]");
			writer.flush();
			return;
		}
		
		encodeListNullToNull(writer, list);
	}

	/**
	 * {@link AtndUserResult} の {@link List} のJSON表現を生成します.
	 * 
	 * @param writer JSONを追記する {@link Writer}
	 * @param list JSON変換したい {@link AtndUserResult} の {@link List}
	 * @throws IOException 
	 */
	public static void encodeListNullToNull(Writer writer, List<? extends AtndUserResult> list) throws IOException {
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
	 * {@link AtndUserResult} のJSON表現を生成します.<br>
	 * out は {@link JsonPullParser#DEFAULT_CHARSET} で処理されます.<br>
	 * このメソッドは{@link #encodeNullToBlank(Writer, AtndUserResult)} へのaliasです.
	 * 
	 * @param out JSONを追記する {@link OutputStream}
	 * @param obj JSON変換したい {@link AtndUserResult}
	 * @throws IOException 
	 */
	public static void encode(OutputStream out, AtndUserResult obj) throws IOException {
		OutputStreamWriter writer = new OutputStreamWriter(out, JsonPullParser.DEFAULT_CHARSET);
		encodeNullToBlank(writer, obj);
	}
	
	/**
	 * {@link AtndUserResult} のJSON表現を生成します.<br>
	 * このメソッドは{@link #encodeNullToBlank(Writer, AtndUserResult)} へのaliasです.
	 * 
	 * @param writer JSONを追記する {@link Writer}
	 * @param obj JSON変換したい {@link AtndUserResult}
	 * @throws IOException 
	 */
	public static void encode(Writer writer, AtndUserResult obj) throws IOException {
		encodeNullToBlank(writer, obj);
	}

	/**
	 * {@link AtndUserResult} のJSON表現を生成します.<br>
	 * もし、受け取った obj が null だった場合、{} を出力します.
	 * 
	 * @param writer JSONを追記する {@link Writer}
	 * @param obj JSON変換したい {@link AtndUserResult}
	 * @throws IOException 
	 */
	public static void encodeNullToBlank(Writer writer, AtndUserResult obj) throws IOException {
		if (obj == null) {
			writer.write("{}");
			writer.flush();
			return;
		}

		encodeNullToNull(writer, obj);
	}
	
	/**
	 * {@link AtndUserResult} のJSON表現を生成します.<br>
	 * もし、受け取った obj が null だった場合、{@code "null"} を出力します.
	 * 
	 * @param writer JSONを追記する {@link Writer}
	 * @param obj JSON変換したい {@link AtndUserResult}
	 * @throws IOException 
	 */
	public static void encodeNullToNull(Writer writer, AtndUserResult obj) throws IOException {
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
	public static void encodeValue(Writer writer, AtndUserResult obj) throws IOException {


		JsonUtil.putKey(writer, "users");
	
		
		com.tomovwgti.atnd.json.UsersGen.encodeListNullToNull(writer, obj.getUsers());
		
		
	

	}
}
