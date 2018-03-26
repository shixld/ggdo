package cn.ggdo.system.jcgm.dbToJsp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.ggdo.system.jcgm.until.DBColumns;
import cn.ggdo.system.jcgm.until.FileUntil;
import cn.ggdo.system.jcgm.until.GeneratePage;
import cn.ggdo.system.jcgm.until.NameUntil;
import cn.ggdo.system.jcgm.until.StringUntil;

public class Handle {
	public Config config = new Config();
	public StringUntil su = new StringUntil();
	public FileUntil fu = new FileUntil();

	public boolean setNameUntil() {
		try {
			String functionName = "generate";
			String genPathHead = fu.readPropertiesValue("GenPathHead");
			if(genPathHead.indexOf("/") > 0){
				functionName = genPathHead.substring(genPathHead.lastIndexOf("/")+1 ,genPathHead.length());
			}
			String listJspName = config.getBEFORE()
					+ su.toStandardFirstBig(fu.readPropertiesValue("DBTableAlias"), "_")
					+ config.getAFTER();
			String listJspPath = fu.readPropertiesValue("PathProject") + "/"
					+ fu.readPropertiesValue("PathJsp") + "/"
				    + listJspName
					+ config.getFILE_TYPE();
			
			String jsName = su.toStandardFirstBig(fu.readPropertiesValue("DBTableAlias"), "_");
			String jsPath = fu.readPropertiesValue("PathProject") + "/"
					+ fu.readPropertiesValue("PathResources") + "/"
				    + jsName
					+ config.getJS_FILE_TYPE();
			
			String cssName = su.toStandardFirstBig(fu.readPropertiesValue("DBTableAlias"), "_");
			String cssPath = fu.readPropertiesValue("PathProject") + "/"
					+ fu.readPropertiesValue("PathResources") + "/"
				    + cssName
					+ config.getCSS_FILE_TYPE();

			NameUntil.setJsName(jsName);
			NameUntil.setJsPath(jsPath);
			NameUntil.setCssName(cssName);
			NameUntil.setCssPath(cssPath);
			NameUntil.setListJspName(listJspName);
			NameUntil.setListJspPath(listJspPath);
			NameUntil.setFunctionName(functionName);
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
			writeGenerateJs();
			writeGenerateCss();
			cn.ggdo.system.jcgm.dbToEntity.Handle entityHandle = new cn.ggdo.system.jcgm.dbToEntity.Handle();
			StringBuffer first = new StringBuffer();
			StringBuffer second = new StringBuffer();
			StringBuffer addTable = new StringBuffer();
			StringBuffer resourcesFile = new StringBuffer();
			List<DBColumns> list = entityHandle.initColumns().getDbColumnss();
			for (DBColumns dbColumns : list) {
				//生成List表头部分
				if ("".equals(dbColumns.getColumnsComment())) {
					first
							.append("\t\t\t\t\t\t\t<th><strong>"
									+ su.toStandardFirstSmall(dbColumns
											.getColumnsName(), "_")
									+ "</strong></th>\r\n");
				} else {
					first
							.append("\t\t\t\t\t\t\t\t<th><strong>"
									+ dbColumns.getColumnsComment()
									+ "</strong></th>\r\n");
				}
				
				//生成List的体部分
				second
						.append("\t\t\t\t\t\t\t<td id='"+su.toStandardFirstSmall(dbColumns.getColumnsName(), "_")+"'>${"+StringUntil.toLowerCaseFirst(NameUntil.getBeanName())+"."+su.toStandardFirstSmall(dbColumns.getColumnsName(), "_")+" }"
								+ "</td>\r\n");
				
				//生成增加和修改部分
				if ("".equals(dbColumns.getColumnsComment())) {
					addTable.append("\t\t\t\t\t\t<tr>\r\n");
					addTable.append("\t\t\t\t\t\t\t<td width=30% class=\"td-lf td-text-ri\">"
							+ su.toStandardFirstSmall(dbColumns.getColumnsName(), "_") + ":</td>\r\n");
					addTable.append("\t\t\t\t\t\t\t<td width=70% style=\"text-align: left;padding-left: 2px;\"><input type=\"text\" name=\""
							+ su.toStandardFirstSmall(dbColumns.getColumnsName(), "_")
							+"\" id=\""
							+ su.toStandardFirstSmall(dbColumns.getColumnsName(), "_")
							+ "\" class=\"form-control\" placeholder=\"\" /></td>\r\n");
					addTable.append("\t\t\t\t\t\t</tr>\r\n");
				} else {
					addTable.append("\t\t\t\t\t\t<tr>\r\n");
					addTable.append("\t\t\t\t\t\t\t<td width=30% class=\"td-lf td-text-ri\">"
							+ dbColumns.getColumnsComment() + ":</td>\r\n");
					addTable.append("\t\t\t\t\t\t\t<td width=70% style=\"text-align: left;padding-left: 2px;\"><input type=\"text\" name=\""
							+ su.toStandardFirstSmall(dbColumns.getColumnsName(), "_")
							+"\" id=\""
							+ su.toStandardFirstSmall(dbColumns.getColumnsName(), "_")
							+ "\" class=\"form-control\" placeholder=\"\" /></td>\r\n");
					addTable.append("\t\t\t\t\t\t</tr>\r\n");
				}
			}
			
			resourcesFile.append("\t\t<link rel=\"stylesheet\" type=\"text/css\"	href=\"<%=basePath%>resources/admin/" + NameUntil.getCssName() + ".css\" /> \r\n");
			resourcesFile.append("\t\t<script type=\"text/javascript\" src=\"<%=basePath %>resources/admin/" + NameUntil.getJsName() + ".js\"></script> \r\n");
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("LIST_VALUE", "${"+StringUntil.toLowerCaseFirst(NameUntil.getBeanName())+"List}");
			map.put("ADD_TABLE", addTable.toString());
			map.put("TABLE_HEAD", first.toString());
			map.put("FUNCTION_NAME", NameUntil.getFunctionName());
			map.put("TABLE_BODY", second.toString());
			map.put("LOWER_BEAN_NAME", StringUntil.toLowerCaseFirst(NameUntil.getBeanName()));
			map.put("INCLUDE_RESOURCES_FILE", resourcesFile.toString());
			//模板的路径
			String templatePath = fu.readPropertiesValue("PathTemplate") + "/";
			//输出的路径
			String outputPath = NameUntil.getListJspPath();
			GeneratePage bean = new GeneratePage();
			bean.analysisTemplate(templatePath, config.getTEMPLATE_NAME(), outputPath, map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}	
	}
	
	/**
	 * 使用freemark生成模板的替换后的文件
	 * @return
	 */
	public boolean writeGenerateJs() {
		try {
			cn.ggdo.system.jcgm.dbToEntity.Handle entityHandle = new cn.ggdo.system.jcgm.dbToEntity.Handle();
			StringBuffer updateResult = new StringBuffer();
			List<DBColumns> list = entityHandle.initColumns().getDbColumnss();
			for (DBColumns dbColumns : list) {
				//生成修改的赋值部分JS
				//生成List的体部分
				updateResult.append("\t\t\t\t\t$('#dataForm input[id="+su.toStandardFirstSmall(dbColumns.getColumnsName(), "_")+"]').val(obj."+su.toStandardFirstSmall(dbColumns.getColumnsName(), "_")+");\r\n");
			}
			String content =" function updateObject(id){ \r\n"
					+ "\t var actionUrl = $(\"#toUpdateUrl\").val(); \r\n"
					+ "\t $.ajax({ \r\n"
					+ "\t\t url : actionUrl, \r\n"
					+ "\t\t type : 'post', \r\n"
					+ "\t\t async : false, \r\n"
					+ "\t\t data : \"id=\"+id, \r\n"
					+ "\t\t success : function(data) { \r\n"
					+ "\t\t\t var obj = new Function(\"return\" + data)(); \r\n"
					+ "${UPDATE_RESULT} \r\n"
					+ "\t\t } \r\n"
					+ "\t }); \r\n"
					+ " } \r\n";
			GeneratePage bean = new GeneratePage();
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("UPDATE_RESULT", updateResult.toString());
			bean.analysisTemplateByString(content , NameUntil.getJsPath() , map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 使用freemark生成模板的替换后的文件
	 * @return
	 */
	public boolean writeGenerateCss() {
		try {
			GeneratePage bean = new GeneratePage();
			Map<String,Object> map=new HashMap<String, Object>();
			bean.analysisTemplateByString("" , NameUntil.getCssPath() , map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
