/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.engine.client.model.Results;
import com.liferay.osb.faro.engine.client.util.OrderByField;
import com.liferay.osb.faro.rest.resource.v1_0.InterestResource;
import com.liferay.osb.faro.service.FaroProjectLocalService;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Leslie Wong
 */
@RunWith(Arquillian.class)
public class InterestResourceTest extends BaseInterestResourceTestCase {

	@Override
	@Test
	public void testGetWorkspaceGroupChannelInterestsPage() throws Exception {
		long channelId = RandomTestUtil.randomLong();
		String individualId = RandomTestUtil.randomString();
		String search = RandomTestUtil.randomString();

		Object[] objects = _getWorkspaceGroupChannelInterestsPage(
			String.valueOf(channelId), individualId, search,
			new Sort[] {new Sort("score", true)});

		Assert.assertEquals(String.valueOf(channelId), objects[1]);
		Assert.assertEquals(individualId, objects[2]);
		Assert.assertNull(objects[3]);
		Assert.assertNull(objects[4]);
		Assert.assertEquals(search, objects[5]);
		Assert.assertNull(objects[6]);
		Assert.assertEquals(2, objects[7]);
		Assert.assertEquals(10, objects[8]);

		_assertOrderByField(
			"interestScore", OrderByField.OrderBy.desc, objects);

		_assertOrderByField(
			"ownerId", OrderByField.OrderBy.asc,
			_getWorkspaceGroupChannelInterestsPage(
				String.valueOf(channelId), null, null,
				new Sort[] {new Sort("individualId", false)}));
		_assertOrderByField(
			"name", OrderByField.OrderBy.asc,
			_getWorkspaceGroupChannelInterestsPage(
				String.valueOf(channelId), null, null,
				new Sort[] {new Sort("name", false)}));
		_assertOrderByField(
			"contributingPagesCount", OrderByField.OrderBy.desc,
			_getWorkspaceGroupChannelInterestsPage(
				String.valueOf(channelId), null, null,
				new Sort[] {new Sort("relatedPagesCount", true)}));

		objects = _getWorkspaceGroupChannelInterestsPage(
			String.valueOf(channelId), null, null, null);

		Assert.assertNull(objects[2]);
		Assert.assertNull(objects[5]);
		Assert.assertNull(objects[9]);
	}

	@Ignore
	@Override
	@Test
	public void testGetWorkspaceGroupChannelInterestsPageWithPagination()
		throws Exception {

		super.testGetWorkspaceGroupChannelInterestsPageWithPagination();
	}

	@Ignore
	@Override
	@Test
	public void testGetWorkspaceGroupChannelInterestsPageWithSortDateTime()
		throws Exception {

		super.testGetWorkspaceGroupChannelInterestsPageWithSortDateTime();
	}

	@Ignore
	@Override
	@Test
	public void testGetWorkspaceGroupChannelInterestsPageWithSortDouble()
		throws Exception {

		super.testGetWorkspaceGroupChannelInterestsPageWithSortDouble();
	}

	@Ignore
	@Override
	@Test
	public void testGetWorkspaceGroupChannelInterestsPageWithSortInteger()
		throws Exception {

		super.testGetWorkspaceGroupChannelInterestsPageWithSortInteger();
	}

	@Ignore
	@Override
	@Test
	public void testGetWorkspaceGroupChannelInterestsPageWithSortString()
		throws Exception {

		super.testGetWorkspaceGroupChannelInterestsPageWithSortString();
	}

	private void _assertOrderByField(
		String expectedFieldName, OrderByField.OrderBy expectedOrderBy,
		Object[] objects) {

		List<OrderByField> orderByFields = (List<OrderByField>)objects[9];

		Assert.assertEquals(orderByFields.toString(), 1, orderByFields.size());

		OrderByField orderByField = orderByFields.get(0);

		Assert.assertEquals(expectedFieldName, orderByField.getFieldName());
		Assert.assertEquals(expectedOrderBy, orderByField.getOrderBy());
	}

	private Object[] _getWorkspaceGroupChannelInterestsPage(
			String channelId, String individualId, String search, Sort[] sorts)
		throws Exception {

		AtomicReference<Object[]> atomicReference = new AtomicReference<>();

		InterestResource interestResource = ReflectionTestUtil.getFieldValue(
			this, "_interestResource");

		ContactsEngineClient contactsEngineClient =
			ReflectionTestUtil.getAndSetFieldValue(
				interestResource, "_contactsEngineClient",
				(ContactsEngineClient)ProxyUtil.newProxyInstance(
					ContactsEngineClient.class.getClassLoader(),
					new Class<?>[] {ContactsEngineClient.class},
					(proxy, method, arguments) -> {
						atomicReference.set(arguments);

						return new Results<>();
					}));
		FaroProjectLocalService faroProjectLocalService =
			ReflectionTestUtil.getAndSetFieldValue(
				interestResource, "_faroProjectLocalService",
				(FaroProjectLocalService)ProxyUtil.newProxyInstance(
					FaroProjectLocalService.class.getClassLoader(),
					new Class<?>[] {FaroProjectLocalService.class},
					(proxy, method, arguments) -> null));

		try {
			interestResource.getWorkspaceGroupChannelInterestsPage(
				testGroup.getGroupId(), channelId, individualId, search,
				Pagination.of(2, 10), sorts);
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				interestResource, "_contactsEngineClient",
				contactsEngineClient);
			ReflectionTestUtil.setFieldValue(
				interestResource, "_faroProjectLocalService",
				faroProjectLocalService);
		}

		return atomicReference.get();
	}

}