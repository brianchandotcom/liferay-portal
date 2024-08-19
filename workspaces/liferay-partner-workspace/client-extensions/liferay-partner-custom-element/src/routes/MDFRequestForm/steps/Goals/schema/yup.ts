/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {array, number, object, string} from 'yup';

import isObjectEmpty from '../../../../../common/utils/isObjectEmpty';

const goalsSchema = object({
	company: object({
		id: number(),
		name: string(),
	})
		.default(undefined)
		.test('is-empty', 'Required', (value) => !isObjectEmpty(value)),
	currency: object({
		key: string(),
		name: string(),
	})
		.default(undefined)
		.test('is-empty', 'Required', (value) => !isObjectEmpty(value)),
	currencyExchangeRate: number().moreThan(0).required('Required'),
	liferayBusinessSalesGoals: array()
		.min(1, 'Required')
		.max(3, 'You have exceed the choose limit'),
	liferayBusinessSalesGoalsOther: string().when(
		'liferayBusinessSalesGoals',
		(liferayBusinessSalesGoals, schema) => {
			return liferayBusinessSalesGoals.includes('Other - Please describe')
				? string().required('Required')
				: schema;
		}
	),
	overallCampaignDescription: string()
		.trim()
		.max(255, 'You have exceeded the character limit')
		.required('Required'),
	overallCampaignName: string()
		.trim()
		.max(80, 'You have exceeded the 80 characters limit')
		.required('Required'),
	partnerCountries: array()
		.of(
			object({
				key: string(),
				name: string(),
			})
		)
		.default(undefined)
		.min(1, 'Required'),
	targetAudienceRoles: array().min(1, 'Required'),
	targetMarkets: array()
		.min(1, 'Required')
		.max(3, 'You have exceed the choose limit'),
});

export default goalsSchema;
