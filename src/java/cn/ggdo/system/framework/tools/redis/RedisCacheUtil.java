package cn.ggdo.system.framework.tools.redis;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import cn.ggdo.system.framework.readconfig.ResourceUtil;

/**
 * 类名：RedisCacheUtil 
 * 版本：1.0.0
 * 用途说明：缓存处理池
 * 业务区分(redis)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public class RedisCacheUtil {
	private static JedisPool pool = null;
	private static Log logger = LogFactory.getLog(RedisCacheUtil.class);
    
    /**
     * 构建redis连接池
     * 
     * @param ip
     * @param port
     * @return JedisPool
     */
    public static JedisPool getPool() {
        if (pool == null) {
            synchronized(RedisCacheUtil.class){
            	if (pool == null) {
            		JedisPoolConfig config = new JedisPoolConfig();
                    //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
                    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
                    config.setMaxActive(500);
                    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
                    config.setMaxIdle(5);
                    //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
                    config.setMaxWait(1000 * 100);
                    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
                    config.setTestOnBorrow(true);
                    ResourceUtil configFile = new ResourceUtil("/adapterProperties/redisConfig.properties");
            		String host = configFile.getProPerties("redis.host");
            		int port = Integer.valueOf(configFile.getProPerties("redis.port"));
            		int timeout = Integer.valueOf(configFile.getProPerties("redis.timeout"));
            		String password = configFile.getProPerties("redis.password");
            		if(StringUtils.isBlank(password)){
            			pool = new JedisPool(config, host, port);
            		}else{
            			pool = new JedisPool(config, host, port, timeout, password);
            		}
            	}
            }
        }
        return pool;
    }
    
    /**
     * 返还到连接池
     * 
     * @param pool 
     * @param redis
     */
    public static void returnResource(JedisPool pool, Jedis redis) {
        if (redis != null) {
            pool.returnResource(redis);
        }
    }
    
    /**
     * 设置数据
     * 
     * @param key value
     * @return
     */
    public static String putObject(String key , Object object){
        String returnStr = "";
        JedisPool pool = null;
        Jedis jedis = null;
        ObjectsTranscoder serializeUtil = new ObjectsTranscoder();
        try {
            pool = getPool();
            jedis = pool.getResource();
            returnStr = jedis.set(key.getBytes(),  serializeUtil.serialize(object));
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            logger.error(e.getMessage(), e);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
        
        return returnStr;
    }
    
    /**
     * 获取数据
     * 
     * @param key
     * @return
     */
    public static Object getObject(String key){
        byte[] value = null;
        JedisPool pool = null;
        ObjectsTranscoder serializeUtil = new ObjectsTranscoder();
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            value = jedis.get(key.getBytes());
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            logger.error(e.getMessage(), e);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
        if(value == null) return null;
        return serializeUtil. deserialize(value);
    }

    /**
     * 设置数据
     * 
     * @param key value
     * @return
     */
    public static String putList(String key , List list){
        String returnStr = "";
        JedisPool pool = null;
        Jedis jedis = null;
        ListTranscoder serializeUtil = new ListTranscoder();
        try {
            pool = getPool();
            jedis = pool.getResource();
            returnStr = jedis.set(key.getBytes(),  serializeUtil.serialize(list));
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            logger.error(e.getMessage(), e);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
        
        return returnStr;
    }
    
    /**
     * 获取数据
     * 
     * @param key
     * @return
     */
    public static List getList(String key){
        byte[] value = null;
        JedisPool pool = null;
        ListTranscoder serializeUtil = new ListTranscoder();
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            value = jedis.get(key.getBytes());
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            logger.error(e.getMessage(), e);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
        if(value == null) return null;
        return serializeUtil.deserialize(value);
    }
   
    /**delete a key**/
    public boolean del(String key)
   {
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.del(key.getBytes())>0;
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            logger.error(e.getMessage(), e);
            return false;
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
          
   }
}
