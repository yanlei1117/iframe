package com.asiainfo.dbx.ln.component.json;

import com.asiainfo.dbx.ln.component.util.AppJsonUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/7/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value={"classpath*:config/spring/base/component*.xml"})
public class TestJsonMapperJackson {
    private final Logger logger = LoggerFactory.getLogger(TestJsonMapperJackson.class);
    @Test
    public void testJson(){

        CarBMW car = new CarBMW();
        car.setBrand("BMW");
        car.setModelNumber("x7");
        car.setSpecialSystem(" gsi system");
        Person person = new Person();
        person.setTempA("hello");
        person.setPersonName("yanlei");
        person.setCar(car);

        JobImpl job = new JobImpl();
        job.setJobName("writer");
        person.setJob(job);
        try {
            String jsonString = AppJsonUtils.objToJson(person);
            logger.info(jsonString);
            Person newPerson = AppJsonUtils.jsonToObj(jsonString,Person.class);
            Assert.assertEquals(person.getPersonName(),newPerson.getPersonName());
            Assert.assertEquals(person.getCar().getBrand(),newPerson.getCar().getBrand());
            Assert.assertEquals(person.getCar().getModelNumber(),newPerson.getCar().getModelNumber());
            Assert.assertEquals(((JobImpl)person.getJob()).getJobName(),((JobImpl)newPerson.getJob()).getJobName());
            Assert.assertNull(newPerson.getTempA());

           Map map =  AppJsonUtils.jsonToMap(jsonString);

            Assert.assertEquals(person.getPersonName(),map.get("_personName"));
            Assert.assertEquals(person.getCar().getBrand(),((Map)map.get("car")).get("brand"));
            Assert.assertEquals(person.getCar().getModelNumber(),((Map)map.get("car")).get("modelNumber"));
            Assert.assertEquals(((JobImpl)person.getJob()).getJobName(),((Map)map.get("job")).get("jobName"));

            List list =  AppJsonUtils.jsonToList("[" + jsonString + "]");
            Assert.assertEquals(1,list.size());
            Assert.assertEquals(person.getPersonName(),((Map)list.get(0)).get("_personName"));
            Assert.assertEquals(person.getCar().getBrand(),((Map)((Map)list.get(0)).get("car")).get("brand"));
            Assert.assertEquals(person.getCar().getModelNumber(),((Map)((Map)list.get(0)).get("car")).get("modelNumber"));
            Assert.assertEquals(((JobImpl)person.getJob()).getJobName(),((Map)((Map)list.get(0)).get("job")).get("jobName"));



        }catch(Exception e){
            logger.error("",e);
        }
    }

    @Test
    public void testParseJson(){
        try {
            String json = "{name:'yanlei',addresss:[{street:yalujiang street},{street:\"TEST\"}]}";
            String standardJson = AppJsonUtils.formatToStandardJsonString(json);
            Assert.assertEquals("{\"name\":\"yanlei\",\"addresss\":[{\"street\":\"yalujiang street\"},{\"street\":\"TEST\"}]}", standardJson);


           Map map =  AppJsonUtils.jsonToMap(standardJson);
           Assert.assertEquals("yanlei",map.get("name"));
            Assert.assertEquals("TEST",((Map)(((List)map.get("addresss")).get(1))).get("street"));

            json = "{name:#{param.name},addresss:{street:#{param.street},flag:true}}";
            standardJson = AppJsonUtils.formatToStandardJsonString(json);
            Assert.assertEquals("{\"name\":\"#{param.name}\",\"addresss\":{\"street\":\"#{param.street}\",\"flag\":true}}", standardJson);


        }catch(Exception e){
            logger.error("",e);
            Assert.fail();
        }

    }

}
