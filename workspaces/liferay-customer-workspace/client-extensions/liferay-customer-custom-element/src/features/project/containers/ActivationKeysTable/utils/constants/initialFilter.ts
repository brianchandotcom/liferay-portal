/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const INITIAL_FILTER = {
	environmentTypes: {
		name: 'Environment Type',
		value: [],
	},
	expirationDate: {
		name: 'Exp. Date',
		value: {
			onOrAfter: undefined,
			onOrBefore: undefined,
		},
	},
	hasValue: false,
	instanceSizes: {
		name: 'Instance Size',
		value: [],
	},
	keyType: {
		name: 'Key Type',
		value: {
			hasCluster: undefined,
			hasOnPremise: undefined,
			hasVirtualCluster: undefined,
			maxNodes: '',
			minNodes: '',
		},
	},
	productVersions: {
		name: 'Product Version',
		value: [],
	},
	searchTerm: '',
	startDate: {
		name: 'Start Date',
		value: {
			onOrAfter: undefined,
			onOrBefore: undefined,
		},
	},
	status: {
		name: 'Status',
		value: [],
	},
};
