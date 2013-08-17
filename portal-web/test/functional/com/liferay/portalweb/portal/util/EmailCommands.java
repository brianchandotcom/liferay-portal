/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portalweb.portal.util;

import com.liferay.portal.kernel.util.GetterUtil;

import com.sun.mail.imap.IMAPFolder;

import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

/**
 * @author Kwang Lee
 */
public class EmailCommands {

	public static void connectToEmailAccount(
			String emailAddress, String emailPassword)
		throws Exception {

		Properties imapProperties = System.getProperties();

		imapProperties.setProperty("mail.store.protocol", "imaps");

		_imapSession = Session.getInstance(imapProperties);

		Store store = _imapSession.getStore("imaps");

		store.connect("imap.gmail.com", emailAddress, emailPassword);

		_imapFolder = (IMAPFolder)store.getFolder("Inbox");

		_imapFolder.open(Folder.READ_WRITE);

		Properties smtpProperties = System.getProperties();

		smtpProperties.put("mail.smtp.auth", "true");
		smtpProperties.put("mail.smtp.host", "smtp.gmail.com");
		smtpProperties.put("mail.smtp.password", emailPassword);
		smtpProperties.put("mail.smtp.port", "587");
		smtpProperties.put("mail.smtp.starttls.enable", "true");
		smtpProperties.put("mail.smtp.user", emailAddress);

		_smtpSession = Session.getDefaultInstance(smtpProperties);

		_transport = _smtpSession.getTransport("smtp");

		_transport.connect("smtp.gmail.com", emailAddress, emailPassword);
	}

	public static void deleteAllEmails() throws Exception {
		Message[] messages = _imapFolder.getMessages();

		for (Message message : messages) {
			message.setFlag(Flags.Flag.DELETED, true);
		}

		_imapFolder.close(true);
	}

	public static String getEmailContent(String index) throws Exception {
		int i = GetterUtil.getInteger(index);

		Message message = _imapFolder.getMessage(i);

		String content = (String)message.getContent();

		return content.trim();
	}

	public static String getEmailSubject(String index) throws Exception {
		int i = GetterUtil.getInteger(index);

		Message message = _imapFolder.getMessage(i);

		String subject = (String)message.getSubject();

		return subject;
	}

	public static void replyToEmail(String to, String content)
		throws Exception {

		Message retreiveMessage = _imapFolder.getMessage(1);

		Message replyMessage = retreiveMessage.reply(false);

		replyMessage.setRecipient(RecipientType.TO, new InternetAddress(to));
		replyMessage.setText(content);

		_transport.sendMessage(
			replyMessage, replyMessage.getRecipients(RecipientType.TO));

		_transport.close();
	}

	public static void sendEmail(String to, String subject, String content)
		throws Exception {

		MimeMessage message = new MimeMessage(_smtpSession);

		message.addRecipient(RecipientType.TO, new InternetAddress(to));
		message.setSubject(subject);
		message.setText(content);

		message.saveChanges();

		_transport.sendMessage(message, message.getAllRecipients());

		_transport.close();
	}

	private static IMAPFolder _imapFolder;
	private static Session _imapSession;
	private static Session _smtpSession;
	private static Transport _transport;

}