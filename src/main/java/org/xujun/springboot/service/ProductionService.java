/**
 *
 */
package org.xujun.springboot.service;

import java.util.List;

import javax.annotation.Resource;


import org.springframework.stereotype.Component;
import org.xujun.springboot.dao.ProductionDao;
import org.xujun.springboot.dao.UserPDao;
import org.xujun.springboot.model.MapEntity;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author admin
 *
 */

@Component
public class ProductionService {

    @Resource
    private ProductionDao productionDao;


    public PageInfo<MapEntity> selectProduction(String workpieceId, String userId,
                                                String classes, String beginTime, String endTime, int pageNo) {

        PageHelper.startPage(pageNo, 10);

        List<MapEntity> historyDatas = productionDao.selectProduction(workpieceId, userId, classes,
                beginTime, endTime);

        PageInfo<MapEntity> pageInfo = new PageInfo<>(historyDatas);

        return pageInfo;

    }


    public void deleteHistoryByIds(String ids) {

        productionDao.deleteHistoryByIds(ids);

    }


    public PageInfo<MapEntity> selectFaults(String workpieceId, String userId,
                                            String classes, String beginTime, String endTime, int pageNo) {

        PageHelper.startPage(pageNo, 10);

        List<MapEntity> selectFaults = productionDao.selectFaults(workpieceId, userId, classes,
                beginTime, endTime);

        PageInfo<MapEntity> pageInfo = new PageInfo<>(selectFaults);

        return pageInfo;

    }


    public List<MapEntity> rankingRatios() {


        return productionDao.rankingRatios();


    }

}
