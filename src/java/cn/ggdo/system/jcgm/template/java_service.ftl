package ${PACKAGE_NAME};

import java.util.List;
import java.util.Map;

import cn.ggdo.system.framework.exception.BusinessException;
import ${BEAN_PACKAGE_NAME};

/**
 * 类名：${BEAN_NAME}Service 
 * 版本：1.0.0
 * 用途说明：实现${BEAN_NAME}业务的服务接口层
 * 业务区分(Service业务层)
 * 依赖：spring相关jar包
 * 序号       日期                  修改版本       更新者      内容
 *   1      2017-10-11      V1.0.0              史晓林     初版
 */
public interface I${BEAN_NAME}Service {

/**
 * 方法名：lookForAll
 * 输入参数：无
 * 返回值：List<${BEAN_NAME}>
 * 功能描述：查询全部
 * 创建时间：2017-10-11
 * 序号       日期                  修改版本       更新者      内容
 *   1      2017-10-11      V1.0.0              史晓林     初版
 */
	public List<${BEAN_NAME}> lookForAll() throws BusinessException;
	
/**
 * 方法名：lookForAll
 * 输入参数：Map<String, Object> map
 * 返回值：List<${BEAN_NAME}>
 * 功能描述：按条件分页查询数据
 * 创建时间：2017-10-11
 * 序号       日期                  修改版本       更新者      内容
 *   1      2017-10-11      V1.0.0              史晓林     初版
 */
	public List<${BEAN_NAME}> lookForAllByList(Map<String, Object> map) throws BusinessException;
	
/**
 * 方法名：lookForAllByCount
 * 输入参数：Map<String, Object> map
 * 返回值：int
 * 功能描述：按条件查询数据总数
 * 创建时间：2017-10-11
 * 序号       日期                  修改版本       更新者      内容
 *   1      2017-10-11      V1.0.0              史晓林     初版
 */
	public int lookForAllByCount(Map<String, Object> map) throws BusinessException;
	
/**
 * 方法名：saveBean
 * 输入参数：${BEAN_NAME} bean
 * 返回值：int
 * 功能描述：增加新数据
 * 创建时间：2017-10-11
 * 序号       日期                  修改版本       更新者      内容
 *   1      2017-10-11      V1.0.0              史晓林     初版
 */
	public int saveBean(${BEAN_NAME} bean) throws BusinessException ;
	
/**
 * 方法名：saveAllBean
 * 输入参数：List<${BEAN_NAME}>
 * 返回值：int
 * 功能描述：批量增加新数据
 * 创建时间：2017-10-11
 * 序号       日期                  修改版本       更新者      内容
 *   1      2017-10-11      V1.0.0              史晓林     初版
 */
	public int saveAllBean(List<${BEAN_NAME}> beanList) throws BusinessException;
	

/**
 * 方法名：updateBean
 * 输入参数：${BEAN_NAME} bean
 * 返回值：int
 * 功能描述：修改Bean
 * 创建时间：2017-10-11
 * 序号       日期                  修改版本       更新者      内容
 *   1      2017-10-11      V1.0.0              史晓林     初版
 */
	public int updateBean(${BEAN_NAME} bean) throws BusinessException;
	
/**
 * 方法名：deleteBeanById
 * 输入参数：long
 * 返回值：int
 * 功能描述：根据id删除Bean
 * 创建时间：2017-10-11
 * 序号       日期                  修改版本       更新者      内容
 *   1      2017-10-11      V1.0.0              史晓林     初版
 */
	public int deleteBeanById(long pkId) throws BusinessException ;
	
/**
 * 方法名：deleteAllBean
 * 输入参数：List<Long> ids
 * 返回值：int
 * 功能描述：根据一批id删除一批Bean
 * 创建时间：2017-10-11
 * 序号       日期                  修改版本       更新者      内容
 *   1      2017-10-11      V1.0.0              史晓林     初版
 */
	public int deleteAllBean(List<Long> ids) throws BusinessException ;

/**
 * 方法名：getBeanById
 * 输入参数：Long pkId
 * 返回值：int
 * 功能描述：根据ID的值查询对象
 * 创建时间：2017-10-11
 * 序号       日期                  修改版本       更新者      内容
 *   1      2017-10-11      V1.0.0              史晓林     初版
 */
	public ${BEAN_NAME} getBeanById(Long pkId) throws BusinessException ;

/**
 * 方法名：updateBatch
 * 输入参数：List<${BEAN_NAME}>
 * 返回值：int
 * 功能描述：批量更新对象对象
 * 创建时间：2017-10-11
 * 序号       日期                  修改版本       更新者      内容
 *   1      2017-10-11      V1.0.0              史晓林     初版
 */
	public int updateBatch(List<${BEAN_NAME}> beanList) throws BusinessException ;
	
}