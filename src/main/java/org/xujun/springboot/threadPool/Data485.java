/**
 *
 */
package org.xujun.springboot.threadPool;

import java.awt.geom.Line2D;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.xujun.springboot.dao.HistoryDao;
import org.xujun.springboot.model.MapEntity;
import org.xujun.springboot.threadPool.MyThread485;
import org.xujun.springboot.threadPool.ReceiveData;
import org.xujun.springboot.threadPool.TimeThread;
import org.xujun.springboot.websoket.MyWebSocket;
import org.xujun.springboot.websoket.utils.ToJsonUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

/**
 * @author admin
 */
public class Data485 {

    public static MapEntity entity; // 数据库获取的当前用户userId和pieceId信息和存入的件数铆钉数

    public static String userId; // 当前用户

    public static String workpieceId;

    public static int count = 0; // 件数

    public static int workpieceCount; // 件数

    public static int nutsNumber = 0; // 铆钉数

    public static int maxDisplace = 0; // 最大位移

    public static int maxValue = 0; // 最大拉力值

    public static int inputConfig = 0; // 是否输入到硬件 工件完成时

    public static String workpieceNo = "";//自动生成的工件号

    public static String inputWorkpieceNo = ""; //输入工件号

    public static int workpieceStatus = 0;  //工件状态,是否手动还是自动生成标识

    public static double coefficient = 1; // 校准数值

    static List<String> dataList = new ArrayList<String>();//添加数据

    public static MapEntity enSend = new MapEntity(); // 发送websocket消息用的

    static String checkPass = "false"; // 是否计算通过合格铆钉

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    static String strDate = sdf.format(new Date()); // 计算是否为第二天

    static SimpleDateFormat sdfHistory = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static String historyTime; // 计算第一个铆钉,开始就插入数据库时间
    static DecimalFormat df = new DecimalFormat("#0.00");

    public static int status = 0; // 0代表发送485异常消息,1是正常开启485气门
    public static int adjustingStartEnd = 0; // 0关闭校验 1是开启校验

    public static int testState = 10; // 10正常,11开启测试状态

    public static Long lastDataReceivedTime; //暂时没有用

    public static Thread thread = new Thread(); //暂时没有用

    public static void checkData(String data, HistoryDao historyDao) throws IOException {

        System.out.println(data);

        ReceiveData.machineData(data, historyDao);

        if (data.contains("@@@")) {

            System.out.println("收到复位按钮的消息---");

            if (entity.get("process").toString().equals("0")) { // 数据库配置

                MyWebSocket.sendInfo(ToJsonUtil.buildJsonRs(true, "cover", "1").toString());// 1是跳過
            } else {
                MyWebSocket.sendInfo(ToJsonUtil.buildJsonRs(true, "cover", "0").toString());//  0 重做
            }
        } else if (data.contains("###")) {   // 起始
            if (testState == 10) {
                String strDateSconce = sdf.format(new Date());
                if (!strDateSconce.equals(strDate)) { // 第二天重新開始計數
                    count = 0;
                    nutsNumber = 0;
                    strDate = strDateSconce;
                    sendRatio(historyDao);
                }
                if (nutsNumber == 0) {
                    historyTime = sdfHistory.format(new Date());
                }
            }

            MyWebSocket.sendInfo(ToJsonUtil.buildJsonRs(true, "clear", data).toString());//清除前段图形,画新图
            dataList.clear();//开始新一轮,清除数据


        } else if (data.contains("&&&")) { // 结束时

            maxValue = 0;//完成一个铆钉后,最大值复位,重新开始
            if (testState == 11) { //测试发送
                MyWebSocket.sendInfo(ToJsonUtil.buildJsonRs(true, "startEnd", "0").toString());
                return;
            }

//            if (dataList == null ||  dataList.size() < 3) { // 中间没有数据的时候不发送
//                return;
//            }

            MyWebSocket.sendInfo(ToJsonUtil
                    .buildJsonRs(true, "numberValue", df.format((double) maxDisplace / 100) + "," + df.format(0))
                    .toString());// 补充最后一条数据

            dataList.add(df.format((double) maxDisplace / 100) + "," + df.format(0));
            checkPass = checkPass(dataList);
            System.out.println("checkPass: " + checkPass);

            if (checkPass.equals("0")) {
                savedata(historyDao, 0);// 拉铆合格,写入历史数据库
                MyThread485.write(new byte[]{'S', 'N', '$'}); // 单个螺母报警

            } else {
                MyThread485.write(new byte[]{'N', 'O', '$'});// 异常关闭阀门
                new TimeThread(new byte[]{'N', 'O', '$'}).start();// 再發一條
            }

            MyWebSocket.sendInfo(ToJsonUtil.buildJsonRs(true, "startEnd", checkPass).toString());
            addRealLog(historyDao);// 判断预警次数报警

        } else if (data.equals("OK") || data.contains("NO") || data.contains("J0") || data.contains("J1")) { // 发送异常失败后,返回继续发送
            System.out.println("收到回复的消息:" + data);
            TimeThread.isStop = false;
        } else if (data.equals("***")) {//校准系数是开始字符
            MyWebSocket.sendInfo(ToJsonUtil.buildJsonRs(true, "clear", data).toString());

        } else if (data.equals("+++")) {//校准系数是结束字符
            MyWebSocket.sendInfo(ToJsonUtil.buildJsonRs(true, "startEnd", data).toString());
        }
        // 一下发送数据
        String[] datas2 = data.split(",");

        try {
            if (datas2.length == 3 && adjustingStartEnd == 0) {
                regularData(datas2); // 正常发送数据

                System.out.println("limina==="+datas2);
            } else if (datas2.length == 4 && adjustingStartEnd == 1) { //校准系数
                adjustData(datas2); // 校准发送数据
            }
        } catch (Exception e) {

        }

    }

