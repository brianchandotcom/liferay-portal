/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.engine.client.model.Provider;
import com.liferay.osb.faro.engine.client.model.Results;
import com.liferay.osb.faro.engine.client.model.provider.CSVProvider;
import com.liferay.osb.faro.engine.client.model.provider.DemandbaseProvider;
import com.liferay.osb.faro.engine.client.model.provider.HubSpotProvider;
import com.liferay.osb.faro.engine.client.model.provider.LiferayProvider;
import com.liferay.osb.faro.engine.client.model.provider.MarketoCampaignProvider;
import com.liferay.osb.faro.engine.client.model.provider.MarketoProvider;
import com.liferay.osb.faro.engine.client.model.provider.SalesforceProvider;
import com.liferay.osb.faro.engine.client.util.OrderByField;
import com.liferay.osb.faro.rest.dto.v1_0.DataSource;
import com.liferay.osb.faro.rest.resource.v1_0.DataSourceResource;
import com.liferay.osb.faro.service.FaroProjectLocalService;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Leslie Wong
 */
@RunWith(Arquillian.class)
public class DataSourceResourceTest extends BaseDataSourceResourceTestCase {

	@Override
	@Test
	public void testGetWorkspaceGroupDataSource() throws Exception {
		_assertCounts(null, null, null, null, new CSVProvider());
		_assertCounts(1L, null, null, null, new DemandbaseProvider());
		_assertCounts(null, null, 3L, 4L, new HubSpotProvider());
		_assertCounts(null, null, null, 5L, new LiferayProvider());
		_assertCounts(null, null, 3L, 4L, new MarketoProvider());
		_assertCounts(1L, null, null, 4L, new MarketoCampaignProvider());
		_assertCounts(1L, 2L, null, 4L, new SalesforceProvider());
	}

	@Override
	@Test
	public void testGetWorkspaceGroupDataSourcesPage() throws Exception {
		String search = RandomTestUtil.randomString();

		Object[] objects = _getWorkspaceGroupDataSourcesPage(
			"LIFERAY", search, "READY", new Sort[] {new Sort("name", true)});

		Assert.assertNull(objects[1]);
		Assert.assertEquals(search, objects[2]);
		Assert.assertNull(objects[3]);
		Assert.assertEquals("LIFERAY", objects[4]);
		Assert.assertEquals(Collections.singletonList("READY"), objects[5]);
		Assert.assertEquals(2, objects[6]);
		Assert.assertEquals(10, objects[7]);

		List<OrderByField> orderByFields = (List<OrderByField>)objects[8];

		Assert.assertEquals(orderByFields.toString(), 1, orderByFields.size());

		OrderByField orderByField = orderByFields.get(0);

		Assert.assertEquals("name", orderByField.getFieldName());
		Assert.assertEquals(
			OrderByField.OrderBy.desc, orderByField.getOrderBy());

		objects = _getWorkspaceGroupDataSourcesPage(null, null, null, null);

		Assert.assertNull(objects[2]);
		Assert.assertNull(objects[4]);
		Assert.assertNull(objects[5]);
		Assert.assertNull(objects[8]);
	}

	@Ignore
	@Override
	@Test
	public void testGetWorkspaceGroupDataSourcesPageWithPagination()
		throws Exception {

		super.testGetWorkspaceGroupDataSourcesPageWithPagination();
	}

	@Ignore
	@Override
	@Test
	public void testGetWorkspaceGroupDataSourcesPageWithSortDateTime()
		throws Exception {

		super.testGetWorkspaceGroupDataSourcesPageWithSortDateTime();
	}

	@Ignore
	@Override
	@Test
	public void testGetWorkspaceGroupDataSourcesPageWithSortDouble()
		throws Exception {

		super.testGetWorkspaceGroupDataSourcesPageWithSortDouble();
	}

	@Ignore
	@Override
	@Test
	public void testGetWorkspaceGroupDataSourcesPageWithSortInteger()
		throws Exception {

		super.testGetWorkspaceGroupDataSourcesPageWithSortInteger();
	}

	@Ignore
	@Override
	@Test
	public void testGetWorkspaceGroupDataSourcesPageWithSortString()
		throws Exception {

		super.testGetWorkspaceGroupDataSourcesPageWithSortString();
	}

