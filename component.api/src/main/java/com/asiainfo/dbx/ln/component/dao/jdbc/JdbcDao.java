package com.asiainfo.dbx.ln.component.dao.jdbc;

import com.asiainfo.dbx.ln.component.dao.ResultOperator;
import com.asiainfo.dbx.ln.component.dao.RowLimit;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *   基于jdbc 进行数据库数据访问
 *
 * Created by yanlei on 2014/8/6.
 */
public interface JdbcDao {

    /**
     * 用于一条SQL查询出大量数据，不能所有数据都存入List再返回,因为占用内存，
     * 查询出一条记录，生成一个Map<列属性名, 列值>,并调用ResultOperator对其处理，处理完成后该Map丢弃，不占用内存，之后再处理下一个
     * @param sql 查询的sql语名
     * @param handler 对每一条数据进行处理的接口，数据形式Map<列属性名, 列值>
     */
    public  void select(String sql, ResultOperator<Map<String, Object>> handler);

    /**
     * 用于一条SQL查询出大量数据，不能所有数据都存入List再返回,因为占用内存，
     * 查询出一条记录，生成一个Map<列属性名, 列值>,并调用ResultOperator对其处理，处理完成后该Map丢弃，不占用内存，之后再处理下一个
     * @param sql 查询的sql语名
     * @param parameters parameters sql语名中用到的参数
     * @param handler 对每一条数据进行处理的接口，数据形式Map<列属性名, 列值>
     */
    public  void select(String sql, Object[] parameters, ResultOperator<Map<String, Object>> handler);

    /**
     * 用于一条SQL查询出大量数据，不能所有数据都存入List再返回,因为占用内存，
     * 查询出一条记录，生成一个Map<列属性名, 列值>,并调用ResultOperator对其处理，处理完成后该Map丢弃，不占用内存，之后再处理下一个
     * @param sql 查询的sql语名
     * @param parameters  sql语名中用到的参数
     * @param parametersSqlType  parameters参数值对应到数据库中列的SQL类型
     * @param handler 对每一条数据进行处理的接口，数据形式Map<列属性名, 列值>
     */
    public  void select(String sql, Object[] parameters, int[] parametersSqlType, ResultOperator<Map<String, Object>> handler);

    /**
     * 用于一条SQL查询出大量数据，不能所有数据都存入List再返回,因为占用内存，
     * 查询出一条记录，反射生成javaBean,并调用ResultOperator对其处理，处理完成后该JavaBean丢弃，不占用内存，之后再处理下一个
     * @param sql 查询的sql语名
     * @param elementType 生成的javabean 类型
     * @param handler 对每一条数据进行处理的接口，数据形式javaBean
     */

    public <T> void select(String sql, Class<T> elementType, ResultOperator<T> handler);

    /**
     * 用于一条SQL查询出大量数据，不能所有数据都存入List再返回,因为占用内存，
     * 查询出一条记录，反射生成javaBean,并调用ResultOperator对其处理，处理完成后该JavaBean丢弃，不占用内存，之后再处理下一个
     * @param sql 查询的sql语名
     * @param parameters  sql语名中用到的参数
     * @param elementType 生成的javabean 类型
     * @param handler 对每一条数据进行处理的接口，数据形式javaBean
     */

    public <T> void select(String sql, Object[] parameters, Class<T> elementType, ResultOperator<T> handler);

    /**
     * 用于一条SQL查询出大量数据，不能所有数据都存入List再返回,因为占用内存，
     * 查询出一条记录，反射生成javaBean,并调用ResultOperator对其处理，处理完成后该JavaBean丢弃，不占用内存，之后再处理下一个
     * @param sql 查询的sql语名
     * @param parameters  sql语名中用到的参数
     * @param parametersSqlType  parameters参数值对应到数据库中列的SQL类型
     * @elementType 生成的javabean 类型
     * @param handler 对每一条数据进行处理的接口，数据形式javaBean
     */

    public <T>  void select(String sql, Object[] parameters, int[] parametersSqlType, Class<T> elementType, ResultOperator<T> handler);

    /**
     * 执行SQL查询，查询出的结果存入List并返回，List中每一个Map<列名，列值>为一条记录,
     * @param sql 要执行的sql
     * @return   List<Map<String,Object>>  查询结果，List中每一个Map<列名，列值>为一条记录,
     * @throws Exception
     */
    public List<Map<String,Object>> selectList(String sql) throws Exception;
    /**
     * 执行SQL查询，查询出的结果存入List并返回，List中每一个Map<列名，列值>为一条记录,
     * @param sql 要执行的sql
     * @param parameters   sql语名中用到的参数
     * @return   List<Map<String,Object>>  查询结果，List中每一个Map<列名，列值>为一条记录,
     * @throws Exception
     */
    public List<Map<String,Object>> selectList(String sql, Object[] parameters) throws Exception;

    /**
     * 执行SQL查询，查询出的结果存入List并返回，List中每一个Map<列名，列值>为一条记录,
     * @param sql 要执行的sql
     * @param parameters   sql语名中用到的参数
     * @param parametersSqlType parameters参数值对应到数据库中列的SQL类型
     * @return   List<Map<String,Object>>  查询结果，List中每一个Map<列名，列值>为一条记录,
     * @throws Exception
     */

