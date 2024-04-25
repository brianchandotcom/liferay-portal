/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ProductType} from '../../../../../../../enums/ProductType';
import i18n from '../../../../../../../i18n';

export const offeringTypesDescription = {
	[ProductType.CLOUD]: [
		{
			description: i18n.translate(
				'the-cloud-app-is-client-extension-based-and-is-compatible-with-a-customer’s-self-hosted-environment'
			),
			label: i18n.translate('self-hosted'),
		},
		{
			description: i18n.translate(
				'the-cloud-app-is-client-extension-based-and-is-compatible-with-liferay’s-self-managed-formerly-dxp-cloud-offering'
			),
			label: i18n.translate('self-managed'),
		},
		{
			description: i18n.translate(
				'the-cloud-app-is-client-extension-based-and-is-compatible-with-liferay-experience-cloud-lxc-it-fully-supports-and-deploys-on-extension-environments-in-lxc'
			),
			label: i18n.translate('fully-managed'),
		},
	],
	[ProductType.DXP]: [
		{
			description: i18n.translate(
				'the-dxp-app-is-module-based-and-is-compatible-with-7-4-builds-of-liferay-dxp'
			),
			label: i18n.translate('self-hosted'),
		},
		{
			description: i18n.translate(
				'the-dxp-app-is-module-based-and-is-compatible-with-7-4-builds-of-liferay-dxp-self-hosted-liferay-cloud-formerly-dxp-cloud'
			),
			label: i18n.translate('self-managed'),
		},
		{
			description: i18n.translate(
				'dxp-module-based-apps-are-not-supported-on-liferay-experience-cloud-lxc'
			),
			label: i18n.translate('fully-managed'),
		},
	],
} as const;
