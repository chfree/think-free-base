package com.tennetcn.free.data.dao.base.mapper;

import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.MapperException;
import tk.mybatis.mapper.code.Style;
import tk.mybatis.mapper.entity.Config;
import tk.mybatis.mapper.entity.EntityTable;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.resolve.DefaultEntityResolve;
import tk.mybatis.mapper.mapperhelper.resolve.EntityResolve;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenghuan
 * @email 79763939@qq.com
 * @createtime 2016年5月10日 下午10:29:04
 * @comment
 */
public class SqlMapper {

	private final MSUtils msUtils;
	private final SqlSession sqlSession;

	/**
	 * 构造方法，默认缓存MappedStatement
	 *
	 * @param sqlSession
	 */
	public SqlMapper(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
		this.msUtils = new MSUtils(sqlSession.getConfiguration());
	}

	/**
	 * 获取List中最多只有一个的数据
	 *
	 * @param list
	 *            List结果
	 * @param <T>
	 *            泛型类型
	 * @return
	 */
	private <T> T getOne(List<T> list) {
		if (list.size() == 1) {
			return list.get(0);
		} else if (list.size() > 1) {
			throw new TooManyResultsException(
					"Expected one result (or null) to be returned by selectOne(), but found: "
							+ list.size());
		} else {
			return null;
		}
	}

	/**
	 * 查询返回一个结果，多个结果时抛出异常
	 *
	 * @param sql
	 *            执行的sql
	 * @return
	 */
	public Map<String, Object> selectOne(String sql) {
		List<Map<String, Object>> list = selectList(sql);
		return getOne(list);
	}

	/**
	 * 查询返回一个结果，多个结果时抛出异常
	 *
	 * @param sql
	 *            执行的sql
	 * @param value
	 *            参数
	 * @return
	 */
	public Map<String, Object> selectOne(String sql, Object value) {
		List<Map<String, Object>> list = selectList(sql, value);
		return getOne(list);
	}
	
	public int queryCount(String sql, Object value) {
		List<Map<String, Object>> list = selectList(sql, value);
		Map<String,Object> map= getOne(list);
		return Integer.parseInt(map.get(map.keySet().toArray()[0]).toString());
	}

	/**
	 * 查询返回一个结果，多个结果时抛出异常
	 *
	 * @param sql
	 *            执行的sql
	 * @param resultType
	 *            返回的结果类型
	 * @param <T>
	 *            泛型类型
	 * @return
	 */
	public <T> T selectOne(String sql, Class<T> resultType) {
		List<T> list = selectList(sql, resultType);
		return getOne(list);
	}

	/**
	 * 查询返回一个结果，多个结果时抛出异常
	 *
	 * @param sql
	 *            执行的sql
	 * @param value
	 *            参数
	 * @param resultType
	 *            返回的结果类型
	 * @param <T>
	 *            泛型类型
	 * @return
	 */
	public <T> T selectOne(String sql, Object value, Class<T> resultType) {
		List<T> list = selectList(sql, value, resultType);
		return getOne(list);
	}

	/**
	 * 查询返回List<Map<String, Object>>
	 *
	 * @param sql
	 *            执行的sql
	 * @return
	 */
	public List<Map<String, Object>> selectList(String sql) {
		String msId = msUtils.select(sql);
		return sqlSession.selectList(msId);
	}

	/**
	 * 查询返回List<Map<String, Object>>
	 *
	 * @param sql
	 *            执行的sql
	 * @param value
	 *            参数
	 * @return
	 */
	public List<Map<String, Object>> selectList(String sql, Object value) {
		Class<?> parameterType = value != null ? value.getClass() : null;
		String msId = msUtils.selectDynamic(sql, parameterType);
		return sqlSession.selectList(msId, value);
	}
	
	/**
	 * 返回分页的 List<Map<String, Object>>
	 * @param sql
	 * @param value
	 * @param rowBounds
	 * @return
	 */
	public List<Map<String, Object>> selectListEx(String sql, Object value,RowBounds rowBounds) {
		Class<?> parameterType = value != null ? value.getClass() : null;
		String msId = msUtils.selectDynamic(sql, parameterType);
		return sqlSession.selectList(msId, value,rowBounds);
	}
	
