package com.tennetcn.free.data.message;

/** 
 * @author  chenghuan-home
 * @email   79763939@qq.com
 * @comment dao操作相关的异常
 */
public class DaoBaseException extends Exception {

	private static final long serialVersionUID = 7818375828146090358L;
	
	public DaoBaseException(){
		super("operate database exception in dao.base");
	}
	
	public DaoBaseException(String paramString) {
		super(paramString);
	}

	public DaoBaseException(String paramString, Throwable paramThrowable) {
		super(paramString, paramThrowable);
	}

	public DaoBaseException(Throwable paramThrowable) {
		super(paramThrowable);
	}

	protected DaoBaseException(String paramString, Throwable paramThrowable,
			boolean paramBoolean1, boolean paramBoolean2) {
		super(paramString, paramThrowable, paramBoolean1, paramBoolean2);
	}
}
