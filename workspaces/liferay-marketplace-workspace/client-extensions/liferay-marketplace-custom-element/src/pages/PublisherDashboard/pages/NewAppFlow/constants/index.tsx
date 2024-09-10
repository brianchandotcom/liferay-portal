/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ReactDOMServer from 'react-dom/server';

import {NewAppInitialState} from '../../../../../context/NewAppContext';
import {ProductUploadType} from '../../../../../enums/ProductUploadType';
import i18n from '../../../../../i18n';
import zodSchema from '../../../../../schema/zod';

export const LIFERAY_VERSION_PICKLIST = 'LIFERAY-VERSIONS';
export const MAX_IMAGE_QUANTITY = 5;
export const MAX_SIZE_5MBS = 5_000_000;

export const APP_FLOW_ITEMS = [
	{
		description:
			'Review and accept the legal agreement between you and Liferay before proceeding, You are about to create a new app submission.',
		label: i18n.translate('create'),
		path: '',
		title: 'Create new app',
	},
	{
		description:
			'Enter your new app details. This information will be used for submission, presentation, customer support, and search capabilities.',
		label: i18n.translate('profile'),
		parseSchema: (context: NewAppInitialState) =>
			zodSchema.appPublishing.profile.safeParse(context.profile),
		path: 'profile',
		title: 'Define the app profile',
	},
	{
		description:
			'Use one of the following methods to provide your app builds.',
		label: 'Build',
		parseSchema: (context: NewAppInitialState) =>
			zodSchema.appPublishing.build.safeParse(context.build),
		path: 'build',
		title: 'Provide app build',
	},
	{
		description:
			'Design the storefront for your app. This will set the information displayed on the app page in the Marketplace.',
		label: 'Storefront',
		parseSchema: (context: NewAppInitialState) =>
			zodSchema.appPublishing.storefront.safeParse(context.storefront),
		path: 'storefront',
		title: 'Customize app storefront',
	},
	{
		description: `Define version information for your app. This will inform users about this version's updates on the storefront.`,
		label: 'Version',
		parseSchema: (context: NewAppInitialState) =>
			zodSchema.appPublishing.version.safeParse(context.version),
		path: 'version',
		title: 'Provide version details',
	},
	{
		description:
			'Select one of the pricing models for your app. This will define how much users will pay. To enable paid apps, you must be a business and enter payment information in your Marketplace account profile.',
		label: 'Pricing',
		path: 'pricing',
		title: 'Choose pricing model',
	},
	{
		description: `Define the licensing approach for your app. This will impact users' licensing renewal experience.`,
		label: 'Licensing',
		path: 'licensing',
		title: 'Select licensing terms',
	},
	{
		description: `Define the licensing approach for your app. This will impact users' licensing renewal experience.`,
		hide: true,
		label: 'Licensing',
		path: 'licensing-prices',
		title: 'Select licensing terms',
	},
	{
		description: `Inform the support and help references. This will impact how users will experience this app's customer support and learning.`,
		label: 'Support',
		path: 'support',
		title: 'Provide app support and help',
	},
	{
		description:
			'Please, review before submitting. Once sent, you will not be able to edit any information until this submission is completely reviewed by Liferay.',
		label: 'Submit',
		path: 'submit',
		title: 'Review and submit app',
	},
];

export const COMPATIBLE_OFFERING_CARDS = [
	{
		description: i18n.translate(
			'create-a-cloud-app-to-be-delivered-as-a-live-service'
		),
		icon: 'check-circle',
		title: i18n.translate('yes'),
		tooltip: ReactDOMServer.renderToString(
			<span>
				{i18n.translate(
					'the-app-submission-is-compatible-with-liferay-experience-cloud-and'
				)}
				<a
					href="https://learn.liferay.com/web/guest/w/dxp/building-applications/client-extensions#client-extensions"
					target="_blank"
				>
					{i18n.translate('client-extensions')}
				</a>
				.
			</span>
		),
		value: true,
	},
	{
		description: i18n.translate(
			'create-a-dxp-app-to-be-delivered-as-a-download'
		),
		icon: 'times-circle',
		title: i18n.translate('no'),
		tooltip: i18n.translate(
			'the-app-submission-is-integrates-with-liferay-dxp-version-7-4-or-later'
		),
		value: false,
	},
];

