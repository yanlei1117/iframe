package com.asiainfo.dbx.ln.component.pool;

import com.asiainfo.dbx.ln.component.api.AppComponent;
import com.asiainfo.dbx.ln.component.util.AppPoolUtils;
import com.asiainfo.dbx.ln.component.util.AppSpringUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by yanlei on 2014/9/16.
 */
@RunWith(JUnit4.class)
@SuppressWarnings(value="unchecked")
public class TestObjectRefPool {

    @BeforeClass
    public static void before() throws Exception{
        AppComponent.start();
    }

    @Test
    public void test() throws Exception {
        Map map = new HashMap();
        Map pool = new ConcurrentHashMap();
        pool.put(map,"111");
        String str  = (String)pool.get(map);

        ObjectRefPool objectRefPool = (ObjectRefPool)AppPoolUtils.getObjectRefPool(this.getClass());
        String objectName = HashMap.class.getName();
         map = (Map)objectRefPool.borrowObject(objectName);
        map.put("1","2");
        objectRefPool.returnObject(map,objectName);
        map = (Map)objectRefPool.borrowObject(objectName);
        assertThat(map.size(),equalTo(0));
        objectRefPool.returnObject(map,objectName);
    }
}
