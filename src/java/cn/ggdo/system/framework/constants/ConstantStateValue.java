package cn.ggdo.system.framework.constants;

/**
 * 类名：ConstantStateValue 
 * 版本：1.0.0
 * 用途说明：状态
 * 业务区分(常量)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public interface ConstantStateValue {
	String STATE_ALL = "100"; // 全部
	int STATE_ALL_INT = 100; // 全部
	String STATE_ENABLED = "1"; // 启用
	int STATE_ENABLED_INT = 1; // 启用
	String STATE_DISABLED = "2"; // 停用
	int STATE_DISABLED_INT = 2; // 停用

	String STATE_DELETED = "10"; // 删除
	int STATE_DELETED_INT = 10; // 删除

	int QUA_NO = 1;
	int QUA_ING = 2;
	int QUA_OK = 3;
}
