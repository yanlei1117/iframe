package com.asiainfo.dbx.ln.component.dao.mybatis;

import com.asiainfo.dbx.ln.component.dao.ResultOperator;
import com.asiainfo.dbx.ln.component.dao.RowLimit;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * 基于MyBatis 进行数据库数据访问
 *
 * 使用MyBatis进行数据操作时，可能需要一些参数，那么在映身的配置文件中如何引用参数呢？
 * parameter 参数说明：
 * 1.参数是数组时，配置xml，array[0]和array[1]是数组中第2个和每2个元素。
 <select id="selectByArrayParam" parameterType="map"    resultMap="BaseResultMap">
    select * from person where name =#{array[0],jdbcType=VARCHAR} and birthday=#{array[1],jdbcType=DATE}
 </select>
 2.参数是List时，配置xml,list[0]和list[1] 是列表中第1个和第2个元素。
 <select id="selectByListParam" parameterType="map" resultMap="BaseResultMap">
    select * from person where name =#{list[0],jdbcType=VARCHAR} and birthday=#{list[1],jdbcType=DATE}

 </select>
 3.参数是Map时，配置xml, param1和param2 是Map的key名
 <select id="selectByMapParam" parameterType="map" resultMap="BaseResultMap">
    select * from person where name =#{param1,jdbcType=VARCHAR} and birthday=#{param2,jdbcType=DATE}
 </select>
