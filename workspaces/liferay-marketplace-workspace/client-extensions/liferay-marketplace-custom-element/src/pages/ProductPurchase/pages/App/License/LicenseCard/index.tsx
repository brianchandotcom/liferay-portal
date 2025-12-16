/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {useSelector} from '@xstate/store/react';

import ButtonWithIcon from '../../../../../../components/ButtonWithIcon';
import {ProductLicense} from '../../../../../../enums/Product';
import i18n from '../../../../../../i18n';
import {useProductPurchaseOutletContext} from '../../../../ProductPurchaseOutlet';
import {cartStore} from '../../../../store/CartStore';
import LicenseTier from './LicenseTier';

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
	sku: DeliverySKU;
};

const LicenseCard: React.FC<LicenseCardProps> = ({sku}) => {
	const {product, productPurchaseCart} = useProductPurchaseOutletContext();

	const cartItems = useSelector(cartStore, ({context}) => context.cartItems);

	const cartItemsCount =
		cartItems.find((item) => item.skuId === sku.id)?.quantity || MIN_ITEM;

	const skuOption = sku.skuOptions.find((skuOption) =>
		[
			ProductLicense.BASE,
			ProductLicense.CLOUD,
			ProductLicense.DXP,
		].includes(skuOption.skuOptionKey as ProductLicense)
	);

	const licenseType =
		skuOption?.skuOptionValueKey?.toLocaleLowerCase() as string;

	const licenseDescription =
		licenseTypeDescriptions[
			licenseType as keyof typeof licenseTypeDescriptions
		];

	return (
		<div className="license__card mb-4 p-3">
			<div className="align-items-center d-flex justify-content-between w-100">
				<span>
					<span className="font-weight-bold text-capitalize">
						{`${licenseType} ${i18n.translate('license')}`}
					</span>

					<span className="license__card__icon ml-3">
						<ClayIcon symbol="code" />
					</span>

					<p className="license__card__text mb-0 mt-2">
						{licenseDescription}
					</p>
				</span>

				<div className="align-items-center d-flex justify-content-between license__card__buttons__container p-1">
					<ButtonWithIcon
						aria-label="Remove from Cart"
						className="align-items-center d-flex justify-content-center license__card__buttons p-2"
						disabled={cartItemsCount === MIN_ITEM}
						displayType="primary"
						iconWithMargin={false}
						onClick={() =>
							productPurchaseCart.removeFromCart(sku.id)
						}
						symbol="hr"
					/>

					<span className="d-flex justify-content-center license__card__buttons__container__count">
						{cartItemsCount}
					</span>

					<ButtonWithIcon
						aria-label="Add To Cart"
						className="align-items-center d-flex justify-content-center license__card__buttons p-2"
						disabled={cartItemsCount === MAX_ITEM}
						displayType="primary"
						iconWithMargin={false}
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

				<LicenseTier sku={sku} />
			</div>
		</div>
	);
};

export default LicenseCard;
