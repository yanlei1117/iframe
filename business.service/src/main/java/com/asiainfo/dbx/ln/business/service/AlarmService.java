package com.asiainfo.dbx.ln.business.service;

import java.util.List;

/**
 * Created by yanlei on 2014/8/22.
 */
public class AlarmService {
    List<String> msisdnList = null;

    public List<String> getMsisdnList() {
        return msisdnList;
    }

    public void setMsisdnList(List<String> msisdnList) {
        this.msisdnList = msisdnList;
    }

    private SmsService smsService;

    public SmsService getSmsService() {
        return smsService;
    }

    public void setSmsService(SmsService smsService) {
        this.smsService = smsService;
    }

    public void alarm(String message){
        if(msisdnList!= null && msisdnList.size()>0){
            for(String msisdn:msisdnList){
                this.getSmsService().sendSms(msisdn,message);
            }
        }

    }
}
