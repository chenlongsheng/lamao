/**
 * 
 */
package org.xujun.springboot.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.xujun.springboot.websoket.utils.ToJsonUtil;
import org.xujun.springboot.service.WorkPieceService;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

/**
 * @author admin
 *
 */

@RestController
@CrossOrigin(origins = "*")
public class WorkpieceController {

	@Resource
	WorkPieceService WorkPieceService;

	@RequestMapping(value = { "getPieceWorks" })
	public JSONObject getPieceWorks() {

		return ToJsonUtil.buildJsonRs(true, "工件集合", WorkPieceService.getPieceWorks());
	}
 	
	@RequestMapping(value = { "insertWorkPiece" })
	public JSONObject insertWorkPiece(String workpieceName, String nutsNumber, String workpiecePrefix,
			String workpieceNum, String displaceMax, String displaceMin, String relayMax, String relayMin,
			String workpieceTime, String id, String status) {
		try {

			if (id == null || id.equals("")) {
				WorkPieceService.insertWorkPiece(workpieceName, nutsNumber, workpiecePrefix, workpieceNum, displaceMax,
						displaceMin, relayMax, relayMin, workpieceTime,status);
			} else {
				WorkPieceService.updateWorkPiece(workpieceName, nutsNumber, workpiecePrefix, workpieceNum, displaceMax,
						displaceMin, relayMax, relayMin, workpieceTime, id,status);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ToJsonUtil.buildJsonRs(false, "添加/修改失败", "");
		}
		return ToJsonUtil.buildJsonRs(true, "添加/修改成功", "");

	}

	
	@RequestMapping(value = { "deleteWorkPieceById" })
	public JSONObject deleteWorkPieceById(String id) {

		try {
			WorkPieceService.deleteWorkPieceById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return ToJsonUtil.buildJsonRs(false, "删除失败", "");
		}
		return ToJsonUtil.buildJsonRs(true, "删除成功", "");

	}

}
