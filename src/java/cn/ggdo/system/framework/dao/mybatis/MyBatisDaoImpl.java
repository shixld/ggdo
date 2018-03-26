package cn.ggdo.system.framework.dao.mybatis;

import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * 类名：MyBatisDaoImpl 
 * 版本：1.0.0
 * 用途说明：配置MyBatis的数据session连接
 * 业务区分(MyBatis框架)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public class MyBatisDaoImpl extends SqlSessionDaoSupport {
    private TimeFactory timeFactory;
    
    public void setTimeFactory(TimeFactory timeFactory) {
        this.timeFactory = timeFactory;
    }

    @Override
	protected void checkDaoConfig() {
        super.checkDaoConfig();
        
        if(timeFactory==null){
            throw new IllegalArgumentException("'timeFactory' is required");
        }
    }
    
}