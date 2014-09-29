package com.asiainfo.dbx.ln.component.dao;

/**
 * 分页对象，表示从数据库的结果集中，第offset开始，取limit条记录，做为结果集
 *
 * Created by yanlei on 2014/8/4.
 */
public class RowLimit {
    private int offset;
    private int limit;

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

    public static class Builder{
        RowLimit rowLimit = new RowLimit();
        public Builder offset(int offset){
            rowLimit.setOffset(offset);
            return this;
        }
        public Builder limit(int limit){
            rowLimit.setLimit(limit);
            return this;
        }
        public RowLimit build(){
            return rowLimit;
        }

    }
}
