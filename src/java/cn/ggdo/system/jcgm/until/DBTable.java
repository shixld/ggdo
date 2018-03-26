package cn.ggdo.system.jcgm.until;

import java.util.List;

public class DBTable {
	private String tableName;
	private String tableComment;
	private List<DBColumns> dbColumnss;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableComment() {
		return tableComment;
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}

	public List<DBColumns> getDbColumnss() {
		return dbColumnss;
	}

	public void setDbColumnss(List<DBColumns> dbColumnss) {
		this.dbColumnss = dbColumnss;
	}
}
