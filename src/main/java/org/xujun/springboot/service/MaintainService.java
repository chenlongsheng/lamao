/**
 * 
 */
package org.xujun.springboot.service;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.xujun.springboot.dao.HistoryDao;
import org.xujun.springboot.dao.MaintainDao;
import org.xujun.springboot.dao.UserPDao;
import org.xujun.springboot.model.MapEntity;
import org.xujun.springboot.threadPool.MyThread485;
import org.xujun.springboot.websoket.MyWebSocket;
import org.xujun.springboot.websoket.utils.ToJsonUtil;

/**
 * @author admin
 *
 */

@Component
public class MaintainService {

	@Resource
	private MaintainDao mainTainDao;

	@Resource
	private HistoryDao historyDao;

	public void updateOperation(String mandrel, String hydraulicOil, String repair) {

		mainTainDao.updateOperation(mandrel, hydraulicOil, repair);

	}

	public void updateConfig(String di1, String di2, String inputConfig) {

		mainTainDao.updateConfig(di1, di2, inputConfig);

		try {
			MyWebSocket.sendInfo(ToJsonUtil.buildJsonRs(true, "selectIsOpen", historyDao.selectIsOpen()).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public MapEntity selectMainTain() {

		return mainTainDao.selectMainTain();
	}

	public MapEntity selectConfig() {

		return mainTainDao.selectConfig();
	}

	public void updataTaintain(String mandrelReal, String hydraulicOilReal, String repairReal) {

		if (mandrelReal != null && mandrelReal != "") {
			mainTainDao.insertLog("3");// 解除报警

		} else if (hydraulicOilReal != null && hydraulicOilReal != "") {
			mainTainDao.insertLog("4");

		} else if (repairReal != null && repairReal != "") {
			mainTainDao.insertLog("5");
		}

		mainTainDao.updataTaintain(mandrelReal, hydraulicOilReal, repairReal);

		if (mainTainDao.selectMainTain().get("alarmState").toString().equals("0")) { // 没有报警后发送恢复正常
			MyThread485.write(new byte[] { 'O', 'K', '$' });
		}

	}

	public MapEntity selectPrintConfig() {

		return mainTainDao.selectPrintConfig();
	}

	public void updatePrint(String deviceNo, String isprint) {

		mainTainDao.updatePrint(deviceNo, isprint);

	}
}
