/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';

import i18n from '../i18n';
import { removeHTMLTags } from '../utils/string';

const domainRegex = /^(?!:\/\/)([a-zA-Z0-9-_]+?\.)+[a-zA-Z]{2,}$/;

const ipv4Regex =
	/^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;

const macAddressRegex = /^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$/;

function checkRegExp(regex: RegExp, values: string) {
	if (!values) {
		return true;
	}

	return values.split('\n').every((value) => {
		if (!value.trim()) {
			return true;
		}

		return regex.test(value.trim());
	});
}

const baseAppSchema = {
	appUsageTermsURL: z.string().url().or(z.literal('')),
	documentationURL: z.string().url().or(z.literal('')),
	installationGuideURL: z.string().url().or(z.literal('')),
	url: z.string().url().or(z.literal('')),
};

const baseContentSchema = z.object({
	description: z.string().min(1).refine(removeHTMLTags),
	title: z.string().min(1),
});

const billingAddress = z.object({
	city: z.string().min(1),
	country: z.string().min(1),
	countryISOCode: z.string().optional(),
	name: z.string().min(1),
	phoneNumber: z.string().min(1),
	regionISOCode: z.string().optional(),
	street1: z.string().min(1),
	street2: z.string().optional(),
	zip: z.string().min(1),
});

const blocksContentSchemas = {
	textBlock: baseContentSchema,
	textImages: baseContentSchema.extend({
		files: z.array(z.any()).min(1),
	}),
	textVideo: baseContentSchema.extend({
		videoUrl: z.string().url().min(1),
	}),
};

const contentMediaTypeImage = z.object({
	headerImages: z.array(z.any()).min(1),
});

const contentMediaTypeVideo = z.object({
	headerVideoDescription: z.string().optional(),
	headerVideoUrl: z.string().url().min(1),
});

const freeApp = z.object({
	...baseAppSchema,
	email: z.string().email().or(z.literal('')),
	phone: z.string().min(8).or(z.literal('')),
	publisherWebsiteURL: z.string().url().or(z.literal('')),
});

const paidApp = z.object({
	...baseAppSchema,
	email: z.string().email(i18n.translate('please-fill-in-a-valid-email')),
	phone: z.string().min(8, {
		message: i18n.translate('please-fill-in-a-valid-phone-number'),
	}),
	publisherWebsiteURL: z
		.string()
		.url({ message: i18n.translate('please-fill-in-a-valid-url') })
		.transform((url) => (url.startsWith('http') ? url : `https://${url}`)),
});

const personalInformationSchema = {
	businessEmail: z.string().email('Please fill in valid email'),
	companyName: z
		.string()
		.min(3, 'Company name is required')
		.optional()
		.or(z.literal('')),
	country: z.string().min(2, 'Please select the country to continue'),
	extension: z.string().optional(),
	fullName: z.string().min(3, 'Full name is required'),
	intlCode: z.object({ code: z.string(), flag: z.string() }),
	jobTitle: z
		.string()
		.min(3, 'Job title is required')
		.optional()
		.or(z.literal('')),
	phoneNumber: z.string(),
};

const resources = z.object({
	free: z.number(),
	limit: z.number(),
	used: z.number(),
});

const rootProjectPlanUsage = z.object({
	cpu: resources,
	instance: resources,
	memory: resources,
});

