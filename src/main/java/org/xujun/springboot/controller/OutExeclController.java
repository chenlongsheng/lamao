/**
 *
 */
package org.xujun.springboot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.xujun.springboot.dao.HistoryDao;
import org.xujun.springboot.dao.ProductionDao;
import org.xujun.springboot.model.MapEntity;
import org.xujun.springboot.websoket.utils.ToJsonUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * @author admin
 */
@RestController
@CrossOrigin(origins = "*")
public class OutExeclController {

    @Resource
    org.xujun.springboot.service.HistoryService historyService;

    @Resource
    private ProductionDao productionDao;

    @Resource
    org.xujun.springboot.service.OutExeclService outExeclService;


    @Resource
    private HistoryDao historyDao;

    static String workpieNo = "";

    /*
     *
     * 导出铆接监控数据
     *
     */
    @RequestMapping("/exportHisReports0")
    @ResponseBody
    public JSONObject exportHisReports0(String workpieceId, String workpieceName, String userId, String workpieceNo,
                                        String beginTime, String endTime, String isPass, String ipAddr, HttpServletResponse response) throws Exception {

        List<MapEntity> historyDatas = historyDao.getHistoryDatas(workpieceId, workpieceName, userId, workpieceNo,
                beginTime, endTime, isPass);

        System.out.println(ipAddr);

        try {
            outExeclService.exportAnalysisReports(historyDatas, ipAddr);
        } catch (Exception e) {
            return ToJsonUtil.buildJsonRs(false, "失败", "");
        }
        return ToJsonUtil.buildJsonRs(true, "成功", ipAddr);

    }


    @RequestMapping("/exportHisReports")  //新写
    @ResponseBody
    public JSONObject exportHisReports(String workpieceId, String workpieceName, String userId, String workpieceNo,
                                       String beginTime, String endTime, String isPass, String ipAddr, HttpServletResponse response) throws Exception {

        List<MapEntity> historyDatas = historyDao.getHistoryDetails(workpieceId, workpieceName, userId, workpieceNo,
                beginTime, endTime, isPass);

        int a = 0;


        for (MapEntity historyData : historyDatas) {
            String json = historyData.get("sum_values").toString();
            List<String> dataList = JSON.parseArray(json, String.class);
            double maxValue = 0;
            double maxDisplace = 0;
            for (String data : dataList) {
                String[] values = data.split(",");
                double valueOf = Double.parseDouble(values[0]);
                double valueOf1 = Double.parseDouble(values[1]);

                if (maxValue < valueOf1) {
                    maxValue = valueOf1;
                    maxDisplace = valueOf;
                }
            }
            historyData.put("maxDisplace", maxDisplace);
            historyData.put("sum_values", maxValue);

        }


        System.out.println(ipAddr);
        try {
            outExeclService.exportAnalysisReports1(historyDatas, ipAddr);
        } catch (Exception e) {
            e.printStackTrace();
            return ToJsonUtil.buildJsonRs(false, "失败", "");
        }
        return ToJsonUtil.buildJsonRs(true, "成功", ipAddr);
    }


    @RequestMapping("/exportFaultsReports")
    @ResponseBody
    public JSONObject exportFaultsReports(String workpieceId, String userId, String classes, String beginTime,
                                          String endTime, String ipAddr) throws Exception {
        List<MapEntity> selectFaults = productionDao.selectFaults(workpieceId, userId, classes,
                beginTime, endTime);

        System.out.println(ipAddr);
        try {
            outExeclService.exportFaultsReports(selectFaults, ipAddr);
        } catch (Exception e) {
            return ToJsonUtil.buildJsonRs(false, "失败", "");
        }
        return ToJsonUtil.buildJsonRs(true, "成功", ipAddr);
    }


    @RequestMapping("/exportProReports")
    @ResponseBody
    public JSONObject exportProReports(String workpieceId, String userId, String classes, String beginTime,
                                       String endTime, String ipAddr) throws Exception {
        List<MapEntity> historyDatas = productionDao.selectProduction(workpieceId, userId, classes,
                beginTime, endTime);

        System.out.println(
                ipAddr);
        try {
            outExeclService.exportProductionsReports(historyDatas, ipAddr);
        } catch (Exception e) {
            e.printStackTrace();
            return ToJsonUtil.buildJsonRs(false, "失败", "");
        }
        return ToJsonUtil.buildJsonRs(true, "成功", ipAddr);
    }


}
