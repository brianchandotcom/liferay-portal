/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.osb.faro.constants.FaroProjectConstants;
import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.rest.client.dto.v1_0.AccountLifecycle;
import com.liferay.osb.faro.rest.client.pagination.Pagination;
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
import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Leslie Wong
 */
@RunWith(Arquillian.class)
public class AccountLifecycleResourceTest
	extends BaseAccountLifecycleResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_contactsEngineHttpServer = new ContactsEngineHttpServer(
			_contactsEngineClient);

		_contactsEngineHttpServer.addLink(
			"account-lifecycles",
			"/account-lifecycles{?channelId,externalReferenceCode,page,query," +
				"size,sort}");

		_addAccountLifecyclesJSON();

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
	public void testGetWorkspaceGroupChannelAccountLifecyclesPage()
		throws Exception {

		super.testGetWorkspaceGroupChannelAccountLifecyclesPage();

		_testGetWorkspaceGroupChannelAccountLifecyclesPageWithDateModifiedSort();
		_testGetWorkspaceGroupChannelAccountLifecyclesPageWithSort();
		_testGetWorkspaceGroupChannelAccountLifecyclesPageWithoutSort();
	}

	@Ignore
	@Override
	@Test
	public void testGetWorkspaceGroupChannelAccountLifecyclesPageWithPagination()
		throws Exception {

		super.testGetWorkspaceGroupChannelAccountLifecyclesPageWithPagination();
	}

	@Ignore
	@Override
	@Test
	public void testGetWorkspaceGroupChannelAccountLifecyclesPageWithSortDateTime()
		throws Exception {

		super.
			testGetWorkspaceGroupChannelAccountLifecyclesPageWithSortDateTime();
	}

	@Ignore
	@Override
	@Test
	public void testGetWorkspaceGroupChannelAccountLifecyclesPageWithSortDouble()
		throws Exception {

		super.testGetWorkspaceGroupChannelAccountLifecyclesPageWithSortDouble();
	}

	@Ignore
	@Override
	@Test
	public void testGetWorkspaceGroupChannelAccountLifecyclesPageWithSortInteger()
		throws Exception {

		super.
			testGetWorkspaceGroupChannelAccountLifecyclesPageWithSortInteger();
	}

	@Ignore
	@Override
	@Test
	public void testGetWorkspaceGroupChannelAccountLifecyclesPageWithSortString()
		throws Exception {

		super.testGetWorkspaceGroupChannelAccountLifecyclesPageWithSortString();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"description", "name"};
	}

	@Override
	protected AccountLifecycle
			testGetWorkspaceGroupChannelAccountLifecyclesPage_addAccountLifecycle(
				Long groupId, String channelId,
				AccountLifecycle accountLifecycle)
		throws Exception {

		Date dateModified = accountLifecycle.getDateModified();

		_accountLifecyclesJSONArray.put(
			JSONUtil.put(
				"defaultAccountLifecycle",
				accountLifecycle.getDefaultAccountLifecycle()
			).put(
				"description", accountLifecycle.getDescription()
			).put(
				"id", accountLifecycle.getId()
			).put(
				"modifiedDate", dateModified.getTime()
			).put(
				"name", accountLifecycle.getName()
			).put(
				"stages", JSONFactoryUtil.createJSONArray()
			));

		_addAccountLifecyclesJSON();

		return accountLifecycle;
	}

	@Override
	protected String
			testGetWorkspaceGroupChannelAccountLifecyclesPage_getChannelId()
		throws Exception {

		return _CHANNEL_ID;
	}

	@Override
	protected Long
			testGetWorkspaceGroupChannelAccountLifecyclesPage_getGroupId()
		throws Exception {

		return _faroProject.getGroupId();
	}

	private void _addAccountLifecyclesJSON() {
		_contactsEngineHttpServer.addJSON(
			"/account-lifecycles",
			JSONUtil.put(
				"_embedded",
				JSONUtil.put("account-lifecycles", _accountLifecyclesJSONArray)
			).put(
				"page",
				JSONUtil.put(
					"number", 0
				).put(
					"size", _accountLifecyclesJSONArray.length()
				).put(
					"totalElements", _accountLifecyclesJSONArray.length()
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

	private String _getLocation(String search, String sortString)
		throws Exception {

		accountLifecycleResource.getWorkspaceGroupChannelAccountLifecyclesPage(
			_faroProject.getGroupId(), _CHANNEL_ID, search,
			Pagination.of(2, 10), sortString);

		return _contactsEngineHttpServer.getLocation();
	}

	private void _testGetWorkspaceGroupChannelAccountLifecyclesPageWithDateModifiedSort()
		throws Exception {

		String search = RandomTestUtil.randomString();

		String location = _getLocation(search, "dateModified:asc");

		_assertParameter(_CHANNEL_ID, location, "channelId");
		_assertParameter("1", location, "page");
		_assertParameter(search, location, "query");
		_assertParameter("10", location, "size");
		_assertParameter("modifiedDate,asc", location, "sort");
	}

	private void _testGetWorkspaceGroupChannelAccountLifecyclesPageWithSort()
		throws Exception {

		_assertParameter(
			"id,desc", _getLocation(RandomTestUtil.randomString(), "id:desc"),
			"sort");
	}

	private void _testGetWorkspaceGroupChannelAccountLifecyclesPageWithoutSort()
		throws Exception {

		_assertParameter(
			"name,asc", _getLocation(RandomTestUtil.randomString(), null),
			"sort");
	}

	private static final String _CHANNEL_ID = String.valueOf(
		RandomTestUtil.randomLong());

	private final JSONArray _accountLifecyclesJSONArray =
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