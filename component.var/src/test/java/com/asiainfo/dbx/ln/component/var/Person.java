package com.asiainfo.dbx.ln.component.var;



import java.util.Date;
import java.util.List;

/**
 * Created by yanlei on 2014/7/11.
 */
public class Person {
       String name;
       Integer age;
       Date birthday ;
       float stature;
       char sex = 'M';
       Address address = null;
       List<StudyExperience> studyExperienceList = null;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public float getStature() {
        return stature;
    }

    public void setStature(float stature) {
        this.stature = stature;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public List<StudyExperience> getStudyExperiencList() {
        return studyExperienceList;
    }

    public void setStudyExperiencList(List<StudyExperience> studyExperiencList) {
        this.studyExperienceList = studyExperiencList;
    }

    public List<StudyExperience> getStudyExperienceList() {
        return studyExperienceList;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    public static class Builder{
        Person person = new Person();
        public Person builder(){
            return person;
        }
        public Builder name(String name){
            person.setName(name);
            return this;
        }
        public Builder age(Integer age){
            person.setAge(age);
            return this;
        }
        public Builder birthday(Date birthday){
            person.setBirthday(birthday);
            return this;
        }
        public Builder stature(float stature){
            person.setStature(stature);
            return this;
        }
        public Builder sex(char sex){
            person.setSex(sex);
            return this;
        }
        public Builder address(Address address){
            person.setAddress(address);
            return this;
        }
        public Builder studyExperienceList( List<StudyExperience> studyExperienceList){
            person.setStudyExperiencList(studyExperienceList);
            return this;
        }
    }
}
