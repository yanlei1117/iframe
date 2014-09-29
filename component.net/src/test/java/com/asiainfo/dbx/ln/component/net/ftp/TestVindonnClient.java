package com.asiainfo.dbx.ln.component.net.ftp;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by yanlei on 2014/8/29.
 */
public class TestVindonnClient {

    SocketChannel vindonnsocketChannel = null;
    private SocketChannel getVindonnsocketChannel() throws Exception {
        if (vindonnsocketChannel == null) {
            vindonnsocketChannel = SocketChannel.open();
            boolean connect = vindonnsocketChannel.connect(new InetSocketAddress("114.215.128.190", 80));
            if(!connect){
                throw new Exception ("connecto to 114.215.128.190 fail");
            }

        }
        return vindonnsocketChannel;
    }

    public String getStep1String() throws Exception{
        String str = "GET /download/update.xml HTTP/1.1\n" +
                "Host: www.vidonn.com\n" +
                "Connection: Keep-Alive\n\n";
       return str;
    }

    public String getStep2String(int length) throws Exception{
        String str = "POST /webservice/service.asmx HTTP/1.1\n" +
                "User-Agent: Mozilla/4.0 (compatible; MSIE 6.0; MS Web Services Client Protocol 2.0.50727.5448)\n" +
                "Content-Type: text/xml; charset=utf-8\n" +
                "SOAPAction: \"http://tempuri.org/CheckLogin\"\n" +
                "Host: www.vidonn.com\n" +
                "Content-Length: "+length+"\n" +
                "Expect: 100-continue\n\n";
       return str;
    }

    public String getStep3String() throws Exception{
        String str = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">" +
                "<soap:Body>" +
                "<CheckLogin xmlns=\"http://tempuri.org/\">" +
                "<LoginID>"+this.getUserName()+"</LoginID>" +
                "<Password>"+this.getPassword()+"</Password>" +
                "<MobileIMEI>"+this.getMobileIMEI()+"</MobileIMEI>" +
                "<verCode>"+this.getVerCode()+"</verCode>" +
                "</CheckLogin>" +
                "</soap:Body>" +
                "</soap:Envelope>";
       return str;
    }
    public String getStep4String(int length) throws Exception{
        String str = "POST /webservice/service.asmx HTTP/1.1\n" +
                "User-Agent: Mozilla/4.0 (compatible; MSIE 6.0; MS Web Services Client Protocol 2.0.50727.5448)\n" +
                "Content-Type: text/xml; charset=utf-8\n" +
                "SOAPAction: \"http://tempuri.org/SyncData\"\n" +
                "Host: www.vidonn.com\n" +
                "Content-Length: "+length+"\n" +
                "Expect: 100-continue\n\n";
        return str;
    }

    public String getSetpMessage(Map <String ,Integer> dayAndStepMap){
        long srcStep = 8584;
        long srcDistance= 8072;
        long srccalorie = 524;
        StringBuilder sb = new StringBuilder();
        Iterator<String> iterator = dayAndStepMap.keySet().iterator();
        while(iterator.hasNext()){
            String day = iterator.next();
            int step = dayAndStepMap.get(day);
            long  distance= step*(srcDistance/srcStep);
            long  calorie= step*(srccalorie/srcStep);
            String date = day+" 19:12:14";
            sb.append("&lt;item&gt;" );
            sb.append("&lt;step&gt;"+step+"&lt;/step&gt;");
            sb.append("&lt;distance&gt;"+distance+"&lt;/distance&gt;" );
            sb.append("&lt;calorie&gt;"+calorie+"&lt;/calorie&gt;" );
            sb.append("&lt;storey&gt;0&lt;/storey&gt;" );
            sb.append("&lt;sleep&gt;0&lt;/sleep&gt;" );
            sb.append("&lt;acttime&gt;"+date+"&lt;/acttime&gt;" );
            sb.append("&lt;/item&gt;");
        }
        return sb.toString();
    }
    public String getStep5String(Map <String ,Integer> dayAndStepMap) throws Exception{

        String str = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">" +
                    "<soap:Body>" +
                        "<SyncData xmlns=\"http://tempuri.org/\">" +
                            "<LoginID>"+this.getUserName()+"</LoginID>" +
                            "<DeviceID>"+this.getDeviceId()+"</DeviceID>" +
                            "<DataXML>&lt;?xml version=\"1.0\" encoding=\"utf-8\" ?&gt;" +
                                    "&lt;root&gt;" +
                                     getSetpMessage(dayAndStepMap)+
                                    "&lt;/root&gt;" +
                            "</DataXML>" +
                            "<verCode>VD2013</verCode>" +
                        "</SyncData>" +
                    "</soap:Body>" +
                "</soap:Envelope>";
        return str;
    }


