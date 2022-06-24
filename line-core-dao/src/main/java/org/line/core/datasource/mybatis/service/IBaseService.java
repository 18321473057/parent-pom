package org.line.core.datasource.mybatis.service;


import org.line.core.datasource.mybatis.service.delete.IDeleteService;
import org.line.core.datasource.mybatis.service.insert.IInsertService;
import org.line.core.datasource.mybatis.service.select.ISelectService;
import org.line.core.datasource.mybatis.service.update.IUpdateService;


public interface IBaseService<T> extends IDeleteService<T>, IInsertService<T>, IUpdateService<T>, ISelectService<T> {


}
