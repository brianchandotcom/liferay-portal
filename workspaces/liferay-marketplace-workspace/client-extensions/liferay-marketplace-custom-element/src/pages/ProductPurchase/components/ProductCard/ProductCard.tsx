/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactNode} from 'react';

import {normalizeURLProtocol} from '../../../../utils/string';
import {getValueFromDeliverySpecifications} from '../../../../utils/util';

import './ProductCard.scss';

type ProductCardProps = {
	ExtendBanner: React.ReactNode;
	RightSideBanner: React.ReactNode;
	creatorAccountName?: string;
	product: DeliveryProduct;
	showExtendBanner?: boolean;
};

type ProductCardPropsRevamp = {
	children?: ReactNode;
	icon: string;
	rightNode?: ReactNode;
	subtitle?: string | ReactNode;
	title: string;
};

const ProductCardRevamp = ({
	children,
	icon,
	rightNode,
	subtitle,
	title,
}: ProductCardPropsRevamp) => {
	const HeadingComponent = title.length > 30 ? 'h3' : 'h1';

	return (
		<div className="product-banner px-5 py-5">
			<div className="d-flex flex-row justify-content-between">
				<div className="d-flex flex-row">
					<img
						alt="App Icon"
						className="object-fit-cover rounded"
						draggable={false}
						height="64px"
						src={icon}
						width="64px"
					/>

					<div className="align-items-center ml-4">
						<HeadingComponent className="product-banner-title text-weight-bold">
							{title}
						</HeadingComponent>

						<span className="sub-text">{subtitle}</span>
					</div>
				</div>

				{rightNode}
			</div>

			{children}
		</div>
	);
};

const ProductCard = ({
	ExtendBanner,
	RightSideBanner,
	creatorAccountName,
	product,
	showExtendBanner = false,
}: ProductCardProps) => (
	<div className="pb-5 product-banner pt-5 px-5">
		<div className="d-flex flex-row justify-content-between">
			<div className="d-flex flex-row">
				<img
					alt="App Icon"
					className="object-fit-cover rounded"
					height="64px"
					src={normalizeURLProtocol(product.urlImage)}
					width="64px"
				/>

				<div className="align-items-center ml-4">
					<h1 className="product-banner-title text-weight-bold">
						{product?.name}
					</h1>

					<div className="sub-text">
						{getValueFromDeliverySpecifications(
							product?.productSpecifications,
							'latest-version'
						)}{' '}
						by {creatorAccountName}
					</div>
				</div>
			</div>

			{RightSideBanner}
		</div>

		{showExtendBanner && (
			<>
				<hr /> {ExtendBanner}
			</>
		)}
	</div>
);

export {ProductCardRevamp};

export default ProductCard;
