/**
 *
 */
package org.xujun.springboot.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.stereotype.Component;
import org.xujun.springboot.dao.HistoryDao;
import org.xujun.springboot.model.MapEntity;
import org.xujun.springboot.service.impl.IHistoryService;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * @author admin
 */
@Component
public class OutExeclService implements IHistoryService {


    public static void exportAnalysisReports(List<MapEntity> historyDatas, String ipAddr) throws Exception {

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
        cell0.setCellValue("工件号");
        cell0.setCellStyle(style);

        cell0 = row0.createCell((short) 1);
        cell0.setCellValue("工件名");
        cell0.setCellStyle(style);

        cell0 = row0.createCell((short) 2);
        cell0.setCellValue("螺母数");
        cell0.setCellStyle(style);

        cell0 = row0.createCell((short) 3);
        cell0.setCellValue("操作员");
        cell0.setCellStyle(style);


        cell0 = row0.createCell((short) 4);
        cell0.setCellValue("作业时间");
        cell0.setCellStyle(style);

        cell0 = row0.createCell((short) 5);
        cell0.setCellValue("是否合格");
        cell0.setCellStyle(style);

        cell0 = row0.createCell((short) 6);
        cell0.setCellValue("异常说明");
        cell0.setCellStyle(style);


        HSSFCellStyle style1 = wb.createCellStyle();
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        sheet.setDefaultColumnWidth(20);
        HSSFFont nameRowFont1 = wb.createFont();
        nameRowFont1.setFontName("微软雅黑");
        nameRowFont1.setFontHeightInPoints((short) 12);// 设置字体大小
        style1.setFont(nameRowFont1);
        String fileName = "历史数据";
        for (int i = 0; i < historyDatas.size(); i++) {
            row = sheet.createRow((int) i + 1);

            // 第四步，创建单元格，并设置值
            cell0 = row.createCell((short) 0);
            cell0.setCellValue((String) historyDatas.get(i).get("workpieceNo"));
            cell0.setCellStyle(style1);

            cell0 = row.createCell((short) 1);
            cell0.setCellValue((String) historyDatas.get(i).get("workpieceName"));
            cell0.setCellStyle(style1);
            // 第四步，创建单元格，并设置值
            cell0 = row.createCell((short) 2);
            cell0.setCellValue((String) historyDatas.get(i).get("nutsNumber").toString());
            cell0.setCellStyle(style1);

            cell0 = row.createCell((short) 3);
            cell0.setCellValue((String) historyDatas.get(i).get("name"));
            cell0.setCellStyle(style1);
            // 第四步，创建单元格，并设置值
            cell0 = row.createCell((short) 4);
            cell0.setCellValue((String) historyDatas.get(i).get("historyTime").toString());
            cell0.setCellStyle(style1);


            // 第四步，创建单元格，并设置值
            cell0 = row.createCell((short) 5);
            cell0.setCellValue((String) historyDatas.get(i).get("isPass").toString());
            cell0.setCellStyle(style1);

            cell0 = row.createCell((short) 6);
            cell0.setCellValue((String) historyDatas.get(i).get("errorNum").toString());
            cell0.setCellStyle(style1);
        }
        // 响应到客户端
        System.out.println("ipAddr:   " + ipAddr);
        OutputStream os = new FileOutputStream(new File(ipAddr));
        wb.write(os);
        os.flush();
        os.close();

    }

