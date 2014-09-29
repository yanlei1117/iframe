package com.asiainfo.dbx.ln.business.service;

import java.util.List;

/**
 *
 * 数据维表service
 *
 * Created by yanlei on 2014/8/13.
 */
public interface DimService {
    /**
     * 获取维表列表
     * @param dimId
     * @return
     * @throws Exception
     */
    public List<DimData> getDimList(String dimId)  throws Exception;
    /**
     * 根据 parentId,获取维表列表,一般用于关联维表操作
     * @param dimId
     * @param parent
     * @return
     * @throws Exception
     */
    public List<DimData> getDimListByParentId(String dimId, String parent)  throws Exception;

    public List<DimData> getDimListByParentSpell(String dimId, String parent, String spell) throws Exception ;
    /**
     * 根据拼音码查找维表列表，一般用于维表量特别大，不利于用户选择，用户可录入拼音码方式查询维表值
     * @param dimId
     * @param likeValue
     * @return
     * @throws Exception
     */
    public List<DimData> getDimListBySpell(String dimId, String likeValue)  throws Exception;

    public List<DimData> getDimListByLabel(String dimId, String name)throws Exception;
    /**
     * 将维表值转换为汉字。(翻译)
     * @param dimId
     * @param dimValue
     * @return
     * @throws Exception
     */
    public String getDimLabel(String dimId, String dimValue) throws Exception;
    /**
     *
     * @param dimId
     * @param dimValue
     * @return
     * @throws Exception
     */
    public DimData getDimData(String dimId, String dimValue) throws Exception;

/**
 * 注册维表
 * @param dimId
 * @param dimSql 查询维表的SQL 格式：select  id,label,parent_id,status,spell from table，注意列类型均为字符串
 *     id ==维表值
 *     label ==维表中文名
 *     parent_id==父ID
 *     status==有效标识  0 无效 1有效。 有效的才能显示在选择列表中，0和1都可用于翻译
 *             作用：如某维值不再使用了，标记为0，在用户选择列表中不再显示，但历史数据仍可翻译出来。
 *     spell==拼音码  用于用户录入查询。
 * @param daoName
 * @throws Exception
 */
	public void registerDim(String dimId, String dimSql, String daoName, String dbType)  throws Exception;
/**
 * 设定最大缓存行数
 *
 * 如设为1000,则大于1000行的维表不缓存，小于等于1000的维表缓存。
 * 用于防止数据量大造成内存溢出。
 *
 * 获取维表列表，缓存过的从缓存中查询，未缓存的按SQL查询。
 *
 * @param cacheMaxRowNum
 */
public void setCacheMaxRowNum(int cacheMaxRowNum) ;
    /**
     * 刷新维表，用于维表变动，重新载入。
     * @throws Exception
     */
    public void refresh() throws Exception;
}
