/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.ee.tools;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.RandomAccessFile;

import java.security.Key;

import javax.crypto.Cipher;

import org.apache.tools.ant.DirectoryScanner;

/**
 * @author Brian Wing Shun Chan
 */
public class ClassEncryptorBuilder {

	public static void main(String[] args) {
		new ClassEncryptorBuilder(args[0]);
	}

	public ClassEncryptorBuilder(String basedir) {
		try {
			Class<?> clazz = getClass();

			ClassLoader classLoader = clazz.getClassLoader();

			String[] keys = StringUtil.split(
				StringUtil.read(
					classLoader,
					"com/liferay/portal/license/classloader/keys.txt"),
				StringPool.NEW_LINE);

			_keys[0] = (Key)Base64.stringToObject(keys[706]);
			_keys[1] = (Key)Base64.stringToObject(keys[542]);
			_keys[2] = (Key)Base64.stringToObject(keys[175]);

			_basedir = basedir;

			_encryptClasses();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private byte[] _encrypt(Key key, byte[] plainBytes) throws Exception {
		Cipher cipher = Cipher.getInstance(key.getAlgorithm());

		cipher.init(Cipher.ENCRYPT_MODE, key);

		return cipher.doFinal(plainBytes);
	}

	private void _encryptClasses() throws Exception {
		DirectoryScanner ds = new DirectoryScanner();

		ds.setBasedir(_basedir);
		ds.setIncludes(
			new String[] {
				"**\\license\\LifecycleAction.class",
				"**\\license\\StartupAction.class"
			});

		ds.scan();

		String[] files = ds.getIncludedFiles();

		for (int i = 0; i < files.length; i++) {
			File file = new File(_basedir + "/" + files[i]);

			RandomAccessFile randomAccessFile = new RandomAccessFile(
				file, "rw");

			byte[] bytes = new byte[(int)randomAccessFile.length()];

			randomAccessFile.readFully(bytes);

			for (Key key : _keys) {
				bytes = _encrypt(key, bytes);
			}

			randomAccessFile.seek(0);
			randomAccessFile.setLength(bytes.length);

			randomAccessFile.write(bytes);

			randomAccessFile.close();

			System.out.println("Encrypting " + file);
		}
	}

	private final String _basedir;
	private final Key[] _keys = new Key[3];

}