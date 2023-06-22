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

package com.liferay.portal.kernel.encryptor;

import com.liferay.petra.encryptor.Encryptor;
import com.liferay.petra.encryptor.EncryptorException;

import java.security.Key;

/**
 * @author Julius Lee
 */
public class EncryptorUtil {

	public static String decrypt(Key key, String encryptedString)
		throws EncryptorException {

		return Encryptor.decrypt(key, encryptedString);
	}

	public static byte[] decryptUnencodedAsBytes(Key key, byte[] encryptedBytes)
		throws EncryptorException {

		return Encryptor.decryptUnencodedAsBytes(key, encryptedBytes);
	}

	public static Key deserializeKey(String base64String) {
		return Encryptor.deserializeKey(base64String);
	}

	public static String encrypt(Key key, String plainText)
		throws EncryptorException {

		return Encryptor.encrypt(key, plainText);
	}

	public static byte[] encryptUnencoded(Key key, byte[] plainBytes)
		throws EncryptorException {

		return Encryptor.encryptUnencoded(key, plainBytes);
	}

	public static byte[] encryptUnencoded(Key key, String plainText)
		throws EncryptorException {

		return Encryptor.encryptUnencoded(key, plainText);
	}

	public static Key generateKey() throws EncryptorException {
		return Encryptor.generateKey();
	}

	public static String serializeKey(Key key) {
		return Encryptor.serializeKey(key);
	}

}