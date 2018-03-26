package cn.ggdo.system.jcgm.dbToEntity;

public class Config {
	// [模板名称]
	private String TEMPLATE_NAME = "";
	// [生成路径]
	private String SECOND_PATH = "entity";
	// [名称前缀]
	private String BEFORE = "";
	// [名称后缀]
	private String AFTER = "";
	// [文件类型]
	private String FILE_TYPE = ".java";

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
}
