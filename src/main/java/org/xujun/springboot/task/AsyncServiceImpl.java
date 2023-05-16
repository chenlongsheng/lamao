/**
 * 
 */
package org.xujun.springboot.task;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.xujun.springboot.dao.HistoryDao;
import org.xujun.springboot.service.ThreadInitService;
import org.xujun.springboot.threadPool.MyThread485;

/**
 * @author admin
 *
 */	
	@Service
	public class AsyncServiceImpl{
		

		 @Resource
		  HistoryDao historyDao;
 
		 

		@Async("asyncServiceExecutor")
	    public  void executeAsync(Thread thread) {		
			System.out.println("000000");
			
//			Thread thread =   new Thread(new MyThread485(historyDao));
			ThreadInitService.thread = thread;
			
			thread.start();
						
			
			
	       
	    }
		
		
		
	}
 
