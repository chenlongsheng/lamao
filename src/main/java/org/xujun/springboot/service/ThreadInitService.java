/**
 * 
 */
package org.xujun.springboot.service;

import javax.annotation.Resource;

import org.xujun.springboot.dao.HistoryDao;
import org.xujun.springboot.task.AsyncServiceImpl;
import org.xujun.springboot.threadPool.Data485;

import org.xujun.springboot.threadPool.MyThread485;

/**
 * @author admin
 *
 */
public class ThreadInitService {

	@Resource
	HistoryDao historyDao;

	@Resource
	HistoryService historyService;

	@Resource
	AsyncServiceImpl asyncService;

	public static int online;

	public static Thread thread;

	public static HistoryDao hisDao;

	public void init() {

		System.out.println("55555");

		online = 0; // websocket 启动重连

		Data485.userId = "1";
		Data485.workpieceId = "1";

		new Thread(new MyThread485(historyDao)).start();
		hisDao = historyDao;

		new AlarmDataThread().start();

		Data485.entity = historyDao.getCountById("1", "1");

		// 置空
		Data485.count = Integer.parseInt(Data485.entity.get("count").toString());

		Data485.nutsNumber = Integer.parseInt(Data485.entity.get("nowNumber").toString());
		Data485.workpieceCount = Integer.parseInt(Data485.entity.get("workpieceCount").toString());

	}

	class AlarmDataThread extends Thread {

		public AlarmDataThread() {

		}

		public void run() {
			while (true) {
				Object devAlarm = null;
				try {
					devAlarm = MyThread485.devAlarmQueue.take();

					Data485.checkData(devAlarm.toString(), historyDao);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public ThreadInitService() {
		super();
//	        System.out.println("构造函数1");
	}

	public void destroy() {
//	        System.out.println("this is destroy method1");
	}

}
