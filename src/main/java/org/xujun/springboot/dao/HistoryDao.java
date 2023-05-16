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
public interface HistoryDao {

	public MapEntity getPieceNo(@Param(value = "id") String id);

	void addHistory(@Param(value = "workpieceId") String workpieceId, @Param(value = "userId") String userId,
			@Param(value = "workpieceNo") String workpieceNo, @Param(value = "workpieceName") String workpieceName,
			@Param(value = "values") String values, @Param(value = "historyTime") String historyTime,
			@Param(value = "notPass") Integer notPass, @Param(value = "checkPass") String checkPass

	);

	public List<MapEntity> getHistoryDatas(@Param(value = "workpieceId") String workpieceId,
			@Param(value = "workpieceName") String workpieceName, @Param(value = "userId") String userId,
			@Param(value = "workpieceNo") String workpieceNo, @Param(value = "beginTime") String beginTime,
			@Param(value = "endTime") String endTime, @Param(value = "isPass") String isPass

	);

	public List<MapEntity> getHistoryDetails(@Param(value = "workpieceId") String workpieceId,
										   @Param(value = "workpieceName") String workpieceName, @Param(value = "userId") String userId,
										   @Param(value = "workpieceNo") String workpieceNo, @Param(value = "beginTime") String beginTime,
										   @Param(value = "endTime") String endTime, @Param(value = "isPass") String isPass

	);



	void deleteHistoryById(@Param(value = "userId") String userId, @Param(value = "workpieceNo") String workpieceNo);

	List<MapEntity> getHistoryDataByWorkpieceNo(@Param(value = "workpieceNo") String workpieceNo);

	MapEntity getHistoryDataById(@Param(value = "id") String id);

	void updateUserPiece(@Param(value = "workpieceId") String workpieceId, @Param(value = "userId") String userId,
			@Param(value = "nutsNumber") String nutsNumber, @Param(value = "count") Integer count,
			@Param(value = "workpieceCount") Integer workpieceCount);

	MapEntity getCountById(@Param(value = "workpieceId") String workpieceId, @Param(value = "userId") String userId);

	String getRatio(@Param(value = "state") String state, @Param(value = "userId") String userId);

	void createHistoryEvent(@Param(value = "day") String day);

	void updataTaintainAddOne();

	MapEntity selectMaintainConfig();

	void updateRealLog(@Param(value = "state") String state, @Param(value = "time") String time,
			@Param(value = "relieveState") String relieveState, @Param(value = "id") String id);

	void insertLog(@Param(value = "id") String id);

	String selectIsOpen();

	void updateRealTcongfig(@Param(value = "realDi1") String realDi1, @Param(value = "realDi2") String realDi2);
	
	
	List<MapEntity>  selectAlarmState();

}
