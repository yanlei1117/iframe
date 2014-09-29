package com.asiainfo.dbx.ln.component.util;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.commons.beanutils.converters.SqlTimestampConverter;

/**
 * Created by yanlei on 2014/7/10.
 */
public class AppDateUtils {



    private static  Map<String,SimpleDateFormat> formateDateMap = new ConcurrentHashMap<String,SimpleDateFormat>();
    private static SimpleDateFormat sdf_show_yyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    static{

        registerSimpleDateFormat("yyyyMM",new SimpleDateFormat("yyyyMM"));
        registerSimpleDateFormat("yyyyMMdd",new SimpleDateFormat("yyyyMMdd"));
        registerSimpleDateFormat("yyyyMMddHHmmss",new SimpleDateFormat("yyyyMMddHHmmss"));
        registerSimpleDateFormat("MM",new SimpleDateFormat("MM"));
        registerSimpleDateFormat("dd",new SimpleDateFormat("dd"));
    }
    public static  void registerSimpleDateFormat(String pattern,SimpleDateFormat sdf){
        formateDateMap.put(pattern,sdf);
    }
    public static String formatDate(Date date,String pattern){
        SimpleDateFormat sdf = formateDateMap.get(pattern);
        if(sdf == null){
            sdf = new SimpleDateFormat(pattern);
            formateDateMap.put(pattern,sdf);
        }
        return sdf.format(date);
    }
    public static String formatDate(Date date){
        return ConvertUtils.convert(date);
    }
    public static Date parseString(String dateStr){
        return (Date)ConvertUtils.convert(dateStr,Date.class);
    }
    public static Date parseString(String dateStr,String pattern) throws Exception{
        SimpleDateFormat sdf = formateDateMap.get(pattern);
        if(sdf == null){
            sdf = new SimpleDateFormat(pattern);
            formateDateMap.put(pattern,sdf);
        }
        return sdf.parse(pattern);
    }

    public static int getCurrentDayOfMonth(){

        return getCurrentCalendar().get(Calendar.DAY_OF_MONTH);
    }

    public static int getCurrentDayOfWeek(){

        return  getCurrentCalendar().get(Calendar.DAY_OF_WEEK);
    }

    public static int getCurrentDayOfYear(){

        return  getCurrentCalendar().get(Calendar.DAY_OF_YEAR);
    }

    public static int getCurrentMonth(){
        return  getCurrentCalendar().get(Calendar.MONTH)+1;
    }

    public static int getDayOfMonth(Date date){
        return getCalendar(date).get(Calendar.DAY_OF_MONTH);
    }

    private static Calendar getCurrentCalendar(){
        return  Calendar.getInstance();
    }

    private static Calendar getCalendar(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return  calendar;
    }

    public static int getDayOfWeek(Date date){

        return getCalendar(date).get(Calendar.DAY_OF_WEEK);
    }

    public static int getDayOfYear(Date date){
        return getCalendar(date).get(Calendar.DAY_OF_YEAR);
    }

    public static int getMonth(Date date){
        return getCalendar(date).get(Calendar.MONTH)+1;
    }


  public static Date getCurrentDateNoTime(){
      Calendar calendar = getCurrentCalendar();
      calendar.set(Calendar.HOUR_OF_DAY, 0);
      calendar.set(Calendar.MINUTE, 0);
      calendar.set(Calendar.SECOND, 0);
      calendar.set(Calendar.MILLISECOND,0);
      return calendar.getTime();
  }

    public static Date getDateNoTime(Date date){
        Calendar calendar = getCalendar(date);
        return getDateNoTime(calendar);
    }

