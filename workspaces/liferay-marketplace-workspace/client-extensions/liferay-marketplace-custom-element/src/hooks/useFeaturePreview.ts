/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMarketplaceContext} from '../context/MarketplaceContext';

type ID = number | string;

const useFeaturePreview = () => {
	const {properties} = useMarketplaceContext();

	/**
	 *
	 * @description due a breaking change on commerce product specification API
	 * starting on > U112 the API expects to receive productId instead of appId
	 * remove this helper after Marketplace UAT/PRD get upgraded to > U112
	 */

	const getTemporaryProductIdForSpefication = ({
		appId,
		productId,
	}: {
		appId: ID;
		productId: ID;
	}) => {
		if (
			properties.featurePreviews.includes(
				'use-product-id-for-specification'
			)
		) {
			return productId;
		}

		return appId;
	};

	return {getTemporaryProductIdForSpefication};
};

export default useFeaturePreview;
