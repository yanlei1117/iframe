package com.asiainfo.dbx.ln.component.dao.jdbc;

import com.asiainfo.dbx.ln.component.dao.ResultOperator;
import com.asiainfo.dbx.ln.component.dao.RowLimit;
import com.asiainfo.dbx.ln.component.dao.Utils.DasUtils;
import com.asiainfo.dbx.ln.component.util.AppBeanUtils;
import com.asiainfo.dbx.ln.component.util.AppValidationUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by yanlei on 2014/8/4.
 */
public class JdbcDaoImpl implements JdbcDao {

    @Resource(name="jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    final ColumnMapRowMapper  columnMapRowMapper = new ColumnMapRowMapper();

    @Override
    public void select(String sql, final ResultOperator<Map<String, Object>> handler) {
        this.select(sql,null,(int [])null,handler);
    }



    @Override
    public void select(String sql, Object[] parameters, final ResultOperator<Map<String, Object>> handler) {
        this.select(sql,parameters,(int [])null,handler);
    }


    @Override
    public void select(String sql, Object[] parameters, int[] argTypes, final ResultOperator<Map<String, Object>> handler) {
        argTypes = getUnknowParameterTypesForUnSetSqlType(parameters,argTypes);
        this.getJdbcTemplate().query(sql,parameters,argTypes,new ResultSetExtractor(){
            @Override
            public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                int rowNum = 0;
                while (rs.next()) {
                    rowNum++;
                    Map<String,Object> result = columnMapRowMapper.mapRow(rs,rowNum);
                    boolean flag = handler.deal(result,rowNum);
                    if(!flag){
                        break;
                    }
                }
                return null;
            }
        });

    }

    @Override
    public <T> void select(String sql, final Class<T> elementType, final ResultOperator<T> handler) {
        this.select(sql,null,null,elementType,handler);
    }

    @Override
    public <T> void select(String sql, Object[] parameters, final Class<T> elementType, final ResultOperator<T> handler) {
        this.select(sql,parameters,null,elementType,handler);
    }

    private int [] getUnknowParameterTypesForUnSetSqlType(Object[] parameters,int[] argTypes){
        if(parameters != null && argTypes == null){
            argTypes  = new int [parameters.length];
            for(int i=0;i<parameters.length;i++){
                argTypes [i]=SqlTypeValue.TYPE_UNKNOWN;
            }
        }
        return argTypes;
    }