    public static Date getDateNoTime(Calendar calendar){

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getNextDateNoTime(Date date){
        Calendar calendar = getCalendar(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return getDateNoTime(calendar);
    }

    public static Date getLastDateNoTime(Date date){
        Calendar calendar = getCalendar(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return getDateNoTime(calendar);
    }

    public static Date getTomorrowDateNoTime(){
        Calendar calendar = getCurrentCalendar();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return getDateNoTime(calendar);
    }

    public static Date getYesterdayDateNoTime(){
        Calendar calendar = getCurrentCalendar();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return getDateNoTime(calendar);
    }

    public static Date getFirstDayOfMonthNoTime(Date date){
        Calendar calendar = getCalendar(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return getDateNoTime(calendar);
    }
    public static Date getFirstDayOfThisMonthNoTime(){
        Calendar calendar = getCurrentCalendar();

        calendar.set(Calendar.DAY_OF_MONTH, 1);
       return calendar.getTime();
    }
    public static Date getFirstDayOfNextMonthNoTime(){
        Calendar calendar = AppDateUtils.getCurrentCalendar();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return getDateNoTime(calendar);
    }
    public static Date getFirstDayOfLastMonthNoTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return getDateNoTime(calendar);
    }

    public static Date getLastDayOfMonthNoTime(Date date){
        Calendar calendar = getCalendar(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.add(Calendar.DAY_OF_MONTH,-1);

        return getDateNoTime(calendar);


    }
    public static Date getLastDayOfThisMonthNoTime(){
        Calendar calendar = getCurrentCalendar();

        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.add(Calendar.DAY_OF_MONTH,-1);

        return getDateNoTime(calendar);


    }
    public static Date getLastDayOfNextMonthNoTime(){
        Calendar calendar = AppDateUtils.getCurrentCalendar();

        calendar.add(Calendar.DAY_OF_MONTH, 2);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return getDateNoTime(calendar);
    }
    public static Date getLastDayOfLastMonthNoTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        return getDateNoTime(calendar);
    }




    public static Object parseDateByExpression( Date date,String exception)throws Exception{
        if(exception == null || exception.length()==0){
            return date;
        }else {
            exception = exception.trim();
            if(exception.charAt(0)=='.'){
                exception = exception.substring(1);
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            StringTokenizer st = new StringTokenizer(exception,".");
            while(st.hasMoreTokens()){
                String pression = st.nextToken();
                int addIndex = pression.indexOf("+");
                int decIndex = pression.indexOf("-");
                int equalIndex = pression.indexOf("=");
                if(addIndex >0 && AppNumberUtils.isInteger(pression.substring(addIndex + 1))){
                    setCalendar(calendar,"+",pression.substring(0,addIndex),pression.substring(addIndex+1));
                }else if(decIndex >0 && AppNumberUtils.isInteger(pression.substring(decIndex + 1))){
                    setCalendar(calendar,"-",pression.substring(0,decIndex),pression.substring(decIndex+1));
                }else if(equalIndex>0 && AppNumberUtils.isInteger(pression.substring(equalIndex + 1))){
                    setCalendar(calendar,"=",pression.substring(0,equalIndex),pression.substring(equalIndex+1));
                }else if(pression.equals("millis")){
                    return calendar.getTime().getTime();
                }else{
                    return formatDate(calendar.getTime(),pression);
                }
            }
            return calendar.getTime();

        }

    }

    private static void setCalendar(Calendar calendar ,String operater,String field,String value) throws Exception{
        int fieldId = getField(field);
        if(operater.endsWith("=")){
            calendar.set(fieldId, Integer.parseInt(value));
        }else if(operater.endsWith("-")){
            calendar.add(fieldId, -Integer.parseInt(value));
        }else if(operater.endsWith("+")){
            calendar.add(fieldId, Integer.parseInt(value));
        }
    }
    private static int getField(String field) throws Exception{
        if(field.equals("YYYY")){
            return Calendar.YEAR;
        }else if(field.equals("MM")){
            return Calendar.MONTH;
        }else if(field.equals("dd")){
            return Calendar.DAY_OF_MONTH;
        }else if(field.equals("HH")){
            return Calendar.HOUR_OF_DAY;
        }else if(field.equals("mm")){
            return Calendar.MINUTE;
        }else if(field.equals("ss")){
            return Calendar.SECOND;
        }else if(field.equals("SS")){
            return Calendar.MILLISECOND;
        }else if(field.equals("E")){
            return Calendar.DAY_OF_WEEK;
        }else{
            throw new Exception(" field:"+field +" error");
        }
    }
}
