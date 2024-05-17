/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import i18n from '../i18n';

export enum PRODUCT_CATEGORIES {
	MARKETPLACE_APP_CATEGORY = 'marketplace-app-category',
	MARKETPLACE_APP_TAGS = 'marketplace-app-tags',
	MARKETPLACE_SOLUTION_CATEGORY = 'marketplace-solution-category',
	MARKETPLACE_SOLUTION_TAGS = 'marketplace-solution-tags',
}

export enum PRODUCT_SPECIFICATION_KEY {
	SOLUTION_COMPANY_DESCRIPTION = 'solution-company-description',
	SOLUTION_COMPANY_EMAIL = 'solution-company-email',
	SOLUTION_COMPANY_PHONE = 'solution-company-phone',
	SOLUTION_COMPANY_WEBSITE = 'solution-company-website',
	SOLUTION_CONTACT_EMAIL = 'solution-contact-email',
	SOLUTION_DETAILS_BLOCKS = 'solution-details-blocks',
	SOLUTION_HEADER_DESCRIPTION = 'solution-header-description',
	SOLUTION_HEADER_TITLE = 'solution-header-title',
	SOLUTION_HEADER_VIDEO_DESCRIPTION = 'solution-header-video-description',
	SOLUTION_HEADER_VIDEO_URL = 'solution-header-video-url',
}

export enum PRODUCT_TAGS {
	APP_ICON = 'app-icon',
	SOLUTION_DETAILS = 'solution-details',
	SOLUTION_HEADER = 'solution-header',
}

export enum PRODUCT_WORKFLOW_STATUS_CODE {
	APPROVED = 0,
	PENDING = 1,
	DRAFT = 2,
}

export const PRODUCT_WORKFLOW_STATUS_LABEL = {
	[PRODUCT_WORKFLOW_STATUS_CODE.APPROVED]: i18n.translate('approved'),
	[PRODUCT_WORKFLOW_STATUS_CODE.PENDING]: i18n.translate('under-review'),
	[PRODUCT_WORKFLOW_STATUS_CODE.DRAFT]: i18n.translate('draft'),
};
