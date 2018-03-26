package cn.ggdo.system.function.common;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

/**
 * 1、Extjs 表单提交返回SUCCESS 为true执行success 为false执行failure ， 2、Ext.Ajax返回false仍然执行success 需要判断返回结果
 */
public class JsonResultBean implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final boolean SUCCESS = true;
	public static final boolean FAULT = false;
	private boolean success;
	private String msg;
	private Object obj;

	public JsonResultBean() {
		this.success = true;
	}

	public JsonResultBean(final String message) {
		this.success = true;
		this.msg = message;
	}

	public JsonResultBean(final Object object) {
		this.success = true;
		this.obj = object;
	}

	public JsonResultBean(final boolean suc) {
		this.success = suc;
	}

	public JsonResultBean(final boolean suc, final String message) {
		this.success = suc;
		this.msg = message;
	}

	/**
	 * 返回异常信息
	 */
	public JsonResultBean(final Throwable exceptionMessage) {
		exceptionMessage.printStackTrace(new PrintWriter(new StringWriter()));
		// 异常情况
		this.success = false;
		// this.exceptionMessage = sw.toString(); 太详细了
		this.msg = exceptionMessage.getMessage();
	}

	public boolean isSuccess() {
		return success;
	}

	public String getMsg() {
		return msg;
	}

	public Object getObj() {
		return obj;
	}
}
