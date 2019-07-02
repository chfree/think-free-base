package com.tennetcn.free.data.message;

/** 
 * @author  chenghuan-home
 * @email   79763939@qq.com
 * @comment dao操作相关的异常
 */
public class DaoBaseRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 7818375828146090358L;
	
	public DaoBaseRuntimeException(){
		super("operate database exception in dao.base");
	}
	
	public DaoBaseRuntimeException(String paramString) {
		super(paramString);
	}

	public DaoBaseRuntimeException(String paramString, Throwable paramThrowable) {
		super(paramString, paramThrowable);
	}

	public DaoBaseRuntimeException(Throwable paramThrowable) {
		super(paramThrowable);
	}

	protected DaoBaseRuntimeException(String paramString, Throwable paramThrowable,
			boolean paramBoolean1, boolean paramBoolean2) {
		super(paramString, paramThrowable, paramBoolean1, paramBoolean2);
	}
}
