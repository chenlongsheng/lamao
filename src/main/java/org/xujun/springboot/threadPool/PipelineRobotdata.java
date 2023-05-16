package org.xujun.springboot.threadPool;

import java.io.IOException;


import org.xujun.springboot.websoket.MyWebSocket;
import org.xujun.springboot.websoket.utils.ToJsonUtil;

public class PipelineRobotdata {

	public static int checkAF = 0;

	public static void pipelineData(String[] datas2) throws IOException {

		String str = datas2[0];
		String str1 = datas2[1];
		String str2 = datas2[2];
		String str3 = datas2[3];
		String str4 = datas2[4];
		String str5 = datas2[5];

		String str0 = str.replaceAll("\\^", "");

		
		try {
			
		
		int valueOf = Integer.parseInt(str0);
		int valueOf1 = Integer.parseInt(str1);
		int valueOf2 = Integer.parseInt(str2);
		int valueOf3 = Integer.parseInt(str3);
		int valueOf4 = Integer.parseInt(str4);
		int valueOf5 = Integer.parseInt(str5);
//x轴偏移量 y轴偏移量 z轴偏移量  测距仪距离 移动距离
		if (valueOf + valueOf1 + valueOf2 + valueOf3 + valueOf4 != valueOf5) {
			return;
		}

		String numberValue = Data485.df.format((double) valueOf / 100) + ","
				+ Data485.df.format((double) valueOf1 / 100) + "," + Data485.df.format((double) valueOf2 / 100) + ","
				+ Data485.df.format((double) valueOf3 / 100) + "," + Data485.df.format((double) valueOf4 / 100);

		MyWebSocket.sendInfo(ToJsonUtil.buildJsonRs(true, "numberValue", numberValue).toString());
		System.out.println(numberValue);
		
		
		} catch (Exception e) {
			 
		}
	}

}
