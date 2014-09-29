package com.asiainfo.dbx.ln.component.net.ftp;


import com.asiainfo.dbx.ln.component.util.AppNumberUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by yanlei on 2014/8/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value={"classpath*:config/spring/base/component*.xml", "classpath:config/spring/test/ftp_config.xml"})
public class TestFtpOperator {
    Logger logger = LoggerFactory.getLogger(TestFtpOperator.class);
    @Resource(name="ftpOperator")
    private FtpOperator ftpOperator;

    public FtpOperator getFtpOperator() {
        return ftpOperator;
    }

    public void setFtpOperator(FtpOperator ftpOperator) {
        this.ftpOperator = ftpOperator;
    }



    @Test
    public void testFtp(){
        try {
            File file = new File("c://temp14638473");
            if(!file.exists()){
                file.mkdir();
            }
            for(int i=1;i<101;i++){
                File newFile = new File(file.getAbsolutePath(),"ftpFile"+i+".dat");
                if(!newFile.exists()){
                    newFile.createNewFile();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(newFile);
                for(int j=0;j<10000;j++){
                    fileOutputStream.write(AppNumberUtils.intToBytes(j));
                }
                fileOutputStream.close();
            }


            int uploadFileNum =  this.getFtpOperator().uploadAllFile("c://temp14638473",null,"/data/ailn/temp14638473",false);
            assertThat(uploadFileNum, equalTo(100));

            List<FtpFile> ftpFileList = this.getFtpOperator().getFileListInDir("/data/ailn/temp14638473");
            assertThat(ftpFileList.size(),equalTo(100));
           List<File> fileList =  this.getFtpOperator().downloadAllFile("/data/ailn/temp14638473","c://temp14638473");
            assertThat(fileList.size(), equalTo(100));
            for(File downloadfile:fileList){
                downloadfile.delete();
            }
            new File("c://temp14638473").delete();

           int renameFileNum =  this.getFtpOperator().renameAllServerFile("/data/ailn/temp14638473",null,".bak",false);
            assertThat(renameFileNum, equalTo(100));

            ftpFileList = this.getFtpOperator().getFileListInDir("/data/ailn/temp14638473",".*bak");
            assertThat(ftpFileList.size(),equalTo(100));
           int moveFileNum =  this.getFtpOperator().moveAllServerFile("/data/ailn/temp14638473",null,"/data/ailn/temp99999",false);
            assertThat(moveFileNum,equalTo(100));
            ftpFileList = this.getFtpOperator().getFileListInDir("/data/ailn/temp14638473");
            assertThat(ftpFileList ,nullValue());

           int deleteNum =   this.getFtpOperator().deleteDir("/data/ailn/temp14638473");
            assertThat(deleteNum,equalTo(1));//删除空目录

            deleteNum = this.getFtpOperator().deleteDir("/data/ailn/temp99999");
            assertThat(deleteNum,equalTo(101));//100个文件+1个目录

        }catch(Throwable e){
            logger.error("",e);
            fail();
        }finally{
            this.getFtpOperator().close();
        }
    }

}
