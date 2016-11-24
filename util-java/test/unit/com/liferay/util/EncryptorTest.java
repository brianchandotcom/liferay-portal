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

import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.security.Key;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Mika Koivisto
 * @see    com.liferay.petra.encryptor.EncryptorTest
 */
@PowerMockIgnore("javax.crypto.*")
@RunWith(PowerMockRunner.class)
public class EncryptorTest extends PowerMockito {

	@Before
	public void setUp() {
		_props = PropsUtil.getProps();

		Props props = mock(Props.class);

		PropsUtil.setProps(props);

		when(
			props.get(Mockito.eq(PropsKeys.COMPANY_ENCRYPTION_ALGORITHM))
		).thenReturn(
			"AES"
		);

		when(
			props.get(Mockito.eq(PropsKeys.COMPANY_ENCRYPTION_KEY_SIZE))
		).thenReturn(
			"128"
		);
	}

	@After
	public void tearDown() {
		PropsUtil.setProps(_props);
	}

	@Test
	public void testKeySerialization() throws Exception {
		Key key = Encryptor.generateKey();

		String encryptedString = Encryptor.encrypt(key, "Hello World!");

		String serializedKey = Encryptor.serializeKey(key);

		key = Encryptor.deserializeKey(serializedKey);

		Assert.assertEquals(
			"Hello World!", Encryptor.decrypt(key, encryptedString));
	}

	private Props _props;

}