    public List<Map<String,Object>> selectList(String sql, Object[] parameters, int[] parametersSqlType) throws Exception;


    /**
     * 执行SQL查询，分页查询出的结果存入List并返回，List中每一个Map<列名，列值>为一条记录,
     * @param sql 要执行的sql
     *
     * @param   rowLimit   分页信息，属性offset 记录中的偏移量，属性limit 返回记录数
     * @return   List<Map<String,Object>>  查询结果，List中每一个Map<列名，列值>为一条记录,
     * @throws Exception
     */

    public List<Map<String,Object>> selectList(String sql, RowLimit rowLimit) throws Exception;

    /**
     * 执行SQL查询，分页查询出的结果存入List并返回，List中每一个Map<列名，列值>为一条记录,
     * @param sql 要执行的sql
     * @param parameters   sql语名中用到的参数
     * @param   rowLimit   分页信息，属性offset 记录中的偏移量，属性limit 返回记录数
     * @return   List<Map<String,Object>>  查询结果，List中每一个Map<列名，列值>为一条记录,
     * @throws Exception
     */

    public List<Map<String,Object>> selectList(String sql, Object[] parameters, RowLimit rowLimit) throws Exception;
    /**
     * 执行SQL查询，分页查询出的结果存入List并返回，List中每一个Map<列名，列值>为一条记录,
     * @param sql 要执行的sql
     * @param parameters   sql语名中用到的参数
     * @param parametersSqlType   parameters参数值对应到数据库中列的SQL类型
     * @param   rowLimit   分页信息，属性offset 记录中的偏移量，属性limit 返回记录数
     * @return   List<Map<String,Object>>  查询结果，List中每一个Map<列名，列值>为一条记录,
     * @throws Exception
     */
    public List<Map<String,Object>> selectList(String sql, Object[] parameters, int[] parametersSqlType, RowLimit rowLimit) throws Exception;

    /**
     *  执行SQL查询，查询出的结果存入List并返回，List中每一个javabean为一条记录,javabean的类型由elementType决定
     * @param sql 要执行的sql
     * @param elementType 生成javabean的类型
     * @return List<T> 查询结果
     * @throws Exception
     */
    public <T> List<T> selectList(String sql, Class elementType) throws Exception;

    /**
     *  执行SQL，查询出的结果存入List并返回，List中每一个javabean为一条记录,javabean的类型由elementType决定
     * @param sql  要执行的sql
     * @param parameters sql语名中用到的参数
     * @param elementType 生成javabean的类型
     * @return List<T> 查询结果
     * @throws Exception
     */
    public <T> List<T> selectList(String sql, Object[] parameters, Class elementType) throws Exception;

    /**
     *  执行SQL查询，查询出的结果存入List并返回，List中每一个javabean为一条记录,javabean的类型由elementType决定
     * @param sql  要执行的sql
     * @param parameters sql语名中用到的参数
     * @param parametersSqlType  parameters参数值对应到数据库中列的SQL类型
     * @param elementType 生成javabean的类型
     * @return List<T> 查询结果
     * @throws Exception
     */

    public <T> List<T> selectList(String sql, Object[] parameters, int[] parametersSqlType, Class<T> elementType) throws Exception;

    /**
     * 执行SQL查询，查询出的分页结果存入List并返回，List中每一个javabean为一条记录,javabean的类型由elementType决定
     * @param sql 要执行的sql
     * @param elementType 生成javabean的类型
     * @param rowLimit  分页信息，属性offset 记录中的偏移量，属性limit 返回记录数
     * @return
     * @throws Exception
     */
    public <T> List<T> selectList(String sql, Class<T> elementType, RowLimit rowLimit) throws Exception;
    /**
     * 执行SQL查询，查询出的分页结果存入List并返回，List中每一个javabean为一条记录,javabean的类型由elementType决定
     * @param sql 要执行的sql
     * @param parameters sql语名中用到的参数
     * @param elementType 生成javabean的类型
     * @param rowLimit  分页信息，属性offset 记录中的偏移量，属性limit 返回记录数
     * @return List<T> 查询结果
     * @throws Exception
     */
    public <T> List<T> selectList(String sql, Object[] parameters, Class<T> elementType, RowLimit rowLimit) throws Exception;

    /**
     * 执行SQL查询，查询出的分页结果存入List并返回，List中每一个javabean为一条记录,javabean的类型由elementType决定
     * @param sql 要执行的sql
     * @param parameters sql语名中用到的参数
     * @param parametersSqlType parameters参数值对应到数据库中列的SQL类型
     * @param elementType 生成javabean的类型
     * @param rowLimit  分页信息，属性offset 记录中的偏移量，属性limit 返回记录数
     * @return  List<T> 查询结果
     * @throws Exception
     */
    public <T> List<T> selectList(String sql, Object[] parameters, int[] parametersSqlType, Class<T> elementType, RowLimit rowLimit) throws Exception;

