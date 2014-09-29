package com.asiainfo.dbx.ln.component.exception.File;

/**
 * Created by yanlei on 2014/8/21.
 */
public class IllegalFilePathException extends RuntimeException{
    public IllegalFilePathException(String filePath){
        super(" the filePath  named '"+filePath+"' is Illegal");
    }
}