export const BUILD_UPLOAD_OPTIONS = {
	cloud: [
		{
			description: i18n.translate(
				'use-any-local-zip-files-to-upload-max-file-size-is-500-mb'
			),
			icon: 'upload',
			title: i18n.translate('via-zip-upload'),
			tooltip: ReactDOMServer.renderToString(
				<span>
					{i18n.translate(
						'zip-files-must-be-in-universal-file-format-archive-uffa-the-specially-structured-zip-encoded-archive-used-to-package-client-extension-project-outputs-this-format-must-support-the-following-use-cases-deliver-batch-engine-data-files-compatible-with-all-deployment-targets-deliver-dxp-configuration-resource-compatible-with-all-deployment-targets-deliver-static-resources-compatible-with-all-deployment-targets-deliver-the-infrastructure-metadata-necessary-to-deploy-to-lxc-sm-for-more-information-see'
					)}
					<a href="https://learn.liferay.com/web/guest/w/dxp/building-applications/client-extensions/working-with-client-extensions#working-with-client-extensions">
						{i18n.translate('liferay-learn')}
					</a>
				</span>
			),
			value: ProductUploadType.ZIP_UPLOAD,
		},
		{
			description: i18n.translate(
				'use-any-build-from-any-available-liferay-experience-cloud-account-requires-lxc-account'
			),
			disabled: true,
			icon: 'cloud',
			title: i18n.translate('via-liferay-experience-cloud-integration'),
			tooltip: i18n.translate(
				'in-the-future-you-will-be-able-to-submit-your-app-directly-from-liferay-experience-cloud-projects'
			),
			value: ProductUploadType.LXC,
		},
		{
			description: i18n.translate(
				'use-any-build-from-your-computer-connecting-with-a-github-provider'
			),
			disabled: true,
			title: i18n.translate('via-github-repo'),
			tooltip: i18n.translate(
				'in-the-future-you-will-be-able-to-submit-your-app-source-code-for-additional-support-and-partnership-opportunities-with-liferay'
			),
			value: ProductUploadType.GITHUB,
		},
	],
	dxp: [
		{
			description: i18n.translate(
				'please-be-sure-to-specify-liferay-compatibility-through-the-appropriate-properties-or-xml-files-in-your-plugin'
			),
			icon: 'upload',
			title: i18n.translate('via-liferay-plugin-packages'),
			tooltip: ReactDOMServer.renderToString(
				<span>
					{i18n.translate(
						'zip-files-must-be-in-universal-file-format-archive-uffa-the-specially-structured-zip-encoded-archive-used-to-package-client-extension-project-outputs-this-format-must-support-the-following-use-cases-deliver-batch-engine-data-files-compatible-with-all-deployment-targets-deliver-dxp-configuration-resource-compatible-with-all-deployment-targets-deliver-static-resources-compatible-with-all-deployment-targets-deliver-the-infrastructure-metadata-necessary-to-deploy-to-lxc-sm-for-more-information-see'
					)}
					<a href="https://learn.liferay.com/web/guest/w/dxp/building-applications/client-extensions/working-with-client-extensions#working-with-client-extensions">
						{i18n.translate('liferay-learn')}
					</a>
				</span>
			),
			value: ProductUploadType.ZIP_UPLOAD,
		},
		{
			description: i18n.translate(
				'use-any-build-from-any-available-liferay-experience-cloud-account-requires-lxc-account'
			),
			disabled: true,
			icon: 'cloud',
			title: i18n.translate('via-liferay-experience-cloud-integration'),
			tooltip: i18n.translate(
				'in-the-future-you-will-be-able-to-submit-your-app-directly-from-liferay-experience-cloud-projects'
			),
			value: ProductUploadType.LXC,
		},
		{
			description: i18n.translate(
				'use-any-build-from-your-computer-connecting-with-a-github-provider'
			),
			disabled: true,
			title: i18n.translate('via-github-repo'),
			tooltip: i18n.translate(
				'in-the-future-you-will-be-able-to-submit-your-app-source-code-for-additional-support-and-partnership-opportunities-with-liferay'
			),
			value: ProductUploadType.GITHUB,
		},
	],
};

export const LICENSING_OPTIONS = [
	{
		description: 'The app version is offered in perpetuity.',
		icon: 'time',
		title: 'Perpetual License',
		tooltip: 'A perpetual license requires no renewal and never expires.',
		value: 'Perpetual',
	},
	{
		description: 'App License must be renewed annually.',
		icon: 'document-pending',
		title: 'Subscription License',
		tooltip: 'A subscription license that must be renewed annually.',
		value: 'Subscription',
	},
] as const;

export const LICENSING_30_DAYS_TRIAL_OPTIONS = [
	{
		description: 'Offer a 30-day free trial for this app.',
		icon: 'check-circle',
		title: 'Yes',
		tooltip: 'Offer a 30-day free trial for this app.',
		value: true,
	},
	{
		description: 'Do not offer a 30-day free trial.',
		icon: 'times-circle',
		title: 'No',
		tooltip: 'Do not offer a 30-day trial for this app.',
		value: false,
	},
] as const;

export const PRICING_OPTIONS = [
	{
		description: 'The app is offered in the Marketplace with no charge.',
		title: 'Free',
		tooltip: 'The app is offered in the Marketplace with no charge.',
	},
	{
		description:
			'To enable paid apps, you must be a business and enter payment information in your Marketplace account profile.',
		icon: 'credit-card',
		title: 'Paid',
		tooltip:
			'For paid apps, you can choose the subscription model you want to use on the next screen.',
	},
] as const;