	public <T> List<T> selectList(String sql,Object value,RowBounds rowBounds) {
		Class<?> parameterType = value != null ? value.getClass() : null;
		String msId = msUtils.selectDynamic(sql, parameterType);
		return sqlSession.selectList(msId,value,rowBounds);
	}

	/**
	 * 查询返回指定的结果类型
	 *
	 * @param sql
	 *            执行的sql
	 * @param resultType
	 *            返回的结果类型
	 * @param <T>
	 *            泛型类型
	 * @return
	 */
	public <T> List<T> selectList(String sql, Class<T> resultType) {
		String msId;
		if (resultType == null) {
			msId = msUtils.select(sql);
		} else {
			msId = msUtils.select(sql, resultType);
		}
		return sqlSession.selectList(msId);
	}
	
	public <T> List<T> selectList(String sql,RowBounds rowBounds, Class<T> resultType) {
		String msId;
		if (resultType == null) {
			msId = msUtils.select(sql);
		} else {
			msId = msUtils.select(sql, resultType);
		}
		return sqlSession.selectList(msId,null,rowBounds);
	}
	

	/**
	 * 查询返回指定的结果类型
	 *
	 * @param sql
	 *            执行的sql
	 * @param value
	 *            参数
	 * @param resultType
	 *            返回的结果类型
	 * @param <T>
	 *            泛型类型
	 * @return
	 */
	public <T> List<T> selectList(String sql, Object value, Class<T> resultType) {
		String msId;
		Class<?> parameterType = value != null ? value.getClass() : null;
		if (resultType == null) {
			msId = msUtils.selectDynamic(sql, parameterType);
		} else {
			msId = msUtils.selectDynamic(sql, parameterType, resultType);
		}
		return sqlSession.selectList(msId, value);
	}
	
	
	
	public <T> List<T> selectList(String sql, Object value,RowBounds rowBounds, Class<T> resultType) {
		String msId;
		Class<?> parameterType = value != null ? value.getClass() : null;
		if (resultType == null) {
			msId = msUtils.selectDynamic(sql, parameterType);
		} else {
			msId = msUtils.selectDynamic(sql, parameterType, resultType);
		}
		return sqlSession.selectList(msId, value,rowBounds);
	}

	/**
	 * 插入数据
	 *
	 * @param sql
	 *            执行的sql
	 * @return
	 */
	public int insert(String sql) {
		String msId = msUtils.insert(sql);
		return sqlSession.insert(msId);
	}

	/**
	 * 插入数据
	 *
	 * @param sql
	 *            执行的sql
	 * @param value
	 *            参数
	 * @return
	 */
	public int insert(String sql, Object value) {
		Class<?> parameterType = value != null ? value.getClass() : null;
		String msId = msUtils.insertDynamic(sql, parameterType);
		return sqlSession.insert(msId, value);
	}

	/**
	 * 更新数据
	 *
	 * @param sql
	 *            执行的sql
	 * @return
	 */
	public int update(String sql) {
		String msId = msUtils.update(sql);
		return sqlSession.update(msId);
	}

	/**
	 * 更新数据
	 *
	 * @param sql
	 *            执行的sql
	 * @param value
	 *            参数
	 * @return
	 */
	public int update(String sql, Object value) {
		Class<?> parameterType = value != null ? value.getClass() : null;
		String msId = msUtils.updateDynamic(sql, parameterType);
		return sqlSession.update(msId, value);
	}

	/**
	 * 删除数据
	 *
	 * @param sql
	 *            执行的sql
	 * @return
	 */
	public int delete(String sql) {
		String msId = msUtils.delete(sql);
		return sqlSession.delete(msId);
	}

	/**
	 * 删除数据
	 *
	 * @param sql
	 *            执行的sql
	 * @param value
	 *            参数
	 * @return
	 */
	public int delete(String sql, Object value) {
		Class<?> parameterType = value != null ? value.getClass() : null;
		String msId = msUtils.deleteDynamic(sql, parameterType);
		return sqlSession.delete(msId, value);
	}

	private static final Map<Class<?>, EntityTable> entityTableMap = new ConcurrentHashMap<Class<?>, EntityTable>();

	private class MSUtils {
		private Configuration configuration;
		private LanguageDriver languageDriver;

