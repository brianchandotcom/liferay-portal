/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLabel from '@clayui/label';
import {useEffect, useState} from 'react';

import './ProductCard.scss';

import ClaySticker from '@clayui/sticker';

import emptyPictureIcon from '../../../assets/icons/avatar.svg';
import {getProductById} from '../../../utils/api';
import {getCustomFieldValue} from '../../../utils/customFieldUtil';
import {getValueFromSpecifications} from '../../../utils/util';
import {LicenseType} from '../enums/licenseType';

interface ProductCardProps {
	productId: number | null;
	selectedAccount?: Account;
	setProductToForm: (product: Product) => void;
}

const ProductCard = ({
	productId,
	selectedAccount,
	setProductToForm,
}: ProductCardProps) => {
	const [product, setProduct] = useState<Product[]>([]);

	useEffect(() => {
		const getProdut = async () => {
			// eslint-disable-next-line promise/catch-or-return
			{
				productId &&
					getProductById({
						nestedFields: 'productSpecifications,skus',
						productId,
					}).then((item: Product) => {
						setProduct([item]);
					});
			}
		};
		getProdut();
	}, [productId]);

	setProductToForm(product[0]);

	const currentValue = product[0];

	const getLicenseTagText = (product: Product) => {
		if (
			getValueFromSpecifications(
				product.productSpecifications,
				'License Type'
			).toLowerCase === LicenseType.Perpetual.toLowerCase
		) {
			return 'One-Time';
		}
		else if (
			getValueFromSpecifications(
				product.productSpecifications,
				'License Type'
			).toLowerCase === LicenseType.Subscription.toLowerCase
		) {
			return 'Annually';
		}

		return '';
	};

	return (
		<>
			{currentValue && (
				<div className="p-5 product-banner">
					<div className="d-flex flex-row justify-content-between">
						<div className="d-flex flex-row">
							<img
								height="64px"
								src={currentValue?.thumbnail}
								width="64px"
							/>
							<div className="align-items-center ml-4">
								<h1 className="text-weight-bold">
									{currentValue.name.en_US}
								</h1>
								<div className="sub-text">
									{getValueFromSpecifications(
										currentValue.productSpecifications,
										'latest-version'
									)}{' '}
									by{' '}
									{currentValue.customFields &&
										getCustomFieldValue(
											currentValue.customFields,
											'Developer Name'
										)}
								</div>
							</div>
						</div>
						<div className="align-items-end d-flex flex-column price-text">
							<strong>Price</strong>
							<div>Textinho com o preco</div>
							<ClayLabel displayType="secondary">
								{getLicenseTagText(currentValue)}
							</ClayLabel>
						</div>
					</div>
					{selectedAccount && (
						<>
							<hr></hr>
							<div className="account-banner d-flex flex-row justify-content-between px-4 py-3">
								<strong className="align-self-center sub-text">
									Account Selected
								</strong>
								<div className="align-items-center d-flex">
									<div className="align-items-end d-flex flex-column m-2">
										<strong>{selectedAccount?.name}</strong>
										<div className="sub-text">
											{selectedAccount?.customFields &&
												getCustomFieldValue(
													selectedAccount.customFields,
													'Contact Email'
												)}
										</div>
									</div>
									<ClaySticker shape="circle" size="lg">
										<ClaySticker.Image
											alt="placeholder"
											height="24"
											src={
												selectedAccount &&
												(selectedAccount?.logoURL ??
													emptyPictureIcon)
											}
											width="24"
										></ClaySticker.Image>
									</ClaySticker>
								</div>
							</div>
						</>
					)}
				</div>
			)}
		</>
	);
};
export default ProductCard;
