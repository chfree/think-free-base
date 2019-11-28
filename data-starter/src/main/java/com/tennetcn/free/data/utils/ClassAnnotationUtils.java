package com.tennetcn.free.data.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.springframework.util.StringUtils;

import com.tennetcn.free.core.message.data.OrderByEnum;
import com.tennetcn.free.data.message.OrderInfo;

/**
 * @author  chenghuan-home
 * @email   79763939@qq.com
 * @comment 
 */

public class ClassAnnotationUtils {
	static{
		entityNames=new HashMap<String,String>();
		tableNames=new HashMap<String, String>();
		primaryKeys=new HashMap<String, String>();
		orderByKeys=new HashMap<String, List<OrderInfo>>();
	}
	private static Map<String,String> entityNames;
	private static Map<String,String> tableNames;
	private static Map<String,String> primaryKeys;
	private static Map<String,List<OrderInfo>> orderByKeys;
	
	public static <E> String getClassName(Class<E> clzz){
		String classAllName = clzz.toString();
		return classAllName.substring(classAllName.lastIndexOf(".") + 1,
				classAllName.length());
	}

	public static <E> String getModelName(Class<E> eClass){
		String entityName= getEntityName(eClass);
		
		if(entityName==null||entityName.isEmpty()){
			entityName= getClassName(eClass);
		}
		return entityName;
	}
	
	public static <E> String getEntityName(Class<E> eClass){
		String entityName=entityNames.get(eClass.getName());
		if(!StringUtils.isEmpty(entityName)){return entityName;}
		
		Entity entity=eClass.getAnnotation(Entity.class);
		if(entity==null){return "";}
		
		entityName=entity.name();
		entityNames.put(eClass.getName(), entityName);
		
		return entityName;
	}
	
	public static <E> String getTableName(Class<E> eClass){
		String tableName=tableNames.get(eClass.getName());
		if(!StringUtils.isEmpty(tableName)){return tableName;}
		
		Table table=eClass.getAnnotation(Table.class);
		if(table==null){return "";}
		
		tableName= table.name();
		tableNames.put(eClass.getName(), tableName);
		
		return tableName;
	}
	
	public static <E> String getPrimaryKey(Class<E> eClass){
		String primaryKey=primaryKeys.get(eClass.getName());
		if(!StringUtils.isEmpty(primaryKey)){return primaryKey;}
		
		primaryKey=getFieldId(eClass);
		if(StringUtils.isEmpty(primaryKey)){
			primaryKey=getMethodId(eClass);
		}
		if(!StringUtils.isEmpty(primaryKey)){
			primaryKeys.put(eClass.getName(), primaryKey);
		}
		return primaryKey;
	}
	
	
	public static <E> List<OrderInfo> getOrderInfoList(Class<E> eClass){
		List<OrderInfo> orderInfoList=orderByKeys.get(eClass.getName());
		if(orderInfoList!=null){
			return orderInfoList;
		}
		
		orderInfoList=new ArrayList<OrderInfo>();
		
		List<OrderInfo> fieldOrderInfo=getFieldOrderBy(eClass);
		if(fieldOrderInfo!=null&&fieldOrderInfo.size()>0){
			orderInfoList.addAll(fieldOrderInfo);
		}
		
		List<OrderInfo> methodOrderInfo=getMethodOrderBy(eClass);
		if(methodOrderInfo!=null&&methodOrderInfo.size()>0){
			orderInfoList.addAll(methodOrderInfo);
		}
		orderByKeys.put(eClass.getName(), orderInfoList);
		
		return orderInfoList;
	}
	
	private static <E> String getMethodId(Class<E> eClass){
		Method[] methods= eClass.getMethods();
		for (Method method : methods) {
			if(method.getAnnotation(Id.class)!=null){
				String methodName= method.getName().substring(3);
				return methodName.substring(0,1).toLowerCase()+methodName.substring(1);
			}
		}
		return "";
	}
	
	private static <E> String getFieldId(Class<E> eClass){
		Field[] fields=eClass.getDeclaredFields();
	    for (Field field : fields) {
	    	if(field.getAnnotation(Id.class)!=null){
	    		return field.getName();
	    	}
		}
	    return "";
	}
	
	private static <E> List<OrderInfo> getMethodOrderBy(Class<E> eClass){
		List<OrderInfo> orderInfoList=new ArrayList<OrderInfo>();
		OrderInfo orderInfo=null;
		Method[] methods= eClass.getMethods();
		for (Method method : methods) {
			OrderBy orderBy= method.getAnnotation(OrderBy.class);
			if(orderBy!=null){
				String methodName= method.getName().substring(3);
				String fieldName= methodName.substring(0,1).toLowerCase()+methodName.substring(1);
				
				orderInfo=new OrderInfo(fieldName,StringUtils.isEmpty(orderBy.value())?OrderByEnum.ASC:orderBy.value());
				orderInfoList.add(orderInfo);
			}
		}
		return orderInfoList;
	}
	
	private static <E> List<OrderInfo> getFieldOrderBy(Class<E> eClass){
		List<OrderInfo> orderInfoList=new ArrayList<OrderInfo>();
		OrderInfo orderInfo=null;
		
		Field[] fields=eClass.getDeclaredFields();
	    for (Field field : fields) {
	    	OrderBy orderBy=field.getAnnotation(OrderBy.class);
	    	if(orderBy!=null){
	    		String fieldName= field.getName();
	    		orderInfo=new OrderInfo(fieldName,StringUtils.isEmpty(orderBy.value())?OrderByEnum.ASC:orderBy.value());
				orderInfoList.add(orderInfo);
	    	}
		}
	    return orderInfoList;
	}
}
