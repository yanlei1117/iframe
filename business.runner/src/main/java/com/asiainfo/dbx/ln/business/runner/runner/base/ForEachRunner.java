package com.asiainfo.dbx.ln.business.runner.runner.base;

import com.asiainfo.dbx.ln.business.runner.Runner;
import com.asiainfo.dbx.ln.component.exception.ExceptionFactory;
import com.asiainfo.dbx.ln.component.util.AppValidationUtils;
import com.asiainfo.dbx.ln.component.util.AppVarUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by yanlei on 2014/9/16.
 */
public class ForEachRunner extends Runner {
    private static final Logger logger = LoggerFactory.getLogger(ForEachRunner.class);
    String item = null;
    String collection = null;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    @Override
    public void run() throws Exception {
        if (AppValidationUtils.isNull(this.collection)) {
            throw ExceptionFactory.createPropertyNotConfigedException("collection");
        }
        if (AppValidationUtils.isNull(this.item)) {
            throw ExceptionFactory.createPropertyNotConfigedException("item");
        }
        logger.debug("ForEachRunner collection:{},item:{},desc:{} start....",this.collection,this.item,this.getDesc());
        Object obj = AppVarUtils.getVarContainer(ForEachRunner.class).getVar(this.collection);
        logger.debug("ForEachRunner collectionValue:{}",obj);
        if(obj != null){
            Iterator iterator = null;
            if(Collection.class.isAssignableFrom(obj.getClass())){
                iterator = ((Collection)obj).iterator();
            }else if(Iterator.class.isAssignableFrom(obj.getClass())){
                iterator = ((Iterator)obj);
            }else if(obj.getClass().isArray()) {
                iterator = new ArrayIterator((Object [])obj);
            }else {
                throw new UnsupportedOperationException(" collection expression only support 'collection'、'Iterator'、'ARRAY' ,not support '"+obj.getClass()+"'");
            }
            int index =0;
            while(iterator.hasNext()){
                index++;
                Object item = iterator.next();
                logger.debug("ForEachRunner index:{},value:{}",index,item);
                AppVarUtils.getVarContainer(ForEachRunner.class).setVar(this.getItem(),item);
            }
        }
        logger.debug("ForEachRunner collection:{},item:{},desc:{} finish....",this.collection,this.item,this.getDesc());
    }
    class ArrayIterator implements Iterator{
        Object [] objs = null;
        int currentIndex = -1;
        ArrayIterator(Object [] objs){
            this.objs = objs;
        }
        @Override
        public boolean hasNext() {
            currentIndex++;
            if(objs.length>currentIndex){
                return true;
            }else {
                return false;
            }
        }

        @Override
        public Object next() {
            return objs[currentIndex];
        }

        @Override
        public void remove() {

        }
    }

    @Override
    public String toString() {
        return "\nForEachRunner{" +
                "item='" + item + '\'' +
                ", collection='" + collection + '\'' +
                ", runnerList='" + this.getRunnerList() + '\'' +
                '}';
    }
}
