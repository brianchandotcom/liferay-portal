/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {zodResolver} from '@hookform/resolvers/zod';
import {z} from 'zod';

import i18n from '../i18n';

const zodSchema = {
	accountCreator: z.object({
		accounts: z.any().array().optional(),
		agreeToTermsAndConditions: z.boolean(),
		companyName: z
			.string()
			.min(1, {message: 'Please enter a company name to continue'}),
		emailAddress: z.string().email('Please fill in valid email'),
		extension: z.string().optional(),
		familyName: z
			.string()
			.min(3, {message: i18n.translate('this-field-is-required')}),
		givenName: z.string(),
		industry: z
			.string()
			.min(3, {message: 'Please select an industry to continue'}),
		phone: z.object({
			code: z.string(),
			flag: z.string(),
		}),
		phoneNumber: z
			.string()
			.min(1, {message: i18n.translate('this-field-is-required')}),
	}),
	becomePublisherForm: z.object({
		emailAddress: z.string().email('Please fill in valid email'),
		extension: z.string().optional(),
		firstName: z.string().min(3, 'First name is required'),
		lastName: z.string().min(3, 'Last name is required'),
		phone: z
			.object({
				code: z.string(),
				flag: z.string(),
			})
			.optional(),
		phoneNumber: z
			.string()
			.min(1, {message: i18n.translate('this-field-is-required')}),
		requestDescription: z
			.string()
			.max(500, {message: 'Request Description is required'}),
	}),
	billingAddress: z.object({
		city: z.string().min(1),
		country: z.string().min(1),
		countryISOCode: z.string().optional(),
		name: z.string().min(1),
		phoneNumber: z.string().min(1),
		regionISOCode: z.string().optional(),
		street1: z.string().min(1),
		street2: z.string().optional(),
		zip: z.string().min(1),
	}),
	contactSales: z.object({
		accountName: z
			.string()
			.min(3, i18n.sub('x-is-required', 'account-name')),
		additionalAppsRequested: z.string(),
		comments: z.string(),
		email: z.string().email(i18n.translate('please-fill-in-a-valid-email')),
		name: z.string().min(3, i18n.sub('x-is-required', 'name')),
	}),
	generateLicenseKey: z.object({
		description: z.string().max(100, {message: 'Invalid license name'}),
		hostname: z.string(),
		ipAddress: z.string(),
		macAddress: z.string(),
		subscription: z
			.object({
				name: z.string(),
				productPurchasedKey: z.string(),
				skuId: z.number(),
			})
			.optional(),
	}),
	invitedNewMember: z.object({
		emailAddress: z
			.string()
			.min(5, 'Please enter an email')
			.email('Invalid email address'),
		firstName: z.string().min(3, 'Please enter member name'),
		lastName: z.string().min(3, 'Last name is required'),
		roles: z.string().array().min(5, 'Please select at least one role'),
	}),
	newCustomer: z.object({
		accountBriefs: z.any().optional(),
		alternateName: z.string().optional(),
		currentPassword: z.string().optional(),
		emailAddress: z.string().email(),
		familyName: z.string(),
		givenName: z.string(),
		id: z.number().optional(),
		image: z.string().optional(),
		imageBlob: z.any().optional(),
		isCustomerAccount: z.boolean().optional(),
		isPublisherAccount: z.boolean().optional(),
		newsSubscription: z.boolean(),
		password: z.string().optional(),
	}),
};

export {zodResolver};

export default zodSchema;
