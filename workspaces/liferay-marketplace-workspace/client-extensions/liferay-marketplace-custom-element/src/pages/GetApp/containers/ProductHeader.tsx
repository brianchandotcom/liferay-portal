/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMarketplaceContext} from '../../../context/MarketplaceContext';
import {getValueFromDeliverySpecifications} from '../../../utils/util';
import AccountEmailInfo from '../../CustomerDashboard/pages/Apps/App/Licenses/CreateLicense/AccountInfo';
import {useGetAppContext} from '../GetAppContextProvider';
import {getProductBasePriceAndTrial} from '../GetAppOutlet';
import {ProductCardRevamp} from '../components/ProductCard/ProductCard';
import {LicenseType} from '../enums/licenseType';
import {getIconUrl} from '../utils/getIcon';
import ProductHeaderPrice from './ProductHeaderPrice';

const getLicenseTagText = (product: DeliveryProduct) => {
	const licenseTypeSpecification = getValueFromDeliverySpecifications(
		product.productSpecifications,
		'license-type'
	).toLowerCase();

	if (licenseTypeSpecification) {
		return licenseTypeSpecification === LicenseType.Perpetual
			? 'One-Time'
			: 'Annually';
	}
};

type ProductHeaderProps = {
	productBasePriceAndTrial: ReturnType<typeof getProductBasePriceAndTrial>;
};

const ProductHeader: React.FC<ProductHeaderProps> = ({
	productBasePriceAndTrial,
}) => {
	const [{account, product}] = useGetAppContext();
	const {myUserAccount} = useMarketplaceContext();

	const productCreatorAccountName = product?.catalogName || '';

	const latestVersion = getValueFromDeliverySpecifications(
		product?.productSpecifications,
		'latest-version'
	);

	return (
		<ProductCardRevamp
			icon={getIconUrl(product)}
			rightNode={
				<div className="align-items-end d-flex flex-column price-text">
					<strong className="mr-1">Price</strong>

					<div className="mr-1 py-2">
						<ProductHeaderPrice
							productBasePriceAndTrial={productBasePriceAndTrial}
						/>
					</div>

					{!!productBasePriceAndTrial.basePrice && (
						<div className="license-tag px-2">
							{getLicenseTagText(product as DeliveryProduct)}
						</div>
					)}
				</div>
			}
			subtitle={
				latestVersion
					? `${latestVersion} by ${productCreatorAccountName} `
					: productCreatorAccountName
			}
			title={product.name}
		>
			{account && (
				<>
					<hr />

					<div className="d-flex flex-row justify-content-between">
						<strong className="account-banner-title-text align-self-center">
							Account Selected
						</strong>

						<AccountEmailInfo
							userAccount={{
								...myUserAccount,
								...account,
								image: account.logoURL,
							}}
						/>
					</div>
				</>
			)}
		</ProductCardRevamp>
	);
};

export default ProductHeader;
