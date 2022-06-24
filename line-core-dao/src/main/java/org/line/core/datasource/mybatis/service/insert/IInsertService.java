package org.line.core.datasource.mybatis.service.insert;


public interface IInsertService<T> {

    /**
     * 保存记录
     *
     * @param record
     * @return
     */
    int save(T record);

    /**
     * 保存记录
     * 属性为null的不会保存使用数据库默认值
     *
     * @param record
     * @return
     */
    int saveSelective(T record);
}
