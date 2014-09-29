package  com.asiainfo.dbx.ln.component.dao.mybatis;


import com.asiainfo.dbx.ln.component.dao.ResultOperator;
import com.asiainfo.dbx.ln.component.dao.RowLimit;
import com.asiainfo.dbx.ln.component.util.AppStringUtils;
import com.asiainfo.dbx.ln.component.util.AppValidationUtils;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MyBatisDaoImpl implements MyBatisDao {
    private final Logger logger = LoggerFactory.getLogger(MyBatisDaoImpl.class);


    @Resource(name="sqlSessionTemplate")
    private SqlSessionTemplate sqlSessionTemplate;

    public SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }

    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }
    @Resource(name="batchSqlSessionTemplate")
    private SqlSessionTemplate batchSqlSessionTemplate;

    public SqlSessionTemplate getBatchSqlSessionTemplate() {
        return batchSqlSessionTemplate;
    }

    public void setBatchSqlSessionTemplate(SqlSessionTemplate batchSqlSessionTemplate) {
        this.batchSqlSessionTemplate = batchSqlSessionTemplate;
    }

    
    public void select(String statement, ResultOperator handler){
        this.select(statement, null, null, handler);
    }


    public void select(String statement, RowLimit rowLimit, ResultOperator handler){
        this.select(statement, null, rowLimit, handler);
    }

    
    public void select(String statement, Object parameter, ResultOperator handler){
        this.select(statement, parameter, null, handler);
    }


    
    public void select(String statement, Object parameter, RowLimit rowLimit, ResultOperator handler){
         RowBounds rowBounds = convertToRowBounds(rowLimit);
        ResultHandler resultHandler = convertToResultHandler(handler);
        this.getSqlSessionTemplate().select(statement, parameter, rowBounds, resultHandler);
    }


    
    public  <A> List<A> selectList(String statement){
        return  this.selectList(statement, null,null);
    }


    public  <E> List<E> selectList(String statement, RowLimit rowLimit){
        return  this.selectList(statement,null, rowLimit);
    }




    public  <E> List<E> selectList(String statement, Object parameter){
       return  this.selectList(statement,parameter, null);
    }

    public   <E> List<E> selectList(String statement, Object parameter, RowLimit rowLimit){
        RowBounds rowBounds = convertToRowBounds(rowLimit);
        return  this.getSqlSessionTemplate().selectList(statement,parameter,rowBounds);
    }




    public <K, V> Map<K, V> selectMap(String statement, String mapKey){
        return this.selectMap(statement, null, mapKey, null);
    }

    public <K, V> Map<K, V> selectMap(String statement, String mapKey, RowLimit rowLimit){
        return this.selectMap(statement,null,mapKey,rowLimit);
    }

    public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey){
        return this.selectMap(statement,parameter,mapKey,null);
    }

    public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey,RowLimit rowLimit){
        RowBounds rowBounds = this.convertToRowBounds(rowLimit);
        return this.getSqlSessionTemplate().selectMap(statement,parameter,mapKey,rowBounds);
    }



  /*  public <T> T selectOne(String statement){

        return this.getSqlSessionTemplate().selectOne(statement);
    }


    public <T> T selectOne(String statement, Object parameter){
        return this.getSqlSessionTemplate().selectOne(statement,parameter);
    }
*/

    
    public int update(String statement){
        return this.getSqlSessionTemplate().update(statement);
    }

    
    public int  update(String statement, Object parameter){
        return this.getSqlSessionTemplate().update(statement,parameter);
    }
    

    


    


    


    private RowBounds convertToRowBounds(RowLimit rowLimit){
        if(AppValidationUtils.isNull(rowLimit)){
            return RowBounds.DEFAULT;
        }else{
            return new RowBounds(rowLimit.getOffset(),rowLimit.getLimit());
        }
    }
    private ResultHandler convertToResultHandler(final ResultOperator handler){
        return new ResultHandler(){

            public void handleResult(ResultContext context) {
                boolean flag = handler.deal(context.getResultObject(),context.getResultCount());
                if(!flag){
                    context.stop();
                }
            }
        };
    }

    @Override
    public <T> T selectOne(String sqlId) {
       return (T)this.selectOne(sqlId,null);
    }

    @Override
    public <T> T selectOne(String sqlId, Object parameter) {
       final  List<T> list = new ArrayList<T>(1);
         this.select(sqlId,parameter,new ResultOperator<T>() {
            @Override
            public boolean deal(T resultObject, int currentRowNum) {
                list.add(resultObject);
                return false;
            }
        });
        return list.size()>0?list.get(0):null;
    }

    @Override
    public void batchUpdate(String sqlId, Iterator<Object> parameterIterator, int flushBatchSize) {
        int batchSize = 0;
        while(parameterIterator.hasNext()){
            Object parameter = parameterIterator.next();
            this.getBatchSqlSessionTemplate().update(sqlId,parameter);
            batchSize++;
            if(flushBatchSize>0 && batchSize%flushBatchSize==0){
                List<BatchResult> batchResultList =   this.getBatchSqlSessionTemplate().flushStatements();
                logBatchResult(batchResultList);

            }
        }
      List<BatchResult> batchResultList=   this.getBatchSqlSessionTemplate().flushStatements();
      logBatchResult(batchResultList);

    }

    private void logBatchResult(List<BatchResult> batchResultList){
        if(AppValidationUtils.notEmpty(batchResultList)){
            logger.debug("update sql:{}",batchResultList.get(0).getSql());
            for(BatchResult batchResult:batchResultList){
                logger.debug("parameter {},update num:",batchResult.getParameterObjects(), AppStringUtils.join(batchResult.getUpdateCounts(),","));
            }
        }
    }

    public void batchUpdate(String sqlId, Iterator<Object> parameterIterator) {
        this.batchUpdate(sqlId,parameterIterator,0);
    }
}