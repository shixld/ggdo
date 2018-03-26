package cn.ggdo.system.framework.adapter.mongodb;

/**
 * 类名：MongoDBinfo
 * 版本：1.0.0
 * 用途说明：Mongo数据库处理
 * 业务区分(Mongo数据库处理)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public class MongoDBinfo {

    private String ip = "127.0.0.1";
    
    private String port = "27017";
    
    private String defaultDB = "prns";
    
    private String defaultCol = "";

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDefaultDB() {
        return defaultDB;
    }

    public void setDefaultDB(String defaultDB) {
        this.defaultDB = defaultDB;
    }

    public String getDefaultCol() {
        return defaultCol;
    }

    public void setDefaultCol(String defaultCol) {
        this.defaultCol = defaultCol;
    }
    
    public String toString(){
        StringBuffer strb = new StringBuffer();
        strb.append("ip:" + ip);
        strb.append(" , port:" + port);
        strb.append(" , defaultDB:" + defaultDB);
        strb.append(" , defaultCol:" + defaultCol);
        return strb.toString();
    }
}

