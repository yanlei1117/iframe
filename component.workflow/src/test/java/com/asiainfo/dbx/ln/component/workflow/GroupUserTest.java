package com.asiainfo.dbx.ln.component.workflow;

import java.util.List;

import javax.annotation.Resource;

import com.asiainfo.dbx.ln.component.workflow.activiti.group.GroupManager;
import com.asiainfo.dbx.ln.component.workflow.activiti.user.UserManager;
import org.junit.Assert;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value={"classpath*:config/spring/base/component*.xml","classpath*:config/spring/component*.xml","classpath*:config/spring/workflow/component*.xml"})
public class GroupUserTest {
	@Resource(name="groupManager")
	private GroupManager groupManager;
	
	
	public GroupManager getGroupManager() {
		return groupManager;
	}

	public void setGroupManager(GroupManager groupManager) {
		this.groupManager = groupManager;
	}

	@Resource(name="userManager")
	private UserManager userManager;

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}	
	
	@Test
	public void test(){
		User user = this.getUserManager().createUser("lining", "li", "ning", "java.yanlei@163.com", "yanlei");
		Group group = this.getGroupManager().createGroup("tester1", "测试部门");
		this.getUserManager().createMemeberShip(user.getId(), group.getId());
		List<User> userList = this.getUserManager().queryUserByGroupId(group.getId());
		Assert.assertEquals(1, userList.size());
		Assert.assertEquals(user.getId(), userList.get(0).getId());
		List<Group> groupList = this.getGroupManager().queryGroupByUserId(user.getId());
		Assert.assertEquals(1, groupList.size());
		Assert.assertEquals(group.getId(), groupList.get(0).getId());
	}
}
