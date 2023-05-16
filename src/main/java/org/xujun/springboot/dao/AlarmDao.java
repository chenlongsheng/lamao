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
public interface AlarmDao {

	List<MapEntity> selectRealLog();

	List<MapEntity> selectAlarmLog();
	
	
	MapEntity selectHistoryByWorkpieceNo(@Param(value= "workpieceNo") String workpieceNo);
	
	void modifyCoefficient(@Param(value= "coefficient") String coefficient);
}







