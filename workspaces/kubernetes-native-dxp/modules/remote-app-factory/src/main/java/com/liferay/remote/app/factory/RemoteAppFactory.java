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

package com.liferay.remote.app.factory;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.osgi.framework.Constants;
import org.osgi.service.component.ComponentConstants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import com.liferay.petra.string.StringUtil;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PropsValues;
import com.liferay.remote.app.factory.configuration.v1.RemoteAppFactoryConfiguration;
import com.liferay.remote.app.model.RemoteAppEntry;
import com.liferay.remote.app.service.RemoteAppEntryLocalService;

/**
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.remote.app.factory.configuration.v1.RemoteAppFactoryConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE,
	immediate = true
)
public class RemoteAppFactory {

	@Activate
	public RemoteAppFactory(
			@Reference CompanyLocalService companyLocalService,
			@Reference RemoteAppEntryLocalService remoteAppEntryLocalService,
			@Reference UserLocalService userLocalService,
			Map<String, Object> properties)
		throws PortalException {

		_remoteAppEntryLocalService = remoteAppEntryLocalService;

		RemoteAppFactoryConfiguration remoteAppFactoryConfiguration =
			ConfigurableUtil.createConfigurable(
				RemoteAppFactoryConfiguration.class, properties);

		String externalReferenceCode = _getExternalReferenceCode(properties);

		Company company = companyLocalService.getCompanyById(
			remoteAppFactoryConfiguration.companyId());

		User defaultAdminUser = userLocalService.getUserByScreenName(
			company.getCompanyId(), PropsValues.DEFAULT_ADMIN_SCREEN_NAME);

		Map<Locale, String> nameMap = Collections.singletonMap(
			LocaleUtil.getMostRelevantLocale(),
			remoteAppFactoryConfiguration.name());

		RemoteAppEntry remoteAppEntry =
			_remoteAppEntryLocalService.
				fetchRemoteAppEntryByExternalReferenceCode(
					company.getCompanyId(), externalReferenceCode);

		if (remoteAppEntry == null) {
			remoteAppEntry =
				_remoteAppEntryLocalService.
					addOrUpdateCustomElementRemoteAppEntry(
						externalReferenceCode, defaultAdminUser.getUserId(),
						StringUtil.merge(
							remoteAppFactoryConfiguration.webComponentCssUrl(),
							","),
						remoteAppFactoryConfiguration.elementName(),
						StringUtil.merge(
							remoteAppFactoryConfiguration.webComponentUrl(),
							","),
						false, remoteAppFactoryConfiguration.description(),
						remoteAppFactoryConfiguration.friendlyURLMapping(),
						remoteAppFactoryConfiguration.instanceable(), nameMap,
						remoteAppFactoryConfiguration.portletDisplayCategory(),
						remoteAppFactoryConfiguration.
							portletServiceProperties(),
						null);

			_remoteAppEntryLocalService.updateStatus(
				defaultAdminUser.getUserId(),
				remoteAppEntry.getRemoteAppEntryId(),
				WorkflowConstants.STATUS_APPROVED);
		}

		_remoteAppEntry = remoteAppEntry;
	}

	@Deactivate
	private void deactivate(Integer reason) throws PortalException {
		if (reason ==
				ComponentConstants.DEACTIVATION_REASON_CONFIGURATION_DELETED) {

			_remoteAppEntryLocalService.deleteRemoteAppEntry(_remoteAppEntry);
		}
	}

	private String _getExternalReferenceCode(Map<String, Object> properties) {
		String externalReferenceCode = GetterUtil.getString(
			properties.get(Constants.SERVICE_PID));

		int pos = externalReferenceCode.indexOf('~');

		if (pos > 0) {
			externalReferenceCode = externalReferenceCode.substring(pos + 1);
		}

		return externalReferenceCode;
	}

	private final RemoteAppEntry _remoteAppEntry;
	private final RemoteAppEntryLocalService _remoteAppEntryLocalService;

}