package com.sikiedu.betobe.utils;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Properties;

/**
 * @author zhu
 * @date 2021/4/9 13:34:56
 * @description 发送Email的工具类，异步发送，线程发送邮件
 */
public class SendEmailManager extends Thread {

	// 谁发送

	// 发送的地址
	private String mailAdr;
	// 发送的内容
	private String content;
	// 发送的标题
	private String subject;

	public SendEmailManager(String mailAdr, String subject, String content) {
		this.mailAdr = mailAdr;
		this.content = content;
		this.subject = subject;
	}

	@Override
	public void run() {
		try {
			sendMail(mailAdr, subject, content);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 发送邮件
	private void sendMail(String mailAdr, String subject, String content) throws Exception {

		// 邮箱工厂
		MailSSLSocketFactory mailSSLSocketFactory = new MailSSLSocketFactory();
		// 设置所有主机
		mailSSLSocketFactory.setTrustAllHosts(true);
		// 设置参数
		final Properties properties = new Properties();
		// 表示SMTP发送邮件，需要进行身份验证
		// 协议
		properties.setProperty("mail.transport.protocol", "smtp");
		// 授权
		properties.setProperty("mail.smtp.auth", "true");
		// QQ协议
		properties.setProperty("mail.smtp.host", "smtp.qq.com");
		// smtp登录的账号、密码
		properties.setProperty("mail.user", "594010359@qq.com");
		properties.setProperty("mail.password", "wlnagpqtlwznbehg");
		properties.setProperty("mail.debug", "true");

		// 注意，要将ssl协议设置为true，530错误
		properties.setProperty("mail.stmp.ssl.enable", "true");
		properties.put("mail.stmp.ssl.socketFactory", mailSSLSocketFactory);

		// 设置发送者
		Authenticator authenticator = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// 用户名、密码
				return new PasswordAuthentication(properties.getProperty("mail.user"),
						properties.getProperty("mail.password"));
			}
		};

		// 使用环境属性和授权信息，创建邮件会话
		Session mailSession = Session.getInstance(properties, authenticator);

		// 创建邮件消息
		MimeMessage mimeMessage = new MimeMessage(mailSession);

		// 设置发送人
		mimeMessage.setFrom(new InternetAddress(properties.getProperty("mail.user")));
		// 设置收件人
		mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(mailAdr));
		// 设置邮件标题
		mimeMessage.setSubject(subject);
		// 设置邮件的内容题
		mimeMessage.setContent(content, "text/html;charset=utf-8");
		// 发送邮件
		Transport.send(mimeMessage);
	}
}
