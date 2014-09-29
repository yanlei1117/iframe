package com.asiainfo.dbx.ln.component.file;

import com.asiainfo.dbx.ln.component.util.AppFieldUtils;
import com.asiainfo.dbx.ln.component.util.AppFileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.*;

/**
 * Created by yanlei on 2014/9/12.
 */
public class FileOperatorImpl implements FileOperator {
    private Logger logger = LoggerFactory.getLogger(FileOperatorImpl.class);
    private File file = null;
    public FileOperatorImpl(File file ){
        this.file = file;
    }
    public InputStream getInputStream() throws FileNotFoundException {
        return new FileInputStream(this.file);
    }
    public String getFileName(){
        return this.file.getName();
    }
    public boolean rename(String newFileName){
        File dest = new File(this.file.getParent(),newFileName);
        return  this.file.renameTo(dest);
    }

    public File getFile(){
        return this.file;
    }
    public String getAbsolutePath(){
        return this.file.getAbsolutePath();
    }
    public long getLength(){
        return this.file.length();
    }

    public boolean isFileExist() throws IOException{
        return this.file.exists();
    }

    public boolean delete(){
        return this.file.delete();
    }
    public void createNewFile() throws IOException {
        this.file.createNewFile();
    }

    @Override
    public FileOperator getParentFile() {
         return  new FileOperatorImpl(file.getParentFile());
    }

    @Override
    public List<FileOperator> getFileList() {
        File files[] = this.file.listFiles();
        List<FileOperator> fileOperatorList = new ArrayList<FileOperator>();
        for(File subFile:files){
            fileOperatorList.add(new FileOperatorImpl(subFile));
        }
        return fileOperatorList;
    }

    @Override
    public boolean copyFile(String destFile) throws Exception {
        File dest = new File(destFile);
        AppFileUtils.createLocalPathIfNotExist(new File(destFile));
        if(!dest.exists()){
            dest.createNewFile();
        }
        streamCopy(dest);
        return true;
    }

    private void streamCopy(File destFile) throws Exception{
        FileChannel srcFileChannel = null;
        FileChannel destFileChannel = null;
        try{
            srcFileChannel = ((FileInputStream)this.getInputStream()).getChannel();
            destFileChannel = new FileOutputStream(destFile).getChannel();
            srcFileChannel.transferTo(0, this.file.length(), destFileChannel);
            srcFileChannel.close();
            destFileChannel.close();
        }finally{
            if(srcFileChannel != null){
                srcFileChannel.close();
            }
            if(destFileChannel != null){
                destFileChannel.close();
            }
        }
    }
    @Override
    public boolean zipFile(String zipFileName) throws Exception {
        File zipFile = new File(zipFileName);

        CheckedOutputStream cos = new CheckedOutputStream(new FileOutputStream(zipFile),new CRC32());
        ZipOutputStream out = new ZipOutputStream(cos);
        zipFile(out,file,"");
        out.close();
        logger.debug(" compress zip  file:{}  to {}  finish", file.getAbsolutePath(), zipFile.getAbsolutePath());
        return true;
    }
    protected boolean zipFile(ZipOutputStream out,File file,String baseDir) throws Exception{
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        ZipEntry entry = new ZipEntry(baseDir + file.getName());
        out.putNextEntry(entry);
        int count;
        byte data[] = new byte[1024];
        while ((count = bis.read(data, 0, 1024)) != -1) {
            out.write(data, 0, count);
        }
        bis.close();
        return true;
    }

    @Override
    public boolean unZipFile(String  destDir) throws Exception{
        ZipFile zip= new ZipFile(this.file);
        Enumeration enumeration = zip.entries();

        ZipEntry zipEntry = null ;
        while(enumeration.hasMoreElements()){
            zipEntry = (ZipEntry)enumeration.nextElement();
            File toZipFile = getUnZipFile(destDir,zipEntry.getName());
            if(zipEntry.isDirectory() ){
                if( !toZipFile.exists()){
                    toZipFile.mkdir();
                }
                logger.info(" extract dir:{}   to {} finish",zipEntry.getName(),toZipFile.getAbsolutePath());
            }else{
                FileOutputStream fos = new FileOutputStream(toZipFile);
                InputStream is = zip.getInputStream(zipEntry);
                byte data [] = new byte [1024];
                int readNum = is.read(data);
                while(readNum != -1){
                    fos.write(data,0,readNum);
                    readNum = is.read(data);
                }
                is.close();
                fos.close();
                logger.info(" extract file:{}   to {} finish",zipEntry.getName(),toZipFile.getAbsolutePath());
            }
        }
        return true;

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



    public boolean moveTo (String destDir) throws Exception{
        File dest = new File(destDir,this.getFileName());
        AppFileUtils.createLocalPathIfNotExist(dest);
        if(!dest.exists()){
            dest.createNewFile();
        }
        streamCopy(dest);
        if(delete()){
            return true;
        }else{
            dest.delete();
            return false;
        }
    }

}
