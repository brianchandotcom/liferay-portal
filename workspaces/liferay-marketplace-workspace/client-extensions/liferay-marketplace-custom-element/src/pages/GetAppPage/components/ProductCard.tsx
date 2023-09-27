/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

import './ProductCard.scss';

import ClaySticker from '@clayui/sticker';

import emptyPictureIcon from '../../../assets/icons/avatar.svg';
import {getProductById} from '../../../utils/api';
import {getCustomFieldValue} from '../../../utils/customFieldUtil';
import {
	getThumbnailByProductAttachment,
	getValueFromSpecifications,
} from '../../../utils/util';
import {LicenseType} from '../enums/licenseType';
import {Price} from '../enums/price';
import {SkuOptions} from '../enums/skuOptions';

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
	const [product, setProduct] = useState<Product>();
	const [hasTrial, setHasTrial] = useState<boolean>(false);
	const [basePrice, setBasePrice] = useState<Number | undefined>(undefined);

	const productHasTrialSKU = (skus: SKU[]) => {
		skus.forEach((sku) => {
			const licenseUsageType = sku.skuOptions.find(
				(option) =>
					option.key.toLowerCase() === 'dxp-license-usage-type'
			);
			if (
				licenseUsageType &&
				licenseUsageType.value.toLowerCase() ===
					SkuOptions.TRIAL.toLowerCase()
			) {
				setHasTrial(true);
			}
		});
	};

	const getProductBasePrice = (product: Product) => {
		product &&
			product.skus.forEach((sku) => {
				const licenseUsageType = sku.skuOptions.find(
					(option) =>
						option.key.toLowerCase() === 'dxp-license-usage-type'
				);
				if (
					licenseUsageType &&
					licenseUsageType.value.toLowerCase() ===
						SkuOptions.STANDARD.toLowerCase()
				) {
					setBasePrice(sku.price);
				}
			});
	};

	useEffect(() => {
		const fetchData = async () => {
			const productResponse =
				productId &&
				(await getProductById({
					nestedFields: 'attachments,productSpecifications,skus',
					productId,
				}));

			if (productResponse) {
				setProduct(productResponse);
				productHasTrialSKU(productResponse.skus);
				setProductToForm(productResponse);
				getProductBasePrice(productResponse);
			}
		};

		fetchData();
	}, [productId, setProductToForm]);

	const iconURL =
		product &&
		getThumbnailByProductAttachment(product.attachments)?.split('/o/');
	const convertedIconURL = iconURL ? `/o/${iconURL[1]}` : '';

	const getLicenseTagText = (product: Product) => {
		if (
			getValueFromSpecifications(
				product.productSpecifications,
				'license-type'
			).toLowerCase() === LicenseType.Perpetual
		) {
			return 'One-Time';
		}
		else if (
			getValueFromSpecifications(
				product.productSpecifications,
				'license-type'
			).toLowerCase() === LicenseType.Subscription
		) {
			return 'Annually';
		}

		return '';
	};

	const getPriceText = (product: Product) => {
		if (
			getValueFromSpecifications(
				product.productSpecifications,
				'price-model'
			).toLowerCase() === Price.PAID
		) {
			if (basePrice) {
				return hasTrial
					? `30-day trial or $${basePrice}`
					: `$${basePrice}`;
			}
		}
		else if (
			getValueFromSpecifications(
				product.productSpecifications,
				'price-model'
			).toLowerCase() === Price.FREE
		) {
			return 'Free';
		}
	};

	return (
		<>
			{product && (
				<div className="p-5 product-banner">
					<div className="d-flex flex-row justify-content-between">
						<div className="d-flex flex-row">
							<img
								height="64px"
								src={convertedIconURL}
								width="64px"
							/>
							<div className="align-items-center ml-4">
								<h1 className="text-weight-bold">
									{product.name.en_US}
								</h1>
								<div className="sub-text">
									{getValueFromSpecifications(
										product.productSpecifications,
										'latest-version'
									)}{' '}
									by{' '}
									{product.productSpecifications &&
										getValueFromSpecifications(
											product.productSpecifications,
											'developer-name'
										)}
								</div>
							</div>
						</div>
						<div className="align-items-end d-flex flex-column price-text">
							<strong className="mr-1">Price</strong>
							<div className="mr-1 py-2">
								{getPriceText(product)}
							</div>
							<div className="license-tag px-2">
								{getLicenseTagText(product)}
							</div>
						</div>
					</div>
					{selectedAccount && (
						<>
							<hr></hr>
							<div className="d-flex flex-row justify-content-between">
								<strong className="account-banner-title-text align-self-center">
									Account Selected
								</strong>
								<div className="align-items-center d-flex">
									<div className="account-banner-name-text align-items-end d-flex flex-column m-2">
										<strong>{selectedAccount?.name}</strong>
										<div className="account-banner-email-text">
											{selectedAccount?.customFields &&
												getCustomFieldValue(
													selectedAccount.customFields,
													'Contact Email'
												)}
										</div>
									</div>
									<ClaySticker shape="circle" size="sm">
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
