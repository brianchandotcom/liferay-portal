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

package com.liferay.oauth2.application.factory;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.VirtualHost;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.VirtualHostLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Raymond Augé
 */
@Component(
	immediate = true,
	service = CompanyDomainProvider.class
)
public class CompanyDomainProvider {

	@Activate
	public CompanyDomainProvider(
		@Reference CompanyLocalService companyLocalService,
		@Reference Portal portal,
		@Reference VirtualHostLocalService virtualHostLocalService) {

		_companyLocalService = companyLocalService;
		_portal = portal;
		_virtualHostLocalService = virtualHostLocalService;
	}

	public List<String> getCompanyDomains(long companyId) {
		List<String> domains = new ArrayList<>();

		_addLCPDomain(domains);

		Company company = _companyLocalService.fetchCompanyById(companyId);

		if ((company != null) && !domains.contains(company.getWebId())) {
			domains.add(company.getWebId());
		}

		DynamicQuery dynamicQuery = _virtualHostLocalService.dynamicQuery();

		Property companyIdProperty = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(companyIdProperty.eq(companyId));

		List<VirtualHost> virtualHosts = _virtualHostLocalService.dynamicQuery(
			dynamicQuery);

		for (VirtualHost virtualHost : virtualHosts) {
			if (!domains.contains(virtualHost.getHostname())) {
				domains.add(virtualHost.getHostname());
			}
		}

		String cdnHostHttp = _portal.getCDNHostHttp(companyId);

		if (Validator.isNotNull(cdnHostHttp) &&
			!domains.contains(cdnHostHttp)) {

			domains.add(cdnHostHttp);
		}

		String cdnHostHttps = _portal.getCDNHostHttps(companyId);

		if (Validator.isNotNull(cdnHostHttps) &&
			!domains.contains(cdnHostHttps)) {

			domains.add(cdnHostHttps);
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"For companyId ", companyId,
					" found the following domains ", domains));
		}

		return domains;
	}

	private void _addLCPDomain(List<String> domains) {
		String lcpServiceDomain = System.getenv("LCP_SERVICE_DOMAIN");
		String lcpProjectId = System.getenv("LCP_PROJECT_ID");
		String webserverServiceHost = System.getenv("WEBSERVER_SERVICE_HOST");

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Attempting to detect LCP Domain from environment using: ",
					"LCP_SERVICE_DOMAIN=", lcpServiceDomain, " LCP_PROJECT_ID=",
					lcpProjectId, " WEBSERVER_SERVICE_HOST=",
					webserverServiceHost));
		}

		String lcpDomain = null;

		if (Validator.isNotNull(lcpServiceDomain) &&
			Validator.isNotNull(lcpProjectId)) {

			lcpDomain = StringBundler.concat(
				lcpProjectId, ".", lcpServiceDomain);
		}

		if ((lcpDomain != null) && Validator.isNotNull(webserverServiceHost)) {
			lcpDomain = "webserver-".concat(lcpDomain);
		}

		if ((lcpDomain != null) && !domains.contains(lcpDomain)) {
			domains.add(lcpDomain);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CompanyDomainProvider.class);

	private final CompanyLocalService _companyLocalService;
	private final Portal _portal;
	private final VirtualHostLocalService _virtualHostLocalService;

}
