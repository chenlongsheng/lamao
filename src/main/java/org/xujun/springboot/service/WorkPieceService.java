/**
 * 
 */
package org.xujun.springboot.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
 

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.xujun.springboot.dao.UserPDao;
import org.xujun.springboot.dao.WorkPieceDao;
import org.xujun.springboot.model.MapEntity;

/**
 * @author admin
 *
 */

@Component
public class WorkPieceService {

	@Resource
	private WorkPieceDao workPieceDao;
	
	
	 @Autowired
	    private SqlSessionTemplate sqlSessionTemplate;

	    @Transactional
	    public void testMybatisInsert() {
			
			// 准备1000条测试数据
	        ArrayList<MapEntity> entityList = new ArrayList<>();
	        MapEntity enntity = null;
	        for (int i = 0; i < 1000; i++) {
	            enntity = new MapEntity();
	            enntity.put("time",new Date());
	            enntity.put("time1",new Date());
	            entityList.add(enntity);
	        }

	        // 如果自动提交设置为true,将无法控制提交的条数，会变成最后统一提交，有可能导致内存溢出
	        // 设置为false,手动控制提交的时点
	        SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
	        WorkPieceDao mapper = session.getMapper(WorkPieceDao.class);

	        try {
	            for (int i = 0; i < entityList.size(); i++) {
	            	
	            	// 进行数据插入(此时相当于准备插入的sql语句,并没有真正将数据入库)
	                mapper.manyInsertTest(entityList.get(i));
	                
	                if (i % 100 == 0 || i == entityList.size() - 1) {
	                    // 每100条提交一次,提交后无法回滚
	                    session.commit();
	                    // 刷新执行结果
	                    session.flushStatements();
	                    // 清理缓存,防止溢出
	                    session.clearCache();
	                }
	            }
	        } catch (Exception e) {
	            // 回滚
	            session.rollback();
	        } finally {
	            session.close();
	        }
	    }
 	        
	public List<MapEntity> getPieceWorks() {

		return workPieceDao.getPieceWorks();

	}

	public void insertWorkPiece(String workpieceName, String nutsNumber, String workpiecePrefix, String workpieceNum,
			String displaceMax, String displaceMin, String relayMax, String relayMin, String workpieceTime,String status) {

		workPieceDao.insertWorkPiece(workpieceName, nutsNumber, workpiecePrefix, workpieceNum, displaceMax, displaceMin,
				relayMax, relayMin, workpieceTime,status);
	}

	public void updateWorkPiece(String workpieceName, String nutsNumber, String workpiecePrefix, String workpieceNum,
			String displaceMax, String displaceMin, String relayMax, String relayMin, String workpieceTime, String id,String status) {

		workPieceDao.updateWorkPiece(workpieceName, nutsNumber, workpiecePrefix, workpieceNum, displaceMax, displaceMin,
				relayMax, relayMin, workpieceTime, id,status);
	}

	public void deleteWorkPieceById(String id) {
		workPieceDao.deleteWorkPieceById(id);
	}

}
