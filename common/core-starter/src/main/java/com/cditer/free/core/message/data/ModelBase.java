package com.cditer.free.core.message.data;



import javax.persistence.Id;
import javax.persistence.Transient;

import cn.hutool.core.bean.BeanUtil;
import com.cditer.free.core.util.PkIdUtils;
import com.cditer.free.core.util.ReflectUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.cditer.free.core.enums.ModelStatus;
import lombok.Data;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

/** 
 * @author      chenghuan
 * @email       79763939@qq.com
 * @comment 
 */

@Data
public class ModelBase implements IDbModel{
	public ModelBase(){
		this.versionNum=System.currentTimeMillis()+"-"+System.nanoTime();
		this.modelStatus=ModelStatus.none;
	}
	
	@Transient
	@JsonIgnore
	private ModelStatus modelStatus;
	
	@Transient
	@JsonIgnore
	private String versionNum;

	public void autoPkIdAndStatus(){
		List<Field> allFieldList = ReflectUtils.getAllFieldList(this.getClass(), ModelBase.class);

		if(CollectionUtils.isEmpty(allFieldList)){
			return;
		}
		Optional<Field> first = allFieldList.stream().filter(item -> item.getAnnotation(Id.class) != null).findFirst();
		if(!first.isPresent()){
			return;
		}
		String fieldName = first.get().getName();
		Object fieldValue = BeanUtil.getFieldValue(this, fieldName);
		if(fieldValue==null|| !StringUtils.hasText(fieldValue.toString())){
			BeanUtil.setFieldValue(this, fieldName, PkIdUtils.getId());
			this.setModelStatus(ModelStatus.add);
		}else{
			this.setModelStatus(ModelStatus.update);
		}
	}

}
