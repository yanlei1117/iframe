package com.asiainfo.dbx.ln.component.net.ftp;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by yanlei on 2014/8/18.
 */
public interface FtpFile {

    public boolean isDirectory()  ;


    public boolean isFile() ;


    public long getSize() ;



    public String getName();



    public Date getTimestamp() ;


}
