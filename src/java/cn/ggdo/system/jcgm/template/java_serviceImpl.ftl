package ${PACKAGE_NAME};

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.ggdo.system.framework.exception.BusinessException;
import cn.ggdo.system.framework.exception.ExceptionCode;
import cn.ggdo.system.framework.spring.annotation.MethodLog;

import ${I_DAOPACKAGE_NAME};
import ${BEAN_PACKAGE_NAME};
import ${I_SERVICEPACKAGE_NAME};
 
/**
 * 类名：${BEAN_NAME}ServiceImpl 
 * 版本：1.0.0
 * 用途说明：实现${BEAN_NAME}业务的服务接口实现层
 * 业务区分(Service业务层)
 * 依赖：spring相关jar包
 * 序号       日期                  修改版本       更新者      内容
 *   1      2017-10-11      V1.0.0              史晓林     初版
 */
@Service("${LOWER_BEAN_NAME}ServiceImpl")
@Transactional
public class ${BEAN_NAME}ServiceImpl implements I${BEAN_NAME}Service {
	
	@Autowired  
	private I${BEAN_NAME}Mapper ${LOWER_BEAN_NAME}Mapper;
	
/**
 * 方法名：lookForAll
 * 输入参数：无
 * 返回值：List<${BEAN_NAME}>
 * 功能描述：查询全部
 * 创建时间：2017-10-11
 * 序号       日期                  修改版本       更新者      内容
 *   1      2017-10-11      V1.0.0              史晓林     初版
 */
	@Override
	@MethodLog(name = "lookForAll", description = "查询全部数据，不分页")
	public List<${BEAN_NAME}> lookForAll() throws BusinessException {
		try {
			return ${LOWER_BEAN_NAME}Mapper.lookForAll();
		} catch (DataAccessException e) {
			throw new BusinessException(ExceptionCode.CODE_1000, e.getCause());
		}
	}
	
/**
 * 方法名：lookForAll
 * 输入参数：Map<String, Object> map
 * 返回值：List<${BEAN_NAME}>
 * 功能描述：按条件分页查询数据
 * 创建时间：2017-10-11
 * 序号       日期                  修改版本       更新者      内容
 *   1      2017-10-11      V1.0.0              史晓林     初版
 */
	@Override
	@MethodLog(name = "lookForAllByList", description = "查询数据，分页")
	public List<${BEAN_NAME}> lookForAllByList(Map<String, Object> map) throws BusinessException {
		try {
			return ${LOWER_BEAN_NAME}Mapper.lookForAllByList(map);
		} catch (DataAccessException e) {
			throw new BusinessException(ExceptionCode.CODE_1000, e.getCause());
		}
	}

/**
 * 方法名：lookForAllByCount
 * 输入参数：Map<String, Object> map
 * 返回值：int
 * 功能描述：按条件查询数据总数
 * 创建时间：2017-10-11
 * 序号       日期                  修改版本       更新者      内容
 *   1      2017-10-11      V1.0.0              史晓林     初版
 */
	@Override
	@MethodLog(name = "lookForAllByCount", description = "查询的总数")
	public int lookForAllByCount(Map<String, Object> map) throws BusinessException {
		try {
			return ${LOWER_BEAN_NAME}Mapper.lookForAllByCount(map);
		} catch (DataAccessException e) {
			throw new BusinessException(ExceptionCode.CODE_1000, e.getCause());
		}
	}
	
/**
 * 方法名：saveBean
 * 输入参数：${BEAN_NAME} bean
 * 返回值：int
 * 功能描述：增加新数据
 * 创建时间：2017-10-11
 * 序号       日期                  修改版本       更新者      内容
 *   1      2017-10-11      V1.0.0              史晓林     初版
 */
	@Override
	@MethodLog(name = "saveBean", description = "增加新数据")
	public int saveBean(${BEAN_NAME} ${LOWER_BEAN_NAME}) throws BusinessException {
		try {
			return ${LOWER_BEAN_NAME}Mapper.saveBean(${LOWER_BEAN_NAME});
		} catch (DataAccessException e) {
			throw new BusinessException(ExceptionCode.CODE_1000, e.getCause());
		}
	}
	
/**
 * 方法名：saveAllBean
 * 输入参数：List<${BEAN_NAME}>
 * 返回值：int
 * 功能描述：批量增加新数据
 * 创建时间：2017-10-11
 * 序号       日期                  修改版本       更新者      内容
 *   1      2017-10-11      V1.0.0              史晓林     初版
 */
	@Override
	@MethodLog(name = "saveAllBean", description = "批量增加新数据")
	public int saveAllBean(List<${BEAN_NAME}> ${LOWER_BEAN_NAME}List) throws BusinessException {
		try {
			return ${LOWER_BEAN_NAME}Mapper.saveAllBean(${LOWER_BEAN_NAME}List);
		} catch (DataAccessException e) {
			throw new BusinessException(ExceptionCode.CODE_1000, e.getCause());
		}
	}
	
/**
 * 方法名：updateBean
 * 输入参数：${BEAN_NAME} bean
 * 返回值：int
 * 功能描述：修改Bean
 * 创建时间：2017-10-11
 * 序号       日期                  修改版本       更新者      内容
 *   1      2017-10-11      V1.0.0              史晓林     初版
 */
	@Override
	@MethodLog(name = "updateBean", description = "修改Bean")
	public int updateBean(${BEAN_NAME} ${LOWER_BEAN_NAME}) throws BusinessException {
		try {
			return ${LOWER_BEAN_NAME}Mapper.updateBean(${LOWER_BEAN_NAME});
		} catch (DataAccessException e) {
			throw new BusinessException(ExceptionCode.CODE_1000, e.getCause());
		}
	}

/**
 * 方法名：updateBatch
 * 输入参数：List<${BEAN_NAME}>
 * 返回值：int
 * 功能描述：批量更新对象对象
 * 创建时间：2017-10-11
 * 序号       日期                  修改版本       更新者      内容
 *   1      2017-10-11      V1.0.0              史晓林     初版
 */
	@Override
	@MethodLog(name = "updateBatch", description = "根据ID的值查询对象")
	public int updateBatch(List<${BEAN_NAME}> ${LOWER_BEAN_NAME}List) throws BusinessException {
		try {
			return ${LOWER_BEAN_NAME}Mapper.updateBatch(${LOWER_BEAN_NAME}List);
		} catch (DataAccessException e) {
			throw new BusinessException(ExceptionCode.CODE_1000, e.getCause());
		}
	}
	
/**
 * 方法名：deleteBeanById
 * 输入参数：long
 * 返回值：int
 * 功能描述：根据id删除Bean
 * 创建时间：2017-10-11
 * 序号       日期                  修改版本       更新者      内容
 *   1      2017-10-11      V1.0.0              史晓林     初版
 */
	@Override
	@MethodLog(name = "deleteBeanById", description = "根据id删除Bean")
	public int deleteBeanById(long pkId) throws BusinessException {
		try {
			return ${LOWER_BEAN_NAME}Mapper.deleteBeanById(pkId);
		} catch (DataAccessException e) {
			throw new BusinessException(ExceptionCode.CODE_1000, e.getCause());
		}
	}
	
/**
 * 方法名：deleteAllBean
 * 输入参数：List<Long> ids
 * 返回值：int
 * 功能描述：根据一批id删除一批Bean
 * 创建时间：2017-10-11
 * 序号       日期                  修改版本       更新者      内容
 *   1      2017-10-11      V1.0.0              史晓林     初版
 */
	@Override
	@MethodLog(name = "deleteAllBean", description = "根据一批id删除一批Bean")
	public int deleteAllBean(List<Long> ids) throws BusinessException {
		try {
			return ${LOWER_BEAN_NAME}Mapper.deleteAllBean(ids);
		} catch (DataAccessException e) {
			throw new BusinessException(ExceptionCode.CODE_1000, e.getCause());
		}
	}
	

/**
 * 方法名：getBeanById
 * 输入参数：Long pkId
 * 返回值：int
 * 功能描述：根据ID的值查询对象
 * 创建时间：2017-10-11
 * 序号       日期                  修改版本       更新者      内容
 *   1      2017-10-11      V1.0.0              史晓林     初版
 */
	@Override
	@MethodLog(name = "getBeanById", description = "根据ID的值查询对象")
	public ${BEAN_NAME} getBeanById(Long pkId) throws BusinessException {
		try {
			return ${LOWER_BEAN_NAME}Mapper.getBeanById(pkId);
		} catch (DataAccessException e) {
			throw new BusinessException(ExceptionCode.CODE_1000, e.getCause());
		}
	}

}
