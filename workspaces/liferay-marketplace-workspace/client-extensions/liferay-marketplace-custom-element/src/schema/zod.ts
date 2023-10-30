/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {zodResolver} from '@hookform/resolvers/zod';
import {z} from 'zod';

const zodSchema = {
	accountCreator: z.object({
		accounts: z.any().array().optional(),
		agreeToTermsAndConditions: z.boolean(),
		companyName: z
			.string()
			.min(1, {message: 'Please enter a company name to continue'}),
		emailAddress: z.string().email('Please fill in valid email'),
		extension: z.string().optional(),
		familyName: z.string().nonempty({message: 'This field is required'}),
		givenName: z.string(),
		industry: z
			.string()
			.nonempty({message: 'Please select an industry to continue'}),
		phone: z.object({
			code: z.string(),
			flag: z.string(),
		}),
		phoneNumber: z.string().min(1, {message: 'This field is required'}),
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
			.nonempty('Please enter an email')
			.email('Invalid email address'),
		firstName: z.string().nonempty('Please enter member name'),
		lastName: z.string().nonempty('Last name is required'),
		roles: z.string().array().nonempty('Please select at least one role'),
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
