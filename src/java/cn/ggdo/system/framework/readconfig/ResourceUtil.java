package cn.ggdo.system.framework.readconfig;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 类名：ResourceUtil 
 * 版本：1.0.0
 * 用途说明：读取filePath.properties配置文件相关的配置
 * 业务区分(配置文件)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public class ResourceUtil {

    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(getClass());
    private Properties props = new Properties();
    private InputStream in = null;
    
    public ResourceUtil(String path) {
        this.in = new BufferedInputStream(getClass().getResourceAsStream(path));
        try {
            this.props.load(this.in);
        }
        catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e) ;
        }
    }

    public String getProPerties(String key) {
        return this.props.getProperty(key);
    }
}
