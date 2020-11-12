package mail;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.sun.mail.util.MailSSLSocketFactory;

public class JavaMailWithAttachment {

	public static void main(String[] args) throws Exception {
//			 設置訪問hotmail 內容
		Properties props = new Properties();
//		props.put("mail.transport.protocal", "smtp");// 使用協議:smtp
//		props.put("mail.smtp.host", "smtp.live.com");// 協議地址
//		props.put("mail.smtp.port", "587");// 協議端口
		props.put("mail.smtp.auth", "true");// true:需要授權

		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.enable", "false");
		MailSSLSocketFactory sf = new MailSSLSocketFactory();
		sf.setTrustAllHosts(true);
		props.put("mail.smtp.ssl.checkserveridentity", "false");
		props.put("mail.smtp.ssl.socketFactory", sf);
		Session ses = Session.getInstance(props);
		ses.setDebug(true);// 開啟日誌提示

		// 創建郵件
		MimeMessage message = creatMimeMessage(ses, "a8001082002@hotmail.com", "a8001082002@yahoo.com.tw", null, null);
		// 建立連接對象
		Transport transport = ses.getTransport("smtp");// 透過session與hotmail產生連結, 使用協議:smtp
		// 建立連結
		transport.connect("smtp.live.com", 587, "a8001082002@hotmail.com", "*********");
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();

	}

	// 有附件的郵件
	public static MimeMessage creatMimeMessage(Session session, String send, String receive, String cReceive,
			String mReceive) throws Exception {
		MimeMessage message = new MimeMessage(session);// 透過session訪問服務
		// 郵件:標題、正文、收件人、寄件人 {附件、圖片}
		Address address = new InternetAddress(send, "寄件人姓名");
		message.setFrom(address);
		message.setSubject("這是標題(含有圖片+附件)");

		// 創建圖片節點
		MimeBodyPart imagePart = new MimeBodyPart();
		// DataHandler用於包裝文件和數據
		DataHandler imageDataHandler = new DataHandler(new FileDataSource("src/資訊安全備忘錄20200428.jpg"));// 圖片地址
		imagePart.setDataHandler(imageDataHandler);
		imagePart.setContentID("myImage0428");// 藉由id來區分節點

		// 創建文本節點:目的是為了加載圖片節點
		MimeBodyPart textPart = new MimeBodyPart();
		// 將之前設置的節點代號加入src裡
		textPart.setContent("正文內容<br/>  image:<img src='cid:myImage0428'/>啦啦啦 ", "text/html;charset=utf-8");

		// 將文本節點、圖片節點 --->組裝成一個複合節點
		MimeMultipart mm_text_image = new MimeMultipart();
		mm_text_image.addBodyPart(imagePart);
		mm_text_image.addBodyPart(textPart);
		mm_text_image.setSubType("related");// related:設置關聯關係

		// 注意:正文中只能出現普通節點(MimeBodyPart)不能出現複合節點(MimeMultipart)
		// MimeMultipart -> MimeBodyPart
		MimeBodyPart text_imageBodyPart = new MimeBodyPart();
		text_imageBodyPart.setContent(mm_text_image);

		// 圖片節點加進文本節點並設置兩者間的關係 -> 複合節點 ->根據要求變成普通節點

		// 附件
		MimeBodyPart attachment = new MimeBodyPart();
		DataHandler fileDataHandler = new DataHandler(new FileDataSource("src/問題.txt"));// 附件地址
		attachment.setDataHandler(fileDataHandler);
		// 在mail中附件都是顯示檔案名稱,沒有向圖片文字直接show出來,所以附件要設置文件名稱
		attachment.setFileName(MimeUtility.encodeText(fileDataHandler.getName()));// 解決出現亂碼問題

		// 將剛才處理好的"文本+圖片"的節點(text_imageBodyPart)與附件節點(attachment)組合成一個新的複合節點
		MimeMultipart mm = new MimeMultipart();
		mm.addBodyPart(text_imageBodyPart);
		mm.addBodyPart(attachment);
		mm.setSubType("mixed");// 混合關係
		// 如果設置正文+附件的話,就不需要轉成普通節點用複合節點
		message.setContent(mm);

		// 收件人類型:TO一般、CC副本、BCC密件副本
		message.setRecipient(RecipientType.TO, new InternetAddress(receive, "收件人A"));
//			message.setRecipient(RecipientType.CC, new InternetAddress(cReceive, "副本人B", "UTF-8"));
//			message.setRecipient(RecipientType.BCC, new InternetAddress(mReceive, "密件人C", "UTF-8"));
//		message.setSentDate(new Date());// 設置發送時間
		message.saveChanges();// 保存郵件
		return message;
	}
}
