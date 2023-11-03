/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

import useCart from '../../../../../../hooks/useCart';
import {getLicenseDescription, getTierPrice} from '../../../../../../utils/api';
import LicenseCard from '../../LicenseCard';

interface PaidTimelineProps {
	cartUtil: ReturnType<typeof useCart>;
	product?: Product;
}

export function PaidTimeline({cartUtil, product}: PaidTimelineProps) {
	const [skuInfo, setSkuInfo] = useState({});
	const [tierPrices, setTierPrices] = useState<any[]>([]);

	const {id: productId, skus} = product || {};

	useEffect(() => {
		(async () => {
			const catalogName = product?.catalog?.name;

			const [tierpriceData, skuDescription] = await Promise.all([
				getTierPrice(catalogName as string),
				getLicenseDescription(),
			]);

			setTierPrices(tierpriceData);
			setSkuInfo(skuDescription?.items[0]);
		})();
	}, [product?.catalog?.name]);

	const purchasebleSkus = skus?.filter((sku) =>
		sku?.skuOptions.find(
			(skuOption) => skuOption?.value.toLocaleLowerCase() !== 'trial'
		)
	);

	return (
		<div className="paid-timeline">
			<div>
				<p className="mt-3">Need help with license calculations?</p>

				{purchasebleSkus
					?.map((sku, index) => {
						const tierPricesFiltered = tierPrices?.filter(
							(tier: any) =>
								tier?.tierPrice.length && tier.skuId === sku.id
						);

						const licenseTypeName = sku.skuOptions.find(
							(skuOption) =>
								skuOption.value.toLocaleLowerCase() ===
								sku.sku.toLocaleLowerCase()
						);

						return (
							<div className="mb-5" key={index}>
								<LicenseCard
									cartUtil={cartUtil}
									licenseDescription={
										skuInfo[
											sku.sku.toLocaleLowerCase() as keyof typeof skuInfo
										]
									}
									licensetiers={tierPricesFiltered}
									lisenceType={licenseTypeName?.value}
									productId={productId}
									sku={sku}
								/>
							</div>
						);
					})
					.reverse()}
			</div>
		</div>
	);
}
