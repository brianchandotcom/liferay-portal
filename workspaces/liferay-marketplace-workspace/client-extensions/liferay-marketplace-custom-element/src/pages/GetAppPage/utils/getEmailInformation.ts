/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function getEmailInformation(
	dashboardURL: string,
	orderID: number,
	productType: string,
	priceModel?: string,
	productName?: string
) {
	const emailAppInformation: EmailAppInformation = {
		dashboardLink: dashboardURL,
		orderID,
		priceModel,
		productName,
		productType,
	};

	return emailAppInformation;
}
