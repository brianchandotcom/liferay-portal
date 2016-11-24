/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.util.mail;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;
import java.io.InputStream;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;

/**
 * @author Brian Wing Shun Chan
 * @see    com.liferay.petra.mail.JavaMailUtil
 */
public class JavaMailUtil {

	public static byte[] getBytes(Part part)
		throws IOException, MessagingException {

		InputStream is = part.getInputStream();

		return FileUtil.getBytes(is);
	}

	public static String toUnicodeString(Address[] addresses) {
		return toUnicodeString((InternetAddress[])addresses);
	}

	public static String toUnicodeString(InternetAddress[] addresses) {
		if (ArrayUtil.isEmpty(addresses)) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(addresses.length * 2 - 1);

		for (int i = 0; i < addresses.length; i++) {
			if (addresses[i] != null) {
				sb.append(addresses[i].toUnicodeString());
			}

			if ((i + 1) != addresses.length) {
				sb.append(", ");
			}
		}

		return sb.toString();
	}

}