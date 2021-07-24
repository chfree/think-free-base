package com.cditer.free.core.message.data;



import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.cditer.free.core.enums.ModelStatus;
import lombok.Data;

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

}
