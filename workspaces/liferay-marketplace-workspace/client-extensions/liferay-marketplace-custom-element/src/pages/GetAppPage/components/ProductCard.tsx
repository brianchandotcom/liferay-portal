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
import {SkuOptions} from '../enums/skuOptions';
import {StepType} from '../enums/stepType';

interface ProductCardProps {
	cartinfo: any;
	productId: number | null;
	selectedAccount?: Account;
	setProductToForm: (product: Product) => void;
	step: StepType;
}

const ProductCard = ({
	cartinfo,
	productId,
	selectedAccount,
	setProductToForm,
	step,
}: ProductCardProps) => {
	const [product, setProduct] = useState<Product>();
	const [hasTrial, setHasTrial] = useState<boolean>(false);
	const [basePrice, setBasePrice] = useState<Number | undefined>(undefined);

	const totalFormatted = () => {
		if (step === StepType.LICENSES || step === StepType.PAYMENT) {
			return (
				<span className="paid-price-text">
					{cartinfo?.cart?.id
						? `${cartinfo?.cart?.summary?.totalFormatted}`
						: `$0`}
				</span>
			);
		}

		if (basePrice && hasTrial) {
			return <span>{`30-day trial or $${basePrice}`}</span>;
		}
	};

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

	const getProductBasePrice = async (product: Product) => {
		product &&
			product.skus.forEach((sku) => {
				const licenseUsageType = sku?.skuOptions.find((option) => {
					return (
						option?.key === 'standard' &&
						option?.value === 'yes' &&
						option?.key
					);
				});
				if (
					licenseUsageType &&
					licenseUsageType?.key.toLowerCase() ===
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
					nestedFields:
						'attachments,productSpecifications,skus,catalog',
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

	return (
		<>
			{product && (
				<div className="p-5 product-banner">
					<div className="d-flex flex-row justify-content-between">
						<div className="d-flex flex-row">
							<img
								alt=""
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
							<div className="mr-1 py-2">{totalFormatted()}</div>
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
