/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
export default function getBye(lang) {
	switch (lang) {
		case 'en':
			return 'Bye';
		case 'es':
			return 'Adiós';
		case 'fr':
			return 'Au revoir';
		case 'it':
			return 'Arrivederci';
		case 'pt':
			return 'Até logo';
		default:
			return '???';
	}
}
