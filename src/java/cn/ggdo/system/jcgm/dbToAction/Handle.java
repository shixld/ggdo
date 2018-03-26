package cn.ggdo.system.jcgm.dbToAction;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cn.ggdo.system.jcgm.until.GeneratePage;
import cn.ggdo.system.jcgm.until.FileUntil;
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

			String menuFilePath = fu.readPropertiesValue("PathProject") + "/" + fu.readPropertiesValue("FilePathMenu");
			
			NameUntil.setActionName(className);
			NameUntil.setActionPackageName(packageName);
			NameUntil.setActionPathName(outputPath);
			NameUntil.setMenuFilePath(menuFilePath);
			
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
			map.put("PACKAGE_NAME", NameUntil.getActionPackageName());
			map.put("I_SERVICE_PACKAGE_NAME", NameUntil.getServicePackageName() + "." + NameUntil.getServiceName());
			//模板的路径
			String templatePath = fu.readPropertiesValue("PathTemplate") + "/";
			//输出的路径
			String outputPath = NameUntil.getActionPathName();
			GeneratePage bean = new GeneratePage();
			bean.analysisTemplate(templatePath, config.getTEMPLATE_NAME(), outputPath, map);
			
			//修改mybatis文件
			map=new HashMap<String, Object>();
			String mainFileContext = "";
			// 读取原有xml追加新信息
			if(new File(NameUntil.getMenuFilePath()).exists()){
				//读取主文件的流
				mainFileContext = fu.readFile(NameUntil.getMenuFilePath(), "UTF-8");
			}else{
				return true;
			}
			//读取要添加文件的流
			String addContext = "+'<li>" + NameUntil.getBeanName() + "<input type=\"hidden\" value=\"" + StringUntil.toLowerCaseFirst(NameUntil.getBeanName()) + "/lookForAll.action\"></li>'\r\n";
			if(mainFileContext.indexOf(addContext) > 0){
			}else{
				addContext += "\t\t/*-menuConfig-*/";
				//增加内容
				if(mainFileContext.indexOf("/*-menuConfig-*/") > 0)
					mainFileContext = mainFileContext.replace("/*-menuConfig-*/", addContext);
			}
			
			//输出的路径
			outputPath = NameUntil.getMenuFilePath();
			bean.analysisTemplateByString(mainFileContext, outputPath, map);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}	
	}

}
