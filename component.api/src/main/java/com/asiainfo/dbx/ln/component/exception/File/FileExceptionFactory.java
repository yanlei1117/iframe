package com.asiainfo.dbx.ln.component.exception.File;

/**
 * Created by yanlei on 2014/8/21.
 */
public class FileExceptionFactory {
    public static IllegalFilePathException createIllegalFilePathException(String filePath){
        return new IllegalFilePathException(filePath);
    }
}
