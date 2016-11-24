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
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.validator.routines.EmailValidator;

/**
 * @author Alexander Chow
 * @see    com.liferay.petra.mail.InternetAddressUtil
 */
public class InternetAddressUtil {

	public static boolean contains(
		InternetAddress[] internetAddresses, String emailAddress) {

		if ((internetAddresses != null) && Validator.isNotNull(emailAddress)) {
			for (int i = 0; i < internetAddresses.length; i++) {
				if (emailAddress.equals(internetAddresses[i].getAddress())) {
					return true;
				}
			}
		}

		return false;
	}

	public static boolean isValid(String emailAddress) {
		return EmailValidator.getInstance().isValid(emailAddress);
	}

	public static InternetAddress[] removeEntry(
		Address[] addresses, String emailAddress) {

		InternetAddress[] internetAddresses = (InternetAddress[])addresses;

		List<InternetAddress> list = new ArrayList<>();

		if ((internetAddresses == null) || Validator.isNull(emailAddress)) {
			return internetAddresses;
		}

		for (int i = 0; i < internetAddresses.length; i++) {
			if (!emailAddress.equals(internetAddresses[i].getAddress())) {
				list.add(internetAddresses[i]);
			}
		}

		return list.toArray(new InternetAddress[list.size()]);
	}

	public static String toString(Address address) {
		InternetAddress internetAddress = (InternetAddress)address;

		if (internetAddress != null) {
			StringBundler sb = new StringBundler(5);

			String personal = internetAddress.getPersonal();
			String emailAddress = internetAddress.getAddress();

			if (Validator.isNotNull(personal)) {
				sb.append(personal);
				sb.append(StringPool.SPACE);
				sb.append(StringPool.LESS_THAN);
				sb.append(emailAddress);
				sb.append(StringPool.GREATER_THAN);
			}
			else {
				sb.append(emailAddress);
			}

			return sb.toString();
		}

		return StringPool.BLANK;
	}

	public static String toString(Address[] addresses) {
		if (ArrayUtil.isEmpty(addresses)) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(addresses.length * 2 - 1);

		for (int i = 0; i < (addresses.length - 1); i++) {
			sb.append(toString(addresses[i]));
			sb.append(StringPool.COMMA);
		}

		sb.append(toString(addresses[addresses.length - 1]));

		return sb.toString();
	}

	public static void validateAddress(Address address)
		throws AddressException {

		if (address == null) {
			throw new AddressException("Email address is null");
		}

		String addressString = address.toString();

		for (char c : addressString.toCharArray()) {
			if ((c == CharPool.NEW_LINE) || (c == CharPool.RETURN)) {
				StringBundler sb = new StringBundler(3);

				sb.append("Email address ");
				sb.append(addressString);
				sb.append(" is invalid because it contains line breaks");

				throw new AddressException(sb.toString());
			}
		}
	}

	public static void validateAddresses(Address[] addresses)
		throws AddressException {

		if (addresses == null) {
			throw new AddressException();
		}

		for (Address internetAddress : addresses) {
			validateAddress(internetAddress);
		}
	}

}