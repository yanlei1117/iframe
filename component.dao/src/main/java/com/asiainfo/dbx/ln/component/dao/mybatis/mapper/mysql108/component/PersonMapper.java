package com.asiainfo.dbx.ln.component.dao.mybatis.mapper.mysql108.component;

import com.asiainfo.dbx.ln.component.dao.mybatis.MyBatisExample;
import com.asiainfo.dbx.ln.component.dao.mybatis.MyBatisMapper;
import com.asiainfo.dbx.ln.component.dao.mybatis.mapper.mysql108.component.Person;
import com.asiainfo.dbx.ln.component.dao.mybatis.mapper.mysql108.component.PersonExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PersonMapper extends MyBatisMapper<Person, MyBatisExample> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table person
     *
     * @mbggenerated
     */
    float countByExample(MyBatisExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table person
     *
     * @mbggenerated
     */
    int deleteByExample(MyBatisExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table person
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table person
     *
     * @mbggenerated
     */
    int insert(Person record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table person
     *
     * @mbggenerated
     */
    int insertSelective(Person record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table person
     *
     * @mbggenerated
     */
    List<Person> selectByExample(MyBatisExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table person
     *
     * @mbggenerated
     */
    Person selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table person
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") Person record, @Param("example") MyBatisExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table person
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") Person record, @Param("example") MyBatisExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table person
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Person record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table person
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Person record);
}