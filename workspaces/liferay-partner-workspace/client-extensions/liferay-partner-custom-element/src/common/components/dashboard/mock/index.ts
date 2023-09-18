/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

interface IObject {
	[key: string]: {
		[key: string]: number;
	};
}

export const partnerLevelProperties: IObject = {
	gold: {
		goalARR: 125000,
		newProjectExistingBusiness: 2,
		partnerMarketingUser: 1,
		partnerSalesUser: 3,
	},
	platinum: {
		partnerMarketingUser: 1,
		partnerSalesUser: 5,
	},
	silver: {
		partnerMarketingUser: 1,
		partnerSalesUser: 1,
	},
};

export const mdf = {
	ProgressClain: {
		approved: {qtd: 120, total: 'USD $6.500,50'},
		pending: {qtd: 100, total: 'USD $5.500,00'},
	},
	ProgressMdf: {
		approved: {qtd: 300, total: 'USD $80.000,29'},
		pending: {qtd: 92, total: 'USD $12.993,00'},
	},
};