    public static void regularData(String[] datas2) throws IOException {

        String str = datas2[0];
        String str1 = datas2[1];
        String str2 = datas2[2];

        String str0 = str.replaceAll("\\^", "");

        int valueOf = Integer.parseInt(str0);
        int valueOf1 = Integer.parseInt(str1);
        int valueOf2 = Integer.parseInt(str2);

        if (valueOf + valueOf1 != valueOf2) {
            return;
        }

        lastDataReceivedTime = System.currentTimeMillis();

//过滤多余的数据
        if (maxValue < valueOf1) {
            maxValue = valueOf1;
            maxDisplace = valueOf;
        } else {
            return;
        }
//----------
        if (valueOf > 0 && valueOf1 > 0 && valueOf < 8 * 100 && valueOf1 < 150 * 100) {
            String numberValue = df.format((double) valueOf / 100) + ","
                    + df.format((double) valueOf1 * coefficient / 100);
            dataList.add(numberValue);

            int num = (int) (Math.random() * 2);

            MyWebSocket.sendInfo(ToJsonUtil.buildJsonRs(true, "numberValue", numberValue).toString());

//            System.out.println(numberValue);
        } else {

        }
    }

    public static void adjustData(String[] adjDatas) throws IOException {

        int data0 = Integer.parseInt(adjDatas[0].replaceAll("\\^", ""));
        int data1 = Integer.parseInt(adjDatas[1]);
        int data2 = Integer.parseInt(adjDatas[2]);
        int data3 = Integer.parseInt(adjDatas[3]);

        if (data0 + data1 + data2 != data3) {
            return;
        }

        String numberValue = df.format((double) data0) + "," + df.format((double) data1 / 100) + ","
                + df.format((double) data2 / 100);
        System.out.println(numberValue);
        MyWebSocket.sendInfo(ToJsonUtil.buildJsonRs(true, "adjustData", numberValue).toString());

    }

