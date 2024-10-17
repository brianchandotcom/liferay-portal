/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import i18n from '~/common/I18n';
import {
	createAccountUserRoles,
	deleteAccountUserRoles,
	getAccountAccountRolesByExternalReferenceCode,
} from '~/common/services/liferay/graphql/queries';

import {
	addContactRoleNameByEmailByProject,
	deleteContactRoleNameByEmailByProject,
} from '../../../common/services/liferay/rest/raysource/LicenseKeys';

const addContactRoleLiferay = async (item, project, client) => {
	return client.mutate({
		context: {
			displaySuccess: false,
		},
		mutation: createAccountUserRoles,
		variables: {
			accountRoleId: item.filterId,
			emailAddress: item.email,
			externalReferenceCode: project.accountKey,
		},
	});
};

const addContactRoleRaysource = (
	item,
	oAuthToken,
	project,
	provisioningServerAPI
) => {
	return addContactRoleNameByEmailByProject({
		accountKey: project.accountKey,
		emailURI: encodeURI(item.email),
		firstName: item.label,
		lastName: item.label,
		oAuthToken,
		provisioningServerAPI,
		roleName: item.category.role,
	});
};

const getAccountRolesId = async (project, client) => {
	const result = await client.query({
		context: {
			displaySuccess: false,
		},
		query: getAccountAccountRolesByExternalReferenceCode,
		variables: {
			externalReferenceCode: project.accountKey,
		},
	});

	return result.data.accountAccountRolesByExternalReferenceCode.items;
};

const getContactRoleByFilter = (filter) => {
	if (filter.includes('privacy')) {
		return 'Data Breach Contact';
	}

	if (filter.includes('security')) {
		return 'Security Incident Contact';
	}

	if (filter.includes('critical')) {
		return 'Critical Incident Contact';
	}
};

const HIGH_PRIORITY_CONTACT_CATEGORIES = {
	criticalIncident: i18n.translate('critical-incident'),
	privacyBreach: i18n.translate('privacy-breach'),
	securityBreach: i18n.translate('security-breach'),
};

const removeContactRoleLiferay = async (item, project, client) => {
	return client.mutate({
		context: {
			displaySuccess: false,
		},
		mutation: deleteAccountUserRoles,
		variables: {
			accountKey: project.accountKey,
			accountRoleId: item.filterId,
			emailAddress: item.email,
		},
	});
};

const removeContactRoleRaysource = async (
	item,
	oAuthToken,
	project,
	provisioningServerAPI
) => {
	return await deleteContactRoleNameByEmailByProject({
		accountKey: project.accountKey,
		emailURI: encodeURI(item.email),
		oAuthToken,
		provisioningServerAPI,
		rolesToDelete: item.filter,
	});
};

const rolesHighPriorityContacts = [
	'Data Breach Contact',
	'Security Incident Contact',
	'Critical Incident Contact',
];

const updateLiferayContact = (items, fn, project, client) =>
	Promise.all(items?.map((item) => fn(item, project, client)));

const updateRaysourceContact = (
	fn,
	contacts,
	oAuthToken,
	project,
	provisioningServerAPI
) =>
	Promise.all(
		contacts?.map((item) =>
			fn(item, oAuthToken, project, provisioningServerAPI)
		)
	);

export {
	addContactRoleLiferay,
	addContactRoleRaysource,
	getAccountRolesId,
	getContactRoleByFilter,
	HIGH_PRIORITY_CONTACT_CATEGORIES,
	removeContactRoleLiferay,
	removeContactRoleRaysource,
	rolesHighPriorityContacts,
	updateLiferayContact,
	updateRaysourceContact,
};
