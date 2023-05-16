/**
 * 
 */
package org.xujun.springboot.websoket.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * @author admin
 *
 */
public class ToJsonUtil {
	
	public static JSONObject buildJsonRs(boolean success, String msg, Object obj) {
		JSONObject json = new JSONObject();
		json.put("success", success ? "0" : "1");
		json.put("dataType", msg);
		json.put("datas", obj);
		return json;
	}

}
