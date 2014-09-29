package com.asiainfo.dbx.ln.business.service.data;

import com.asiainfo.dbx.ln.component.dao.mybatis.MyBatisExample;
import com.asiainfo.dbx.ln.component.dao.mybatis.MyBatisExampleFactory;
import com.asiainfo.dbx.ln.component.util.AppJsonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by yanlei on 2014/8/28.
 */
@SuppressWarnings("unchecked")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value={"classpath*:config/spring/base/component*.xml"})
public class TestJsonToExample {
    private Logger logger = LoggerFactory.getLogger(TestJsonToExample.class);
    @Test
    public void test(){
       String str1 =  "{\"name\":\"yanlei\",\"age\":\"30\",\"sex\":\"boy\",\"id\":\"1193\"}";
       String str2 =  "{\"name\":\"yanlei\",\"age\":{\"$ge\":\"25\",\"$le\":\"35\"},\"sex\":{\"$in\":[\"boy\",\"girl\"]},\"id\":{\"$between\":[\"1000\",\"1900\"]}}";
       String str3 =  "{\"name\":\"yanlei\",\"$or\":{\"age\":\"32\",\"age\":\"33\"}}";
        JsonToExample jsonToExample = new JsonToExample();
        try {

            MyBatisExample myBatisExample = MyBatisExampleFactory.createMyBatisExample();
            Map<String, Object> jsonMap = AppJsonUtils.jsonToMap(str1);
            myBatisExample =  jsonToExample.toExample(jsonMap, Pojo.class,myBatisExample);
            assertThat(myBatisExample,notNullValue());

            jsonMap = AppJsonUtils.jsonToMap(str2);
            myBatisExample =  jsonToExample.toExample(jsonMap, Pojo.class,myBatisExample);
            assertThat(myBatisExample,notNullValue());

            jsonMap = AppJsonUtils.jsonToMap(str3);
            myBatisExample =  jsonToExample.toExample(jsonMap, Pojo.class,myBatisExample);
            assertThat(myBatisExample,notNullValue());
        }catch(Exception e){
            logger.error("",e);
            fail();
        }
    }
}