    /**
     * 执行SQL查询，查询结果的第一条记录，转换为JAVA对象，JAVA对象的类型由elementType决定 ，查询结果为空则返回null
     * @param sql 要执行的sql
     * @param    elementType     如果java基本类型，则第一条记录的唯一列转换为java基本类型，要求sql语句的查询结果只能有一列，否则报错IncorrectResultSetColumnCountException，
     *                           如果是javabean,则查询结果的第一条记录转换为javabean
     * @return javabean
     */

    public <T> T selectOne(String sql, Class<T> elementType);

    /**
     * 执行SQL查询，查询结果的第一条记录，转换为JAVA对象，JAVA对象的类型由elementType决定 ，查询结果为空则返回null
     * @param sql 要执行的sql
     * @param parameters    sql语名中用到的参数
     @param    elementType     如果java基本类型，则第一条记录的唯一列转换为java基本类型，要求sql语句的查询结果只能有一列，否则报错IncorrectResultSetColumnCountException，
     *                           如果是javabean,则查询结果的第一条记录转换为javabean
     * @return
     */
    public <T> T selectOne(String sql, Object[] parameters, Class<T> elementType);

    /**
     * 执行SQL查询，查询结果的第一条记录，转换为JAVA对象，JAVA对象的类型由elementType决定 ，查询结果为空则返回null
     * @param sql 要执行的sql
     * @param parameters    sql语名中用到的参数
     * @param parametersSqlType  parameters参数值对应到数据库中列的SQL类型
     * @param    elementType     如果java基本类型，则第一条记录的唯一列转换为java基本类型，要求sql语句的查询结果只能有一列，否则报错IncorrectResultSetColumnCountException，
     *                           如果是javabean,则查询结果的第一条记录转换为javabean
     * @return  javabean
     */
    public <T> T selectOne(String sql, Object[] parameters, int[] parametersSqlType, Class<T> elementType);
    /**
     * 执行SQL查询，查询结果的第一条记录，存入Map<列名，列值> ，查询结果为空则返回null
     * @param sql 要执行的sql
     * @return Map<列名，列值>
     */

    public  Map<String,Object> selectOne(String sql);
    /**
     * 执行SQL查询，查询结果的第一条记录，存入Map<列名，列值> ，查询结果为空则返回null
     * @param sql 要执行的sql
     * @param parameters    sql语名中用到的参数
     * @return Map<列名，列值>
     */
    public  Map<String,Object> selectOne(String sql, Object[] parameters);

    /**
     * 执行SQL查询，查询结果的第一条记录，存入Map<列名，列值> ，查询结果为空则返回null
     * @param sql 要执行的sql
     * @param parameters    sql语名中用到的参数
     * @return Map<列名，列值>
     */

    public  Map<String,Object>selectOne(String sql, Object[] parameters, int[] parametersSqlType);


    /**
     * 执行sql更新数据,
     * @param sql 要执行的sql
     * @return 更新的记录数
     */
    public int update(String sql);
    /**
     * 执行sql更新数据,
     * @param sql 要执行的sql
     * @param   parameters  sql语名中用到的参数
     * @return 更新的记录数
     */
    public int update(String sql, Object[] parameters);

    /**
     * 执行sql更新数据,
     * @param sql 要执行的sql
     * @param   parameters  sql语名中用到的参数
     * @param parametersSqlType  parameters参数值对应到数据库中列的SQL类型
     * @return 更新的记录数
     */

    public  int update(String sql, Object[] parameters, int[] parametersSqlType);

    /**
     * 执行sql批量更新数据,
     * @param sql 要执行的sql
     * @param   parameterIterator  参数迭代器
     * @return 更新的记录数
     */

    public  int batchUpdate(String sql, Iterator<Object[]> parameterIterator);
    /**
     * 执行sql批量更新数据,
     * @param sql 要执行的sql
     * @param   parameterIterator  参数迭代器
     * @param   flushBatchSize  每执行多少次参数更新时，更新数据库
     * @return 更新的记录数
     */
    public  int batchUpdate(String sql, Iterator<Object[]> parameterIterator, int flushBatchSize);

    /**
     * 执行sql批量更新数据,
     * @param sql 要执行的sql
     * @param   parameterIterator  参数迭代器
     * @param   parametersSqlType   parameters参数值对应到数据库中列的SQL类型
     * @return 更新的记录数
     */

    public  int batchUpdate(String sql, Iterator<Object[]> parameterIterator, int[] parametersSqlType);

    /**
     * 执行sql批量更新数据,
     * @param sql 要执行的sql
     * @param   parameterIterator  参数迭代器
     * @param   parametersSqlType   parameters参数值对应到数据库中列的SQL类型
     * @param   flushBatchSize  每执行多少次参数更新时，刷新更新数据到数据库
     * @return 更新的记录数
     */

    public int batchUpdate(String sql, Iterator<Object[]> parameterIterator, int[] parametersSqlType, int flushBatchSize);

    /**
     * 执行SQL命令，如建表，删表等等
     * @param sql
     */
    public void execute(String sql);
}
