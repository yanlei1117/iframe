package com.asiainfo.dbx.ln.component.workflow.vacation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by yanlei on 2014/7/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value={"classpath*:config/spring/base/component*.xml","classpath*:config/spring/workflow/component*.xml"})
public class VacationTest {

    @Resource(name="vacationService")
    private VacationService vacationService;

    public VacationService getVacationService() {
        return vacationService;
    }

    public void setVacationService(VacationService vacationService) {
        this.vacationService = vacationService;
    }

    @Test
    public void testAroundMethod(){
        Vacation vacation = new Vacation();
        vacation.setRequestDate(new Date());
        vacation.setRequestReason("旅游");
        vacation.setRequestUserId("yanlei");
        vacation.setVacationDays(5);
        this.getVacationService().vacationAuditFail(vacation);
    }
}
