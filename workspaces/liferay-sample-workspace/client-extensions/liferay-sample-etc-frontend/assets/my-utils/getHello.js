/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
export default function getHello(lang) {
	switch (lang) {
		case 'en':
			return 'Hello';
		case 'es':
			return 'Hola';
		case 'fr':
			return 'Bonjour';
		case 'it':
			return 'Ciao';
		case 'pt':
			return 'Olá';
		default:
			return '???';
	}
}
