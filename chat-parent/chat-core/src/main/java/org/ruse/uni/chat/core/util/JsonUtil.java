package org.ruse.uni.chat.core.util;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil {

	public static String getString(String key, JSONObject json) {
		try {
			return json.getString(key);
		} catch (JSONException e) {
			return null;
		}
	}

}
