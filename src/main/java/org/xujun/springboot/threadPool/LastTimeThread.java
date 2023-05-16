package org.xujun.springboot.threadPool;

import org.xujun.springboot.dao.HistoryDao;

public class LastTimeThread extends Thread {


    public HistoryDao historyDao;

    public LastTimeThread(HistoryDao historyDao) {

        this.historyDao = historyDao;
    }


    @Override
    public void run() {
        while (true) {
            long currentTime = System.currentTimeMillis();

            try {
                if (currentTime - Data485.lastDataReceivedTime > 200) {
                    Data485.checkData("&&&", historyDao);
                    break;
                }
              //  System.out.println("===============109");
                    Thread.sleep(109);
                } catch(Exception e){
                    e.printStackTrace();
                }
            }

    }

}
