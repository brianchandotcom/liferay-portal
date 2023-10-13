/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import './ProductCard.scss';

import ClaySticker from '@clayui/sticker';
import {useEffect, useState} from 'react';

import emptyPictureIcon from '../../../assets/icons/avatar.svg';
import useCart from '../../../hooks/useCart';
import {getCustomFieldValue} from '../../../utils/customFieldUtil';
import {
	getThumbnailByProductAttachment,
	getValueFromSpecifications,
} from '../../../utils/util';
import {LicenseType} from '../enums/licenseType';
import {SkuOptions} from '../enums/skuOptions';
import {StepType} from '../enums/stepType';

interface ProductCardProps {
	cartUtil: ReturnType<typeof useCart>;
	product?: Product;
	selectedAccount?: Account;
	step: StepType;
}

const ProductCard = ({
	cartUtil,
	product,
	selectedAccount,
	step,
}: ProductCardProps) => {
	const [hasTrial, setHasTrial] = useState(false);
	const [basePrice, setBasePrice] = useState(0);

	const productHasTrialSKU = (skus: SKU[]) => {
		skus.forEach(async (sku) => {
			const licenseUsageType = sku?.skuOptions.find((option) => {
				return (
					option?.key === 'trial' &&
					option?.value === 'yes' &&
					option?.key
				);
			});
			if (
				licenseUsageType &&
				licenseUsageType?.key.toLowerCase() ===
					SkuOptions.TRIAL.toLowerCase()
			) {
				setHasTrial(true);
			}
		});
	};

	const getProductBasePrice = (product: Product) => {
		product?.skus?.forEach((sku) => {
			const licenseUsageType = sku?.skuOptions.find(
				(skuOption) =>
					skuOption?.key === 'standard' &&
					skuOption?.value === 'yes' &&
					skuOption?.key
			);

			if (
				licenseUsageType?.key.toLowerCase() ===
				SkuOptions.STANDARD.toLowerCase()
			) {
				setBasePrice(sku.price);
			}
		});
	};

	const FormattedValues = () => {
		if (step === StepType.LICENSES || step === StepType.PAYMENT) {
			return (
				<span className="price-text-value">
					{cartUtil?.cart?.id
						? `${cartUtil?.cart?.summary?.totalFormatted}`
						: `$0`}
				</span>
			);
		}

		if (basePrice) {
			if (hasTrial) {
				return <span>30-day trial or ${basePrice}</span>;
			}

			return <span>${basePrice}</span>;
		}

		return <span className="price-text-value">Free</span>;
	};

	const getIconUrl = () => {
		const iconURL = product
			? getThumbnailByProductAttachment(product.attachments)?.split('/o/')
			: '';

		return iconURL ? `/o/${iconURL[1]}` : '';
	};

	const getLicenseTagText = (product: Product) => {
		const licenseTypeSpecification = getValueFromSpecifications(
			product.productSpecifications,
			'license-type'
		).toLowerCase();

		if (licenseTypeSpecification) {
			return licenseTypeSpecification === LicenseType.Perpetual
				? 'One-Time'
				: 'Annually';
		}
	};

	useEffect(() => {
		if (product) {
			productHasTrialSKU(product.skus);
			getProductBasePrice(product);
		}
	}, [product]);

	if (!product) {
		return null;
	}

	return (
		<div className="p-5 product-banner">
			<div className="d-flex flex-row justify-content-between">
				<div className="d-flex flex-row">
					<img
						alt="App Icon"
						className="rounded"
						height="64px"
						src={getIconUrl()}
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
						<FormattedValues />
					</div>
					<div className="license-tag px-2">
						{getLicenseTagText(product)}
					</div>
				</div>
			</div>
			{selectedAccount && (
				<>
					<hr />

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
										selectedAccount?.logoURL ??
										emptyPictureIcon
									}
									width="24"
								/>
							</ClaySticker>
						</div>
					</div>
				</>
			)}
		</div>
	);
};
export default ProductCard;
