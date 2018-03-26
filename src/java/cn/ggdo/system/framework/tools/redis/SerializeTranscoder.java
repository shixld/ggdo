package cn.ggdo.system.framework.tools.redis;

import java.io.Closeable;

import org.apache.log4j.Logger;

/**
 * 类名：SerializeTranscoder 
 * 版本：1.0.0
 * 用途说明：缓存处理
 * 业务区分(redis)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public abstract class SerializeTranscoder {

    protected static Logger logger = Logger
            .getLogger(SerializeTranscoder.class);

    public abstract byte[] serialize(Object value);

    public abstract Object deserialize(byte[] in);

    public void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                logger.info("Unable to close " + closeable, e);
            }
        }
    }
}