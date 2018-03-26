package cn.ggdo.system.framework.adapter.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cn.ggdo.system.framework.adapter.AdapterFactory;
import cn.ggdo.system.framework.adapter.jdbc.properties.DbPropertiesFile;

/**
 * 类名：DBAdapter
 * 版本：1.0.0
 * 用途说明：数据库处理
 * 业务区分(数据库处理)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public class DBAdapter extends AdapterFactory {

	private	Connection conn = null;

	private String driver;
	private String url;
	private String userName;
	private String password;
	
	public DBAdapter(){
		this.driver = DbPropertiesFile.DB_DRIVER;
		this.url=DbPropertiesFile.DB_URL;
    	this.userName=DbPropertiesFile.DB_USER;
    	this.password=DbPropertiesFile.DB_PASSWORD;
    	
    }
    public DBAdapter(String driver,String url,String userName,String password){
    	this.driver=driver;
    	this.url=url;
    	this.userName=userName;
    	this.password=password;
    }
    /***
     * 
     * 功能链接数据库
     * 
     * 作者：史晓林 创建时间：2014-02-24 修改时间 2014-02-24
     * 
     * @return
     */
    public Connection connction(){
    	
    	try {
			Class.forName(driver);
			conn=DriverManager.getConnection(url, userName, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
    }
    /***
     * 
     * 功能退出数据库
     * 
     * 作者：史晓林 创建时间：2014-02-24 修改时间 2014-02-24
     * @return
     */
    public void exitConnction() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.toString();
		}

	}
    
    /*
	 * 
	 */
    /***
	 * 功能描述：统计某一个表的记录数其中strQuery为表名。
	 * @param tableName
	 * @return int
	 * 
	 * 作者：史晓林 创建时间：2014-02-24 修改时间 2014-02-24
	 */
	public int getRecordCount(String tableName) {
		String strSQL = "select count(*) from (" + tableName + ")";
		ResultSet rs = null;
		System.out.println(strSQL);
		if (this.conn == null) {
			System.out.println("还没有连接数据库!");
			return 0;
		}
		try {
			Statement stm = this.conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = stm.executeQuery(strSQL);
			if (rs.next()) {
				return rs.getInt(1);
			} else {
				return 0;
			}

		} catch (Exception e) {
			System.out.println("错误：" + e.getMessage());
			return 0;
		}
	}

	/***
	 * 功能描述：执行某一个查询SQL语句。返回ResultSet的结果集。
	 * @param strSQL
	 * @return ResultSet
	 * 
	 * 作者：史晓林 创建时间：2014-02-24 修改时间 2014-02-24
	 */
	public ResultSet openSelect(String sql) throws SQLException {
		Statement stmt;
		stmt = this.conn.createStatement();
		ResultSet rset = stmt.executeQuery(sql);
		return rset;
	}

	/***
	 * 功能描述：执行某一个查询SQL语句。返回ResultSet的结果集。
	 * @param strSQL
	 * @return ResultSet
	 * 
	 * 作者：史晓林 创建时间：2014-02-24 修改时间 2014-02-24
	 */
	public ResultSet openSQL(String strSQL) {
		ResultSet rs = null;
		System.out.println(strSQL);
		if (this.conn == null) {
			System.out.println("还没有连接数据库!");
			return null;
		}
		try {
			Statement stm = this.conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = stm.executeQuery(strSQL);//查询SQL执行
		} catch (Exception e) {
			System.out.println("错误：" + e.getMessage());
		}
		return rs;
	}

	/***
	 * 功能描述：执行某一个非查询SQL语句。返回记录数。
	 * @param strSQL
	 * @return int
	 * 
	 * 作者：史晓林 创建时间：2014-02-24 修改时间 2014-02-24
	 */
	public int ExecuteSQL(String strSQL) throws Exception {
		int result = 0;
		System.out.println(strSQL);
		try {
			Statement stm = this.conn.createStatement();
			result = stm.executeUpdate(strSQL);//非查询SQL执行
		} catch (Exception e) {
			System.out.println("错误：" + e.getMessage());
			throw new Exception(e);
		}
		return result;
	}

	/***
	 * 功能描述：执行某一个查询SQL语句。返回ResultSet的结果集。
	 * @param sql,Object[] data
	 * @return
	 * 
	 * 作者：史晓林 创建时间：2014-02-24 修改时间 2014-02-24
	 */
	public ResultSet openSQL2(String sql, Object[] data) throws SQLException {
		PreparedStatement stmt = this.conn.prepareStatement(sql);
		stmt.clearParameters();
		for (int i = 0; data != null && i < data.length; i++) {
			System.out.print("这里的SQL语句是 =" + i + " " + sql);
			System.out.println("这里的数据是 " + i + " " + data[i]);
			stmt.setObject(i + 1, data[i]);
		}
		ResultSet rset = stmt.executeQuery();//查询SQL执行
		return rset;
	}
	
	/***
	 * 功能描述： 执行某一个非查询SQL语句。返回记录数。
	 * @param sql,Object[] data
	 * @return int
	 * 
	 * 作者：史晓林 创建时间：2014-02-24 修改时间 2014-02-24
	 */
	public int ExecuteUpdate(String strSQL, Object[] obj) throws Exception {
		int result = 0;
		PreparedStatement stmt = this.conn.prepareStatement(strSQL);
		stmt.clearParameters();
		try {
			System.out.println("the aql is =" + strSQL);
			for (int i = 0; obj != null && i < obj.length; i++) {
				System.out.println("data is " + obj[i]);
				stmt.setObject(i + 1, obj[i]);
			}
			result = stmt.executeUpdate();//非查询SQL执行
		} catch (Exception e) {
			System.out.println("错误：" + e.getMessage());
			throw new Exception(e);
		}
		return result;
	}

	/***
	 * 功能描述：执行某一个查询SQL语句。返回一个对象。
	 * @param sql,Object[] data
	 * @return
	 * 
	 * 作者：史晓林 创建时间：2014-02-24 修改时间 2014-02-24
	 */
	public Object[][] openSQL(String sql, Object[] data) throws SQLException {
		PreparedStatement stmt = this.conn.prepareStatement(sql);
		stmt.clearParameters();
		System.out.print("the aql is =" + sql);
		for (int i = 0; data != null && i < data.length; i++) {
			System.out.println("data is " + data[i]);
			stmt.setObject(i + 1, data[i]);
		}
		System.out.println(sql);
		ResultSet rset = stmt.executeQuery();
		ResultSetMetaData rsm = rset.getMetaData();
		int col = rsm.getColumnCount();
		List<Object> list = new ArrayList<Object>();
		// Each element of list contains a record of resultset
		while (rset.next()) {
			list.add(getLine(rset, col));
		}
		if (list.size() == 0 || col == 0) {
			stmt.close();
			return null;
		}
		// Construct box as a data matrix
		Object[][] box = new Object[list.size() + 1][col];
		for (int i = 1; i <= col; i++) {
			try {
				box[0][i - 1] = (Object) rsm.getColumnLabel(i);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				box[0][i - 1] = "";
			}
			// System.out.println(box[0][i-1]);
		}
		stmt.close();
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < col; j++) {
				box[i + 1][j] = ((Object[]) list.get(i))[j];
			}
		}
		return box;
	}

	/***
	 * 功能描述：得到第一条记录的记录集。
	 * @param ResultSet,int
	 * @return
	 * 
	 * 作者：史晓林 创建时间：2014-02-24 修改时间 2014-02-24
	 */
	private Object[] getLine(ResultSet rs, int col) {
		Object[] obj = new Object[col];
		try {
			for (int i = 0; i < col; i++) {
				obj[i] = rs.getObject(i + 1);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return obj;
	}

	/***
	 * 功能描述：得到某一条记录的记录集，并返回所操作的个数。
	 * @param strSQL,obj,types
	 * @return
	 * 
	 * 作者：史晓林 创建时间：2014-02-24 修改时间 2014-02-24
	 */
	public int ExecuteUpdate(String strSQL, Object[] obj, int[] types)
			throws Exception {
		int result = 0;
		PreparedStatement stmt = this.conn.prepareStatement(strSQL);
		stmt.clearParameters();
		try {
			System.out.println("the aql is =" + strSQL);
			for (int i = 0; obj != null && i < obj.length; i++) {
				System.out.println("data is " + obj[i]);
				stmt.setObject(i + 1, obj[i], types[i]);
			}
			result = stmt.executeUpdate();
			this.conn.commit();
		} catch (Exception e) {
			System.out.println("错误：" + e.getMessage());
			throw new Exception(e);
		}
		return result;
	}

	/***
	 * 功能描述：执行某一个非查询SQL语句。返回记录数
	 * @param sql
	 * @return
	 * 
	 * 作者：史晓林 创建时间：2014-02-24 修改时间 2014-02-24
	 */
	public boolean update(String sql) {
		boolean f = true;
		Statement select;
		try {

			select = this.conn.createStatement();
			select.executeUpdate(sql);

		} catch (Exception e) {
			e.printStackTrace();
			f = false;
		}

		return f;

	}

	/***
	 * 功能描述：创建DB数据库
	 * @param database
	 * @return
	 * 
	 * 作者：史晓林 创建时间：2014-02-24 修改时间 2014-02-24
	 */
	public boolean createdatabase(String database) {
		boolean f = true;
		Statement select;
		try {

			select = this.conn.createStatement();
			select.executeUpdate("create database " + database + "");
			select.executeBatch();

		} catch (Exception e) {
			e.printStackTrace();
			f = false;
		}

		return f;

	}

	/***
	 * 功能描述：删除DB数据库
	 * @param database
	 * @return
	 * 
	 * 作者：史晓林 创建时间：2014-02-24 修改时间 2014-02-24
	 */
	public boolean deledatabase(String database) {
		boolean f = true;
		Statement select;
		try {

			select = this.conn.createStatement();
			select.executeUpdate("drop database " + database + "");
			select.executeBatch();

		} catch (Exception e) {
			e.printStackTrace();
			f = false;
		}

		return f;

	}

	/***
	 * 功能描述：备份DB数据库
	 * @param database , backupPath文件备份的路径
	 * @return
	 * 
	 * 作者：史晓林 创建时间：2014-02-24 修改时间 2014-02-24
	 */
	public boolean backupDB(String database , String backupPath) {
		boolean f = true;
		Statement select;
		try {

			select = this.conn.createStatement();
			String sql = "USE MASTER";
			select.executeUpdate(sql);
			sql = "ALTER DATABASE " + database
					+ " SET OFFLINE WITH ROLLBACK IMMEDIATE";
			select.executeUpdate(sql);
			sql = "RESTORE DATABASE " + database
					+ " FROM DISK = '" + backupPath + "'";
			select.executeUpdate(sql);
			sql = "ALTER DATABASE " + database
					+ " SET ONLINE WITH ROLLBACK IMMEDIATE";
			select.executeUpdate(sql);

		} catch (Exception e) {
			e.printStackTrace();
			f = false;
		}

		return f;

	}
}
