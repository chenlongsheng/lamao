/**
 * 
 */
package org.xujun.springboot.threadPool;


import gnu.io.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
/**
 * @author admin
 *
 */
public class RS485Demo extends Thread implements SerialPortEventListener{
	
	
	public static void main(String[] args) {
		
	}

//    封装十六进制的打开、关闭命令
    private static final List<byte[]> onOrderList = Arrays.asList(
            new byte[]{0x01, 0x05, 0x00, 0x00, (byte) 0xFF, 0x00, (byte) 0x8C, 0x3A},       new byte[]{0x01, 0x05, 0x00, 0x01, (byte) 0xFF, 0x00, (byte) 0xDD, (byte)0xFA},
            new byte[]{0x01, 0x05, 0x00, 0x02, (byte) 0xFF, 0x00, (byte) 0x2D, (byte)0xFA}, new byte[]{0x01, 0x05, 0x00, 0x03, (byte) 0xFF, 0x00, (byte) 0x7C, 0x3A},
            new byte[]{0x01, 0x05, 0x00, 0x04, (byte) 0xFF, 0x00, (byte) 0xCD,(byte) 0xFB}, new byte[]{0x01, 0x05, 0x00, 0x05, (byte) 0xFF, 0x00, (byte) 0x9C, 0x3B},
            new byte[]{0x01, 0x05, 0x00, 0x06, (byte) 0xFF, 0x00, (byte) 0x6C, 0x3B},       new byte[]{0x01, 0x05, 0x00, 0x07, (byte) 0xFF, 0x00,  0x3D, (byte)0xFB});
    private static final List<byte[]> offOrderList = Arrays.asList(
            new byte[]{0x01, 0x05, 0x00, 0x00,  0x00, 0x00, (byte) 0xCD, (byte)0xCA},new byte[]{0x01, 0x05, 0x00, 0x01,  0x00, 0x00, (byte) 0x9C, (byte)0x0A},
            new byte[]{0x01, 0x05, 0x00, 0x02,  0x00, 0x00, (byte) 0x6C, (byte)0x0A},new byte[]{0x01, 0x05, 0x00, 0x03,  0x00, 0x00, (byte) 0x3D, (byte)0xCA},
            new byte[]{0x01, 0x05, 0x00, 0x04,  0x00, 0x00, (byte) 0x8C, (byte)0x0B},new byte[]{0x01, 0x05, 0x00, 0x05,  0x00, 0x00, (byte) 0xDD, (byte)0xCB},
            new byte[]{0x01, 0x05, 0x00, 0x06,  0x00, 0x00, (byte) 0x2D, (byte)0xCB},new byte[]{0x01, 0x05, 0x00, 0x07,  0x00, 0x00, (byte) 0x7C, (byte)0x0B});

    // 监听器,这里独立开辟一个线程监听串口数据
// 串口通信管理类
    static CommPortIdentifier portId;
    static RS485Demo cRead = null;
    //USB在主机上的通信端口名称，如：COM1、COM2等
    static String COMNUM = "";

    static Enumeration<?> portList;
    InputStream inputStream; // 从串口来的输入流
    static OutputStream outputStream;// 向串口输出的流
    static SerialPort serialPort; // 串口的引用
    // 堵塞队列用来存放读到的数据
    private BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>();

    /**
     * SerialPort EventListene 的方法,持续监听端口上是否有数据流
     */
    public void serialEvent(SerialPortEvent event) {

        switch (event.getEventType()) {
            case SerialPortEvent.BI:
            case SerialPortEvent.OE:
            case SerialPortEvent.FE:
            case SerialPortEvent.PE:
            case SerialPortEvent.CD:
            case SerialPortEvent.CTS:
            case SerialPortEvent.DSR:
            case SerialPortEvent.RI:
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                break;
            case SerialPortEvent.DATA_AVAILABLE:// 当有可用数据时读取数据
                byte[] readBuffer = null;
                int availableBytes = 0;
                try {
                    availableBytes = inputStream.available();
                    while (availableBytes > 0) {
                        readBuffer = RS485Demo.readFromPort(serialPort);
                        String needData = printHexString(readBuffer);
                        System.out.println(new Date() + "真实收到的数据为：-----" + needData);
                        availableBytes = inputStream.available();
                        msgQueue.add(needData);
                    }
                } catch (IOException e) {
                }
            default:
                break;
        }
    }

    /**
     * 从串口读取数据
     *
     * @param serialPort 当前已建立连接的SerialPort对象
     * @return 读取到的数据
     */
    public static byte[] readFromPort(SerialPort serialPort) {
        InputStream in = null;
        byte[] bytes = {};
        try {
            in = serialPort.getInputStream();
            // 缓冲区大小为一个字节
            byte[] readBuffer = new byte[1];
            int bytesNum = in.read(readBuffer);
            while (bytesNum > 0) {
                bytes = concat(bytes, readBuffer);
                bytesNum = in.read(readBuffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                    in = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    

    /** 
     * 关闭串口 
     *  
     * @param serialport 
     *            待关闭的串口对象 
     */ 
    public static void closePort(SerialPort serialPort) { 
        if (serialPort != null) { 
            serialPort.close(); 
          
        } 
    } 
 
   

    // 16转10计算
    public long getNum(String num1, String num2) {
        long value = Long.parseLong(num1, 16) * 256 + Long.parseLong(num2, 16);
        return value;
    }

    // 字节数组转字符串
    private String printHexString(byte[] b) {
        StringBuffer sbf = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sbf.append(hex.toUpperCase() + "  ");
        }
        return sbf.toString().trim();
    }

    /**
     * 合并数组
     *
     * @param firstArray  第一个数组
     * @param secondArray 第二个数组
     * @return 合并后的数组
     */
    public static byte[] concat(byte[] firstArray, byte[] secondArray) {
        if (firstArray == null || secondArray == null) {
            if (firstArray != null)
                return firstArray;
            if (secondArray != null)
                return secondArray;
            return null;
        }
        byte[] bytes = new byte[firstArray.length + secondArray.length];
        System.arraycopy(firstArray, 0, bytes, 0, firstArray.length);
        System.arraycopy(secondArray, 0, bytes, firstArray.length, secondArray.length);
        return bytes;
    }

    
	}


