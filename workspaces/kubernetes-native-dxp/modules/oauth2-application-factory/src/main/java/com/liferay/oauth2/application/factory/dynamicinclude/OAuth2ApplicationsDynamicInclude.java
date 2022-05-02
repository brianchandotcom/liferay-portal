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

package com.liferay.oauth2.application.factory.dynamicinclude;

import com.liferay.oauth2.application.factory.OAuth2ApplicationFactoryConstants;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(immediate = true, property = {}, service = DynamicInclude.class)
public class OAuth2ApplicationsDynamicInclude implements DynamicInclude {

	@Activate
	public OAuth2ApplicationsDynamicInclude(
		@Reference OAuth2ApplicationLocalService oAuth2ApplicationLocalService,
		@Reference Portal portal) {

		_oAuth2ApplicationLocalService = oAuth2ApplicationLocalService;
		_portal = portal;
	}

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		PrintWriter printWriter = httpServletResponse.getWriter();

		DynamicQuery dynamicQuery =
			_oAuth2ApplicationLocalService.dynamicQuery();

		Property companyIdProperty = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(
			companyIdProperty.eq(_portal.getCompanyId(httpServletRequest)));

		Property nameProperty = PropertyFactoryUtil.forName("name");

		dynamicQuery.add(nameProperty.like("%".concat(_SUBDOMAIN)));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		List<OAuth2Application> oAuth2Applications =
			_oAuth2ApplicationLocalService.dynamicQuery(dynamicQuery);

		for (OAuth2Application oAuth2Application : oAuth2Applications) {
			String name = oAuth2Application.getName();

			name = name.substring(0, name.length() - _SUBDOMAIN.length());

			jsonObject.put(
				name,
				JSONFactoryUtil.createJSONObject(
				).put(
					"clientId", oAuth2Application.getClientId()
				).put(
					"homePageURL", oAuth2Application.getHomePageURL()
				).put(
					"redirectURIs", JSONFactoryUtil.createJSONArray(
						oAuth2Application.getRedirectURIsList())
				));
		}

		printWriter.write(
			StringBundler.concat(
				"<script type=\"text/javascript\">",
				"Liferay.OAuth = {",
				"  getAuthorizeURL: function() {",
				"    return '", _portal.getPortalURL(httpServletRequest),
					_portal.getPathContext(), "/o/oauth2/authorize';",
				"  },",
				"  getBuiltInRedirectURL: function() {",
				"    return '", _portal.getPortalURL(httpServletRequest),
					_portal.getPathContext(), "/o/builtin/oauth2/redirect';",
				"  },",
				"  getIntrospectURL: function() {",
				"    return '", _portal.getPortalURL(httpServletRequest),
					_portal.getPathContext(), "/o/oauth2/introspect';",
				"  },",
				"  getTokenURL: function() {",
				"    return '", _portal.getPortalURL(httpServletRequest),
					_portal.getPathContext(), "/o/oauth2/token';",
				"  },",
				"  getUserAgentApplication: function(serviceName) {",
				"    return Liferay.OAuth._userAgentApplications[serviceName];",
				"  },",
				"  _userAgentApplications: ", jsonObject.toJSONString(),
				"}",
				"</script>"));
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"/html/common/themes/top_js.jspf#resources");
	}

	private static final String _SUBDOMAIN =
		OAuth2ApplicationFactoryConstants.USER_AGENT_SUBDOMAIN;

	private final OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;
	private final Portal _portal;

}