4.参数是Bean时，配置xml, id、name、identityNumber，sex、birthday、 stature是bean的属性名

 <insert id="insertPerson" parameterType="com.Person">
 insert into person (id, name, identity_number,
 sex, birthday, stature)
 values (#{id,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, #{identityNumber,jdbcType=INTEGER},
 #{sex,jdbcType=CHAR}, #{birthday,jdbcType=DATE}, #{stature,jdbcType=REAL})
 </insert>


 5. 在配置的xml中，_parameter指参数对象
 <if test="_parameter != null" >
    <include refid="Example_Where_Clause" />
 </if>
 *
 * Created by yanlei on 2014/8/3.
 */
public interface MyBatisDao {

    /**
     * 用于一条SQL查询出大量数据，不能所有数据都存入List再返回,因为占用内存，
     * 查询出一条记录，生成一个对象，对象类型由sqlId对应的配置信息决定，,并调用ResultOperator对其处理，处理完成后该对象丢弃，不占用内存，之后再处理下一个
     * @param sqlId sql对应的id值
     * @param parameter 参数对象
     * @param handler 对每一条数据进行处理的接口，数据形式Map<列属性名, 列值>
     */
    public <T> void    select(String sqlId, Object parameter, ResultOperator<T> handler);

    /**
     * 用于一条SQL查询出大量数据，不能所有数据都存入List再返回,因为占用内存，
     * 查询出一条记录，生成一个对象，对象类型由sqlId对应的配置信息决定，,并调用ResultOperator对其处理，处理完成后该对象丢弃，不占用内存，之后再处理下一个
     * @param sqlId sql对应的id值
     * @param handler 对每一条数据进行处理的接口，数据形式Map<列属性名, 列值>
     */

    public  <T> void  select(String sqlId, ResultOperator<T> handler);


    /**
     * 执行SQL查询，查询结果生成对象，对象类型由sqlId对应的配置信息决定，存入List返回
     * @param sqlId  sql对应的id值
     *
     * @return
     */
    public  <E> List<E> selectList(String sqlId);
    /**
     * 执行SQL查询，查询结果生成对象，对象类型由sqlId对应的配置信息决定，存入List返回
     * @param sqlId  sql对应的id值
     *  @param   rowLimit   分页信息，属性offset 记录中的偏移量，属性limit 返回记录数
     * @return
     */
    public  <E> List<E> selectList(String sqlId, RowLimit rowLimit);
    /**
     * 执行SQL查询，查询结果生成对象，对象类型由sqlId对应的配置信息决定，存入List返回
     * @param sqlId  sql对应的id值
     * @param   parameter  参数对象
     * @return
     */
    public  <E> List<E> selectList(String sqlId, Object parameter);
    /**
     * 执行SQ分页L查询，查询结果生成对象，对象类型由sqlId对应的配置信息决定，存入List返回
     * @param sqlId  sql对应的id值
     * @param   parameter  参数对象
     *  @param   rowLimit   分页信息，属性offset 记录中的偏移量，属性limit 返回记录数
     * @return
     */
    public   <E> List<E> selectList(String sqlId, Object parameter, RowLimit rowLimit);

    /**
     * 执行SQL查询，查询结果生成一个Map，每一条记录生成一对key/value,key是以mapKey为列名对应的列值，value是记录转换成的对象，对象类型由sqlId对应的配置信息决定
     * @param sqlId  sql对应的id值
     * @param   mapKey  做key的列名
     * @return
     */
    public   <K, V> Map<K, V> selectMap(String sqlId, String mapKey);
    /**
     * 执行SQL分页查询，查询结果生成一个Map，每一条记录生成一对key/value,key是以mapKey为列名对应的列值，value是记录转换成的对象，对象类型由sqlId对应的配置信息决定
     * @param sqlId  sql对应的id值
     * @param   mapKey  做key的列名
     *  @param   rowLimit   分页信息，属性offset 记录中的偏移量，属性limit 返回记录数
     * @return
     */
    public  <K, V> Map<K, V> selectMap(String sqlId, String mapKey, RowLimit rowLimit);
    /**
     * 执行SQL查询，查询结果生成一个Map，每一条记录生成一对key/value,key是以mapKey为列名对应的列值，value是记录转换成的对象，对象类型由sqlId对应的配置信息决定
     * @param sqlId  sql对应的id值
     * @param   parameter  参数对象
     * @param   mapKey  做key的列名
     * @return
     */
    public  <K, V> Map<K, V> selectMap(String sqlId, Object parameter, String mapKey);
    /**
     * 执行SQL查询，查询结果生成一个Map，每一条记录生成一对key/value,key是以mapKey为列名对应的列值，value是记录转换成的对象，对象类型由sqlId对应的配置信息决定
     * @param sqlId  sql对应的id值
     * @param   parameter  参数对象
     * @param   mapKey  做key的列名
     * @param   rowLimit   分页信息，属性offset 记录中的偏移量，属性limit 返回记录数
     * @return
     */
    public  <K, V> Map<K, V> selectMap(String sqlId, Object parameter, String mapKey, RowLimit rowLimit);

    /**
     *
     *  执行SQL查询，查询结果的第一条记录，存入对象返回，对象类型由sqlId对应的配置信息决定
     * @param sqlId  sql对应的id值
     * @return
     */
    public  <T> T  selectOne(String sqlId);
    /**
     * 执行SQL查询，查询结果的第一条记录，存入对象返回，对象类型由sqlId对应的配置信息决定
     * @param sqlId  sql对应的id值
     * @param   parameter  参数对象
     * @return
     */
    public  <T> T  selectOne(String sqlId, Object parameter);


    /**
     * 执行sql更新数据,
     * @param sqlId sql对应的id值
     * @return 更新的记录数
     */

    public  int update(String sqlId);
    /**
     * 执行sql更新数据,
     * @param sqlId sql对应的id值
     * @param   parameter  参数对象
     * @return 更新的记录数
     */
    public  int  update(String sqlId, Object parameter);
    /**
     * 执行sql批量更新数据,
     * @param sqlId sql对应的id值
     * @param   parameterIterator  参数迭代器
     * @return 更新的记录数
     */
    public    void  batchUpdate(String sqlId, Iterator<Object> parameterIterator);
    /**
     * 执行sql批量更新数据,
     * @param sqlId sql对应的id值
     * @param   parameterIterator  参数迭代器
     * @param flushBatchSize      每执行多少次参数更新时，刷新更新数据到数据库
     * @return 更新的记录数
     */
    public   void  batchUpdate(String sqlId, Iterator<Object> parameterIterator,int flushBatchSize);
}
