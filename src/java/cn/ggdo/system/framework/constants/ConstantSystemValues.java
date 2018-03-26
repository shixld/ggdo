package cn.ggdo.system.framework.constants;

/**
 * 类名：ConstantStateValue 
 * 版本：1.0.0
 * 用途说明：系统常量
 * 业务区分(常量)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public interface ConstantSystemValues {
	// 默认编码
	String DEFAULT_ENCODING = "UTF-8";
	// 初始密码
	String INIT_PASS = "000000";
	// 当前权限系统
	String CURRENT_SEC_SYSTEM = "currentSecSystem";
	// 当前权限系统code
	String CURRENT_SEC_SYSTEMCODE = "currentSecSystemCode";
	// 当前用户
	//String CURRENT_USER = "currentUser";
	String CURRENT_USER = "ACLLOGINUSERS";
	// 当前用户id
	String CURRENT_USERID = "currentUserId";
	// 当前用户名
	String CURRENT_USERNAME = "currentUsername";
	// 当前用户真实姓名
	String CURRENT_USEREALRNAME = "currentUserRealname";
	// 当前用户部门id
	String CURRENT_USERDEPTID = "currentUserDeptId";
	// 当前用户职务id
	String CURRENT_USERGROUPID = "currentUserGroupId";
	// 当前用户部门职务id
	String CURRENT_USERDEPTGROUPID = "currentUserDeptGroupId";
	// 登录出错次数
	String LOGIN_ERRORCOUNT = "loginErrorCount";
	// 锁定
	String LOGIN_LOCK = "lock";
	// 错误信息
	String ERROR_MESSAGE = "errorMessage";
	// admin
	String ADMIN_USER = "administrator";
	// userList
	String USER_LIST_INFO = "redisUserInfo";
}