	private void _assertCounts(
			Long expectedAccountsCount, Long expectedCampaignsCount,
			Long expectedEventsCount, Long expectedUsersCount,
			Provider provider)
		throws Exception {

		com.liferay.osb.faro.engine.client.model.DataSource
			engineClientDataSource =
				new com.liferay.osb.faro.engine.client.model.DataSource();

		engineClientDataSource.setId(
			String.valueOf(RandomTestUtil.randomLong()));
		engineClientDataSource.setProvider(provider);

		AtomicReference<DataSource> atomicReference = new AtomicReference<>();

		Map<String, Object[]> arguments = _invoke(
			HashMapBuilder.<String, Object>put(
				"getDataSource", engineClientDataSource
			).put(
				"getDataSourceMetricsAccountsCount", 1L
			).put(
				"getDataSourceMetricsCampaignsCount", 2L
			).put(
				"getDataSourceMetricsEventsCount", 3L
			).put(
				"getDataSourceMetricsUsersCount", 4L
			).put(
				"getDXPUsersCount", 5L
			).build(),
			dataSourceResource -> atomicReference.set(
				dataSourceResource.getWorkspaceGroupDataSource(
					testGroup.getGroupId(), engineClientDataSource.getId())));

		for (Object[] objects : arguments.values()) {
			Assert.assertEquals(engineClientDataSource.getId(), objects[1]);
		}

		DataSource dataSource = atomicReference.get();

		Assert.assertEquals(
			expectedAccountsCount, dataSource.getAccountsCount());
		Assert.assertEquals(
			expectedCampaignsCount, dataSource.getCampaignsCount());
		Assert.assertEquals(expectedEventsCount, dataSource.getEventsCount());
		Assert.assertEquals(engineClientDataSource.getId(), dataSource.getId());
		Assert.assertEquals(provider.getType(), dataSource.getProviderType());
		Assert.assertEquals(expectedUsersCount, dataSource.getUsersCount());
	}

	private Object[] _getWorkspaceGroupDataSourcesPage(
			String providerType, String search, String state, Sort[] sorts)
		throws Exception {

		Map<String, Object[]> arguments = _invoke(
			HashMapBuilder.<String, Object>put(
				"getDataSources", new Results<>()
			).build(),
			dataSourceResource ->
				dataSourceResource.getWorkspaceGroupDataSourcesPage(
					testGroup.getGroupId(), providerType, search, state,
					Pagination.of(2, 10), sorts));

		return arguments.get("getDataSources");
	}

	private Map<String, Object[]> _invoke(
			Map<String, Object> returnValues,
			UnsafeConsumer<DataSourceResource, Exception> unsafeConsumer)
		throws Exception {

		Map<String, Object[]> arguments = new HashMap<>();

		DataSourceResource dataSourceResource =
			ReflectionTestUtil.getFieldValue(this, "_dataSourceResource");

		ContactsEngineClient contactsEngineClient =
			ReflectionTestUtil.getAndSetFieldValue(
				dataSourceResource, "_contactsEngineClient",
				(ContactsEngineClient)ProxyUtil.newProxyInstance(
					ContactsEngineClient.class.getClassLoader(),
					new Class<?>[] {ContactsEngineClient.class},
					(proxy, method, methodArguments) -> {
						arguments.put(method.getName(), methodArguments);

						return returnValues.get(method.getName());
					}));
		FaroProjectLocalService faroProjectLocalService =
			ReflectionTestUtil.getAndSetFieldValue(
				dataSourceResource, "_faroProjectLocalService",
				(FaroProjectLocalService)ProxyUtil.newProxyInstance(
					FaroProjectLocalService.class.getClassLoader(),
					new Class<?>[] {FaroProjectLocalService.class},
					(proxy, method, methodArguments) -> null));

		try {
			unsafeConsumer.accept(dataSourceResource);
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				dataSourceResource, "_contactsEngineClient",
				contactsEngineClient);
			ReflectionTestUtil.setFieldValue(
				dataSourceResource, "_faroProjectLocalService",
				faroProjectLocalService);
		}

		return arguments;
	}

}