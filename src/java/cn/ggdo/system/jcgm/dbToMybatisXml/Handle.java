package cn.ggdo.system.jcgm.dbToMybatisXml;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cn.ggdo.system.jcgm.until.DBTable;
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
			String outputPath = fu.readPropertiesValue("PathProject") + "/" + fu.readPropertiesValue("PathMybatis") + "/"+StringUntil.toLowerCaseFirst(className)+ config.getFILE_TYPE();
			String mybatisMainPath = fu.readPropertiesValue("PathProject") + "/" + fu.readPropertiesValue("PathMainMybatis");
			
			NameUntil.setMybatisMapperName(className);
			NameUntil.setMybatisMainXMLMapper(mybatisMainPath);
			NameUntil.setDaoPackageName(packageName);
			NameUntil.setDaoPathName(outputPath);
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
			map.put("PACKAGE_NAME", NameUntil.getDaoPackageName());
			map.put("MAPPER_PACKAGE_NAME", NameUntil.getDaoPackageName()+"."+NameUntil.getDaoName()); //mybatis 的namespace
			
			//方法的差数名
			map.put("SQL_LOOK_FOR_ALL", getLookForAllString()); // lookForAll:查询全部数据
			map.put("SQL_GET_BEAN_BY_ID", getBeanByIdString()); // getBeanById:根据ID查询数据
			map.put("SQL_LOOK_FOR_ALL_BY_LIST", lookForAllByListString()); // lookForAllByList:按条件分页查询数据
			map.put("SQL_LOOK_FOR_ALL_BY_COUNT", lookForAllByCountString()); // lookForAllByCount:按条件查询数据总数
			map.put("SQL_SAVE_BEAN", saveBeanString()); // saveBean:增加新数据
			map.put("SQL_SAVE_ALL_BEAN", saveAllBeanString()); // saveAllBean:批量增加新数据
			map.put("SQL_UPDATE_BEAN", updateBeanString()); // updateBean: 修改Bean
			map.put("SQL_DELETE_BEAN_BY_ID", deleteBeanByIdString()); // deleteBeanById: 根据id删除Bean
			map.put("SQL_DELETE_ALL_BEAN", deleteAllBeanString()); // deleteAllBean: 根据一批id删除一批Bean
			map.put("SQL_UPDATE_ALL_BEAN", updateAllBeanString()); // 批量更新Bean对象
			
			//模板的路径
			String templatePath = fu.readPropertiesValue("PathTemplate") + "/";
			//输出的路径
			String outputPath = NameUntil.getDaoPathName();
			GeneratePage bean = new GeneratePage();
			bean.analysisTemplate(templatePath, config.getTEMPLATE_NAME(), outputPath, map);
			
			//修改mybatis文件
			map=new HashMap<String, Object>();
			String mainFileContext = "";
			// 读取原有xml追加新信息
			if(new File(NameUntil.getMybatisMainXMLMapper()).exists()){
				//读取主文件的流
				mainFileContext = fu.readFile(NameUntil.getMybatisMainXMLMapper(), "UTF-8");
			}else{
				/**生成主文件，如果主文件不存在**/
				//读取主文件的流
				String templateMainPath = fu.readPropertiesValue("PathTemplate") + "/" + config.getMAIN_MYBATIS_MAIN_TEMPLATE();
				mainFileContext = fu.readFile(templateMainPath, "UTF-8");
			}
			//读取要添加文件的流
			String addContext = "<mapper resource=\"mybatis/" + StringUntil.toLowerCaseFirst(NameUntil.getMybatisMapperName())+ config.getFILE_TYPE() + "\" />\r\n";
			if(mainFileContext.indexOf(addContext) > 0){
			}else{
				addContext += "\t\t<!-- ====================@replace@==================== -->\r\n";
				//增加内容
				mainFileContext = mainFileContext.replace("<!-- ====================@replace@==================== -->", addContext);
			}
			
			//输出的路径
			outputPath = NameUntil.getMybatisMainXMLMapper();
			bean.analysisTemplateByString(mainFileContext, outputPath, map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}	
	}
	
	
	/**
	 * lookForAll: 查询全部数据的SQl
	 */
	@SuppressWarnings("unchecked")
	public String getLookForAllString(){
		cn.ggdo.system.jcgm.dbToEntity.Handle entityHandle = new cn.ggdo.system.jcgm.dbToEntity.Handle();
		DBTable dbTable = new DBTable();
		try {
			 dbTable = entityHandle.initColumns();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String sql = "";
		StringBuffer s = new StringBuffer();
		
		String select = "SELECT ";
		s.append(select);  //select
		
		Map<String ,String> columnsMap = NameUntil.getCOLUMNS_MAP();
		Iterator it = columnsMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry entry = (Map.Entry) it.next();
			Object key = entry.getKey();  //数据库字段的名称
			Object value = entry.getValue();  //bean对象属性的名称
			if(it.hasNext()){
				s.append(key+" as "+value+" , ");
			}else{
				s.append(key+" as "+value);
			}
		}
		
		String from = " FROM ";
		s.append(from);
		
		String tableName = dbTable.getTableName();
		s.append(tableName);
		
		String orderBy = " ORDER BY PK_ID DESC";
		s.append(orderBy);
		
		sql = s.toString();
		return sql;
	}
	
	/**
	 * getBeanById:  根据ID查询数据
	 */
	@SuppressWarnings("unchecked")
	public String getBeanByIdString(){
		cn.ggdo.system.jcgm.dbToEntity.Handle entityHandle = new cn.ggdo.system.jcgm.dbToEntity.Handle();
		DBTable dbTable = new DBTable();
		try {
			 dbTable = entityHandle.initColumns();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String sql = "";
		StringBuffer s = new StringBuffer();
		
		String select = "SELECT ";
		s.append(select);
		
		Map<String ,String> columnsMap = NameUntil.getCOLUMNS_MAP();
		Iterator it = columnsMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry entry = (Map.Entry) it.next();
			Object key = entry.getKey();  //数据库字段的名称
			Object value = entry.getValue();  //bean对象属性的名称
			if(it.hasNext()){
				s.append(key+" as "+value+" , ");
			}else{
				s.append(key+" as "+value);
			}
		}
		
		String from = " FROM ";
		s.append(from);
		
		String tableName =dbTable.getTableName();
		s.append(tableName);
		
		String where = " WHERE PK_ID = #{pkId}";
		s.append(where);
		
		sql = s.toString();
		return sql;
	}
	
	
	/**
	 * lookForAllByList:  按条件分页查询数据
	 */
	@SuppressWarnings("unchecked")
	public String lookForAllByListString(){
		cn.ggdo.system.jcgm.dbToEntity.Handle entityHandle = new cn.ggdo.system.jcgm.dbToEntity.Handle();
		DBTable dbTable = new DBTable();
		try {
			 dbTable = entityHandle.initColumns();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String sql = "";
		StringBuffer s = new StringBuffer();
		
		String select = "SELECT ";
		s.append(select);
		
		Map<String ,String> columnsMap = NameUntil.getCOLUMNS_MAP();
		Iterator it = columnsMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry entry = (Map.Entry) it.next();
			Object key = entry.getKey();  //数据库字段的名称
			Object value = entry.getValue();  //bean对象属性的名称
			if(it.hasNext()){
				s.append(key+" as "+value+" , ");
			}else{
				s.append(key+" as "+value);
			}
		}
		
		String from = " FROM ";
		s.append(from);
		
		String tableName =dbTable.getTableName();
		s.append(tableName);
		
		String where = " WHERE 1=1 \r\n";
		s.append(where);
		
		Iterator itTerm = columnsMap.entrySet().iterator();
		while(itTerm.hasNext()){
			Map.Entry entry = (Map.Entry) itTerm.next();
			Object key = entry.getKey();  //数据库字段的名称
			Object value = entry.getValue();  //bean对象属性的名称
			s.append("\t\t<if test=\""+value+" != null and "+value+" != ''\">\r\n");
			s.append(" \t\t\tAND "+key+" = #{"+value+"}\r\n");
			s.append("\t\t</if>\r\n");
		}
		
		String orderBy = " \t\tORDER BY PK_ID DESC LIMIT #{start},#{range}";
		s.append(orderBy);
		
		sql = s.toString();
		return sql;
	}
	
	/**
	 * lookForAllByCount:  按条件查询数据总数
	 */
	@SuppressWarnings("unchecked")
	public String lookForAllByCountString(){
		cn.ggdo.system.jcgm.dbToEntity.Handle entityHandle = new cn.ggdo.system.jcgm.dbToEntity.Handle();
		DBTable dbTable = new DBTable();
		try {
			 dbTable = entityHandle.initColumns();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String sql = "";
		StringBuffer s = new StringBuffer();
		
		String select = "SELECT ";
		s.append(select);
		
		String count = " COUNT(*) ";
		s.append(count);
		
		Map<String ,String> columnsMap = NameUntil.getCOLUMNS_MAP();
		
		String from = " FROM ";
		s.append(from);
		
		String tableName =dbTable.getTableName();
		s.append(tableName);
		
		String where = " WHERE 1=1 \r\n";
		s.append(where);
		
		Iterator itTerm = columnsMap.entrySet().iterator();
		while(itTerm.hasNext()){
			Map.Entry entry = (Map.Entry) itTerm.next();
			Object key = entry.getKey();  //数据库字段的名称
			Object value = entry.getValue();  //bean对象属性的名称
			s.append("\t\t<if test=\""+value+" != null and "+value+" != ''\">\r\n");
			s.append(" \t\t\tAND "+key+" = #{"+value+"}\r\n");
			s.append("\t\t</if>\r\n");
		}
		
		sql = s.toString();
		return sql;
	}
	
	/**
	 * saveBean:增加新数据
	 */
	@SuppressWarnings("unchecked")
	public String saveBeanString(){
		cn.ggdo.system.jcgm.dbToEntity.Handle entityHandle = new cn.ggdo.system.jcgm.dbToEntity.Handle();
		DBTable dbTable = new DBTable();
		try {
			 dbTable = entityHandle.initColumns();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String sql = "";
		StringBuffer s = new StringBuffer();
		
		String returnId = "<selectKey resultType=\"long\" order=\"AFTER\" keyProperty=\"pkId\">\r\n"+
			                              "\t\t\tSELECT LAST_INSERT_ID()\r\n"+
		                            "\t\t</selectKey>\r\n";
		s.append(returnId);
		
		s.append("\t\tINSERT INTO ");
		
		String tableName = dbTable.getTableName();
		s.append(tableName);
		
		s.append(" (");
		Map<String ,String> columnsMap = NameUntil.getCOLUMNS_MAP();
		Iterator it = columnsMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry entry = (Map.Entry) it.next();
			Object key = entry.getKey();  //数据库字段的名称
			if(it.hasNext()){
				s.append(key+" , ");
			}else{
				s.append(key);
			}
		}
		s.append(" )\r\n");
		
		s.append(" \t\tVALUES( ");
		Iterator itValue = columnsMap.entrySet().iterator();
		while(itValue.hasNext()){
			Map.Entry entry = (Map.Entry) itValue.next();
			Object value = entry.getValue();  //数据库字段的名称
			if(itValue.hasNext()){
				s.append("#{"+value+"}"+" , ");
			}else{
				s.append("#{"+value+"}");
			}
		}
		s.append(" )");
		
		sql = s.toString();
		return sql;
	}
	
	/**
	 * saveAllBean:批量增加新数据
	 */
	@SuppressWarnings("unchecked")
	public String saveAllBeanString(){
		cn.ggdo.system.jcgm.dbToEntity.Handle entityHandle = new cn.ggdo.system.jcgm.dbToEntity.Handle();
		DBTable dbTable = new DBTable();
		try {
			 dbTable = entityHandle.initColumns();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String sql = "";
		StringBuffer s = new StringBuffer();
		
		String returnId = "<selectKey resultType=\"long\" order=\"AFTER\" keyProperty=\"pkId\">\r\n"+
			                              "\t\t\tSELECT LAST_INSERT_ID()\r\n"+
		                            "\t\t</selectKey>\r\n";
		s.append(returnId);
		
		s.append("\t\tINSERT INTO ");
		
		String tableName = dbTable.getTableName();
		s.append(tableName);
		
		s.append(" (");
		Map<String ,String> columnsMap = NameUntil.getCOLUMNS_MAP();
		Iterator it = columnsMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry entry = (Map.Entry) it.next();
			Object key = entry.getKey();  //数据库字段的名称
			if(it.hasNext()){
				s.append(key+" , ");
			}else{
				s.append(key);
			}
		}
		s.append(" )\r\n");
		
		s.append(" \t\tVALUES\r\n \t\t<foreach item=\"bean\"  collection=\"list\" open=\"\"    separator=\",\" close=\"\" index=\"index\" >\r\n \t\t\t( ");
		Iterator itValue = columnsMap.entrySet().iterator();
		while(itValue.hasNext()){
			Map.Entry entry = (Map.Entry) itValue.next();
			Object value = entry.getValue();  //数据库字段的名称
			if(itValue.hasNext()){
				s.append("#{bean."+value+"}"+",");
			}else{
				s.append("#{bean."+value+"}");
			}
		}
		s.append(" )\r\n");
		s.append("\t\t</foreach>");
		
		sql = s.toString();
		return sql;
	}
	
	/**
	 * updateBean  修改Bean
	 */
	@SuppressWarnings("unchecked")
	public String updateBeanString(){
		cn.ggdo.system.jcgm.dbToEntity.Handle entityHandle = new cn.ggdo.system.jcgm.dbToEntity.Handle();
		DBTable dbTable = new DBTable();
		try {
			 dbTable = entityHandle.initColumns();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String sql = "";
		StringBuffer s = new StringBuffer();
		
		s.append("UPDATE ");
		
		String tableName = dbTable.getTableName();
		s.append(tableName);
		
		s.append(" SET \r\n");
		
		Map<String ,String> columnsMap = NameUntil.getCOLUMNS_MAP();
		Iterator it = columnsMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry entry = (Map.Entry) it.next();
			Object key = entry.getKey();  //数据库字段的名称
			Object value = entry.getValue();  //bean对象属性的名称
			if(!(key.equals("PK_ID")) && key != "PK_ID"){
				if(it.hasNext()){
					s.append("\t\t"+key+" = #{"+value+"},\r\n");
				}else{
					s.append("\t\t"+key+" = #{"+value+"}\r\n");
				}
			}
		}
		
		s.append(" \t\tWHERE PK_ID  = #{pkId}");
		
		sql = s.toString();
		return sql;
	}
	
	/**
	 * updateAllBean:批量更新新数据
	 */
	@SuppressWarnings("unchecked")
	public String updateAllBeanString(){
		cn.ggdo.system.jcgm.dbToEntity.Handle entityHandle = new cn.ggdo.system.jcgm.dbToEntity.Handle();
		DBTable dbTable = new DBTable();
		try {
			 dbTable = entityHandle.initColumns();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//准备对象
		String sql = "";
		StringBuffer s = new StringBuffer();
		//封装第一部分：循环部分
		String returnId = "<foreach collection=\"list\" item=\"item\" index=\"index\" open=\"\" close=\"\" separator=\";\">\r\n";
		s.append(returnId);
		//封装第二部分：表头部分
		s.append("\t\tUPDATE ");
		String tableName = dbTable.getTableName();
		s.append(tableName);
		s.append("\r\n");
		//封装第三部分：<set>
		s.append("\t\t<set>\r\n");
		
		//封装第四部分：更新部分
		Map<String ,String> columnsMap = NameUntil.getCOLUMNS_MAP();
		Iterator it = columnsMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry entry = (Map.Entry) it.next();
			Object key = entry.getKey();  //数据库字段的名称
			Object value = entry.getValue();  //bean对象属性的名称
			if(!(key.equals("PK_ID")) && key != "PK_ID"){
				if(it.hasNext()){
					s.append("\t\t\t"+key+" = #{"+value+"},\r\n");
				}else{
					s.append("\t\t\t"+key+" = #{"+value+"}\r\n");
				}
			}
		}
		//封装第五部分：</set>
		s.append("\t\t</set>\r\n");
		//封装第六部分：更新条件
		s.append("\t\tWHERE PK_ID  = #{pkId}\r\n");
		//封装第七部分：封闭循环
		s.append("\t\t</foreach>\r\n");
		
		sql = s.toString();
		return sql;
	}

	/**
	 * deleteBeanById 根据id删除Bean
	 */
	public String deleteBeanByIdString(){
		cn.ggdo.system.jcgm.dbToEntity.Handle entityHandle = new cn.ggdo.system.jcgm.dbToEntity.Handle();
		DBTable dbTable = new DBTable();
		try {
			 dbTable = entityHandle.initColumns();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String sql = "";
		StringBuffer s = new StringBuffer();
		
		s.append("DELETE FROM ");
		
		String tableName = dbTable.getTableName();
		s.append(tableName);
		
		s.append(" WHERE PK_ID  = #{pkId}");
		
		sql = s.toString();
		return sql;
		
	}
	
	/**
	 * deleteAllBean 根据一批id删除一批Bean
	 */
	public String deleteAllBeanString(){
		cn.ggdo.system.jcgm.dbToEntity.Handle entityHandle = new cn.ggdo.system.jcgm.dbToEntity.Handle();
		DBTable dbTable = new DBTable();
		try {
			 dbTable = entityHandle.initColumns();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String sql = "";
		StringBuffer s = new StringBuffer();
		
		s.append("DELETE FROM ");
		
		String tableName = dbTable.getTableName();
		s.append(tableName);
		
		s.append(" WHERE PK_ID  IN\r\n");
		
		s.append("\t\t<foreach item=\"value\"  collection=\"list\" open=\"(\"    separator=\",\" close=\")\" index=\"index\" >\r\n");		
		s.append("\t\t\t#{value}\r\n");
		s.append("\t\t</foreach>");
		
		sql = s.toString();
		return sql;
	}
}
