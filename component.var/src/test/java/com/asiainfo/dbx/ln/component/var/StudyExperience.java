package com.asiainfo.dbx.ln.component.var;

import java.util.Date;

/**
 * 学习经历
 * Created by yanlei on 2014/7/11.
 */
public class StudyExperience {
        Date startDate;
        Date endDate;
        String schoolName;
        int schoolType;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public int getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(int schoolType) {
        this.schoolType = schoolType;
    }
}
