package cn.ggdo.system.framework.adapter.mongodb;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.DBTCPConnector;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

/**
 * 类名：SearchMongoDb
 * 版本：1.0.0
 * 用途说明：Mongo数据库查询业务
 * 业务区分(Mongo数据库查询业务)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public class SearchMongoDb {

	private Mongo mongo;

	private DBTCPConnector conn;

	private DB db;

	/***************************************************************************
	 * 创建链接
	 * 
	 * @param ip
	 * @param port
	 * @param database
	 * @throws UnknownHostException
	 */
	public SearchMongoDb(String ip, int port, String database)
			throws UnknownHostException {
		// mongo = new Mongo(ip + ":" + port);
		mongo = new Mongo(ip, port);

		System.out.println("mongo=" + mongo);

		db = mongo.getDB(database);

		System.out.println("DB=" + db);

		conn = mongo.getConnector();

		System.out.println("DBTCPConnector: " + conn.getServerAddressList());

		boolean auth = db.authenticate("shixld", "2803934".toCharArray());

		System.out.println("auth=" + auth);
	}

	public void GetAllCollection() throws Exception {

		// 获取db里面的collection(表)

		Set<String> names = db.getCollectionNames();

		for (String name : names) {

			System.out.println("CollectionName: " + name);

			DBCollection coll = db.getCollection(name);

			System.out.println("CollectionCount=" + coll.count());

			DBCursor cursor = coll.find();

			while (cursor.hasNext()) {

				System.out.println("DBObject=" + cursor.next());

			}

			List<DBObject> objs = coll.getIndexInfo();

			for (DBObject obj : objs) {

				System.out.println("IndexInfo=" + obj);

			}

			System.out.println("==============");
			// String code = "";
			// for(int i=0;i<names.length;i++)
			// code += "db.getCollection('" + names[i] + "').drop();";
			// db.eval(code);
			// db.getCollection(name).drop();

		}

		// 获取表结果

	}

	public void funCollection() throws Exception {

		if (db.isAuthenticated()) {

			// for (String coll : db.getCollectionNames()) {
			//
			// System.out.println("collection=" + coll);
			//
			// }

			boolean flag = db.collectionExists("prns_info");
			if (!flag) {
				// 如果不存在，就插入
				DBObject obj = (BasicDBObject) JSON
						.parse("{'name':'shixld','age':'25','company':'china'}");
				db.createCollection("prns_info", obj);
				System.out.println("创建集合 prns_info");
				// 这里就是创建集合的时候直接创建了DB
			} else {
				System.out.println("集合 prns_info 已经存在。");
			}
		}

	}

	// public void funInsert(List<PRNSEntity> list) throws Exception {
	//
	// if (db.isAuthenticated()) {
	// DBCollection coll = db.getCollection("prns_info");
	// if (list != null)
	// for (int i = 0; i < list.size(); i++) {
	// PRNSEntity bean = list.get(i);
	//
	// BasicDBObject doc = new BasicDBObject();
	// doc.put("V_NAME", bean.getvName() != null ? bean.getvName()
	// : "");
	// doc.put("V_LABEL", bean.getvLabel() != null ? bean
	// .getvLabel() : "");
	// doc.put("BASE_UOM", bean.getBaseUOM() != null ? bean
	// .getBaseUOM() : "");
	// doc.put("PRODUCTTYPE", bean.getProductType() != null ? bean
	// .getProductType() : "");
	// doc.put("UPDATE_DATE", bean.getUpdateDate() != null ? bean
	// .getUpdateDate() : "");
	//
	// if (bean.getpHInfo() != null) {
	// BasicDBObject ph_info = new BasicDBObject();
	// ph_info.put("HIERARCHY_CODE", bean.getpHInfo()
	// .getHierarchyCode() != null ? bean.getpHInfo()
	// .getHierarchyCode() : "");
	// ph_info.put("DESCRIPTION", bean.getpHInfo()
	// .getDescription() != null ? bean.getpHInfo()
	// .getDescription() : "");
	// ph_info.put("UPDATE_DATE", bean.getpHInfo()
	// .getUpdateDate() != null ? bean.getpHInfo()
	// .getUpdateDate() : "");
	// doc.put("PH_INFO", ph_info);
	// }
	//
	// if (bean.getSalesInfo() != null) {
	// BasicDBObject sales_info = new BasicDBObject();
	// sales_info.put("SALES_ORG", bean.getSalesInfo()
	// .getSalesOrg() != null ? bean.getSalesInfo()
	// .getSalesOrg() : "");
	// sales_info.put("SALES_STATUS", bean.getSalesInfo()
	// .getSalesStatus() != null ? bean.getSalesInfo()
	// .getSalesStatus() : "");
	// sales_info.put("UPDATE_DATE", bean.getSalesInfo()
	// .getUpdateDate() != null ? bean.getSalesInfo()
	// .getUpdateDate() : "");
	// doc.put("SALE_INFO", sales_info);
	// }
	//
	// if (bean.getSapInfo() != null) {
	// BasicDBObject sap_info = new BasicDBObject();
	// sap_info.put("ECC_STATUS", bean.getSapInfo()
	// .getEccStatus() != null ? bean.getSapInfo()
	// .getEccStatus() : "");
	// sap_info.put("ECC_DESC",
	// bean.getSapInfo().getEccDesc() != null ? bean
	// .getSapInfo().getEccDesc() : "");
	// sap_info.put("CRM_STATUS", bean.getSapInfo()
	// .getCrmStatus() != null ? bean.getSapInfo()
	// .getCrmStatus() : "");
	// sap_info.put("CRM_DESC",
	// bean.getSapInfo().getCrmDesc() != null ? bean
	// .getSapInfo().getCrmDesc() : "");
	// sap_info.put("FLAG",
	// bean.getSapInfo().getFlag() != null ? bean
	// .getSapInfo().getFlag() : 0);
	// doc.put("SAP_STATUS", sap_info);
	// }
	// coll.insert(doc);
	// //WriteResult wr = coll.insert(doc);
	// //System.out.println("WriteResult=" + wr);
	// }
	//
	// }
	//
	// }

	public void funRemove() throws Exception {

		if (db.isAuthenticated()) {

			DBCollection coll = db.getCollection("prns_info");

			DBObject obj = new BasicDBObject();

			// obj.put("V_NAME","0000000-000060");

			obj.put("V_LABEL", "ThinkCentre M57shixld");

			System.out.println("WriteResult=" + coll.remove(obj));

		}

	}

	public void funUpdate() throws Exception {

		if (db.isAuthenticated()) {

			DBCollection coll = db.getCollection("prns_info");

			DBObject whereObj = new BasicDBObject();

			whereObj.put("V_NAME", "0000000-000060");

			BasicDBObject doc = new BasicDBObject();
			doc.put("V_NAME", "0000000-000060");
			doc.put("V_LABEL", "ThinkCentre M57");
			doc.put("BASE_UOM", "EA");
			doc.put("PRODUCTTYPE", "ZREV");
			doc.put("UPDATE_DATE", "2012-05-06 10:02:59");

			BasicDBObject ph_info = new BasicDBObject();
			ph_info.put("HIERARCHY_CODE", "11TC1TMM570607510R");
			ph_info.put("DESCRIPTION", "Relationship");
			ph_info.put("UPDATE_DATE", "2012-05-06 10:02:59");
			doc.put("PH_INFO", ph_info);

			BasicDBObject sales_info = new BasicDBObject();
			sales_info.put("SALES_ORG", "CA10");
			sales_info.put("SALES_STATUS", "12");
			sales_info.put("UPDATE_DATE", "2012-05-06 10:02:59");
			doc.put("SALE_INFO", sales_info);

			BasicDBObject sap_info = new BasicDBObject();
			sap_info.put("ECC_STATUS", "finial");
			sap_info.put("ECC_DESC", "Thinkpad");
			sap_info.put("CRM_STATUS", "");
			sap_info.put("CRM_DESC", "Thinkpad");
			sap_info.put("FLAG", "1");
			doc.put("SAP_STATUS", sap_info);
			System.out.println("WriteResult=" + coll.update(whereObj, doc));

		}

	}

	public void funQuery() throws Exception {

		if (db.isAuthenticated()) {

			DBCollection coll = db.getCollection("prns_info");

			DBObject obj = new BasicDBObject();

			// obj.put("V_NAME","0000000-000060");

			BasicDBObject sap_info = new BasicDBObject();

			sap_info.put("FLAG", "1");

			// obj.put("V_NAME","0000000-000060");

			obj.put("SAP_STATUS", sap_info);

			DBObject orderBy = new BasicDBObject();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("V_NAME", 1);
			orderBy.putAll(map);

			DBCursor cursor = coll.find(obj).sort(orderBy).skip(1000).limit(
					1000);

			while (cursor.hasNext()) {

				System.out.println("DBObject=" + cursor.next());

			}

		}

	}

	public void funClose() {
		mongo.close();
	}

	public static void main(String[] args) {
		try {
			SearchMongoDb monogodb = new SearchMongoDb("127.0.0.1", 27017,
					"prns");

			// monogodb.funCollection();
			monogodb.funQuery();
			monogodb.funClose();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
