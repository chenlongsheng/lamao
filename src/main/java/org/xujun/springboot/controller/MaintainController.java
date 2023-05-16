/**
 * 
 */
package org.xujun.springboot.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.xujun.springboot.service.MaintainService;
import org.xujun.springboot.websoket.utils.ToJsonUtil;

import com.alibaba.fastjson.JSONObject;

/**
 * @author admin
 *
 */

@RestController
@CrossOrigin(origins = "*")
public class MaintainController {

	@Resource
	MaintainService maintainService;

	@RequestMapping(value = { "updateMaintainOperation" })
	public JSONObject updateMaintainOperation(String mandrel, String hydraulicOil, String repair) {

		maintainService.updateOperation(mandrel, hydraulicOil, repair);
		return ToJsonUtil.buildJsonRs(true, "更新配置", "");

	}

	@RequestMapping(value = { "updateConfig" })
	public JSONObject updateConfig(String di1, String di2, String inputConfig) {

		maintainService.updateConfig(di1, di2, inputConfig);
		return ToJsonUtil.buildJsonRs(true, "更新配置", "");

	}

	@RequestMapping(value = { "selectMainTain" })
	public JSONObject selectMainTain() {

		return ToJsonUtil.buildJsonRs(true, "", maintainService.selectMainTain());

	}

	@RequestMapping(value = { "selectConfig" })
	public JSONObject selectConfig() {

		return ToJsonUtil.buildJsonRs(true, "", maintainService.selectConfig());

	}

	@RequestMapping(value = { "updateMaintain" })
	public JSONObject updateMaintain(String mandrelReal, String hydraulicOilReal, String repairReal) {

		try {

			maintainService.updataTaintain(mandrelReal, hydraulicOilReal, repairReal);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return ToJsonUtil.buildJsonRs(true, "", "");

	}

	@RequestMapping(value = { "selectPrintConfig" })
	public JSONObject selectPrintConfig() {

		return ToJsonUtil.buildJsonRs(true, "", maintainService.selectPrintConfig());

	}

	@RequestMapping(value = { "updatePrint" })
	public JSONObject updatePrint(String deviceNo, String isprint) {

		try {
			maintainService.updatePrint(deviceNo, isprint);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return ToJsonUtil.buildJsonRs(true, "", "");
	}
}
