/**
 * 
 */
package org.xujun.springboot.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.xujun.springboot.dao.AlarmDao;
import org.xujun.springboot.dao.HistoryDao;
import org.xujun.springboot.dao.UserPDao;
import org.xujun.springboot.model.MapEntity;
import org.xujun.springboot.service.ThreadInitService;
import org.xujun.springboot.service.UserPService;
import org.xujun.springboot.threadPool.Data485;
import org.xujun.springboot.threadPool.MyThread485;
import org.xujun.springboot.websoket.utils.ToJsonUtil;

import com.alibaba.fastjson.JSONObject;

/**
 * @author admin
 *
 */

@RestController
@CrossOrigin(origins = "*")
public class UserPController {

	@Resource
	UserPService userPService;

	@Resource
	UserPDao userPDao;

	@Resource
	HistoryDao historyDao;

	@Resource
	AlarmDao alarmDao;

	/*
	 * 
	 * 进入用户系统
	 */
	@RequestMapping(value = { "entrance" })
	public JSONObject entrance(String uId, String pieceId) throws IOException {

		MyThread485.isStop = true;

		ThreadInitService.online = 1; // 服务重启,已经重新登入,获取用户和工件号id

		Data485.userId = uId;
		Data485.workpieceId = pieceId;
		Data485.entity = historyDao.getCountById(pieceId, uId);

		Data485.count = Integer.parseInt(Data485.entity.get("count").toString()); // 刚进入获取件数
		Data485.nutsNumber = Integer.parseInt(Data485.entity.get("nowNumber").toString()); // 铆钉数
		Data485.workpieceCount = Integer.parseInt(Data485.entity.get("workpieceCount").toString());
		Data485.workpieceStatus = Integer.parseInt(Data485.entity.get("workpieceStatus").toString());
		Data485.coefficient = Double.parseDouble(Data485.entity.get("coefficient").toString());
		Data485.inputConfig = Integer.parseInt(Data485.entity.get("inputConfig").toString());

		return ToJsonUtil.buildJsonRs(true, "员工進入成功", Data485.entity);
	}

	@RequestMapping(value = { "exit" })
	public void exit() {
		
		Data485.status = 0;
		
		//MyThread485.write(new byte[] { 'D', 'J', '$' });// 异常关闭阀门				
	    //	new TimeThread(new byte[] { 'D', 'J', '$' }).start();// 再發一條
		// MyThread485.isStop = false;

	}

	@RequestMapping(value = { "getUserList" })
	public JSONObject getUserList() {

		return ToJsonUtil.buildJsonRs(true, "员工集合", userPService.getUsers());

	}

	@RequestMapping(value = { "addUser" })
	public JSONObject addUser(String name, String jobNo, String classes) {
		try {
			userPService.addUser(name, jobNo, classes);

		} catch (Exception e) {
			e.printStackTrace();
			return ToJsonUtil.buildJsonRs(false, "添加失败", "");
		}
		return ToJsonUtil.buildJsonRs(true, "添加成功", "");

	}

	@RequestMapping(value = { "updateUserById" })
	public JSONObject updateUserById(String name, String jobNo, String id, String classes) {
		try {
			userPService.updateUserById(name, jobNo, id, classes);
		} catch (Exception e) {
			return ToJsonUtil.buildJsonRs(false, "修改失败", "");
		}
		return ToJsonUtil.buildJsonRs(true, "修改成功", "");
	}

	@RequestMapping(value = { "deleteUserById" })
	public JSONObject deleteUserById(String id) {
		try {
			userPService.deleteUserById(id);
		} catch (Exception e) {
			return ToJsonUtil.buildJsonRs(false, "修改失败", "");
		}
		return ToJsonUtil.buildJsonRs(true, "修改成功", "");
	}

	// ==========================================
	@RequestMapping(value = { "checkPassword" })
	public JSONObject checkPassword(String password, String status) {

		System.out.println(status);

		try {
			List<MapEntity> passwordList = userPDao.getPassword(password, status);

			if (passwordList != null && passwordList.size() > 0) {

				return ToJsonUtil.buildJsonRs(true, "核查成功", userPDao.getPassword(password, status));
			} else {

				return ToJsonUtil.buildJsonRs(false, "核查失败", "");
			}

		} catch (Exception e) {
			return ToJsonUtil.buildJsonRs(false, "核查失败", "");
		}
	}

	@RequestMapping(value = { "updatePass" })
	public JSONObject updatePass(String oldPassword, String password, String ip, String status) {

		if (status == null) {
			status = "1";
		}
		try {
			List<MapEntity> checkOldPassword = userPService.getPassword(oldPassword, status);

			if (checkOldPassword == null || checkOldPassword.size() == 0) {

				return ToJsonUtil.buildJsonRs(false, "旧密码错误", 0);
			}
			userPDao.updatePass(password, ip, status);
		} catch (Exception e) {
			e.printStackTrace();
			return ToJsonUtil.buildJsonRs(true, "修改失败", null);
		}
		return ToJsonUtil.buildJsonRs(true, "修改成功", null);

	}

	
	@RequestMapping(value = { "getOperation" })
	public JSONObject getOperation() {

		return ToJsonUtil.buildJsonRs(true, "配置获取", userPDao.getOperation());

	}

	@RequestMapping(value = { "updateOperation" })
	public JSONObject updateOperation(String warningCount, String process, String pressure) {

		try {
			userPService.updateOperation(warningCount, process, pressure);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ToJsonUtil.buildJsonRs(true, "修改配置", "");
	}

	@RequestMapping(value = { "finishWorkpiece" })
	public JSONObject finishWorkpiece() {

		try {
			Data485.addWorkpieceNum();

			Data485.finishWorkpieceNum(historyDao);

		} catch (IOException e) {
			e.printStackTrace();
			return ToJsonUtil.buildJsonRs(false, "", null);
		}

		return ToJsonUtil.buildJsonRs(true, "完成工件", null);

	}

	/*
	 * 
	 * 
	 * 输入工件号
	 */
	@RequestMapping(value = { "inputWorkpieceNo" })
	public JSONObject inputWorkpieceNo(String inputWorkpieceNo) {

		MapEntity selectHistoryByWorkpieceNo = alarmDao.selectHistoryByWorkpieceNo(inputWorkpieceNo);

		if (selectHistoryByWorkpieceNo == null) {

			Data485.inputWorkpieceNo = inputWorkpieceNo;
		} else {

			return ToJsonUtil.buildJsonRs(false, "工件号已存在", "");

		}

		return ToJsonUtil.buildJsonRs(true, "可用工件号", inputWorkpieceNo);

	}

}
