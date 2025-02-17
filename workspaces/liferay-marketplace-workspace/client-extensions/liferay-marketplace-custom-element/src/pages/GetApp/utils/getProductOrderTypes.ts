/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function getProductOrderTypes(productSpecificationValue: any) {
	const productSpecification = productSpecificationValue.toLowerCase();

	if (productSpecification === 'client-extension') {
		return {externalReferenceCode: 'CLIENTEXTENSION'} as OrderType;
	}
	if (productSpecification === 'cloud') {
		return {externalReferenceCode: 'CLOUDAPP'} as OrderType;
	}
	if (productSpecification === 'dxp') {
		return {externalReferenceCode: 'DXPAPP'} as OrderType;
	}
	if (productSpecification === 'fragment') {
		return {externalReferenceCode: 'FRAGMENT'} as OrderType;
	}

	return {externalReferenceCode: 'NOTYPE'} as OrderType;
}
