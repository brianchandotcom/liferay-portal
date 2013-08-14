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

import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

/**
 * @author Kwang Lee
 */
public class SendEmail {

	public void send(
		String user, String password, String to, String subject,
		String content) {

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.user", user);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.debug", "true");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put(
			"mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");

		try {
			Session session = Session.getDefaultInstance(props, null);

			MimeMessage message = new MimeMessage(session);
			message.setText(content);
			message.setSubject(subject);
			message.setFrom(new InternetAddress(user));
			message.addRecipient(RecipientType.TO, new InternetAddress(to));
			message.saveChanges();

			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.gmail.com", user, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}