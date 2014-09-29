package com.asiainfo.dbx.ln.business.service.data.result;

import java.util.List;

/**
 * Created by yanlei on 2014/9/13.
 */
public class DataReadPageResult {
    int readNum =0;
    int totalNum = 0;
    int offset =-1;
    int limit = -1;
    List dataList;

    public int getReadNum() {
        return readNum;
    }

    public void setReadNum(int readNum) {
        this.readNum = readNum;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List getDataList() {
        return dataList;
    }

    public void setDataList(List dataList) {
        this.dataList = dataList;
    }

}
