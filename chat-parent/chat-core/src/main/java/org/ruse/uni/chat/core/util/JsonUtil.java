package org.ruse.uni.chat.core.util;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil {

	public static String getString(String key, JSONObject json) {
		try {
			return json.getString(key);
		} catch (JSONException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static boolean getBoolean(String key, JSONObject json) {
		try {
			return json.getBoolean(key);
		} catch (JSONException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

}
