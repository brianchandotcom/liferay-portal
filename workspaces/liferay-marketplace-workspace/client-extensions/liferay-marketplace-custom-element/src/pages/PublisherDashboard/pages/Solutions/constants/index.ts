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
		label: i18n.translate('create'),
		path: 'publisher',
		selected: true,
		title: i18n.translate('create-template'),
	},
	{
		checked: false,
		description: i18n.translate(
			'enter-your-solution-details-this-information-will-be-used-for-submission-presentation-customer-support-and-search-capabilities'
		),
		label: i18n.translate('profile'),
		path: 'profile',
		selected: false,
		title: i18n.translate('define-the-solution-profile'),
	},
	{
		checked: false,
		description: i18n.translate(
			'design-the-storefront-for-your-solution-this-will-set-the-information-displayed-on-the-solutions-page-this-section-is-dedicated-to-creating-the-solutions-header'
		),
		label: i18n.translate('solution-header'),
		path: 'header',
		selected: false,
		title: i18n.translate('customize-solution-header'),
	},
	{
		checked: false,
		description: i18n.translate(
			'design-the-storefront-for-your-solution-this-will-set-the-information-displayed-on-the-solutions-page-this-section-is-dedicated-to-creating-the-solutions-detail-content'
		),
		label: i18n.translate('solution-details'),
		path: 'details',
		selected: false,
		title: i18n.translate('customize-storefront-solutions-details'),
	},
	{
		checked: false,
		description: i18n.translate(
			'define-profile-company-information-for-your-solution-this-will-inform-users-about-this-versions-updates-on-the-storefront'
		),
		label: i18n.translate('company-profile'),
		path: 'company',
		selected: false,
		title: i18n.translate('provide-company-profile-details'),
	},
	{
		checked: false,
		description: i18n.translate(
			'define-contact-us-information-for-your-solution-this-will-inform-users-about-this-versions-updates-on-the-storefront'
		),
		label: i18n.translate('contact-us'),
		path: 'contact',
		selected: false,
		title: i18n.translate('provide-contact-us-details'),
	},
	{
		checked: false,
		description: i18n.translate(
			'please-review-before-submitting-once-sent-you-will-not-be-able-to-edit-any-information-until-this-submission-is-completely-reviewed-by-liferay'
		),
		label: i18n.translate('submit'),
		path: 'submit',
		selected: false,
		title: 'Review and submit solution',
	},
];
