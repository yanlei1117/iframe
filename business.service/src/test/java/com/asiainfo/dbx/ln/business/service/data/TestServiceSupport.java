package com.asiainfo.dbx.ln.business.service.data;

import com.asiainfo.dbx.ln.component.api.AppComponent;
import com.asiainfo.dbx.ln.component.dao.mybatis.mapper.mysql108.component.Person;
import com.asiainfo.dbx.ln.business.service.*;

import com.asiainfo.dbx.ln.business.service.operation.OperationDefineFactory;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefine;
import com.asiainfo.dbx.ln.business.service.resource.ResourceDefineFactory;
import com.asiainfo.dbx.ln.component.util.AppSpringUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by yanlei on 2014/8/29.
 */
@RunWith(JUnit4.class)
public class TestServiceSupport {
    private final Logger logger = LoggerFactory.getLogger(TestServiceSupport.class);
    @BeforeClass
    public  static void  before() throws Exception{
        new AppComponent().start();
    }

    public ServiceSupport getServiceSupport() {
        return (ServiceSupport)AppSpringUtils.getApplicationContextHolder().getBean("defaultServiceSupport");
    }

    @Test
    public void test(){


        ResourceDefine resourceDefine = ResourceDefineFactory.createDataResourceDefine("mysql108","component", "person");

        String jsonString1="{\""+ServiceConstant.MODEL_PRAMETER_NAME+"\":{\"id\":\"@{UNIQUE}\",\"name\":\"petter1\",\"stature\":1.61,\"sex\":\"girl\",\"birthday\":\"19910402\",\"identityNumber\":100001}}";
        String jsonString2="{\""+ServiceConstant.MODEL_PRAMETER_NAME+"\":{\"id\":\"@{UNIQUE}\",\"name\":\"petter2\",\"stature\":1.62,\"sex\":\"girl\",\"birthday\":\"19920402\",\"identityNumber\":100002}}";
        String jsonString3="{\""+ServiceConstant.MODEL_PRAMETER_NAME+"\":{\"id\":\"@{UNIQUE}\",\"name\":\"petter3\",\"stature\":1.63,\"sex\":\"girl\",\"birthday\":\"19930402\",\"identityNumber\":100003}}";
        String jsonString4="{\""+ServiceConstant.MODEL_PRAMETER_NAME+"\":{\"id\":\"@{UNIQUE}\",\"name\":\"petter4\",\"stature\":1.64,\"sex\":\"girl\",\"birthday\":\"19940402\",\"identityNumber\":100004}}";
        String jsonString5="{\""+ServiceConstant.MODEL_PRAMETER_NAME+"\":{\"id\":\"@{UNIQUE}\",\"name\":\"petter5\",\"stature\":1.65,\"sex\":\"girl\",\"birthday\":\"19950402\",\"identityNumber\":100005}}";
        String jsonString6="{\""+ServiceConstant.MODEL_PRAMETER_NAME+"\":{\"id\":\"@{UNIQUE}\",\"name\":\"petter6\",\"stature\":1.66,\"sex\":\"girl\",\"birthday\":\"19960402\",\"identityNumber\":100006}}";
        String jsonString7="{\""+ServiceConstant.MODEL_PRAMETER_NAME+"\":{\"id\":\"@{UNIQUE}\",\"name\":\"petter7\",\"stature\":1.67,\"sex\":\"girl\",\"birthday\":\"19970402\",\"identityNumber\":100007}}";
        String jsonString8="{\""+ServiceConstant.MODEL_PRAMETER_NAME+"\":{\"id\":\"@{UNIQUE}\",\"name\":\"petter8\",\"stature\":1.68,\"sex\":\"girl\",\"birthday\":\"19980402\",\"identityNumber\":100008}}";
        String jsonString9="{\""+ServiceConstant.MODEL_PRAMETER_NAME+"\":{\"id\":\"@{UNIQUE}\",\"name\":\"petter9\",\"stature\":1.69,\"sex\":\"girl\",\"birthday\":\"19990402\",\"identityNumber\":100009}}";
        String jsonString0="{\""+ServiceConstant.MODEL_PRAMETER_NAME+"\":{\"id\":\"@{UNIQUE}\",\"name\":\"petter0\",\"stature\":1.60,\"sex\":\"girl\",\"birthday\":\"19900402\",\"identityNumber\":100000}}";

        String queryAllJson="{\""+ ServiceConstant.QUERY_PRAMETER_NAME+"\":{\"name\":{\"$like\":\"petter%\"}}}";
        try{

            Integer deleteNum =  this.getServiceSupport().call(resourceDefine, OperationDefineFactory.getOperationDefineForDelete(),queryAllJson);
            Person person1  = this.getServiceSupport().call(resourceDefine,OperationDefineFactory.getOperationDefineForCreate(),jsonString1);
            assertThat(person1.getName(), equalTo("petter1"));
            Person person2 = this.getServiceSupport().call(resourceDefine,OperationDefineFactory.getOperationDefineForCreate(),jsonString2);
            assertThat(person2.getName(), equalTo("petter2"));

            Person person3 = this.getServiceSupport().call(resourceDefine,OperationDefineFactory.getOperationDefineForCreate(),jsonString3);
            assertThat(person3.getName(), equalTo("petter3"));
            Person person4 = this.getServiceSupport().call(resourceDefine,OperationDefineFactory.getOperationDefineForCreate(),jsonString4);
            assertThat(person4.getName(), equalTo("petter4"));
            Person person5 = this.getServiceSupport().call(resourceDefine,OperationDefineFactory.getOperationDefineForCreate(),jsonString5);
            assertThat(person5.getName(), equalTo("petter5"));
            Person person6 = this.getServiceSupport().call(resourceDefine,OperationDefineFactory.getOperationDefineForCreate(),jsonString6);
            assertThat(person6.getName(), equalTo("petter6"));
            Person person7 = this.getServiceSupport().call(resourceDefine,OperationDefineFactory.getOperationDefineForCreate(),jsonString7);
            assertThat(person7.getName(), equalTo("petter7"));
            Person person8 = this.getServiceSupport().call(resourceDefine,OperationDefineFactory.getOperationDefineForCreate(),jsonString8);
            assertThat(person8.getName(), equalTo("petter8"));
            Person person9 = this.getServiceSupport().call(resourceDefine,OperationDefineFactory.getOperationDefineForCreate(),jsonString9);
            assertThat(person9.getName(), equalTo("petter9"));
            Person person0= this.getServiceSupport().call(resourceDefine,OperationDefineFactory.getOperationDefineForCreate(),jsonString0);
            assertThat(person0.getName(), equalTo("petter0"));

            String queryJsonString="{\"queryParams\":{\"sex\":\"girl\",\"name\":{\"$like\":\"petter%\"},\"birthday\":{\"$gt\":\"19900101\",\"$lt\":\"20000101\"},\"identityNumber\":{\"$in\":[\"100001\",\"100002\",\"100003\",\"100004\",\"100005\",\"100006\",\"100007\",\"100008\",\"100009\",\"100000\"]},\"stature\":{\"$between\":[1.60,1.70]}}}";
            List<Person> personList  = this.getServiceSupport().call(resourceDefine,OperationDefineFactory.getOperationDefineForRead(),queryJsonString);
            assertThat(personList.size(), equalTo(10));


             queryJsonString="{\"limit\":5,\"queryParams\":{\"sex\":\"girl\",\"name\":{\"$like\":\"petter%\"},\"birthday\":{\"$gt\":\"19900101\",\"$lt\":\"20000101\"},\"identityNumber\":{\"$in\":[\"100001\",\"100002\",\"100003\",\"100004\",\"100005\",\"100006\",\"100007\",\"100008\",\"100009\",\"100000\"]},\"stature\":{\"$between\":[1.60,1.70]}}}";
             List  objectList  = this.getServiceSupport().call(resourceDefine,OperationDefineFactory.getOperationDefineForRead(),queryJsonString);
             assertThat(objectList.size(),equalTo(5));



            Person person = (Person)personList.get(0);
            Integer updateNum = this.getServiceSupport().call(resourceDefine,OperationDefineFactory.getOperationDefineForUpdate(),"{\""+ServiceConstant.MODEL_PRAMETER_NAME+"\":{\"name\":\"petter99\"},\""+ServiceConstant.QUERY_PRAMETER_NAME+"\":{\"id\":\""+person.getId()+"\"}}");
            assertThat(updateNum,equalTo(1));

            personList  = this.getServiceSupport().call(resourceDefine,OperationDefineFactory.getOperationDefineForRead(),"{\""+ServiceConstant.QUERY_PRAMETER_NAME+"\":{\"id\":\""+person.getId()+"\"}}");

            assertThat(personList.size(),equalTo(1));
            assertThat((personList.get(0)).getName(),equalTo("petter99"));

            String countJson = "{\"countExpression\":\"sum(stature)\",\""+ ServiceConstant.QUERY_PRAMETER_NAME+"\":{\"name\":{\"$like\":\"petter%\"}}}";
            Float totalStature = this.getServiceSupport().call(resourceDefine,OperationDefineFactory.getOperationDefineForCount(),countJson);
            assertThat(totalStature,equalTo(16.45f));

            deleteNum =  this.getServiceSupport().call(resourceDefine,OperationDefineFactory.getOperationDefineForDelete(),queryAllJson);
            assertThat(deleteNum,equalTo(10));

            personList = this.getServiceSupport().call(resourceDefine,OperationDefineFactory.getOperationDefineForRead(),queryAllJson);
            assertThat(personList.size(),equalTo(0));



            /**
             *  EffectRecord effectRecord =  this.getServiceSupport().call(resourceDefine,OperationDefineFactory.getOperationDefineForDelete(),queryAllJson);
             EffectRecord effectRecord1 = this.getServiceSupport().call(resourceDefine,OperationDefineFactory.getOperationDefineForCreate(),jsonString1);
             assertThat(((Person) effectRecord1.getEffectModel()).getName(), equalTo("petter1"));
             EffectRecord effectRecord2 = this.getServiceSupport().call(resourceDefine,OperationDefineFactory.getOperationDefineForCreate(),jsonString2);
             assertThat(((Person) effectRecord2.getEffectModel()).getName(), equalTo("petter2"));
             EffectRecord effectRecord3 = this.getServiceSupport().call(resourceDefine,OperationDefineFactory.getOperationDefineForCreate(),jsonString3);
             assertThat(((Person) effectRecord3.getEffectModel()).getName(), equalTo("petter3"));
             EffectRecord effectRecord4 = this.getServiceSupport().call(resourceDefine,OperationDefineFactory.getOperationDefineForCreate(),jsonString4);
             assertThat(((Person) effectRecord4.getEffectModel()).getName(), equalTo("petter4"));
             EffectRecord effectRecord5 = this.getServiceSupport().call(resourceDefine,OperationDefineFactory.getOperationDefineForCreate(),jsonString5);
             assertThat(((Person) effectRecord5.getEffectModel()).getName(), equalTo("petter5"));
             EffectRecord effectRecord6 = this.getServiceSupport().call(resourceDefine,OperationDefineFactory.getOperationDefineForCreate(),jsonString6);
             assertThat(((Person) effectRecord6.getEffectModel()).getName(), equalTo("petter6"));
             EffectRecord effectRecord7 = this.getServiceSupport().call(resourceDefine,OperationDefineFactory.getOperationDefineForCreate(),jsonString7);
             assertThat(((Person) effectRecord7.getEffectModel()).getName(), equalTo("petter7"));
             EffectRecord effectRecord8 = this.getServiceSupport().call(resourceDefine,OperationDefineFactory.getOperationDefineForCreate(),jsonString8);
             assertThat(((Person) effectRecord8.getEffectModel()).getName(), equalTo("petter8"));
             EffectRecord effectRecord9 = this.getServiceSupport().call(resourceDefine,OperationDefineFactory.getOperationDefineForCreate(),jsonString9);
             assertThat(((Person) effectRecord9.getEffectModel()).getName(), equalTo("petter9"));
             EffectRecord effectRecord0= this.getServiceSupport().call(resourceDefine,OperationDefineFactory.getOperationDefineForCreate(),jsonString0);
             assertThat(((Person) effectRecord0.getEffectModel()).getName(), equalTo("petter0"));

             String queryJsonString="{\"queryParams\":{\"sex\":\"girl\",\"name\":{\"$like\":\"petter%\"},\"birthday\":{\"$gt\":\"19900101\",\"$lt\":\"20000101\"},\"identityNumber\":{\"$in\":[\"100001\",\"100002\",\"100003\",\"100004\",\"100005\",\"100006\",\"100007\",\"100008\",\"100009\",\"100000\"]},\"stature\":{\"$between\":[1.60,1.70]}}}";
             DataReadResult dataReadResult  = this.getServiceSupport().call(resourceDefine,OperationDefineFactory.getOperationDefineForRead(),queryJsonString);
             assertThat(dataReadResult.getReadNum(), equalTo(10));


             queryJsonString="{\"limit\":5,\"queryParams\":{\"sex\":\"girl\",\"name\":{\"$like\":\"petter%\"},\"birthday\":{\"$gt\":\"19900101\",\"$lt\":\"20000101\"},\"identityNumber\":{\"$in\":[\"100001\",\"100002\",\"100003\",\"100004\",\"100005\",\"100006\",\"100007\",\"100008\",\"100009\",\"100000\"]},\"stature\":{\"$between\":[1.60,1.70]}}}";
             dataReadResult  = this.getServiceSupport().call(resourceDefine,OperationDefineFactory.getOperationDefineForRead(),queryJsonString);
             assertThat(dataReadResult.getReadNum(),equalTo(5));
             assertThat(dataReadResult.getDataList().size(), equalTo(5));
             assertThat(dataReadResult.getTotalNum(),equalTo(10));

             Person person = (Person)dataReadResult.getDataList().get(0);
             effectRecord = this.getServiceSupport().call(resourceDefine,OperationDefineFactory.getOperationDefineForUpdate(),"{\""+ServiceConstant.MODEL_PRAMETER_NAME+"\":{\"name\":\"petter99\"},\""+ServiceConstant.QUERY_PRAMETER_NAME+"\":{\"id\":\""+person.getId()+"\"}}");
             assertThat(effectRecord.getEffectRecordNum(),equalTo(1));

             dataReadResult  = this.getServiceSupport().call(resourceDefine,OperationDefineFactory.getOperationDefineForRead(),"{\""+ServiceConstant.QUERY_PRAMETER_NAME+"\":{\"id\":\""+person.getId()+"\"}}");

             assertThat(dataReadResult.getReadNum(),equalTo(1));
             assertThat(((Person)dataReadResult.getDataList().get(0)).getName(),equalTo("petter99"));


             effectRecord =  this.getServiceSupport().call(resourceDefine,OperationDefineFactory.getOperationDefineForDelete(),queryAllJson);
             assertThat(effectRecord.getEffectRecordNum(),equalTo(10));

             dataReadResult = this.getServiceSupport().call(resourceDefine,OperationDefineFactory.getOperationDefineForRead(),queryAllJson);
             assertThat(dataReadResult.getReadNum(),equalTo(0));
             */
        }catch(Throwable e){
                logger.error("",e);
                fail();
            }

    }
}
