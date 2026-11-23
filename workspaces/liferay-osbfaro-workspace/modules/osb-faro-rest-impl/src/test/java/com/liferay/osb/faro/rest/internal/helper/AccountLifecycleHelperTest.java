/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.internal.helper;

import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.engine.client.exception.FaroEngineClientException;
import com.liferay.osb.faro.engine.client.exception.NoSuchEntryException;
import com.liferay.osb.faro.engine.client.model.AccountLifecycle;
import com.liferay.osb.faro.engine.client.model.Results;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Caio Pinheiro
 */
public class AccountLifecycleHelperTest {

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(
			_accountLifecycleHelper, "_contactsEngineClient",
			_contactsEngineClient);
	}

	@Test
	public void testGetAccountLifecycle() {
		AccountLifecycle accountLifecycle = _mockAccountLifecycle(
			RandomTestUtil.randomString());

		_mockAccountLifecycles(accountLifecycle, _CHANNEL_ID, accountLifecycle);

		Assert.assertSame(
			accountLifecycle,
			_accountLifecycleHelper.getAccountLifecycle(
				_faroProject, String.valueOf(_CHANNEL_ID),
				accountLifecycle.getId()));
	}

	@Test
	public void testGetAccountLifecycleWithAnotherChannel() {
		AccountLifecycle accountLifecycle = _mockAccountLifecycle(
			RandomTestUtil.randomString());

		_mockAccountLifecycles(accountLifecycle, _CHANNEL_ID);

		_assertNotFoundException(
			accountLifecycle.getId(), String.valueOf(_CHANNEL_ID));
		_assertNotFoundException(
			accountLifecycle.getId(), RandomTestUtil.randomString());

		_mockAccountLifecycles(
			accountLifecycle, _CHANNEL_ID,
			_mockAccountLifecycle(RandomTestUtil.randomString()));

		_assertNotFoundException(
			accountLifecycle.getId(), String.valueOf(_CHANNEL_ID));
	}

	@Test
	public void testGetAccountLifecycleWithEngineError() {
		String accountLifecycleId = RandomTestUtil.randomString();
		FaroEngineClientException faroEngineClientException1 =
			new FaroEngineClientException(RandomTestUtil.randomString(), 500);

		Mockito.when(
			_contactsEngineClient.getAccountLifecycle(
				_faroProject, accountLifecycleId)
		).thenThrow(
			faroEngineClientException1
		);

		try {
			_accountLifecycleHelper.getAccountLifecycle(
				_faroProject, String.valueOf(_CHANNEL_ID), accountLifecycleId);

			Assert.fail();
		}
		catch (FaroEngineClientException faroEngineClientException2) {
			Assert.assertSame(
				faroEngineClientException1, faroEngineClientException2);
		}
	}

	@Test
	public void testGetAccountLifecycleWithNonexistentAccountLifecycle() {
		String accountLifecycleId1 = RandomTestUtil.randomString();

		Mockito.when(
			_contactsEngineClient.getAccountLifecycle(
				_faroProject, accountLifecycleId1)
		).thenThrow(
			new NoSuchEntryException(RandomTestUtil.randomString())
		);

		_assertNotFoundException(
			accountLifecycleId1, String.valueOf(_CHANNEL_ID));

		String accountLifecycleId2 = RandomTestUtil.randomString();

		Mockito.when(
			_contactsEngineClient.getAccountLifecycle(
				_faroProject, accountLifecycleId2)
		).thenThrow(
			new FaroEngineClientException(RandomTestUtil.randomString(), 400)
		);

		_assertNotFoundException(
			accountLifecycleId2, String.valueOf(_CHANNEL_ID));
	}

	private void _assertNotFoundException(
		String accountLifecycleId, String channelId) {

		try {
			_accountLifecycleHelper.getAccountLifecycle(
				_faroProject, channelId, accountLifecycleId);

			Assert.fail();
		}
		catch (NotFoundException notFoundException) {
			String expectedTitle =
				"No account lifecycle was found with ID " + accountLifecycleId;

			Assert.assertEquals(expectedTitle, notFoundException.getMessage());

			Response response = notFoundException.getResponse();

			Problem problem = (Problem)response.getEntity();

			Assert.assertEquals(Response.Status.NOT_FOUND, problem.getStatus());
			Assert.assertEquals(expectedTitle, problem.getTitle());
		}
	}

	private AccountLifecycle _mockAccountLifecycle(String accountLifecycleId) {
		AccountLifecycle accountLifecycle = new AccountLifecycle();

		accountLifecycle.setExternalReferenceCode(
			RandomTestUtil.randomString());
		accountLifecycle.setId(accountLifecycleId);

		Mockito.when(
			_contactsEngineClient.getAccountLifecycle(
				_faroProject, accountLifecycleId)
		).thenReturn(
			accountLifecycle
		);

		return accountLifecycle;
	}

	private void _mockAccountLifecycles(
		AccountLifecycle accountLifecycle, long channelId,
		AccountLifecycle... channelAccountLifecycles) {

		Mockito.when(
			_contactsEngineClient.getAccountLifecycles(
				_faroProject, channelId, null,
				accountLifecycle.getExternalReferenceCode(), null, 1, 1, null)
		).thenReturn(
			new Results<>(
				Arrays.asList(channelAccountLifecycles),
				channelAccountLifecycles.length)
		);
	}

	private static final long _CHANNEL_ID = RandomTestUtil.randomLong();

	private final AccountLifecycleHelper _accountLifecycleHelper =
		new AccountLifecycleHelper();
	private final ContactsEngineClient _contactsEngineClient = Mockito.mock(
		ContactsEngineClient.class);
	private final FaroProject _faroProject = Mockito.mock(FaroProject.class);

}