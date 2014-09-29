package com.asiainfo.dbx.ln.component.var;

import com.asiainfo.dbx.ln.component.api.AppComponent;
import com.asiainfo.dbx.ln.component.spring.impl.ApplicationContextHolderParentFixed;
import com.asiainfo.dbx.ln.component.util.AppDateUtils;
import com.asiainfo.dbx.ln.component.util.AppVarUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;


/**
 * Created by yanlei on 2014/7/11.
 */
@SuppressWarnings("unchecked")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath*:config/spring/base/component*.xml"})
public class TestAppVarUtils {
    private final Logger logger = LoggerFactory.getLogger(TestAppVarUtils.class);
    Person person = null;

    @Before
    public void beforeTest()  throws Exception{
        AppComponent.start();
        person = PersonHolder.createPerson();

        AppVarUtils.getVarContainer(this.getClass()).setVar("person", person);
    }

    @Test
    public void testBean() throws Exception {


        String name = (String)  AppVarUtils.getVarContainer(this.getClass()).getVar("@{person.name}");
        Assert.assertEquals(person.getName(), name);

        Integer age = (Integer)  AppVarUtils.getVarContainer(this.getClass()).getVar("@{person.age}");
        Assert.assertEquals(person.getAge(), age);

        Character sex = (Character)  AppVarUtils.getVarContainer(this.getClass()).getVar("@{person.sex}");
        Assert.assertEquals(person.getSex(), sex.charValue());

        Float stature = (Float)  AppVarUtils.getVarContainer(this.getClass()).getVar("@{person.stature}");
        assertThat(person.getStature(), equalTo(stature.floatValue()));

        String street = (String)  AppVarUtils.getVarContainer(this.getClass()).getVar("@{person.address.street}");
        Assert.assertEquals(person.getAddress().getStreet(), street);

    }


    @Test
    public void testDate() throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
       Object obj =  AppVarUtils.getVarContainer(this.getClass()).getVar("@{DATE.@{SYSDATE}.dd-1.yyyyMMd}");
        String datestr = (String) AppVarUtils.getVarContainer(this.getClass()).getVar("@{DATE.@{SYSDATE}.yyyyMMdd}");
        assertThat(datestr,equalTo(sdf1.format(new Date())));


        Date date = (Date) AppVarUtils.getVarContainer(this.getClass()).getVar("@{SYSDATE}");
        Assert.assertEquals(sdf.format(new Date()),sdf.format(date));


         AppVarUtils.getVarContainer(this.getClass()).setVar("today",new Date());
         AppVarUtils.getVarContainer(this.getClass()).setVar("dateStr","@{DATE.@{today}.dd-1.MM+1.YYYY+1.yyyy-MM-dd HH:mm}");
        String actualDateStr = (String) AppVarUtils.getVarContainer(this.getClass()).getVar("@{dateStr}");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        calendar.add(Calendar.MONTH,1);
        calendar.add(Calendar.YEAR,1);
        String expectDateStr = sdf.format(calendar.getTime());

