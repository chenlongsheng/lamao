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
public interface ProductionDao {

	 
	
	
	List<MapEntity> selectProduction(@Param(value = "workpieceId") String workpieceId, @Param(value = "userId") String userId, @Param(value = "classes") String classes,
			@Param(value = "beginTime") String 	beginTime, @Param(value = "endTime") String endTime);
	
	void deleteHistoryByIds(@Param(value = "ids") String ids);
	
	List<MapEntity> selectFaults(@Param(value = "workpieceId") String workpieceId, @Param(value = "userId") String userId, @Param(value = "classes") String classes,
			@Param(value = "beginTime") String 	beginTime, @Param(value = "endTime") String endTime);
	
	
	
	List<MapEntity> rankingRatios();
}