    /*
     *
     * 添加历史数据
     */
    public static void savedata(HistoryDao historyDao, int num) throws IOException {

        nutsNumber += 1; // 铆钉+1

        if (workpieceStatus == 0) {// 自动输入

            workpieceNo = (String) entity.get("dateTime")
                    + String.format((String) entity.get("formatNum"), workpieceCount);// 拼接工单号
        } else {
            workpieceNo = inputWorkpieceNo; // 手动
        }
        if (num != 0) {
            num = nutsNumber; // num= 0代表合格...num= 1 代表不合格可以添加
        }
        if (nutsNumber != 1) {
            historyTime = sdfHistory.format(new Date()); // 获取 最新时间
        }
        historyDao.addHistory( workpieceId, userId,workpieceNo, (String) entity.get("workpieceName"),
                JSON.toJSONString(dataList), historyTime, num, checkPass);
        if (nutsNumber >= Integer.parseInt(entity.get("nutsNumber").toString())) { // 鉚釘數 大於最大數 计件
            addWorkpieceNum();
        }

        historyDao.updateUserPiece(workpieceId, userId, nutsNumber + "", count, workpieceCount); // 更新最新件数和铆钉数到数据库

        enSend.put("count", count);
        enSend.put("currentNutsNumber", nutsNumber);

        if (workpieceStatus == 0) { // 输入
            enSend.put("workpieceNo",
                    (String) entity.get("dateTime") + String.format((String) entity.get("formatNum"), workpieceCount));
            System.out.println(
                    (String) entity.get("dateTime") + String.format((String) entity.get("formatNum"), workpieceCount));
        }

        String ratio = historyDao.getRatio("2", userId);
        enSend.put("Ratio", ratio); // 员工当天效率

        System.out.println("count=" + count); // 件数
        System.out.println("nutsNumber=" + nutsNumber); // 铆钉数
        System.out.println("当天效率" + ratio);

        MyWebSocket.sendInfo(ToJsonUtil.buildJsonRs(true, "begin", enSend).toString());
        enSend.clear();

    }

    public static void addWorkpieceNum() throws IOException { // 完成一个工件用

        nutsNumber = 0;
        count += 1;
        workpieceCount += 1;

        if (inputConfig == 1) { //
            MyThread485.write(new byte[]{'S', 'P', '$'});// 完成一个工件发送消息
        }

        MyWebSocket.sendInfo(ToJsonUtil.buildJsonRs(true, "finish", "").toString()); // 工件完成

    }

    public static void finishWorkpieceNum(HistoryDao historyDao) throws IOException {

        historyDao.updateUserPiece(workpieceId, userId, nutsNumber + "", count, workpieceCount); // 更新最新件数和铆钉数到数据库

        enSend.put("count", count);
        enSend.put("currentNutsNumber", nutsNumber);

        if (workpieceStatus == 0) { // 自动输入
            enSend.put("workpieceNo",
                    (String) entity.get("dateTime") + String.format((String) entity.get("formatNum"), workpieceCount));

            System.out.println(
                    (String) entity.get("dateTime") + String.format((String) entity.get("formatNum"), workpieceCount));
        }

        MyWebSocket.sendInfo(ToJsonUtil.buildJsonRs(true, "begin", enSend).toString());
        enSend.clear();

    }

    public static void addRealLog(HistoryDao historyDao) {

        historyDao.updataTaintainAddOne();

//		MapEntity selectMaintainConfig = historyDao.selectMaintainConfig();

        List<MapEntity> selectAlarmStates = historyDao.selectAlarmState();

        int status = 0;
        for (MapEntity mapEntity : selectAlarmStates) {
            int sta = Integer.parseInt(mapEntity.get("status").toString());
            String id = mapEntity.get("id").toString();

            String realRelieveState = mapEntity.get("realRelieveState").toString();

            String time = (String) mapEntity.get("time");

            if (status < sta) {
                status = sta;
            }

            if (sta != 1) { // 不解除凤鸣时候
                historyDao.updateRealLog(realRelieveState, time, realRelieveState, id);
            }

        }

        if (status == 2) {
            MyThread485.write(new byte[]{'F', 'A', '$'});// 发送故障报警

        } else if (status == 1) {
            MyThread485.write(new byte[]{'R', 'A', '$'});// 发送红灯
        }
        //
//		if (selectMaintainConfig.get("isMandrel").toString().equals("1")
//				|| selectMaintainConfig.get("isHydraulicOilReal").toString().equals("1")
//				|| selectMaintainConfig.get("isRepairReal").toString().equals("1")) {
//
//			MyThread485.write(new byte[] { 'F', 'A', '$' });// 发送故障报警
//
//		}

//		historyDao.updateRealLog(selectMaintainConfig.get("isMandrel").toString(),
//				selectMaintainConfig.get("isMandrelTime").toString(), selectMaintainConfig.get("isMandrel").toString(),
//				"3");
//		historyDao.updateRealLog(selectMaintainConfig.get("isHydraulicOilReal").toString(),
//				selectMaintainConfig.get("isHydraulicOilRealTime").toString(),
//				selectMaintainConfig.get("isHydraulicOilReal").toString(), "4");
//		historyDao.updateRealLog(selectMaintainConfig.get("isRepairReal").toString(),
//				selectMaintainConfig.get("isRepairRealTime").toString(),
//				selectMaintainConfig.get("isRepairReal").toString(), "5");

    }

