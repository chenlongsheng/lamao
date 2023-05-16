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
public interface WorkPieceDao {

	public List<MapEntity> getPieceWorks();

	void insertWorkPiece(@Param(value = "workpieceName") String workpieceName,
			@Param(value = "nutsNumber") String nutsNumber, @Param(value = "workpiecePrefix") String workpiecePrefix,
			@Param(value = "workpieceNum") String workpieceNum, @Param(value = "displaceMax") String displaceMax,
			@Param(value = "displaceMin") String displaceMin, @Param(value = "relayMax") String relayMax,
			@Param(value = "relayMin") String relayMin, @Param(value = "workpieceTime") String workpieceTime,@Param(value = "status") String status);

	void updateWorkPiece(@Param(value = "workpieceName") String workpieceName,
			@Param(value = "nutsNumber") String nutsNumber, @Param(value = "workpiecePrefix") String workpiecePrefix,
			@Param(value = "workpieceNum") String workpieceNum, @Param(value = "displaceMax") String displaceMax,
			@Param(value = "displaceMin") String displaceMin, @Param(value = "relayMax") String relayMax,
			@Param(value = "relayMin") String relayMin, @Param(value = "workpieceTime") String workpieceTime,
			@Param(value = "id") String id,@Param(value = "status") String status);
	
	void deleteWorkPieceById(@Param(value = "id") String id);
	
	
	void manyInsertTest(MapEntity entity);

}
