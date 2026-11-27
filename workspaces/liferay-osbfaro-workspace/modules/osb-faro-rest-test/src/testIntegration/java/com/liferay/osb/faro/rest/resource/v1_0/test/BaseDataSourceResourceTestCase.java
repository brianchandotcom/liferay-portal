/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.osb.faro.rest.client.dto.v1_0.DataSource;
import com.liferay.osb.faro.rest.client.http.HttpInvoker;
import com.liferay.osb.faro.rest.client.pagination.Page;
import com.liferay.osb.faro.rest.client.pagination.Pagination;
import com.liferay.osb.faro.rest.client.resource.v1_0.DataSourceResource;
import com.liferay.osb.faro.rest.client.serdes.v1_0.DataSourceSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import jakarta.annotation.Generated;

import jakarta.ws.rs.core.MultivaluedHashMap;

import java.lang.reflect.Method;

import java.text.Format;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Leslie Wong
 * @generated
 */
@Generated("")
public abstract class BaseDataSourceResourceTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_format = FastDateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");
	}

	@Before
	public void setUp() throws Exception {
		irrelevantGroup = GroupTestUtil.addGroup();
		testGroup = GroupTestUtil.addGroup();

		testCompany = CompanyLocalServiceUtil.getCompany(
			testGroup.getCompanyId());

		_dataSourceResource.setContextCompany(testCompany);

		_testCompanyAdminUser = UserTestUtil.getAdminUser(
			testCompany.getCompanyId());

		dataSourceResource = DataSourceResource.builder(
		).authentication(
			_testCompanyAdminUser.getEmailAddress(),
			PropsValues.DEFAULT_ADMIN_PASSWORD
		).endpoint(
			testCompany.getVirtualHostname(),
			PortalUtil.getPortalServerPort(false), "http"
		).locale(
			LocaleUtil.getDefault()
		).build();
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testClientSerDesToDTO() throws Exception {
		ObjectMapper objectMapper = getClientSerDesObjectMapper();

		DataSource dataSource1 = randomDataSource();

		String json = objectMapper.writeValueAsString(dataSource1);

		DataSource dataSource2 = DataSourceSerDes.toDTO(json);

		Assert.assertTrue(equals(dataSource1, dataSource2));
	}

	@Test
	public void testClientSerDesToJSON() throws Exception {
		ObjectMapper objectMapper = getClientSerDesObjectMapper();

		DataSource dataSource = randomDataSource();

		String json1 = objectMapper.writeValueAsString(dataSource);
		String json2 = DataSourceSerDes.toJSON(dataSource);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	protected ObjectMapper getClientSerDesObjectMapper() {
		return new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				enable(SerializationFeature.INDENT_OUTPUT);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		DataSource dataSource = randomDataSource();

		dataSource.setId(regex);
		dataSource.setName(regex);
		dataSource.setProviderType(regex);
		dataSource.setState(regex);
		dataSource.setStatus(regex);

		String json = DataSourceSerDes.toJSON(dataSource);

		Assert.assertFalse(json.contains(regex));

		dataSource = DataSourceSerDes.toDTO(json);

		Assert.assertEquals(regex, dataSource.getId());
		Assert.assertEquals(regex, dataSource.getName());
		Assert.assertEquals(regex, dataSource.getProviderType());
		Assert.assertEquals(regex, dataSource.getState());
		Assert.assertEquals(regex, dataSource.getStatus());
	}

	@Test
	public void testGetWorkspaceGroupDataSource() throws Exception {
		DataSource postDataSource =
			testGetWorkspaceGroupDataSource_addDataSource();

		DataSource getDataSource =
			dataSourceResource.getWorkspaceGroupDataSource(
				testGetWorkspaceGroupDataSource_getGroupId(),
				postDataSource.getId());

		assertEquals(postDataSource, getDataSource);
		assertValid(getDataSource);
	}

	protected DataSource testGetWorkspaceGroupDataSource_addDataSource()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetWorkspaceGroupDataSource_getGroupId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetWorkspaceGroupDataSourcesPage() throws Exception {
		Long groupId = testGetWorkspaceGroupDataSourcesPage_getGroupId();
		Long irrelevantGroupId =
			testGetWorkspaceGroupDataSourcesPage_getIrrelevantGroupId();

		Page<DataSource> page =
			dataSourceResource.getWorkspaceGroupDataSourcesPage(
				groupId, RandomTestUtil.randomString(), null,
				RandomTestUtil.randomString(), Pagination.of(1, 10), null);

		long totalCount = page.getTotalCount();

		if (irrelevantGroupId != null) {
			DataSource irrelevantDataSource =
				testGetWorkspaceGroupDataSourcesPage_addDataSource(
					irrelevantGroupId, randomIrrelevantDataSource());

			page = dataSourceResource.getWorkspaceGroupDataSourcesPage(
				irrelevantGroupId, null, null, null,
				Pagination.of(1, (int)totalCount + 1), null);

			Assert.assertEquals(totalCount + 1, page.getTotalCount());

			assertContains(
				irrelevantDataSource, (List<DataSource>)page.getItems());
			assertValid(
				page,
				testGetWorkspaceGroupDataSourcesPage_getExpectedActions(
					irrelevantGroupId));
		}

		DataSource dataSource1 =
			testGetWorkspaceGroupDataSourcesPage_addDataSource(
				groupId, randomDataSource());

		DataSource dataSource2 =
			testGetWorkspaceGroupDataSourcesPage_addDataSource(
				groupId, randomDataSource());

		page = dataSourceResource.getWorkspaceGroupDataSourcesPage(
			groupId, null, null, null, Pagination.of(1, (int)totalCount + 2),
			null);

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(dataSource1, (List<DataSource>)page.getItems());
		assertContains(dataSource2, (List<DataSource>)page.getItems());
		assertValid(
			page,
			testGetWorkspaceGroupDataSourcesPage_getExpectedActions(groupId));
	}

	protected Map<String, Map<String, String>>
			testGetWorkspaceGroupDataSourcesPage_getExpectedActions(
				Long groupId)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetWorkspaceGroupDataSourcesPageWithPagination()
		throws Exception {

		Long groupId = testGetWorkspaceGroupDataSourcesPage_getGroupId();

		Page<DataSource> dataSourcesPage =
			dataSourceResource.getWorkspaceGroupDataSourcesPage(
				groupId, null, null, null, null, null);

		int totalCount = GetterUtil.getInteger(dataSourcesPage.getTotalCount());

		DataSource dataSource1 =
			testGetWorkspaceGroupDataSourcesPage_addDataSource(
				groupId, randomDataSource());

		DataSource dataSource2 =
			testGetWorkspaceGroupDataSourcesPage_addDataSource(
				groupId, randomDataSource());

		DataSource dataSource3 =
			testGetWorkspaceGroupDataSourcesPage_addDataSource(
				groupId, randomDataSource());

		// See com.liferay.portal.vulcan.internal.configuration.HeadlessAPICompanyConfiguration#pageSizeLimit

		int pageSizeLimit = 500;

		if (totalCount >= (pageSizeLimit - 2)) {
			Page<DataSource> page1 =
				dataSourceResource.getWorkspaceGroupDataSourcesPage(
					groupId, null, null, null,
					Pagination.of(
						(int)Math.ceil((totalCount + 1.0) / pageSizeLimit),
						pageSizeLimit),
					null);

			Assert.assertEquals(totalCount + 3, page1.getTotalCount());

			assertContains(dataSource1, (List<DataSource>)page1.getItems());

			Page<DataSource> page2 =
				dataSourceResource.getWorkspaceGroupDataSourcesPage(
					groupId, null, null, null,
					Pagination.of(
						(int)Math.ceil((totalCount + 2.0) / pageSizeLimit),
						pageSizeLimit),
					null);

			assertContains(dataSource2, (List<DataSource>)page2.getItems());

			Page<DataSource> page3 =
				dataSourceResource.getWorkspaceGroupDataSourcesPage(
					groupId, null, null, null,
					Pagination.of(
						(int)Math.ceil((totalCount + 3.0) / pageSizeLimit),
						pageSizeLimit),
					null);

			assertContains(dataSource3, (List<DataSource>)page3.getItems());
		}
		else {
			Page<DataSource> page1 =
				dataSourceResource.getWorkspaceGroupDataSourcesPage(
					groupId, null, null, null, Pagination.of(1, totalCount + 2),
					null);

			List<DataSource> dataSources1 = (List<DataSource>)page1.getItems();

			Assert.assertEquals(
				dataSources1.toString(), totalCount + 2, dataSources1.size());

			Page<DataSource> page2 =
				dataSourceResource.getWorkspaceGroupDataSourcesPage(
					groupId, null, null, null, Pagination.of(2, totalCount + 2),
					null);

			Assert.assertEquals(totalCount + 3, page2.getTotalCount());

			List<DataSource> dataSources2 = (List<DataSource>)page2.getItems();

			Assert.assertEquals(
				dataSources2.toString(), 1, dataSources2.size());

			Page<DataSource> page3 =
				dataSourceResource.getWorkspaceGroupDataSourcesPage(
					groupId, null, null, null,
					Pagination.of(1, (int)totalCount + 3), null);

			assertContains(dataSource1, (List<DataSource>)page3.getItems());
			assertContains(dataSource2, (List<DataSource>)page3.getItems());
			assertContains(dataSource3, (List<DataSource>)page3.getItems());
		}
	}

	@Test
	public void testGetWorkspaceGroupDataSourcesPageWithSortDateTime()
		throws Exception {

		testGetWorkspaceGroupDataSourcesPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, dataSource1, dataSource2) -> {
				BeanTestUtil.setProperty(
					dataSource1, entityField.getName(),
					new Date(System.currentTimeMillis() - (2 * Time.MINUTE)));
			});
	}

	@Test
	public void testGetWorkspaceGroupDataSourcesPageWithSortDouble()
		throws Exception {

		testGetWorkspaceGroupDataSourcesPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, dataSource1, dataSource2) -> {
				BeanTestUtil.setProperty(
					dataSource1, entityField.getName(), 0.1);
				BeanTestUtil.setProperty(
					dataSource2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetWorkspaceGroupDataSourcesPageWithSortInteger()
		throws Exception {

		testGetWorkspaceGroupDataSourcesPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, dataSource1, dataSource2) -> {
				BeanTestUtil.setProperty(dataSource1, entityField.getName(), 0);
				BeanTestUtil.setProperty(dataSource2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetWorkspaceGroupDataSourcesPageWithSortString()
		throws Exception {

		testGetWorkspaceGroupDataSourcesPageWithSort(
			EntityField.Type.STRING,
			(entityField, dataSource1, dataSource2) -> {
				Class<?> clazz = dataSource1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanTestUtil.setProperty(
						dataSource1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						dataSource2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						dataSource1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						dataSource2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						dataSource1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
						dataSource2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetWorkspaceGroupDataSourcesPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, DataSource, DataSource, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long groupId = testGetWorkspaceGroupDataSourcesPage_getGroupId();

		DataSource dataSource1 = randomDataSource();
		DataSource dataSource2 = randomDataSource();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, dataSource1, dataSource2);
		}

		dataSource1 = testGetWorkspaceGroupDataSourcesPage_addDataSource(
			groupId, dataSource1);

		dataSource2 = testGetWorkspaceGroupDataSourcesPage_addDataSource(
			groupId, dataSource2);

		Page<DataSource> page =
			dataSourceResource.getWorkspaceGroupDataSourcesPage(
				groupId, null, null, null, null, null);

		for (EntityField entityField : entityFields) {
			Page<DataSource> ascPage =
				dataSourceResource.getWorkspaceGroupDataSourcesPage(
					groupId, null, null, null,
					Pagination.of(1, (int)page.getTotalCount() + 1),
					entityField.getName() + ":asc");

			assertContains(dataSource1, (List<DataSource>)ascPage.getItems());
			assertContains(dataSource2, (List<DataSource>)ascPage.getItems());

			Page<DataSource> descPage =
				dataSourceResource.getWorkspaceGroupDataSourcesPage(
					groupId, null, null, null,
					Pagination.of(1, (int)page.getTotalCount() + 1),
					entityField.getName() + ":desc");

			assertContains(dataSource2, (List<DataSource>)descPage.getItems());
			assertContains(dataSource1, (List<DataSource>)descPage.getItems());
		}
	}

	protected DataSource testGetWorkspaceGroupDataSourcesPage_addDataSource(
			Long groupId, DataSource dataSource)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetWorkspaceGroupDataSourcesPage_getGroupId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetWorkspaceGroupDataSourcesPage_getIrrelevantGroupId()
		throws Exception {

		return null;
	}

	protected void assertContains(
		DataSource dataSource, List<DataSource> dataSources) {

		boolean contains = false;

		for (DataSource item : dataSources) {
			if (equals(dataSource, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			dataSources + " does not contain " + dataSource, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		DataSource dataSource1, DataSource dataSource2) {

		Assert.assertTrue(
			dataSource1 + " does not equal " + dataSource2,
			equals(dataSource1, dataSource2));
	}

	protected void assertEquals(
		List<DataSource> dataSources1, List<DataSource> dataSources2) {

		Assert.assertEquals(dataSources1.size(), dataSources2.size());

		for (int i = 0; i < dataSources1.size(); i++) {
			DataSource dataSource1 = dataSources1.get(i);
			DataSource dataSource2 = dataSources2.get(i);

			assertEquals(dataSource1, dataSource2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<DataSource> dataSources1, List<DataSource> dataSources2) {

		Assert.assertEquals(dataSources1.size(), dataSources2.size());

		for (DataSource dataSource1 : dataSources1) {
			boolean contains = false;

			for (DataSource dataSource2 : dataSources2) {
				if (equals(dataSource1, dataSource2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				dataSources2 + " does not contain " + dataSource1, contains);
		}
	}

	protected void assertValid(DataSource dataSource) throws Exception {
		boolean valid = true;

		if (dataSource.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("accountsCount", additionalAssertFieldName)) {
				if (dataSource.getAccountsCount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("campaignsCount", additionalAssertFieldName)) {
				if (dataSource.getCampaignsCount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("channelIds", additionalAssertFieldName)) {
				if (dataSource.getChannelIds() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("contactsSynced", additionalAssertFieldName)) {
				if (dataSource.getContactsSynced() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("createDate", additionalAssertFieldName)) {
				if (dataSource.getCreateDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("eventsCount", additionalAssertFieldName)) {
				if (dataSource.getEventsCount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("modifiedDate", additionalAssertFieldName)) {
				if (dataSource.getModifiedDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (dataSource.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("providerType", additionalAssertFieldName)) {
				if (dataSource.getProviderType() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("sitesCount", additionalAssertFieldName)) {
				if (dataSource.getSitesCount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("sitesSynced", additionalAssertFieldName)) {
				if (dataSource.getSitesSynced() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("state", additionalAssertFieldName)) {
				if (dataSource.getState() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (dataSource.getStatus() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("usersCount", additionalAssertFieldName)) {
				if (dataSource.getUsersCount() == null) {
					valid = false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		Assert.assertTrue(valid);
	}

	protected void assertValid(Page<DataSource> page) {
		assertValid(page, Collections.emptyMap());
	}

	protected void assertValid(
		Page<DataSource> page,
		Map<String, Map<String, String>> expectedActions) {

		boolean valid = false;

		java.util.Collection<DataSource> dataSources = page.getItems();

		int size = dataSources.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);

		assertValid(page.getActions(), expectedActions);
	}

	protected void assertValid(
		Map<String, Map<String, String>> actions1,
		Map<String, Map<String, String>> actions2) {

		for (String key : actions2.keySet()) {
			Map action = actions1.get(key);

			Assert.assertNotNull(key + " does not contain an action", action);

			Map<String, String> expectedAction = actions2.get(key);

			Assert.assertEquals(
				expectedAction.get("method"), action.get("method"));
			Assert.assertEquals(expectedAction.get("href"), action.get("href"));
		}
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected List<GraphQLField> getGraphQLFields() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		graphQLFields.add(new GraphQLField("id"));

		for (java.lang.reflect.Field field :
				getDeclaredFields(
					com.liferay.osb.faro.rest.dto.v1_0.DataSource.class)) {

			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			graphQLFields.addAll(getGraphQLFields(field));
		}

		return graphQLFields;
	}

	protected List<GraphQLField> getGraphQLFields(
			java.lang.reflect.Field... fields)
		throws Exception {

		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (java.lang.reflect.Field field : fields) {
			com.liferay.portal.vulcan.graphql.annotation.GraphQLField
				vulcanGraphQLField = field.getAnnotation(
					com.liferay.portal.vulcan.graphql.annotation.GraphQLField.
						class);

			if (vulcanGraphQLField != null) {
				Class<?> clazz = field.getType();

				if (clazz.isArray()) {
					clazz = clazz.getComponentType();
				}

				List<GraphQLField> childrenGraphQLFields = getGraphQLFields(
					getDeclaredFields(clazz));

				graphQLFields.add(
					new GraphQLField(field.getName(), childrenGraphQLFields));
			}
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
		return new String[0];
	}

	protected boolean equals(DataSource dataSource1, DataSource dataSource2) {
		if (dataSource1 == dataSource2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("accountsCount", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataSource1.getAccountsCount(),
						dataSource2.getAccountsCount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("campaignsCount", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataSource1.getCampaignsCount(),
						dataSource2.getCampaignsCount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("channelIds", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataSource1.getChannelIds(),
						dataSource2.getChannelIds())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("contactsSynced", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataSource1.getContactsSynced(),
						dataSource2.getContactsSynced())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("createDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataSource1.getCreateDate(),
						dataSource2.getCreateDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("eventsCount", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataSource1.getEventsCount(),
						dataSource2.getEventsCount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataSource1.getId(), dataSource2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("modifiedDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataSource1.getModifiedDate(),
						dataSource2.getModifiedDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataSource1.getName(), dataSource2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("providerType", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataSource1.getProviderType(),
						dataSource2.getProviderType())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("sitesCount", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataSource1.getSitesCount(),
						dataSource2.getSitesCount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("sitesSynced", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataSource1.getSitesSynced(),
						dataSource2.getSitesSynced())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("state", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataSource1.getState(), dataSource2.getState())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataSource1.getStatus(), dataSource2.getStatus())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("usersCount", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataSource1.getUsersCount(),
						dataSource2.getUsersCount())) {

					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		return true;
	}

	protected boolean equals(
		Map<String, Object> map1, Map<String, Object> map2) {

		if (Objects.equals(map1.keySet(), map2.keySet())) {
			for (Map.Entry<String, Object> entry : map1.entrySet()) {
				if (entry.getValue() instanceof Map) {
					if (!equals(
							(Map)entry.getValue(),
							(Map)map2.get(entry.getKey()))) {

						return false;
					}
				}
				else if (!Objects.deepEquals(
							entry.getValue(), map2.get(entry.getKey()))) {

					return false;
				}
			}

			return true;
		}

		return false;
	}

	protected java.lang.reflect.Field[] getDeclaredFields(Class clazz)
		throws Exception {

		if (clazz.getClassLoader() == null) {
			return new java.lang.reflect.Field[0];
		}

		return TransformUtil.transform(
			ReflectionUtil.getDeclaredFields(clazz),
			field -> {
				if (field.isSynthetic()) {
					return null;
				}

				return field;
			},
			java.lang.reflect.Field.class);
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_dataSourceResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_dataSourceResource;

		EntityModel entityModel = entityModelResource.getEntityModel(
			new MultivaluedHashMap());

		if (entityModel == null) {
			return Collections.emptyList();
		}

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		return entityFieldsMap.values();
	}

	protected List<EntityField> getEntityFields(EntityField.Type type)
		throws Exception {

		return TransformUtil.transform(
			getEntityFields(),
			entityField -> {
				if (!Objects.equals(entityField.getType(), type) ||
					ArrayUtil.contains(
						getIgnoredEntityFieldNames(), entityField.getName())) {

					return null;
				}

				return entityField;
			});
	}

	protected String getFilterString(
		EntityField entityField, String operator, DataSource dataSource) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("accountsCount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("campaignsCount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("channelIds")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("contactsSynced")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("createDate")) {
			if (operator.equals("between")) {
				Date date = dataSource.getCreateDate();

				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(_format.format(date.getTime() - (2 * Time.SECOND)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(_format.format(date.getTime() + (2 * Time.SECOND)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_format.format(dataSource.getCreateDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("eventsCount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
			Object object = dataSource.getId();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("modifiedDate")) {
			if (operator.equals("between")) {
				Date date = dataSource.getModifiedDate();

				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(_format.format(date.getTime() - (2 * Time.SECOND)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(_format.format(date.getTime() + (2 * Time.SECOND)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_format.format(dataSource.getModifiedDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("name")) {
			Object object = dataSource.getName();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("providerType")) {
			Object object = dataSource.getProviderType();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("sitesCount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("sitesSynced")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("state")) {
			Object object = dataSource.getState();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("status")) {
			Object object = dataSource.getStatus();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("usersCount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected String invoke(String query) throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			JSONUtil.put(
				"query", query
			).toString(),
			"application/json");
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);
		httpInvoker.path(
			"http://localhost:" + PortalUtil.getPortalServerPort(false) +
				"/o/graphql");
		httpInvoker.userNameAndPassword(
			"test@liferay.com:" + PropsValues.DEFAULT_ADMIN_PASSWORD);

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return httpResponse.getContent();
	}

	protected JSONObject invokeGraphQLMutation(GraphQLField graphQLField)
		throws Exception {

		GraphQLField mutationGraphQLField = new GraphQLField(
			"mutation", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(mutationGraphQLField.toString()));
	}

	protected JSONObject invokeGraphQLQuery(GraphQLField graphQLField)
		throws Exception {

		GraphQLField queryGraphQLField = new GraphQLField(
			"query", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(queryGraphQLField.toString()));
	}

	protected DataSource randomDataSource() throws Exception {
		return new DataSource() {
			{
				accountsCount = RandomTestUtil.randomLong();
				campaignsCount = RandomTestUtil.randomLong();
				contactsSynced = RandomTestUtil.randomBoolean();
				createDate = RandomTestUtil.nextDate();
				eventsCount = RandomTestUtil.randomLong();
				id = StringUtil.toLowerCase(RandomTestUtil.randomString());
				modifiedDate = RandomTestUtil.nextDate();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				providerType = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				sitesCount = RandomTestUtil.randomLong();
				sitesSynced = RandomTestUtil.randomBoolean();
				state = StringUtil.toLowerCase(RandomTestUtil.randomString());
				status = StringUtil.toLowerCase(RandomTestUtil.randomString());
				usersCount = RandomTestUtil.randomLong();
			}
		};
	}

	protected DataSource randomIrrelevantDataSource() throws Exception {
		DataSource randomIrrelevantDataSource = randomDataSource();

		return randomIrrelevantDataSource;
	}

	protected DataSource randomPatchDataSource() throws Exception {
		return randomDataSource();
	}

	protected DataSourceResource dataSourceResource;
	protected com.liferay.portal.kernel.model.Group irrelevantGroup;
	protected com.liferay.portal.kernel.model.Company testCompany;
	protected com.liferay.portal.kernel.model.Group testGroup;

	protected static class BeanTestUtil {

		public static void copyProperties(Object source, Object target)
			throws Exception {

			Class<?> sourceClass = source.getClass();

			Class<?> targetClass = target.getClass();

			for (java.lang.reflect.Field field :
					_getAllDeclaredFields(sourceClass)) {

				if (field.isSynthetic()) {
					continue;
				}

				Method getMethod = _getMethod(
					sourceClass, field.getName(), "get");

				try {
					Method setMethod = _getMethod(
						targetClass, field.getName(), "set",
						getMethod.getReturnType());

					setMethod.invoke(target, getMethod.invoke(source));
				}
				catch (Exception e) {
					continue;
				}
			}
		}

		public static boolean hasProperty(Object bean, String name) {
			Method setMethod = _getMethod(
				bean.getClass(), "set" + StringUtil.upperCaseFirstLetter(name));

			if (setMethod != null) {
				return true;
			}

			return false;
		}

		public static void setProperty(Object bean, String name, Object value)
			throws Exception {

			Class<?> clazz = bean.getClass();

			Method setMethod = _getMethod(
				clazz, "set" + StringUtil.upperCaseFirstLetter(name));

			if (setMethod == null) {
				throw new NoSuchMethodException();
			}

			Class<?>[] parameterTypes = setMethod.getParameterTypes();

			setMethod.invoke(bean, _translateValue(parameterTypes[0], value));
		}

		private static List<java.lang.reflect.Field> _getAllDeclaredFields(
			Class<?> clazz) {

			List<java.lang.reflect.Field> fields = new ArrayList<>();

			while ((clazz != null) && (clazz != Object.class)) {
				for (java.lang.reflect.Field field :
						clazz.getDeclaredFields()) {

					fields.add(field);
				}

				clazz = clazz.getSuperclass();
			}

			return fields;
		}

		private static Method _getMethod(Class<?> clazz, String name) {
			for (Method method : clazz.getMethods()) {
				if (name.equals(method.getName()) &&
					(method.getParameterCount() == 1) &&
					_parameterTypes.contains(method.getParameterTypes()[0])) {

					return method;
				}
			}

			return null;
		}

		private static Method _getMethod(
				Class<?> clazz, String fieldName, String prefix,
				Class<?>... parameterTypes)
			throws Exception {

			return clazz.getMethod(
				prefix + StringUtil.upperCaseFirstLetter(fieldName),
				parameterTypes);
		}

		private static Object _translateValue(
			Class<?> parameterType, Object value) {

			if ((value instanceof Integer) &&
				parameterType.equals(Long.class)) {

				Integer intValue = (Integer)value;

				return intValue.longValue();
			}

			return value;
		}

		private static final Set<Class<?>> _parameterTypes = new HashSet<>(
			Arrays.asList(
				Boolean.class, Date.class, Double.class, Integer.class,
				Long.class, Map.class, String.class));

	}

	protected class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(String key, List<GraphQLField> graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = Arrays.asList(graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			List<GraphQLField> graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = graphQLFields;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(_key);

			if (!_parameterMap.isEmpty()) {
				sb.append("(");

				for (Map.Entry<String, Object> entry :
						_parameterMap.entrySet()) {

					sb.append(entry.getKey());
					sb.append(": ");
					sb.append(entry.getValue());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append(")");
			}

			if (!_graphQLFields.isEmpty()) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append("}");
			}

			return sb.toString();
		}

		private final List<GraphQLField> _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final com.liferay.portal.kernel.log.Log _log =
		LogFactoryUtil.getLog(BaseDataSourceResourceTestCase.class);

	private static Format _format;

	private com.liferay.portal.kernel.model.User _testCompanyAdminUser;

	@Inject
	private com.liferay.osb.faro.rest.resource.v1_0.DataSourceResource
		_dataSourceResource;

}
// LIFERAY-REST-BUILDER-HASH:1936420191