    /*
     *
     * 判断拉铆是否合格
     */
    public static String checkPass(List<String> dataList) {

        String boo = "1";
        int boo6 = 0;

        double x0 = 0;
        double y0 = 0;

        double x = 0;
        double y = 0;

        double minX = Double.parseDouble(entity.get("displaceMin").toString());
        double maxX = Double.parseDouble(entity.get("displaceMax").toString());
        double minY = Double.parseDouble(entity.get("relayMin").toString());
        double maxY = Double.parseDouble(entity.get("relayMax").toString());

        double realMaxX = 0;
        double realMaxY = 0;

        // (minX,minY) (minX,maxY) (maxX,minY)

        for (int i = 1; i < dataList.size(); i++) {

            x = Double.parseDouble(dataList.get(i).split(",")[0]);
            y = Double.parseDouble(dataList.get(i).split(",")[1]);

            if (realMaxY < y) {
                realMaxY = y;
                realMaxX = x;
            }

            if (x > 1 && x < 3 && y > 50) {
                boo6 = 1;
            } else if (x >= 6) {

                if (boo6 == 1) {
                    return "5";// 斷軸
                } else {
                    return "6";// 空搶
                }

            }
        }

        for (int i = 1; i < dataList.size(); i++) {

            x0 = Double.parseDouble(dataList.get(i - 1).split(",")[0]);
            y0 = Double.parseDouble(dataList.get(i - 1).split(",")[1]);

            x = Double.parseDouble(dataList.get(i).split(",")[0]);
            y = Double.parseDouble(dataList.get(i).split(",")[1]);

            boolean linesIntersect0 = Line2D.linesIntersect(x0, y0, x, y, minX, minY, minX, maxY);

            boolean linesIntersect = Line2D.linesIntersect(x0, y0, x, y, minX, minY, maxX, minY);

            if (linesIntersect0 || linesIntersect) {

                System.out.println(x0 + "," + y0 + "," + x + "," + y);

                return "0";
            } else if (realMaxX > minX && realMaxX < maxX) {

                if (realMaxY < minY) {
                    boo = "1"; // 拉力小
                } else if (realMaxY > maxY) {
                    boo = "2"; // 拉力太大
                }
            } else if (realMaxX < minX) {

                boo = "3"; // 位移太小

            } else if (realMaxX > maxX) {

                boo = "4"; // 位移太大
            }

        }
        return boo;
    }

    public static void sendRatio(HistoryDao historyDao) throws IOException {

        String ratio2 = historyDao.getRatio("2", userId); // 员工当天
        String ratio1 = historyDao.getRatio("1", userId);// 员工历史的
        String ratio3 = historyDao.getRatio("3", userId);// 所有的

        enSend.put("currentAveRatio", ratio1);
        enSend.put("historyAveRatio", ratio3);
        enSend.put("Ratio", ratio2);

        System.out.println(enSend.toString());
        MyWebSocket.sendInfo(ToJsonUtil.buildJsonRs(true, "begin", enSend).toString());
        enSend.clear();

    }

}
