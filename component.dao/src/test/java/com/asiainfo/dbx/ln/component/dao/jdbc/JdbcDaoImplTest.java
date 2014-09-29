package com.asiainfo.dbx.ln.component.dao.jdbc;

import com.asiainfo.dbx.ln.component.dao.ResultOperator;
import com.asiainfo.dbx.ln.component.dao.RowLimit;

import com.asiainfo.dbx.ln.component.dao.mybatis.mapper.mysql108.component.Person;
import com.asiainfo.dbx.ln.component.util.AppDateUtils;
import com.asiainfo.dbx.ln.component.util.AppStringUtils;
import org.hamcrest.core.*;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.sql.Types;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


/**
 * 对JdbcDaoImpl进行单元测试
 * Created by yanlei on 2014/8/6.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value={"classpath*:config/spring/base/component*.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JdbcDaoImplTest {

    @Resource(name="jdbcDao")
    JdbcDao jdbcDao;

    public JdbcDao getJdbcDao() {
        return jdbcDao;
    }

    public void setJdbcDao(JdbcDao jdbcDao) {
        this.jdbcDao = jdbcDao;
    }
    private AtomicInteger id = new AtomicInteger();
    private  Iterator<Object []> getParameterIterator(final int count){
        return new Iterator<Object[]>(){
            int size = 0;
            @Override
            public boolean hasNext() {
                return size<count;
            }

            @Override
            public Object[] next() {
                size++;
                return  new Object[]{"join"+size, AppStringUtils.getUUID(),id.incrementAndGet(),"boy",new Date(),1.45F};

            }

            @Override
            public void remove() {

            }
        };
    }
    String insertSql="\n" +
            "INSERT INTO person\n" +
            "(name, id, identity_number, sex, birthday,stature)\n" +
            "VALUES(?, ?, ?, ?, ?,?)";
    String deleteSql="delete from person";
    String selectAllSql="select * from person order by identity_number";

    String selectByName="select * from person where name like ? order by identity_number";

    String selectByNameAndBirthday ="select * from person where name like ? and birthday =? order by identity_number";

    String selectByStature =" select * from person where stature >? order by identity_number ";

    @Test
    public void test1Delete(){
        int deleteNum = this.getJdbcDao().update(deleteSql);
        assertThat(true,IsEqual.equalTo(true));
    }
    int count = 10;
    @Test
    public void test2BatchUpdate(){

       int updateNum =  this.getJdbcDao().batchUpdate(insertSql,getParameterIterator(count));
        assertThat(updateNum, IsEqual.equalTo(count));

    }

    int foundNum =0;
    public void addFoundNum(int i){
        foundNum+=i;
    }
    @Test
    public void test3Select(){
        ResultOperator resultBeanOperator = new SelectBeanResultOperator();
        List<Integer> countList =  new ArrayList<Integer>(1);
        this.foundNum =0;
        this.getJdbcDao().select(selectAllSql,Person.class ,resultBeanOperator);
        assertThat(this.count,IsEqual.equalTo(foundNum));

        this.getJdbcDao().select(selectByName,new Object[]{"join%"},Person.class ,resultBeanOperator);
        this.getJdbcDao().select(selectByNameAndBirthday,new Object[]{"join%",new Date()},new int []{Types.VARCHAR,Types.DATE},Person.class ,resultBeanOperator);

        ResultOperator resultMapOperator = new SelectMapResultOperator();

        this.foundNum =0;
        this.getJdbcDao().select(selectAllSql ,resultMapOperator);
        assertThat(this.count,IsEqual.equalTo(foundNum));

        this.getJdbcDao().select(selectByName,new Object[]{"join%"},resultMapOperator);
        this.getJdbcDao().select(selectByNameAndBirthday,new Object[]{"join%",new Date()},new int []{Types.VARCHAR,Types.DATE},resultMapOperator);



    }
    @Test
    public void test4Selectlist() throws Exception{
        List<Person> personsList=  this.getJdbcDao().selectList(selectAllSql,Person.class);

        assertSelectList(personsList);
        personsList=  this.getJdbcDao().selectList(selectByStature,new Object []{1},Person.class);
        assertSelectList(personsList);
        personsList=  this.getJdbcDao().selectList(selectByStature,new Object []{1},new int []{Types.FLOAT},Person.class);
        assertSelectList(personsList);

        RowLimit rowLimit = new RowLimit.Builder().offset(0).limit(3).build();
        personsList=  this.getJdbcDao().selectList(selectByStature,new Object []{1},new int []{Types.FLOAT},Person.class,rowLimit);
        assertThat(3, IsEqual.equalTo(personsList.size()));

       List<Map<String,Object>> resultMapList=  this.getJdbcDao().selectList(selectAllSql);

        assertSelectListMap(resultMapList);
        resultMapList=  this.getJdbcDao().selectList(selectByStature,new Object []{1} );
        assertSelectListMap(resultMapList);
        resultMapList=  this.getJdbcDao().selectList(selectByStature,new Object []{1},new int []{Types.FLOAT});
        assertSelectListMap(resultMapList);

        resultMapList=  this.getJdbcDao().selectList(selectByStature,new Object []{1},new int []{Types.FLOAT},new RowLimit.Builder().offset(0).limit(3).build());
        assertThat(3,IsEqual.equalTo(resultMapList.size()));


    }

    String selectOneSql="select * from person  ";
    String selectOneSql1="select * from person  where name =?";

    String selectOneFieldSql="select birthday from person  where name =?";

    @Test
    public void test4SelectOne(){
        Person person = this.getJdbcDao().selectOne(selectOneSql,Person.class);
        assertThat(person, IsNull.notNullValue());
         person = this.getJdbcDao().selectOne(selectOneSql1,new Object []{"join1"},Person.class);
        assertThat(person, IsNull.notNullValue());
        person = this.getJdbcDao().selectOne(selectOneSql1,new Object []{"join1"},new int []{Types.VARCHAR},Person.class);
        assertThat(person, IsNull.notNullValue());

        Date date =  this.getJdbcDao().selectOne(this.selectOneFieldSql,new Object []{"join1"},Date.class);
        assertThat(AppDateUtils.getDateNoTime(date),IsEqual.equalTo(AppDateUtils.getCurrentDateNoTime()));

        Map<String,Object> map = this.getJdbcDao().selectOne(selectOneSql);
        assertThat(person, IsNull.notNullValue());
        map = this.getJdbcDao().selectOne(selectOneSql1,new Object []{"join1"});
        assertThat(person, IsNull.notNullValue());
        map = this.getJdbcDao().selectOne(selectOneSql1,new Object []{"join1"},new int []{Types.VARCHAR});
        assertThat(person, IsNull.notNullValue());
    }

    String updateSql="update person set stature=2.5 where name='join1'";
    String updateSql1="update person set stature=2.5 where name=?";
    @Test
    public void test5Update(){
        int updateNum = this.getJdbcDao().update(updateSql);
        assertTrue(updateNum>0);
        updateNum = this.getJdbcDao().update(updateSql1,new Object []{"join2"});
        assertTrue(updateNum>0);
        updateNum = this.getJdbcDao().update(updateSql1,new Object []{"join3"},new int []{Types.VARCHAR});
        assertTrue(updateNum>0);
    }
    private void assertSelectListMap( List<Map<String,Object>> resultMapList){
        assertThat(this.count,IsEqual.equalTo(resultMapList.size()));
        for(Map<String,Object> map:resultMapList){
            assertThat((String)map.get("name"), StringStartsWith.startsWith("join"));
        }
    }
    private void assertSelectList(List<Person> personsList){
        assertThat(this.count,IsEqual.equalTo(personsList.size()));
        for(Person person:personsList){
            assertThat(person.getName(), StringStartsWith.startsWith("join"));
        }
    }
    class  SelectBeanResultOperator implements ResultOperator<Person>{

        public boolean deal(Person resultObject, int currentRowNum) {
            assertThat(resultObject.getName(), StringStartsWith.startsWith("join"));
            assertThat(resultObject.getIdentityNumber(),IsEqual.equalTo(currentRowNum));
            addFoundNum(1);
            return true;
        }
    }
    class  SelectMapResultOperator implements ResultOperator<Map<String,Object>>{

        public boolean deal(Map<String,Object> resultObject, int currentRowNum) {
            assertThat((String)resultObject.get("name"), StringStartsWith.startsWith("join"));
            assertThat((Integer)resultObject.get("identity_number"),IsEqual.equalTo(currentRowNum));
            addFoundNum(1);
            return true;
        }
    }


}
