package cn.ggdo.system.framework.constants;

/**
 * 类名：ConstantWFProcessState 
 * 版本：1.0.0
 * 用途说明：工作流流程状态
 * 业务区分(常量)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public interface ConstantWFProcessState {
	String START = "start"; // 开始
	String LIVE = "live"; // 进行中
	String END = "end"; // 结束
	String ACTIVE = "active"; // 激活
	String SUSPEND = "suspend"; // 挂起
}
