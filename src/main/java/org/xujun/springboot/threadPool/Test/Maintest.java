/**
 *
 */
package org.xujun.springboot.threadPool.Test;

import java.awt.geom.Line2D;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.xujun.springboot.websoket.utils.ToJsonUtil;

/**
 * @author admin
 *
 */
public class Maintest {

    public static void main(String[] args) throws Exception {

//		DecimalFormat df = new DecimalFormat("#.00");
//
//		System.out.println(df.format(220.22));

//		 DecimalFormat df = new DecimalFormat("#0.00");		 
//		 File[] disks = File.listRoots();
//		 for (File file : disks) {			 
//			long usableSpace = file.getUsableSpace();
//			 String a= usableSpace/1024/1024/1024 +"GB";
//			 System.out.println(a);			
//		}
//		 

//		 List<String> list = new ArrayList<>();
//	        list.add("111,222");
//	        list.add("222,333");
//	        list.add("333,685");
//	        list.add("444,568");
//	        String str = String.join("#",list);
//	        System.out.println(str);

//		Line2D line1 = new Line2D.Float(100, 100, 200, 200);
//		Line2D line2 = new Line2D.Float(150, 100, 210, 200);
//		boolean result = line2.intersectsLine(line1);
//		System.out.println(Line2D.linesIntersect(100,100,200,200,150,100,210,200));

//		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	    String strDate = sdf.format(new Date());
//		 
//		
//		 System.out.println(strDate);
//		 
//		 
//		 File file = new File("path");//所属文件夹的路径
//		 if(file.isDirectory()){
//		 File[] files = file.listFiles();
//		 if(files.length > 0){
//		 System.out.println("有文件");
//		 } else {
//		 System.out.println("空文件夹，没有文件");
//		 }
//		 }

        ddd();
    }

    public static void ddd() {

        try {
//			Process pcs = Runtime.getRuntime().exec("/usr/bin/florence " + name);
//            System.out.println(Arrays.toString(cmds));

            System.out.println("发送---");
            Process pcs = Runtime.getRuntime().exec("/usr/bin/onboard");

            BufferedReader in = new BufferedReader(new InputStreamReader(pcs.getInputStream(), "utf-8"));

            String line = "";
            while ((line = in.readLine()) != null) {
                System.out.println("wifi收到一个的:  " + line);
            }
            int waitFor = pcs.waitFor();
            System.out.println("waitFor = " + waitFor);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

	public static boolean juge(String str) {

		String[] split = str.split("\\.");

		if (split.length != 2) {
			return false;
		} else if (split[0].length() == 0 || split[1].length() != 2) {
			return false;
		}
		return true;
	}

	public static void exportDaypartDataRport() throws Exception {

		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("分时段");
// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row0 = sheet.createRow((int) 0);

		HSSFRow row = sheet.createRow((int) 1);
// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		sheet.setDefaultColumnWidth(20);
		HSSFFont nameRowFont = wb.createFont();
		nameRowFont.setFontName("微软雅黑");
		nameRowFont.setFontHeightInPoints((short) 12);// 设置字体大小
		nameRowFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
		style.setFont(nameRowFont);

		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		HSSFCell cell0 = row0.createCell((short) 0);
		cell0.setCellValue("分区名称");
		cell0.setCellStyle(style);

		cell0 = row0.createCell((short) 1);
		cell0.setCellValue("峰");
		cell0.setCellStyle(style);

		HSSFCellStyle style1 = wb.createCellStyle();
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		sheet.setDefaultColumnWidth(20);
		HSSFFont nameRowFont1 = wb.createFont();
		nameRowFont1.setFontName("微软雅黑");
		nameRowFont1.setFontHeightInPoints((short) 12);// 设置字体大小
		style1.setFont(nameRowFont1);
		String fileName = "分时段用电";
		for (int i = 0; i < 10; i++) {
			row = sheet.createRow((int) i + 1);

			// 第四步，创建单元格，并设置值
			cell0 = row.createCell((short) 0);
			cell0.setCellValue((String) "111");
			cell0.setCellStyle(style1);
			cell0 = row.createCell((short) 1);
			cell0.setCellValue((String) "222");
			cell0.setCellStyle(style1);
		}
		// 响应到客户端
		System.out.println("8888");

		OutputStream os = new FileOutputStream(new File("d:\\据.xlsx"));
		wb.write(os);
		os.flush();
		os.close();

	}

}
