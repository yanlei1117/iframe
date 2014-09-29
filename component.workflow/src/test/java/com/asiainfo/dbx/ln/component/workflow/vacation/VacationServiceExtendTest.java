package com.asiainfo.dbx.ln.component.workflow.vacation;

import com.asiainfo.dbx.ln.component.util.AppVarUtils;
import com.asiainfo.dbx.ln.component.workflow.ProcessFlow;
import com.asiainfo.dbx.ln.component.workflow.TaskNode;
import com.asiainfo.dbx.ln.component.workflow.utils.WorkFlowCommonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by yanlei on 2014/7/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value={"classpath*:config/spring/base/component*.xml","classpath*:config/spring/base/workflow/component*.xml","classpath*:config/spring/workflow/component_workflow_test_extend.xml"})
public class VacationServiceExtendTest {
    private final Logger logger = LoggerFactory.getLogger(VacationServiceExtendTest.class);
    @Resource(name="vacationService")
    private VacationService vacationService;

    public VacationService getVacationService() {
        return vacationService;
    }

    public void setVacationService(VacationService vacationService) {
        this.vacationService = vacationService;
    }

    @Resource(name="processFlow")
    private ProcessFlow processFlow;

    public ProcessFlow getProcessFlow() {
        return processFlow;
    }

    public void setProcessFlow(ProcessFlow processFlow) {
        this.processFlow = processFlow;
    }



    @Test
    public void testVacation(){
        try {
            Vacation vacation = new Vacation();
            vacation.setRequestUserId("yanlei");
            vacation.setRequestDate(new Date());
            vacation.setRequestReason("学车请假");

            vacation.setVacationDays(5);
            vacation.setVacationId("3408");
            WorkFlowCommonUtils.setCurrentUserId("yanlei");
            //请假申请
            this.getVacationService().vactionRequset(vacation);
            List<TaskNode> taskNodeList =   this.getProcessFlow().findTaskByAssigneeUserId("yanlei");
            //选择审批候选组
            //设置TaskId变量
            WorkFlowCommonUtils.setTaskId( taskNodeList.get(0).getTaskId());

            this.getVacationService().choiceVacationAuditorCandidateGroup("911");

            //设置审批任务的受理人
            taskNodeList =   this.getProcessFlow().findTaskByCandidateGroupId("911");

            this.getProcessFlow().setAssigneeUserToTask(taskNodeList.get(0).getTaskId(),"leader");

            //审批不同意
            WorkFlowCommonUtils.setCurrentUserId( "leader");
            WorkFlowCommonUtils.setTaskId( taskNodeList.get(0).getTaskId());
           this.getVacationService().vacationAuditFail(vacation);

            //请假审请修改
            taskNodeList =   this.getProcessFlow().findTaskByAssigneeUserId("yanlei");
            WorkFlowCommonUtils.setTaskId(taskNodeList.get(0).getTaskId());
            Object obj = AppVarUtils.getVarContainer(VacationServiceExtendTest.class).getVar("@{CURRENT_TASK.completeTaskListInExecution}");
            logger.info(obj.toString());
            Object obj1 =  AppVarUtils.getVarContainer(VacationServiceExtendTest.class).getVar("@{CURRENT_TASK.completeTaskListInExecution.{$#this.taskName='请假流程审批'}[0].candidateGroupId}");
            logger.info(obj1.toString());


            this.getVacationService().vactionModify(vacation);


            //设置审批任务的受理人
            taskNodeList =   this.getProcessFlow().findTaskByCandidateGroupId("911");

            this.getProcessFlow().setAssigneeUserToTask(taskNodeList.get(0).getTaskId(),"leader");


            //审批成功
            WorkFlowCommonUtils.setCurrentUserId( "leader");
            WorkFlowCommonUtils.setTaskId( taskNodeList.get(0).getTaskId());
            this.getVacationService().vacationAuditSuccess(vacation);

        }catch(Exception e){
            logger.error("",e);
        }
    }
}
