package com.asiainfo.dbx.ln.component.workflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.asiainfo.dbx.ln.component.workflow.activiti.user.UserManager;
import org.junit.Assert;

import org.activiti.engine.identity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@SuppressWarnings("unchecked")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value={"classpath*:config/spring/base/component*.xml","classpath*:config/spring/component*.xml","classpath*:config/spring/workflow/component*.xml"})
public class UserManagerTest {
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
		User user = this.getUserManager().createUser("yanlei", "yan", "lei", "java.yanlei@163.com", "abcd");
		String userId = user.getId();
		user = this.getUserManager().queryUserById(userId);
		Assert.assertEquals("java.yanlei@163.com", user.getEmail());
		
	   user = 	this.getUserManager().createUser("zhangsan", "zhang", "san", "zhang.san@163com", "abcdd");
	   List<User>  userList = this.getUserManager().queryAllUser();
	  
	   Assert.assertTrue(userList.size()>2);
	   boolean checkPassword = this.getUserManager().checkPassword(user.getId(),"abcdd" );
	   Assert.assertEquals(true, checkPassword);
		Map map = new HashMap();
		map.put("age", "30");
		this.getUserManager().createUserInfo(user.getId(), map);
		Map infoMap = this.getUserManager().getUserInfoMap(user.getId());
		Assert.assertEquals(map, infoMap);
		
	}
}