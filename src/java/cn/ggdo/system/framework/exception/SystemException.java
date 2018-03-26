package cn.ggdo.system.framework.exception;

/**
 * 类名：SpringSimpleMappingExceptionHandler 
 * 版本：1.0.0
 * 用途说明：系统异常
 * 业务区分(异常)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public class SystemException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SystemException() {
		super();
	}

	public SystemException(final String code) {
		super(code);
	}

	public SystemException(final Throwable cause) {
		super(cause);
	}

	public SystemException(final String code, final Throwable cause) {
		super(code, cause);
	}

	public String getMessage() {
		return ExceptionCode.getMsgByCode(super.getMessage());
	}
}
