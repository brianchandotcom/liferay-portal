/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.internal.resource.v1_0;

import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.engine.client.model.Results;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.rest.dto.v1_0.AccountLifecycle;
import com.liferay.osb.faro.service.FaroProjectLocalService;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Caio Pinheiro
 */
public class AccountLifecycleResourceImplTest {

	@Before
	public void setUp() throws Exception {
		Mockito.when(
			_accountLifecycleDTOConverter.toDTO(
				Mockito.any(DTOConverterContext.class),
				Mockito.eq(_engineClientAccountLifecycle))
		).thenReturn(
			_accountLifecycle
		);

		Mockito.when(
			_faroProjectLocalService.getFaroProjectByGroupId(_GROUP_ID)
		).thenReturn(
			_faroProject
		);

		_accountLifecycleResourceImpl.setContextAcceptLanguage(
			Mockito.mock(AcceptLanguage.class));

		ReflectionTestUtil.setFieldValue(
			_accountLifecycleResourceImpl, "_accountLifecycleDTOConverter",
			_accountLifecycleDTOConverter);
		ReflectionTestUtil.setFieldValue(
			_accountLifecycleResourceImpl, "_contactsEngineClient",
			_contactsEngineClient);
		ReflectionTestUtil.setFieldValue(
			_accountLifecycleResourceImpl, "_faroProjectLocalService",
			_faroProjectLocalService);
	}

	@Test
	public void testGetWorkspaceGroupChannelAccountLifecyclesPage()
		throws Exception {

		String fieldName = RandomTestUtil.randomString();

		_assertGetWorkspaceGroupChannelAccountLifecyclesPage(
			fieldName + ":desc",
			new Sort[] {new Sort(fieldName, Sort.STRING_TYPE, true)});
	}

	@Test
	public void testGetWorkspaceGroupChannelAccountLifecyclesPageWithDateModifiedSort()
		throws Exception {

		_assertGetWorkspaceGroupChannelAccountLifecyclesPage(
			"modifiedDate:asc",
			new Sort[] {new Sort("dateModified", Sort.LONG_TYPE, false)});
	}

	@Test
	public void testGetWorkspaceGroupChannelAccountLifecyclesPageWithoutSort()
		throws Exception {

		_assertGetWorkspaceGroupChannelAccountLifecyclesPage("name:asc", null);
	}

	private void _assertGetWorkspaceGroupChannelAccountLifecyclesPage(
			String expectedSortString, Sort[] sorts)
		throws Exception {

		long channelId = RandomTestUtil.randomLong();
		int page = RandomTestUtil.randomInt();
		int pageSize = RandomTestUtil.randomInt();
		String search = RandomTestUtil.randomString();
		int total = RandomTestUtil.randomInt();

		Mockito.when(
			_contactsEngineClient.getAccountLifecycles(
				_faroProject, channelId, null, null, search, page, pageSize,
				expectedSortString)
		).thenReturn(
			new Results<>(List.of(_engineClientAccountLifecycle), total)
		);

		Page<AccountLifecycle> accountLifecyclesPage =
			_accountLifecycleResourceImpl.
				getWorkspaceGroupChannelAccountLifecyclesPage(
					_GROUP_ID, String.valueOf(channelId), search,
					Pagination.of(page, pageSize), sorts);

		Assert.assertEquals(
			List.of(_accountLifecycle), accountLifecyclesPage.getItems());
		Assert.assertEquals(total, accountLifecyclesPage.getTotalCount());
	}

	private static final long _GROUP_ID = RandomTestUtil.randomLong();

	private final AccountLifecycle _accountLifecycle = new AccountLifecycle();
	private final DTOConverter
		<com.liferay.osb.faro.engine.client.model.AccountLifecycle,
		 AccountLifecycle> _accountLifecycleDTOConverter = Mockito.mock(
			DTOConverter.class);
	private final AccountLifecycleResourceImpl _accountLifecycleResourceImpl =
		new AccountLifecycleResourceImpl();
	private final ContactsEngineClient _contactsEngineClient = Mockito.mock(
		ContactsEngineClient.class);
	private final com.liferay.osb.faro.engine.client.model.AccountLifecycle
		_engineClientAccountLifecycle =
			new com.liferay.osb.faro.engine.client.model.AccountLifecycle();
	private final FaroProject _faroProject = Mockito.mock(FaroProject.class);
	private final FaroProjectLocalService _faroProjectLocalService =
		Mockito.mock(FaroProjectLocalService.class);

}