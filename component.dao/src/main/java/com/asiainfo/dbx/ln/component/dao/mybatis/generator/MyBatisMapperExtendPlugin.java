package com.asiainfo.dbx.ln.component.dao.mybatis.generator;

import com.asiainfo.dbx.ln.component.dao.mybatis.MyBatisMapper;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import java.util.List;

/**
 * 使生成的MyBatits的Mapper接口扩展com.asiainfo.dbx.ln.component.dao.mybatis.MyBatisMapper接口
 * 目的：对所有Mapper接口统一处理
 *
 * Created by yanlei on 2014/8/14.
 */
public class MyBatisMapperExtendPlugin extends PluginAdapter {

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        List<Method> methodList = interfaze.getMethods();
        String modelName = null;
        String modelExample = null;
        for(Method method:methodList){
            if(method.getName().equals("updateByExampleSelective")){
                List<Parameter> parameterList = method.getParameters();
                modelName = parameterList.get(0).getType().getShortName();
                modelExample = parameterList.get(1).getType().getShortName();
            }
        }
        interfaze.addImportedType(new FullyQualifiedJavaType(MyBatisMapper.class.getName()));
        interfaze.addSuperInterface(new FullyQualifiedJavaType(MyBatisMapper.class.getName()+"<"+modelName+","+modelExample+">"));
        return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }
}
