package cn.ggdo.system.function.common;

import java.io.Serializable;

/**
 * extjs 单个文件上传结果
 */
public class JsonResult4UploadBean extends JsonResultBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Object item;

	public JsonResult4UploadBean(final boolean suc, final Object obj) {
		super(suc);
		this.item = obj;
	}

	public JsonResult4UploadBean(final Object obj) {
		super(JsonResult4UploadBean.SUCCESS);
		this.item = obj;
	}

	public Object getItem() {
		return item;
	}
}
