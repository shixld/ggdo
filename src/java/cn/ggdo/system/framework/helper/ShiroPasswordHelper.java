package cn.ggdo.system.framework.helper;

import org.apache.shiro.crypto.hash.SimpleHash;

import cn.ggdo.system.framework.readconfig.ResourceUtil;

/**
 * 类名：ShiroPasswordHelper 
 * 版本：1.0.0
 * 用途说明：定义一个自定义的PasswordEncoder，从而加强应用的安全认证和高安全性。 使用shiro的RSA-256加密
 * 业务区分(helper)
 * 履历：
 * 序号       日期                  修改版本       更新者      内容
 *   1      2013-02-21      V1.0.0              史晓林     初版
 */
public final class ShiroPasswordHelper {
	
	private static final String applicationSalt;
	private static final String hashAlgorithmName;
	private static final  int hashIterations;
	private static final  boolean storedCredentialsHexEncoded;
	static{
		ResourceUtil configFile = new ResourceUtil("/properties/systemCode.properties");
		applicationSalt =  configFile.getProPerties("shiro.applicationSalt");
		hashAlgorithmName = configFile.getProPerties("shiro.hashAlgorithmName");
		hashIterations =   Integer.valueOf(configFile.getProPerties("shiro.hashIterations"));
		storedCredentialsHexEncoded =  Boolean.valueOf(configFile.getProPerties("shiro.storedCredentialsHexEncoded"));
	}
	
	private ShiroPasswordHelper() {
	}

	/**
	 * 将输入的密码进行特殊处理，防止密码轻易被破解，增强应用的安全性 密码 + 常量 +用户名
	 * 
	 * @param password
	 *            需要加密的字符串
	 * @param userItcode
	 *            用户名
	 * @return 返回加密后的密码字符串
	 */
	public static String encodePassword(final String password, final String userItcode) {
		if (storedCredentialsHexEncoded) {
			return new SimpleHash(hashAlgorithmName, password, applicationSalt + userItcode, hashIterations).toHex();
		} else {
			return new SimpleHash(hashAlgorithmName, password, applicationSalt + userItcode, hashIterations).toString();
		}
	}

	/**
	 * 判断输入的密码是否与应用中存储的密码相符合。因为应用中存储的密码是由输入的密码经过特殊处理后生成的， 所以需要我们自己定义如何判断输入的密码和存储的密码的一致性
	 * 
	 * @param encPass
	 *            加密后的字符串
	 * @param password
	 *            需要加密的字符串
	 * @param userItcode
	 *            username
	 * @return 加密后的字符串和传入的字符串是否相同
	 */
	public static boolean isPasswordValid(final String encPass, final String password, final String userItcode) {
		if (storedCredentialsHexEncoded) {
			return new SimpleHash(hashAlgorithmName, password, applicationSalt + userItcode, hashIterations).toHex().equals(encPass);
		} else {
			return new SimpleHash(hashAlgorithmName, password, applicationSalt + userItcode, hashIterations).toString().equals(encPass);
		}
	}
	
	public static void main(String[] args) {
		System.out.println(ShiroPasswordHelper.encodePassword("000000","admin"));
	}
}
