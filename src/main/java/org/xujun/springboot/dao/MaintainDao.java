/**
 * 
 */
package org.xujun.springboot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.xujun.springboot.model.MapEntity;

/**
 * @author admin
 *
 */

@Mapper
public interface MaintainDao {

	void updateOperation(@Param(value = "mandrel") String mandrel, @Param(value = "hydraulicOil") String hydraulicOil,
			@Param(value = "repair") String repair);

	void updateConfig(@Param(value = "di1") String di1, @Param(value = "di2") String di2,
			@Param(value = "inputConfig") String inputCnfig);
	
	
	MapEntity selectMainTain();
	
	
	MapEntity selectConfig();
	
	
    void updataTaintain(@Param(value = "mandrelReal") String mandrelReal, @Param(value = "hydraulicOilReal") String hydraulicOilReal,
			@Param(value = "repairReal") String repairReal);
    
   void  insertLog(@Param(value = "id") String id);
   
   
   void  updatePrint(@Param(value = "deviceNo") String deviceNo,@Param(value = "isprint") String isprint);
   
   MapEntity selectPrintConfig();
   
   
// void  updataTaintainAddOne();
// 
// MapEntity  selectMaintainConfig();
	

}
