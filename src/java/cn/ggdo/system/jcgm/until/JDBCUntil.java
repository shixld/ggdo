﻿package cn.ggdo.system.jcgm.until;import java.sql.Connection;import java.sql.DriverManager;import java.sql.SQLException;public class JDBCUntil {	private String driverString = null;	private String username = null;	private String password = null;	private String url = null;	public JDBCUntil(String driverString, String url, String user, String password) {		this.driverString = driverString;		this.username = user;		this.password = password;		this.url = url;	}	public Connection getConnection() throws ClassNotFoundException, SQLException {		Class.forName(driverString); // 指定JDBC数据库驱动程序		return DriverManager.getConnection(url, username, password);	}}