const zodSchema = {
	accountCreator: z.object({
		accounts: z.any().array().optional(),
		companyName: z
			.string()
			.min(1, { message: 'Please enter a company name to continue' }),
		country: z
			.string()
			.min(2, { message: 'Please select the country to continue' }),
		emailAddress: z
			.string()
			.email(i18n.translate('this-field-is-required')),
		extension: z.string().optional(),
		familyName: z
			.string()
			.min(3, { message: i18n.translate('this-field-is-required') }),
		givenName: z.string(),
		phone: z.object({
			code: z.string(),
			flag: z.string(),
		}),
		phoneNumber: z
			.string()
			.min(1, { message: 'Please enter a phone number to continue.' }),
	}),
	accountForm: z.object({
		accountImage: z.any(),
		accountName: z
			.string()
			.min(1, { message: 'Please enter a company name to continue' }),
		accountType: z.string().min(1),
		billingAddress,
		emailAddress: z.string().email('Please fill in valid email'),
		taxNumber: z
			.string()
			.min(1, { message: 'Please enter a Tax/VAT number to continue' }),
	}),
	activationKey: z.object({
		...personalInformationSchema,
		domain: z.string().min(3, 'Domain is required'),
		notifyMeAboutProducts: z.boolean(),
		purpose: z.string().min(3, 'Purpose is required'),
		purposeDescription: z
			.string()
			.min(3, 'Purpose is required')
			.optional()
			.or(z.literal('')),
		termsAndConditions: z.boolean().refine((value) => value === true),
		userAgreement: z.boolean().refine((value) => value === true),
	}),
	aiHubForm: z.object({
		...personalInformationSchema,
		administrationEmail: z
			.string()
			.email('Please fill in valid email'),
		aiHubAccountName: z.string().min(3, 'AI Hub Account Name is required'),
		purpose: z.string().min(3, 'Purpose is required'),
		purposeDescription: z
			.string()
			.min(3, 'Purpose is required')
			.optional()
			.or(z.literal('')),
		termsAndConditions: z.boolean().refine((value) => value === true),
		userAgreement: z.boolean().refine((value) => value === true),
	}),
	analyticsProvisioning: z.object({
		_refAllowedEmailDomains: z.array(z.any()),
		_refIncidentReportContacts: z.array(z.any()),
		acceptTerms: z.boolean().refine((value) => value, {
			message: 'You must agree with the terms',
		}),
		allowedEmailDomains: z
			.array(z.string())
			.optional()
			.default([])
			.refine(
				(values) =>
					values.length
						? values.every((value) => domainRegex.test(value))
						: true,
				'One of the chosen domains is invalid.'
			),
		dataCenterLocation: z.string(),
		friendlyWorkspaceURL: z.string().optional(),
		incidentReportContacts: z.array(z.string().email()).min(1),
		productKey: z.string().optional(),
		productName: z.string(),
		productPurchaseKey: z.string().optional(),
		workspaceName: z.string().min(3),
		workspaceOwnerEmail: z.string().email(),
	}),
	appPublishing: {
		build: z.object({
			appType: z.string(),
			liferayPackages: z
				.array(
					z.object({
						file: z.array(z.any()).nonempty(),
						versions: z.array(z.string()).min(1),
					})
				)
				.min(1),
		}),
		profile: z.object({
			areas: z.array(z.any()).nonempty(),
			categories: z.object({ label: z.string(), value: z.string().min(1) }),
			description: z.string().min(3),
			name: z.string().min(3),
			tags: z.array(z.any()).nonempty(),
		}),
		storefront: z.object({ images: z.array(z.any()).min(1).max(10) }),
		support: {
			supportForFreeApp: freeApp,
			supportForPaidApp: paidApp,
		},
		termsAndConditions: z.boolean().refine((data) => data === true),
		version: z.object({
			notes: z.string().optional(),
			version: z.string().min(1),
		}),
	},
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
			.min(1, { message: i18n.translate('this-field-is-required') }),
		publisherType: z.array(z.string()).min(1),
		requestDescription: z
			.string()
			.min(3, { message: 'Request Description is required' }),
	}),
	billingAddress,
	contactSales: z.object({
		accountName: z
			.string()
			.min(3, i18n.sub('x-is-required', 'account-name')),
		additionalAppsRequested: z.string(),
		comments: z.string(),
		email: z.string().email(i18n.translate('please-fill-in-a-valid-email')),
		name: z.string().min(3, i18n.sub('x-is-required', 'name')),
	}),
	extendSSATrial: z.object({
		duration: z.coerce
			.number()
			.int()
			.min(1, 'Please enter a valid number (1-90)')
			.max(90, 'Please enter a valid number (1-90)'),
		reason: z.string().min(3),
	}),
	generateLicenseKey: z.object({
		description: z
			.string()
			.min(3)
			.max(100, { message: 'Invalid license name' }),
		hostname: z.string().optional().or(z.literal('')),
		ipAddress: z.string().refine((value) => checkRegExp(ipv4Regex, value), {
			message: 'Invalid IP address',
		}),
		macAddress: z
			.string()
			.refine((value) => checkRegExp(macAddressRegex, value), {
				message: 'Invalid MAC address',
			}),
		subscription: z
			.object({
				name: z.string(),
				productPurchasedKey: z.string(),
				productVersion: z.string(),
				skuId: z.number(),
			})
			.optional(),
	}),
	installProductSchema: z.object({
		environment: z.object({
			isExtensionEnvironment: z.boolean(),
			projectId: z.string(),
		}),
		project: z.object({
			availabilityToProduct: z.boolean(),
			environments: z.array(
				z.object({
					isExtensionEnvironment: z.boolean(),
					projectId: z.string(),
				})
			),
			rootProjectId: z.string(),
			rootProjectPlanUsage,
		}),
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
	ldpProvisioning: z.object({
		_refAllowedEmailDomains: z.array(z.any()),
		_refIncidentReportContacts: z.array(z.any()),
		acceptTerms: z.boolean().refine((value) => value, {
			message: 'You must agree with the terms',
		}),
		allowedEmailDomains: z
			.array(z.string())
			.optional()
			.default([])
			.refine(
				(values) =>
					values.length
						? values.every((value) => domainRegex.test(value))
						: true,
				'One of the chosen domains is invalid.'
			),
		dataCenterLocation: z.string(),
		friendlyWorkspaceURL: z.string().optional(),
		incidentReportContacts: z.array(z.string().email()).min(1),
		productKey: z.string().optional(),
		productPurchaseKey: z.string().optional(),
		workspaceName: z.string().min(3),
		workspaceOwnerEmail: z.string().email(),
	}),
	productFeedback: z.object({
		companyName: z.string().optional(),
		emailAddress: z
			.string()
			.email('Invalid email address')
			.min(1, 'Email is required'),
		fullName: z.string().min(1, 'Full Name is required'),
		jobTitle: z.string().optional(),
		notify: z.boolean().optional(),
		ratingEaseOfUse: z.number().min(0).max(5).optional(),
		ratingSatisfaction: z.number().min(0).max(5).optional(),
		ratingUsefulness: z.number().min(0).max(5).optional(),
		suggestionFeatures: z.string().optional(),
		suggestionImprovements: z.string().optional(),
		suggestionSatisfaction: z.string().optional(),
	}),

	solutionPublishing: {
		company: z
			.object({
				description: z.string().min(1),
				email: z.string().email().min(1),
				phone: z.string().min(1),
				website: z.string().min(1),
			})
			.refine((data) => !!removeHTMLTags(data.description)),
		contactUs: z.string().email().min(1),
		details: z
			.array(
				z.object({
					content: z.lazy(() =>
						z.union([
							blocksContentSchemas.textBlock,
							blocksContentSchemas.textImages,
							blocksContentSchemas.textVideo,
						])
					),
					type: z.enum([
						'text-block',
						'text-images-block',
						'text-video-block',
					]),
				})
			)
			.min(2),
		header: z
			.object({
				contentType: z.object({
					content: z.lazy(() =>
						z.union([contentMediaTypeImage, contentMediaTypeVideo])
					),
					type: z.enum(['embed-video-url', 'upload-images']),
				}),
				description: z.string().min(1),
				title: z.string().min(1),
			})
			.refine((data) => !!removeHTMLTags(data.description)),
		profile: z.object({
			categories: z.array(z.any()).nonempty(),
			description: z.string().min(3),
			name: z.string().min(3),
			tags: z.array(z.any()).nonempty(),
		}),
		termsAndConditions: z.boolean().refine((data) => data === true),
	},
	ssaInviteUsers: z.object({
		emailAddress: z
			.string()
			.email({ message: i18n.translate('please-fill-in-a-valid-email') }),
		roles: z
			.array(z.object({ value: z.string() }))
			.nonempty(i18n.translate('at-least-one-role-must-be-provided')),
	}),
	ssaTrialForm: z.object({
		duration: z.coerce
			.number()
			.int()
			.min(1, 'Please enter a valid number (1-90)')
			.max(90, 'Please enter a valid number (1-90)'),
		emailAddress: z
			.array(
				z.object({
					key: z.string(),
					label: z.string(),
					value: z.string(),
				})
			)
			.refine(
				(emails) =>
					emails.every(
						(error) =>
							z.string().email().safeParse(error.value).success
					),
				{ message: 'One or more email addresses are invalid' }
			)
			.optional(),
		objective: z.string().refine((val) => val, {
			message: 'Select an Option',
		}),
		projectId: z
			.string()
			.min(3, { message: 'Project ID must have at least 3 characters' })
			.regex(/^[a-zA-Z0-9]+$/, {
				message: 'Only alphanumeric characters are allowed',
			}),
		siteInitializerKey: z.string(),
	}),
	trialForm: z.object({
		accountId: z.string().optional(),
		consoleInviteEmailAddresses: z.array(z.string().email()),
		product: z
			.any()
			.refine((value) => !!value, { message: 'Product is required' }),
		sendNotificationEmail: z.boolean(),
	}),
};

export { z, zodResolver };

export default zodSchema;
