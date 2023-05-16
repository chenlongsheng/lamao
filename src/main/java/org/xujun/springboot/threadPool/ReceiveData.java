/**
* 
*/
package org.xujun.springboot.threadPool;

import java.io.IOException;

import org.xujun.springboot.dao.HistoryDao;
import org.xujun.springboot.websoket.MyWebSocket;
import org.xujun.springboot.websoket.utils.ToJsonUtil;
 

/**
 * @author admin
 *
 */
public class ReceiveData {

	public static int checkAF = 0;

	public static void machineData(String data, HistoryDao historyDao) throws IOException {

		if (data.contains("AF1")) { // 气压过高报警

			historyDao.updateRealLog("1", "1", "1", "2");

			checkAF = 2;

		} else if (data.contains("AF2")) { // 气压过低报警

			historyDao.updateRealLog("1", "1", "1", "1");

			checkAF = 1;

		} else if (data.contains("AF3")) { // 气压过高报警 断气源报警

			MyWebSocket.sendInfo(ToJsonUtil.buildJsonRs(true, "AF3", "").toString());
			historyDao.updateRealLog("1", "1", "1", "7");
			checkAF = 7;

		} else if (data.contains("AF4")) { // 气压过低报警 断气源报警
			MyWebSocket.sendInfo(ToJsonUtil.buildJsonRs(true, "AF4", "").toString());
			historyDao.updateRealLog("1", "1", "1", "6");

			checkAF = 6;

		}else if (data.equals("AFC")) { // 气压正常
			
			MyWebSocket.sendInfo(ToJsonUtil.buildJsonRs(true, "AFC", "").toString());
			
			historyDao.updateRealLog("0", "1", "0", "1,2,6,7");
			historyDao.insertLog(checkAF + "");

		}else if (data.contains("I11")) { // 输入in1
			
			historyDao.updateRealTcongfig("1", null);

			MyWebSocket.sendInfo(ToJsonUtil.buildJsonRs(true, "selectIsOpen", historyDao.selectIsOpen()).toString());

		} else if (data.contains("I10")) { // 关闭in1
			
			historyDao.updateRealTcongfig("0", null);

			MyWebSocket.sendInfo(ToJsonUtil.buildJsonRs(true, "selectIsOpen", historyDao.selectIsOpen()).toString());
			

		} else if (data.contains("I21")) { // 输入in2
			
			historyDao.updateRealTcongfig(null, "1");

			MyWebSocket.sendInfo(ToJsonUtil.buildJsonRs(true, "selectIsOpen", historyDao.selectIsOpen()).toString());

		} else if (data.contains("I20")) { // 关闭in2
			
			historyDao.updateRealTcongfig(null, "0");
			MyWebSocket.sendInfo(ToJsonUtil.buildJsonRs(true, "selectIsOpen", historyDao.selectIsOpen()).toString());
		}

	}

	
}
