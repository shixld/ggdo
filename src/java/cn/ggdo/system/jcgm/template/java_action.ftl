package ${PACKAGE_NAME};

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.ggdo.system.framework.exception.BusinessException;
import cn.ggdo.system.framework.spring.SpringBindingResultWrapper;
import cn.ggdo.system.framework.spring.annotation.MethodLog;
import cn.ggdo.system.function.common.BaseAction;
import cn.ggdo.system.function.common.JsonResultBean;
import cn.ggdo.system.function.common.PageInfo;
import cn.ggdo.system.framework.lang.DateUtil;
import ${BEAN_PACKAGE_NAME};
import ${I_SERVICE_PACKAGE_NAME};

/**
 * 类名：${BEAN_NAME}Action 
 * 版本：1.0.0
 * 用途说明：实现${BEAN_NAME}业务的对外接口
 * 业务区分(View业务层)
 * 依赖：springmvc相关jar包
 * 序号       日期                  修改版本       更新者      内容
 *   1      2017-10-11      V1.0.0              史晓林     初版
 */
@Controller
@RequestMapping("/${LOWER_BEAN_NAME}")
public class ${BEAN_NAME}Action extends BaseAction {

	@Autowired
	private I${BEAN_NAME}Service ${LOWER_BEAN_NAME}ServiceImpl;

