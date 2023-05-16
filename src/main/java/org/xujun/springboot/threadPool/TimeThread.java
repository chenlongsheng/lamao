/**
 * 
 */
package org.xujun.springboot.threadPool;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author admin
 *
 */
public class TimeThread extends Thread {

	public byte[] byt;

	public static boolean isStop = true;

	/**
	 * @param byt
	 */
	public TimeThread(byte[] byt) {
		super();
		this.byt = byt;
	}

	public void run() {

		try {
			TimeUnit.MILLISECONDS.sleep(1000);
		} catch (InterruptedException e) {
			// TODO 记日志
			e.printStackTrace();
		}
		if (isStop) {

			MyThread485.write(byt);

		}
		isStop = true;

	}

}
