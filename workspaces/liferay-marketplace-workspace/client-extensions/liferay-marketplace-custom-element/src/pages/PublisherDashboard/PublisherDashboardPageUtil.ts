/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Liferay} from '../../liferay/liferay';

export function formatDate(date: string) {
	const locale = Liferay.ThemeDisplay.getLanguageId().replace('_', '-');

	const dateOptions: Intl.DateTimeFormatOptions = {
		day: 'numeric',
		month: 'short',
		year: 'numeric',
	};

	const formattedDate = new Intl.DateTimeFormat(locale, dateOptions).format(
		new Date(date)
	);

	return formattedDate;
}

export function getProductTypeFromSpecifications(
	specifications: ProductSpecification[]
) {
	let productType = 'no type';

	specifications.forEach((specification: ProductSpecification) => {
		if (specification.specificationKey === 'type') {
			productType = specification.value.en_US;

			if (productType === 'cloud') {
				productType = 'Cloud';
			}
			else if (productType === 'dxp') {
				productType = 'DXP';
			}
		}
	});

	return productType;
}
