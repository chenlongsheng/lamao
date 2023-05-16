/**
 *
 */
package org.xujun.springboot.service;

import java.util.List;

import javax.annotation.Resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xujun.springboot.dao.AlarmDao;

import org.xujun.springboot.model.MapEntity;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.xujun.springboot.service.impl.IAlamService;

/**
 * @author admin
 */

@Component
public class AlarmService implements IAlamService {


    @Resource
    private AlarmDao alarmDao;

        public List<MapEntity> selectRealLog() {

        return alarmDao.selectRealLog();
    }

    public PageInfo<MapEntity> selectAlarmLog(int pageNo, int pageSize) {

        PageHelper.startPage(pageNo, pageSize);
        List<MapEntity> selectAlarmLog = alarmDao.selectAlarmLog();

        PageInfo<MapEntity> pageInfo = new PageInfo<>(selectAlarmLog);
        return pageInfo;
    }

    public void modifyCoefficient(String coefficient) {


        alarmDao.modifyCoefficient(coefficient);

    }

}
