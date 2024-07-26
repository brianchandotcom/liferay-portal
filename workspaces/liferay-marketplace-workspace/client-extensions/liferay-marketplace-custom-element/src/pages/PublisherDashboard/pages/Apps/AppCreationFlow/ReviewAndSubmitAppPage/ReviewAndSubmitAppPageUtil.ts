/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {TierPrices} from '../../../../../../components/LicensePriceCard/LicensePriceChildren';

export type App = {
	'attachmentTitle': string;
	'categories': string[];
	'description': string;
	'license-type': string;
	'name': string;
	'price': number;
	'price-model': string;
	'resourceRequirements': {
		cpu: string;
		ram: string;
	};
	'skus': SKU[];
	'storefront': ProductImages[];
	'supportAndHelp': {
		icon: string;
		link: string;
		title: string;
	}[];
	'tags': string[];
	'thumbnail': string;
	'tierPrice': TierPrices[];
	'type': string;
	'version': string;
	'versionDescription': string;
};

export const supportAndHelpMap = new Map<string, {icon: string; title: string}>(
	[
		[
			'supportemailaddress',
			{
				icon: 'envelope-open',
				title: 'Support Email',
			},
		],
		[
			'supportphone',
			{
				icon: 'phone',
				title: 'Support Phone',
			},
		],
		[
			'supporturl',
			{
				icon: 'link',
				title: 'Support URL',
			},
		],
		[
			'publisherwebsiteurl',
			{
				icon: 'globe',
				title: 'Publisher website URL',
			},
		],
		[
			'appusagetermsurl',
			{
				icon: 'document',
				title: 'App usage terms (EULA) URL',
			},
		],
		[
			'appdocumentationurl',
			{
				icon: 'document',
				title: 'App documentation URL',
			},
		],
		[
			'appinstallationguideurl',
			{
				icon: 'sites',
				title: 'App installation guide URL',
			},
		],
	]
);
