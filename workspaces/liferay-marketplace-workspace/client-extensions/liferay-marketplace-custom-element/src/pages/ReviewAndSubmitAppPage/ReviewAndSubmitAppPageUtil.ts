/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
