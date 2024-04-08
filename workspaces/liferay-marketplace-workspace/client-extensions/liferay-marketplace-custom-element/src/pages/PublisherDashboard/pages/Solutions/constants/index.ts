/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import i18n from '../../../../../i18n';

export const SOLUTION_FLOW_ITEMS = [
	{
		checked: false,
		description: i18n.translate(
			'review-and-accept-the-legal-agreement-between-you-and-liferay-before-proceeding-you-are-about-to-create-a-new-solution-submission'
		),
		label: 'Create',
		path: 'publisher',
		selected: true,
		title: i18n.translate('create-template'),
	},
	{
		checked: false,
		description: i18n.translate(
			'enter-your-solution-details-this-information-will-be-used-for-submission-presentation-customer-support-and-search-capabilities'
		),
		label: 'Profile',
		path: 'profile',
		selected: false,
		title: i18n.translate('define-the-solution-profile'),
	},
	{
		checked: false,
		description: i18n.translate(
			'design-the-storefront-for-your-solution-this-will-set-the-information-displayed-on-the-solutions-page-this-section-is-dedicated-to-creating-the-solutions-header'
		),
		label: 'Solution Header',
		path: 'header',
		selected: false,
		title: i18n.translate('customize-solution-header'),
	},
	{
		checked: false,
		description: i18n.translate(
			'design-the-storefront-for-your-solution-this-will-set-the-information-displayed-on-the-solutions-page-this-section-is-dedicated-to-creating-the-solutions-detail-content'
		),
		label: 'Solution Details',
		path: 'details',
		selected: false,
		title: i18n.translate('customize-storefront-solutions-details'),
	},
	{
		checked: false,
		description:
			'Define profile company information for your solution. This will inform users about this version’s updates on the storefront.',
		label: 'Company Profile',
		path: 'company',
		selected: false,
		title: 'Provide company profile details',
	},
	{
		checked: false,
		description:
			'Define contact us information for your solution. This will inform users about this version’s updates on the storefront.',
		label: 'Contact Us',
		path: 'contact',
		selected: false,
		title: 'Provide contact us details',
	},
	{
		checked: false,
		description:
			'Please, review before submitting. Once sent, you will not be able to edit any information until this submission is completely reviewed by Liferay.',
		label: 'Submit',
		path: 'submit',
		selected: false,
		title: 'Review and submit solution',
	},
];
