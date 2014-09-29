package com.asiainfo.dbx.ln.business.service.resource;

import com.asiainfo.dbx.ln.component.dao.mybatis.MyBatisExample;
import com.asiainfo.dbx.ln.component.dao.mybatis.MyBatisMapper;
import com.asiainfo.dbx.ln.component.util.AppBeanUtils;

/**
 * Created by yanlei on 2014/9/3.
 */
public class MyBatisResource {
   private  MyBatisMapper myBatisMapper;
   private  Class myBatisModelClass ;
   private  Class myBatisExampleClass;
    public MyBatisResource(MyBatisMapper myBatisMapper, Class myBatisModelClass,Class myBatisExampleClass) {
        this.myBatisMapper = myBatisMapper;
        this.myBatisModelClass = myBatisModelClass;
        this.myBatisExampleClass = myBatisExampleClass;
    }

    public Class getMyBatisExampleClass() {
        return myBatisExampleClass;
    }

    public void setMyBatisExampleClass(Class myBatisExampleClass) {
        this.myBatisExampleClass = myBatisExampleClass;
    }

    public MyBatisMapper getMyBatisMapper() {
        return myBatisMapper;
    }

    public void setMyBatisMapper(MyBatisMapper myBatisMapper) {
        this.myBatisMapper = myBatisMapper;
    }

    public Class getMyBatisModelClass() {
        return myBatisModelClass;
    }

    public void setMyBatisModelClass(Class myBatisModelClass) {
        this.myBatisModelClass = myBatisModelClass;
    }

    public MyBatisExample createMyBatisExample() throws Exception{
        return (MyBatisExample)AppBeanUtils.instanceBean(this.myBatisExampleClass);
    }
    @Override
    public String toString() {
        return "MyBatisResource{" +
                "myBatisMapper=" + myBatisMapper.getClass().getName() +
                ", myBatisModelClass=" + myBatisModelClass.getName() +
                '}';
    }
}
