/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

import {useMarketplaceContext} from '../../../../context/MarketplaceContext';
import useCart from '../../../../hooks/useCart';
import {Liferay} from '../../../../liferay/liferay';
import {getLicenseDescription, getTierPrice} from '../../../../utils/api';
import {isCloudProduct, isTrialSKU} from '../../../../utils/productUtils';
import LicenseCard from './LicenseCard';

type PaidTimelineProps = {
	cartUtil: ReturnType<typeof useCart>;
	product?: DeliveryProduct;
};

export function PaidTimeline({cartUtil, product}: PaidTimelineProps) {
	const {channel} = useMarketplaceContext();
	const [skuInfo, setSkuInfo] = useState({});
	const [tierPrices, setTierPrices] = useState<
		{skuId: number; tierPrice: TierPrice[]}[]
	>([]);

	const {id: productId, skus} = product || {};
	const accountId = Liferay.CommerceContext.account?.accountId;

	useEffect(() => {
		(async () => {
			const [tierpriceData, skuDescription] = await Promise.all([
				getTierPrice(channel.id, product?.productId, Number(accountId)),
				getLicenseDescription(),
			]);

			setTierPrices(tierpriceData);
			setSkuInfo(skuDescription?.items[0] || {});
		})();
	}, [accountId, channel.id, product?.productId]);

	const purchasebleSkus = (skus || []).filter((sku) => {
		return (
			sku?.price?.price &&
			sku.purchasable &&
			!isTrialSKU(sku as unknown as SKU)
		);
	});

	return (
		<div className="paid-timeline">
			<div>
				<p className="mt-3">Need help with license calculations?</p>

				{purchasebleSkus
					.map((sku, index) => {
						const tierPricesFiltered = tierPrices?.filter(
							({skuId, tierPrice}) =>
								!!tierPrice.length && skuId === sku.id
						);

						const skuOption = sku.skuOptions.find(
							(skuOption) =>
								skuOption.skuOptionKey ===
								'dxp-license-usage-type'
						);

						return (
							<div className="mb-5" key={index}>
								<LicenseCard
									cartUtil={cartUtil}
									licenseDescription={
										skuInfo[
											skuOption?.skuOptionValueKey?.toLocaleLowerCase() as keyof typeof skuInfo
										]
									}
									licensetiers={tierPricesFiltered}
									lisenceType={
										isCloudProduct(product)
											? 'Standard'
											: skuOption?.skuOptionValueKey ??
												sku.sku
									}
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
