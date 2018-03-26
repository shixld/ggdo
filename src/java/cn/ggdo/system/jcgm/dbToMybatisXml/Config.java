package cn.ggdo.system.jcgm.dbToMybatisXml;

public class Config {
	// [模板名称]
	private String TEMPLATE_NAME = "bean-mybatis.ftl";
	
	// [模板名称]
	private String MAIN_MYBATIS_MAIN_TEMPLATE = "mybatis-config.ftl";
	
	// [生成路径]
	private String SECOND_PATH = "dao";
	// [名称前缀]
	private String BEFORE = "";
	// [名称后缀]
	private String AFTER = "-mybatis";
	// [文件类型]
	private String FILE_TYPE = ".xml";

	public String getTEMPLATE_NAME() {
		return TEMPLATE_NAME;
	}

	public void setTEMPLATE_NAME(String tEMPLATE_NAME) {
		TEMPLATE_NAME = tEMPLATE_NAME;
	}

	public String getSECOND_PATH() {
		return SECOND_PATH;
	}

	public void setSECOND_PATH(String sECOND_PATH) {
		SECOND_PATH = sECOND_PATH;
	}

	public String getBEFORE() {
		return BEFORE;
	}

	public void setBEFORE(String bEFORE) {
		BEFORE = bEFORE;
	}

	public String getAFTER() {
		return AFTER;
	}

	public void setAFTER(String aFTER) {
		AFTER = aFTER;
	}

	public String getFILE_TYPE() {
		return FILE_TYPE;
	}

	public void setFILE_TYPE(String fILE_TYPE) {
		FILE_TYPE = fILE_TYPE;
	}

	public String getMAIN_MYBATIS_MAIN_TEMPLATE() {
		return MAIN_MYBATIS_MAIN_TEMPLATE;
	}

	public void setMAIN_MYBATIS_MAIN_TEMPLATE(String main_mybatis_main_template) {
		MAIN_MYBATIS_MAIN_TEMPLATE = main_mybatis_main_template;
	}
}