		private MSUtils(Configuration configuration) {
			this.configuration = configuration;
			languageDriver = configuration.getDefaultScriptingLanguageInstance();
		}

		/**
		 * 创建MSID
		 *
		 * @param sql
		 *            执行的sql
		 * @param sql
		 *            执行的sqlCommandType
		 * @return
		 */
		private String newMsId(String sql, SqlCommandType sqlCommandType) {
			StringBuilder msIdBuilder = new StringBuilder(
					sqlCommandType.toString());
			msIdBuilder.append(".").append(sql.hashCode());
			return msIdBuilder.toString();
		}

		/**
		 * 是否已经存在该ID
		 *
		 * @param msId
		 * @return
		 */
		private boolean hasMappedStatement(String msId) {
			return configuration.hasStatement(msId, false);
		}

		final EntityResolve resolve = new DefaultEntityResolve();

		@Autowired
		MapperHelper mapperHelper;

		/**
		 * 创建一个查询的MS
		 *
		 * @param msId
		 * @param sqlSource
		 *            执行的sqlSource
		 * @param resultType
		 *            返回的结果类型
		 */
		private void newSelectMappedStatement(String msId, SqlSource sqlSource, final Class<?> resultType) {
			List<ResultMap> resultMaps = new ArrayList();
			try{
				ResultMap rm = getEneityTable(resultType).getResultMap(configuration);
				if(rm==null){
                    resultMaps.add(new ResultMap.Builder(configuration,"defaultResultMap", resultType,new ArrayList<ResultMapping>(0)).build());
                }else{
                    resultMaps.add(rm);
                }
			}catch (MapperException ex){
				resultMaps.add(new ResultMap.Builder(configuration,"defaultResultMap", resultType,new ArrayList<ResultMapping>(0)).build());
			}
			MappedStatement ms = new MappedStatement.Builder(configuration,msId, sqlSource, SqlCommandType.SELECT).resultMaps(resultMaps).build();

			// 缓存
			configuration.addMappedStatement(ms);
		}

		private EntityTable getEneityTable(Class<?> resultType){
			EntityTable entityTable = entityTableMap.get(resultType);
			if(entityTable==null){
				entityTable = resolve.resolveEntity(resultType,getConfig());
				entityTableMap.put(resultType,entityTable);
			}
			return entityTable;
		}

		private Config getConfig(){
			//特殊配置
			Config config = new Config();
			config.setStyle(Style.normal);
			// 主键自增回写方法,默认值MYSQL,详细说明请看文档
			config.setIDENTITY("HSQLDB");
			// 支持方法上的注解
			// 3.3.1版本增加
			config.setEnableMethodAnnotation(true);
			config.setNotEmpty(true);
			//校验Example中的类型是否一致
			config.setCheckExampleEntityClass(true);
			//启用简单类型
			config.setUseSimpleType(true);
			config.setEnumAsSimpleType(true);
			// 序列的获取规则,使用{num}格式化参数，默认值为{0}.nextval，针对Oracle
			// 可选参数一共3个，对应0,1,2,分别为SequenceName，ColumnName, PropertyName
			//config.setSeqFormat("NEXT VALUE FOR {0}");
			// 设置全局的catalog,默认为空，如果设置了值，操作表时的sql会是catalog.tablename
			//config.setCatalog("");
			// 设置全局的schema,默认为空，如果设置了值，操作表时的sql会是schema.tablename
			// 如果同时设置了catalog,优先使用catalog.tablename
			//config.setSchema("");
			// 主键自增回写方法执行顺序,默认AFTER,可选值为(BEFORE|AFTER)
			//config.setOrder("AFTER");
			//自动关键字 - mysql
			//config.setWrapKeyword("`{0}`");
			//使用 javaType
			config.setUseJavaType(true);
			return config;
		}

		/**
		 * 创建一个简单的MS
		 *
		 * @param msId
		 * @param sqlSource
		 *            执行的sqlSource
		 * @param sqlCommandType
		 *            执行的sqlCommandType
		 */
		private void newUpdateMappedStatement(String msId, SqlSource sqlSource,SqlCommandType sqlCommandType) {
			List<ResultMap> resultMapList=new ArrayList<ResultMap>();
			resultMapList.add(new ResultMap.Builder(configuration,"defaultResultMap", int.class,new ArrayList<ResultMapping>(0)).build());
			MappedStatement ms = new MappedStatement.Builder(configuration,msId, sqlSource, sqlCommandType).resultMaps(resultMapList).build();
			// 缓存
			configuration.addMappedStatement(ms);
		}

