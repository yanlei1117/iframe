package com.asiainfo.dbx.ln.component.workflow;

import java.util.List;

import javax.annotation.Resource;

import com.asiainfo.dbx.ln.component.workflow.activiti.group.GroupManager;
import org.activiti.engine.identity.Group;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value={"classpath*:config/spring/base/component*.xml","classpath*:config/spring/component*.xml","classpath*:config/spring/workflow/component*.xml"})
public class GroupManagerTest {
	@Resource(name="groupManager")
	private GroupManager groupManager;
	
	
	public GroupManager getGroupManager() {
		return groupManager;
	}

	public void setGroupManager(GroupManager groupManager) {
		this.groupManager = groupManager;
	}

	
	@Test
	public void testGroupManager() {
		Group planGroup = this.getGroupManager().createGroup("plan","产品策划部","A");
		Group developGroup = this.getGroupManager().createGroup("develop","产品开发部","B");
		Group testGroup = this.getGroupManager().createGroup("tester","产品测试部","C");
		Group maintenanceGroup = this.getGroupManager().createGroup("maintenance","产品维护部");
		Group saleGroup = this.getGroupManager().createGroup("sale","产品销售部");
	   
		Assert.assertEquals(5, this.getGroupManager().countGroup());
		Assert.assertEquals(1,this.getGroupManager().countGroup("产品策划部", "A"));
		this.getGroupManager().deleteGroup(saleGroup.getId());
		Assert.assertEquals(4, this.getGroupManager().countGroup());
		Group group = this.getGroupManager().queryGroupById(maintenanceGroup.getId());
		Assert.assertEquals("产品维护部", group.getName());
		List<Group> groupList =  this.getGroupManager().queryPageGroup(1, 2);
		Assert.assertEquals(2, groupList.size());
	}

}
