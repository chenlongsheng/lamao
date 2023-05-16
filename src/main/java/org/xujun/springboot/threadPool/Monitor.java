/**
 * 
 */
package org.xujun.springboot.threadPool;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.xujun.springboot.dao.HistoryDao;
import org.xujun.springboot.task.AsyncServiceImpl;

/**
 * @author admin
 *
 */
public class Monitor  extends Thread {
	

    private Thread monitoredThread; // 要监控的线程名
    
    AsyncServiceImpl asyncService;
 
	  HistoryDao historyDao;

    public Monitor(Thread monitoredThread,AsyncServiceImpl asyncService,  HistoryDao historyDao) {
    	 this.monitoredThread = monitoredThread;
        this.asyncService = asyncService;
        this.historyDao = historyDao;
	}

    public void run() {
        while (true) {
        	
            monitor();
            try {
                TimeUnit.MILLISECONDS.sleep(10000);
            } catch (InterruptedException e) {
                // TODO 记日志
                e.printStackTrace();
            }
        }

    }

    /**
     * 监控主要逻辑
     */
    private void monitor() {
        
            Thread.State state = monitoredThread.getState(); // 获得指定线程状态

             System.out.println(monitoredThread.getName() + "监控线程状态是:"
                   + monitoredThread.getState());

            /*
             * 如果监控线程为终止状态,则重启监控线程
             */
            if (Thread.State.TERMINATED.equals(state)) {
                System.out.println(monitoredThread.getName()
                        + "监控线程已经终止,现在开始重启监控线程!");
                
//              monitoredThread = new Thread(new MyThread485(historyDao));                
                asyncService.executeAsync(monitoredThread);
            }
        
    }
	
	
	
	

}
