package cn.ggdo.system.framework.adapter.mongodb;


import java.util.Iterator;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

/**
 * 类名：ThreadInsertData
 * 版本：1.0.0
 * 用途说明：Mongo数据库批量插入业务
 * 业务区分(Mongo数据库批量插入业务)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public class ThreadInsertData { 
    public static void main(String[] args) {
        MongoDBinfo  dbinfo = new MongoDBinfo();
        dbinfo.setIp("127.0.0.1");
        dbinfo.setPort("27017");
        dbinfo.setDefaultDB("prns");
        dbinfo.setDefaultCol("prns_info");
        
        try 
        {
        	
        	boolean defaultDbflag = false;//判断DB是否存在
            boolean defaultColflag = false;//判断collections是否存在
            
            /**
             * 连接MongoDB
             */
            Mongo mongo = new Mongo(dbinfo.getIp() + ":" + dbinfo.getPort());
            DB adminDb = mongo.getDB("prns");
            
            
            
            /***
             * 取出所有的DB信息判断是否该DB已经存在,collections是否存在
             */
            List<String> listDb = mongo.getDatabaseNames();
            for(String oneDb : listDb){
                if(oneDb.equals(dbinfo.getDefaultDB())){
                    defaultDbflag = true; 
                    Iterator<String> iterCol = adminDb.getCollectionNames().iterator();
                    while(iterCol.hasNext()){
                        String colName = iterCol.next();
                        if(colName.equals(dbinfo.getDefaultCol())){
                            defaultColflag = true;
                        }
                    }
                    break;
                }
            }
            
            /**
             * 如果数据库不存在创建数据库
             */
            if(!defaultDbflag){ 
                CommandResult crs = adminDb.command("use " + dbinfo.getDefaultDB()); 
                System.out.println(crs.toString());  // { "errmsg" : "no such cmd: use roothomesDB" , "bad cmd" : { "use roothomesDB" : true} , "ok" : 0.0}
                //这里是无法创建DB的，可以再创建集合的时候直接创建DB
            }
            
            /**
             * 如果collections不存在创建collections
             */
            if(!defaultColflag){
                DB db = mongo.getDB(dbinfo.getDefaultDB()); 
                DBObject obj =  (BasicDBObject)JSON.parse("{'name':'shixld','age':'25','company':'china'}");
                //判断collections是否存在
                boolean flag = db.collectionExists(dbinfo.getDefaultCol()); 
                if(!flag){
                	//如果不存在，就插入
                    db.createCollection(dbinfo.getDefaultCol(),obj);
                    System.out.println("创建集合"+ dbinfo.getDefaultCol());
                    //这里就是创建集合的时候直接创建了DB
                }else{
                    System.out.println("集合"+dbinfo.getDefaultCol()+"已经存在。");
                }
                //db.roothomesCol.remove({ }); //情况集合
                //db.roothomesCol.find().limit(100); //有限制的查询集合
            }else{
                 DB db = mongo.getDB(dbinfo.getDefaultDB()); 
            	 DBCollection coll = db.getCollection(dbinfo.getDefaultCol());
                 for (long i = 1; i <= 50; i++) {
                 	BasicDBObject doc = new BasicDBObject();
                 	doc.put("V_NAME","0000000-000060");
                 	doc.put("V_LABEL","ThinkCentre M57");
                 	doc.put("BASE_UOM","EA");
                 	doc.put("PRODUCTTYPE","ZREV");
                 	doc.put("UPDATE_DATE","2012-05-06 10:02:59");
                 	
                 	BasicDBObject ph_info = new BasicDBObject();
                 	ph_info.put("HIERARCHY_CODE","11TC1TMM570607510R");
                 	ph_info.put("DESCRIPTION","Relationship");
                 	ph_info.put("UPDATE_DATE","2012-05-06 10:02:59");
                 	doc.put("PH_INFO", ph_info);
                 	
                 	BasicDBObject sales_info = new BasicDBObject();
                 	sales_info.put("SALES_ORG","CA10");
                 	sales_info.put("SALES_STATUS","12");
                 	sales_info.put("UPDATE_DATE","2012-05-06 10:02:59");
                 	doc.put("SALE_INFO", sales_info);
                 	
                 	BasicDBObject sap_info = new BasicDBObject();
                 	sap_info.put("ECC_STATUS","2012-05-06 10:02:59");
                 	sap_info.put("ECC_DESC","2012-05-06 10:02:59");
                 	sap_info.put("CRM_STATUS","2012-05-06 10:02:59");
                 	sap_info.put("CRM_DESC","2012-05-06 10:02:59");
                 	sap_info.put("FLAG","2012-05-06 10:02:59");
                 	doc.put("SAP_STATUS", sap_info);
                 	coll.insert(doc);
                 }
            }
            mongo.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
