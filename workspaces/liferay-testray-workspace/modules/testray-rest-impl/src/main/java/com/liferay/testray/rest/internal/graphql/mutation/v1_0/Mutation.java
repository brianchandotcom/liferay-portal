package com.liferay.testray.rest.internal.graphql.mutation.v1_0;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineExportTaskResource;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineImportTaskResource;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.testray.rest.dto.v1_0.TestrayTestSuite;
import com.liferay.testray.rest.resource.v1_0.TestrayTestSuiteResource;

import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Nilton Vieira
 * @generated
 */
@Generated("")
public class Mutation {

	public static void setTestrayTestSuiteResourceComponentServiceObjects(
		ComponentServiceObjects<TestrayTestSuiteResource>
			testrayTestSuiteResourceComponentServiceObjects) {

		_testrayTestSuiteResourceComponentServiceObjects =
			testrayTestSuiteResourceComponentServiceObjects;
	}

	@GraphQLField
	@GraphQLName(
		description = "null", value = "postTestrayTestSuiteMultipartBody"
	)
	public TestrayTestSuite createTestrayTestSuite(
			@GraphQLName("multipartBody") MultipartBody multipartBody)
		throws Exception {

		return _applyComponentServiceObjects(
			_testrayTestSuiteResourceComponentServiceObjects,
			this::_populateResourceContext,
			testrayTestSuiteResource ->
				testrayTestSuiteResource.postTestrayTestSuite(multipartBody));
	}

	@GraphQLField
	public Response createTestrayTestSuiteBatch(
			@GraphQLName("multipartBody") MultipartBody multipartBody,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_testrayTestSuiteResourceComponentServiceObjects,
			this::_populateResourceContext,
			testrayTestSuiteResource ->
				testrayTestSuiteResource.postTestrayTestSuiteBatch(
					multipartBody, callbackURL, object));
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

	private <T, E1 extends Throwable, E2 extends Throwable> void
			_applyVoidComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeConsumer<T, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			unsafeFunction.accept(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private void _populateResourceContext(
			TestrayTestSuiteResource testrayTestSuiteResource)
		throws Exception {

		testrayTestSuiteResource.setContextAcceptLanguage(_acceptLanguage);
		testrayTestSuiteResource.setContextCompany(_company);
		testrayTestSuiteResource.setContextHttpServletRequest(
			_httpServletRequest);
		testrayTestSuiteResource.setContextHttpServletResponse(
			_httpServletResponse);
		testrayTestSuiteResource.setContextUriInfo(_uriInfo);
		testrayTestSuiteResource.setContextUser(_user);
		testrayTestSuiteResource.setGroupLocalService(_groupLocalService);
		testrayTestSuiteResource.setRoleLocalService(_roleLocalService);

		testrayTestSuiteResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		testrayTestSuiteResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private static ComponentServiceObjects<TestrayTestSuiteResource>
		_testrayTestSuiteResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;
	private VulcanBatchEngineExportTaskResource
		_vulcanBatchEngineExportTaskResource;
	private VulcanBatchEngineImportTaskResource
		_vulcanBatchEngineImportTaskResource;

}