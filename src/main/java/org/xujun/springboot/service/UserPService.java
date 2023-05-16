/**
 * 
 */
package org.xujun.springboot.service;

import java.util.List;

import javax.annotation.Resource;


import org.springframework.stereotype.Component;

import org.xujun.springboot.dao.UserPDao;
import org.xujun.springboot.model.MapEntity;

/**
 * @author admin
 *
 */

@Component
public class UserPService {

	 @Resource
	private UserPDao userPDao;

	public List<MapEntity> getUsers() {

		return userPDao.getUsers();

	}

	public void addUser(String name, String jobNo,String classes) {

		userPDao.addUser(name, jobNo,classes);
	}

	public void updateUserById(String name, String jobNo, String id,String classes) {

		userPDao.updateUserById(name, jobNo, id,classes);
	}

	public void deleteUserById(String id) {

		userPDao.deleteUserById(id);
	}
	
	public void updateOperation(String warningCount, String process, String pressure) {

		userPDao.updateOperation(warningCount, process, pressure);
	}
	
	public List<MapEntity> getPassword(String password, String status) {

		return userPDao.getPassword(password, status);
	}
	

}
