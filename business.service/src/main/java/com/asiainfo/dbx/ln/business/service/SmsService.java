package com.asiainfo.dbx.ln.business.service;

import com.asiainfo.dbx.ln.component.dao.jdbc.JdbcDao;

/**
 * Created by yanlei on 2014/8/22.
 */
public class SmsService {
    JdbcDao jdbcDao = null;

    public JdbcDao getJdbcDao() {
        return jdbcDao;
    }

    public void setJdbcDao(JdbcDao jdbcDao) {
        this.jdbcDao = jdbcDao;
    }

    String sql = "insert into bs_sms_send(id, task_id, sys_id, subsys_id, msisdn, subject, channel_id, start_time, end_time, status, send_count, channel_type, create_date) values(sms_instant_seq.Nextval,'ftpdownload','AI-JF','AI-JF-YX',?,?,'SMS1',0,24,0,1,'SMS',sysdate)";
    public void sendSms(String msisdn,String message){
        this.getJdbcDao().update(sql,new Object []{msisdn,message});
    }
}
