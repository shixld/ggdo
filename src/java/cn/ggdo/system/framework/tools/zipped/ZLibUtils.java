package cn.ggdo.system.framework.tools.zipped;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * 类名：ZLibUtils 
 * 版本：1.0.0
 * 用途说明：压缩方法
 * 业务区分(压缩方法)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public abstract class ZLibUtils {

	/** 
	 * 压缩 
	 *  
	 * @param data 压缩字符串
	 *           
	 * @return byte[] 返回压缩结果
	 */
	public static byte[] compress(byte[] data) {
		byte[] output = new byte[0];

		Deflater compresser = new Deflater();

		compresser.reset();
		compresser.setInput(data);
		compresser.finish();
		ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
		try {
			byte[] buf = new byte[1024];
			while (!compresser.finished()) {
				int i = compresser.deflate(buf);
				bos.write(buf, 0, i);
			}
			output = bos.toByteArray();
		} catch (Exception e) {
			output = data;
			e.printStackTrace();
		} finally {
			try {
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		compresser.end();
		return output;
	}

	/** 
	 * 解压
	 *  
	 * @param data 
	 *           解压字符串
	 * @return byte[] 返回解压结果
	 */
	public static byte[] decompress(byte[] data) {
		byte[] output = new byte[0];

		Inflater decompresser = new Inflater();
		decompresser.reset();
		decompresser.setInput(data);

		ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);
		try {
			byte[] buf = new byte[1024];
			while (!decompresser.finished()) {
				int i = decompresser.inflate(buf);
				o.write(buf, 0, i);
			}
			output = o.toByteArray();
		} catch (Exception e) {
			output = data;
			e.printStackTrace();
		} finally {
			try {
				o.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		decompresser.end();
		return output;
	}
	
	public static void main(String[] args) throws Exception {
		String inputStr = "测试解压";  
        System.err.println("解压前:" + inputStr);  
        byte[] input = inputStr.getBytes();  
        System.err.println( input.length);  
  
        byte[] data = ZLibUtils.compress(input);  
        System.err.println("解压后:" + data.length);  
  
        byte[] output = ZLibUtils.decompress(data);  
        System.err.println("解压后:" + output.length);  
        String outputStr = new String(output);  
        System.err.println(outputStr);  
	}
}