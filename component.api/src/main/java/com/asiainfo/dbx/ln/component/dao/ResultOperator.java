package com.asiainfo.dbx.ln.component.dao;

/**
 *用于从数据库中查询出来的对象操作
 *
 * Created by yanlei on 2014/8/4.
 */
public interface ResultOperator<T> {
      public boolean deal(T resultObject,int currentRowNum);
}
