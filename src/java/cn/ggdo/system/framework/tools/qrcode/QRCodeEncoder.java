package cn.ggdo.system.framework.tools.qrcode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.MessageFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

/**
 * 类名：QRCodeEncoder 
 * 版本：1.0.0
 * 用途说明：二维码处理相关方法
 * 业务区分(二维码)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public class QRCodeEncoder {

	public static String createQrCode(String context, String path)
			throws Exception {

		byte[] d;
		String fileStr; // 文件名和路径
		// 处理文件内容
		if (context != null && context != "") {
			d = context.getBytes("UTF-8");
		} else {
			String result;
			StringBuffer sb;
			sb = new StringBuffer("");
			String beginDate = MessageFormat
					.format("{0,date,yyyy-MM-dd hh:mm:ss}",
							new Object[] { new java.sql.Date(System
									.currentTimeMillis()) });
			String endDate = MessageFormat
					.format("{0,date,yyyy-MM-dd hh:mm:ss}",
							new Object[] { new java.sql.Date(System
									.currentTimeMillis()) });

			sb.append(beginDate);
			sb.append(endDate);
			sb.append("数据无内容");
			result = sb.toString();
			d = result.getBytes("UTF-8");
		}

		// 实例化二维码
		Qrcode qrcode = new Qrcode();
		qrcode.setQrcodeErrorCorrect('M');
		qrcode.setQrcodeEncodeMode('B');
		qrcode.setQrcodeVersion(7);
		BufferedImage bi = new BufferedImage(98, 98,
				BufferedImage.TYPE_BYTE_BINARY);
		Graphics2D g = bi.createGraphics();
		g.setBackground(Color.WHITE);
		g.clearRect(0, 0, 98, 98);
		g.setColor(Color.BLACK);

		// 开始写二维码内容
		if (d.length > 0) {
			boolean[][] s = qrcode.calQrcode(d);
			for (int i = 0; i < s.length; i++) {
				for (int j = 0; j < s.length; j++) {
					if (s[j][i]) {
						g.fillRect(j * 2 + 3, i * 2 + 3, 2, 2);
					}
				}
			}
		}
		g.dispose();
		bi.flush();

		/**
		 * 获取毫秒以毫秒创建文件名
		 */
		if (path != null && path != "") {
			Date date = new Date();
			String fileName = "qrcode_" + date.getTime();
			File filePath = new File(path);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}

			fileStr = path + "/" + fileName + ".jpg";
			File file = new File(fileStr);
			if (!file.exists()) {
				file.createNewFile();
			}
			// 创建图片
			ImageIO.write(bi, "jpg", file);
			fileStr = file.getName();
		} else {
			fileStr = "";
		}
		return fileStr;
	}

	public static void main(String[] args) throws Exception {
		String str = "www.baidu.com";
		String path = "D://";
		System.out.println(QRCodeEncoder.createQrCode(str, path));
	}
}
