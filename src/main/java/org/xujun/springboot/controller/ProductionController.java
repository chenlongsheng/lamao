/**
 *
 */
package org.xujun.springboot.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.xujun.springboot.model.MapEntity;
import org.xujun.springboot.service.ProductionService;
import org.xujun.springboot.websoket.utils.ToJsonUtil;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

/**
 * @author admin
 *
 */

@RestController
@CrossOrigin(origins = "*")
public class ProductionController {

    @Resource
    ProductionService productionService;


    @RequestMapping(value = {"selectProduction"})
    public JSONObject selectProduction(String workpieceId, String userId, String classes, String beginTime,
                                       String endTime, Integer pageNo) {

        PageInfo<MapEntity> selectProduction = productionService.selectProduction(workpieceId, userId, classes,
                beginTime, endTime, pageNo);

        return ToJsonUtil.buildJsonRs(true, "生产数据报表", selectProduction);
    }


    @RequestMapping(value = {"deleteHistoryByIds"})
    public JSONObject deleteHistoryByIds(String ids) {

        try {

            productionService.deleteHistoryByIds(ids);
        } catch (Exception e) {
            e.printStackTrace();

            return ToJsonUtil.buildJsonRs(false, "删除生产数据报表失败", "");
        }
        return ToJsonUtil.buildJsonRs(true, "删除生产数据报表", "");
    }

    @RequestMapping(value = {"selectFaults"})
    public JSONObject selectFaults(String workpieceId, String userId, String classes, String beginTime,
                                   String endTime, Integer pageNo) {

        PageInfo<MapEntity> selectFaults = productionService.selectFaults(workpieceId, userId, classes,
                beginTime, endTime, pageNo);

        return ToJsonUtil.buildJsonRs(true, "不良分析报表", selectFaults);

    }

    @RequestMapping(value = {"rankingRatios"})
    public JSONObject rankingRatios() {

        return ToJsonUtil.buildJsonRs(true, "员工效率排行榜", productionService.rankingRatios());
    }


}
