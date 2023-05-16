/**
 * 
 */
package org.xujun.springboot.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xujun.springboot.dao.HistoryDao;

import org.xujun.springboot.websoket.utils.ToJsonUtil;

import com.alibaba.fastjson.JSONObject;

@RestController
@CrossOrigin(origins = "*")
public class WifiController {

	@Resource
	HistoryDao historyDao;

	@RequestMapping(value = { "showInfomation" })
	public JSONObject showInfomation() throws IOException {

		List<String> list = new ArrayList<String>();
		try {

			Process pcs = Runtime.getRuntime().exec("sudo nmcli -fields signal,ssid dev wifi list");

			BufferedReader in = new BufferedReader(new InputStreamReader(pcs.getInputStream(), "utf-8"));

			String line = "";

			while ((line = in.readLine()) != null) {

				System.out.println("wifi收到的:" + line);

				String signal = null;
				signal = line.substring(0, 4); // 找到分隔符，截取子字符串
				line = line.substring(6); // 剩下需要处理的字符串
				if (line.trim().equals("SSID")) {
					continue;
				} else {
					list.add(line.trim() + "," + signal.trim());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ToJsonUtil.buildJsonRs(true, "wifi信息列表", list);

	}

	@RequestMapping(value = { "scanning" })
	public JSONObject scanning() throws IOException {

		List<String> list = new ArrayList<String>();

		try {

			Process pcs = Runtime.getRuntime().exec("sudo nmcli device wifi rescan ifname wlan0");

			BufferedReader in = new BufferedReader(new InputStreamReader(pcs.getInputStream(), "utf-8"));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ToJsonUtil.buildJsonRs(true, "手动扫描成功", "");

	}

	@RequestMapping(value = { "getWifis" }) //
	public JSONObject getWifis() {

		List<String> list = new ArrayList<String>();

		try {

			Process pcs = Runtime.getRuntime().exec("nmcli -fields device,name connection show");

			BufferedReader in = new BufferedReader(new InputStreamReader(pcs.getInputStream(), "utf-8"));

			String line = "";

			while ((line = in.readLine()) != null) {

				System.out.println(line);

				String device = null;

				device = line.substring(0, 6); // 找到分隔符，截取子字符串
				line = line.substring(8); // 剩下需要处理的字符串

				if (device.trim().equals("DEVICE") || device.trim().equals("eth0")) {
					continue;
				} else {
					list.add(line.trim() + "," + device.trim());
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ToJsonUtil.buildJsonRs(true, "wifi已存信息", list);

	}

	@RequestMapping(value = { "ConnectWifi" }) // 链接wifi 保存配置
	public JSONObject ConnectWifi(String ssid, String password) {

		List<String> list = new ArrayList<String>();

		try {

			Process pcs = null;
			if (password == null || password == "") {

				pcs = Runtime.getRuntime().exec("sudo nmcli connection up " + ssid);
			} else {

				pcs = Runtime.getRuntime()
						.exec("sudo  nmcli device wifi connect " + ssid + " password " + password + " ifname wlan0");
			}

			BufferedReader in = new BufferedReader(new InputStreamReader(pcs.getInputStream(), "utf-8"));

			String line = "";

			int si = 0;

			while ((line = in.readLine()) != null) {

				System.out.println("收到的: " + line);

				if (line.contains("成功") || line.contains("2K")) {
					return ToJsonUtil.buildJsonRs(true, "链接成功", line);

				} else {
					return ToJsonUtil.buildJsonRs(false, "失败", line);
				}
			}
		} catch (Exception e) {
			return ToJsonUtil.buildJsonRs(false, "失败", "");
		}

		return ToJsonUtil.buildJsonRs(false, "失败", "");

	}

	@RequestMapping(value = { "deleteWifi" }) //  
	public JSONObject deleteWifi(String ssid) {

		List<String> list = new ArrayList<String>();

		try {

			Process pcs = null;

			pcs = Runtime.getRuntime().exec("sudo nmcli connection delete " + ssid);

			BufferedReader in = new BufferedReader(new InputStreamReader(pcs.getInputStream(), "utf-8"));

			String line = "";

			while ((line = in.readLine()) != null) {

				System.out.println("收到的: " + line);

				if (line.contains("成功删除")) {
					return ToJsonUtil.buildJsonRs(true, "成功删除", line);

				} else {
					return ToJsonUtil.buildJsonRs(false, "删除失败", line);
				}

			}
		} catch (Exception e) {
			return ToJsonUtil.buildJsonRs(false, "失败", "");
		}

		return ToJsonUtil.buildJsonRs(false, "失败", "");

	}

	@RequestMapping(value = { "breakOpenWlan" }) //
	public JSONObject breakOpenWlan(Integer state) {

		try {

			Process pcs = null;
			if (state == 0) { // 关闭
				pcs = Runtime.getRuntime().exec("sudo nmcli device disconnect wlan0");

			} else {// 开启
				pcs = Runtime.getRuntime().exec("sudo nmcli device connect wlan0");
			}

			BufferedReader in = new BufferedReader(new InputStreamReader(pcs.getInputStream(), "utf-8"));

			String line = "";

			while ((line = in.readLine()) != null) {

				System.out.println("收到的: " + line);

				if (line.contains("successfully")) {
					return ToJsonUtil.buildJsonRs(true, "成功", line);

				} else {
					return ToJsonUtil.buildJsonRs(false, "失败", line);
				}
			}
		} catch (Exception e) {
			return ToJsonUtil.buildJsonRs(false, "失败", "");
		}

		return ToJsonUtil.buildJsonRs(false, "失败", "");

	}

	@RequestMapping(value = { "connectDownUp" }) //
	public JSONObject connectDownUp(Integer state, String ssid) {

		try {

			Process pcs = null;
			if (state == 0) { // 关闭
				pcs = Runtime.getRuntime().exec("sudo nmcli connection down " + ssid);
			} else {// 开启
				pcs = Runtime.getRuntime().exec("sudo nmcli connection up " + ssid);

			}

			BufferedReader in = new BufferedReader(new InputStreamReader(pcs.getInputStream(), "utf-8"));

			String line = "";

			while ((line = in.readLine()) != null) {

				System.out.println("收到的: " + line);

				if (line.contains("successfully")) {
					return ToJsonUtil.buildJsonRs(true, "成功", line);

				} else {
					return ToJsonUtil.buildJsonRs(false, "失败", line);
				}
			}
		} catch (Exception e) {
			return ToJsonUtil.buildJsonRs(false, "失败", "");
		}

		return ToJsonUtil.buildJsonRs(false, "失败", "");

	}




}