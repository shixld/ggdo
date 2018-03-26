package cn.ggdo.system.framework.io;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.alibaba.fastjson.JSON;
import cn.ggdo.system.framework.lang.StringUtil;

/**
 * 类名：JsonUtil 
 * 版本：1.0.0
 * 用途说明：json 操作工具类
 * 业务区分(IO处理工具)
 * 依赖 fastJson.jar
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public class JsonUtil {

	/**
	 * 将JSON解析成map
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> json2Map(final String json) {
		return (Map<String, Object>) JSON.parse(json);
	}

	/**
	 * 将JSON解析成对象
	 */
	public static final <T> T json2Object(final String json, final Class<T> c) {
		return JSON.parseObject(json, c);
	}

	/**
	 * 将字符串包装成json数组
	 */
	public static String warpJson2ListJson(final String json) {
		String jsonStr = json;
		if (!StringUtil.isNullOrBlank(json)) {
			if (!json.startsWith("[")) {
				jsonStr = "[" + json + "]";
			}
		}
		return jsonStr;
	}

	/**
	 * 将JSON解析成对象list
	 */
	public static final <T> List<T> json2List(final String json, final Class<T> c) {
		String jsonStr = json;
		if (!StringUtil.isNullOrBlank(json)) {
			if (!json.startsWith("[")) {
				jsonStr = "[" + json + "]";
			}
			return JSON.parseArray(jsonStr, c);
		} else {
			return new ArrayList<T>();
		}
	}

	/**
	 * 将对象转换成json
	 */
	public static final String object2Json(final Object entity) {
		return JSON.toJSONString(entity);
	}

	// ////////////////////////jackson///////////////////////////////////////////////////
	// ObjectMapper 线程安全具有缓存机制，重用可显著提高效率，实际使用中可设为全局公用
	// @Getter
	// private static ObjectMapper mapper = new ObjectMapper();
	//
	// /**
	// * 将JSON解析成map
	// */
	// @SuppressWarnings("unchecked")
	// public static Map<String, Object> json2Map(final String json) {
	// Map<String, Object> map = null;
	// try {
	// map = mapper.readValue(json, Map.class);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return map;
	// }
	//
	// /**
	// * 将JSON解析成对象
	// */
	// public final Object json2Object(final String json, final Class<T> c) {
	// Object obj = null;
	// try {
	// obj = mapper.readValue(json, c);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return obj;
	// }
	//
	// /**
	// * 将字符串包装成json数组
	// */
	// public static String warpJson2ListJson(final String json) {
	// String jsonStr = "";
	// if (!Strings.isNullOrEmpty(json)) {
	// if (!json.startsWith("[")) {
	// jsonStr = "[" + json + "]";
	// }
	// }
	// return jsonStr;
	// }
	//
	// /**
	// * 将JSON解析成对象list
	// */
	// public final List<T> json2List(final String json) {
	// String jsonStr = "";
	// if (!Strings.isNullOrEmpty(json)) {
	// if (!json.startsWith("[")) {
	// jsonStr = "[" + json + "]";
	// }
	// List<T> list = null;
	// try {
	// list = mapper.readValue(jsonStr, new TypeReference<List<T>>() {});
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return list;
	// } else {
	// return null;
	// }
	// }

	/**
     * json字符串转为bean
     * 
     * @auther tianxiaoyu
     * @date 2017-3-20 上午09:33:40
     * @param <T>
     * @param jsonString
     *            json字符串
     * @param beanCalss
     *            转换成的bean
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T json2Bean(String jsonString, Class<T> beanClass) {
        T bean = null;
        if (!StringUtil.isNullOrBlank(jsonString)) {
            JSONObject jsonObject = JSONObject.fromObject(jsonString);
            bean = (T) JSONObject.toBean(jsonObject, beanClass);
        }
        return bean;
    }

    /**
     * json字符串转为List的bean
     * 
     * @auther tianxiaoyu
     * @date 2017-3-20 上午09:33:40
     * @param <T>
     * @param jsonString
     *            json字符串
     * @param beanCalss
     *            转换成的bean
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> json2ListBean(String jsonString,
            Class<T> beanClass) {
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        JSONObject jsonObject;
        T bean;
        int size = jsonArray.size();
        List<T> list = new ArrayList<T>(size);

        for (int i = 0; i < size; i++) {

            jsonObject = jsonArray.getJSONObject(i);
            bean = (T) JSONObject.toBean(jsonObject, beanClass);
            list.add(bean);

        }
        return list;
    }

    public static List<Map<String, Object>> parseJSON2List(String jsonStr) {
        JSONArray jsonArr = JSONArray.fromObject(jsonStr);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Iterator<JSONObject> it = jsonArr.iterator();
        while (it.hasNext()) {
            JSONObject json2 = it.next();
            list.add(parseJSON2Map(json2.toString()));
        }
        return list;
    }

    public static Map<String, Object> parseJSON2Map(String jsonStr) {
        Map<String, Object> map = new HashMap<String, Object>();
        //最外层解析
        JSONObject json = JSONObject.fromObject(jsonStr);
        for (Object k : json.keySet()) {
            Object v = json.get(k);
            //如果内层还是数组的话，继续解析
            if (v instanceof JSONArray) {
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                Iterator<JSONObject> it = ((JSONArray) v).iterator();
                while (it.hasNext()) {
                    JSONObject json2 = it.next();
                    list.add(parseJSON2Map(json2.toString()));
                }
                map.put(k.toString(), list);
            } else {
                map.put(k.toString(), v);
            }
        }
        return map;
    }

    public static Map<String, String> parseJSON2StringMap(String jsonStr) {
        Map<String, String> map = new HashMap<String, String>();
        //最外层解析
        JSONObject json = JSONObject.fromObject(jsonStr);
        for (Object k : json.keySet()) {
            Object v = json.get(k);
            map.put(k.toString(), v.toString());
        }
        return map;
    }
    
    public static List<Map<String, Object>> getListByUrl(String url) {
        try {
            //通过HTTP获取JSON数据
            InputStream in = new URL(url).openStream();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return parseJSON2List(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, Object> getMapByUrl(String url) {
        try {
            //通过HTTP获取JSON数据
            InputStream in = new URL(url).openStream();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return parseJSON2Map(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	// public static void main(String[] args) {
	// String s = "[{\"taskItemId\":\"\",\"naming\":\"\",\"startTime\":\"\",\"endTime\":\"\",\"weight\":\"\"}]";
	// IoJsonUtil<ProjectPlanDetailForm> u = new IoJsonUtil<ProjectPlanDetailForm>();
	// List<ProjectPlanDetailForm> m = u.json2List(s);
	//
	// }
}
