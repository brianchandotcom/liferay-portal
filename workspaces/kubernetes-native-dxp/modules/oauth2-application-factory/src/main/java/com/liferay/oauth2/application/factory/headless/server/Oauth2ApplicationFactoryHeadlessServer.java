/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.oauth2.application.factory.headless.server;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.ComponentConstants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import com.liferay.k8s.agent.K8sAgent;
import com.liferay.oauth2.application.factory.CompanyDomainProvider;
import com.liferay.oauth2.application.factory.OAuth2ApplicationFactoryConstants;
import com.liferay.oauth2.application.factory.headless.server.configuration.v1.Oauth2ApplicationHeadlessServerConfiguration;
import com.liferay.oauth2.provider.constants.ClientProfile;
import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.oauth2.provider.constants.OAuth2ProviderActionKeys;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.oauth2.provider.util.OAuth2SecureRandomGenerator;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.service.permission.ModelPermissionsFactory;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PropsValues;

/**
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.oauth2.application.factory.headless.server.configuration.v1.Oauth2ApplicationHeadlessServerConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true
)
public class Oauth2ApplicationFactoryHeadlessServer {

	@Activate
	public Oauth2ApplicationFactoryHeadlessServer(
			@Reference CompanyDomainProvider companyDomainProvider,
			@Reference CompanyLocalService companyLocalService,
			@Reference K8sAgent k8sAgent,
			@Reference OAuth2ApplicationLocalService
				oAuth2ApplicationLocalService,
			@Reference ResourceLocalService resourceLocalService,
			@Reference RoleLocalService roleLocalService,
			@Reference UserLocalService userLocalService,
			Map<String, Object> properties)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Creating or Updating Headless Server profile request ",
					"with: ", properties));
		}

		_companyLocalService = companyLocalService;
		_k8sAgent = k8sAgent;
		_oAuth2ApplicationLocalService = oAuth2ApplicationLocalService;
		_resourceLocalService = resourceLocalService;
		_roleLocalService = roleLocalService;
		_userLocalService = userLocalService;

		_oAuth2ApplicationHeadlessServerConfiguration =
			ConfigurableUtil.createConfigurable(
				Oauth2ApplicationHeadlessServerConfiguration.class, properties);

		Company company = _companyLocalService.getCompanyById(
			_oAuth2ApplicationHeadlessServerConfiguration.companyId());

		List<String> companyDomains =
			companyDomainProvider.getCompanyDomains(company.getCompanyId());

		String protocol = GetterUtil.getString(
			PropsValues.WEB_SERVER_PROTOCOL, Http.HTTP);

		String serviceAddress = StringBundler.concat(
			protocol, "://", companyDomains.get(0));

		List<String> redirectURIsList = Collections.singletonList(
			GetterUtil.getString(
				_oAuth2ApplicationHeadlessServerConfiguration.redirectURL(),
				serviceAddress.concat("/o/builtin/oauth2/redirect")));

		OAuth2Application oAuth2Application = _getOrAddOAuth2Application(
			company, redirectURIsList);

		boolean update = false;

		if (!Objects.equals(
				oAuth2Application.getHomePageURL(),
				_oAuth2ApplicationHeadlessServerConfiguration.homePageURL()) ||
			!Objects.equals(
				oAuth2Application.getRedirectURIsList(), redirectURIsList)) {

			update = true;
		}

		if (update) {
			oAuth2Application =
				_oAuth2ApplicationLocalService.updateOAuth2Application(
					oAuth2Application.getOAuth2ApplicationId(),
					oAuth2Application.getOAuth2ApplicationScopeAliasesId(),
					oAuth2Application.getAllowedGrantTypesList(),
					oAuth2Application.getClientCredentialUserId(),
					oAuth2Application.getClientId(),
					oAuth2Application.getClientProfile(),
					oAuth2Application.getClientSecret(),
					oAuth2Application.getDescription(),
					oAuth2Application.getFeaturesList(),
					_oAuth2ApplicationHeadlessServerConfiguration.homePageURL(),
					oAuth2Application.getIconFileEntryId(),
					oAuth2Application.getName(),
					oAuth2Application.getPrivacyPolicyURL(),
					redirectURIsList, oAuth2Application.getRememberDevice(),
					oAuth2Application.getTrustedApplication());
		}

		List<String> scopeAliasesList = ListUtil.fromArray(
			_oAuth2ApplicationHeadlessServerConfiguration.scopes());

		oAuth2Application = _oAuth2ApplicationLocalService.updateScopeAliases(
			oAuth2Application.getUserId(), oAuth2Application.getUserName(),
			oAuth2Application.getOAuth2ApplicationId(), scopeAliasesList);

		_k8sAgent.createOrUpdateConfigMap(
			HashMapBuilder.put(
				"liferay_oauth2_authorization_uri",
				serviceAddress.concat("/o/oauth2/authorize")
			).put(
				"liferay_oauth2_headless_server_client_id",
				oAuth2Application.getClientId()
			).put(
				"liferay_oauth2_headless_server_client_secret",
				oAuth2Application.getClientSecret()
			).put(
				"liferay_oauth2_headless_server_scopes",
				StringUtil.merge(scopeAliasesList, StringPool.COMMA)
			).put(
				"liferay_oauth2_introspection_uri",
				serviceAddress.concat("/o/oauth2/introspect")
			).put(
				"liferay_oauth2_redirect_uris",
				StringUtil.merge(redirectURIsList, StringPool.COMMA)
			).put(
				"liferay_oauth2_token_uri",
				serviceAddress.concat("/o/oauth2/token")
			).put(
				"liferay_service_domains",
				StringUtil.merge(companyDomains, StringPool.COMMA)
			).build(),
			HashMapBuilder.put(
				"extension",
				_oAuth2ApplicationHeadlessServerConfiguration.name()
			).put(
				OAuth2ApplicationFactoryConstants.
					HEADLESS_SERVER_SUBDOMAIN.substring(1),
				"true"
			).build(),
			_getConfigMapName());

		_oAuth2Application = oAuth2Application;

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Completed Headless Server profile request: ",
					_oAuth2Application));
		}
	}

	@Deactivate
	protected void deactivate(Integer reason) throws PortalException {
		if (reason ==
				ComponentConstants.DEACTIVATION_REASON_CONFIGURATION_DELETED) {

			_oAuth2ApplicationLocalService.deleteOAuth2Application(
				_oAuth2Application);

			_k8sAgent.deleteConfigMapByLabels(
				_getConfigMapName(),
				labels -> !labels.containsKey(
					OAuth2ApplicationFactoryConstants.
						USER_AGENT_SUBDOMAIN.substring(1)
				)
			);
		}
	}

	private User _addOrUpdateServiceUser(
			Company company, ServiceContext serviceContext)
		throws PortalException {

		User user = _userLocalService.getDefaultUser(company.getCompanyId());

		String password = OAuth2SecureRandomGenerator.
			generateClientSecret();

		Date birthdayDate = DateUtil.newDate();

		Calendar calendar = CalendarFactoryUtil.getCalendar();

		calendar.setTime(birthdayDate);

		User serviceUser = _userLocalService.addOrUpdateUser(
			_getName(), user.getUserId(), user.getCompanyId(), false, password,
			password, false,
			_oAuth2ApplicationHeadlessServerConfiguration.name(),
			_getEmailAddress(), company.getLocale(), _getFirstName(), null,
			"Service Account", 0, 0, false, calendar.get(Calendar.MONTH),
			calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.YEAR),
			null, false, serviceContext);

		serviceUser = _userLocalService.updateAgreedToTermsOfUse(
			serviceUser.getUserId(), true);
		serviceUser = _userLocalService.updateLockout(serviceUser, true);
		serviceUser = _userLocalService.updateStatus(
			serviceUser.getUserId(), WorkflowConstants.STATUS_APPROVED,
			serviceContext);

		return serviceUser;
	}

	private String _getFirstName() {
		return StringBundler.concat(
			"(", _oAuth2ApplicationHeadlessServerConfiguration.name(), ")");
	}

	private String _getConfigMapName() {
		return StringBundler.concat(
			_oAuth2ApplicationHeadlessServerConfiguration.name(),
			OAuth2ApplicationFactoryConstants.EXTENSION_SUBDOMAIN);
	}

	private String _getEmailAddress() {
		return StringBundler.concat(
			_oAuth2ApplicationHeadlessServerConfiguration.name(), "@",
			OAuth2ApplicationFactoryConstants.
				HEADLESS_SERVER_SUBDOMAIN.substring(1));
	}

	private String _getName() {
		return StringBundler.concat(
			_oAuth2ApplicationHeadlessServerConfiguration.name(),
			OAuth2ApplicationFactoryConstants.HEADLESS_SERVER_SUBDOMAIN);
	}

	private OAuth2Application _getOrAddOAuth2Application(
			Company company, List<String> redirectURIsList)
		throws Exception {

		DynamicQuery dynamicQuery =
			_oAuth2ApplicationLocalService.dynamicQuery();

		Property companyIdProperty = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(companyIdProperty.eq(company.getCompanyId()));

		Property nameProperty = PropertyFactoryUtil.forName("name");

		dynamicQuery.add(nameProperty.eq(_getName()));

		List<OAuth2Application> oAuth2Applications =
			_oAuth2ApplicationLocalService.dynamicQuery(dynamicQuery);

		if (!oAuth2Applications.isEmpty()) {
			return oAuth2Applications.get(0);
		}

		ServiceContext serviceContext = new ServiceContext();

		User serviceUser = _addOrUpdateServiceUser(
			company, serviceContext);

		OAuth2Application oAuth2Application =
			_oAuth2ApplicationLocalService.addOAuth2Application(
				company.getCompanyId(), serviceUser.getUserId(),
				serviceUser.getScreenName(),
				ListUtil.fromArray(
					GrantType.CLIENT_CREDENTIALS , GrantType.JWT_BEARER),
				serviceUser.getUserId(),
				OAuth2SecureRandomGenerator.generateClientId(),
				ClientProfile.HEADLESS_SERVER.id(),
				OAuth2SecureRandomGenerator.generateClientSecret(), null,
				Arrays.asList("token.introspection"),
				_oAuth2ApplicationHeadlessServerConfiguration.homePageURL(), 0,
				_getName(), null, redirectURIsList, false, true, null,
				serviceContext);

		Role serviceRole = _getOrAddServiceRole(
			serviceUser, company, serviceContext);

		ModelPermissions modelPermissions =
			ModelPermissionsFactory.createWithDefaultPermissions(
				OAuth2Application.class.getName());

		modelPermissions.addRolePermissions(
			serviceRole.getName(),
			OAuth2ProviderActionKeys.ACTION_CREATE_TOKEN);

		_resourceLocalService.updateResources(
			company.getCompanyId(), 0, OAuth2Application.class.getName(),
			oAuth2Application.getOAuth2ApplicationId(), modelPermissions);

		_roleLocalService.addUserRole(
			serviceUser.getUserId(), serviceRole.getRoleId());

		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/logo.png");

		return _oAuth2ApplicationLocalService.updateIcon(
			oAuth2Application.getOAuth2ApplicationId(), inputStream);
	}

	private Role _getOrAddServiceRole(
			User user, Company company, ServiceContext serviceContext)
		throws PortalException {

		Role role = _roleLocalService.fetchRole(
			company.getCompanyId(), _OAUTH2_CLIENT_CREDENTIALS_ROLE);

		if (role != null) {
			return role;
		}

		return _roleLocalService.addRole(
			user.getUserId(), null, 0, _OAUTH2_CLIENT_CREDENTIALS_ROLE,
			Collections.singletonMap(
				company.getLocale(), _OAUTH2_CLIENT_CREDENTIALS_ROLE_TITLE),
			null, RoleConstants.TYPE_REGULAR, null, serviceContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		Oauth2ApplicationFactoryHeadlessServer.class);

	private final static String _OAUTH2_CLIENT_CREDENTIALS_ROLE =
		"oauth2-client-credentials-role";
	private final static String _OAUTH2_CLIENT_CREDENTIALS_ROLE_TITLE =
		"OAuth2 Client Credentials (generated)";

	private final CompanyLocalService _companyLocalService;
	private final K8sAgent _k8sAgent;
	private final OAuth2Application _oAuth2Application;
	private final OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;
	private final Oauth2ApplicationHeadlessServerConfiguration
		_oAuth2ApplicationHeadlessServerConfiguration;
	private final ResourceLocalService _resourceLocalService;
	private final RoleLocalService _roleLocalService;
	private final UserLocalService _userLocalService;

}