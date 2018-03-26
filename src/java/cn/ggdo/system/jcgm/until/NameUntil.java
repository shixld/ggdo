package cn.ggdo.system.jcgm.until;

import java.util.Map;

public class NameUntil {
	// class名称
	private static String beanName;
	private static String daoName;
	private static String serviceName;
	private static String serviceImplName;
	private static String actionName;
	// class包名
	private static String beanPackageName;
	private static String daoPackageName;
	private static String servicePackageName;
	private static String serviceImplPackageName;
	private static String actionPackageName;
	// class路径
	private static String beanPathName;
	private static String daoPathName;
	private static String servicePathName;
	private static String serviceImplPathName;
	private static String actionPathName;
	// jsp名称
	private static String listJspName;
	// jsp路径
	private static String listJspPath;
	// js名称
	private static String jsName;
	// js路径
	private static String jsPath;
	// css名称
	private static String cssName;
	// css路径
	private static String cssPath;

	// menu功能文件路径
	private static String menuFilePath;
	// xml名称
	private static String mybatisMapperName;
	private static String mybatisMainXMLMapper;

	//业务名称
	private static String functionName;
	
	//对象的属性及数据库的属性
	private static Map<String ,String> COLUMNS_MAP;

	public static String getBeanName() {
		return beanName;
	}

	public static void setBeanName(String beanName) {
		NameUntil.beanName = beanName;
	}

	public static String getDaoName() {
		return daoName;
	}

	public static void setDaoName(String daoName) {
		NameUntil.daoName = daoName;
	}

	public static String getServiceName() {
		return serviceName;
	}

	public static void setServiceName(String serviceName) {
		NameUntil.serviceName = serviceName;
	}

	public static String getServiceImplName() {
		return serviceImplName;
	}

	public static void setServiceImplName(String serviceImplName) {
		NameUntil.serviceImplName = serviceImplName;
	}

	public static String getActionName() {
		return actionName;
	}

	public static void setActionName(String actionName) {
		NameUntil.actionName = actionName;
	}

	public static String getBeanPackageName() {
		return beanPackageName;
	}

	public static void setBeanPackageName(String beanPackageName) {
		NameUntil.beanPackageName = beanPackageName;
	}

	public static String getDaoPackageName() {
		return daoPackageName;
	}

	public static void setDaoPackageName(String daoPackageName) {
		NameUntil.daoPackageName = daoPackageName;
	}

	public static String getServicePackageName() {
		return servicePackageName;
	}

	public static void setServicePackageName(String servicePackageName) {
		NameUntil.servicePackageName = servicePackageName;
	}

	public static String getServiceImplPackageName() {
		return serviceImplPackageName;
	}

	public static void setServiceImplPackageName(String serviceImplPackageName) {
		NameUntil.serviceImplPackageName = serviceImplPackageName;
	}

	public static String getActionPackageName() {
		return actionPackageName;
	}

	public static void setActionPackageName(String actionPackageName) {
		NameUntil.actionPackageName = actionPackageName;
	}

	public static String getBeanPathName() {
		return beanPathName;
	}

	public static void setBeanPathName(String beanPathName) {
		NameUntil.beanPathName = beanPathName;
	}

	public static String getDaoPathName() {
		return daoPathName;
	}

	public static void setDaoPathName(String daoPathName) {
		NameUntil.daoPathName = daoPathName;
	}

	public static String getServicePathName() {
		return servicePathName;
	}

	public static void setServicePathName(String servicePathName) {
		NameUntil.servicePathName = servicePathName;
	}

	public static String getServiceImplPathName() {
		return serviceImplPathName;
	}

	public static void setServiceImplPathName(String serviceImplPathName) {
		NameUntil.serviceImplPathName = serviceImplPathName;
	}

	public static String getActionPathName() {
		return actionPathName;
	}

	public static void setActionPathName(String actionPathName) {
		NameUntil.actionPathName = actionPathName;
	}

	public static String getListJspName() {
		return listJspName;
	}

	public static void setListJspName(String listJspName) {
		NameUntil.listJspName = listJspName;
	}

	public static String getListJspPath() {
		return listJspPath;
	}

	public static void setListJspPath(String listJspPath) {
		NameUntil.listJspPath = listJspPath;
	}

	public static String getFunctionName() {
		return functionName;
	}

	public static void setFunctionName(String functionName) {
		NameUntil.functionName = functionName;
	}

	public static String getMybatisMapperName() {
		return mybatisMapperName;
	}

	public static void setMybatisMapperName(String mybatisMapperName) {
		NameUntil.mybatisMapperName = mybatisMapperName;
	}

	public static Map<String, String> getCOLUMNS_MAP() {
		return COLUMNS_MAP;
	}

	public static void setCOLUMNS_MAP(Map<String, String> columns_map) {
		NameUntil.COLUMNS_MAP = columns_map;
	}

	public static String getMybatisMainXMLMapper() {
		return mybatisMainXMLMapper;
	}

	public static void setMybatisMainXMLMapper(String mybatisMainXMLMapper) {
		NameUntil.mybatisMainXMLMapper = mybatisMainXMLMapper;
	}

	public static String getJsName() {
		return jsName;
	}

	public static void setJsName(String jsName) {
		NameUntil.jsName = jsName;
	}

	public static String getJsPath() {
		return jsPath;
	}

	public static void setJsPath(String jsPath) {
		NameUntil.jsPath = jsPath;
	}

	public static String getCssName() {
		return cssName;
	}

	public static void setCssName(String cssName) {
		NameUntil.cssName = cssName;
	}

	public static String getCssPath() {
		return cssPath;
	}

	public static void setCssPath(String cssPath) {
		NameUntil.cssPath = cssPath;
	}

	public static String getMenuFilePath() {
		return menuFilePath;
	}

	public static void setMenuFilePath(String menuFilePath) {
		NameUntil.menuFilePath = menuFilePath;
	}

}
