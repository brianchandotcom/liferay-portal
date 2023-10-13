/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

import useCart from '../../../../../../hooks/useCart';
import {getLicenseDescription, getTierPrice} from '../../../../../../utils/api';
import LicenseSectorCard from '../../LicenseCard';

interface PaidTimelineProps {
	cartUtil: ReturnType<typeof useCart>;
	product?: Product;
}

export function PaidTimeline({cartUtil, product}: PaidTimelineProps) {
	const [skuInfo, setSkuInfo] = useState<any>({});
	const [tierPrice, setTierPrice] = useState<any>([]);
	const productId = product?.id;

	useEffect(() => {
		(async () => {
			const catalogName = product?.catalog?.name;

			const [tierpriceData, skuDescription] = await Promise.all([
				getTierPrice(catalogName as string),
				getLicenseDescription(),
			]);

			setTierPrice(tierpriceData);
			setSkuInfo(skuDescription?.items[0]);
		})();
	}, [product?.catalog?.name]);

	const skus = product?.skus;

	const purchasebleSkus = skus?.filter((sku) =>
		sku?.skuOptions.find(
			(skuOption) => skuOption?.value.toLocaleLowerCase() !== 'trial'
		)
	);

	return (
		<div className="paid-timeline">
			<div>
				<span>
					<p className="mt-3">Need help with license calculations?</p>
				</span>

				{purchasebleSkus
					?.map((sku: SKU, index) => {
						const tierPricesList = tierPrice?.filter(
							(tier: any) =>
								tier?.tierPrice.length && tier.skuId === sku.id
						);

						const licenseTypeName = sku.skuOptions.find(
							(optins) =>
								optins.value.toLocaleLowerCase() ===
								sku.sku.toLocaleLowerCase()
						);

						return (
							<div className="mb-5" key={index}>
								<LicenseSectorCard
									cartUtil={cartUtil}
									licenseDescription={
										skuInfo[
											sku.sku.toLocaleLowerCase() as keyof typeof skuInfo
										]
									}
									licensetiers={tierPricesList}
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
