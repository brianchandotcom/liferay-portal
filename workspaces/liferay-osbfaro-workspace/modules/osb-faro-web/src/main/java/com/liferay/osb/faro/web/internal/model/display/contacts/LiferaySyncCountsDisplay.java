/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.engine.client.model.Individual;
import com.liferay.osb.faro.engine.client.model.Results;
import com.liferay.osb.faro.engine.client.model.provider.LiferayProvider;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collections;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class LiferaySyncCountsDisplay {

	public LiferaySyncCountsDisplay() {
	}

	public LiferaySyncCountsDisplay(
		FaroProject faroProject, String dataSourceId,
		LiferayProvider.ContactsConfiguration contactsConfiguration,
		ContactsEngineClient contactsEngineClient) {

		LiferayProvider.ContactsConfiguration allContactsContactsConfiguration =
			new LiferayProvider.ContactsConfiguration();

		allContactsContactsConfiguration.setEnableAllContacts(true);

		_allUsersCount = contactsEngineClient.getDataSourceDXPTotal(
			faroProject, dataSourceId, allContactsContactsConfiguration);

		if (ListUtil.isNotNull(contactsConfiguration.getOrganizations())) {
			LiferayProvider.ContactsConfiguration
				organizationsContactsConfiguration =
					new LiferayProvider.ContactsConfiguration();

			organizationsContactsConfiguration.setOrganizations(
				contactsConfiguration.getOrganizations());
			organizationsContactsConfiguration.setUserGroups(
				Collections.emptyList());

			_organizationsUsersCount =
				contactsEngineClient.getDataSourceDXPTotal(
					faroProject, dataSourceId,
					organizationsContactsConfiguration);
		}

		Results<Individual> results = contactsEngineClient.getIndividuals(
			faroProject, dataSourceId, false, 1, 0, null);

		_currentUsersCount = results.getTotal();

		_totalUsersCount = contactsEngineClient.getDataSourceDXPTotal(
			faroProject, dataSourceId, contactsConfiguration);

		if (ListUtil.isNotNull(contactsConfiguration.getUserGroups())) {
			LiferayProvider.ContactsConfiguration
				userGroupsContactsConfiguration =
					new LiferayProvider.ContactsConfiguration();

			userGroupsContactsConfiguration.setOrganizations(
				Collections.emptyList());
			userGroupsContactsConfiguration.setUserGroups(
				contactsConfiguration.getUserGroups());

			_userGroupsUsersCount = contactsEngineClient.getDataSourceDXPTotal(
				faroProject, dataSourceId, userGroupsContactsConfiguration);
		}
	}

	private long _allUsersCount;
	private long _currentUsersCount;
	private long _organizationsUsersCount;
	private long _totalUsersCount;
	private long _userGroupsUsersCount;

}