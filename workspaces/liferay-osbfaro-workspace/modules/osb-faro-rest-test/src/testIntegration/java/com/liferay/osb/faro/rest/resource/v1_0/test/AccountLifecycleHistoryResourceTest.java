/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.osb.faro.constants.FaroProjectConstants;
import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.rest.client.dto.v1_0.AccountLifecycleHistory;
import com.liferay.osb.faro.rest.client.problem.Problem;
import com.liferay.osb.faro.rest.resource.v1_0.test.util.ContactsEngineHttpServer;
import com.liferay.osb.faro.service.FaroChannelLocalService;
import com.liferay.osb.faro.service.FaroProjectLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;

import java.net.HttpURLConnection;

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
public class AccountLifecycleHistoryResourceTest
	extends BaseAccountLifecycleHistoryResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_contactsEngineHttpServer = new ContactsEngineHttpServer(
			_contactsEngineClient);

		_contactsEngineHttpServer.addLink(
			"account-lifecycle", "/account-lifecycles/{id}");
		_contactsEngineHttpServer.addLink(
			"account-lifecycle-status",
			"/account-lifecycles/{accountLifecycleId}/accounts/{id}/status");
		_contactsEngineHttpServer.addLink(
			"account-lifecycles",
			"/account-lifecycles{?channelId,externalReferenceCode,page,query," +
				"size,sort}");

		String externalReferenceCode = RandomTestUtil.randomString();

		_contactsEngineHttpServer.addJSON(
			"/account-lifecycles/" + _ACCOUNT_LIFECYCLE_ID,
			JSONUtil.put(
				"externalReferenceCode", externalReferenceCode
			).put(
				"id", _ACCOUNT_LIFECYCLE_ID
			).toString());

		_addAccountLifecyclesJSON(
			JSONUtil.putAll(
				JSONUtil.put(
					"externalReferenceCode", externalReferenceCode
				).put(
					"id", _ACCOUNT_LIFECYCLE_ID
				)));

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
	public void testGetWorkspaceGroupChannelAccountAccountLifecycleHistory()
		throws Exception {

		super.testGetWorkspaceGroupChannelAccountAccountLifecycleHistory();

		_testGetWorkspaceGroupChannelAccountAccountLifecycleHistoryWithAccountLifecycleOfAnotherChannel();
		_testGetWorkspaceGroupChannelAccountAccountLifecycleHistoryWithBadRequest();
		_testGetWorkspaceGroupChannelAccountAccountLifecycleHistoryWithEngineError();
		_testGetWorkspaceGroupChannelAccountAccountLifecycleHistoryWithNonexistentAccountLifecycleStatus();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"name"};
	}

	@Override
	protected AccountLifecycleHistory
			testGetWorkspaceGroupChannelAccountAccountLifecycleHistory_addAccountLifecycleHistory()
		throws Exception {

		AccountLifecycleHistory accountLifecycleHistory =
			randomAccountLifecycleHistory();

		_contactsEngineHttpServer.addJSON(
			_getAccountLifecycleStatusPath(),
			JSONUtil.put(
				"id", accountLifecycleHistory.getId()
			).put(
				"name", accountLifecycleHistory.getName()
			).put(
				"stages", JSONFactoryUtil.createJSONArray()
			).toString());

		return accountLifecycleHistory;
	}

	@Override
	protected String
			testGetWorkspaceGroupChannelAccountAccountLifecycleHistory_getAccountId()
		throws Exception {

		return _ACCOUNT_ID;
	}

	@Override
	protected String
			testGetWorkspaceGroupChannelAccountAccountLifecycleHistory_getAccountLifecycleId()
		throws Exception {

		return _ACCOUNT_LIFECYCLE_ID;
	}

	@Override
	protected String
			testGetWorkspaceGroupChannelAccountAccountLifecycleHistory_getChannelId()
		throws Exception {

		return _CHANNEL_ID;
	}

	@Override
	protected Long
			testGetWorkspaceGroupChannelAccountAccountLifecycleHistory_getGroupId()
		throws Exception {

		return _faroProject.getGroupId();
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

	private void _assertNotFound(String expectedTitle) throws Exception {
		try {
			accountLifecycleHistoryResource.
				getWorkspaceGroupChannelAccountAccountLifecycleHistory(
					_faroProject.getGroupId(), _CHANNEL_ID, _ACCOUNT_ID,
					_ACCOUNT_LIFECYCLE_ID);

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("NOT_FOUND", problem.getStatus());
			Assert.assertEquals(expectedTitle, problem.getTitle());
		}
	}

	private String _getAccountLifecycleStatusPath() {
		return StringBundler.concat(
			"/account-lifecycles/", _ACCOUNT_LIFECYCLE_ID, "/accounts/",
			_ACCOUNT_ID, "/status");
	}

	private void _testGetWorkspaceGroupChannelAccountAccountLifecycleHistoryWithAccountLifecycleOfAnotherChannel()
		throws Exception {

		_contactsEngineHttpServer.addJSON(
			_getAccountLifecycleStatusPath(),
			JSONUtil.put(
				"id", RandomTestUtil.randomString()
			).toString());

		_addAccountLifecyclesJSON(JSONFactoryUtil.createJSONArray());

		_assertNotFound(
			"No account lifecycle was found with ID " + _ACCOUNT_LIFECYCLE_ID);

		_addAccountLifecyclesJSON(
			JSONUtil.putAll(JSONUtil.put("id", _ACCOUNT_LIFECYCLE_ID)));
	}

	private void _testGetWorkspaceGroupChannelAccountAccountLifecycleHistoryWithBadRequest()
		throws Exception {

		_contactsEngineHttpServer.addJSON(
			_getAccountLifecycleStatusPath(),
			HttpURLConnection.HTTP_BAD_REQUEST, "{}");

		_assertNotFound("No account was found with ID " + _ACCOUNT_ID);
	}

	private void _testGetWorkspaceGroupChannelAccountAccountLifecycleHistoryWithEngineError()
		throws Exception {

		_contactsEngineHttpServer.addJSON(
			_getAccountLifecycleStatusPath(),
			HttpURLConnection.HTTP_INTERNAL_ERROR, "{}");

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.osb.faro.rest.internal.jaxrs.exception.mapper." +
					"FaroEngineClientExceptionMapper",
				LoggerTestUtil.ERROR)) {

			try {
				accountLifecycleHistoryResource.
					getWorkspaceGroupChannelAccountAccountLifecycleHistory(
						_faroProject.getGroupId(), _CHANNEL_ID, _ACCOUNT_ID,
						_ACCOUNT_LIFECYCLE_ID);

				Assert.fail();
			}
			catch (Problem.ProblemException problemException) {
				Problem problem = problemException.getProblem();

				Assert.assertEquals(
					"INTERNAL_SERVER_ERROR", problem.getStatus());
			}

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());
		}
	}

	private void _testGetWorkspaceGroupChannelAccountAccountLifecycleHistoryWithNonexistentAccountLifecycleStatus()
		throws Exception {

		_contactsEngineHttpServer.addJSON(
			_getAccountLifecycleStatusPath(), HttpURLConnection.HTTP_NOT_FOUND,
			"{}");

		_assertNotFound(
			"No account lifecycle was found with ID " + _ACCOUNT_LIFECYCLE_ID);
	}

	private static final String _ACCOUNT_ID = RandomTestUtil.randomString();

	private static final String _ACCOUNT_LIFECYCLE_ID = String.valueOf(
		RandomTestUtil.randomLong());

	private static final String _CHANNEL_ID = String.valueOf(
		RandomTestUtil.randomLong());

	@Inject
	private ContactsEngineClient _contactsEngineClient;

	private ContactsEngineHttpServer _contactsEngineHttpServer;

	@Inject
	private FaroChannelLocalService _faroChannelLocalService;

	private FaroProject _faroProject;

	@Inject
	private FaroProjectLocalService _faroProjectLocalService;

}