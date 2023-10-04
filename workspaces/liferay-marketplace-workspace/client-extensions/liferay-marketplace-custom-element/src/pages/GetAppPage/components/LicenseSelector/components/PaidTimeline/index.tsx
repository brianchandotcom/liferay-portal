/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { useEffect, useState } from "react";
import { UseFormGetValues, UseFormSetValue } from "react-hook-form";

import {
	getLicenseDescription,
	getTierPrice,
} from "../../../../../../utils/api";
import { getAppProps } from "../../../../GetAppPage";
import LicenseSectorCard from "../../LicenseCard";

interface PaidTimelineProps {
	cart: any;
	form: {
		getValues: UseFormGetValues<getAppProps>;
		setValue?: UseFormSetValue<getAppProps>;
	};
	product?: Product;
}

export function PaidTimeline({ cart, form, product }: PaidTimelineProps) {
	const [skuInfo, setSkuInfo] = useState<any>({});
	const [tierPrice, setTierPrice] = useState<any>([]);
	const productId = product?.id;

	useEffect(() => {
		(async () => {
			const catalogName = product?.catalog?.name;

			const tierpriceData = await getTierPrice(catalogName!);
			setTierPrice(tierpriceData);

			const skuDescription = await getLicenseDescription();
			setSkuInfo(skuDescription?.items[0]);
		})();
	}, [product?.catalog?.name]);

	const skus = product?.skus;

	const purchasebleSkus = skus?.filter((sku) =>
		sku?.skuOptions.find((option) =>
			option?.key !== "trial" && option?.value === "yes" ? true : false,
		),
	);

	return (
		<div className="paid-timeline">
			<div>
				<span>
					<p className="mt-3">Need help with license calculations?</p>
				</span>

				{purchasebleSkus?.map((sku: SKU, index) => {
					const tierPricesList = tierPrice?.filter(
						(tier: any) => tier?.tierPrice.length && tier.skuId === sku.id,
					);

					const licenseTypeName = sku.skuOptions.find(
						(optins) => optins.value === "yes",
					);

					return (
						<div className="mb-5" key={index}>
							<LicenseSectorCard
								cart={cart}
								licenseDescription={
									skuInfo[licenseTypeName?.key as keyof typeof skuInfo]
								}
								licensetiers={tierPricesList}
								lisenceType={licenseTypeName?.key}
								productId={productId}
								sku={sku}
							/>
						</div>
					);
				})}
			</div>
		</div>
	);
}
