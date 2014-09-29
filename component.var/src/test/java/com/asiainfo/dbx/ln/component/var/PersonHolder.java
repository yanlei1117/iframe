package com.asiainfo.dbx.ln.component.var;

import com.asiainfo.dbx.ln.component.util.AppDateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanlei on 2014/7/11.
 */
public class PersonHolder {
    private static Person person = null;
    private static Address address = null;
    private static StudyExperience studyExperience= null;
    public static Person createPerson(){
        address = new Address();
        address.setStreet("yilujiang street");
        List<StudyExperience> studyList = new ArrayList<StudyExperience>();
        StudyExperience studyExperience  = new StudyExperience();
        studyExperience.setStartDate(AppDateUtils.parseString("19870901"));
        studyExperience.setEndDate(AppDateUtils.parseString("19930701"));
        studyExperience.setSchoolName("建设小学");
        studyExperience.setSchoolType(1);
        studyList.add(studyExperience);
        studyExperience  = new StudyExperience();
        studyExperience.setStartDate(AppDateUtils.parseString("19930901"));
        studyExperience.setEndDate(AppDateUtils.parseString("19960701"));
        studyExperience.setSchoolName("建设中学");
        studyExperience.setSchoolType(2);
        studyList.add(studyExperience);
        studyExperience  = new StudyExperience();
        studyExperience.setStartDate(AppDateUtils.parseString("19960901"));
        studyExperience.setEndDate(AppDateUtils.parseString("19990701"));
        studyExperience.setSchoolName("建设高中");
        studyExperience.setSchoolType(3);
        studyList.add(studyExperience);

        studyExperience  = new StudyExperience();
        studyExperience.setStartDate(AppDateUtils.parseString("19990901"));
        studyExperience.setEndDate(AppDateUtils.parseString("20030701"));
        studyExperience.setSchoolName("建设大学");
        studyExperience.setSchoolType(4);
        studyList.add(studyExperience);




        person = new Person.Builder().name("yanlei").age(35).birthday(AppDateUtils.parseString("19801117")).sex('M').stature(1.65f).address(address).studyExperienceList(studyList).builder();
        return person;
    }
    public static Person getPerson(){
        return person;
    }
    public static Address getAddress(){
        return address;
    }
    public static StudyExperience getStudyExperience(){
        return studyExperience;
    }
}
