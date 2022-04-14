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

package com.liferay.oauth2.application.factory.user.agent;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import org.osgi.framework.Constants;
import org.osgi.service.component.ComponentConstants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import com.liferay.oauth2.application.factory.user.agent.configuration.v1.OAuth2ApplicationUserAgentConfiguration;
import com.liferay.oauth2.provider.constants.ClientProfile;
import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.model.OAuth2ApplicationScopeAliases;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.oauth2.provider.service.OAuth2ApplicationScopeAliasesLocalService;
import com.liferay.oauth2.provider.util.OAuth2SecureRandomGenerator;
import com.liferay.oauth2.provider.util.builder.OAuth2ScopeBuilder;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;

/**
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.oauth2.application.factory.user.agent.configuration.v1.OAuth2ApplicationUserAgentConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE,
	immediate = true
)
public class Oauth2ApplicationFactoryUserAgent {

	@Activate
	public Oauth2ApplicationFactoryUserAgent(
			@Reference CompanyLocalService companyLocalService,
			@Reference OAuth2ApplicationLocalService
				oAuth2ApplicationLocalService,
			@Reference OAuth2ApplicationScopeAliasesLocalService
				oAuth2ApplicationScopeAliasesLocalService,
			@Reference UserLocalService userLocalService,
			Map<String, Object> properties)
		throws Exception {

		_companyLocalService = companyLocalService;
		_oAuth2ApplicationLocalService = oAuth2ApplicationLocalService;
		_userLocalService = userLocalService;

		_oAuth2ApplicationUserAgentConfiguration =
			ConfigurableUtil.createConfigurable(
				OAuth2ApplicationUserAgentConfiguration.class, properties);

		Company company = _companyLocalService.getCompanyById(
			_oAuth2ApplicationUserAgentConfiguration.companyId());

		OAuth2Application oAuth2Application = _getOrAddOAuth2Application(
			company);

		boolean update = false;

		if (!Objects.equals(
				oAuth2Application.getDescription(),
				_oAuth2ApplicationUserAgentConfiguration.description()) ||
			!Objects.equals(
				oAuth2Application.getHomePageURL(),
				_oAuth2ApplicationUserAgentConfiguration.homePageURL()) ||
			!Objects.equals(
				oAuth2Application.getPrivacyPolicyURL(),
				_oAuth2ApplicationUserAgentConfiguration.privacyPolicyURL()) ||
			!Objects.equals(
				oAuth2Application.getRedirectURIs(),
				_oAuth2ApplicationUserAgentConfiguration.redirectURL())) {

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
					_oAuth2ApplicationUserAgentConfiguration.description(),
					oAuth2Application.getFeaturesList(),
					_oAuth2ApplicationUserAgentConfiguration.homePageURL(),
					oAuth2Application.getIconFileEntryId(),
					oAuth2Application.getName(),
					_oAuth2ApplicationUserAgentConfiguration.privacyPolicyURL(),
					Collections.singletonList(
						_oAuth2ApplicationUserAgentConfiguration.redirectURL()),
					oAuth2Application.getRememberDevice(),
					oAuth2Application.getTrustedApplication());
		}

		List<String> scopeAliasesList = ListUtil.fromArray(
			_oAuth2ApplicationUserAgentConfiguration.scopes());

		oAuth2Application = _oAuth2ApplicationLocalService.updateScopeAliases(
			oAuth2Application.getUserId(),
			oAuth2Application.getUserName(),
			oAuth2Application.getOAuth2ApplicationId(),
			scopeAliasesList);

		_oAuth2Application = oAuth2Application;
	}

	@Deactivate
	private void deactivate(Integer reason) throws PortalException {
		if (reason ==
				ComponentConstants.DEACTIVATION_REASON_CONFIGURATION_DELETED) {

			_oAuth2ApplicationLocalService.deleteOAuth2Application(
				_oAuth2Application);
		}
	}

	private OAuth2Application _getOrAddOAuth2Application(Company company)
		throws Exception {

		DynamicQuery dynamicQuery =
			_oAuth2ApplicationLocalService.dynamicQuery();

		Property companyIdProperty = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(companyIdProperty.eq(company.getCompanyId()));

		Property nameProperty = PropertyFactoryUtil.forName("name");

		dynamicQuery.add(
			nameProperty.eq(
				_oAuth2ApplicationUserAgentConfiguration.name()));

		List<OAuth2Application> oAuth2Applications =
			_oAuth2ApplicationLocalService.dynamicQuery(dynamicQuery);

		if (!oAuth2Applications.isEmpty()) {
			return oAuth2Applications.get(0);
		}

		User user = _userLocalService.getDefaultUser(company.getCompanyId());

		OAuth2Application oAuth2Application =
			_oAuth2ApplicationLocalService.addOAuth2Application(
				company.getCompanyId(), user.getUserId(), user.getScreenName(),
				ListUtil.fromArray(
					GrantType.AUTHORIZATION_CODE_PKCE, GrantType.JWT_BEARER),
				user.getUserId(),
				OAuth2SecureRandomGenerator.generateClientId(),
				ClientProfile.USER_AGENT_APPLICATION.id(),
				null, _oAuth2ApplicationUserAgentConfiguration.description(),
				null, _oAuth2ApplicationUserAgentConfiguration.homePageURL(), 0,
				_oAuth2ApplicationUserAgentConfiguration.name(),
				_oAuth2ApplicationUserAgentConfiguration.privacyPolicyURL(),
				Collections.singletonList(
					_oAuth2ApplicationUserAgentConfiguration.redirectURL()),
				false, true, null,
				new ServiceContext());

		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
				"dependencies/logo.png");

		return _oAuth2ApplicationLocalService.updateIcon(
			oAuth2Application.getOAuth2ApplicationId(), inputStream);
	}

	private final CompanyLocalService _companyLocalService;
	private final OAuth2Application _oAuth2Application;
	private final OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;
	private final OAuth2ApplicationUserAgentConfiguration
		_oAuth2ApplicationUserAgentConfiguration;
	private final UserLocalService _userLocalService;

}