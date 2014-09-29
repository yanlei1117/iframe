package com.asiainfo.dbx.ln.component.dao.mybatis;

import com.asiainfo.dbx.ln.component.dao.ResultOperator;
import com.asiainfo.dbx.ln.component.dao.RowLimit;


import com.asiainfo.dbx.ln.component.dao.mybatis.mapper.mysql108.component.Person;
import com.asiainfo.dbx.ln.component.util.AppStringUtils;
import com.asiainfo.dbx.ln.component.util.AppValidationUtils;
import org.hamcrest.core.IsEqual;
import static org.hamcrest.Matchers.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.*;

import static org.junit.Assert.assertThat;

/**
 * MyBatisDao 单元测试
 *
 * MyBatis配置文件 config/mybatis/mapper/Person.xml
 *
 * Created by yanlei on 2014/8/7.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value={"classpath*:config/spring/base/component*.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MyBatisDaoTest {
    private static final Logger logger = LoggerFactory.getLogger(MyBatisDaoTest.class);
    @Resource(name = "myBatisDao")
    public MyBatisDao myBatisDao;

    public MyBatisDao getMyBatisDao() {
        return myBatisDao;
    }
    private static int instanceCount =0;
   public void MyBatisDaoTest(){
       logger.info("MyBatisDaoTest created :"+(++instanceCount));
   }
    public void setMyBatisDao(MyBatisDao myBatisDao) {
        this.myBatisDao = myBatisDao;
    }


    static int index = 0;
    public Person createPerson() {
        index++;
        Person  person = new Person();


        person.setBirthday(new Date());
        String id = AppStringUtils.getUUID();
        person.setId(id);
        person.setStature(1.56f);
        person.setIdentityNumber(new Random().nextInt());
        person.setName("yanlei"+index);
        person.setSex("男");
        return person ;
    }

   @Test
    public  void  test1deleteAll(){
        this.getMyBatisDao().update("com.asiainfo.dbx.ln.component.dao.mybatis.mapper.test.Person.deleteAll");

       Integer count= this.getMyBatisDao().selectOne("com.asiainfo.dbx.ln.component.dao.mybatis.mapper.test.Person.selectToCount");
        assertThat(0, IsEqual.equalTo(count));
    }

    @Test
    public void test2Insert(){
        Person person = this.createPerson();
      this.getMyBatisDao().update("com.asiainfo.dbx.ln.component.dao.mybatis.mapper.test.Person.insertPerson",person);
        Integer count= this.getMyBatisDao().selectOne("com.asiainfo.dbx.ln.component.dao.mybatis.mapper.test.Person.selectToCount");
        assertThat(1, IsEqual.equalTo(count));
    }
    @Test
    public void test3BatchInsert(){
       this.getMyBatisDao().batchUpdate("com.asiainfo.dbx.ln.component.dao.mybatis.mapper.test.Person.insertPerson",new Iterator<Object>(){
           int count =10;
           int currentSize =0;
           @Override
           public boolean hasNext() {
               currentSize++;
               return currentSize<=count;
           }

           @Override
           public Person next() {
               return createPerson();
           }

           @Override
           public void remove() {

           }
       });
        Integer count= this.getMyBatisDao().selectOne("com.asiainfo.dbx.ln.component.dao.mybatis.mapper.test.Person.selectToCount");
        assertThat(count,greaterThanOrEqualTo(10));
    }
    int foundNum =0;
    public void addFoundNum(int i){
        foundNum+=i;
    }
    @Test
    public void test4Select(){
        Integer count= this.getMyBatisDao().selectOne("com.asiainfo.dbx.ln.component.dao.mybatis.mapper.test.Person.selectToCount");

        this.foundNum = 0;
        this.getMyBatisDao().select("com.asiainfo.dbx.ln.component.dao.mybatis.mapper.test.Person.selectAll",new ResultOperator<Person>() {
            @Override
            public boolean deal(Person resultObject, int currentRowNum) {
                addFoundNum(1);
                return true;
            }
        });

        assertThat(foundNum,equalTo(count));
        this.foundNum = 0;
        this.getMyBatisDao().select("com.asiainfo.dbx.ln.component.dao.mybatis.mapper.test.Person.selectByLinkName","yanlei%",new ResultOperator<Person>() {
            @Override
            public boolean deal(Person resultObject, int currentRowNum) {
                addFoundNum(1);
                return true;
            }
        });
        assertThat(foundNum,equalTo(count));
    }

    @Test
    public void test5SelectList(){
       Integer count= this.getMyBatisDao().selectOne("com.asiainfo.dbx.ln.component.dao.mybatis.mapper.test.Person.selectToCount");
       List<Person> personList =  this.getMyBatisDao().selectList("selectAll");
       if(AppValidationUtils.notEmpty(personList)){
           assertThat(personList.size(),equalTo(count));
       }else{

           assertThat(count,is(0));
       }
        personList =  this.getMyBatisDao().selectList("com.asiainfo.dbx.ln.component.dao.mybatis.mapper.test.Person.selectByLinkName","yanlei%");
        if(AppValidationUtils.notEmpty(personList)){
            assertThat(personList.size(),equalTo(count));
        }else{

            assertThat(count,is(0));
        }
        personList =  this.getMyBatisDao().selectList("com.asiainfo.dbx.ln.component.dao.mybatis.mapper.test.Person.selectByLinkName","yanlei%",new RowLimit.Builder().limit(3).offset(0).build());
        if(AppValidationUtils.notEmpty(personList)){
            assertThat(personList.size(),lessThanOrEqualTo(3));
        }else{
            assertThat(count,is(0));
        }

    }
    @Test
    public void test6SelectMap(){
        Integer count= this.getMyBatisDao().selectOne("com.asiainfo.dbx.ln.component.dao.mybatis.mapper.test.Person.selectDistinctNameCount");
        Map<String,Person> personMap = this.getMyBatisDao().selectMap("com.asiainfo.dbx.ln.component.dao.mybatis.mapper.test.Person.selectAll","name");
        if(AppValidationUtils.notEmpty(personMap)){
            assertThat(personMap.size(),equalTo(count));
            Iterator<String> iterator = personMap.keySet().iterator();
            while(iterator.hasNext()){
                String name = iterator.next();
                assertThat(name,startsWith("yanlei"));
            }
        }else{
            assertThat(count,is(0));
        }

        personMap = this.getMyBatisDao().selectMap("com.asiainfo.dbx.ln.component.dao.mybatis.mapper.test.Person.selectByLinkName","yanlei%","name");
        if(AppValidationUtils.notEmpty(personMap)){
            assertThat(personMap.size(),equalTo(count));
            Iterator<String> iterator = personMap.keySet().iterator();
            while(iterator.hasNext()){
                String name = iterator.next();
                assertThat(name,startsWith("yanlei"));
            }
        }else{
            assertThat(count,is(0));
        }

    }

    @Test
    public void test7SelectOne(){
        List<Person> personList = this.getMyBatisDao().selectList("com.asiainfo.dbx.ln.component.dao.mybatis.mapper.test.Person.selectAll");
        Integer count= this.getMyBatisDao().selectOne("com.asiainfo.dbx.ln.component.dao.mybatis.mapper.test.Person.selectToCount");
        assertThat(count,equalTo(personList.size()));
        Person person = personList.get(0);

        Person  newPerson =  this.getMyBatisDao().selectOne("com.asiainfo.dbx.ln.component.dao.mybatis.mapper.test.Person.selectById",person.getId());
        assertThat(newPerson.getIdentityNumber(),equalTo(newPerson.getIdentityNumber()));
        Person p1 =  this.getMyBatisDao().selectOne("com.asiainfo.dbx.ln.component.dao.mybatis.mapper.test.Person.selectByArrayParam",new Object[]{newPerson.getName(),newPerson.getBirthday()});
        assertThat(p1.getId(),equalTo(newPerson.getId()));

        List list = new ArrayList();
        list.add(newPerson.getName());
        list.add(newPerson.getBirthday());
         p1 =  this.getMyBatisDao().selectOne("com.asiainfo.dbx.ln.component.dao.mybatis.mapper.test.Person.selectByListParam",list);
        assertThat(p1.getId(),equalTo(newPerson.getId()));

        Map map = new HashMap();
        map.put("param1",newPerson.getName());
        map.put("param2",newPerson.getBirthday());
        p1 =  this.getMyBatisDao().selectOne("com.asiainfo.dbx.ln.component.dao.mybatis.mapper.test.Person.selectByMapParam",map);
        assertThat(p1.getId(),equalTo(newPerson.getId()));


    }

    @Test
    public void test8Update(){
        List<Person> personList = this.getMyBatisDao().selectList("com.asiainfo.dbx.ln.component.dao.mybatis.mapper.test.Person.selectAll");
        Person person = personList.get(0);
        person.setName("xxdd");
        this.getMyBatisDao().update("com.asiainfo.dbx.ln.component.dao.mybatis.mapper.test.Person.updatePerson",person);
        Person newPerson = this.getMyBatisDao().selectOne("com.asiainfo.dbx.ln.component.dao.mybatis.mapper.test.Person.selectById",person.getId());
        assertThat(newPerson.getName(),equalTo("xxdd"));

    }
    public void test9Delete(){
       // this.getMyBatisDao().update("deletePersonById",person.getId());
    }



}
