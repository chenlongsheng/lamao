/**
 *
 */
package org.xujun.springboot.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xujun.springboot.dao.HistoryDao;
import org.xujun.springboot.dao.UserPDao;
import org.xujun.springboot.model.MapEntity;
import org.xujun.springboot.service.impl.IHistoryService;
import org.xujun.springboot.threadPool.MyThread485;
import org.xujun.springboot.websoket.utils.ToJsonUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author admin
 */
@Component
public class HistoryService implements IHistoryService {

    @Resource
    private HistoryDao historyDao;

    public PageInfo<MapEntity> getHistoryDatas(String workpieceId, String workpieceName, String userId,
                                               String workpieceNo, String beginTime, String endTime, String isPass, int pageNo) {

        PageHelper.startPage(pageNo, 10);

        List<MapEntity> historyDatas = historyDao.getHistoryDatas(workpieceId, workpieceName, userId, workpieceNo,
                beginTime, endTime, isPass);

        PageInfo<MapEntity> pageInfo = new PageInfo<>(historyDatas);

        return pageInfo;

    }



    public List<MapEntity> getHistoryDataByWorkpieceNo(String workpieceNo) {

        List<MapEntity> historyDataByWorkpieceNo = historyDao.getHistoryDataByWorkpieceNo(workpieceNo);

        for (MapEntity mapEntity : historyDataByWorkpieceNo) {

            String sumValues = mapEntity.get("sumValues").toString();

            String[] sumValuesT = maxvalues(sumValues);

            mapEntity.put("maxX", sumValuesT[0]);

            mapEntity.put("maxY", sumValuesT[1]);

            mapEntity.put("sumValues", "");
        }

        return historyDataByWorkpieceNo;

    }

    public static String[] maxvalues(String sumValues) {

        List<String> dataList = JSON.parseArray(sumValues, String.class);

        double x = 0;
        double y = 0;

        for (int i = 0; i < dataList.size(); i++) {

            Double.parseDouble(dataList.get(i).split(",")[0]);
            Double.parseDouble(dataList.get(i).split(",")[1]);

            if (x < Double.parseDouble(dataList.get(i).split(",")[0])) {
                x = Double.parseDouble(dataList.get(i).split(",")[0]);
            }

            if (y < Double.parseDouble(dataList.get(i).split(",")[1])) {
                y = Double.parseDouble(dataList.get(i).split(",")[1]);
            }

        }

        System.out.println("数组" + x + "," + y);

        return new String[]{x + "", y + ""};

    }

    public MapEntity getHistoryDataById(String id) {

        return historyDao.getHistoryDataById(id);

    }

    public void deleteHistoryById(String userId, String workpieceNo) {

        historyDao.deleteHistoryById(userId, workpieceNo);
    }

    public void getRatio(String userId) {

        historyDao.getRatio("2", userId);

    }

    public void createHistoryEvent(String day) {

        historyDao.createHistoryEvent(day);

    }



}