		private String select(String sql) {
			String msId = newMsId(sql, SqlCommandType.SELECT);
			if (hasMappedStatement(msId)) {
				return msId;
			}
			StaticSqlSource sqlSource = new StaticSqlSource(configuration, sql);
			newSelectMappedStatement(msId, sqlSource, Map.class);
			return msId;
		}

		private String selectDynamic(String sql, Class<?> parameterType) {
			String msId = newMsId(sql + parameterType, SqlCommandType.SELECT);
			if (hasMappedStatement(msId)) {
				return msId;
			}
			SqlSource sqlSource = languageDriver.createSqlSource(configuration,
					sql, parameterType);
			newSelectMappedStatement(msId, sqlSource, Map.class);
			return msId;
		}

		private String select(String sql, Class<?> resultType) {
			String msId = newMsId(resultType + sql, SqlCommandType.SELECT);
			if (hasMappedStatement(msId)) {
				return msId;
			}
			StaticSqlSource sqlSource = new StaticSqlSource(configuration, sql);
			newSelectMappedStatement(msId, sqlSource, resultType);
			return msId;
		}

		private String selectDynamic(String sql, Class<?> parameterType,
				Class<?> resultType) {
			String msId = newMsId(resultType + sql + parameterType,
					SqlCommandType.SELECT);
			if (hasMappedStatement(msId)) {
				return msId;
			}
			SqlSource sqlSource = languageDriver.createSqlSource(configuration,
					sql, parameterType);
			newSelectMappedStatement(msId, sqlSource, resultType);
			return msId;
		}

		private String insert(String sql) {
			String msId = newMsId(sql, SqlCommandType.INSERT);
			if (hasMappedStatement(msId)) {
				return msId;
			}
			StaticSqlSource sqlSource = new StaticSqlSource(configuration, sql);
			newUpdateMappedStatement(msId, sqlSource, SqlCommandType.INSERT);
			return msId;
		}

		private String insertDynamic(String sql, Class<?> parameterType) {
			String msId = newMsId(sql + parameterType, SqlCommandType.INSERT);
			if (hasMappedStatement(msId)) {
				return msId;
			}
			SqlSource sqlSource = languageDriver.createSqlSource(configuration,
					sql, parameterType);
			newUpdateMappedStatement(msId, sqlSource, SqlCommandType.INSERT);
			return msId;
		}

		private String update(String sql) {
			String msId = newMsId(sql, SqlCommandType.UPDATE);
			if (hasMappedStatement(msId)) {
				return msId;
			}
			StaticSqlSource sqlSource = new StaticSqlSource(configuration, sql);
			newUpdateMappedStatement(msId, sqlSource, SqlCommandType.UPDATE);
			return msId;
		}

		private String updateDynamic(String sql, Class<?> parameterType) {
			String msId = newMsId(sql + parameterType, SqlCommandType.UPDATE);
			if (hasMappedStatement(msId)) {
				return msId;
			}
			SqlSource sqlSource = languageDriver.createSqlSource(configuration,
					sql, parameterType);
			newUpdateMappedStatement(msId, sqlSource, SqlCommandType.UPDATE);
			return msId;
		}

		private String delete(String sql) {
			String msId = newMsId(sql, SqlCommandType.DELETE);
			if (hasMappedStatement(msId)) {
				return msId;
			}
			StaticSqlSource sqlSource = new StaticSqlSource(configuration, sql);
			newUpdateMappedStatement(msId, sqlSource, SqlCommandType.DELETE);
			return msId;
		}

		private String deleteDynamic(String sql, Class<?> parameterType) {
			String msId = newMsId(sql + parameterType, SqlCommandType.DELETE);
			if (hasMappedStatement(msId)) {
				return msId;
			}
			SqlSource sqlSource = languageDriver.createSqlSource(configuration,
					sql, parameterType);
			newUpdateMappedStatement(msId, sqlSource, SqlCommandType.DELETE);
			return msId;
		}
	}

}
