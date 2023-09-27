/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import documentationIcon from '../../assets/icons/documentation_icon.svg';
import globeIcon from '../../assets/icons/globe_icon.svg';
import guideIcon from '../../assets/icons/guide_icon.svg';
import phoneIcon from '../../assets/icons/phone_icon.svg';
import usageTermsIcon from '../../assets/icons/usage_terms_icon.svg';

export type App = {
	attachmentTitle: string;
	categories: string[];
	description: string;
	licenseType: string;
	name: string;
	price: number;
	priceModel: string;
	storefront: ProductImages[];
	supportAndHelp: {
		icon: string;
		link: string;
		title: string;
	}[];
	tags: string[];
	thumbnail: string;
	version: string;
	versionDescription: string;
};

export const supportAndHelpMap = new Map<string, {icon: string; title: string}>(
	[
		[
			'supporturl',
			{
				icon: phoneIcon,
				title: 'Support URL',
			},
		],
		[
			'publisherwebsiteurl',
			{
				icon: globeIcon,
				title: 'Publisher website URL',
			},
		],
		[
			'appusagetermsurl',
			{
				icon: usageTermsIcon,
				title: 'App usage terms (EULA) URL',
			},
		],
		[
			'appdocumentationurl',
			{
				icon: documentationIcon,
				title: 'App documentation URL',
			},
		],
		[
			'appinstallationguideurl',
			{
				icon: guideIcon,
				title: 'App installation guide URL',
			},
		],
	]
);
