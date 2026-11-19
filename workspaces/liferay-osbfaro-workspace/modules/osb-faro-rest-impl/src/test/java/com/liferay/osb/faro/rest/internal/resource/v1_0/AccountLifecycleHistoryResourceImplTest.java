/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.internal.resource.v1_0;

import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.engine.client.exception.FaroEngineClientException;
import com.liferay.osb.faro.engine.client.exception.NoSuchEntryException;
import com.liferay.osb.faro.engine.client.model.AccountLifecycleStatus;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.rest.dto.v1_0.AccountLifecycleHistory;
import com.liferay.osb.faro.rest.internal.helper.AccountLifecycleHelper;
import com.liferay.osb.faro.service.FaroProjectLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Caio Pinheiro
 */
public class AccountLifecycleHistoryResourceImplTest {

	@Before
	public void setUp() throws Exception {
		Mockito.when(
			_faroProjectLocalService.getFaroProjectByGroupId(_GROUP_ID)
		).thenReturn(
			_faroProject
		);

		_accountLifecycleHistoryResourceImpl.setContextAcceptLanguage(
			Mockito.mock(AcceptLanguage.class));

		ReflectionTestUtil.setFieldValue(
			_accountLifecycleHistoryResourceImpl, "_accountLifecycleHelper",
			_accountLifecycleHelper);
		ReflectionTestUtil.setFieldValue(
			_accountLifecycleHistoryResourceImpl,
			"_accountLifecycleHistoryDTOConverter",
			_accountLifecycleHistoryDTOConverter);
		ReflectionTestUtil.setFieldValue(
			_accountLifecycleHistoryResourceImpl, "_contactsEngineClient",
			_contactsEngineClient);
		ReflectionTestUtil.setFieldValue(
			_accountLifecycleHistoryResourceImpl, "_faroProjectLocalService",
			_faroProjectLocalService);
	}

	@Test
	public void testGetWorkspaceGroupChannelAccountAccountLifecycleHistory()
		throws Exception {

		AccountLifecycleStatus accountLifecycleStatus =
			new AccountLifecycleStatus();

		Mockito.when(
			_contactsEngineClient.getAccountLifecycleStatus(
				_faroProject, _ACCOUNT_LIFECYCLE_ID, _ACCOUNT_ID)
		).thenReturn(
			accountLifecycleStatus
		);

		AccountLifecycleHistory accountLifecycleHistory =
			new AccountLifecycleHistory();

		Mockito.when(
			_accountLifecycleHistoryDTOConverter.toDTO(
				Mockito.any(DTOConverterContext.class),
				Mockito.eq(accountLifecycleStatus))
		).thenReturn(
			accountLifecycleHistory
		);

		Assert.assertSame(
			accountLifecycleHistory,
			_getWorkspaceGroupChannelAccountAccountLifecycleHistory());

		Mockito.verify(
			_accountLifecycleHelper
		).getAccountLifecycle(
			_faroProject, _CHANNEL_ID, _ACCOUNT_LIFECYCLE_ID
		);
	}

	@Test
	public void testGetWorkspaceGroupChannelAccountAccountLifecycleHistoryWithBadRequest()
		throws Exception {

		Mockito.when(
			_contactsEngineClient.getAccountLifecycleStatus(
				_faroProject, _ACCOUNT_LIFECYCLE_ID, _ACCOUNT_ID)
		).thenThrow(
			new FaroEngineClientException(RandomTestUtil.randomString(), 400)
		);

		try {
			_getWorkspaceGroupChannelAccountAccountLifecycleHistory();

			Assert.fail();
		}
		catch (NotFoundException notFoundException) {
			Response response = notFoundException.getResponse();

			Problem problem = (Problem)response.getEntity();

			Assert.assertEquals(Response.Status.NOT_FOUND, problem.getStatus());
			Assert.assertEquals(
				"No account was found with ID " + _ACCOUNT_ID,
				problem.getTitle());
		}
	}

