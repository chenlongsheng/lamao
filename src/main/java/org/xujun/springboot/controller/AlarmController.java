/**
 *
 */
package org.xujun.springboot.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.xujun.springboot.dao.HistoryDao;
import org.xujun.springboot.model.MapEntity;
import org.xujun.springboot.service.AlarmService;
import org.xujun.springboot.threadPool.Data485;
import org.xujun.springboot.threadPool.MyThread485;
import org.xujun.springboot.threadPool.TimeThread;
import org.xujun.springboot.websoket.utils.ToJsonUtil;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

/**
 * @author admin
 */

@RestController
@CrossOrigin(origins = "*")
public class AlarmController {

	@Resource
	AlarmService alarmService;

	@Resource
	HistoryDao historyDao;

	/*
	 *
	 * 获取历史报警
	 */
	@RequestMapping(value = { "selectAlarmLog" })
	public JSONObject selectAlarmLog(@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {

		PageInfo<MapEntity> selectAlarmLog = alarmService.selectAlarmLog(pageNo, pageSize);
		return ToJsonUtil.buildJsonRs(true, "获取历史报警", selectAlarmLog);
	}

	/*
	 *
	 * 获取实时报警
	 */
	@RequestMapping(value = { "selectRealLog" })
	public JSONObject selectRealLog() {

		return ToJsonUtil.buildJsonRs(true, "获取实时报警", alarmService.selectRealLog());
	}

	/*
	 * 解除硬件报警
	 *
	 */
	@RequestMapping(value = { "removeWarning" })
	public JSONObject removeWarning(String id) {

		historyDao.updateRealLog("0", "", "", id);// 解除蜂鸣

		MyThread485.write(new byte[] { 'F', 'O', '$' });// 解除报警报警声音（灯依然红灯）

		return ToJsonUtil.buildJsonRs(true, "发送解除报警", "");
	}

	/*
	 *
	 * 发送硬件开始校验
	 */
	@RequestMapping(value = { "send/adjusting" })
	public JSONObject adjusting(Integer startEnd) {

		if (startEnd == 1) {// 開啟校準校準
			MyThread485.write(new byte[] { 'J', '1', '$' });
			new TimeThread(new byte[] { 'J', '1', '$' }).start();// 再發一條
			Data485.adjustingStartEnd = startEnd; // 赋值
			return ToJsonUtil.buildJsonRs(true, "开始校验", "");

		} else if (startEnd == 0) {// 校驗結束
			MyThread485.write(new byte[] { 'J', '0', '$' });
			new TimeThread(new byte[] { 'J', '0', '$' }).start();// 再發一條
			Data485.adjustingStartEnd = startEnd; // 赋值
			return ToJsonUtil.buildJsonRs(true, "结束校验", "");
		}

		else if (startEnd == 10) {// 正常打枪

			Data485.testState = startEnd; // 赋值
			return ToJsonUtil.buildJsonRs(true, "正常状态", "");
		}

		else if (startEnd == 11) {// 开启测试

			Data485.testState = startEnd; // 赋值
			return ToJsonUtil.buildJsonRs(true, "开启测试", "");
		}

		return ToJsonUtil.buildJsonRs(true, "", "");

	}

	/*
	 *
	 * 校准数据存入数据库
	 */
	@RequestMapping(value = { "modifyAdjustData" })
	public JSONObject modifyAdjustData(double coefficient) {

		try {
			alarmService.modifyCoefficient(coefficient + "");
			Data485.coefficient = coefficient; // 赋值校准数据
		} catch (Exception e) {
			return ToJsonUtil.buildJsonRs(false, "", "");
		}
		return ToJsonUtil.buildJsonRs(true, "", "");
	}

}
