/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SkuLicenseUsageType} from '../../../../../enums/Sku';
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
			{purchasebleSkus.map((sku, index) => {
				const skuOption = sku.skuOptions.find((skuOption) =>
					[
						SkuLicenseUsageType.CLOUD,
						SkuLicenseUsageType.DXP,
					].includes(skuOption.skuOptionKey as SkuLicenseUsageType)
				);

				return (
					<LicenseCard
						key={index}
						licenseType={
							skuOption?.skuOptionValueKey?.toLocaleLowerCase() as string
						}
						licensetiers={
							product.skus?.filter(
								({id, tierPrices}) =>
									!!tierPrices?.length && sku.id === id
							) as any
						}
						sku={sku}
					/>
				);
			})}
		</div>
	);
};

export default PaidLicense;
