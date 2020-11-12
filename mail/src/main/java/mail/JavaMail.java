package mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import com.sun.mail.util.MailSSLSocketFactory;

public class JavaMail {
	public static void main(String[] args) throws Exception {
//		 設置訪問hotmail 內容
		Properties props = new Properties();
		props.put("mail.transport.protocal", "smtp");// 使用協議:smtp
		props.put("mail.smtp.host", "smtp.live.com");// 協議地址
		props.put("mail.smtp.port", "587");// 協議端口
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
		Transport transport = ses.getTransport();// 透過session與hotmail產生連結
		// 建立連結
		transport.connect("a8001082002@hotmail.com", "*********");
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();

	}

	// 一般郵件
	public static MimeMessage creatMimeMessage(Session session, String send, String receive, String cReceive,
			String mReceive) throws Exception {
		MimeMessage message = new MimeMessage(session);// 透過session訪問服務
		// 郵件:標題、正文、收件人、寄件人 {附件、圖片}
		Address address = new InternetAddress(send, "寄件人姓名");
		message.setFrom(address);
		message.setSubject("這是標題");
		message.setText("正文內容");
		// 收件人類型:TO一般、CC副本、BCC密件副本
		message.setRecipient(RecipientType.TO, new InternetAddress(receive, "收件人A"));
//		message.setRecipient(RecipientType.CC, new InternetAddress(cReceive, "副本人B", "UTF-8"));
//		message.setRecipient(RecipientType.BCC, new InternetAddress(mReceive, "密件人C", "UTF-8"));
		message.setSentDate(new Date());// 設置發送時間
		message.saveChanges();// 保存郵件
		return message;
	}
}
