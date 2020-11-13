package iisi.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class SecurityDemo {
	public static void main(String[] args) throws Exception {
//		String base64Encode = base64Encode("terry".getBytes());
//		System.out.println(base64Encode("terry".getBytes()));
//		byte[] base64Decode = base64Decode(base64Encode);
//		System.out.println(new String(base64Decode));
//		// MD5:不可逆,速度較快
//		System.out.println(md5Encode("terry".getBytes()));
//		// SHA256:不可逆,安全性較高
//		System.out.println(sha256Encode("terry".getBytes()));
		// 原文
		String input = "硅谷";
		// 定義密鑰key:DES必須為8個字節 AES為16個字節
		String key = "12345678";
		// 算法
		String transformation = "DES";
		// 加密類型
		String algorithm = "DES";
		String encrypt = encrypt(input, key, transformation, algorithm);
		System.out.println(encrypt);
		String decrypt = decrypt(encrypt, key, transformation, algorithm);
		System.out.println(decrypt);
	}

	public static String md5Encode(byte[] input) {
		String md5Hex = DigestUtils.md5Hex(input);// Hex:16進制
		return md5Hex;
	}

	public static String sha256Encode(byte[] input) {
		return DigestUtils.sha256Hex(input);
	}

	/*
	 * base64目的不是保護我們的數據,目的是為了可讀性 base64由64個字符組成,大小寫A-Z、數字0~9、兩個符號(+、/)
	 * base58沒有0、o、I、i、+、/
	 */
	public static String base64Encode(byte[] data) {
		return DatatypeConverter.printBase64Binary(data);
	}

	public static byte[] base64Decode(String str) {
		return DatatypeConverter.parseBase64Binary(str);
	}

	/**
	 * 
	 * @param encrypt        密文
	 * @param key            密鑰(DES必須為8個字節 AES為16個字節)
	 * @param transformation 加密算法(DES或AES)
	 * @param algorithm      加密類型(DES或AES)
	 * @return
	 * @throws Exception
	 */

	public static String decrypt(String encrypt, String key, String transformation, String algorithm) throws Exception {
		Cipher cipher = Cipher.getInstance(transformation);
		SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), algorithm);
		// Cipher.DECRYPT_MODE 表示解密
		// 解密方法
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
		byte[] doFinal = cipher.doFinal(Base64.decodeBase64(encrypt));
		return new String(doFinal);

	}

	/**
	 * 搭配base64加密數據
	 * 
	 * @param input          原文
	 * @param key            密鑰(DES必須為8個字節 AES為16個字節)
	 * @param transformation 獲取Cipher對象的算法(DES或AES)
	 * @param algorithm      獲取密鑰的算法(DES或AES)
	 * @return 密文
	 * @throws Exception
	 */
	public static String encrypt(String input, String key, String transformation, String algorithm) throws Exception {

		// 創建加密對象
		Cipher cipher = Cipher.getInstance(transformation);
		// 創建加密規則
		// 第一個參數:表示key的字節
		// 第二個參數:表示加密的類型
		SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), algorithm);

		// 進行加密初始化
		// 第一個參數表示模式:ENCRYPT_MODE,加密数据、DECRYPT_MODE,解密数据
		// 第二個參數表示:加密的規則
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
		// 調用加密方法 參數表示原文的字節數組
		byte[] bs = cipher.doFinal(input.getBytes());

		// 打印密文會出現亂碼,因為在ASCII編碼表上找不到對應的字符
//		System.out.println(new String(bs));
		// 創建Base64對象
		String encodeBase64URLSafeString = Base64.encodeBase64String(bs);
		return encodeBase64URLSafeString;
	}
}
