package cn.ggdo.system.framework.tools.security;

import javax.crypto.*;
import javax.crypto.spec.*;
	
/**
 * 类名：AES 
 * 版本：1.0.0
 * 用途说明：AES算法
 * 业务区分(加密算法)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public class AES {
	 	/*Key*/
		public static String sKey = "1234aBcD1234aBcD";
	
		/***
		 * 解密
		 * @param sSrc
		 * @return
		 * @throws Exception
		 */
	    public static String decrypt(String sSrc) throws Exception {
	        try {
	            //判断KEY是否空
	            if (sKey == null) {
	                return null;
	            }
	            //判断KEY是否是16位
	            if (sKey.length() != 16) {
	                return null;
	            }
	            byte[] raw = sKey.getBytes("ASCII");
	            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
	            Cipher cipher = Cipher.getInstance("AES");
	            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
	            byte[] encrypted1 = hex2byte(sSrc);
	            try {
	                byte[] original = cipher.doFinal(encrypted1);
	                String originalString = new String(original);
	                return originalString;
	            } catch (Exception e) {
	                System.out.println(e.toString());
	                return null;
	            }
	        } catch (Exception ex) {
	            System.out.println(ex.toString());
	            return null;
	        }
	    }
	    
	    /***
	     * 加密
	     * @param sSrc
	     * @return
	     * @throws Exception
	     */
	    public static String encrypt(String sSrc) throws Exception {
	        if (sKey == null) {
	            return null;
	        }
	        //判断KEY是否是16位
	        if (sKey.length() != 16) {
	            return null;
	        }
	        byte[] raw = sKey.getBytes("ASCII");
	        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
	        Cipher cipher = Cipher.getInstance("AES");
	        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
	        byte[] encrypted = cipher.doFinal(sSrc.getBytes());
	        return byte2hex(encrypted).toLowerCase();
	    }
	    
	    public static byte[] hex2byte(String strhex) {
	        if (strhex == null) {
	            return null;
	        }
	        int l = strhex.length();
	        if (l % 2 == 1) {
	            return null;
	        }
	        byte[] b = new byte[l / 2];
	        for (int i = 0; i != l / 2; i++) {
	            b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2), 16);
	        }
	        return b;
	    }
	    
	    public static String byte2hex(byte[] b) {
	        String hs = "";
	        String stmp = "";
	        for (int n = 0; n < b.length; n++) {
	            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
	            if (stmp.length() == 1) {
	                hs = hs + "0" + stmp;
	            } else {
	                hs = hs + stmp;
	            }
	        }
	        return hs.toUpperCase();
	    }
	    
	    public static void main(String[] args) throws Exception {
	
	    }
	}

