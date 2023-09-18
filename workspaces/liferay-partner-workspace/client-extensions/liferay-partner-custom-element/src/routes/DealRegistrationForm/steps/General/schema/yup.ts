/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {array, number, object, string} from 'yup';

import isObjectEmpty from '../../../../../common/utils/isObjectEmpty';

const phoneRegex = /^[0-9-+.\][ ()]*$/;

const postalCodetRegex = /^[a-zA-Z0-9 _.-]*$/;

const generalSchema = object({
	additionalContact: object({
		emailAddress: string()
			.max(255, 'reached max characters')
			.email('must be a valid email'),
		firstName: string().max(255, 'reached max characters'),
		lastName: string().max(255, 'reached max characters'),
	}),
	additionalInformationAboutTheOpportunity: string().max(
		500,
		'reached max characters'
	),
	currency: object({
		key: string(),
		name: string(),
	}).test('is-empty', 'Required', (value) => !isObjectEmpty(value)),
	partnerAccount: object({
		id: number(),
		name: string(),
	}).test('is-empty', 'Required', (value) => !isObjectEmpty(value)),
	primaryProspect: object({
		businessUnit: string()
			.trim()
			.max(255, 'reached max characters')
			.required('Required'),
		department: object({
			key: string(),
			name: string(),
		}).test('is-empty', 'Required', (value) => !isObjectEmpty(value)),
		emailAddress: string()
			.max(255, 'reached max characters')
			.email('must be a valid email')
			.required('Required'),
		firstName: string()
			.trim()
			.max(255, 'reached max characters')
			.required('Required'),
		jobRole: object({
			key: string(),
			name: string(),
		}).test('is-empty', 'Required', (value) => !isObjectEmpty(value)),
		lastName: string()
			.trim()
			.max(255, 'reached max characters')
			.required('Required'),
		phone: string()
			.trim()
			.matches(phoneRegex, 'Phone number is not valid')
			.required('Required'),
	}),
	projectCategories: array().min(1, 'Required'),
	projectNeed: array().min(1, 'Required'),
	projectTimeline: string()
		.trim()
		.max(255, 'reached max characters')
		.required('Required'),
	prospect: object({
		accountName: string()
			.trim()
			.max(255, 'reached max characters')
			.required('Required'),
		address: string()
			.trim()
			.max(255, 'reached max characters')
			.required('Required'),
		city: string()
			.trim()
			.max(255, 'reached max characters')
			.required('Required'),
		country: object({
			key: string(),
			name: string(),
		}).test('is-empty', 'Required', (value) => !isObjectEmpty(value)),
		industry: object({
			key: string(),
			name: string(),
		}).test('is-empty', 'Required', (value) => !isObjectEmpty(value)),
		postalCode: string()
			.trim()
			.matches(postalCodetRegex, 'Postal Code is not valid')
			.required('Required'),
		state: object({
			key: string(),
			name: string(),
		}).test('is-empty', 'Required', (value, context) =>
			context.parent.country.name === 'US' ? !isObjectEmpty(value) : true
		),
	}),
});

export default generalSchema;