    public <T> RowMapper<T> getRowMapper(Class<T> elementType){
        if(AppValidationUtils.notNull(elementType)){
            if(elementType.getName().startsWith("java")){
                return new SingleColumnRowMapper<T>(elementType);
            }else{
                return new BeanPropertyRowMapper<T>(elementType);
            }
        }
        return null;
    }
    @Override
    public <T>  void select(String sql, Object[] parameters, int[] argTypes, final Class<T> elementType, final ResultOperator<T> handler) {
        argTypes = getUnknowParameterTypesForUnSetSqlType(parameters,argTypes);
        this.getJdbcTemplate().query(sql,parameters,argTypes,new ResultSetExtractor(){
            @Override
            public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                int rowNum = 0;
                 RowMapper<T> rowMapper  = getRowMapper(elementType);;

                while (rs.next()) {
                    rowNum++;
                    T result = rowMapper.mapRow(rs,rowNum);
                    boolean flag = handler.deal(result,rowNum);
                    if(!flag){
                        break;
                    }
                }
                return null;
            }
        });
    }

    @Override
    public List<Map<String,Object>> selectList(String sql) throws Exception {

        return  selectList(sql,(Object [])null,(int [])null,(RowLimit)null);
    }
    @Override
    public List<Map<String,Object>> selectList(String sql, Object[] parameters) throws Exception {

        return  selectList(sql,parameters,(int [])null,(RowLimit)null);
    }

    @Override
    public List<Map<String,Object>> selectList(String sql, Object[] parameters, int[] parametersSqlType) throws Exception {

        return  selectList(sql,parameters,parametersSqlType,(RowLimit)null);

    }

    @Override
    public List<Map<String,Object>> selectList(String sql, RowLimit rowLimit) throws Exception {

        return  selectList(sql,(Object [])null,(int [])null,rowLimit);
    }

    @Override
    public List<Map<String,Object>> selectList(String sql, Object[] parameters, RowLimit rowLimit) throws Exception {

        return  selectList(sql, parameters, (int[]) null, rowLimit);
    }


    @Override
    public List<Map<String,Object>> selectList(String sql, Object[] parameters, int[] parametersSqlType, RowLimit rowLimit) throws Exception {
        if(rowLimit != null){
            sql = this.paginationSql(sql,rowLimit);
        }
        parametersSqlType = getUnknowParameterTypesForUnSetSqlType(parameters,parametersSqlType);
       return  this.getJdbcTemplate().queryForList(sql,parameters,parametersSqlType);

    }

    @Override
    public <T> List<T> selectList(String sql, Class elementType) throws Exception {

        return  selectList(sql,null,null,elementType,null);
    }

    @Override
    public <T> List<T> selectList(String sql, Object[] parameters, Class elementType) throws Exception {

        return  selectList(sql,parameters,null,elementType,null);
    }

    @Override
    public <T> List<T> selectList(String sql, Object[] parameters, int[] parametersSqlType, Class<T> elementType) throws Exception {

        return  selectList(sql, parameters, parametersSqlType, elementType, null);
    }


    @Override
    public <T> List<T> selectList(String sql, Class<T> elementType, RowLimit rowLimit) throws Exception {

        return  selectList(sql,null,null,elementType,rowLimit);
    }

    @Override
    public <T> List<T> selectList(String sql, Object[] parameters, Class<T> elementType, RowLimit rowLimit) throws Exception {

        return  selectList(sql,parameters,null,elementType,rowLimit);
    }



    @Override
    public <T> List<T> selectList(String sql, Object[] parameters, int[] parametersSqlType, Class<T> elementType, RowLimit rowLimit) throws Exception {
        if(rowLimit != null){
            sql = this.paginationSql(sql,rowLimit);
        }
        parametersSqlType = getUnknowParameterTypesForUnSetSqlType(parameters,parametersSqlType);
        return  this.getJdbcTemplate().query (sql,parameters,parametersSqlType,getRowMapper(elementType));
    }







    private    String paginationSql(String oldSql, RowLimit rowLimit) throws Exception{
            return DasUtils.paginationSql(this.getJdbcTemplate().getDataSource().getConnection(),oldSql,rowLimit);
    }



    private <T,E>  Map<E, T> convertListToMap(List<T> list,String mapKey) throws Exception{
        if(AppValidationUtils.notNull(list)){
            Map<E,T> map = new LinkedHashMap<E,T>();
            for(T obj:list){
                E key = (E)AppBeanUtils.getProperty(obj,mapKey);
                map.put(key,obj);
            }
            return map;
        }
        return null;
    }





    @Override
    public <T> T selectOne(String sql, Class<T> elementType) {
        return this.selectOne(sql, (Object[]) null, (int[]) null, elementType);
    }

    @Override
    public <T> T selectOne(String sql, Object[] parameters, Class<T> elementType) {

        return this.selectOne(sql, (Object[]) parameters, (int[]) null, elementType);
    }

    @Override
    public <T> T selectOne(String sql, Object[] parameters, int[] parametersSqlType, Class<T> elementType) {
        parametersSqlType = getUnknowParameterTypesForUnSetSqlType(parameters,parametersSqlType);
        final List<T> list = new ArrayList<T>(1);
        this.select(sql,parameters,parametersSqlType,elementType,new ResultOperator<T>() {
            @Override
            public boolean deal(T resultObject, int currentRowNum) {
                list.add(resultObject);
                return false;
            }
        });
        return list.size()>0?list.get(0):null;

    }


    @Override
    public Map<String,Object> selectOne(String sql) {
        return this.selectOne(sql, (Object[]) null, (int[]) null);
    }

    @Override
    public Map<String,Object> selectOne(String sql, Object[] parameters) {

        return this.selectOne(sql, parameters, (int[]) null);
    }

    @Override
    public Map<String,Object>selectOne(String sql, Object[] parameters, int[] parametersSqlType) {
        parametersSqlType = getUnknowParameterTypesForUnSetSqlType(parameters,parametersSqlType);
        final List<Map<String,Object>> list = new ArrayList<Map<String,Object>>(1);
        this.select(sql,parameters,parametersSqlType,new ResultOperator<Map<String,Object>>() {
            @Override
            public boolean deal(Map<String, Object> resultObject, int currentRowNum) {
                list.add(resultObject);
                return false;
            }
        });
        return list.size()>0?list.get(0):null;

    }







    @Override
    public int update(String sql) {
        return this.update(sql,(Object[])null,(int [])null);
    }


    @Override
    public int update(String sql, Object[] parameters) {
        return this.update(sql,parameters,(int [])null);
    }
    @Override
    public int update(String sql, Object[] parameters, int[] parametersSqlType) {
        parametersSqlType = getUnknowParameterTypesForUnSetSqlType(parameters,parametersSqlType);
        return this.getJdbcTemplate().update(sql,parameters,parametersSqlType);
    }


    @Override
    public int batchUpdate(String sql, Iterator<Object[]> parameterIterator) {
        return this.batchUpdate(sql,parameterIterator,(int[])null,0);
    }

    @Override
    public int batchUpdate(String sql, Iterator<Object[]> parameterIterator, int flushBatchSize) {
        return this.batchUpdate(sql,parameterIterator,(int[])null,flushBatchSize);
    }

    @Override
    public int batchUpdate(String sql, Iterator<Object[]> parameterIterator, int[] argType) {
        return this.batchUpdate(sql,parameterIterator,(int[])argType,0);
    }

    @Override
    public int batchUpdate(String sql, final Iterator<Object[]> parameterIterator, final int[] parametersSqlType, final int flushBatchSize) {

      return   this.getJdbcTemplate().execute(sql,new PreparedStatementCallback<Integer>() {
           @Override
           public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
               int batchNum = 0;
               int updateNum = 0;
               while(parameterIterator.hasNext()){
                    Object [] parameters =   parameterIterator.next();
                    int [] parametersSqlTypes = getUnknowParameterTypesForUnSetSqlType(parameters,parametersSqlType);
                   int parameterPosition = 0;
                   for(Object param:parameters) {

                       parameterPosition++;
                       if(parameters != null){
                           StatementCreatorUtils.setParameterValue(ps, parameterPosition, parametersSqlTypes[parameterPosition-1], param);
                       }else{
                           StatementCreatorUtils.setParameterValue(ps, parameterPosition,SqlTypeValue.TYPE_UNKNOWN, param);
                       }

                   }

                   ps.addBatch();
                   batchNum++;
                   if(flushBatchSize >0 && batchNum%flushBatchSize ==0){
                      int updateNums []  =  ps.executeBatch();
                       updateNum +=intArrayToInt(updateNums);
                   }

               }
               int updateNums []  =  ps.executeBatch();
               updateNum +=intArrayToInt(updateNums);
              return updateNum;
           }
       });

    }

    int  intArrayToInt(int [] data){
        int v =0;
        for(int value:data){
            v+=value;
        }
        return v;
    }
    @Override
    public void execute(String sql) {
        this.getJdbcTemplate().execute(sql);
    }
}
