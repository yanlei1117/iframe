package com.asiainfo.dbx.ln.component.util;

import java.util.regex.Pattern;

/**
 * Created by yanlei on 2014/7/10.
 */

public class AppNumberUtils {

    public static boolean isNumber(String value){
        int occur =0;
        for(int i=0;i<value.length();i++){
            char c = value.charAt(i);
            if(!Character.isDigit(c)){
                return false;
            }
            if(c =='0' && i==0){
                return false;
            }
            if(c=='.'){
                if(i==0 && i==value.length()-1){
                    return false;
                }else if(++occur>1){
                    return false;
                }
            }

        }
        return true;

    }
    public static boolean isInteger(String mm){
        for(int i=0;i<mm.length();i++){
            if(!Character.isDigit(mm.charAt(i))){
                return false;
            }
        }
        return true;
    }

    public static byte [] intToBytes(int i){
        byte[] result = new byte[4];
        result[0] = (byte)((i >> 24) & 0xFF);
        result[1] = (byte)((i >> 16) & 0xFF);
        result[2] = (byte)((i >> 8) & 0xFF);
        result[3] = (byte)(i & 0xFF);
        return result;
    }
}
