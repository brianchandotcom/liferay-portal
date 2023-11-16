/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';

import './index.scss';

import ClayButton from '@clayui/button';

import infoCircleFullIcon from '../../../../../assets/icons/icon_info_circle_full.svg';

const MAX_ITEM = 99;
const MIN_ITEM = 0;

const LicenseSectorCard: React.FC<any> = ({
	cartUtil,
	licenseDescription,
	licensetiers,
	lisenceType = '',
	productId,
	sku,
}) => {
	const count =
		cartUtil.cartItems.find((item: any) => item.skuId === sku.id)
			?.quantity || MIN_ITEM;

	const tiers = licensetiers[0];

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
					<ClayButton
						aria-label="Remove from Cart"
						className="align-items-center d-flex justify-content-center license__card__buttons p-2"
						disabled={count === MIN_ITEM}
						displayType="primary"
						onClick={() => cartUtil.removeFromCart(sku.id)}
					>
						<ClayIcon
							aria-label="Divider"
							className="license__card__buttons__icon"
							symbol="hr"
						/>
					</ClayButton>

					<span className="d-flex justify-content-center license__card__buttons__container__count">
						{count}
					</span>

					<ClayButton
						aria-label="Add To Cart"
						className="align-items-center d-flex justify-content-center license__card__buttons p-2"
						disabled={count === MAX_ITEM}
						displayType="primary"
						onClick={() => cartUtil.addCart(productId, sku.id)}
					>
						<ClayIcon
							aria-label="Plus Button"
							className="license__card__buttons__icon"
							symbol="plus"
						/>
					</ClayButton>
				</div>
			</div>

			{tiers?.tierPrice.length && (
				<div className="d-flex flex-column license__card__tier mt-4 p-4">
					<div className="font-weight-bold license__card__tier__title mb-1">
						License Prices
					</div>

					{(tiers?.tierPrice as any[])?.map((tier: any, index) => (
						<span
							className="license__card__tier__price__text"
							key={index}
						>
							{`${
								tier?.minimumQuantity || tier?.quantity
							} License: ${tier?.priceFormatted} each`}
						</span>
					))}
				</div>
			)}
		</div>
	);
};

export default LicenseSectorCard;
