package com.asiainfo.dbx.ln.business.service.data.result;

import java.util.List;

/**
 * Created by yanlei on 2014/8/31.
 */
public class DataReadResult {
    int readNum =0;
    List dataList;

    public List getDataList() {
        return dataList;
    }

    public void setDataList(List dataList) {
        this.dataList = dataList;
    }

    public int getReadNum() {
        return readNum;
    }

    public void setReadNum(int readNum) {
        this.readNum = readNum;
    }
}
