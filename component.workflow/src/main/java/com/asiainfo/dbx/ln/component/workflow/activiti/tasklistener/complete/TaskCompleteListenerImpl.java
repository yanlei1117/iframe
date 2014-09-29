package com.asiainfo.dbx.ln.component.workflow.activiti.tasklistener.complete;

import com.asiainfo.dbx.ln.component.util.AppAssertUtils;
import com.asiainfo.dbx.ln.component.util.AppValidationUtils;
import com.asiainfo.dbx.ln.component.workflow.ProcessFlow;
import com.asiainfo.dbx.ln.component.workflow.TaskNode;
import com.asiainfo.dbx.ln.component.workflow.activiti.model.TaskThreadLocal;
import com.asiainfo.dbx.ln.component.workflow.activiti.tasklistener.complete.taskResult.TaskResultFinder;
import com.asiainfo.dbx.ln.component.workflow.activiti.tasklistener.complete.taskfinder.TaskFinder;
import com.asiainfo.dbx.ln.component.workflow.utils.WorkFlowCommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 使用spring 在任务完成的Service上加AOP代理，当service 的方法执行完成，即任务完成时，被调用，
 * 作用：
 * 1.设置工作流对应的任务完成(task.complete())
 *
 *
 * 问题：如果根据service的方法来确认对应的任务呢？
 *       方式一：由前台用户为受理人+任务名称，查询任务，如果只有一个即可以确认为当前任务
 *              （这种方式在用户有多个相同任务无法确定当前用户操作的任务，如：同一个领导有5个假期审批，只是申请内容不同）
 *       方式二：在任务的创建时指定一个变量（businessKey_）保存在当前任务（act_ru_task）变量中，任务完成时由http request参数、或
 *               service 方法的入参，出参中可以获得到，再由这个变量（businessKey_）来查询当前任务
 *       方式三：任务完成时，携带 taskid参数，由http request参数、或 service 方法的入参，出参中可以获得到taskId,得到taskId之后就可以
 *               查询当前任务
 *
 *  当前实现为：三种方式都使用，方式一满足？调用方式一：（方式二满足？调用方式二：(方式三满足？)调用方式三：抛出异常)
 *  为了提高灵活性扩展性，将实现方式抽像为接口(taskMatcher)，默认实现按上面的逻辑，也可以自定义实现方式，替换默认实现即可
 * 2.获取当前任务的结果，当前任务后是排他网关、事件网关、兼容网关、并行网关，
 *      这个网关需要前一个任务的结果（或参数）来确定流程的走向
 *      定义接口TaskResultFinder，来获取任务执行结果
 * Created by yanlei on 2014/7/15.
 */
public class TaskCompleteListenerImpl implements  TaskCompleteListener{
    private final Logger logger = LoggerFactory.getLogger(TaskCompleteListenerImpl.class);
    private TaskFinder taskFinder;

    public TaskFinder getTaskFinder() {
        return taskFinder;
    }

    public void setTaskFinder(TaskFinder taskFinder) {
        this.taskFinder = taskFinder;
    }

    @Resource(name="processFlow")
    private ProcessFlow processFlow;

    public ProcessFlow getProcessFlow() {
        return processFlow;
    }

    public void setProcessFlow(ProcessFlow processFlow) {
        this.processFlow = processFlow;
    }

    private TaskResultFinder taskResultFinder ;

    public TaskResultFinder getTaskResultFinder() {
        return taskResultFinder;
    }

    public void setTaskResultFinder(TaskResultFinder taskResultFinder) {
        this.taskResultFinder = taskResultFinder;
    }

    private String taskId;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    private Map<String,String> resultVarMap ;

    public Map<String, String> getResultVarMap() {
        return resultVarMap;
    }

    public void setResultVarMap(Map<String, String> resultVarMap) {
        this.resultVarMap = resultVarMap;
    }

    @Resource(name="currentTask")
    private TaskThreadLocal taskThreadLocal = null;

    public TaskThreadLocal getTaskThreadLocal() {
        return taskThreadLocal;
    }

    public void setTaskThreadLocal(TaskThreadLocal taskThreadLocal) {
        this.taskThreadLocal = taskThreadLocal;
    }


    public void completeTask(){
        try {
            logger.info("Task Complete start :---------------------------------------------------------");
            AppAssertUtils.notNull(this.getProcessFlow(), "please config  property named 'processFlow' for this class");

            String taskIdValue = null;
            TaskNode taskNode = null;
            if(AppValidationUtils.notNull(this.getTaskFinder())){
                taskIdValue = this.getTaskFinder().findCurrentTaskId();
            }else  if(AppValidationUtils.notNull(this.getTaskId())) {
                String taskId1= WorkFlowCommonUtils.getVarToString(this.getTaskId());
                AppAssertUtils.notNull(taskId1, "not found current task id,taskId expression:"+taskId);
                     taskNode = this.getProcessFlow().findTaskByTaskId(taskId1);
                    if (AppValidationUtils.notNull(taskNode)) {
                        taskIdValue = taskNode.getTaskId();
                    }

            }
            if(AppValidationUtils.notNull(this.getTaskThreadLocal())){
                this.getTaskThreadLocal().setCurrentTaskId(taskIdValue);
            }
            AppAssertUtils.notNull(taskIdValue, "not found current task id");
            Map<String, Object> resultMap = null;
            if(AppValidationUtils.notNull(this.getResultVarMap())){
                Iterator<String> varNamesIterator = this.getResultVarMap().keySet().iterator();
                Map<String,Object> tempResultMap = new HashMap<String, Object>(this.getResultVarMap().size());
                while(varNamesIterator.hasNext()){
                    String varName = varNamesIterator.next();
                    String newVarName = this.getResultVarMap().get(varName);
                    Object varValue = WorkFlowCommonUtils.getVar(varName);
                    tempResultMap.put(newVarName,varValue);
                }
                if(AppValidationUtils.notEmpty(tempResultMap)){
                    resultMap = tempResultMap;
                }

            }else if(AppValidationUtils.notNull(this.getTaskResultFinder())){
                resultMap = this.getTaskResultFinder().findTaskResult();
            }

           if (AppValidationUtils.notNull(resultMap)) {
                this.getProcessFlow().completeTask(taskIdValue, resultMap);
            } else {
                this.getProcessFlow().completeTask(taskIdValue);
            }
            logger.info("Task Complete Finish:TaskNode:{} ,resultMap:{}----------------------------------\n",taskNode==null?this.getProcessFlow().findTaskByTaskId(taskIdValue):taskNode,resultMap);


        }catch(Exception e){
            logger.error("",e);
        }

    }

}
