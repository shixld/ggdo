package cn.ggdo.system.framework.helper;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.ggdo.system.framework.constants.ConstantSystemValues;

/**
 * 类名：SystemHelper 
 * 版本：1.0.0
 * 用途说明：当前登录用户相关信息
 * 业务区分(helper)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public class SystemHelper {
	private  HttpSession session = ((ServletRequestAttributes) RequestContextHolder
			.getRequestAttributes()).getRequest().getSession();

	/**
	 * 保存当前用户
	 * 
	 * @auther sunchanglin
	 * @date 2016-9-27 下午04:18:38
	 * @param sysUserinfo
	 * @param userType
	 *            0:系统管理员 1:普通职员 2:单位管理员
	 */
	public static void setCurrentUser(final Map<String, Object> sysUserinfo) {
		SystemHelper  system = new SystemHelper();
		// 保存当前用户到session中
		system.session.setAttribute(ConstantSystemValues.CURRENT_USER, sysUserinfo);
		// 保存
	};
	
	
	/**
	 * 保存当前系统信息
	 * 
	 * @auther sunchanglin
	 * @date 2016-9-27 下午04:18:38
	 * @param systemInfo
	 */
	public static void setCurrentSecSystem(final Map<String, Object> systemInfo) {
		SystemHelper  system = new SystemHelper();
		// 保存当前用户到session中
		system.session.setAttribute(ConstantSystemValues.CURRENT_SEC_SYSTEM, systemInfo);
		// 保存
	};
	
	
	

	/**
	 * 得到当前用户
	 * 
	 * @auther sunchanglin
	 * @date 2016-9-27 下午04:18:47
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getCurrentUser() {
		SystemHelper  system = new SystemHelper();
		return (Map<String, Object>) system.session
				.getAttribute(ConstantSystemValues.CURRENT_USER);
	}
	
	/**
	 * 得到当前权限系统
	 * 
	 * @auther sunchanglin
	 * @date 2016-9-27 下午04:18:47
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getCurrentSecSystem() {
		SystemHelper  system = new SystemHelper();
		return (Map<String, Object>) system.session.getAttribute(ConstantSystemValues.CURRENT_SEC_SYSTEM);
	}

	/**
	 * 得到当前用户id
	 * 
	 * @auther sunchanglin
	 * @date 2016-9-27 下午05:32:26
	 * @return
	 */
	public static long getCurrentUserId() {
		Long userId = 0L;
		if(getCurrentUser()!=null && !getCurrentUser().isEmpty()){
			if(null != getCurrentUser().get("userId")){
				userId = Long.parseLong(String.valueOf(getCurrentUser().get("userId")));
			}
		}
		return userId;
	}
	
	/**
	 * 得到当前用户ItCode/登录名/用户名
	 * 
	 * @auther sunchanglin
	 * @date 2016-9-27 下午05:32:26
	 * @return
	 */
	public static String getCurrentUserItCode() {
		String userItCode ="";
		if(getCurrentUser()!=null && !getCurrentUser().isEmpty()){
			if(null != getCurrentUser().get("userItcode")){
				userItCode = (String) getCurrentUser().get("userItcode");
			}
		}
		return userItCode;
	}

	/**
	 * 得到当前用户名称
	 * 
	 * @auther sunchanglin
	 * @date 2016-9-27 下午05:32:52
	 * @return
	 */
	public static String getCurrentUserName() {
		String userName = "";
		if(getCurrentUser()!=null && !getCurrentUser().isEmpty()){
			if(null!=getCurrentUser().get("userName")){
				userName = (String) getCurrentUser().get("userName");
			}
		}
		return userName;
	}

	/**
	 * 得到当前用户昵称
	 * 
	 * @auther sunchanglin
	 * @date 2016-9-27 下午05:32:57
	 * @return
	 */
	public static String getCurrentUserNickName() {
		String userNickName = "";
		if(getCurrentUser()!=null && !getCurrentUser().isEmpty()){
			if(null!=getCurrentUser().get("userNickName")){
				userNickName = (String) getCurrentUser().get("userNickName");
			}
		}
		return userNickName;
	}

	/**
	 * 得到当前用户英文名称
	 * 
	 * @auther sunchanglin
	 * @date 2016-9-27 下午05:32:57
	 * @return
	 */
	public static String getCurrentUserEnglishName() {
		String userEnglishName = "";
		if(getCurrentUser()!=null && !getCurrentUser().isEmpty()){
			if(null!=getCurrentUser().get("userEnglishName")){
				userEnglishName = (String) getCurrentUser().get("userEnglishName");
			}
		}
		return userEnglishName;
	}
	
	/**
	 * 得到当前用户头像
	 * 
	 * @auther sunchanglin
	 * @date 2016-9-27 下午05:32:57
	 * @return
	 */
	public static String getCurrentUserPicture() {
		String userPicture = "";
		if(getCurrentUser()!=null && !getCurrentUser().isEmpty()){
			if(null!=getCurrentUser().get("userPicture")){
				userPicture = String.valueOf(getCurrentUser().get("userPicture"));
			}
		}
		return userPicture;
	}
	
	/**
	 * 得到当前用户动态Key
	 * 
	 * @auther sunchanglin
	 * @date 2016-9-27 下午05:32:57
	 * @return
	 */
	public static String getCurrentUserDynamicKey() {
		String dynamicKey = "";
		if(getCurrentUser()!=null && !getCurrentUser().isEmpty()){
			if(null!=getCurrentUser().get("dynamicKey")){
				dynamicKey = String.valueOf(getCurrentUser().get("dynamicKey"));
			}
		}
		return dynamicKey;
	}
	
	
	/**
	 * 得到当前用户Token
	 * 
	 * @auther sunchanglin
	 * @date 2016-9-27 下午05:32:57
	 * @return
	 */
	public static String getCurrentUserToken() {
		String token = "";
		if(getCurrentUser()!=null && !getCurrentUser().isEmpty()){
			if(null!=getCurrentUser().get("token")){
				token = String.valueOf(getCurrentUser().get("token"));
			}
		}
		return token;
	}
	
	/**
	 * 得到当前用户状态
	 * 
	 * @auther sunchanglin
	 * @date 2016-9-27 下午05:32:57
	 * @return
	 */
	public static Integer getCurrentUserState() {
		int state = 0;
		if(getCurrentUser()!=null && !getCurrentUser().isEmpty()){
			if(null!=getCurrentUser().get("state")){
				state = Integer.parseInt(getCurrentUser().get("state").toString());
			}
		}
		return state;
	}
	
	
	/**
	 * 得到当前用户类型
	 * 
	 * @auther sunchanglin
	 * @date 2016-9-27 下午05:32:57
	 * @return
	 */
	public static String getCurrentUserType() {
		String type = "";
		if(getCurrentUser()!=null && !getCurrentUser().isEmpty()){
			if(null!=getCurrentUser().get("type")){
				type = String.valueOf(getCurrentUser().get("type"));
			}
		}
		return type;
	}
	
	
	
	
	/**
	 * 得到当前权限系统Id
	 * 
	 * @auther sunchanglin
	 * @date 2016-9-27 下午05:32:26
	 * @return
	 */
	public static long getCurrentSystemId() {
		Long systemId = 0L;
		if(getCurrentSecSystem()!=null && !getCurrentSecSystem().isEmpty()){
			if(null != getCurrentSecSystem().get("systemId")){
				systemId = Long.parseLong(String.valueOf(getCurrentSecSystem().get("systemId")));
			}
		}
		return systemId;
	}
	
	/**
	 * 得到当前权限系统名称
	 * 
	 * @auther sunchanglin
	 * @date 2016-9-27 下午05:32:26
	 * @return
	 */
	public static String getCurrentSystemName() {
		String systemName ="";
		if(getCurrentSecSystem()!=null && !getCurrentSecSystem().isEmpty()){
			if(null != getCurrentSecSystem().get("systemName")){
				systemName = (String) getCurrentSecSystem().get("systemName");
			}
		}
		return systemName;
	}
	
	
	/**
	 * 得到当前权限系统code
	 * 
	 * @auther sunchanglin
	 * @date 2016-9-27 下午05:32:26
	 * @return
	 */
	public static String getCurrentSystemCode() {
		String systemCode ="";
		if(getCurrentSecSystem()!=null && !getCurrentSecSystem().isEmpty()){
			if(null != getCurrentSecSystem().get("systemCode")){
				systemCode = (String) getCurrentSecSystem().get("systemCode");
			}
		}
		return systemCode;
	}
	
	/**
	 * 得到当前权限系统角色Id
	 * 
	 * @auther sunchanglin
	 * @date 2016-9-27 下午05:32:26
	 * @return
	 */
	public static long getCurrentSystemRoleId() {
		Long systemRoleId =0L;
		if(getCurrentSecSystem()!=null && !getCurrentSecSystem().isEmpty()){
			if(null != getCurrentSecSystem().get("systemRoleId")){
				systemRoleId = Long.parseLong(getCurrentSecSystem().get("systemRoleId").toString());
			}
		}
		
		return systemRoleId;
	}
	
	/**
	 * 验证用户是否登录
	 * 
	 * @auther sunchanglin
	 * @date 2016-9-27 下午05:32:26
	 * @return
	 */
	public static boolean isSessionLogin(){
		boolean isLogin = false;
		if(getCurrentUser()!=null && !getCurrentUser().isEmpty()){
			isLogin = true;
		}
		return isLogin;
	}
	
	
	/**
	 * 验证用户是否登录
	 * 
	 * @auther sunchanglin
	 * @date 2016-9-27 下午05:32:26
	 * @return
	 */
	public static void loginOut(){
		SystemHelper  system = new SystemHelper();
		system.session.removeAttribute(ConstantSystemValues.CURRENT_USER);
	}
	
}
