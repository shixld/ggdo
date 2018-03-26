package cn.ggdo.system.jcgm.dbToEntity;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.ggdo.system.jcgm.until.DBColumns;
import cn.ggdo.system.jcgm.until.DBTable;
import cn.ggdo.system.jcgm.until.FileUntil;
import cn.ggdo.system.jcgm.until.JDBCUntil;
import cn.ggdo.system.jcgm.until.NameUntil;
import cn.ggdo.system.jcgm.until.StringUntil;

public class Handle {
	public Config config = new Config();
	public StringUntil su = new StringUntil();
	public FileUntil fu = new FileUntil();

	boolean import_util = false;
	boolean import_sql = false;

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

			NameUntil.setBeanName(className);
			NameUntil.setBeanPackageName(packageName);
			NameUntil.setBeanPathName(outputPath);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 初始化表字段信息
	 * @param tableName 表名
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	public DBTable initColumns() throws Exception {
		// 1.创建数据库连接
		String driverString = fu.readPropertiesValue("DBDriver");
		String url = fu.readPropertiesValue("DBUrl");
		String user = fu.readPropertiesValue("DBUser");
		String password = fu.readPropertiesValue("DBPass");
		String tableName = fu.readPropertiesValue("DBTable");
		Connection conn = new JDBCUntil(driverString, url, user, password).getConnection();
		DatabaseMetaData dbmd = conn.getMetaData();
		// 2.1.获取字段信息
		ResultSet rsc = dbmd.getColumns(null, null, tableName, "%");
		ResultSetMetaData rsmdc = rsc.getMetaData();
		rsc.last();
		int columnsSize = rsc.getRow();
		DBTable dbTable = new DBTable();
		dbTable.setTableName(tableName);
		ArrayList<DBColumns> list = new ArrayList<DBColumns>();
		rsc.beforeFirst();
		int j = 0;
		while (rsc.next()) {
			DBColumns dbColumns = new DBColumns();
			for (int i = 0; i < rsmdc.getColumnCount(); i++) {
				if (i == 3) {
					dbColumns.setColumnsName(rsc.getString(i + 1).toLowerCase());
				}
				if (i == 5) {
					dbColumns.setColumnsType(rsc.getString(i + 1));
					if ("date".equals(dbColumns.getColumnsType().toLowerCase()) ||"datetime".equals(dbColumns.getColumnsType().toLowerCase()) || "timestamp".equals(dbColumns.getColumnsType().toLowerCase())) {
						import_util = true;
					}
					if ("image".equals(dbColumns.getColumnsType().toLowerCase())
							|| "text".equals(dbColumns.getColumnsType().toLowerCase())) {
						import_sql = true;
					}
				}
				if (i == 6) {
					dbColumns.setColumnsLength(rsc.getString(i + 1));
				}
				//nullable
				if (i == 10) {
					if ("0".equals(rsc.getString(i + 1))) {
						dbColumns.setColumnsNullAble("true");
					}else {
						dbColumns.setColumnsNullAble("false");
					}
				}
				if (i == 11) {
					dbColumns.setColumnsComment(rsc.getString(i + 1));
				}
			}
			list.add(dbColumns);
		}
		dbTable.setDbColumnss(list);
		return dbTable;
	}

