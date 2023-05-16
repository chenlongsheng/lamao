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
public interface UserPDao {

//	@Param(value = "orgId") String orgId
	// 区域user下的集合
	public List<MapEntity> getUsers();

	void addUser(@Param(value = "name") String name, @Param(value = "jobNo") String jobNo, @Param(value = "classes") String classes);

	void updateUserById(@Param(value = "name") String name, @Param(value = "jobNo") String jobNo,
			@Param(value = "id") String id, @Param(value = "classes") String classes);

	void deleteUserById(@Param(value = "id") String id);

	public List<MapEntity> getPassword(@Param(value = "password") String password,@Param(value = "status") String status);

	void updatePass(@Param(value = "password") String password, @Param(value = "ip") String ip, @Param(value = "status") String status);

	MapEntity getOperation();
	
	void updateOperation(@Param(value = "warningCount") String warningCount, @Param(value = "process") String process, @Param(value = "pressure") String pressure);
	
	
	 
}