	@Test
	public void testGetWorkspaceGroupChannelAccountAccountLifecycleHistoryWithEngineError()
		throws Exception {

		FaroEngineClientException faroEngineClientException1 =
			new FaroEngineClientException(RandomTestUtil.randomString(), 500);

		Mockito.when(
			_contactsEngineClient.getAccountLifecycleStatus(
				_faroProject, _ACCOUNT_LIFECYCLE_ID, _ACCOUNT_ID)
		).thenThrow(
			faroEngineClientException1
		);

		try {
			_getWorkspaceGroupChannelAccountAccountLifecycleHistory();

			Assert.fail();
		}
		catch (FaroEngineClientException faroEngineClientException2) {
			Assert.assertSame(
				faroEngineClientException1, faroEngineClientException2);
		}
	}

	@Test
	public void testGetWorkspaceGroupChannelAccountAccountLifecycleHistoryWithNoSuchEntryException()
		throws Exception {

		Mockito.when(
			_contactsEngineClient.getAccountLifecycleStatus(
				_faroProject, _ACCOUNT_LIFECYCLE_ID, _ACCOUNT_ID)
		).thenThrow(
			new NoSuchEntryException(RandomTestUtil.randomString())
		);

		try {
			_getWorkspaceGroupChannelAccountAccountLifecycleHistory();

			Assert.fail();
		}
		catch (NotFoundException notFoundException) {
			Response response = notFoundException.getResponse();

			Problem problem = (Problem)response.getEntity();

			Assert.assertEquals(Response.Status.NOT_FOUND, problem.getStatus());
			Assert.assertEquals(
				"No account lifecycle was found with ID " +
					_ACCOUNT_LIFECYCLE_ID,
				problem.getTitle());
		}
	}

	@Test
	public void testGetWorkspaceGroupChannelAccountAccountLifecycleHistoryWithNotFoundException()
		throws Exception {

		NotFoundException notFoundException1 = new NotFoundException();

		Mockito.when(
			_accountLifecycleHelper.getAccountLifecycle(
				_faroProject, _CHANNEL_ID, _ACCOUNT_LIFECYCLE_ID)
		).thenThrow(
			notFoundException1
		);

		try {
			_getWorkspaceGroupChannelAccountAccountLifecycleHistory();

			Assert.fail();
		}
		catch (NotFoundException notFoundException2) {
			Assert.assertSame(notFoundException1, notFoundException2);

			Mockito.verify(
				_contactsEngineClient, Mockito.never()
			).getAccountLifecycleStatus(
				Mockito.any(), Mockito.any(), Mockito.any()
			);
		}
	}

	private AccountLifecycleHistory
			_getWorkspaceGroupChannelAccountAccountLifecycleHistory()
		throws Exception {

		return _accountLifecycleHistoryResourceImpl.
			getWorkspaceGroupChannelAccountAccountLifecycleHistory(
				_GROUP_ID, _CHANNEL_ID, _ACCOUNT_ID, _ACCOUNT_LIFECYCLE_ID);
	}

	private static final String _ACCOUNT_ID = RandomTestUtil.randomString();

	private static final String _ACCOUNT_LIFECYCLE_ID =
		RandomTestUtil.randomString();

	private static final String _CHANNEL_ID = RandomTestUtil.randomString();

	private static final long _GROUP_ID = RandomTestUtil.randomLong();

	private final AccountLifecycleHelper _accountLifecycleHelper = Mockito.mock(
		AccountLifecycleHelper.class);
	private final DTOConverter<AccountLifecycleStatus, AccountLifecycleHistory>
		_accountLifecycleHistoryDTOConverter = Mockito.mock(DTOConverter.class);
	private final AccountLifecycleHistoryResourceImpl
		_accountLifecycleHistoryResourceImpl =
			new AccountLifecycleHistoryResourceImpl();
	private final ContactsEngineClient _contactsEngineClient = Mockito.mock(
		ContactsEngineClient.class);
	private final FaroProject _faroProject = Mockito.mock(FaroProject.class);
	private final FaroProjectLocalService _faroProjectLocalService =
		Mockito.mock(FaroProjectLocalService.class);

}