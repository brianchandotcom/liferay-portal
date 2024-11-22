/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import i18n from '../i18n';

export enum PRODUCT_CATEGORIES {
	MARKETPLACE_APP_CATEGORY = 'marketplace-app-category',
	MARKETPLACE_APP_TAGS = 'marketplace-app-tags',
	MARKETPLACE_LIFERAY_VERSION = 'marketplace-liferay-version',
	MARKETPLACE_PRODUCT_TYPE = 'marketplace-product-type',
	MARKETPLACE_SOLUTION_CATEGORY = 'marketplace-solution-category',
	MARKETPLACE_SOLUTION_TAGS = 'marketplace-solution-tags',
}

export enum PRODUCT_IMAGE_FALLBACK_CATEGORIES {
	PRODUCT_ICON = 'productIcon',
	PRODUCT_IMAGE = 'productImage',
}

export enum PRODUCT_LICENSE {
	CLOUD = 'cloud-license-usage-type',
	DXP = 'dxp-license-usage-type',
}

export enum PRODUCT_PRICE_MODEL {
	FREE = 'Free',
	PAID = 'Paid',
}

export enum PRODUCT_SPECIFICATION_KEY {
	APP_BUILD_CLOUD_COMPATIBLE = 'type',
	APP_BUILD_NUMBER_OF_CPUS = 'cpu',
	APP_BUILD_RAM_IN_GBS = 'ram',
	APP_DEVELOPER_NAME = 'developer-name',
	APP_LICENSING_TYPE = 'license-type',
	APP_PRICING_MODEL = 'price-model',
	APP_SUPPORT_DOCUMENTATION_URL = 'appdocumentationurl',
	APP_SUPPORT_EMAIL = 'supportemailaddress',
	APP_SUPPORT_INSTALLATION_GUIDE_URL = 'appinstallationguideurl',
	APP_SUPPORT_PHONE = 'supportphone',
	APP_SUPPORT_PUBLISHER_WEBSITE_URL = 'publisherwebsiteurl',
	APP_SUPPORT_URL = 'supporturl',
	APP_SUPPORT_USAGE_TERMS_URL = 'appusagetermsurl',
	APP_VERSION = 'latest-version',
	APP_VERSION_NOTES = 'product-notes',
	DOWNLOADABLE_CLOUD_APP = 'downloadable-cloud-app',
	LIFERAY_VERSION = 'liferay-version',
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
	SOLUTION_TYPE = 'solution-type',
}

export enum PRODUCT_SUPPORT_SPECIFICATION_KEY {
	APP_DOCUMENTATION_URL = 'appdocumentationurl',
	APP_INSTALLATION_GUIDE_URL = 'appinstallationguideurl',
	APP_USAGE_TERMS_URL = 'appusagetermsurl',
	PUBLISHER_WEBSITE_URL = 'publisherwebsiteurl',
	SUPPORT_EMAIL = 'supportemailaddress',
	SUPPORT_PHONE = 'supportphone',
	SUPPORT_URL = 'supporturl',
}

export enum PRODUCT_TAGS {
	APP_ICON = 'app-icon',
	SOLUTION_DETAILS = 'solution-details',
	SOLUTION_HEADER = 'solution-header',
	SOLUTION_PROFILE_APP_ICON = 'solution-profile-app-icon',
}

export enum PRODUCT_TYPE_VOCABULARY {
	APP = 'App',
	SOLUTION = 'Solution',
}

export enum PRODUCT_WORKFLOW_STATUS_CODE {
	APPROVED = 0,
	DRAFT = 2,
	PENDING = 1,
}

export const PRODUCT_WORKFLOW_STATUS_LABEL = {
	[PRODUCT_WORKFLOW_STATUS_CODE.APPROVED]: i18n.translate('approved'),
	[PRODUCT_WORKFLOW_STATUS_CODE.DRAFT]: i18n.translate('draft'),
	[PRODUCT_WORKFLOW_STATUS_CODE.PENDING]: i18n.translate('under-review'),
};

export enum SOLUTION_TYPES {
	ANALYTICS = 'analytics',
	PRE_BUILT_TRIAL = 'pre-built-trial',
}
