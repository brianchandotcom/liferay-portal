/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.internal.resource.v1_0;

import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.rest.dto.v1_0.AccountLifecycleStageMetric;
import com.liferay.osb.faro.rest.internal.helper.AccountLifecycleHelper;
import com.liferay.osb.faro.service.FaroProjectLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import jakarta.ws.rs.NotFoundException;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Caio Pinheiro
 */
public class AccountLifecycleStageMetricResourceImplTest {

	@Before
	public void setUp() throws Exception {
		Mockito.when(
			_faroProjectLocalService.getFaroProjectByGroupId(_GROUP_ID)
		).thenReturn(
			_faroProject
		);

		_accountLifecycleStageMetricResourceImpl.setContextAcceptLanguage(
			Mockito.mock(AcceptLanguage.class));

		ReflectionTestUtil.setFieldValue(
			_accountLifecycleStageMetricResourceImpl, "_accountLifecycleHelper",
			_accountLifecycleHelper);
		ReflectionTestUtil.setFieldValue(
			_accountLifecycleStageMetricResourceImpl,
			"_accountLifecycleStageMetricDTOConverter",
			_accountLifecycleStageMetricDTOConverter);
		ReflectionTestUtil.setFieldValue(
			_accountLifecycleStageMetricResourceImpl, "_contactsEngineClient",
			_contactsEngineClient);
		ReflectionTestUtil.setFieldValue(
			_accountLifecycleStageMetricResourceImpl,
			"_faroProjectLocalService", _faroProjectLocalService);
	}

	@Test
	public void testGetWorkspaceGroupChannelAccountLifecycleStageMetricsPage()
		throws Exception {

		String country = RandomTestUtil.randomString();
		String industry = RandomTestUtil.randomString();
		long segmentId = RandomTestUtil.randomLong();

		com.liferay.osb.faro.engine.client.model.AccountLifecycleStageMetric
			engineClientAccountLifecycleStageMetric1 =
				_createEngineClientAccountLifecycleStageMetric();
		com.liferay.osb.faro.engine.client.model.AccountLifecycleStageMetric
			engineClientAccountLifecycleStageMetric2 =
				_createEngineClientAccountLifecycleStageMetric();
		com.liferay.osb.faro.engine.client.model.AccountLifecycleStageMetric
			engineClientAccountLifecycleStageMetric3 =
				_createEngineClientAccountLifecycleStageMetric();

		Mockito.when(
			_contactsEngineClient.getAccountLifecycleStageMetrics(
				_faroProject, country, _ACCOUNT_LIFECYCLE_ID, industry,
				segmentId)
		).thenReturn(
			List.of(
				engineClientAccountLifecycleStageMetric1,
				engineClientAccountLifecycleStageMetric2,
				engineClientAccountLifecycleStageMetric3)
		);

		AccountLifecycleStageMetric accountLifecycleStageMetric =
			new AccountLifecycleStageMetric();

		Mockito.when(
			_accountLifecycleStageMetricDTOConverter.toDTO(
				Mockito.any(DTOConverterContext.class),
				Mockito.eq(engineClientAccountLifecycleStageMetric3))
		).thenReturn(
			accountLifecycleStageMetric
		);

		Page<AccountLifecycleStageMetric> accountLifecycleStageMetricsPage =
			_accountLifecycleStageMetricResourceImpl.
				getWorkspaceGroupChannelAccountLifecycleStageMetricsPage(
					_GROUP_ID, _CHANNEL_ID, _ACCOUNT_LIFECYCLE_ID, country,
					industry, segmentId, Pagination.of(2, 2));

		Assert.assertEquals(
			List.of(accountLifecycleStageMetric),
			accountLifecycleStageMetricsPage.getItems());
		Assert.assertEquals(
			3, accountLifecycleStageMetricsPage.getTotalCount());

		Mockito.verify(
			_accountLifecycleHelper
		).getAccountLifecycle(
			_faroProject, _CHANNEL_ID, _ACCOUNT_LIFECYCLE_ID
		);
	}

	@Test
	public void testGetWorkspaceGroupChannelAccountLifecycleStageMetricsPageWithNotFoundException()
		throws Exception {

		NotFoundException notFoundException1 = new NotFoundException();

		Mockito.when(
			_accountLifecycleHelper.getAccountLifecycle(
				_faroProject, _CHANNEL_ID, _ACCOUNT_LIFECYCLE_ID)
		).thenThrow(
			notFoundException1
		);

		try {
			_accountLifecycleStageMetricResourceImpl.
				getWorkspaceGroupChannelAccountLifecycleStageMetricsPage(
					_GROUP_ID, _CHANNEL_ID, _ACCOUNT_LIFECYCLE_ID, null, null,
					null, Pagination.of(1, 20));

			Assert.fail();
		}
		catch (NotFoundException notFoundException2) {
			Assert.assertSame(notFoundException1, notFoundException2);

			Mockito.verify(
				_contactsEngineClient, Mockito.never()
			).getAccountLifecycleStageMetrics(
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any()
			);
		}
	}

	@Test
	public void testGetWorkspaceGroupChannelAccountLifecycleStageMetricsPageWithoutAccountLifecycleStageMetrics()
		throws Exception {

		Page<AccountLifecycleStageMetric> accountLifecycleStageMetricsPage =
			_accountLifecycleStageMetricResourceImpl.
				getWorkspaceGroupChannelAccountLifecycleStageMetricsPage(
					_GROUP_ID, _CHANNEL_ID, _ACCOUNT_LIFECYCLE_ID, null, null,
					null, Pagination.of(1, 20));

		Assert.assertEquals(
			List.of(), (List<?>)accountLifecycleStageMetricsPage.getItems());
		Assert.assertEquals(
			0, accountLifecycleStageMetricsPage.getTotalCount());
	}

	private com.liferay.osb.faro.engine.client.model.AccountLifecycleStageMetric
		_createEngineClientAccountLifecycleStageMetric() {

		com.liferay.osb.faro.engine.client.model.AccountLifecycleStageMetric
			engineClientAccountLifecycleStageMetric =
				new com.liferay.osb.faro.engine.client.model.
					AccountLifecycleStageMetric();

		engineClientAccountLifecycleStageMetric.setId(
			RandomTestUtil.randomString());

		return engineClientAccountLifecycleStageMetric;
	}

	private static final String _ACCOUNT_LIFECYCLE_ID =
		RandomTestUtil.randomString();

	private static final String _CHANNEL_ID = RandomTestUtil.randomString();

	private static final long _GROUP_ID = RandomTestUtil.randomLong();

	private final AccountLifecycleHelper _accountLifecycleHelper = Mockito.mock(
		AccountLifecycleHelper.class);
	private final DTOConverter
		<com.liferay.osb.faro.engine.client.model.AccountLifecycleStageMetric,
		 AccountLifecycleStageMetric> _accountLifecycleStageMetricDTOConverter =
			Mockito.mock(DTOConverter.class);
	private final AccountLifecycleStageMetricResourceImpl
		_accountLifecycleStageMetricResourceImpl =
			new AccountLifecycleStageMetricResourceImpl();
	private final ContactsEngineClient _contactsEngineClient = Mockito.mock(
		ContactsEngineClient.class);
	private final FaroProject _faroProject = Mockito.mock(FaroProject.class);
	private final FaroProjectLocalService _faroProjectLocalService =
		Mockito.mock(FaroProjectLocalService.class);

}