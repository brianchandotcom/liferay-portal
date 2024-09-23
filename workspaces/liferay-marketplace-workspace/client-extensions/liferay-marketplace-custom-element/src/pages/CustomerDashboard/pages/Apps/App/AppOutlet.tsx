/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {Link, Outlet, useNavigate, useParams} from 'react-router-dom';

import Navbar, {NavbarProps} from '../../../../../components/Navbar';
import {PageRenderer} from '../../../../../components/Page';
import {useMarketplaceContext} from '../../../../../context/MarketplaceContext';
import {ORDER_WORKFLOW_STATUS_CODE} from '../../../../../enums/Order';
import {ProductType} from '../../../../../enums/ProductType';
import useGetProductByOrderId from '../../../../../hooks/useGetProductByOrderId';
import i18n from '../../../../../i18n';
import getProductPriceModel from '../../../../GetApp/utils/getProductPriceModel';
import OrderDetailsHeader from '../../../components/OrderDetailsHeader';

import './App.scss';
import {getSpecificationByKey} from '../../../../../utils/productUtils';

type ProductAndOrderPayload = NonNullable<
	ReturnType<typeof useGetProductByOrderId>['data']
>;

type BaseOutletProps = {
	backTitle: string;
	backURL?: string;
	routes:
		| NavbarProps['routes']
		| ((data: ProductAndOrderPayload) => NavbarProps['routes']);
};

const BaseOutlet: React.FC<BaseOutletProps> = ({
	backTitle,
	backURL = '..',
	routes,
}) => {
	const navigate = useNavigate();
	const {orderId} = useParams();
	const {data, error, isLoading} = useGetProductByOrderId(orderId as string);

	const placedOrderItems = data?.placedOrder.placedOrderItems ?? [];
	const productCreatorAccountName = data?.product?.catalogName || '';

	return (
		<PageRenderer
			className="app-details-header d-flex flex-column w-100"
			error={error}
			isLoading={isLoading}
		>
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
				order={data?.placedOrder as unknown as Cart}
				productOwner={productCreatorAccountName}
			/>

			<Navbar
				routes={
					typeof routes === 'function'
						? data
							? routes(data as ProductAndOrderPayload)
							: []
						: routes
				}
			/>

			<Outlet context={data} />
		</PageRenderer>
	);
};

const AppOutlet = () => {
	const {properties} = useMarketplaceContext();

	return (
		<BaseOutlet
			backTitle={i18n.translate('back-to-my-apps')}
			routes={({placedOrder, product}) => {
				const {isPaidApp} = getProductPriceModel(product);

				const productType = getSpecificationByKey(
					'type',
					product
				)?.value?.toLowerCase();

				const isCompletedOrderWithVirtualItems =
					placedOrder.workflowStatusInfo.code ===
						ORDER_WORKFLOW_STATUS_CODE.COMPLETED &&
					placedOrder.placedOrderItems.some(
						(item: PlacedOrderItems) => item.virtualItems?.length
					);

				const tabs = [
					{
						name: i18n.translate('details'),
						path: '',
					},
				];

				if (productType === ProductType.CLOUD) {
					return [
						...tabs,
						{
							name: i18n.translate('app-provisioning'),
							path: 'cloud-provisioning',
							visible:
								properties.featureFlags.includes('LPD-34129'),
						},
					];
				}

				if (productType === ProductType.DXP) {
					return [
						...tabs,
						{
							name: i18n.translate('download'),
							path: 'download',
							visible:
								properties.featureFlags.includes('LPD-21582') &&
								isCompletedOrderWithVirtualItems,
						},
						{
							name: i18n.translate('licenses'),
							path: 'licenses',
							visible: isPaidApp,
						},
					];
				}

				return tabs;
			}}
		/>
	);
};

export {BaseOutlet};

export default AppOutlet;
