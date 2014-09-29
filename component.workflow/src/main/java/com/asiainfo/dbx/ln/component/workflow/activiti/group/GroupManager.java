package com.asiainfo.dbx.ln.component.workflow.activiti.group;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asiainfo.dbx.ln.component.util.AppAssertUtils;
import com.asiainfo.dbx.ln.component.util.AppValidationUtils;
/**
 * Activiti 用户组管理
 * @author yanlei
 *
 */
public class GroupManager {
	private final Logger logger = LoggerFactory.getLogger(GroupManager.class);
	IdentityService identityService = null;
	public IdentityService getIdentityService() {
		return identityService;
	}

	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}

	public void createMemeberShip(String userId,String groupId ){
		this.getIdentityService().createMembership(userId, groupId);
	}
	public void deleteMemeberShip(String userId,String groupId ){
		this.getIdentityService().deleteMembership(userId, groupId);
	}
	public Group createGroup(String groupId,String groupName,String groupType){
		logger.debug("createGroup(groupName={},groupType={}锛�,groupName,groupType");
		Group group = this.getIdentityService().newGroup(groupId);
		setGroupName(groupName,group);
		setGroupType(groupType,group);
		this.getIdentityService().saveGroup(group);
		return group;
	}
	public Group createGroup(String groupId,String groupName){
		logger.debug("createGroup(groupName={}锛�,groupName");
		return createGroup(groupId,groupName,null);
	}
	private void setGroupName(String groupName,Group group){
		AppAssertUtils.hasLength(groupName, "groupName not allow null or empty");
		AppAssertUtils.notNull(group, "group object not allow null");
		group.setName(groupName);
	}
	
	private void setGroupType(String groupType,Group group){
		if(AppValidationUtils.notEmpty(groupType) && AppValidationUtils.notNull(group)){
			group.setType(groupType);
		}
	}
	
	public void modifyGroup(String groupId,String groupName,String groupType){
		logger.debug("modifyGroup(groupId={},groupName={},groupType={}锛�,groupId,groupName,groupType");
		 Group group = this.getIdentityService().createGroupQuery().groupId(groupId).singleResult();
		 setGroupName(groupName,group);
		 setGroupType(groupType,group);
		 this.getIdentityService().saveGroup(group);
	}
	
	public void modifyGroup(String groupId,String groupName){
		logger.debug("modifyGroup(groupId={},groupName={}锛�,groupId,groupName");
		modifyGroup(groupId,groupName);
	}
	public void deleteGroup(String groupId){
		logger.debug("deleteGroup(groupId={}锛�,groupId");
		AppAssertUtils.hasLength(groupId, "param:groupId not allow empty");
		this.getIdentityService().deleteGroup(groupId);
	}
	public List<Group> queryAllGroup(){
		return createGroupQuery(null).list();
	}
	public List<Group> queryPageGroup(int startIndex,int endIndex){
		return createGroupQuery(null).listPage(startIndex, endIndex);
	}
	public long countGroup(){
		return createGroupQuery(null).count();
	}
	public Group queryGroupById(String groupId){
		return this.getIdentityService().createGroupQuery().groupId(groupId).singleResult();
	}
	public List<Group> queryGroup(Map<String,String> groupMap){
		return createGroupQuery(groupMap).list();
	}
	private GroupQuery createGroupQuery(Map<String,String> groupMap){
		GroupQuery groupQuery =  this.getIdentityService().createGroupQuery();
		if(AppValidationUtils.notEmpty(groupMap)){
			String groupName = groupMap.get("groupName");
			String groupType = groupMap.get("groupType");
			String userId = groupMap.get("userId");
			String processDefinitionId = groupMap.get("processDefinitionId");
			if(AppValidationUtils.notEmpty(groupName)){
				groupQuery.groupName(groupName);
			}
			if(AppValidationUtils.notEmpty(groupType)){
				groupQuery.groupType(groupType);
			}
			if(AppValidationUtils.notEmpty(userId)){
				groupQuery.groupMember(userId);
			}
			if(AppValidationUtils.notEmpty(processDefinitionId)){
				groupQuery.potentialStarter(processDefinitionId);
			}
		}
		return groupQuery;
	}
	public List<Group> queryGroup(String groupName,String groupType){
		Map<String,String> paramMap = createParamMap(groupName,groupName,null,null);
		return createGroupQuery(paramMap).list();
	}
	private Map<String,String> createParamMap(String groupName,String groupType,String userId,String processDefinitionId){
		Map<String,String> map = new HashMap<String,String>();
		map.put("groupName", groupName);
		map.put("groupType", groupType);
		map.put("userId", userId);
		map.put("processDefinitionId", processDefinitionId);
		return map;
	}
	public List<Group> queryPageGroup(String groupName,String groupType,int startIndex,int endIndex){
		Map<String,String> paramMap = createParamMap(groupName,groupName,null,null);
		return createGroupQuery(paramMap).listPage(startIndex, endIndex);
	}
	public long countGroup(String groupName,String groupType){
		Map<String,String> paramMap = createParamMap(groupName,groupType,null,null);
		return createGroupQuery(paramMap).count();
	}
	public  List<Group> queryGroupByUserId(String userId){
		Map<String,String> paramMap = createParamMap(null,null,userId,null);
		return createGroupQuery(paramMap).list();
	}
	public List<Group> queryGroupByProcessDefinitionId(String processDefinitionId){
		Map<String,String> paramMap = createParamMap(null,null,null,processDefinitionId);
		return createGroupQuery(paramMap).list();
	}
}
