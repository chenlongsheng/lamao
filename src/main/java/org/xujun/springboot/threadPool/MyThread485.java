/**
 *
 */
package org.xujun.springboot.threadPool;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.xujun.springboot.dao.HistoryDao;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

/**
 * @author admin
 *
 */
public class MyThread485 implements Runnable {

//	private static AsyncServiceImpl asyncService;

    private static HistoryDao historyDao;

    public static boolean isStop = true;

    public static BlockingQueue<Object> devAlarmQueue = new LinkedBlockingQueue<Object>();

    public static CommPortIdentifier com15 = null;// 定义需要接收数据的端口(未打开)
    public static SerialPort serialCom15 = null;// 打开的端口

    /**
     * @param historyDao
     */
    public MyThread485(HistoryDao historyDao) {
        super();
        this.historyDao = historyDao;
    }

    @Override
    public void run() {

        InputStream inputStream = null;// 端口输入流

        System.out.println("开始监听端口收到的数据");

        listPortChoices();

        try {
            // 获取并打开串口com15

            String property = System.getProperty("serialport");
            System.out.println("输入的serialport: " + property);
            if (property == null || property == "") {
                property = "/dev/ttyS0";
            }
            String windowsPro = System.getProperty("os.name");

            if (windowsPro.contains("Windows")) {

                com15 = CommPortIdentifier.getPortIdentifier("COM5"); // 本地
            } else {
                com15 = CommPortIdentifier.getPortIdentifier(property); // 生产
            }

            serialCom15 = (SerialPort) com15.open("Com4Listener", 5000);
            serialCom15.setSerialPortParams(115200, SerialPort.DATABITS_8, serialCom15.STOPBITS_1,
                    serialCom15.PARITY_NONE);
            // 获取串口的输入流对象
            inputStream = serialCom15.getInputStream();
            // 从串口读出数据
            // 定义用于缓存读入数据的数组
            byte[] cache = new byte[10240];
            // 记录已经到达串口COM15且未被读取的数据的字节（Byte）数
            int availableBytes = 0;
            // 通过无限循环的方式来扫描COM15,检查是否有数据到达端口,间隔20ms

            while (isStop) {
                // 获取串口15收到的字节数
                availableBytes = inputStream.available();
                // 如果可用字节数大于0则开始转换并获取数据
                StringBuffer data = new StringBuffer("");

                while (availableBytes > 0) {
                    // 从串口的输入流对象中读入数据并将数据存放到缓存数组中
                    inputStream.read(cache);
                    // 将获取到的数据进行转码并输出
                    for (int i = 0; i < cache.length && i < availableBytes; i++) {
//						System.out.println("原始收到的:" + (char) cache[i]);
                        if ((char) cache[i] == '$') {
                            devAlarmQueue.put(data);
                            data = new StringBuffer("");
                        } else {
                            data.append(Character.toString((char) cache[i]));
                        }
                    }
                    availableBytes = inputStream.available();
                }
                // 线程睡眠20ms
                Thread.sleep(20);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedCommOperationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchPortException e) {
            // 找不到串口的情况下抛出该异常
            e.printStackTrace();
            System.out.println("串口连接失败!!!");
        } catch (PortInUseException e) {
            // 如果因为端口被占用而导致打开失败，则抛出该异常
            e.printStackTrace();
            System.out.println("串口占用!!!");
        } catch (IOException e) {
            // 如果获取输出流失败，则抛出该异常
            e.printStackTrace();
        }
    }

    /*
     *
     * 发送给硬件方法
     */
    public static void write(byte[] byt) {

        try {
            // 写入数据
            // 获取串口输出流对象
            OutputStream outputStream = serialCom15.getOutputStream();
            // 通过串口的输出流向串口写数据

            outputStream.write(byt);

            System.out.println("发送给硬件的消息: " + new String(byt));
            /*
             * 使用输出流往串口写数据的时候必须将数据转换为byte数组格式或int格式， 当另一个串口接收到数据之后再根据双方约定的规则，对数据进行解码
             */
            // 关闭输出流
            outputStream.flush();
            outputStream.close();
            // 关闭串口
        } catch (IOException e) {
            // 如果获取输出流失败，则抛出该异常
            e.printStackTrace();
        }

    }

    /**
     * @return the historyDao
     */
    public HistoryDao getHistoryDao() {
        return historyDao;
    }

    /**
     * @param historyDao the historyDao to set
     */
    public void setHistoryDao(HistoryDao historyDao) {
        this.historyDao = historyDao;
    }

    public static void listPortChoices() {// 讀取所有串口
        CommPortIdentifier portId;
        Enumeration en = CommPortIdentifier.getPortIdentifiers();
        // iterate through the ports.
        while (en.hasMoreElements()) {
            portId = (CommPortIdentifier) en.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                System.out.println("系统读取串口列表:" + portId.getName());
            }
        }
    }

//    

}