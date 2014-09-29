package com.asiainfo.dbx.ln.component.workflow.activiti.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asiainfo.dbx.ln.component.workflow.activiti.group.GroupManager;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Picture;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asiainfo.dbx.ln.component.AppConstant;
import com.asiainfo.dbx.ln.component.util.AppAssertUtils;
import com.asiainfo.dbx.ln.component.util.AppValidationUtils;
/**
 * Activiti 用户信息管理
 * @author yanlei
 *
 */

public class UserManager {
	private final Logger logger = LoggerFactory.getLogger(GroupManager.class);
	IdentityService identityService = null;
	public IdentityService getIdentityService() {
		return identityService;
	}

	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}

	public User createUser(String userId,String firstName,String lastName,String email,String password){
		
		User user = this.getIdentityService().newUser(userId);
		setProperty(user,firstName,lastName,email,password);
		this.getIdentityService().saveUser(user);
		return user;
	}
	private void setProperty(User user,String firstName,String lastName,String email,String password){
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(password);
	}
	public User ModifyUserById(String userId,String firstName,String lastName,String email,String password){
		User user = this.getIdentityService().createUserQuery().userId(userId).singleResult();
		AppAssertUtils.notNull(user,"not exist user for userId("+userId+")");
		setProperty(user,firstName,lastName,email,password);	
		this.getIdentityService().saveUser(user);
		return user;
	}
	public User queryUserById(String userId){
		return this.getIdentityService().createUserQuery().userId(userId).singleResult();
	}
	public void deleteUserByid(String userId){
		this.getIdentityService().deleteUser(userId);
	}
	public boolean checkPassword(String userId,String password){
		return this.getIdentityService().checkPassword(userId, password);
	}
	public List<User> queryUserByGroupId(String groupId){
		return this.getIdentityService().createUserQuery().memberOfGroup(groupId).list();
	}
	public List<User> queryAllUser(){
		return this.getIdentityService().createUserQuery().list();
	}
	public long queryAllUserCount(){
		return this.getIdentityService().createUserQuery().count();
	}
	
	public void createUserPicture(String userId, Picture picture){
		this.getIdentityService().setUserPicture(userId,  picture);
	}
	public void createUserInfo(String userId,Map<String,String> infoMap){
		if(AppValidationUtils.notEmpty(infoMap)){
			for(String key:infoMap.keySet()){
					String value = infoMap.get(key);
					if(AppValidationUtils.notEmpty(value)){
						this.getIdentityService().setUserInfo(userId, key, value);
					}
			}
		}
	}
	public Map<String,String> getUserInfoMap(String userId){
		List<String> keyList = this.getIdentityService().getUserInfoKeys(userId);
		if(AppValidationUtils.notEmpty(keyList)){
			Map<String,String> userInfoMap = new HashMap<String,String>();
 			for(String key:keyList){
				String value = this.getIdentityService().getUserInfo(userId, key);
				userInfoMap.put(key, value);
			}
 			return userInfoMap;
		}else{
			return null;
		}
	}
	public Picture getUserPicture(String userId){
		return this.getIdentityService().getUserPicture(userId);
	}
	public List<User> queryAllUserByFirstName(String firstName){
		Map<String,String> paramMap = createParamMap(firstName,null,null,null);
		return this.createUserQuery(paramMap).list();
	}
	public List<User> queryAllUserByLastName(String lastName){
		Map<String,String> paramMap = createParamMap(null,lastName,null,null);
		return this.createUserQuery(paramMap).list();
	}
	
	public void createMemeberShip(String userId,String groupId ){
		this.getIdentityService().createMembership(userId, groupId);
	}
	public void deleteMemeberShip(String userId,String groupId ){
		this.getIdentityService().deleteMembership(userId, groupId);
	}
	
	public List<User> queryAllUserByFullName(String fullName){
		Map<String,String> paramMap = createParamMap(null,null,fullName,null);
		return this.createUserQuery(paramMap).list();
	}
	public List<User> queryAllUserByEmail(String email){
		Map<String,String> paramMap = createParamMap(null,null,null,email);
		return this.createUserQuery(paramMap).list();
	}
	public List<User> queryAllUserByEmail(String firstName,String lastName,String fullName,String email){
		Map<String,String> paramMap = createParamMap(firstName,lastName,fullName,email);
		return this.createUserQuery(paramMap).list();
	}
	
	
	private UserQuery createUserQuery(Map<String,String> userMap){
		UserQuery userQuery =  this.getIdentityService().createUserQuery();
		if(AppValidationUtils.notEmpty(userMap)){
			String firstName = userMap.get("firstName");
			String lastName = userMap.get("lastName");
			String email = userMap.get("email");
			String fullName = userMap.get("fullName");
			
			if(AppValidationUtils.notEmpty(firstName)){
				if(StringUtils.contains(firstName, AppConstant.PERCENT)){
					userQuery.userFirstNameLike(firstName);
				}else{
					userQuery.userFirstName(firstName);	
				}
			}
			if(AppValidationUtils.notEmpty(lastName)){
				if(StringUtils.contains(lastName, AppConstant.PERCENT)){
					userQuery.userLastNameLike(lastName);
				}else{
					userQuery.userLastName(lastName);	
				}
			}
			if(AppValidationUtils.notEmpty(email)){
				if(StringUtils.contains(email, AppConstant.PERCENT)){
					userQuery.userEmailLike(email);
				}else{
					userQuery.userEmail(email);	
				}
			}
			if(AppValidationUtils.notEmpty(fullName)){
				userQuery.userFullNameLike(fullName);
			}
			
		}
		return userQuery;
	}
	
	private Map<String,String> createParamMap(String firstName,String lastName,String fullName,String email){
		Map<String,String> map = new HashMap<String,String>();
		map.put("firstName", firstName);
		map.put("lastName", lastName);
		map.put("email", email);
		map.put("fullName", fullName);
		return map;
	}
	
	
}
