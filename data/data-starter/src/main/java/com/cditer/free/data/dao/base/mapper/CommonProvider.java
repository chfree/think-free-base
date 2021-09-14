package com.cditer.free.data.dao.base.mapper;

import org.apache.ibatis.mapping.MappedStatement;

import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

/** 
 * @author      chenghuan
 * @email       79763939@qq.com
 * @createtime  2016年5月11日 上午9:03:08
 * @comment 
 */
public class CommonProvider extends MapperTemplate{

	public CommonProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
		super(mapperClass, mapperHelper);
	}

	public String countForMapper(MappedStatement ms) {
    	final Class<?> entityClass = getEntityClass(ms);
        //开始拼sql
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectCount(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
            
        sql.append(SqlHelper.whereAllIfColumns(entityClass, false));
        return sql.toString();
    }

    public String countByExampleForMapper(MappedStatement ms) {
    	final Class<?> entityClass = getEntityClass(ms);
        //开始拼sql
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectCount(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
            
        sql.append(SqlHelper.exampleWhereClause());
        return sql.toString();
    }

    public String deleteByExampleForMapper(MappedStatement ms) {        
        final Class<?> entityClass = getEntityClass(ms);
        //开始拼sql
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.deleteFromTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.exampleWhereClause());
        return sql.toString();
    }

    public String selectByExampleForMapper(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        //开始拼sql
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectAllColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.exampleWhereClause());
        sql.append(SqlHelper.orderByDefault(entityClass));
        return sql.toString();
    }
}
