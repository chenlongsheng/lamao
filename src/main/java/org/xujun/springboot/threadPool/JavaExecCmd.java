/**
 * 
 */
package org.xujun.springboot.threadPool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @author admin
 *
 */
public class JavaExecCmd {

	public static String execReadToString(String execCommand) throws IOException {
		try (Scanner s = new Scanner(Runtime.getRuntime().exec(execCommand).getInputStream()).useDelimiter("\\A")) {
			return s.hasNext() ? s.next() : "";
		}
	}

	public static Map<String, String> getWifiInfo() {
		Map<String, String> wifiMap = new HashMap<>();
		String macCommand = "/System/Library/PrivateFrameworks/Apple80211.framework/Resources/airport -I";
		String wifiInfo = null;
		try {
			wifiInfo = execReadToString(macCommand);

		} catch (IOException e) {
			e.printStackTrace();
			return wifiMap;
		}
		// System.out.println(wifiInfo);

		for (String line : wifiInfo.split("\n")) {
			if (line.contains(" SSID:")) {
				wifiMap.put("ssid", line.split(":", 2)[1].trim());
			} else if (line.contains("BSSID:")) {
				wifiMap.put("bssid", line.split(":", 2)[1].trim());
			}
		}

		return wifiMap;
	}

	public static void ss() {
		try {
			Process p = Runtime.getRuntime().exec("netsh wlan show networks mode=bssid");
			BufferedReader bf = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while (bf.readLine() != null)
				System.out.println(bf.readLine());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static Boolean getWifiInfomation() {

		List<String> list = new ArrayList<String>();

		Boolean judge = false;
		try {

//			Process pcs = Runtime.getRuntime().exec("netsh wlan show networks mode=bssid");

			Process pcs = Runtime.getRuntime().exec("sudo nmcli dev wifi");

			BufferedReader in = new BufferedReader(new InputStreamReader(pcs.getInputStream(), "utf-8"));

			String line = "";

			int si = 0;

			while ((line = in.readLine()) != null) {

				String[] s = line.split("\\s+");

				for (int i = 0; i < s.length; i++) {

					if (s[i].equals("SSID")) {
						si = i;
					} else {

						list.add(s[si]);
					}
				}
				System.out.println("wifi收到的:" + line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	public static void main(String[] args) {

		String a = "IN-USE SSID  MODE  CHAN  RATE  SIGNAL  BARS  SECURITY";
		String b = "*       cdsoft  红外  7     270 Mbit/s  61      ▂▄▆_  WPA1 WPA2";
		String[] s = a.split("\\s+");

		for (String str : s) {
			System.out.println(str);
		}

//		getWifiInfomation();
//		Map<String, String> result = null;
//		result = getWifiInfo();
//		System.out.println(result);
	}

}
