package cn.ggdo.system.framework.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.DOMBuilder;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.DOMOutputter;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * 类名：XmlUtil 
 * 版本：1.0.0
 * 用途说明：Xml处理工具类
 * 业务区分(IO处理工具)
 * 依赖 jdom2.jar
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public final class XmlUtil {

	private XmlUtil() {
		throw new Error("工具类不能实例化");
	}

	/**
	 * 根据指定路径的XML文件建立JDom对象
	 * 
	 * @param filePath
	 *            XML文件的路径
	 * @return 返回建立的JDom对象，建立不成功返回null 。
	 */
	public static Document buildFromFile(final String filePath) {
		SAXBuilder builder = new SAXBuilder();
		Document document = null;
		try {
			if (filePath.startsWith("http")) {
				System.out.println("http开头的暂不处理");
				// document = builder.build(new URL(filePath));
			} else {
				document = builder.build(new File(filePath));
			}
		} catch ( Exception e) {
			e.printStackTrace();
		}
		return document;
	}

	/**
	 * 根据XML 字符串 建立JDom对象
	 * 
	 * @param xmlString
	 *            XML格式的字符串
	 * @return 返回建立的JDom对象，建立不成功返回null 。
	 */
	public static Document buildFromXMLString(final String xmlString) {
		SAXBuilder builder = new SAXBuilder();
		Document document = null;
		try {
			document = builder.build(new StringReader(xmlString));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return document;
	}

	/**
	 * 根据Dom对象建立JDom对象
	 * 
	 * @param dom
	 *            org.w3c.dom.Document对象
	 * @return 返回建立的JDom对象，建立不成功返回null 。
	 */
	public static Document buildFromDom(final org.w3c.dom.Document dom) {
		DOMBuilder builder = new DOMBuilder();
		Document jdomDoc = builder.build(dom);
		return jdomDoc;
	}

	/**
	 * 这个方法使用XMLOutputer将一个JDom对象输出到标准输出设备，使用 UTF-8 编码
	 * 
	 * @param myDocument
	 *            将要被输出的JDom对象
	 */
	public static void outputToStdoutUTF8(final Document myDocument) {
		outputToStdout(myDocument, "UTF-8");
	}

	/**
	 * 这个方法使用XMLOutputer将一个JDom对象输出到标准输出设备
	 * 
	 * @param myDocument
	 *            将要被输出的JDom对象
	 * @param encoding
	 *            输出使用的编码
	 */
	public static void outputToStdout(final Document myDocument, final String encoding) {
		try {
			XMLOutputter outputter = new XMLOutputter();
			Format fm = Format.getPrettyFormat();
			fm.setEncoding(encoding);
			outputter.setFormat(fm);
			outputter.output(myDocument, System.out);
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 这个方法将JDom对象转换字符串.
	 * 
	 * @param document
	 *            将要被转换的JDom对象
	 */
	public static String outputToString(final Document document) {
		return outputToString(document, "UTF-8");
	}

	/**
	 * 这个方法将JDom对象转换字符串.
	 * 
	 * @param document
	 *            将要被转换的JDom对象
	 * @param encoding
	 *            输出字符串使用的编码
	 */
	public static String outputToString(final Document document, final String encoding) {
		ByteArrayOutputStream byteRep = new ByteArrayOutputStream();
		XMLOutputter outputter = new XMLOutputter();
		Format fm = Format.getPrettyFormat();
		fm.setEncoding(encoding);
		outputter.setFormat(fm);
		try {
			outputter.output(document, byteRep);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return byteRep.toString();
	}

	/**
	 * 这个方法将List对象转换字符串.
	 * 
	 * @param list
	 *            List对象
	 * @return 字符串
	 */
	@SuppressWarnings("rawtypes")
	public static String outputToString(final List list) {
		return outputToString(list, "UTF-8");
	}

	/**
	 * 这个方法将List对象转换字符串.
	 * 
	 * @param encoding
	 *            输出字符串使用的编码
	 * @return List对象转换后的字符串
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String outputToString(final List list, final String encoding) {
		ByteArrayOutputStream byteRep = new ByteArrayOutputStream();
		XMLOutputter outputter = new XMLOutputter();
		Format fm = Format.getPrettyFormat();
		fm.setEncoding(encoding);
		outputter.setFormat(fm);
		try {
			outputter.output(list, byteRep);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return byteRep.toString();
	}

	/**
	 * outputToDom
	 */
	public static org.w3c.dom.Document outputToDom(final Document jdomDoc) throws JDOMException {
		DOMOutputter outputter = new DOMOutputter();
		return outputter.output(jdomDoc);
	}

	/**
	 * 这个方法使用XMLOutputter将JDom对象输出到文件
	 * 
	 * @param myDocument
	 *            将要输出的JDom对象
	 * @param filePath
	 *            将要输出到的磁盘路径
	 */
	public static void outputToFile(final Document myDocument, final String filePath) {
		outputToFile(myDocument, filePath, "UTF-8");
	}

	/**
	 * 这个方法使用XMLOutputter将JDom对象输出到文件
	 * 
	 * @param myDocument
	 *            将要输出的JDom对象
	 * @param filePath
	 *            将要输出到的磁盘路径
	 * @param encoding
	 *            编码方式
	 */
	public static void outputToFile(final Document myDocument, final String filePath, final String encoding) {
		// setup this like outputDocument
		try {
			XMLOutputter outputter = new XMLOutputter();
			Format fm = Format.getPrettyFormat();
			fm.setEncoding(encoding);
			outputter.setFormat(fm);
			// output to a file
			OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filePath), encoding); //
			outputter.output(myDocument, writer);
			writer.close();
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 这个方法将JDom对象通过样式单转换.
	 * 
	 * @param myDocument
	 *            将要被转换的JDom对象
	 * @param xslFilePath
	 *            XSL文件的磁盘路径
	 */
	public static void executeXSL(final Document myDocument, final String xslFilePath, final StreamResult xmlResult) {
		TransformerFactory tFactory = TransformerFactory.newInstance();
		// Make the input sources for the XML and XSLT documents
		DOMOutputter outputter = new DOMOutputter();
		org.w3c.dom.Document domDocument;
		try {
			domDocument = outputter.output(myDocument);
			javax.xml.transform.Source xmlSource = new javax.xml.transform.dom.DOMSource(domDocument);
			StreamSource xsltSource = new StreamSource(new FileInputStream(xslFilePath));
			// Get a XSLT transformer
			Transformer transformer = tFactory.newTransformer(xsltSource);
			// do the transform
			transformer.transform(xmlSource, xmlResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Main 函数，局部测试用。
	// public static void main(String argv[]) {
	// // Document dom = JDomUtil.buildFromFile("f:/web.xml");
	// // List list=dom.getRootElement().getChildren("servlet");
	// // String vs_dom = JDomUtil.outputToString(list,"gb2312");
	// // System.out.println(vs_dom);
	// String xmlString = "<record><row f1=\"111\" f2=\"111\"/><row f1=\"222\" f2=\"222\"/></record>";
	// Document dom = IoXmlUtil.buildFromXMLString(xmlString);
	// List list = dom.getRootElement().getChildren("row");
	// int num = list.size();
	// for (int i = 0; i < num; i++) {
	// Element element = (Element) list.get(i);
	// System.out.println(element.getAttributeValue("f1"));
	// System.out.println(element.getAttributeValue("f2"));
	// }
	// }

}
