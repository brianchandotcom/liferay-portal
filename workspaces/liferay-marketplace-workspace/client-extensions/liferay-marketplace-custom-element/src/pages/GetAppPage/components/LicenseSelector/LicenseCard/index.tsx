/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';

import './index.scss';

import ClayButton from '@clayui/button';

import infoCircleFullIcon from '../../../../../assets/icons/icon_info_circle_full.svg';

const maxItem = 99;
const minItem = 0;

const LicenseSectorCard: React.FC<any> = ({
	cart,
	licenseDescription,
	licensetiers,
	lisenceType,
	productId,
	sku,
}) => {
	const count =
		cart.cartItems.find((item: any) => item.skuId === sku.id)?.quantity ||
		minItem;

	const tiers = licensetiers[0];

	const tierPrices = (tiers: any) => {
		return tiers?.tierPrice?.map((tier: any, index: number) => {
			return (
				<span className="license__card__tier__price__text" key={index}>
					{`${tier?.minimumQuantity} License: ${tier?.priceFormatted} each`}
				</span>
			);
		});
	};

	return (
		<div className="license__card p-3">
			<div className="align-items-center d-flex justify-content-between w-100">
				<span>
					<div className="mb-1">
						<span className="font-weight-bold text-capitalize">
							{`${lisenceType} License`}
						</span>
						<span className="license__card__icon ml-3">
							{lisenceType.toLowerCase() === 'standard' ? (
								<img alt="Info" src={infoCircleFullIcon} />
							) : (
								<ClayIcon symbol="code" />
							)}
						</span>
					</div>
					<div>
						<p className="license__card__text mb-0">
							{licenseDescription}
						</p>
					</div>
				</span>
				<div className="align-items-center d-flex justify-content-between license__card__buttons__container p-1">
					<span>
						<ClayButton
							aria-label=""
							className="align-items-center d-flex justify-content-center license__card__buttons p-2"
							disabled={count === minItem}
							displayType="primary"
							onClick={() => cart.removeFromCart(sku.id)}
						>
							<ClayIcon
								aria-label="123"
								className="license__card__buttons__icon"
								symbol="hr"
							/>
						</ClayButton>
					</span>
					<span className="d-flex justify-content-center license__card__buttons__container__conut">
						{count.toString()}
					</span>
					<span>
						<ClayButton
							aria-label=""
							className="align-items-center d-flex justify-content-center license__card__buttons p-2"
							disabled={count === maxItem}
							displayType="primary"
							onClick={() => {
								cart.addCartItem(productId, sku.id);
							}}
						>
							<ClayIcon
								aria-label="123"
								className="license__card__buttons__icon"
								symbol="plus"
							/>
						</ClayButton>
					</span>
				</div>
			</div>

			<div className="d-flex flex-column license__card__tier mt-4 p-4">
				<div className="font-weight-bold license__card__tier__title mb-1">
					License Prices
				</div>
				{tierPrices(tiers)}
			</div>
		</div>
	);
};

export default LicenseSectorCard;