	private final Logger log = LogManager.getLogger(${BEAN_NAME}Action.class);
/**
 * 方法名：lookForAll
 * 输入参数：String pageIndexs , Map<String ,Object> params
 * 返回值：ModelAndView
 * 功能描述：分页查询某一张表的相关列表信息
 * 创建时间：2017-10-11
 * 序号       日期                  修改版本       更新者      内容
 *   1      2017-10-11      V1.0.0              史晓林     初版
 */
	@ResponseBody//作用是将返回的对象作为响应，发送给页面
	@MethodLog(name = "lookForAll", description = "查询全部数据，不分页")
	@RequestMapping("/lookForAll")
	public ModelAndView lookForAll(String pageIndexs, @RequestParam Map<String ,Object> params){
		log.info("time:" + DateUtil.getNowDate(new Date())
				+ ",desc:进入${BEAN_NAME}Action类lookForAll的方法,type:method");
		ModelAndView model = new ModelAndView();
		List<${BEAN_NAME}> ${LOWER_BEAN_NAME}List = new ArrayList<${BEAN_NAME}>();
		try {
			if(pageIndexs == null || pageIndexs.equals("")){
				this.setPageIndexs("1");
			}else{
				
				this.setPageIndexs(pageIndexs);
			}
			Long totalRowNum = Long.valueOf(${LOWER_BEAN_NAME}ServiceImpl.lookForAllByCount(params));
			setPageInfo(new PageInfo());
			this.getPageInfo().search(totalRowNum);
			this.getPageInfo().turnToPage(Integer.valueOf(this.getPageIndexs()));
			int start = (int) this.getPageInfo().getFirstResult() - 1;
			int range = this.getPageInfo().getPageSize();
			params.put("start", start);
			params.put("range", range);
			${LOWER_BEAN_NAME}List = ${LOWER_BEAN_NAME}ServiceImpl.lookForAllByList(params);
		} catch (BusinessException e) {
			StackTraceElement stackTraceElement= e.getStackTrace()[0];// 得到异常棧的首个元素 
			log.error("time:" + DateUtil.getNowDate(new Date()) +",class:"+stackTraceElement.getFileName()+",method:"+stackTraceElement.getMethodName()+",line:"+stackTraceElement.getLineNumber()+ ",desc:" + e.getMessage()
					+ ",type: Exception");
			e.printStackTrace();
		}
		
		model.addObject("pageIndexs", this.getPageIndexs());
		model.addObject("pageInfo", this.getPageInfo());
		model.addObject("${LOWER_BEAN_NAME}List", ${LOWER_BEAN_NAME}List);
		model.setViewName("list${BEAN_NAME}");
		log.info("time:" + DateUtil.getNowDate(new Date()) + ",desc:离开${BEAN_NAME}Action类lookForAll的方法,type:method");
		return model;
		
	}
	
/**
 * 方法名：deleteBeanById
 * 输入参数：id
 * 返回值：JsonResultBean
 * 功能描述：根据id删除Bean
 * 创建时间：2017-10-11
 * 序号       日期                  修改版本       更新者      内容
 *   1      2017-10-11      V1.0.0              史晓林     初版
 */
	@ResponseBody//作用是将返回的对象作为响应，发送给页面
	@MethodLog(name = "deleteBeanById", description = "根据id删除Bean")
	@RequestMapping(value = "/deleteBeanById")
	public JsonResultBean deleteBeanById(@RequestParam("id")
	int id) throws BusinessException {
		log.info("time:" + DateUtil.getNowDate(new Date())
				+ ",desc:进入${BEAN_NAME}Action类deleteBeanById的方法，参数id为" + id + ",type:method");
		${LOWER_BEAN_NAME}ServiceImpl.deleteBeanById(id);
		log.info("time:" + DateUtil.getNowDate(new Date()) + ",desc:离开${BEAN_NAME}Action类deleteBeanById的方法,type:method");
		return new JsonResultBean(JsonResultBean.SUCCESS , "数据删除成功");
	}

/**
 * 方法名：getBeanById
 * 输入参数：id
 * 返回值：${BEAN_NAME}
 * 功能描述：根据id查询对于的对象
 * 创建时间：2017-10-11
 * 序号       日期                  修改版本       更新者      内容
 *   1      2017-10-11      V1.0.0              史晓林     初版
 */
	@ResponseBody//作用是将返回的对象作为响应，发送给页面
	@MethodLog(name = "getBeanById", description = "根据id查询Bean")
	@RequestMapping(value = "/getBeanById")
	public ${BEAN_NAME} getBeanById(@RequestParam("id") int id) throws BusinessException {
		log.info("time:" + DateUtil.getNowDate(new Date())
				+ ",desc:进入${BEAN_NAME}Action类getBeanById的方法，参数id为" + id + ",type:method");
		${BEAN_NAME} ${LOWER_BEAN_NAME} = ${LOWER_BEAN_NAME}ServiceImpl.getBeanById(Long.valueOf(id));
		log.info("time:" + DateUtil.getNowDate(new Date()) + ",desc:离开${BEAN_NAME}Action类getBeanById的方法,type:method");
		return ${LOWER_BEAN_NAME};
	}
	
/**
 * 方法名：saveBean
 * 输入参数：${BEAN_NAME} bean
 * 返回值：JsonResultBean
 * 功能描述：增加新数据
 * 创建时间：2017-10-11
 * 序号       日期                  修改版本       更新者      内容
 *   1      2017-10-11      V1.0.0              史晓林     初版
 */
	@ResponseBody//作用是将返回的对象作为响应，发送给页面
	@MethodLog(name = "saveBean", description = "增加新数据")
	@RequestMapping(value = "/saveBean")
	public JsonResultBean saveBean(@ModelAttribute ${BEAN_NAME} bean, BindingResult bindingResult)
			throws BusinessException {
		log.info("time:" + DateUtil.getNowDate(new Date())
				+ ",desc:进入${BEAN_NAME}Action类saveBean的方法,type:method");
		// 数据校验
		if (bindingResult.hasErrors()) {
			String errorMsg = SpringBindingResultWrapper
					.warpErrors(bindingResult);
			log.error("time:" + DateUtil.getNowDate(new Date())
				+ ",desc:离开${BEAN_NAME}Action类saveBean的方法,type:method");
			return new JsonResultBean(JsonResultBean.FAULT, errorMsg);
		} else {
			${LOWER_BEAN_NAME}ServiceImpl.saveBean(bean);
			log.info("time:" + DateUtil.getNowDate(new Date())
				+ ",desc:离开${BEAN_NAME}Action类saveBean的方法,type:method");
			return new JsonResultBean(JsonResultBean.SUCCESS, "数据成功插入");
		}
	}

/**
 * 方法名：saveBean
 * 输入参数：${BEAN_NAME} form
 * 返回值：JsonResultBean
 * 功能描述：修改Bean
 * 创建时间：2017-10-11
 * 序号       日期                  修改版本       更新者      内容
 *   1      2017-10-11      V1.0.0              史晓林     初版
 */
	@ResponseBody//作用是将返回的对象作为响应，发送给页面
	@MethodLog(name = "updateBean", description = "修改Bean")
	@RequestMapping(value = "/updateBean")
	public final JsonResultBean updateBean(
			@ModelAttribute("form")
			final ${BEAN_NAME} form, final BindingResult bindingResult)
			throws BusinessException {
		log.info("time:" + DateUtil.getNowDate(new Date())
				+ ",desc:进入${BEAN_NAME}Action类updateBean的方法,type:method");
		// 数据校验
		if (bindingResult.hasErrors()) {
			log.error("time:" + DateUtil.getNowDate(new Date())
				+ ",desc:离开${BEAN_NAME}Action类updateBean的方法,type:method");
			return new JsonResultBean(JsonResultBean.FAULT,
					SpringBindingResultWrapper.warpErrors(bindingResult));
		} else {
			${LOWER_BEAN_NAME}ServiceImpl.updateBean(form);
			log.info("time:" + DateUtil.getNowDate(new Date())
				+ ",desc:离开${BEAN_NAME}Action类updateBean的方法,type:method");
			return new JsonResultBean(JsonResultBean.SUCCESS, "数据修改成功");
		}
	}

}