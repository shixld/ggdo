package cn.ggdo.system.function.servlet;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ggdo.system.framework.tools.code.VerifyCode;

public class VerifyCodeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8250666714244431188L;

	public static final int WIDTH = 120;//生成的图片的宽度
	public static final int HEIGHT = 30;//生成的图片的高度

	/**
	 * 设置图片的背景色
	 * @param g
	 */
	private void setBackGround(Graphics g) {
		// 设置颜色
		g.setColor(Color.WHITE);
		// 填充区域
		g.fillRect(0, 0, WIDTH, HEIGHT);
	}

	/**
	 * 设置图片的边框
	 * @param g
	 */
	private void setBorder(Graphics g) {
		// 设置边框颜色
		g.setColor(Color.BLUE);
		// 边框区域
		g.drawRect(1, 1, WIDTH - 2, HEIGHT - 2);
	}

	/**
	 * 在图片上画随机线条
	 * @param g
	 */
	private void drawRandomLine(Graphics g) {
		// 设置颜色
		g.setColor(Color.GREEN);
		// 设置线条个数并画线
		for (int i = 0; i < 5; i++) {
			int x1 = new Random().nextInt(WIDTH);
			int y1 = new Random().nextInt(HEIGHT);
			int x2 = new Random().nextInt(WIDTH);
			int y2 = new Random().nextInt(HEIGHT);
			g.drawLine(x1, y1, x2, y2);
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//String createTypeFlag = request.getParameter("createTypeFlag");// 接收客户端传递的createTypeFlag标识
		// 1.在内存中创建一张图片
		BufferedImage bi = new BufferedImage(WIDTH, HEIGHT,
				BufferedImage.TYPE_INT_RGB);
		// 2.得到图片
		Graphics g = bi.getGraphics();
		// 3.设置图片的背影色
		setBackGround(g);
		// 4.设置图片的边框
		setBorder(g);
		// 5.在图片上画干扰线
		drawRandomLine(g);
		// 6.写在图片上随机数
		VerifyCode verifyCode = new VerifyCode();
		String random = verifyCode.drawRandomNum((Graphics2D) g,"ch");//生成中文验证码图片
		// String random = verifyCode.drawRandomNum((Graphics2D) g,"nl");//生成数字和字母组合的验证码图片
		//String random = verifyCode.drawRandomNum((Graphics2D) g,"n");//生成纯数字的验证码图片
		// String random = verifyCode.drawRandomNum((Graphics2D) g,"l");//生成纯字母的验证码图片
		//String random = verifyCode.drawRandomNum((Graphics2D) g, createTypeFlag);// 根据客户端传递的createTypeFlag标识生成验证码图片
		// 7.将随机数存在session中
		request.getSession().setAttribute("checkcode", random);
		// 8.设置响应头通知浏览器以图片的形式打开
		response.setContentType("image/jpeg");// 等同于response.setHeader("Content-Type",
		// "image/jpeg");
		// 9.设置响应头控制浏览器不要缓存
		response.setDateHeader("expries", -1);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		// 10.将图片写给浏览器
		ImageIO.write(bi, "jpg", response.getOutputStream());
	}
}
