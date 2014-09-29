package com.asiainfo.dbx.ln.component.dao.mybatis.generator;

import com.asiainfo.dbx.ln.component.util.AppResourceUtils;
import org.mybatis.generator.config.*;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanlei on 2014/8/1.
 */
public class MyBatisFileGeneratorByXml implements MyBatisFileGenerator {
    private final Logger logger = LoggerFactory.getLogger(MyBatisFileGeneratorByXml.class);
    private String generatorXml;

    public String getGeneratorXml() {
        return generatorXml;
    }

    public void setGeneratorXml(String generatorXml) {
        this.generatorXml = generatorXml;
    }

    @Override
    public boolean generate() {
        try {
            List<String> warnings = new ArrayList<String>();
            boolean overwrite = true;
            Resource resource = AppResourceUtils.getResource(this.getGeneratorXml());
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(resource.getFile());
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            org.mybatis.generator.api.MyBatisGenerator myBatisGenerator = new org.mybatis.generator.api.MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
            logger.info("Generate MyBatis File Finish!");
            return true;
        }catch (Exception e){
            logger.error(" Generate MyBatis File Fail:",e);
            return false;
        }
    }
}
