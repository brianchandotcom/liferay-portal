/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	createProductSpecification,
	getProductSpecifications,
	updateProductSpecification,
} from '../../../utils/api';

export type BodyProductSpecificationProps = {
	productId: number;
	specificationKey: string;
	value: number | string;
};

const upsertProductSpecification = async (
	appProductId: number,
	productSpecification: BodyProductSpecificationProps
) => {
	const dataSpecificationList = await getProductSpecifications({
		appProductId,
	});

	const dataSpecification = dataSpecificationList?.find(
		(specification) =>
			specification?.specificationKey ===
			productSpecification.specificationKey
	);

	const fn = dataSpecification?.id
		? updateProductSpecification
		: createProductSpecification;

	return await fn({
		body: {
			specificationKey: productSpecification.specificationKey,
			value: {en_US: productSpecification.value},
		},
		id: dataSpecification?.id || appProductId,
	});
};

export default upsertProductSpecification;
