package com.asiainfo.dbx.ln.component.file;

import com.asiainfo.dbx.ln.component.util.AppFileUtils;
import com.asiainfo.dbx.ln.component.util.AppStringUtils;
import com.asiainfo.dbx.ln.component.util.AppValidationUtils;
import com.asiainfo.dbx.ln.component.util.AppVarUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.*;

/**
 * Created by yanlei on 2014/9/23.
 */
public class FileServiceImpl implements  FileService{
    private final static  Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);



    @Override
    public List<File> listFile(String localPath, final String localFileNameRegex) throws Exception {

        File files [] = null;
        if(localFileNameRegex == null){
            files = new File(localPath).listFiles();
        }else {
             files = new File(localPath).listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return AppStringUtils.matchPartString(localFileNameRegex, name);
                }
            });
        }

        List<File> fileList= null;
        if(files == null){
            fileList = Collections.emptyList();
        }else {
             fileList = Arrays.asList(files);
        }
        logger.info("listFile  localPath:{},localFileNameRegex:{}, the files  matched ={}",localPath,localFileNameRegex,fileList);
        return fileList;
    }

    @Override
    public boolean deleteFile(String localPath, String localFileNameRegex) throws Exception {
        List<File> fileList = listFile(localPath,localFileNameRegex);
        logger.info("deleteFile localPath:{} ,localFileNameRegex:{}",localPath,localFileNameRegex);
        for(File file:fileList){
            if(file.isFile()){
                if(!file.delete()){
                    logger.info("deleteFile:{} result = FAIL",file.getAbsolutePath());
                    return false;
                }
                logger.info("deleteFile:{} result = OK",file.getAbsolutePath());
            }
        }
        return true;
    }
    @Override
    public boolean deleteDir(String localPath) throws Exception {
        logger.info("deleteDir localPath:{} ",localPath);
        List<File> fileList = listFile(localPath,null);
        for(File file:fileList){
            if(file.isDirectory()){
                if(!deleteDir(file.getAbsolutePath())){
                    break;
                }
            }else {
                if(!file.delete()){
                    logger.info("deleteFile  filePath:{} result = FAIL",file.getAbsolutePath());
                    return false;
                }else{
                    logger.info("deleteFile  filePath:{} result = OK",file.getAbsolutePath());
                }
            }
        }
        if(new File(localPath).delete()){
            logger.info("deleteDir localPath:{} ,result = OK",localPath);
            return true;
        }else{
            logger.info("deleteDir localPath:{} ,result = FAIL",localPath);
            return false;
        }

    }
    @Override
    public boolean renameFile(String localPath, String localFileNameRegex, String destFileNameRegex) throws Exception {
        logger.info("renameFile localPath:{},localFileNameRegex:{}, destFileNameRegex:{}",localPath,localFileNameRegex,destFileNameRegex);
        List<File> fileList = listFile(localPath,localFileNameRegex);
        boolean renameFlag  = true;
        for(File file:fileList){
            String destFileName = destFileNameRegex.replace("@{srcFileName}",file.getName());
            destFileName =  (String)AppVarUtils.getVarContainer(FileServiceImpl.class).getVar(destFileName);
            renameFlag = file.renameTo(new File(localPath,destFileName));

            if(!renameFlag){
                logger.info("renameFile localPath:{},srcFileName:{}, destFileNameRegex:{},result = FAIL",localPath,file.getName(),destFileName);
                return false;
            }else{
                logger.info("renameFile localPath:{},srcFileName:{}, destFileNameRegex:{},result = OK",localPath,file.getName(),destFileName);
            }
        }
        return true;
    }
    @Override
    public boolean renameDir(String localPath, String destDirName) throws Exception {
        File srcFile = new File(localPath);
        File destFile = new File(srcFile.getParentFile().getAbsolutePath(),destDirName);
        boolean renameFlag = srcFile.renameTo(destFile);
        if(renameFlag){
            logger.info("renameDir localPath:{},destDirName:{}, result = OK",localPath,destDirName);
            return true;
        }else{
            logger.info("renameDir localPath:{},destDirName:{}, result = FAIL",localPath,destDirName);
            return false;
        }
    }

    @Override
    public boolean copyFile(String localPath, String localFileNameRegex, String destLocalPath) throws Exception {
        logger.info("copyFile localPath:{},localFileNameRegex:{}, destLocalPath:{}..START",localPath,localFileNameRegex ,destLocalPath);
        List<File> fileList = listFile(localPath,localFileNameRegex);
        if(fileList.size()>0){
            AppFileUtils.createLocalPathIfNotExist(new File(destLocalPath));
            for(File file:fileList){
                streamCopy(file, new File(destLocalPath, file.getName()));
            }
        }
        logger.info("copyFile localPath:{},localFileNameRegex:{}, destLocalPath:{}..FINISH,result=OK",localPath,localFileNameRegex ,destLocalPath);
        return true;
    }

    @Override
    public boolean copyDir(String localPath, String destLocalPath) throws Exception {
        logger.info("copyDir localPath:{},destLocalPath:{}, ...START",localPath,destLocalPath );
        AppFileUtils.createLocalPathIfNotExist(new File(destLocalPath));
        List<File> fileList = listFile(localPath,null);
        for(File file:fileList){
                if (file.isFile()) {
                    streamCopy(file, new File(destLocalPath, file.getName()));
                } else if (file.isDirectory()) {
                    File destFilePath = new File(destLocalPath,file.getName());
                    AppFileUtils.createLocalPathIfNotExist(destFilePath);
                    copyDir(new File(localPath,file.getName()).getAbsolutePath(),destFilePath.getAbsolutePath());
                }
        }
        logger.info("copyDir localPath:{},destLocalPath:{}, ...FINISH,result=OK",localPath,destLocalPath );
        return true;
    }


    @Override
    public boolean moveFile(String localPath, String localFileNameRegex, String destLocalPath) throws Exception {
        logger.info("moveFile localPath:{},localFileNameRegex:{},destLocalPath:{}, ...START",localPath,localFileNameRegex,destLocalPath );
        List<File> fileList = listFile(localPath,localFileNameRegex);
        if(fileList.size()>0){
             AppFileUtils.createLocalPathIfNotExist(new File(destLocalPath));
            for(File file:fileList){
                if(file.isFile()) {
                    streamCopy(file, new File(destLocalPath, file.getName()));
                    deleteFile(file);
                }else{
                    logger.error("moveFile  found file '{}' is directory :{},to ignore", file.getAbsolutePath());
                }
            }
        }else{
            logger.error("moveFile  no matched file found in {},by localFileNameRegex :{}", localPath, localFileNameRegex);
        }
        logger.info("moveFile localPath:{},localFileNameRegex:{},destLocalPath:{}, ...FINISH,result=OK",localPath,localFileNameRegex,destLocalPath );
        return true;
    }

    private boolean deleteFile(File file){
        if(file.delete()){
            logger.info("moveFile ,deleteFile fileName:{},delete result=FAIL",file.getAbsolutePath());
            return false;
        }else{
            logger.info("moveFile ,deleteFile fileName:{},delete result=OK",file.getAbsolutePath());
            return true;
        }
    }
    @Override
    public boolean moveDir(String localPath,  String destLocalPath) throws Exception {
        logger.info("moveDir localPath:{}, destLocalPath:{}, ...START",localPath,destLocalPath );

        AppFileUtils.createLocalPathIfNotExist(new File(destLocalPath));
        List<File> fileList = listFile(localPath,null);
        for(File file:fileList){
            if(file.isFile()){
                streamCopy(file, new File(destLocalPath, file.getName()));
            }else if(file.isDirectory()){
                moveDir(file.getAbsolutePath(),new File(destLocalPath,file.getName()).getAbsolutePath());
            }
            if(!(this.deleteFile(file))){
                logger.info("moveDir localPath:{}, destLocalPath:{}, ...FINISH,result=OK", localPath, destLocalPath);
                return false;
            }
        }
        if(new File(localPath).delete()){
            logger.info("moveDir localPath:{}, destLocalPath:{}, ...FINISH,result=OK", localPath, destLocalPath);
            return true;

        }else {
            logger.info("moveDir localPath:{}, destLocalPath:{}, ...FINISH,result=FAIL", localPath, destLocalPath);
            return false;
        }
    }
    private boolean streamCopy(File srcFile,File destFile) throws Exception{
        FileChannel srcFileChannel = null;
        FileChannel destFileChannel = null;
        boolean copyFlag = false;
        try{
            srcFileChannel = (new FileInputStream(srcFile)).getChannel();
            destFileChannel = new FileOutputStream(destFile).getChannel();
            srcFileChannel.transferTo(0, srcFile.length(), destFileChannel);
            srcFileChannel.close();
            destFileChannel.close();
            copyFlag = true;
        }finally{
            if(srcFileChannel != null){
                srcFileChannel.close();
            }
            if(destFileChannel != null){
                destFileChannel.close();
            }
            logger.info(" ----copyFile srcFile:{},destFile:{}, result={}",srcFile.getAbsolutePath(),destFile.getAbsolutePath() ,copyFlag==true?"OK":"FAIL");
        }
        return true;
    }
    @Override
    public boolean  zipFile(String localPath, String localFileNameRegex, String zipFilePath, String zipFileName) throws Exception {
        logger.info("zipFile localPath:{},localFileNameRegex={}, zipFilePath:{},zipFileName:{}, ...START",localPath,localFileNameRegex,zipFilePath,zipFileName );
        List<File> fileList = listFile(localPath,localFileNameRegex);
        if(fileList != null && fileList.size()>0){
            File zipFile = new File(zipFilePath,zipFileName);

            ZipOutputStream out =null;
            boolean flag = false;
            try {
                out = new ZipOutputStream(new CheckedOutputStream(new FileOutputStream(zipFile),new CRC32()));
                for (File file : fileList) {
                    if (file.isDirectory()) {
                        logger.info("zipFile file:'{}' is directory,to ignore", file.getAbsolutePath());
                    } else {
                        zipFile(out, file, "");
                    }
                }
                flag = true;
            }finally {
                if(out != null) {
                    out.flush();
                    out.close();
                }

            }
        }else{
            logger.error("zipFile localPath:{},localFileNameRegex={}, zipFilePath:{},zipFileName:{}, ...FINSISH,result=found none matched file",localPath,localFileNameRegex,zipFilePath,zipFileName );
            return true;
        }
        logger.info("zipFile localPath:{},localFileNameRegex={}, zipFilePath:{},zipFileName:{}, ...FINISH,result=OK",localPath,localFileNameRegex,zipFilePath,zipFileName );
        return true;
    }
    @Override
    public boolean  zipDir(String localPath, String localFileNameRegex, String zipFilePath, String zipFileName) throws Exception {
        logger.info("zipDir localPath:{},localFileNameRegex={}, zipFilePath:{},zipFileName:{}, ...START",localPath,localFileNameRegex,zipFilePath,zipFileName );
        List<File> fileList = listFile(localPath,localFileNameRegex);
        if(fileList != null && fileList.size()>0){
            File zipFile = new File(zipFilePath,zipFileName);

            ZipOutputStream out = null;
            try {
                out = new ZipOutputStream(new CheckedOutputStream(new FileOutputStream(zipFile), new CRC32()));
                String localPathName = new File(localPath).getName();
                for (File file : fileList) {
                    zipFile(out, file, localPathName);
                }
            }finally {
                if(out != null){
                    out.close();
                }
            }

        }else{
            logger.error("zipFile localPath:{},localFileNameRegex={}, zipFilePath:{},zipFileName:{}, ...FINSISH,result=found none matched file",localPath,localFileNameRegex,zipFilePath,zipFileName );
            return true;
        }
        logger.info("zipDir localPath:{},localFileNameRegex={}, zipFilePath:{},zipFileName:{}, ...START",localPath,localFileNameRegex,zipFilePath,zipFileName );
        return true;
    }

    protected boolean zipFile(ZipOutputStream out,File file,String baseDir) throws Exception{
        boolean flag  = false;
        BufferedInputStream bis = null;
        try {
            ZipEntry entry = new ZipEntry(AppValidationUtils.isEmpty(baseDir) ? file.getName() : baseDir + "/" + file.getName());
            out.putNextEntry(entry);
            if (file.isFile()) {
                bis = new BufferedInputStream(new FileInputStream(file));
                int count;
                byte data[] = new byte[1024];
                while ((count = bis.read(data, 0, 1024)) != -1) {
                    out.write(data, 0, count);
                }
                bis.close();

            }
            flag = true;
        }finally {
            if(bis!= null){
                bis.close();
            }
            logger.info("zipFile: file:{} result ={}",file.getAbsolutePath(),flag?"OK":"FAIL");
        }
        return flag;
    }
    @Override
    public boolean  unZipFile(String localPath, String zipFileName, String destLocalPath) throws Exception {
        logger.info("unZipFile localPath:{},zipFileName={}, destLocalPath:{} ...START",localPath,zipFileName,destLocalPath);
        ZipFile zip= null;
        FileOutputStream fos = null;
        boolean flag = false;
        try {
            zip = new ZipFile(new File(localPath, zipFileName));
            Enumeration enumeration = zip.entries();
            ZipEntry zipEntry = null;
            while (enumeration.hasMoreElements()) {
                zipEntry = (ZipEntry) enumeration.nextElement();
                File toZipFile = getUnZipFile(destLocalPath, zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    if (!toZipFile.exists()) {
                        if (toZipFile.mkdir()) {
                            logger.info(" extract dir:{}   to {} finish,result = FAIL", zipEntry.getName(), toZipFile.getAbsolutePath());
                            return false;
                        }else{
                            logger.info(" extract dir:{}   to {} finish,result = OK", zipEntry.getName(), toZipFile.getAbsolutePath());
                        }
                    }

                } else {
                    boolean extractFlag = false;
                    InputStream is= null;
                    try {
                         fos = new FileOutputStream(toZipFile);
                         is = zip.getInputStream(zipEntry);
                        byte data[] = new byte[1024];
                        int readNum = is.read(data);
                        while (readNum != -1) {
                            fos.write(data, 0, readNum);
                            readNum = is.read(data);
                        }
                        extractFlag = true;
                    }finally {
                        if(fos != null){
                            fos.close();
                        }
                        if(is != null){
                            is.close();
                        }
                        logger.info(" extract file:{}   to {} finish,result={}", zipEntry.getName(), toZipFile.getAbsolutePath(),extractFlag?"OK":"FAIL");
                    }

                }
            }
            flag = true;
        }finally{

                if(zip != null){
                    zip.close();
                }
            logger.info("unZipFile localPath:{},zipFileName={}, destLocalPath:{} ...FINISH,result={}",localPath,zipFileName,destLocalPath,flag?"OK":"FAIL");
          }
        return flag;
    }
    private File getUnZipFile(String destDir,String zipPath){
        String path[] = zipPath.split("/");
        if(path != null && path.length==1){
            return new File(destDir , zipPath);
        }else{
            File destFile = new File(destDir);
            for(int i=0;i<path.length;i++){
                if(path[i] == null || path[i].length()==0){
                    continue;
                }
                destFile= new File(destFile.getAbsolutePath(),path[i]);
                if(i<path.length-1 && !destFile.exists()){
                    destFile.mkdir();
                }
            }
            return destFile;
        }
    }

}
