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

import com.sun.mail.imap.IMAPFolder;

import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage.RecipientType;

/**
 * @author Kwang Lee
 */
public class MailFetching {

	public static void connect(String emailAddress, String password)
		throws Exception {

		Properties imaps_properties = System.getProperties();
		imaps_properties.setProperty("mail.store.protocol", "imaps");

		try {
			Session session = Session.getInstance(imaps_properties);
			Store store = session.getStore("imaps");

			store.connect("imap.gmail.com", emailAddress, password);
			inbox = (IMAPFolder) store.getFolder("Inbox");
			inbox.open(Folder.READ_WRITE);
		}
		catch (MessagingException e) {
			System.out.println(e.toString());
			System.exit(2);
		}

		Properties smtp_properties = System.getProperties();
		smtp_properties.put("mail.smtp.starttls.enable", "true");
		smtp_properties.put("mail.smtp.host", "smtp.gmail.com");
		smtp_properties.put("mail.smtp.user", emailAddress);
		smtp_properties.put("mail.smtp.password", password);
		smtp_properties.put("mail.smtp.port", "587");
		smtp_properties.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(smtp_properties);
		transport = session.getTransport("smtp");
		transport.connect("smtp.gmail.com", emailAddress, password);
	}

	public static void deleteMails() throws Exception {

		Message messages[] = inbox.getMessages();

		for (Message message : messages) {
			message.setFlag(Flags.Flag.DELETED, true);
		}

		inbox.close(true);
	}

	public static String getContent(String number) throws Exception {

		int num = Integer.parseInt(number);
		Message message = inbox.getMessage(num);
		String content = ((String)message.getContent()).trim();

		return content;
	}

	public static String getSubject(String number) throws Exception {

		int num = Integer.parseInt(number);
		Message message = inbox.getMessage(num);
		String subject = message.getSubject().toString();

		return subject;
	}

	public static void reply(String to, String content) throws Exception {

		Message message = inbox.getMessage(1);
		Message reply = message.reply(false);
		reply.setRecipient(RecipientType.TO, new InternetAddress(to));
		reply.setText(content);

		transport.sendMessage(
			reply, reply.getRecipients(Message.RecipientType.TO));
		transport.close();
	}

	private static IMAPFolder inbox;
	private static Transport transport;

}