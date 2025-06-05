/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import i18n from '../../../../../../i18n';

const getTierPriceText = (
	tierPrices: TierPrice[],
	tierPrice: TierPrice,
	index: number
): string => {
	const {priceFormatted, quantity} = tierPrice;

	const isLastTier = index === tierPrices.length - 1;
	const maxQuantity = tierPrices[index + 1]?.quantity - 1;

	let quantityRange = `${quantity}`;

	if (isLastTier) {
		quantityRange = `${quantity}+`;
	}
	else if (maxQuantity !== quantity) {
		quantityRange = `${quantity}-${maxQuantity}`;
	}

	const licensesText = i18n.translate(
		quantity === 1 ? 'license' : 'licenses'
	);

	return `${quantityRange} ${licensesText}: ${priceFormatted} ${i18n.translate('each')}`;
};

type LicenseTierProps = {
	sku: DeliverySKU;
};

const LicenseTier: React.FC<LicenseTierProps> = ({sku}) => {
	const tierPrices = sku.tierPrices ?? [];

	if (tierPrices.length === 1) {
		return (
			<span className="license__card__tier__price__text">
				{`1 License: ${sku?.price?.priceFormatted}`}
			</span>
		);
	}

	return (
		<>
			{tierPrices.map((tierPrice, index) => (
				<span className="license__card__tier__price__text" key={index}>
					{getTierPriceText(tierPrices, tierPrice, index)}
				</span>
			))}
		</>
	);
};

export default LicenseTier;
