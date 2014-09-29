package com.asiainfo.dbx.ln.component.api;



import com.asiainfo.dbx.ln.component.util.AppDeployPathUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URLEncoder;


/**
 * Created by yanlei on 2014/9/9.
 */
public class WebServer {
    private final  Logger logger = LoggerFactory.getLogger(WebServer.class);
    int port = 18086;
    String webAppDir =null;

    public String getWebAppDir() {
        if(webAppDir ==null){
            if(AppDeployPathUtils.getDeployWebDir() != null) {
                webAppDir = AppDeployPathUtils.getDeployWebDir();
            }else{
                webAppDir = "deploy\\web";
            }
        }
        return webAppDir;
    }

    public void setWebAppDir(String webAppDir) {

            this.webAppDir = webAppDir;


    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void start() throws Exception{
        logger.info("start web server");
        Server server = new Server(port);
        logger.info("port:{}",port);
        WebAppContext context = new WebAppContext();
        String webAppDir = this.getWebAppDir();
        String webxmlPath = webAppDir + File.separator+"WEB-INF"+ File.separator+"web.xml";
        logger.info("web.xml:{}",webxmlPath);
        context.setDescriptor(webxmlPath);  //指定web.xml配置文件
        context.setResourceBase(webAppDir);  //指定webapp目录
        logger.info("docBase:{}",webAppDir);
        context.setContextPath("/");
        logger.info("contextPath:{}","/");
        context.setParentLoaderPriority(true);
        server.setHandler(context);
        server.start();
        logger.info("start web server OK!");
    }
    public static void main(String args []){
        try {
            new WebServer().start();
            URLEncoder.encode("xxx","utf-8");
            String str = "中文";
            System.out.println(URLEncoder.encode(str, "utf-8"));
            String str1 = new String (str.getBytes("UTF-8"),"iso-8859-1");
            System.out.println(str1);
            String str2 = new String (str1.getBytes("iso-8859-1"),"UTF-8");
            System.out.println(str2);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