	/**
	 * 生成实体类主体(指定路径中)
	 * @param packagePath 包名
	 * @param tableName 表名
	 * @param outputPath 输出路径
	 * @return
	 * @throws IOException
	 */
	public boolean writeGenerate() throws IOException {
		try {
			this.initColumns();
			StringBuffer sb = new StringBuffer();
			// 第一步类包信息
			sb.append("package " + NameUntil.getBeanPackageName() + ";\r\n");
			sb.append("\r\n");
			// 第二步引用包信息
			sb.append("import java.io.Serializable;");
			sb.append("\r\n");
			sb.append("import lombok.Data;");
			sb.append("\r\n");
			sb.append("import lombok.EqualsAndHashCode;");
			
			// 第三步判断是否导入工具包
			if (import_sql) {
				sb.append("import java.sql.*;");
				sb.append("\r\n");
			}
			if (import_util) {
				sb.append("import java.util.Date;");
				sb.append("\r\n");
			}
			sb.append("\r\n");
			
			// 第四步实体部分
			sb.append("@Data");
			sb.append("\r\n");
			sb.append("@EqualsAndHashCode(callSuper = false)");
			sb.append("\r\n");

			sb.append("public class " + NameUntil.getBeanName() + " implements Serializable {\r\n");
			// 第五步全部属性
			processAllAttrsBeautiful(sb);
			// 第六步全部方法
			processAllMethod(sb);
			sb.append("}");

			String outputPath = NameUntil.getBeanPathName();
			String content = sb.toString();
			String encoding = "UTF-8";

			fu.writerFile(content, outputPath, encoding);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 生成实体类全部属性(美化)
	 * @param sb
	 * @throws Exception
	 */
	public void processAllAttrsBeautiful(StringBuffer sb) throws Exception {
		Map<String ,String> columnsMap = new HashMap<String ,String>();
		List<DBColumns> dbColumnss = this.initColumns().getDbColumnss();
		int thisLength = 0;
		for (DBColumns dbColumns : dbColumnss) {
			String str = "\tprivate " + getJavaType(dbColumns.getColumnsType()) + " "
					+ su.toStandardFirstSmall(dbColumns.getColumnsName(), "_") + ";";
			if (thisLength < str.length()) {
				thisLength = str.length() + 1;
			}
		}
		for (DBColumns dbColumns : dbColumnss) {
			String str = "\tprivate " + getJavaType(dbColumns.getColumnsType()) + " "
					+ su.toStandardFirstSmall(dbColumns.getColumnsName(), "_") + ";";
			int nbspLength = thisLength - str.length();
			String nbsp = "";
			for (int j = 0; j < nbspLength; j++) {
				nbsp = nbsp + " ";
			}
			if (dbColumns.getColumnsComment() != null && !"".equals(dbColumns.getColumnsComment())) {
				sb.append(str + nbsp + " // [" + dbColumns.getColumnsComment() + "]\r\n");
			} else {
				sb.append(str + nbsp + " // [" + dbColumns.getColumnsName() + "]\r\n");
			}
			columnsMap.put(dbColumns.getColumnsName(), su.toStandardFirstSmall(dbColumns.getColumnsName(), "_"));
		}
		NameUntil.setCOLUMNS_MAP(columnsMap);
		sb.append("\r\n");
	}

	/**
	 * 生成实体类全部方法
	 * @param sb
	 * @throws Exception
	 */
	public void processAllMethod(StringBuffer sb) throws Exception {
		List<DBColumns> dbColumnss = this.initColumns().getDbColumnss();
		for (DBColumns dbColumns : dbColumnss) {
			sb.append("\tpublic void set" + su.toStandardFirstBig(dbColumns.getColumnsName(), "_") + "("
					+ getJavaType(dbColumns.getColumnsType()) + " "
					+ su.toStandardFirstSmall(dbColumns.getColumnsName(), "_") + ") {\r\n");
			sb.append("\t\tthis." + su.toStandardFirstSmall(dbColumns.getColumnsName(), "_") + " = "
					+ su.toStandardFirstSmall(dbColumns.getColumnsName(), "_") + ";\r\n");
			sb.append("\t}\r\n\r\n");
			sb.append("\tpublic " + getJavaType(dbColumns.getColumnsType()) + " get"
					+ su.toStandardFirstBig(dbColumns.getColumnsName(), "_") + "() {\r\n");
			sb.append("\t\treturn " + su.toStandardFirstSmall(dbColumns.getColumnsName(), "_") + ";\r\n");
			sb.append("\t}\r\n\r\n");
		}
	}

	/**
	 * 获取JAVA数据类型
	 * @param DB数据类型
	 * @return JAVA数据类型
	 */
	public String getJavaType(String dbType) {
		if (dbType.equalsIgnoreCase("bit")) {
			return "boolean";
		} else if (dbType.equalsIgnoreCase("tinyint")) {
			return "byte";
		} else if (dbType.equalsIgnoreCase("smallint")) {
			return "short";
		} else if (dbType.equalsIgnoreCase("int") || dbType.equalsIgnoreCase("int unsigned")) {
			return "int";
		} else if (dbType.equalsIgnoreCase("bigint")) {
			return "Long";
		} else if (dbType.equalsIgnoreCase("float")) {
			return "float";
		} else if (dbType.equalsIgnoreCase("decimal") || dbType.equalsIgnoreCase("numeric")
				|| dbType.equalsIgnoreCase("real") || dbType.equalsIgnoreCase("money")
				|| dbType.equalsIgnoreCase("smallmoney")) {
			return "double";
		} else if (dbType.equalsIgnoreCase("varchar") || dbType.equalsIgnoreCase("char")
				|| dbType.equalsIgnoreCase("nvarchar") || dbType.equalsIgnoreCase("nchar")
				|| dbType.equalsIgnoreCase("text") || dbType.equalsIgnoreCase("longtext")) {
			return "String";
		} else if (dbType.equalsIgnoreCase("date") ||dbType.equalsIgnoreCase("datetime") || dbType.equalsIgnoreCase("timestamp")) {
			return "Date";
		} else if (dbType.equalsIgnoreCase("image")) {
			return "Blod";
		} else {
			return "String";
		}
	}
}
