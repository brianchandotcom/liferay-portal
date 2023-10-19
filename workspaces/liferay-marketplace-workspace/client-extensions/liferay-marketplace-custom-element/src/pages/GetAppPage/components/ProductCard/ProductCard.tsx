/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import './ProductCard.scss';
import {
	getThumbnailByProductAttachment,
	getValueFromSpecifications,
} from '../../../../utils/util';

interface ProductCardProps {
	ExtendBanner: React.FC;
	RightSideBanner: React.FC;
	creatorAccount?: Account;
	product?: Product;
	showExtendBanner?: boolean;
}

const ProductCard = ({
	ExtendBanner,
	RightSideBanner,
	creatorAccount,
	product,
	showExtendBanner = false,
}: ProductCardProps) => {
	const getIconUrl = () => {
		const iconURL = product
			? getThumbnailByProductAttachment(product.attachments)?.split('/o/')
			: '';

		return iconURL ? `/o/${iconURL[1]}` : '';
	};

	if (!product) {
		return null;
	}

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

				<RightSideBanner />
			</div>

			{showExtendBanner && (
				<>
					<hr /> <ExtendBanner />
				</>
			)}
		</div>
	);
};
export default ProductCard;
