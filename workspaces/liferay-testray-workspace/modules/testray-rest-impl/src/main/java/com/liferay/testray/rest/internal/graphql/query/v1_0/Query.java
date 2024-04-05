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
import com.liferay.testray.rest.dto.v1_0.TestrayRunComparison;
import com.liferay.testray.rest.resource.v1_0.TestrayRunComparisonResource;

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

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {testrayRunComparison(testrayCasePriorities: ___, testrayRunId1: ___, testrayRunId2: ___, testrayTeamId: ___){results, testrayCasePriorities, testrayTeamId}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public TestrayRunComparison testrayRunComparison(
			@GraphQLName("testrayRunId1") Long testrayRunId1,
			@GraphQLName("testrayRunId2") Long testrayRunId2,
			@GraphQLName("testrayCasePriorities") String testrayCasePriorities,
			@GraphQLName("testrayTeamId") Long testrayTeamId)
		throws Exception {

		return _applyComponentServiceObjects(
			_testrayRunComparisonResourceComponentServiceObjects,
			this::_populateResourceContext,
			testrayRunComparisonResource ->
				testrayRunComparisonResource.getTestrayRunComparison(
					testrayRunId1, testrayRunId2, testrayCasePriorities,
					testrayTeamId));
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

	private static ComponentServiceObjects<TestrayRunComparisonResource>
		_testrayRunComparisonResourceComponentServiceObjects;

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