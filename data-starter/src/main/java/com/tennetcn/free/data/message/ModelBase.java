package com.tennetcn.free.data.message;



import javax.persistence.Transient;

import com.tennetcn.free.data.enums.ModelStatus;

/** 
 * @author      chenghuan
 * @email       79763939@qq.com
 * @comment 
 */

public class ModelBase {
	public ModelBase(){
		this.versionNum=System.currentTimeMillis()+"-"+System.nanoTime();
		this.modelStatus=ModelStatus.none;
	}
	
	@Transient
	private ModelStatus modelStatus;
	
	@Transient
	private String versionNum;

	public ModelStatus getModelStatus() {
		return modelStatus;
	}

	public void setModelStatus(ModelStatus modelStatus) {
		this.modelStatus = modelStatus;
	}

	public String getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(String versionNum) {
		this.versionNum = versionNum;
	}

}
