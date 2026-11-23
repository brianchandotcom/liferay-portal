/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.osb.faro.constants.FaroProjectConstants;
import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.rest.client.dto.v1_0.AccountLifecycleStageMetric;
import com.liferay.osb.faro.rest.client.pagination.Page;
import com.liferay.osb.faro.rest.client.pagination.Pagination;
import com.liferay.osb.faro.rest.client.problem.Problem;
import com.liferay.osb.faro.rest.resource.v1_0.test.util.ContactsEngineHttpServer;
import com.liferay.osb.faro.service.FaroChannelLocalService;
import com.liferay.osb.faro.service.FaroProjectLocalService;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.test.rule.Inject;

import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Leslie Wong
 */
@RunWith(Arquillian.class)
public class AccountLifecycleStageMetricResourceTest
	extends BaseAccountLifecycleStageMetricResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_contactsEngineHttpServer = new ContactsEngineHttpServer(
			_contactsEngineClient);

		_contactsEngineHttpServer.addLink(
			"account-lifecycle", "/account-lifecycles/{id}");
		_contactsEngineHttpServer.addLink(
			"account-lifecycle-stages",
			"/account-lifecycles/{id}/stages{?country,industry,segmentId}");
		_contactsEngineHttpServer.addLink(
			"account-lifecycles",
			"/account-lifecycles{?channelId,externalReferenceCode,page,query," +
				"size,sort}");

		_contactsEngineHttpServer.addJSON(
			"/account-lifecycles/" + _ACCOUNT_LIFECYCLE_ID,
			JSONUtil.put(
				"id", _ACCOUNT_LIFECYCLE_ID
			).toString());

		_addAccountLifecyclesJSON(
			JSONUtil.putAll(JSONUtil.put("id", _ACCOUNT_LIFECYCLE_ID)));
		_addAccountLifecycleStageMetricsJSON();

		_faroProject = _faroProjectLocalService.addFaroProject(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(), null,
			null, null, null, Collections.emptyList(), null, null, null, null,
			null, FaroProjectConstants.STATE_READY, "{}", "UTC",
			RandomTestUtil.randomString());

		_faroChannelLocalService.addFaroChannel(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			_CHANNEL_ID, _faroProject.getGroupId());
	}

	@After
	@Override
	public void tearDown() throws Exception {
		_contactsEngineHttpServer.close();

		_faroProjectLocalService.deleteFaroProjectByGroupId(
			_faroProject.getGroupId());

		super.tearDown();
	}

	@Override
	@Test
	public void testGetWorkspaceGroupChannelAccountLifecycleStageMetricsPage()
		throws Exception {

		super.testGetWorkspaceGroupChannelAccountLifecycleStageMetricsPage();

		_testGetWorkspaceGroupChannelAccountLifecycleStageMetricsPageWithAccountLifecycleOfAnotherChannel();
		_testGetWorkspaceGroupChannelAccountLifecycleStageMetricsPageWithFilters();
		_testGetWorkspaceGroupChannelAccountLifecycleStageMetricsPageWithoutAccountLifecycleStageMetrics();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"accountsCount", "description", "name", "percentage", "position"
		};
	}

	@Override
	protected AccountLifecycleStageMetric
			testGetWorkspaceGroupChannelAccountLifecycleStageMetricsPage_addAccountLifecycleStageMetric(
				Long groupId, String channelId, String accountLifecycleId,
				AccountLifecycleStageMetric accountLifecycleStageMetric)
		throws Exception {

		_accountLifecycleStageMetricsJSONArray.put(
			JSONUtil.put(
				"accountsCount", accountLifecycleStageMetric.getAccountsCount()
			).put(
				"averageStageDuration",
				accountLifecycleStageMetric.getAverageStageDuration()
			).put(
				"conversionRateToNextStage",
				accountLifecycleStageMetric.getConversionRateToNextStage()
			).put(
				"description", accountLifecycleStageMetric.getDescription()
			).put(
				"id", RandomTestUtil.randomString()
			).put(
				"name", accountLifecycleStageMetric.getName()
			).put(
				"percentage", accountLifecycleStageMetric.getPercentage()
			).put(
				"position", accountLifecycleStageMetric.getPosition()
			));

		_addAccountLifecycleStageMetricsJSON();

		return accountLifecycleStageMetric;
	}

	@Override
	protected String
			testGetWorkspaceGroupChannelAccountLifecycleStageMetricsPage_getAccountLifecycleId()
		throws Exception {

		return _ACCOUNT_LIFECYCLE_ID;
	}

	@Override
	protected String
			testGetWorkspaceGroupChannelAccountLifecycleStageMetricsPage_getChannelId()
		throws Exception {

		return _CHANNEL_ID;
	}

	@Override
	protected Long
			testGetWorkspaceGroupChannelAccountLifecycleStageMetricsPage_getGroupId()
		throws Exception {

		return _faroProject.getGroupId();
	}

	private void _addAccountLifecycleStageMetricsJSON() {
		_contactsEngineHttpServer.addJSON(
			"/account-lifecycles/" + _ACCOUNT_LIFECYCLE_ID + "/stages",
			_accountLifecycleStageMetricsJSONArray.toString());
	}

	private void _addAccountLifecyclesJSON(
		JSONArray accountLifecyclesJSONArray) {

		_contactsEngineHttpServer.addJSON(
			"/account-lifecycles",
			JSONUtil.put(
				"_embedded",
				JSONUtil.put("account-lifecycles", accountLifecyclesJSONArray)
			).put(
				"page",
				JSONUtil.put(
					"number", 0
				).put(
					"size", accountLifecyclesJSONArray.length()
				).put(
					"totalElements", accountLifecyclesJSONArray.length()
				).put(
					"totalPages", 1
				)
			).toString());
	}

	private void _assertParameter(
		String expectedValue, String location, String name) {

		Assert.assertEquals(
			expectedValue,
			URLCodec.decodeURL(
				HttpComponentsUtil.getParameter(location, name, false)));
	}

	private void _testGetWorkspaceGroupChannelAccountLifecycleStageMetricsPageWithAccountLifecycleOfAnotherChannel()
		throws Exception {

		_addAccountLifecyclesJSON(JSONFactoryUtil.createJSONArray());

		try {
			accountLifecycleStageMetricResource.
				getWorkspaceGroupChannelAccountLifecycleStageMetricsPage(
					_faroProject.getGroupId(), _CHANNEL_ID,
					_ACCOUNT_LIFECYCLE_ID, null, null, null,
					Pagination.of(1, 20));

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("NOT_FOUND", problem.getStatus());
			Assert.assertEquals(
				"No account lifecycle was found with ID " +
					_ACCOUNT_LIFECYCLE_ID,
				problem.getTitle());
		}

		_addAccountLifecyclesJSON(
			JSONUtil.putAll(JSONUtil.put("id", _ACCOUNT_LIFECYCLE_ID)));
	}

	private void _testGetWorkspaceGroupChannelAccountLifecycleStageMetricsPageWithFilters()
		throws Exception {

		String country = RandomTestUtil.randomString();
		String industry = RandomTestUtil.randomString();
		long segmentId = RandomTestUtil.randomLong();

		accountLifecycleStageMetricResource.
			getWorkspaceGroupChannelAccountLifecycleStageMetricsPage(
				_faroProject.getGroupId(), _CHANNEL_ID, _ACCOUNT_LIFECYCLE_ID,
				country, industry, segmentId, Pagination.of(1, 20));

		String location = _contactsEngineHttpServer.getLocation();

		_assertParameter(country, location, "country");
		_assertParameter(industry, location, "industry");
		_assertParameter(String.valueOf(segmentId), location, "segmentId");
	}

	private void _testGetWorkspaceGroupChannelAccountLifecycleStageMetricsPageWithoutAccountLifecycleStageMetrics()
		throws Exception {

		_contactsEngineHttpServer.addJSON(
			"/account-lifecycles/" + _ACCOUNT_LIFECYCLE_ID + "/stages", "null");

		Page<AccountLifecycleStageMetric> accountLifecycleStageMetricsPage =
			accountLifecycleStageMetricResource.
				getWorkspaceGroupChannelAccountLifecycleStageMetricsPage(
					_faroProject.getGroupId(), _CHANNEL_ID,
					_ACCOUNT_LIFECYCLE_ID, null, null, null,
					Pagination.of(1, 20));

		Assert.assertEquals(
			Collections.emptyList(),
			(List<AccountLifecycleStageMetric>)
				accountLifecycleStageMetricsPage.getItems());
		Assert.assertEquals(
			0, accountLifecycleStageMetricsPage.getTotalCount());
	}

	private static final String _ACCOUNT_LIFECYCLE_ID = String.valueOf(
		RandomTestUtil.randomLong());

	private static final String _CHANNEL_ID = String.valueOf(
		RandomTestUtil.randomLong());

	private final JSONArray _accountLifecycleStageMetricsJSONArray =
		JSONFactoryUtil.createJSONArray();

	@Inject
	private ContactsEngineClient _contactsEngineClient;

	private ContactsEngineHttpServer _contactsEngineHttpServer;

	@Inject
	private FaroChannelLocalService _faroChannelLocalService;

	private FaroProject _faroProject;

	@Inject
	private FaroProjectLocalService _faroProjectLocalService;

}