package cn.ggdo.system.jcgm.until;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class GeneratePage {

	private Configuration config;

	public Configuration getConfig() {
		return config;
	}

	/** 
	 * 注意：setEncoding这个方法一定要设置国家及其编码，不然在flt中的中文在生成html后会变成乱码 
	 * @param filePath 文件路径 
	 * @throws Exception 
	 */
	public void init(String filePath) {
		try {
			config = new Configuration();
			//设置要解析的模板所在的目录，并加载模板文件  
			config.setDirectoryForTemplateLoading(new File(filePath));
			//设置包装器，并将对象包装为数据模型  
			config.setObjectWrapper(new DefaultObjectWrapper());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/** 
	 * 通过flt文件用html文件展示课程数据 
	 * @param templatePath flt文件路径 
	 * @param templateName flt模板文件 
	 * @param root 要生成html的集合数据 
	 * @param htmlFile 通过flt生成html的文件 
	 * @throws Exception 
	 */
	public void analysisTemplate(String templatePath, String templateName,
			String htmlFile, Map<String, Object> root) {
		try {
			File file = new File(htmlFile);
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			init(templatePath);
			//获取模板,并设置编码方式，这个编码必须要与页面中的编码格式一致  
			//否则会出现乱码  
			Template template = config.getTemplate(templateName, "UTF-8");
			//合并数据模型与模板  
			FileOutputStream fos = new FileOutputStream(htmlFile);
			Writer out = new OutputStreamWriter(fos, "UTF-8");
			template.process(root, out);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}

	/** 
	 * 通过String流用html文件展示课程数据  
	 * @param root 要生成html的集合数据 
	 * @param htmlFile 通过flt生成html的文件 
	 * @throws Exception 
	 * @throws Exception 
	 */
	public void analysisTemplateByString(String templateContent,
			String htmlFile, Map<String, Object> root) {
		try {
			File file = new File(htmlFile);
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			Configuration config = new Configuration();
			StringTemplateLoader stringLoader = new StringTemplateLoader();
			stringLoader.putTemplate("myTemplate", templateContent);
			config.setTemplateLoader(stringLoader);
			//获取模板,并设置编码方式，这个编码必须要与页面中的编码格式一致  
			//否则会出现乱码  
			Template template = config.getTemplate("myTemplate", "UTF-8");
			FileOutputStream fos = new FileOutputStream(htmlFile);
			Writer out = new OutputStreamWriter(fos, "UTF-8");
			template.process(root, out);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		GeneratePage gp = new GeneratePage();
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("BEAN_NAME", "shixld");
		map.put("BEANstes", "${BEANstes}");
		gp.analysisTemplateByString("您好呀，${BEAN_NAME}，${BEANstes}","D://shixld.html", map);
	}
}
