package com.liferay.testray.rest.internal.graphql.query.v1_0;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.testray.rest.dto.v1_0.TestrayRunComparison;
import com.liferay.testray.rest.dto.v1_0.TestrayStatusMetric;
import com.liferay.testray.rest.resource.v1_0.TestrayRunComparisonResource;
import com.liferay.testray.rest.resource.v1_0.TestrayStatusMetricResource;

import java.util.Map;
import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Nilton Vieira
 * @generated
 */
@Generated("")
public class Query {

	public static void setTestrayRunComparisonResourceComponentServiceObjects(
		ComponentServiceObjects<TestrayRunComparisonResource>
			testrayRunComparisonResourceComponentServiceObjects) {

		_testrayRunComparisonResourceComponentServiceObjects =
			testrayRunComparisonResourceComponentServiceObjects;
	}

	public static void setTestrayStatusMetricResourceComponentServiceObjects(
		ComponentServiceObjects<TestrayStatusMetricResource>
			testrayStatusMetricResourceComponentServiceObjects) {

		_testrayStatusMetricResourceComponentServiceObjects =
			testrayStatusMetricResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {testrayRunComparison(filter: ___, testrayRunId1: ___, testrayRunId2: ___){results, testrayCaseResultComparisons}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public TestrayRunComparison testrayRunComparison(
			@GraphQLName("testrayRunId1") Long testrayRunId1,
			@GraphQLName("testrayRunId2") Long testrayRunId2,
			@GraphQLName("filter") String filterString)
		throws Exception {

		return _applyComponentServiceObjects(
			_testrayRunComparisonResourceComponentServiceObjects,
			this::_populateResourceContext,
			testrayRunComparisonResource ->
				testrayRunComparisonResource.getTestrayRunComparison(
					testrayRunId1, testrayRunId2,
					_filterBiFunction.apply(
						testrayRunComparisonResource, filterString)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {testrayRunComparisonDetail(filter: ___, testrayCaseResultError1: ___, testrayCaseResultError2: ___, testrayCaseResultIssue1: ___, testrayCaseResultIssue2: ___, testrayCaseResultStatus1: ___, testrayCaseResultStatus2: ___, testrayRunId1: ___, testrayRunId2: ___){results, testrayCaseResultComparisons}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public TestrayRunComparison testrayRunComparisonDetail(
			@GraphQLName("testrayRunId1") Long testrayRunId1,
			@GraphQLName("testrayRunId2") Long testrayRunId2,
			@GraphQLName("testrayCaseResultError1") String
				testrayCaseResultError1,
			@GraphQLName("testrayCaseResultError2") String
				testrayCaseResultError2,
			@GraphQLName("testrayCaseResultIssue1") String
				testrayCaseResultIssue1,
			@GraphQLName("testrayCaseResultIssue2") String
				testrayCaseResultIssue2,
			@GraphQLName("testrayCaseResultStatus1") String
				testrayCaseResultStatus1,
			@GraphQLName("testrayCaseResultStatus2") String
				testrayCaseResultStatus2,
			@GraphQLName("filter") String filterString)
		throws Exception {

		return _applyComponentServiceObjects(
			_testrayRunComparisonResourceComponentServiceObjects,
			this::_populateResourceContext,
			testrayRunComparisonResource ->
				testrayRunComparisonResource.getTestrayRunComparisonDetail(
					testrayRunId1, testrayRunId2, testrayCaseResultError1,
					testrayCaseResultError2, testrayCaseResultIssue1,
					testrayCaseResultIssue2, testrayCaseResultStatus1,
					testrayCaseResultStatus2,
					_filterBiFunction.apply(
						testrayRunComparisonResource, filterString)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {testrayStatusMetricByTestrayBuildIdTestrayBuildTestrayCaseTypesMetrics(page: ___, pageSize: ___, testrayBuildId: ___, testrayCasePriorities: ___, testrayTeamId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public TestrayStatusMetricPage
			testrayStatusMetricByTestrayBuildIdTestrayBuildTestrayCaseTypesMetrics(
				@GraphQLName("testrayBuildId") Long testrayBuildId,
				@GraphQLName("testrayCasePriorities") String
					testrayCasePriorities,
				@GraphQLName("testrayTeamId") Long testrayTeamId,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_testrayStatusMetricResourceComponentServiceObjects,
			this::_populateResourceContext,
			testrayStatusMetricResource -> new TestrayStatusMetricPage(
				testrayStatusMetricResource.
					getTestrayStatusMetricByTestrayBuildIdTestrayBuildTestrayCaseTypesMetricsPage(
						testrayBuildId, testrayCasePriorities, testrayTeamId,
						Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {testrayStatusMetricByTestrayBuildIdTestrayBuildTestrayComponentsMetrics(page: ___, pageSize: ___, testrayBuildId: ___, testrayCasePriorities: ___, testrayCaseTypes: ___, testrayTeamId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public TestrayStatusMetricPage
			testrayStatusMetricByTestrayBuildIdTestrayBuildTestrayComponentsMetrics(
				@GraphQLName("testrayBuildId") Long testrayBuildId,
				@GraphQLName("testrayCasePriorities") String
					testrayCasePriorities,
				@GraphQLName("testrayCaseTypes") String testrayCaseTypes,
				@GraphQLName("testrayTeamId") Long testrayTeamId,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_testrayStatusMetricResourceComponentServiceObjects,
			this::_populateResourceContext,
			testrayStatusMetricResource -> new TestrayStatusMetricPage(
				testrayStatusMetricResource.
					getTestrayStatusMetricByTestrayBuildIdTestrayBuildTestrayComponentsMetricsPage(
						testrayBuildId, testrayCasePriorities, testrayCaseTypes,
						testrayTeamId, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {testrayStatusMetricByTestrayBuildIdTestrayBuildTestrayRunsMetrics(page: ___, pageSize: ___, testrayBuildId: ___, testrayCasePriorities: ___, testrayCaseTypes: ___, testrayTeamId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public TestrayStatusMetricPage
			testrayStatusMetricByTestrayBuildIdTestrayBuildTestrayRunsMetrics(
				@GraphQLName("testrayBuildId") Long testrayBuildId,
				@GraphQLName("testrayCasePriorities") String
					testrayCasePriorities,
				@GraphQLName("testrayCaseTypes") String testrayCaseTypes,
				@GraphQLName("testrayTeamId") Long testrayTeamId,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_testrayStatusMetricResourceComponentServiceObjects,
			this::_populateResourceContext,
			testrayStatusMetricResource -> new TestrayStatusMetricPage(
				testrayStatusMetricResource.
					getTestrayStatusMetricByTestrayBuildIdTestrayBuildTestrayRunsMetricsPage(
						testrayBuildId, testrayCasePriorities, testrayCaseTypes,
						testrayTeamId, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {testrayStatusMetricByTestrayBuildIdTestrayBuildTestrayTeamsMetrics(page: ___, pageSize: ___, testrayBuildId: ___, testrayCasePriorities: ___, testrayCaseTypes: ___, testrayRunId: ___, testrayTeamId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public TestrayStatusMetricPage
			testrayStatusMetricByTestrayBuildIdTestrayBuildTestrayTeamsMetrics(
				@GraphQLName("testrayBuildId") Long testrayBuildId,
				@GraphQLName("testrayCasePriorities") String
					testrayCasePriorities,
				@GraphQLName("testrayCaseTypes") String testrayCaseTypes,
				@GraphQLName("testrayRunId") Long testrayRunId,
				@GraphQLName("testrayTeamId") Long testrayTeamId,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_testrayStatusMetricResourceComponentServiceObjects,
			this::_populateResourceContext,
			testrayStatusMetricResource -> new TestrayStatusMetricPage(
				testrayStatusMetricResource.
					getTestrayStatusMetricByTestrayBuildIdTestrayBuildTestrayTeamsMetricsPage(
						testrayBuildId, testrayCasePriorities, testrayCaseTypes,
						testrayRunId, testrayTeamId,
						Pagination.of(page, pageSize))));
	}

	@GraphQLName("TestrayRunComparisonPage")
	public class TestrayRunComparisonPage {

		public TestrayRunComparisonPage(Page testrayRunComparisonPage) {
			actions = testrayRunComparisonPage.getActions();

			items = testrayRunComparisonPage.getItems();
			lastPage = testrayRunComparisonPage.getLastPage();
			page = testrayRunComparisonPage.getPage();
			pageSize = testrayRunComparisonPage.getPageSize();
			totalCount = testrayRunComparisonPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<TestrayRunComparison> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("TestrayStatusMetricPage")
	public class TestrayStatusMetricPage {

		public TestrayStatusMetricPage(Page testrayStatusMetricPage) {
			actions = testrayStatusMetricPage.getActions();

			items = testrayStatusMetricPage.getItems();
			lastPage = testrayStatusMetricPage.getLastPage();
			page = testrayStatusMetricPage.getPage();
			pageSize = testrayStatusMetricPage.getPageSize();
			totalCount = testrayStatusMetricPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<TestrayStatusMetric> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	private <T, R, E1 extends Throwable, E2 extends Throwable> R
			_applyComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeFunction<T, R, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			return unsafeFunction.apply(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private void _populateResourceContext(
			TestrayRunComparisonResource testrayRunComparisonResource)
		throws Exception {

		testrayRunComparisonResource.setContextAcceptLanguage(_acceptLanguage);
		testrayRunComparisonResource.setContextCompany(_company);
		testrayRunComparisonResource.setContextHttpServletRequest(
			_httpServletRequest);
		testrayRunComparisonResource.setContextHttpServletResponse(
			_httpServletResponse);
		testrayRunComparisonResource.setContextUriInfo(_uriInfo);
		testrayRunComparisonResource.setContextUser(_user);
		testrayRunComparisonResource.setGroupLocalService(_groupLocalService);
		testrayRunComparisonResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			TestrayStatusMetricResource testrayStatusMetricResource)
		throws Exception {

		testrayStatusMetricResource.setContextAcceptLanguage(_acceptLanguage);
		testrayStatusMetricResource.setContextCompany(_company);
		testrayStatusMetricResource.setContextHttpServletRequest(
			_httpServletRequest);
		testrayStatusMetricResource.setContextHttpServletResponse(
			_httpServletResponse);
		testrayStatusMetricResource.setContextUriInfo(_uriInfo);
		testrayStatusMetricResource.setContextUser(_user);
		testrayStatusMetricResource.setGroupLocalService(_groupLocalService);
		testrayStatusMetricResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<TestrayRunComparisonResource>
		_testrayRunComparisonResourceComponentServiceObjects;
	private static ComponentServiceObjects<TestrayStatusMetricResource>
		_testrayStatusMetricResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
	private BiFunction<Object, String, Filter> _filterBiFunction;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;

}