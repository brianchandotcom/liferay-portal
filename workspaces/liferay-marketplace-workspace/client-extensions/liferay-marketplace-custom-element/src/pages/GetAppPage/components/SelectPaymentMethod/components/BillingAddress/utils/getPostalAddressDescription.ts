/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function (address: BillingAddress) {
	const description = `${address.street1}, ${
		address.street2 ? address.street2 + ',' : ''
	} ${address.city}, ${address.regionISOCode}, ${address.countryISOCode} ${
		address.zip
	} `;

	return {
		description,
		title: address.name,
	};
}
