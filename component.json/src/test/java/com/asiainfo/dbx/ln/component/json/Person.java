package com.asiainfo.dbx.ln.component.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


//忽略掉json中某些属性，即如下tempB，tempC属性不用映身到Person中
@JsonIgnoreProperties({"tempB","tempC"})

//忽略掉json中所有与person类属性不对应的:
//@JsonIgnoreProperties(ignoreUnknown=true)

public class Person {


    //默认输出json属性名与java属性名相同，
//而@JsonProperty可以改变匹配的json属性名
    @JsonProperty("_personName")
    private String personName;

    //配置单个属性不被序列化为json或反序列化为属性
    @JsonIgnore
    private String tempA;

    //反序列化时，映射到子类JobImpl， job= new JobImpl(),并注入属性
//未配置时，默认实例化Job并属性注入（可能Job为接口无法实例化,或Job为JobImpl的父类而 JobImpl的属性无注注入父类）
    @JsonDeserialize(as = JobImpl.class)
    private Job job = null;

    //有可能car实例为其某个子类，可以使之按父类Car序列化，而忽略了子类特有属性
    @JsonSerialize(as = Car.class)
    private Car car = null;

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getTempA() {
        return tempA;
    }

    public void setTempA(String tempA) {
        this.tempA = tempA;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