    //新写
    public static void exportAnalysisReports1(List<MapEntity> historyDatas, String ipAddr) throws Exception {

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
        cell0.setCellValue("工件号");
        cell0.setCellStyle(style);

        cell0 = row0.createCell((short) 1);
        cell0.setCellValue("工件名");
        cell0.setCellStyle(style);

        cell0 = row0.createCell((short) 2);
        cell0.setCellValue("螺母数");
        cell0.setCellStyle(style);

        cell0 = row0.createCell((short) 3);
        cell0.setCellValue("完成螺母数");
        cell0.setCellStyle(style);

        cell0 = row0.createCell((short) 4);
        cell0.setCellValue("操作员");
        cell0.setCellStyle(style);


        cell0 = row0.createCell((short) 5);
        cell0.setCellValue("作业时间");
        cell0.setCellStyle(style);


        cell0 = row0.createCell((short) 6);
        cell0.setCellValue("拉力max（KN)");
        cell0.setCellStyle(style);


        cell0 = row0.createCell((short) 7);
        cell0.setCellValue("位移max（mm）");
        cell0.setCellStyle(style);

        cell0 = row0.createCell((short) 8);
        cell0.setCellValue("是否合格");
        cell0.setCellStyle(style);

        cell0 = row0.createCell((short) 9);
        cell0.setCellValue("异常说明");
        cell0.setCellStyle(style);


        HSSFCellStyle style1 = wb.createCellStyle();
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        sheet.setDefaultColumnWidth(20);
        HSSFFont nameRowFont1 = wb.createFont();
        nameRowFont1.setFontName("微软雅黑");
        nameRowFont1.setFontHeightInPoints((short) 12);// 设置字体大小
        style1.setFont(nameRowFont1);
        String fileName = "历史数据";
        for (int i = 0; i < historyDatas.size(); i++) {
            row = sheet.createRow((int) i + 1);

            // 第四步，创建单元格，并设置值
            cell0 = row.createCell((short) 0);
            cell0.setCellValue((String) historyDatas.get(i).get("workpieceNo"));
            cell0.setCellStyle(style1);

            cell0 = row.createCell((short) 1);
            cell0.setCellValue((String) historyDatas.get(i).get("workpieceName"));
            cell0.setCellStyle(style1);
            // 第四步，创建单元格，并设置值
            cell0 = row.createCell((short) 2);
            cell0.setCellValue((String) historyDatas.get(i).get("nutsNumber").toString());
            cell0.setCellStyle(style1);


            cell0 = row.createCell((short) 3);
            cell0.setCellValue(Integer.parseInt(historyDatas.get(i).get("number").toString().split("\\.")[0]));
            cell0.setCellStyle(style1);


            cell0 = row.createCell((short) 4);
            cell0.setCellValue((String) historyDatas.get(i).get("name"));
            cell0.setCellStyle(style1);


            // 第四步，创建单元格，并设置值
            cell0 = row.createCell((short) 5);
            cell0.setCellValue((String) historyDatas.get(i).get("historyTime").toString());
            cell0.setCellStyle(style1);

            // 第四步，创建单元格，并设置值
            cell0 = row.createCell((short) 6);
            cell0.setCellValue((String) historyDatas.get(i).get("sum_values").toString());
            cell0.setCellStyle(style1);


            cell0 = row.createCell((short) 7);
            cell0.setCellValue((String) historyDatas.get(i).get("maxDisplace").toString());
            cell0.setCellStyle(style1);


            // 第四步，创建单元格，并设置值
            cell0 = row.createCell((short) 8);
            cell0.setCellValue((String) historyDatas.get(i).get("isPass").toString());
            cell0.setCellStyle(style1);

            cell0 = row.createCell((short) 9);
            cell0.setCellValue((String) historyDatas.get(i).get("errorNum").toString());
            cell0.setCellStyle(style1);
        }
        // 响应到客户端
        System.out.println("ipAddr:   " + ipAddr);
        OutputStream os = new FileOutputStream(new File(ipAddr));
        wb.write(os);
        os.flush();
        os.close();

    }

    public static void exportFaultsReports(List<MapEntity> historyDatas, String ipAddr) throws Exception {

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
        cell0.setCellValue("工作时间");
        cell0.setCellStyle(style);

        cell0 = row0.createCell((short) 1);
        cell0.setCellValue("班次");
        cell0.setCellStyle(style);

        cell0 = row0.createCell((short) 2);
        cell0.setCellValue("工件名");
        cell0.setCellStyle(style);

        cell0 = row0.createCell((short) 3);
        cell0.setCellValue("工件名");
        cell0.setCellStyle(style);


        cell0 = row0.createCell((short) 4);
        cell0.setCellValue("螺母数");
        cell0.setCellStyle(style);


        cell0 = row0.createCell((short) 5);
        cell0.setCellValue("操作员");
        cell0.setCellStyle(style);

        cell0 = row0.createCell((short) 6);
        cell0.setCellValue("不良判别");
        cell0.setCellStyle(style);


        HSSFCellStyle style1 = wb.createCellStyle();
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        sheet.setDefaultColumnWidth(20);
        HSSFFont nameRowFont1 = wb.createFont();
        nameRowFont1.setFontName("微软雅黑");
        nameRowFont1.setFontHeightInPoints((short) 12);// 设置字体大小
        style1.setFont(nameRowFont1);
        String fileName = "不良品数据";
        for (int i = 0; i < historyDatas.size(); i++) {
            row = sheet.createRow((int) i + 1);

            // 第四步，创建单元格，并设置值
            cell0 = row.createCell((short) 0);
            cell0.setCellValue((String) historyDatas.get(i).get("time"));
            cell0.setCellStyle(style1);

            cell0 = row.createCell((short) 1);
            cell0.setCellValue((String) historyDatas.get(i).get("classes"));
            cell0.setCellStyle(style1);
            // 第四步，创建单元格，并设置值
            cell0 = row.createCell((short) 2);
            cell0.setCellValue((String) historyDatas.get(i).get("workpiece_name").toString());
            cell0.setCellStyle(style1);

            cell0 = row.createCell((short) 3);
            cell0.setCellValue((String) historyDatas.get(i).get("workpiece_no"));
            cell0.setCellStyle(style1);
            // 第四步，创建单元格，并设置值
            cell0 = row.createCell((short) 4);
            cell0.setCellValue((String) historyDatas.get(i).get("nuts_number").toString());
            cell0.setCellStyle(style1);

            // 第四步，创建单元格，并设置值
            cell0 = row.createCell((short) 5);
            cell0.setCellValue((String) historyDatas.get(i).get("login_name").toString());
            cell0.setCellStyle(style1);


            cell0 = row.createCell((short) 6);
            cell0.setCellValue((String) historyDatas.get(i).get("fault_name").toString());
            cell0.setCellStyle(style1);
        }
        // 响应到客户端
        System.out.println("ipAddr:   " + ipAddr);
        OutputStream os = new FileOutputStream(new File(ipAddr));
        wb.write(os);
        os.flush();
        os.close();

    }


