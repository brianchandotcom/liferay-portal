/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import './ProductCard.scss';

import ClaySticker from '@clayui/sticker';
import {useEffect, useState} from 'react';

import emptyPictureIcon from '../../../../assets/icons/avatar.svg';
import useCart from '../../../../hooks/useCart';
import {
	getThumbnailByProductAttachment,
	getValueFromSpecifications,
} from '../../../../utils/util';
import {LicenseType} from '../../enums/licenseType';
import {SkuOptions} from '../../enums/skuOptions';
import {StepType} from '../../enums/stepType';

interface ProductCardProps {
	cartUtil: ReturnType<typeof useCart>;
	creatorAccount?: Account;

	isSelectSubscription?: boolean;

	product?: Product;
	selectedAccount?: any;
	step: StepType;
	userAccount?: UserAccount;
}

const ProductCard = ({
	cartUtil,
	creatorAccount,

	isSelectSubscription,

	product,
	selectedAccount,
	step,
	userAccount,
}: ProductCardProps) => {
	const [hasTrial, setHasTrial] = useState(false);
	const [basePrice, setBasePrice] = useState(0);

	const getProductBasePriceAndTrial = (skus: SKU[]) => {
		skus?.forEach((sku) => {
			const licenseUsageTypes = sku?.skuOptions.filter(
				(skuOption) =>
					skuOption?.value === SkuOptions.STANDARD.toLowerCase() ||
					skuOption?.value === SkuOptions.TRIAL.toLowerCase()
			);

			licenseUsageTypes.forEach((licenseUsageType) => {
				switch (licenseUsageType?.value.toLowerCase()) {
					case SkuOptions.STANDARD.toLowerCase():
						setBasePrice(sku.price);
						break;
					case SkuOptions.TRIAL.toLowerCase():
						setHasTrial(true);
						break;
					default:
						break;
				}
			});
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
			getProductBasePriceAndTrial(product.skus);
		}
	}, [product]);

	if (!product) {
		return null;
	}

	const AccountEmailInfo = () => (
		<div className="align-items-center d-flex">
			<div className="account-banner-name-text align-items-end d-flex flex-column m-2">
				<strong>{selectedAccount?.name}</strong>

				<div className="account-banner-email-text">
					{userAccount?.emailAddress}
				</div>
			</div>

			<ClaySticker shape="circle" size="sm">
				<ClaySticker.Image
					alt="placeholder"
					height="24"
					src={selectedAccount?.logoURL ?? emptyPictureIcon}
					width="24"
				/>
			</ClaySticker>
		</div>
	);

	const PriceTypeInfo = () => (
		<div className="align-items-end d-flex flex-column price-text">
			<strong className="mr-1">Price</strong>

			<div className="mr-1 py-2">
				<FormattedValues />
			</div>

			<div className="license-tag px-2">{getLicenseTagText(product)}</div>
		</div>
	);

	const ExtendBanner = () => (
		<>
			<hr />

			{!isSelectSubscription ? (
				<div className="d-flex flex-row justify-content-between">
					<strong className="account-banner-title-text align-self-center">
						Account Selected
					</strong>

					<AccountEmailInfo />
				</div>
			) : (
				<>
					<div className="align-items-center d-flex mb-3 row">
						<small className="col-6 col-md-4 font-weight-bold m-0">
							Key type
						</small>
						<small className="col-6 col-md-4 subscription-banner-text">
							Trial
						</small>
					</div>

					<div className="align-items-center d-flex row">
						<small className="col-6 col-md-4 font-weight-bold m-0">
							Start Date - Exp. Date
						</small>
						<small className="col-6 col-md-4 subscription-banner-text text-nowrap">
							Sep 24, 2023 &ndash; Oct 24, 2024
						</small>
					</div>
				</>
			)}
		</>
	);

	return (
		<div className="pb-3 product-banner pt-5 px-5">
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
							by {creatorAccount?.name}
						</div>
					</div>
				</div>

				{!isSelectSubscription ? (
					<PriceTypeInfo />
				) : (
					<AccountEmailInfo />
				)}
			</div>

			{selectedAccount && <ExtendBanner />}
		</div>
	);
};
export default ProductCard;
