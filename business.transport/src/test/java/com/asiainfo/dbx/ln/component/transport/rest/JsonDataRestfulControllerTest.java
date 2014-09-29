package com.asiainfo.dbx.ln.component.transport.rest;

import com.asiainfo.dbx.ln.component.api.AppComponent;
import com.asiainfo.dbx.ln.business.service.ServiceConstant;
import com.asiainfo.dbx.ln.business.transport.http.rest.JsonDataRestfulController;
import com.asiainfo.dbx.ln.component.util.AppJsonUtils;
import com.asiainfo.dbx.ln.component.util.AppSpringUtils;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Map;


import static org.junit.Assert.fail;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by yanlei on 2014/9/5.
 */

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JsonDataRestfulControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(JsonDataRestfulControllerTest.class);


    private static  MockMvc mockMvc;

    @BeforeClass
    public static void before(){
        try {
            AppComponent.start();
            JsonDataRestfulController controller = (JsonDataRestfulController)AppSpringUtils.getApplicationContextHolder().getBean("jsonDataRestfulController");
            mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        }catch(Exception e){
            logger.error("",e);
        }
    }
    String requestUrl = "/vgop/data/mysql108/component/person";
    @Test
    public void test1Delete(){
        try{
            String jsonString="{\""+ServiceConstant.QUERY_PRAMETER_NAME+"\":{\"name\":{\"$like\":\"pig%\"}}}";
            JsonDataRestfulControllerTest.mockMvc.perform(MockMvcRequestBuilders.delete(requestUrl).contentType(MediaType.APPLICATION_JSON).content(jsonString.getBytes()));

        }catch(Exception e){
            fail();
        }
    }

    @Test
    public void test2Create(){
        try {

            for(int i=0;i<10;i++){
                String jsonString1="{\""+ ServiceConstant.MODEL_PRAMETER_NAME+"\":{\"id\":\"@{UNIQUE}\",\"name\":\"pig"+i+"\",\"stature\":1.61,\"sex\":\"girl\",\"birthday\":\"19910402\",\"identityNumber\":100001}}";

                MvcResult mvcResult = JsonDataRestfulControllerTest.mockMvc.perform(MockMvcRequestBuilders
                        .post(requestUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString1.getBytes())
                        ).andReturn();
                Map effectModel =  readEffectModel(mvcResult,1);
                assertThat((String)effectModel.get("name"),equalTo("pig"+i));
            }
        }catch(Throwable e){
            logger.error("",e);
            fail();
        }
    }


    private Map readByIdResult(MvcResult mvcResult,int expectEfectNum) throws Exception{
        assertThat(mvcResult.getResponse().getStatus(),equalTo(HttpStatus.OK.value()));
        String body = mvcResult.getResponse().getContentAsString();
        logger.info("result:{}",body);
        Map map = AppJsonUtils.jsonToMap(body);
        assertThat((String)map.get("status"),equalTo("0"));
        Map result = (Map)map.get("result");
        assertThat(result, notNullValue());
        String readNum = (String)result.get("readNum");
        assertThat(readNum,equalTo(expectEfectNum+""));
        Map effectModel = (Map)result.get("dataModel");
        return effectModel;

    }

    private Map readEffectModel(MvcResult mvcResult,int expectEfectNum) throws Exception{
        assertThat(mvcResult.getResponse().getStatus(),equalTo(HttpStatus.OK.value()));
        String body = mvcResult.getResponse().getContentAsString();
        logger.info("result:{}",body);
        Map map = AppJsonUtils.jsonToMap(body);
        assertThat((String)map.get("status"),equalTo("0"));
        Map result = (Map)map.get("result");
        assertThat(result, notNullValue());
        String effectRecordNum1 = (String)result.get("effectRecordNum");
        assertThat(effectRecordNum1,equalTo(expectEfectNum+""));
        Map effectModel = (Map)result.get("effectModel");
        return effectModel;

    }
    private Map readFirstObjectOfDataList( MvcResult mvcResult, String readNum) throws Exception{
        assertThat(mvcResult.getResponse().getStatus(),equalTo(HttpStatus.OK.value()));
        String body = mvcResult.getResponse().getContentAsString();
        logger.info("result:{}",body);
        Map map = AppJsonUtils.jsonToMap(body);
        assertThat((String)map.get("status"),equalTo("0"));
        Map result = (Map)map.get("result");
        String readNum1 = (String)result.get("readNum");
        assertThat(readNum1,equalTo(""+readNum));
        List dataList = (List)result.get("dataList");
        Map personMap  = (Map)dataList.get(0);
       return personMap;

    }
    private void  asssertUpdateEffectNum( MvcResult mvcResult, String effectNum) throws Exception{
        assertThat(mvcResult.getResponse().getStatus(),equalTo(HttpStatus.OK.value()));
       String  body = mvcResult.getResponse().getContentAsString();
        logger.info("result:{}",body);
      Map   map = AppJsonUtils.jsonToMap(body);
        assertThat((String)map.get("status"),equalTo("0"));
      Map   result = (Map)map.get("result");
        String  effectRecordNum = (String)result.get("effectRecordNum");
        assertThat(effectRecordNum,equalTo(effectNum));

    }
        @Test
        public void test3Read(){
            try{
                String jsonString="{\""+ServiceConstant.QUERY_PRAMETER_NAME+"\":{\"name\":{\"$like\":\"pig%\"}}}";
                MvcResult mvcResult = JsonDataRestfulControllerTest.mockMvc.perform(MockMvcRequestBuilders.get(requestUrl).contentType(MediaType.APPLICATION_JSON).content(jsonString.getBytes())).andReturn();
                Map personMap = readFirstObjectOfDataList(mvcResult, "10");
                String id = (String)personMap.get("id");


                String readByIdUrl = requestUrl+"/"+id;
                 mvcResult = JsonDataRestfulControllerTest.mockMvc.perform(MockMvcRequestBuilders.get(readByIdUrl).contentType(MediaType.APPLICATION_JSON)).andReturn();
                 personMap = readByIdResult(mvcResult,1);
                String readId = (String)personMap.get("id");
                assertThat(id,equalTo(readId));


            }catch(Exception e){
                logger.error("",e);
                fail();
            }
        }
    @Test
    public void test4Update(){
        try{
            String jsonString="{\""+ServiceConstant.QUERY_PRAMETER_NAME+"\":{\"name\":{\"$like\":\"pig%\"}}}";
            MvcResult mvcResult = JsonDataRestfulControllerTest.mockMvc.perform(MockMvcRequestBuilders.get(requestUrl).contentType(MediaType.APPLICATION_JSON).content(jsonString.getBytes())).andReturn();
            Map  personMap = readFirstObjectOfDataList(mvcResult,"10");
            String id = (String)personMap.get("id");
            String updateJsonString = "{\""+ ServiceConstant.MODEL_PRAMETER_NAME+"\":{\"name\":\"pig10\",\"stature\":2.61,\"sex\":\"girl119\",\"birthday\":\"20140402\",\"identityNumber\":200001},\""+ServiceConstant.QUERY_PRAMETER_NAME+"\":{\"id\":\""+id+"\"}}";
            mvcResult = JsonDataRestfulControllerTest.mockMvc.perform(MockMvcRequestBuilders.put(requestUrl).contentType(MediaType.APPLICATION_JSON).content(updateJsonString.getBytes())).andReturn();
            asssertUpdateEffectNum(mvcResult,"1");


            String readByIdUrl = requestUrl+"/"+id;
            mvcResult = JsonDataRestfulControllerTest.mockMvc.perform(MockMvcRequestBuilders.get(readByIdUrl).contentType(MediaType.APPLICATION_JSON)).andReturn();
            personMap = this.readByIdResult(mvcResult,1);
            String readId = (String)personMap.get("id");
            assertThat(id,equalTo(readId));
            assertThat((String)personMap.get("name"),equalTo("pig10"));
            assertThat((String)personMap.get("stature"),equalTo("2.61"));
            assertThat((String)personMap.get("sex"),equalTo("girl119"));

        }catch(Exception e){
            logger.error("",e);
            fail();
        }
    }

}