    public static void exportProductionsReports(List<MapEntity> historyDatas, String ipAddr) throws Exception {

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
        cell0.setCellValue("日期");
        cell0.setCellStyle(style);

        cell0 = row0.createCell((short) 1);
        cell0.setCellValue("班次");
        cell0.setCellStyle(style);

        cell0 = row0.createCell((short) 2);
        cell0.setCellValue("工件名");
        cell0.setCellStyle(style);

        cell0 = row0.createCell((short) 3);
        cell0.setCellValue("螺母数");
        cell0.setCellStyle(style);


        cell0 = row0.createCell((short) 4);
        cell0.setCellValue("操作员");
        cell0.setCellStyle(style);

        cell0 = row0.createCell((short) 5);
        cell0.setCellValue("作业时长");
        cell0.setCellStyle(style);

        cell0 = row0.createCell((short) 6);
        cell0.setCellValue("生产数");
        cell0.setCellStyle(style);

        cell0 = row0.createCell((short) 7);
        cell0.setCellValue("良品数");
        cell0.setCellStyle(style);

        cell0 = row0.createCell((short) 8);
        cell0.setCellValue("不良数");
        cell0.setCellStyle(style);

        cell0 = row0.createCell((short) 9);
        cell0.setCellValue("良品率");
        cell0.setCellStyle(style);

        cell0 = row0.createCell((short) 10);
        cell0.setCellValue("生产效率（S/PC)");
        cell0.setCellStyle(style);


        HSSFCellStyle style1 = wb.createCellStyle();
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        sheet.setDefaultColumnWidth(20);
        HSSFFont nameRowFont1 = wb.createFont();
        nameRowFont1.setFontName("微软雅黑");
        nameRowFont1.setFontHeightInPoints((short) 12);// 设置字体大小
        style1.setFont(nameRowFont1);
        String fileName = "生产数据报表";
        for (int i = 0; i < historyDatas.size(); i++) {
            row = sheet.createRow((int) i + 1);

            // 第四步，创建单元格，并设置值
            cell0 = row.createCell((short) 0);
            cell0.setCellValue((String) historyDatas.get(i).get("history_time").toString());
            cell0.setCellStyle(style1);

            cell0 = row.createCell((short) 1);
            cell0.setCellValue((String) historyDatas.get(i).get("classes"));
            cell0.setCellStyle(style1);
            // 第四步，创建单元格，并设置值
            cell0 = row.createCell((short) 2);
            cell0.setCellValue((String) historyDatas.get(i).get("workpiece_name").toString());
            cell0.setCellStyle(style1);

            cell0 = row.createCell((short) 3);
            cell0.setCellValue((String) historyDatas.get(i).get("workpiece_num").toString());
            cell0.setCellStyle(style1);
            // 第四步，创建单元格，并设置值
            cell0 = row.createCell((short) 4);
            cell0.setCellValue((String) historyDatas.get(i).get("login_name").toString());
            cell0.setCellStyle(style1);

            // 第四步，创建单元格，并设置值
            cell0 = row.createCell((short) 5);
            cell0.setCellValue((String) historyDatas.get(i).get("totalHour").toString());
            cell0.setCellStyle(style1);


            cell0 = row.createCell((short) 6);
            cell0.setCellValue((String) historyDatas.get(i).get("totalCount").toString());
            cell0.setCellStyle(style1);

            cell0 = row.createCell((short) 7);
            cell0.setCellValue((String) historyDatas.get(i).get("goodCount").toString());
            cell0.setCellStyle(style1);
            // 第四步，创建单元格，并设置值
            cell0 = row.createCell((short) 8);
            cell0.setCellValue((String) historyDatas.get(i).get("notPassCount").toString());
            cell0.setCellStyle(style1);

            // 第四步，创建单元格，并设置值
            cell0 = row.createCell((short) 9);
            cell0.setCellValue((String) historyDatas.get(i).get("goodEffic").toString());
            cell0.setCellStyle(style1);


            cell0 = row.createCell((short) 10);
            cell0.setCellValue((String) historyDatas.get(i).get("efficiency").toString());
            cell0.setCellStyle(style1);
        }
        // 响应到客户端
        System.out.println("ipAddr:   " + ipAddr);
        OutputStream os = new FileOutputStream(new File(ipAddr));
        wb.write(os);
        os.flush();
        os.close();

    }
}
