/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isTrialSKU} from '../../../../../utils/productUtils';
import {useProductPurchaseOutletContext} from '../../../ProductPurchaseOutlet';
import LicenseCard from './LicenseCard';

const PaidLicense = () => {
	const {product} = useProductPurchaseOutletContext();

	const purchasebleSkus = (product.skus || []).filter(
		(sku) =>
			sku?.price?.price &&
			sku.purchasable &&
			!isTrialSKU(sku as unknown as SKU)
	);

	return (
		<div className="paid-timeline">
			{purchasebleSkus.map((sku, index) => (
				<LicenseCard key={index} sku={sku} />
			))}
		</div>
	);
};

export default PaidLicense;
