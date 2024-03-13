package com.liferay.testray.rest.internal.resource.v1_0.factory;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.odata.filter.ExpressionConvert;
import com.liferay.portal.odata.filter.FilterParserProvider;
import com.liferay.portal.odata.sort.SortParserProvider;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.testray.rest.internal.security.permission.LiberalPermissionChecker;
import com.liferay.testray.rest.resource.v1_0.TestrayRunComparisonResource;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author Nilton Vieira
 * @generated
 */
@Component(
	property = "resource.locator.key=/testray-rest/v1.0/TestrayRunComparison",
	service = TestrayRunComparisonResource.Factory.class
)
@Generated("")
public class TestrayRunComparisonResourceFactoryImpl
	implements TestrayRunComparisonResource.Factory {

	@Override
	public TestrayRunComparisonResource.Builder create() {
		return new TestrayRunComparisonResource.Builder() {

			@Override
			public TestrayRunComparisonResource build() {
				if (_user == null) {
					throw new IllegalArgumentException("User is not set");
				}

				Function<InvocationHandler, TestrayRunComparisonResource>
					testrayRunComparisonResourceProxyProviderFunction =
						ResourceProxyProviderFunctionHolder.
							_testrayRunComparisonResourceProxyProviderFunction;

				return testrayRunComparisonResourceProxyProviderFunction.apply(
					(proxy, method, arguments) -> _invoke(
						method, arguments, _checkPermissions,
						_httpServletRequest, _httpServletResponse,
						_preferredLocale, _uriInfo, _user));
			}

			@Override
			public TestrayRunComparisonResource.Builder checkPermissions(
				boolean checkPermissions) {

				_checkPermissions = checkPermissions;

				return this;
			}

			@Override
			public TestrayRunComparisonResource.Builder httpServletRequest(
				HttpServletRequest httpServletRequest) {

				_httpServletRequest = httpServletRequest;

				return this;
			}

			@Override
			public TestrayRunComparisonResource.Builder httpServletResponse(
				HttpServletResponse httpServletResponse) {

				_httpServletResponse = httpServletResponse;

				return this;
			}

			@Override
			public TestrayRunComparisonResource.Builder preferredLocale(
				Locale preferredLocale) {

				_preferredLocale = preferredLocale;

				return this;
			}

			@Override
			public TestrayRunComparisonResource.Builder uriInfo(
				UriInfo uriInfo) {

				_uriInfo = uriInfo;

				return this;
			}

			@Override
			public TestrayRunComparisonResource.Builder user(User user) {
				_user = user;

				return this;
			}

			private boolean _checkPermissions = true;
			private HttpServletRequest _httpServletRequest;
			private HttpServletResponse _httpServletResponse;
			private Locale _preferredLocale;
			private UriInfo _uriInfo;
			private User _user;

		};
	}

	private static Function<InvocationHandler, TestrayRunComparisonResource>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			TestrayRunComparisonResource.class.getClassLoader(),
			TestrayRunComparisonResource.class);

		try {
			Constructor<TestrayRunComparisonResource> constructor =
				(Constructor<TestrayRunComparisonResource>)
					proxyClass.getConstructor(InvocationHandler.class);

			return invocationHandler -> {
				try {
					return constructor.newInstance(invocationHandler);
				}
				catch (ReflectiveOperationException
							reflectiveOperationException) {

					throw new InternalError(reflectiveOperationException);
				}
			};
		}
		catch (NoSuchMethodException noSuchMethodException) {
			throw new InternalError(noSuchMethodException);
		}
	}

	private Object _invoke(
			Method method, Object[] arguments, boolean checkPermissions,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Locale preferredLocale,
			UriInfo uriInfo, User user)
		throws Throwable {

		String name = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(user.getUserId());

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (checkPermissions) {
			PermissionThreadLocal.setPermissionChecker(
				_defaultPermissionCheckerFactory.create(user));
		}
		else {
			PermissionThreadLocal.setPermissionChecker(
				new LiberalPermissionChecker(user));
		}

		TestrayRunComparisonResource testrayRunComparisonResource =
			_componentServiceObjects.getService();

		testrayRunComparisonResource.setContextAcceptLanguage(
			new AcceptLanguageImpl(httpServletRequest, preferredLocale, user));

		Company company = _companyLocalService.getCompany(user.getCompanyId());

		testrayRunComparisonResource.setContextCompany(company);

		testrayRunComparisonResource.setContextHttpServletRequest(
			httpServletRequest);
		testrayRunComparisonResource.setContextHttpServletResponse(
			httpServletResponse);
		testrayRunComparisonResource.setContextUriInfo(uriInfo);
		testrayRunComparisonResource.setContextUser(user);
		testrayRunComparisonResource.setExpressionConvert(_expressionConvert);
		testrayRunComparisonResource.setFilterParserProvider(
			_filterParserProvider);
		testrayRunComparisonResource.setGroupLocalService(_groupLocalService);
		testrayRunComparisonResource.setResourceActionLocalService(
			_resourceActionLocalService);
		testrayRunComparisonResource.setResourcePermissionLocalService(
			_resourcePermissionLocalService);
		testrayRunComparisonResource.setRoleLocalService(_roleLocalService);
		testrayRunComparisonResource.setSortParserProvider(_sortParserProvider);

		try {
			return method.invoke(testrayRunComparisonResource, arguments);
		}
		catch (InvocationTargetException invocationTargetException) {
			throw invocationTargetException.getTargetException();
		}
		finally {
			_componentServiceObjects.ungetService(testrayRunComparisonResource);

			PrincipalThreadLocal.setName(name);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);
		}
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<TestrayRunComparisonResource>
		_componentServiceObjects;

	@Reference
	private PermissionCheckerFactory _defaultPermissionCheckerFactory;

	@Reference(
		target = "(result.class.name=com.liferay.portal.kernel.search.filter.Filter)"
	)
	private ExpressionConvert<Filter> _expressionConvert;

	@Reference
	private FilterParserProvider _filterParserProvider;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private SortParserProvider _sortParserProvider;

	@Reference
	private UserLocalService _userLocalService;

	private static class ResourceProxyProviderFunctionHolder {

		private static final Function
			<InvocationHandler, TestrayRunComparisonResource>
				_testrayRunComparisonResourceProxyProviderFunction =
					_getProxyProviderFunction();

	}

	private class AcceptLanguageImpl implements AcceptLanguage {

		public AcceptLanguageImpl(
			HttpServletRequest httpServletRequest, Locale preferredLocale,
			User user) {

			_httpServletRequest = httpServletRequest;
			_preferredLocale = preferredLocale;
			_user = user;
		}

		@Override
		public List<Locale> getLocales() {
			return Arrays.asList(getPreferredLocale());
		}

		@Override
		public String getPreferredLanguageId() {
			return LocaleUtil.toLanguageId(getPreferredLocale());
		}

		@Override
		public Locale getPreferredLocale() {
			if (_preferredLocale != null) {
				return _preferredLocale;
			}

			if (_httpServletRequest != null) {
				Locale locale = (Locale)_httpServletRequest.getAttribute(
					WebKeys.LOCALE);

				if (locale != null) {
					return locale;
				}
			}

			return _user.getLocale();
		}

		@Override
		public boolean isAcceptAllLanguages() {
			return false;
		}

		private final HttpServletRequest _httpServletRequest;
		private final Locale _preferredLocale;
		private final User _user;

	}

}