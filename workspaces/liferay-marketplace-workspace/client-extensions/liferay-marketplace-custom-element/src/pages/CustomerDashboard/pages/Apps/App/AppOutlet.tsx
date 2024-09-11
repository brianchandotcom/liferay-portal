/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {Link, Outlet, useNavigate, useParams} from 'react-router-dom';

import useGetProductByOrderId from '../../../../../hooks/useGetProductByOrderId';
import i18n from '../../../../../i18n';
import OrderDetailsHeader from '../../../components/OrderDetailsHeader';

import './App.scss';
import Navbar, {NavbarProps} from '../../../../../components/Navbar';
import {PageRenderer} from '../../../../../components/Page';
import {useMarketplaceContext} from '../../../../../context/MarketplaceContext';
import {ORDER_WORKFLOW_STATUS_CODE} from '../../../../../enums/Order';
import {isTrialSKU} from '../../../../../utils/productUtils';
import getProductPriceModel from '../../../../GetApp/utils/getProductPriceModel';

type BaseOutletProps = {
	backTitle: string;
	backURL?: string;
	routes: NavbarProps['routes'] | ((data: any) => NavbarProps['routes']);
};

const CLOUD_APP = 'cloud';

const BaseOutlet: React.FC<BaseOutletProps> = ({
	backTitle,
	backURL = '..',
	routes,
}) => {
	const {orderId} = useParams();
	const {data, error, isLoading} = useGetProductByOrderId(orderId as string);
	const product = data?.product;

	const navigate = useNavigate();

	const placedOrderItems = data?.placedOrder.placedOrderItems ?? [];
	const productCreatorAccountName = data?.product?.catalogName || '';

	return (
		<PageRenderer error={error} isLoading={isLoading}>
			<div className="app-details-header d-flex flex-column w-100">
				<Link
					className="align-items-center d-flex text-dark"
					onClick={() => navigate('..')}
					to={backURL}
				>
					<ClayIcon className="mr-2" symbol="order-arrow-left" />

					<span className="h5 mt-1">{backTitle}</span>
				</Link>

				<OrderDetailsHeader
					className="d-flex flex-row justify-content-between pb-3 pt-5"
					hasOrderDetails
					image={placedOrderItems[0]?.thumbnail}
					name={data?.product?.name}
					order={data?.placedOrder}
					productOwner={productCreatorAccountName}
				/>

				<Navbar
					routes={
						typeof routes === 'function'
							? routes({data, placedOrderItems, product})
							: routes
					}
				/>

				<Outlet context={data} />
			</div>
		</PageRenderer>
	);
};

const AppOutlet = () => {
	const {properties} = useMarketplaceContext();

	return (
		<BaseOutlet
			backTitle={i18n.translate('back-to-my-apps')}
			routes={({data, placedOrderItems, product}: any) => {
				function verifyProductType(product: any) {
					const specification = product?.productSpecifications?.find(
						(speficication: ProductSpecification) =>
							speficication?.specificationKey === 'type'
					);

					const value = specification?.value;

					return value ? value : '';
				}

				const tabs = [
					{
						name: i18n.translate('details'),
						path: '',
					},
					{
						name: i18n.translate('download'),
						path: 'download',
						visible:
							properties.featureFlags?.includes('LPD-34129') &&
							verifyProductType(product) !== CLOUD_APP &&
							properties.featureFlags?.includes('LPD-21582') &&
							data?.placedOrder.workflowStatusInfo.code ===
								ORDER_WORKFLOW_STATUS_CODE.COMPLETED &&
							placedOrderItems.some(
								(item: PlacedOrderItems) =>
									item.virtualItems?.length
							),
					},
					{
						name: i18n.translate('licenses'),
						path: 'licenses',
						visible:
							properties.featureFlags?.includes('LPD-34129') &&
							verifyProductType(product) !== CLOUD_APP &&
							!(
								getProductPriceModel(product).isFreeApp ||
								(placedOrderItems[0]?.price?.price === 0 &&
									product?.skus?.some((sku: any) =>
										isTrialSKU(sku as unknown as SKU)
									))
							),
					},
					{
						name: i18n.translate('app-provisioning'),
						path: 'cloud-provisioning',
						visible:
							properties.featureFlags?.includes('LPD-34129') &&
							verifyProductType(product) === CLOUD_APP,
					},
				];

				return tabs;
			}}
		/>
	);
};

export {BaseOutlet};

export default AppOutlet;
