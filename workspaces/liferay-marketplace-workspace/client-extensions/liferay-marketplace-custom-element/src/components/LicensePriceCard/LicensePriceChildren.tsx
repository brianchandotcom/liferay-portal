/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';

import {CurrencyAbbreviation} from '../../enums/CurrencyAbbreviation';
import {App} from '../../pages/PublisherDashboard/pages/Apps/AppCreationFlow/ReviewAndSubmitAppPage/ReviewAndSubmitAppPageUtil';
import {isTrialSKU} from '../../utils/productUtils';

import './LicensePriceChildren.scss';

export type TierPrices = {
	skuId: number;
	tierPrice: TierPrice[];
};

type LicensePriceChildrenType = {
	app: App;
	isCloud: boolean;
	tierPrices: TierPrices[];
};

const LicensePriceChildren = ({
	app,
	isCloud,
	tierPrices,
}: LicensePriceChildrenType) => {
	const {skus} = app;

	const productSkus =
		tierPrices
			.map((sku) => ({
				sku: skus.find(({id}) => id === sku.skuId) as SKU,
				tierPrices: sku.tierPrice,
			}))
			.filter((sku) => sku.tierPrices.length && !isTrialSKU(sku.sku)) ||
		[];

	return (
		<div className="align-items-start d-flex flex-column justify-content-between license-container text-nowrap">
			{productSkus.map(({sku, tierPrices}, index) => (
				<div className="align-items-baseline d-flex mt-4" key={index}>
					<span className="font-weight-bold license-type p-0 text-capitalize">
						{` ${
							isCloud ? 'Standard' : sku.skuOptions[0].value
						} Licenses`}
					</span>
					<div className="align-items-start d-flex flex-column">
						{tierPrices.map((tierPrice, indexTP) => {
							const {currency, priceFormatted, quantity} =
								tierPrice;

							const minPriceLicenseOption =
								indexTP === tierPrices?.length - 1;

							const toLicenseQuantityValue =
								tierPrices[indexTP + 1]?.quantity - 1;

							return (
								<div className="d-flex flex-row" key={indexTP}>
									<div className="license-tier-prices p-0">
										<span className="font-weight-bold text-muted">
											From
										</span>

										<span className="font-weight-bold mx-2">
											{quantity}
										</span>

										<span className="font-weight-bold text-muted">
											To
										</span>

										<span className="font-weight-bold mx-2">
											{minPriceLicenseOption ? (
												<span id="infinity-symbol">
													∞
												</span>
											) : (
												toLicenseQuantityValue
											)}
										</span>
									</div>

									<div className="align-items-end d-flex">
										<span>-</span>

										<div className="mx-2">
											<ClayIcon symbol="en-us" />
										</div>

										<span className="font-weight-bold">
											{currency === 'US Dollar'
												? CurrencyAbbreviation.USD
												: currency}
										</span>

										<span className="mx-2">
											{priceFormatted}
										</span>
									</div>
								</div>
							);
						})}
					</div>
				</div>
			))}
		</div>
	);
};

export default LicensePriceChildren;
