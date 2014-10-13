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

package com.liferay.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.Digester;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;

import java.security.Key;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class Encryptor {

	public static final String ENCODING = Digester.ENCODING;

	public static final String IBM_PROVIDER_CLASS =
		"com.ibm.crypto.provider.IBMJCE";

	public static final String KEY_ALGORITHM = StringUtil.toUpperCase(
		GetterUtil.getString(
			PropsUtil.get(PropsKeys.COMPANY_ENCRYPTION_ALGORITHM)));

	public static final int KEY_SIZE = GetterUtil.getInteger(
		PropsUtil.get(PropsKeys.COMPANY_ENCRYPTION_KEY_SIZE));

	public static final String PROVIDER_CLASS = GetterUtil.getString(
		SystemProperties.get(Encryptor.class.getName() + ".provider.class"),
		Encryptor.SUN_PROVIDER_CLASS);

	public static final String SUN_PROVIDER_CLASS =
		"com.sun.crypto.provider.SunJCE";

	public static String decrypt(Key key, String encryptedString)
		throws EncryptorException {

		byte[] encryptedBytes = Base64.decode(encryptedString);

		return decryptUnencodedAsString(key, encryptedBytes);
	}

	public static byte[] decryptUnencodedAsBytes(Key key, byte[] encryptedBytes)
		throws EncryptorException {

		String algorithm = key.getAlgorithm();

		String cacheKey = algorithm.concat(StringPool.POUND).concat(
			key.toString());

		Cipher cipher = _decryptCipherMap.get(cacheKey);

		try {
			if (cipher == null) {
				Security.addProvider(getProvider());

				cipher = Cipher.getInstance(algorithm);

				cipher.init(Cipher.DECRYPT_MODE, key);

				_decryptCipherMap.put(cacheKey, cipher);
			}

			synchronized (cipher) {
				return cipher.doFinal(encryptedBytes);
			}
		}
		catch (Exception e) {
			throw new EncryptorException(e);
		}
	}

	public static String decryptUnencodedAsString(
			Key key, byte[] encryptedBytes)
		throws EncryptorException {

		try {
			byte[] decryptedBytes = decryptUnencodedAsBytes(
				key, encryptedBytes);

			return new String(decryptedBytes, ENCODING);
		}
		catch (Exception e) {
			throw new EncryptorException(e);
		}
	}

	public static String digest(String text) {
		return DigesterUtil.digest(text);
	}

	public static String digest(String algorithm, String text) {
		return DigesterUtil.digest(algorithm, text);
	}

	public static String encrypt(Key key, String plainText)
		throws EncryptorException {

		if (key == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Skip encrypting based on a null key");
			}

			return plainText;
		}

		byte[] encryptedBytes = encryptUnencoded(key, plainText);

		return Base64.encode(encryptedBytes);
	}

	public static byte[] encryptUnencoded(Key key, byte[] plainBytes)
		throws EncryptorException {

		String algorithm = key.getAlgorithm();

		String cacheKey = algorithm.concat(StringPool.POUND).concat(
			key.toString());

		Cipher cipher = _encryptCipherMap.get(cacheKey);

		try {
			if (cipher == null) {
				Security.addProvider(getProvider());

				cipher = Cipher.getInstance(algorithm);

				cipher.init(Cipher.ENCRYPT_MODE, key);

				_encryptCipherMap.put(cacheKey, cipher);
			}

			synchronized (cipher) {
				return cipher.doFinal(plainBytes);
			}
		}
		catch (Exception e) {
			throw new EncryptorException(e);
		}
	}

	public static byte[] encryptUnencoded(Key key, String plainText)
		throws EncryptorException {

		try {
			byte[] decryptedBytes = plainText.getBytes(ENCODING);

			return encryptUnencoded(key, decryptedBytes);
		}
		catch (Exception e) {
			throw new EncryptorException(e);
		}
	}

	public static Key generateKey() throws EncryptorException {
		return generateKey(KEY_ALGORITHM);
	}

	public static Key generateKey(String algorithm) throws EncryptorException {
		try {
			Security.addProvider(getProvider());

			KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);

			keyGenerator.init(KEY_SIZE, new SecureRandom());

			Key key = keyGenerator.generateKey();

			return key;
		}
		catch (Exception e) {
			throw new EncryptorException(e);
		}
	}

	public static Provider getProvider()
		throws ClassNotFoundException, IllegalAccessException,
			   InstantiationException {

		Class<?> providerClass = null;

		try {
			providerClass = Class.forName(PROVIDER_CLASS);
		}
		catch (ClassNotFoundException cnfe) {
			if (ServerDetector.isWebSphere() &&
				PROVIDER_CLASS.equals(SUN_PROVIDER_CLASS)) {

				if (_log.isWarnEnabled()) {
					_log.warn(
						"WebSphere does not have " + SUN_PROVIDER_CLASS +
							", using " + IBM_PROVIDER_CLASS + " instead");
				}

				providerClass = Class.forName(IBM_PROVIDER_CLASS);
			}
			else if (System.getProperty("java.vm.vendor").equals(
						"IBM Corporation")) {

				if (_log.isWarnEnabled()) {
					_log.warn(
						"IBM JVM does not have " + SUN_PROVIDER_CLASS +
							", using " + IBM_PROVIDER_CLASS + " instead");
				}

				providerClass = Class.forName(IBM_PROVIDER_CLASS);
			}
			else {
				throw cnfe;
			}
		}

		return (Provider)providerClass.newInstance();
	}

	private static final Log _log = LogFactoryUtil.getLog(Encryptor.class);

	private static final Map<String, Cipher> _decryptCipherMap =
		new ConcurrentHashMap<String, Cipher>(1, 1f, 1);
	private static final Map<String, Cipher> _encryptCipherMap =
		new ConcurrentHashMap<String, Cipher>(1, 1f, 1);

}