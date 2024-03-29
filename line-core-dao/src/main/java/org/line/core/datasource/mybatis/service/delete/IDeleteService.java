package org.line.core.datasource.mybatis.service.delete;


public interface IDeleteService<T> {
    /**
     * 根据主键删除数据
     *
     * @param key 主键
     * @return
     */
    int deleteByPrimaryKey(Object key);

    /**
     * 根据实体属性作为条件进行删除，查询条件使用等号
     *
     * @param record
     * @return
     */
    int delete(T record);

    /**
     * 根据Example条件删除数据
     *
     * @param example
     * @return
     */
    int deleteByExample(Object example);
}
