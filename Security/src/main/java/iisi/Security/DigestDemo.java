package iisi.Security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 消息摘要算法,為了防止串改 常見的加密算法:md5、sha1、sha256、sha512
 * 
 * @author 2010013
 *
 */
public class DigestDemo {
	public static void main(String[] args) throws Exception {
		// 原文
		String input = "aa";
		// 算法(以算法進行加密)
		String MD5 = "MD5";
		String digest = getDigest(input, MD5);
		System.out.println(digest);
		String sha1 = getDigest(input, "sha1");
		System.out.println(sha1);
		String sha256 = getDigest(input, "sha256");
		System.out.println(sha256);
		String sha512 = getDigest(input, "sha512");
		System.out.println(sha512);

	}

	/**
	 * 獲取數字摘要
	 * 
	 * @param input     原文
	 * @param algorithm 算法
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	private static String getDigest(String input, String algorithm) throws NoSuchAlgorithmException {
		// 創建消息摘要對象
		MessageDigest digest = MessageDigest.getInstance(algorithm);
		// 執行消息摘要算法
		byte[] digest2 = digest.digest(input.getBytes());

		return toHex(digest2);
	}

	private static String toHex(byte[] digest) {
		StringBuilder sb = new StringBuilder();
		// 對密文進行迭代
		for (byte b : digest) {
			// 把密文轉換成16進制
			String s = Integer.toHexString(b & 0xff);
			// 判斷如果密文長度=1,需要在其高位補0
			if (s.length() == 1) {
				s = "0" + s;
			}
			sb.append(s);
		}
		return sb.toString();
//		System.out.println(new String(digest));// 會出現亂碼
		// 使用base64進行轉碼解決出現亂碼問題
//		System.out.println(Base64.encodeBase64String(digest));
	}
}
