package cn.ggdo.system.framework.tools.charater;

/**
 * 类名：StringTransformUtils 
 * 版本：1.0.0
 * 用途说明：日期编码
 * 业务区分(字符格式转换)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public class StringTransformUtils {

	// 转化html的常量;
	//private static final char[] QUOTE_ENCODE = "&quot;".toCharArray();

	//private static final char[] AMP_ENCODE = "&amp;".toCharArray();

	private static final char[] LT_ENCODE = "&lt;".toCharArray();

	private static final char[] GT_ENCODE = "&gt;".toCharArray();

	//private static final char[] APOS_ENCODE = "&apos;".toCharArray();

	private static final char[] BR_TAG = "<BR>".toCharArray();

	/**
	 * 初始化,并且锁定一个类只能调用
	 * 
	 */
	//private static Object initLock = new Object();

	/**
	 * 替代linde中的oldString为newString
	 * 
	 * @参数 line 需要做替代的字符串
	 * @参数 oldString the String that should be replaced by newString
	 * @param newString
	 *            the String that will replace all instances of oldString
	 * 
	 * @return a String will all instances of oldString replaced by newString
	 */
	public static final String replace(String line, String oldString,
			String newString) {
		if (line == null) {
			return null;
		}
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = line.indexOf(oldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}

	/**
	 * This method takes a string which may contain HTML tags (ie, &lt;b&gt;,
	 * &lt;table&gt;, etc) and converts the '&lt'' and '&gt;' characters to
	 * their HTML escape sequences.
	 * 
	 * @param in
	 *            the text to be converted.
	 * @return the input string with the characters '&lt;' and '&gt;' replaced
	 *         with their HTML escape sequences.
	 */
	public static final String escapeHTMLTags(String in) {
		if (in == null) {
			return null;
		}
		char ch;
		int i = 0;
		int last = 0;
		char[] input = in.toCharArray();
		int len = input.length;
		StringBuffer out = new StringBuffer((int) (len * 1.3));
		for (; i < len; i++) {
			ch = input[i];
			if (ch > '>') {
				continue;
			} else if (ch == '<') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(LT_ENCODE);
			} else if (ch == '>') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(GT_ENCODE);
			}
		}
		if (last == 0) {
			return in;
		}
		if (i > last) {
			out.append(input, last, i - last);
		}
		return out.toString();
	}

	public static String convertNewlines(String input) {
		char[] chars = input.toCharArray();
		int cur = 0;
		int len = chars.length;
		StringBuffer buf = new StringBuffer(len);
		// Loop through each character lookin for newlines.
		for (int i = 0; i < len; i++) {
			// If we've found a Unix newline, add BR tag.
			if (chars[i] == '\n') {
				buf.append(chars, cur, i - cur).append(BR_TAG);
				cur = i + 1;
			}
			// If we've found a Windows newline, add BR tag.
			else if (chars[i] == '\r' && i < len - 1 && chars[i + 1] == '\n') {
				buf.append(chars, cur, i - cur).append(BR_TAG);
				i++;
				cur = i + 1;
			}
		}
		// Add whatever chars are left to buffer.
		buf.append(chars, cur, len - cur);
		return buf.toString();
	}

	public static String getTranslateStr(String sourceStr, String fieldStr) {
		// 处理逻辑表达式的转化问题
		String[] sourceList;
		String resultStr = "";
		// dim i,j
		if (sourceStr.indexOf(" ") > 0) {
			boolean isOperator = true;
			sourceList = sourceStr.split(" ");
			// '--------------------------------------------------------
			// rem Response.Write "num:" & cstr(ubound(sourceList)) & "<br>"
			for (int i = 0; i < sourceList.length; i++) {
				if (sourceList[i].equals("AND") || sourceList[i].equals("&")
						|| sourceList[i].equals("和")
						|| sourceList[i].equals("与")) {
					resultStr = resultStr + " and ";
					isOperator = true;
				} else if (sourceList[i].equals("OR")
						|| sourceList[i].equals("|")
						|| sourceList[i].equals("或")) {
					resultStr = resultStr + " or ";
					isOperator = true;
				} else if (sourceList[i].equals("NOT")
						|| sourceList[i].equals("!")
						|| sourceList[i].equals("！")
						|| sourceList[i].equals("非")) {
					resultStr = resultStr + " not ";
					isOperator = true;
				} else if (sourceList[i].equals("(")
						|| sourceList[i].equals("（")
						|| sourceList[i].equals("（")) {
					resultStr = resultStr + " ( ";
					isOperator = true;
				} else if (sourceList[i].equals(")")
						|| sourceList[i].equals("）")
						|| sourceList[i].equals("）")) {
					resultStr = resultStr + " ) ";
					isOperator = true;
				} else {
					if (!"".equals(sourceList[i])) {
						if (!isOperator) {
							resultStr = resultStr + " and ";
						}
						if (sourceList[i].indexOf("%") > 0) {
							resultStr = resultStr + " " + fieldStr + " like '"
									+ sourceList[i].replaceAll("'", "''")
									+ "' ";

						} else
							resultStr = resultStr + " " + fieldStr + " like '%"
									+ sourceList[i].replaceAll("'", "''")
									+ "%' ";
						isOperator = false;
					}
				}
			}
			return resultStr;
		} else {
			if (sourceStr.indexOf("%") > 0) {
				resultStr = resultStr + " " + fieldStr + " like '"
						+ sourceStr.replaceAll("'", "''") + "' ";

			} else
				resultStr = resultStr + " " + fieldStr + " like '%"
						+ sourceStr.replaceAll("'", "''") + "%' ";

			return resultStr;
		}

	}
	
	public static char doVerify(String id)    
    {    
        char pszSrc[]=id.toCharArray();    
        int iS = 0;    
        int iW[]={7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};    
        char szVerCode[] = new char[]{'1','0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};    
        int i;    
        for(i=0;i<17;i++)    
        {    
            iS += (pszSrc[i]-'0') * iW[i];    
        }    
        int iY = iS%11;    
        return szVerCode[iY];    
    }

	public static String stringArrayToString(String[] array, char separator){
		
		if(array == null)
			return null;
		
		if(array.length == 0)
			return "";
		
		StringBuilder sb = new StringBuilder();
		
		for(String str : array){
			
			sb.append(str).append(separator);
		}
		
		sb.deleteCharAt(sb.length()-1);
		
		return sb.toString();
	}
	
	
	public static String updateFristWordToLower(String str){
		char[] chars=new char[1];   
        chars[0]=str.charAt(0);  
        String temp=new String(chars);  
        if(chars[0]>='A'  &&  chars[0]<='Z')  
        {  
            str = str.replaceFirst(temp,temp.toLowerCase());  
        } 
        return str;
	}
}