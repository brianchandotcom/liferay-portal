/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {useSelector} from '@xstate/store/react';

import infoCircleFullIcon from '../../../../../../assets/icons/icon_info_circle_full.svg';
import i18n from '../../../../../../i18n';
import {useProductPurchaseOutletContext} from '../../../../ProductPurchaseOutlet';
import {cartStore} from '../../../../store/CartStore';

import './index.scss';

const MAX_ITEM = 99;
const MIN_ITEM = 0;

const licenseTypeDescriptions = {
	developer:
		'Limited to 5 unique addresses and should not be used for full scale production deployments.',
	standard:
		'Covers the following DXP environments: production, non-production (UAT) and backup (DR) for both standalone and virtual cluster servers.',
};

type LicenseCardProps = {
	licenseType: string;
	licensetiers: {
		skuId: number;
		tierPrice: TierPrice[];
	}[];
	sku: DeliverySKU;
};

const tierPriceText = (
	tierPrices: TierPrice[],
	tierPrice: TierPrice,
	index: number
) => {
	const {priceFormatted, quantity} = tierPrice;

	const minPriceLicenseOption = index === tierPrices?.length - 1;

	const toLicenseQuantityValue = tierPrices[index + 1]?.quantity - 1;

	const quantityText = `${quantity}${`${
		minPriceLicenseOption ? '+ ' : `-${toLicenseQuantityValue}`
	}`} ${i18n.translate('licenses')}:`;

	const tierPriceValue = `${priceFormatted} ${i18n.translate('each')}`;

	return `${quantityText} ${tierPriceValue}`;
};

const LicenseCard: React.FC<LicenseCardProps> = ({
	licenseType = '',
	licensetiers,
	sku,
}) => {
	const {product, productPurchaseCart} = useProductPurchaseOutletContext();
	const cartItems = useSelector(cartStore, ({context}) => context.cartItems);

	const count =
		cartItems.find((item) => item.skuId === sku.id)?.quantity || MIN_ITEM;

	const licenseDescription =
		licenseTypeDescriptions[
			licenseType as keyof typeof licenseTypeDescriptions
		];

	const tierPrices = licensetiers[0]?.tierPrice ?? ([] as TierPrice[]);

	return (
		<div className="license__card mb-4 p-3">
			<div className="align-items-center d-flex justify-content-between w-100">
				<span>
					<span className="font-weight-bold text-capitalize">
						{`${licenseType} ${i18n.translate('license')}`}
					</span>

					<span className="license__card__icon ml-3">
						{licenseType.toLowerCase() === 'standard' ? (
							<img alt="Info" src={infoCircleFullIcon} />
						) : (
							<ClayIcon symbol="code" />
						)}
					</span>

					<p className="license__card__text mb-0 mt-2">
						{licenseDescription}
					</p>
				</span>

				<div className="align-items-center d-flex justify-content-between license__card__buttons__container p-1">
					<ClayButtonWithIcon
						aria-label="Remove from Cart"
						className="align-items-center d-flex justify-content-center license__card__buttons p-2"
						disabled={count === MIN_ITEM}
						displayType="primary"
						onClick={() =>
							productPurchaseCart.removeFromCart(sku.id)
						}
						symbol="hr"
					/>

					<span className="d-flex justify-content-center license__card__buttons__container__count">
						{count}
					</span>

					<ClayButtonWithIcon
						aria-label="Add To Cart"
						className="align-items-center d-flex justify-content-center license__card__buttons p-2"
						disabled={count === MAX_ITEM}
						displayType="primary"
						onClick={() =>
							productPurchaseCart.addCart(
								Number(product.id),
								sku.id
							)
						}
						symbol="plus"
					/>
				</div>
			</div>

			<div className="d-flex flex-column license__card__tier mt-4 p-4">
				<div className="font-weight-bold license__card__tier__title mb-1">
					{i18n.translate('license-prices')}
				</div>

				{tierPrices.length > 1 ? (
					tierPrices.map((tier: TierPrice, index: number) => (
						<span
							className="license__card__tier__price__text"
							key={index}
						>
							{tierPriceText(tierPrices, tier, index)}
						</span>
					))
				) : (
					<span className="license__card__tier__price__text">
						{`1 License: ${sku?.price?.priceFormatted}`}
					</span>
				)}
			</div>
		</div>
	);
};

export default LicenseCard;
