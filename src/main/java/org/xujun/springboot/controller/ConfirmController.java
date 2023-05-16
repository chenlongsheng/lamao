/**
 *
 */
package org.xujun.springboot.controller;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xujun.springboot.dao.HistoryDao;
import org.xujun.springboot.threadPool.Data485;
import org.xujun.springboot.threadPool.MyThread485;
import org.xujun.springboot.websoket.utils.ToJsonUtil;

import com.alibaba.fastjson.JSONObject;

@RestController
@CrossOrigin(origins = "*")
public class ConfirmController {

    @Resource
    HistoryDao historyDao;

    @RequestMapping(value = {"confirmData"})
    public JSONObject confirmData(Integer state) throws IOException {

        if (state == 1) {
            Data485.savedata(historyDao, 1);
        }
        MyThread485.write(new byte[]{'O', 'K', '$'});// 开启阀门
        return ToJsonUtil.buildJsonRs(true, "确定成功", "");
    }

    @RequestMapping(value = {"startData"})
    public JSONObject startData(Integer state) {
        if (state == 1) {
            MyThread485.write(new byte[]{'O', 'K', '$'});// 启动阀门
        } else if (state == 0) {
            MyThread485.write(new byte[]{'D', 'J', '$'});// 异常关闭阀门
        }
        return ToJsonUtil.buildJsonRs(true, "启动/关闭成功", "");
    }

}