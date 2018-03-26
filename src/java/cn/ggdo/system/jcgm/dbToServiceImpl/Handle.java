package cn.ggdo.system.jcgm.dbToServiceImpl;

import java.util.HashMap;
import java.util.Map;

import cn.ggdo.system.jcgm.until.FileUntil;
import cn.ggdo.system.jcgm.until.GeneratePage;
import cn.ggdo.system.jcgm.until.NameUntil;
import cn.ggdo.system.jcgm.until.StringUntil;

public class Handle {
	public Config config = new Config();
	public StringUntil su = new StringUntil();
	public FileUntil fu = new FileUntil();

	/**
	 * 设置记录当前参数
	 * @return
	 */
	public boolean setNameUntil() {
		try {
			String className = config.getBEFORE() + su.toStandardFirstBig(fu.readPropertiesValue("DBTableAlias"), "_")
					+ config.getAFTER();
			String packageName = (fu.readPropertiesValue("GenPathHead") + "/" + config.getSECOND_PATH()).replace("/",
					".");
			String outputPath = fu.readPropertiesValue("PathProject") + "/" + fu.readPropertiesValue("PathJava") + "/"
					+ fu.readPropertiesValue("GenPathHead") + "/" + config.getSECOND_PATH() + "/" + className
					+ config.getFILE_TYPE();

			NameUntil.setServiceImplName(className);
			NameUntil.setServiceImplPackageName(packageName);
			NameUntil.setServiceImplPathName(outputPath);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 使用freemark生成模板的替换后的文件
	 * @return
	 */
	public boolean writeGenerate() {
		try {
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("LOWER_BEAN_NAME", StringUntil.toLowerCaseFirst(NameUntil.getBeanName()));
			map.put("BEAN_NAME", NameUntil.getBeanName());
			map.put("BEAN_PACKAGE_NAME", NameUntil.getBeanPackageName() + "." + NameUntil.getBeanName());
			map.put("PACKAGE_NAME", NameUntil.getServiceImplPackageName());
			map.put("I_SERVICEPACKAGE_NAME", NameUntil.getServicePackageName() + "." + NameUntil.getServiceName());
			map.put("I_DAOPACKAGE_NAME", NameUntil.getDaoPackageName() + "." + NameUntil.getDaoName());
			//模板的路径
			String templatePath = fu.readPropertiesValue("PathTemplate") + "/";
			//输出的路径
			String outputPath = NameUntil.getServiceImplPathName();
			GeneratePage bean = new GeneratePage();
			bean.analysisTemplate(templatePath, config.getTEMPLATE_NAME(), outputPath, map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}	
	}

}