        Assert.assertEquals(expectDateStr,actualDateStr);
        String str = (String)AppVarUtils.getVarContainer(this.getClass()).getVar("^05724.999.00.@{DATE.@{SYSDATE}.dd-1.yyyy-MM-dd}");
         calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);

         expectDateStr = sdf2.format(calendar.getTime());
         String expectStr = "^05724.999.00."+expectDateStr;
        assertThat(str,equalTo(expectStr));
    }
    @Test
    public void testJson() throws Exception{

        AppVarUtils.getVarContainer(this.getClass()).setVar("age","33");
        AppVarUtils.getVarContainer(this.getClass()).setVar("personMessage","JSON.{name:yanlei,age:@{age}}");
        AppVarUtils.getVarContainer(this.getClass()).setVar("age","99");//改成99
        String age = (String) AppVarUtils.getVarContainer(this.getClass()).getVar("@{personMessage.age}");//获取时执行
        assertThat(age,equalTo("99"));//值是最新的

        AppVarUtils.getVarContainer(this.getClass()).setVar("age","33");
        AppVarUtils.getVarContainer(this.getClass()).setVar("personMessage","@{JSON.{name:yanlei,age:@{age}}}");//立即执行
        AppVarUtils.getVarContainer(this.getClass()).setVar("age","99");//改成99
         age = (String) AppVarUtils.getVarContainer(this.getClass()).getVar("@{personMessage.age}");
        assertThat(age,equalTo("33"));//是设置变量时的值


    }
    @Test
    public void testMap()  throws Exception{
        Map map = new HashMap();
        map.put("birthday",AppDateUtils.parseString("19801117"));
        map.put("name","yanlei");
        map.put("sex","man");

         AppVarUtils.getVarContainer(this.getClass()).setVar("testPerson",map);
        String  name = (String) AppVarUtils.getVarContainer(this.getClass()).getVar("@{testPerson.sex}");
        Assert.assertEquals(((String)map.get("sex")),name);



    }

    @Test
    public void testSelection() throws Exception{
        List list = (List) AppVarUtils.getVarContainer(this.getClass()).getVar("@{person.studyExperienceList.{?#this.schoolType>2}}");//取studyExperienceList中符合schoolType>2的所有元素
        Assert.assertEquals(2,list.size());

        Object lastSchoolName = (Object) AppVarUtils.getVarContainer(this.getClass()).getVar("@{person.studyExperienceList.{$#this.schoolType>2}[0].schoolName}");//取studyExperienceList中符合schoolType>2的最后个元素的schoolName属性
        Assert.assertEquals("建设大学",lastSchoolName);

        Object firstSchoolName = (Object) AppVarUtils.getVarContainer(this.getClass()).getVar("@{person.studyExperienceList.{^#this.schoolType<2}[0].schoolName}");//取studyExperienceList中符合schoolType<2的第一个元素的schoolName属性
        Assert.assertEquals("建设小学",firstSchoolName);

    }

    @Test
    public void testEqual(){
        try {
             AppVarUtils.getVarContainer(this.getClass()).setVar("foo","");
            Boolean flag = (Boolean) AppVarUtils.getVarContainer(this.getClass()).getVar("@{foo==''}");//空判断
            Assert.assertEquals(true,flag.booleanValue());
             AppVarUtils.getVarContainer(this.getClass()).setVar("name",null);//null 判断
            flag = (Boolean) AppVarUtils.getVarContainer(this.getClass()).getVar("@{name==null}");
            Assert.assertEquals(true,flag.booleanValue());

            flag = (Boolean) AppVarUtils.getVarContainer(this.getClass()).getVar("@{noExist==null}");//不存在判断
            Assert.assertEquals(true,flag.booleanValue());




             AppVarUtils.getVarContainer(this.getClass()).setVar("age","35");
            flag = (Boolean) AppVarUtils.getVarContainer(this.getClass()).getVar("@{age==35}");
            Assert.assertEquals(true,flag.booleanValue());
        }catch(Exception e){
            logger.error("",e);
            Assert.fail();

        }
    }
    @Test
    public void testLogic(){
        try {
             AppVarUtils.getVarContainer(this.getClass()).setVar("num","10");
             AppVarUtils.getVarContainer(this.getClass()).setVar("flag","true");
            String flag = (String) AppVarUtils.getVarContainer(this.getClass()).getVar("@{num>5&&flag}");
            Assert.assertEquals(true,Boolean.valueOf(flag).booleanValue());

            Object result = (Object) AppVarUtils.getVarContainer(this.getClass()).getVar("@{(num>5&&flag)?person.name+'10':person.age+1}");
            Assert.assertEquals("yanlei10",result.toString());

        }catch(Exception e){
            logger.error("",e);
            Assert.fail();

        }
    }

    @Test
    public void testString(){
        try {
             AppVarUtils.getVarContainer(this.getClass()).setVar("num","10");
             AppVarUtils.getVarContainer(this.getClass()).setVar("flag","true");
            String str = (String) AppVarUtils.getVarContainer(this.getClass()).getVar("num = @{num},flag = @{flag},person.age=@{person.age}");
            Assert.assertEquals("num = 10,flag = true,person.age="+person.getAge(),str);

             str = (String) AppVarUtils.getVarContainer(this.getClass()).getVar("@{DATE.@{SYSDATE}.yyyyMMdd}/photo");
             System.out.println(str);
        }catch(Exception e){
            logger.error("",e);
            Assert.fail();
        }
    }
    @Test
    public void testUUID(){
        try {
           String unique= (String)  AppVarUtils.getVarContainer(this.getClass()).getVar("@{UNIQUE}");
            logger.info("unique:{}",unique);
        }catch (Exception e){
            logger.error("",e);
            Assert.fail();
        }
    }
    @Test
    public void testMethod(){
        try {
            List result = new ArrayList();
            result.add("A");
            result.add("B");
            result.add("C");

             AppVarUtils.getVarContainer(this.getClass()).setVar("result",result);

            Integer val = (Integer) AppVarUtils.getVarContainer(this.getClass()).getVar("@{result!=null?result.size():0}");
            Assert.assertEquals(3,val.intValue());
        }catch(Exception e){
            logger.error("",e);
            Assert.fail();
        }
    }

}
