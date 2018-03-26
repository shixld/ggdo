package ${PACKAGE_NAME};

import java.util.List;
import java.util.Map;

import ${BEAN_PACKAGE_NAME};

/**
 * 类名：I${BEAN_NAME}Mapper 
 * 版本：1.0.0
 * 用途说明：实现${BEAN_NAME}业务的数据层
 * 业务区分(Dao业务层)
 * 序号       日期                  修改版本       更新者      内容
 *   1      2017-10-11      V1.0.0              史晓林     初版
 */
public interface I${BEAN_NAME}Mapper {
	
	/***
	 * 查询全部数据
	 *@author   史晓林
	 *@version   v1.0
	 *@param    暂无对象
	 *@return   对象的List
	 *@exception  异常抛向上面的方法
	 */
	public List<${BEAN_NAME}> lookForAll();


	/***
	 * 按条件分页查询数据
	 *@author   史晓林
	 *@version   v1.0
	 *@param    查询条件
	 *@return   对象的List
	 *@exception  异常抛向上面的方法
	 */
	public List<${BEAN_NAME}> lookForAllByList(Map<String, Object> map);
	

	/***
	 * 根据ID的值查询对象
	 *@author   史晓林
	 *@version   v1.0
	 *@param    主键ID
	 *@return   实体对象
	 *@exception  异常抛向上面的方法
	 */
	public ${BEAN_NAME} getBeanById(Long pkId);


	/***
	 * 按条件查询数据总数
	 *@author   史晓林
	 *@version   v1.0
	 *@param    查询条件
	 *@return   查询的总数
	 *@exception  异常抛向上面的方法
	 */
	public int lookForAllByCount(Map<String, Object> map);

	
	/***
	 * 增加新数据
	 *@author   史晓林
	 *@version   v1.0
	 *@param    实体对象
	 *@return   插入的状态
	 *@exception  异常抛向上面的方法
	 */
	public int saveBean(${BEAN_NAME} bean);


	/***
	 * 批量增加新数据
	 *@author   史晓林
	 *@version   v1.0
	 *@param    一批实体对象
	 *@return   插入的状态
	 *@exception  异常抛向上面的方法
	 */
	public int saveAllBean(List<${BEAN_NAME}> beanList);


	/***
	 * 修改数据
	 *@author   史晓林
	 *@version   v1.0
	 *@param    实体对象
	 *@return   修改的状态
	 *@exception  异常抛向上面的方法
	 */
	public int updateBean(${BEAN_NAME} bean);


	/***
	 * 根据主键删除Bean
	 *@author   史晓林
	 *@version   v1.0
	 *@param    主键
	 *@return   修改的状态
	 *@exception  异常抛向上面的方法
	 */
	public int deleteBeanById(long pkId);

	
	/***
	 * 根据一批主键删除Bean
	 *@author   史晓林
	 *@version   v1.0
	 *@param    主键LIST
	 *@return   删除的记录数量
	 *@exception  异常抛向上面的方法
	 */
	public int deleteAllBean(List<Long> ids);

	/***
	 * 批量更新数据
	 *@author   史晓林
	 *@version   v1.0
	 *@param    一批实体对象
	 *@return   更新的状态
	 *@exception  异常抛向上面的方法
	 */
	public int updateBatch(List<${BEAN_NAME}> beanList);
}
