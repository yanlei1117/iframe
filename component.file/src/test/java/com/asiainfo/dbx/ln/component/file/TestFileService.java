package com.asiainfo.dbx.ln.component.file;

/**
 * Created by yanlei on 2014/9/23.
 */

import com.asiainfo.dbx.ln.component.api.AppComponent;
import com.asiainfo.dbx.ln.component.util.AppFileUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class TestFileService {

    @BeforeClass
    public static void before() throws Exception{
        AppComponent.start();
    }
    @Test
    public void test() throws Exception{
       FileService  fileService =  AppFileUtils.getFileService(TestFileService.class);
       List<File> listFile =  fileService.listFile("deploy\\lib",".*jar");
       if(listFile.size()>0){
           fileService.copyDir("deploy\\lib","d:\\temp\\lib");
           File file = new File("d:\\temp\\lib");
           assertThat(file.exists(),equalTo(true));
           fileService.moveDir("d:\\temp\\lib","d:\\temp\\lib1");
           fileService.copyFile("d:\\temp\\lib1",null,"d:\\temp\\lib1\\dd1");
           file = new File("d:\\temp\\lib1\\dd1");
           assertThat(file.exists(),equalTo(true));
           fileService.deleteFile("d:\\temp\\lib1",null);
           fileService.zipDir("d:\\temp\\lib1\\dd1",null,"d:\\temp\\lib1","dd1.zip");
           fileService.unZipFile("d:\\temp\\lib1","dd1.zip","d:\\temp\\lib1\\dd1");

           file = new File("d:\\temp\\lib1\\dd1\\dd1");
           assertThat(file.exists(),equalTo(true));
           fileService.zipFile("d:\\temp\\lib1\\dd1",null,"d:\\temp\\lib1","dd2.zip");
           fileService.unZipFile("d:\\temp\\lib1","dd2.zip","d:\\temp\\lib1");
           fileService.deleteDir("d:\\temp\\lib1");
       }

    }
}
