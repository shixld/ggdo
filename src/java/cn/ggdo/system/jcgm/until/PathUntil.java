package cn.ggdo.system.jcgm.until;

public class PathUntil {
	/**
	 * 
	 * @param string 转换前的字符串
	 * @return 转换后的字符串
	 */
	static public String path(String string) {
		String os = System.getProperty("os.name");
		if (!"".equals(string) && string != null) {
			if (os.startsWith("win") || os.startsWith("Win")) {
				string = string.replace("\\", "\\\\");
				string = string.replace("/", "\\");
			} else {
				string = string.replace("\\\\", "/");
				string = string.replace("\\", "/");
			}
			return string;
		} else {
			string = "";
			return string;
		}
	}
}
