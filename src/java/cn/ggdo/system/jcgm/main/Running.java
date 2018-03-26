package cn.ggdo.system.jcgm.main;

import cn.ggdo.system.jcgm.dbToAction.GenerateAction;
import cn.ggdo.system.jcgm.dbToEntity.GenerateEntity;
import cn.ggdo.system.jcgm.dbToJsp.GenerateJsp;
import cn.ggdo.system.jcgm.dbToMybatis.GenerateDao;
import cn.ggdo.system.jcgm.dbToMybatisXml.GenerateMybatisXml;
import cn.ggdo.system.jcgm.dbToService.GenerateService;
import cn.ggdo.system.jcgm.dbToServiceImpl.GenerateServiceImpl;


public class Running {
	public static void main(String[] args) {
		// 生成bean
		new GenerateEntity().generate();
		// 生成dao
		new GenerateDao().generate();
		// 生成mybatisXml
		new GenerateMybatisXml().generate();
		// 生成service
		new GenerateService().generate();
		// 生成serviceImpl
		new GenerateServiceImpl().generate();
		// 生成action
		new GenerateAction().generate();
		// 生成jsp
		new GenerateJsp().generate();
		
	}
}