    public void showMessage(Map<String,Integer> dayAndStepMap){
        System.out.println("userName:"+this.getUserName());
        System.out.println("password:"+this.getPassword());
        System.out.println("mobileIMEI:"+this.getMobileIMEI());
        System.out.println("verCode:" + this.getVerCode());
        System.out.println("deviceId:"+this.getDeviceId());
        Iterator<String> dayIterator = dayAndStepMap.keySet().iterator();
        while(dayIterator.hasNext()){
            String day = dayIterator.next();
            Integer step = dayAndStepMap.get(day);
            System.out.println(day+":"+step);
        }

    }
    private boolean readInputYOrN(){
        System.out.println("continue to write data?[Y/N]");
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        if(line.trim().toUpperCase().equals("Y")){
            return true;
        }else if(line.trim().toUpperCase().equals("N")){
            return false;
        }else{
            System.out.println(" input error.");
            return readInputYOrN();
        }
    }
    public void  run(Map<String,Integer> dayAndStepMap){
        try{

            String step1String = this.getStep1String();

            String step3String = this.getStep3String();
            String step2String = this.getStep2String(step3String.getBytes().length);
            String step5String = this.getStep5String(dayAndStepMap);
            String step4String = this.getStep4String(step5String.getBytes().length);
            byte [] data = null;
            System.out.println("step1:request:" + step1String);
            data=   write(step1String.getBytes());
            System.out.println("step1:response:" + new String(data));

            System.out.println("step2:request:" + step2String);
             data = write(step2String.getBytes());
            System.out.println("step2:response:" + new String(data));

            System.out.println("step3:request:" + step3String);
            data = write(step3String.getBytes());
            System.out.println("step3:response:" + new String(data));

            System.out.println("step4:request:" + step4String);
            data = write(step4String.getBytes());
            System.out.println("step4:response:" + new String(data));

            System.out.println("step5:request:" + step5String);
            data = write(step5String.getBytes());
            System.out.println("step5:response:" + new String(data));


        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try{
                close();
            }catch(Exception ee){
                ee.printStackTrace();;
            }
        }
    }

    public byte [] write(byte [] data) throws Exception {
        this.getVindonnsocketChannel().write(ByteBuffer.wrap(data));
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        this.getVindonnsocketChannel().read(byteBuffer);
        byteBuffer.flip();
        data= new byte [byteBuffer.remaining()];
        byteBuffer.get(data);
        //logger.info("read from server length={}:{}",data.length,new String (data));
        return data;
    }
    public void close() throws Exception {
        if(vindonnsocketChannel != null){
            vindonnsocketChannel.close();
        }
    }

    String userName;
    String password;
    String mobileIMEI;
    String verCode;
    String deviceId;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileIMEI() {
        return mobileIMEI;
    }

    public void setMobileIMEI(String mobileIMEI) {
        this.mobileIMEI = mobileIMEI;
    }

    public String getVerCode() {
        return verCode;
    }

    public void setVerCode(String verCode) {
        this.verCode = verCode;
    }

    public static void main(String [] args){

 /*
        System.out.print("请录入用户名:");
        String userName = scanner.nextLine();
        test.setUserName(userName);
        System.out.print("请录入密码:");
        String password = scanner.nextLine();
        test.setPassword(password);
*/
       /* Scanner scanner = new Scanner(System.in);
        System.out.print("请录入过去一小时走的步数:");
        int stepNum= Integer.parseInt(scanner.nextLine());*/

        try {

            System.out.println(200*(((float)1000/10000)));
            System.out.println();
            if(true){
                return ;
            }
            Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
            Random random = new Random();
            Properties properties = new Properties();
            properties.load(new FileInputStream("vindonn.properties"));
            String userName = properties.getProperty("userName");
            String password = properties.getProperty("password");
            String mobileIMEI = properties.getProperty("mobileIMEI");
            String verCode = properties.getProperty("verCode");
            String deviceId = properties.getProperty("deviceId");
            TestVindonnClient test = new TestVindonnClient();
            test.setUserName(userName);
            test.setPassword(password);
            test.setMobileIMEI(mobileIMEI);
            test.setVerCode(verCode);
            test.setDeviceId(deviceId);
            Enumeration<String> enumeration = (Enumeration<String>)properties.propertyNames();
            Map<String,Integer> dayAndStep = new HashMap<String,Integer>();
            while(enumeration.hasMoreElements()){
                String day =  enumeration.nextElement();
                if(pattern.matcher(day).matches()){
                    String step = properties.getProperty(day);
                    step = step.trim();
                    int spliter = step.indexOf("-");
                    if(spliter >0){
                        int randomSetpStart = Integer.parseInt(step.substring(0, spliter));
                        int randomSetpEnd = Integer.parseInt(step.substring(spliter + 1));
                        step = (randomSetpStart+random.nextInt(randomSetpEnd- randomSetpStart))+"";
                    }
                    System.out.println(day+":"+Integer.parseInt(step));
                    dayAndStep.put(day,Integer.parseInt(step));
                }
            }
            test.showMessage(dayAndStep);
            boolean continueFlag = test.readInputYOrN();
            if(continueFlag){
                test.run(dayAndStep);
                System.out.println("write data success,to exit!");
            }else{
                System.out.println("not write data,to exit!");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
