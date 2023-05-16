/**
 * 
 */
package org.xujun.springboot.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.xujun.springboot.dao.HistoryDao;
import org.xujun.springboot.model.MapEntity;
import org.xujun.springboot.websoket.utils.ToJsonUtil;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

/**
 * @author admin
 *
 */
@RestController
@CrossOrigin(origins = "*")
public class HistoryController {

	@Resource
	org.xujun.springboot.service.HistoryService historyService;
	@Resource
	private HistoryDao historyDao;
	
	static Calendar calendar = Calendar.getInstance();
	SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");

	@RequestMapping(value = { "getHistoryDatas" })
	public JSONObject getHistoryDatas(String workpieceId, String workpieceName, String userId, String workpieceNo,
			String beginTime, String endTime,String isPass, Integer pageNo) {
		if (workpieceId ==null&&workpieceName==null&&userId==null&&workpieceNo==null) {

	        // 获取一个月前的日期
	        calendar.add(Calendar.MONTH, -1);
	        beginTime = sdf.format(calendar.getTime());
		 
			System.out.println(beginTime);

		}
		
		PageInfo<MapEntity> studentPageInfo = historyService.getHistoryDatas(workpieceId, workpieceName, userId,
				workpieceNo, beginTime, endTime,isPass, pageNo);

		return ToJsonUtil.buildJsonRs(true, "", studentPageInfo);
	}

	@RequestMapping(value = { "getHistoryDataByWorkpieceNo" })
	public JSONObject getHistoryDataByWorkpieceNo(String workpieceNo) {

		return ToJsonUtil.buildJsonRs(true, "", historyService.getHistoryDataByWorkpieceNo(workpieceNo));

	}

	@RequestMapping(value = { "getHistoryDataById" })
	public JSONObject getHistoryDataById(String id) {

		return ToJsonUtil.buildJsonRs(true, "", historyService.getHistoryDataById(id));

	}

	@RequestMapping(value = { "deleteHistoryById" })
	public JSONObject deleteHistoryById(String userId, String workpieceNo) {


		try {
			historyService.deleteHistoryById(userId, workpieceNo);
		} catch (Exception e) {
			return ToJsonUtil.buildJsonRs(false, "刪除失敗", null);
		}
		return ToJsonUtil.buildJsonRs(true, "刪除成功", null);

	}
	
	
//	@RequestMapping(value = { "ratio" })
//	public JSONObject ratio() {
//		MapEntity entity  =new MapEntity();
//		entity.put("Ratio","6/m");   //   员工效率
//		entity.put("currentAveRatio","7/m");   // 当前员工平均效率
//		entity.put("historyAveRatio","5/m");  // 历史员工平均效率
//		
//		return ToJsonUtil.buildJsonRs(true, "员工效率", entity);
//
//	}
	
	@RequestMapping(value = { "deleteHistoryDay" })
	public JSONObject deleteHistoryDay(Integer day) {		
		
		try {
			if (day >0) {
				historyService.createHistoryEvent(day+"");				 
			}else {
				return ToJsonUtil.buildJsonRs(false, "设置失败", null);				
			}			
		} catch (Exception e) {
			// TODO: handle exception
			return ToJsonUtil.buildJsonRs(false, "设置失败", null);
		}		
		return ToJsonUtil.buildJsonRs(true, "设置成功", null);

	}





}
