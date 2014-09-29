package com.asiainfo.dbx.ln.component.workflow.vacation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yanlei on 2014/7/17.
 */
public class VacationService {
    private Logger logger = LoggerFactory.getLogger(VacationService.class);
    public void vactionRequset(Vacation vacation){
        logger.info("vactionRequset finish ");
    }

    public boolean vacationAuditFail(Vacation vacation){
        boolean result = false;
        logger.info("vacationAudit finish ,return value:{}",result);
            return result;
    }

    public boolean vacationAuditSuccess(Vacation vacation){
        boolean result = true;
        logger.info("vacationAudit finish ,return value:{}",result);
        return result;
    }
    public void vactionModify(Vacation vacation){

    }
    public void choiceVacationAuditorCandidateGroup(String groupId){

    }

}
