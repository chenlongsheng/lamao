/**
 *
 */
package org.xujun.springboot.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xujun.springboot.dao.HistoryDao;

import org.xujun.springboot.websoket.utils.ToJsonUtil;

import com.alibaba.fastjson.JSONObject;

@RestController
@CrossOrigin(origins = "*")
public class VirtualKeyboardController {

//	接口执行前，先判断进程是否已经执行
//	/bin/ps  -C  florence  1>/dev/null
//	返回值如果是0，则进程运行中，再调用 显示或移动命令；
//	否则没有运行，需要先启动进程，启动后，如果是显示接口，则不需要再调用/usr/bin/florence  show
//	String[] cmds = {"/bin/sh", "-c", " 第一条命令 && 第二条命令"};
//	Process pro = Runtime.getRuntime().exec(cmds);

    @RequestMapping(value = {"keyboardFlorence"})
    public JSONObject keyboardFlorence(String name) throws IOException {
//        String[] cmds = new String[]{"/usr/bin/nohup", "/usr/bin/onboard &&  1>/dev/null && 2>/dev/null"};
        String[] cmds = new String[] {"/usr/bin/nmcli device connect wlan0"};

        try {
//			Process pcs = Runtime.getRuntime().exec("/usr/bin/florence " + name);
            System.out.println(Arrays.toString(cmds));
            Process pcs = Runtime.getRuntime().exec(cmds);
            BufferedReader in = new BufferedReader(new InputStreamReader(pcs.getInputStream(), "utf-8"));
            String line = "";
            while ((line = in.readLine()) != null) {
                System.out.println("第一多种收到的:  " + line);
            }
            int waitFor = pcs.waitFor();
            System.out.println("waitFor = " + waitFor);

        } catch (Exception e) {
            e.printStackTrace();
            return ToJsonUtil.buildJsonRs(false, "失败", "");
        }
        return ToJsonUtil.buildJsonRs(true, "成功", "");
    }


    @RequestMapping(value = {"keyboardFlorence1"})
    public JSONObject keyboardFlorence1(String name) throws IOException {
//        String cmds = "/usr/bin/onboard";

        String[] arguments = new String[] { "sudo /usr/bin/onboard" };
        try {
//			Process pcs = Runtime.getRuntime().exec("/usr/bin/florence " + name);
//            System.out.println(Arrays.toString(cmds));

            System.out.println("发送---");
            Process pcs = Runtime.getRuntime().exec(arguments);

            BufferedReader in = new BufferedReader(new InputStreamReader(pcs.getInputStream(), "utf-8"));

            String line = "";
            while ((line = in.readLine()) != null) {
                System.out.println("wifi收到一个的:  " + line);
            }
            int waitFor = pcs.waitFor();
            System.out.println("waitFor = " + waitFor);

        } catch (IOException e) {
            System.out.println("因为 IOException 执行命令失败：" + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("在等待进程完成时被中断：" + e.getMessage());
        }
        return ToJsonUtil.buildJsonRs(true, "成功", "");
    }

    @RequestMapping(value = {"getRun"})
    public JSONObject getRun(String name) throws IOException {
        try {
            Process process = Runtime.getRuntime().exec("<command>");
            process.waitFor();
        } catch (IOException e) {
            System.out.println("因为 IOException 执行命令失败：" + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("在等待进程完成时被中断：" + e.getMessage());
        }
        System.out.println("成功===");
        return ToJsonUtil.buildJsonRs(true, "成功", "");
    }

}