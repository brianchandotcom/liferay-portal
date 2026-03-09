/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import DropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {ReactNode} from 'react';
import {Outlet, useOutletContext, useParams} from 'react-router-dom';

import BackLink from '../../../../../components/BackLink';
import Navbar, {NavbarProps} from '../../../../../components/Navbar';
import {PageRenderer} from '../../../../../components/Page';
import {MarketplaceDeliveryProduct} from '../../../../../entity/MarketplaceDeliveryProduct';
import {OrderTypes, OrderWorkflowStatusCode} from '../../../../../enums/Order';
import {ProductSupportSpecificationKey} from '../../../../../enums/Product';
import useGetProductByOrderId from '../../../../../hooks/useGetProductByOrderId';
import i18n from '../../../../../i18n';
import {getProductPriceModel} from '../../../../../utils/productUtils';
import OrderDetailsHeader from '../../../components/OrderDetailsHeader';
import AppDropdownActions from './AppDropdownActions/AppDropdownActions';

import './App.scss';

type ProductAndOrderPayload = NonNullable<
	ReturnType<typeof useGetProductByOrderId>['data']
>;

type BaseOutletProps = {
	actionButtons?: ReactNode | ((data: ProductAndOrderPayload) => ReactNode);
	backTitle: string;
	backURL?: string;
	description?: string;
	routes:
		| NavbarProps['routes']
		| ((data: ProductAndOrderPayload) => NavbarProps['routes']);
	showActions?: boolean;
};

const BaseOutlet: React.FC<BaseOutletProps> = ({
	actionButtons,
	backTitle,
	backURL = '..',
	description,
	routes,
	showActions = true,
}) => {
	const {orderId} = useParams();
	const outletContext = useOutletContext();
	const {data, error, isLoading} = useGetProductByOrderId(orderId as string);

	const placedOrderItems = data?.placedOrder.placedOrderItems ?? [];
	const productCreatorAccountName = data?.product?.catalogName || '';

	return (
		<PageRenderer
			className="app-details-header d-flex flex-column w-100"
			error={error}
			isLoading={isLoading}
		>
			<BackLink path={backURL}>{backTitle}</BackLink>

			<div className="d-flex justify-content-between">
				<div className="d-flex flex-column">
					<div className="d-flex justify-content-between">
						<OrderDetailsHeader
							className="d-flex flex-row justify-content-between pb-3 pt-5"
							hasOrderDetails
							image={placedOrderItems[0]?.thumbnail}
							name={placedOrderItems[0]?.name}
							order={data?.placedOrder}
							productOwner={productCreatorAccountName}
						/>

						{actionButtons && (
							<div id="solution-action-buttons">
								{typeof actionButtons === 'function'
									? actionButtons(
											data as ProductAndOrderPayload
										)
									: actionButtons}
							</div>
						)}
					</div>

					<p className="app-details-description">{description}</p>
				</div>

				{showActions && (
					<DropDown
						className="align-items-center cursor-pointer d-flex h-100"
						trigger={
							<ClayButton displayType="secondary">
								{i18n.translate('manage-app')}

								<ClayIcon
									className="ml-2"
									symbol="angle-down-small"
								/>
							</ClayButton>
						}
					>
						{data?.placedOrder && (
							<AppDropdownActions
								placedOrder={data.placedOrder}
							/>
						)}
					</DropDown>
				)}
			</div>

			<Navbar
				routes={
					typeof routes === 'function'
						? data
							? routes(data as ProductAndOrderPayload)
							: []
						: routes
				}
			/>

			<Outlet context={{...data, ...(outletContext || {})}} />
		</PageRenderer>
	);
};

const AppOutlet = () => (
	<BaseOutlet
		backTitle={i18n.translate('back-to-my-apps')}
		routes={({marketplaceDeliveryOrder, placedOrder, product}) => {
			const {isPaidApp} = getProductPriceModel(product);

			const marketplaceDeliveryProduct = new MarketplaceDeliveryProduct(
				product
			);

			const orderCompleted =
				placedOrder.orderStatusInfo.code ===
				OrderWorkflowStatusCode.COMPLETED;

			const isCompletedOrderWithVirtualItems =
				orderCompleted &&
				placedOrder.placedOrderItems.some(
					(item: PlacedOrderItems) => item.virtualItems?.length
				);

			const hasSupportDetails = product.productSpecifications.some(
				(specification: DeliveryProductSpecification) =>
					Object?.values(ProductSupportSpecificationKey).includes(
						specification.specificationKey as ProductSupportSpecificationKey
					)
			);

			const tabs = [
				{
					name: i18n.translate('details'),
					path: '',
				},
				{
					name: i18n.translate('download'),
					path: 'download',
					visible:
						isCompletedOrderWithVirtualItems &&
						(marketplaceDeliveryOrder.canDownload ||
							marketplaceDeliveryProduct.appSettings
								.isDownloadable),
				},
				{
					name: i18n.translate('app-provisioning'),
					path: 'cloud-provisioning',
					visible: [
						OrderTypes.CLIENT_EXTENSION,
						OrderTypes.CLOUD_APP,
					].includes(
						placedOrder.orderTypeExternalReferenceCode as OrderTypes
					),
				},
				{
					name: i18n.translate('licenses'),
					path: 'licenses',
					visible:
						isPaidApp &&
						orderCompleted &&
						[
							OrderTypes.CLIENT_EXTENSION,
							OrderTypes.DXP_APP,
						].includes(
							placedOrder.orderTypeExternalReferenceCode as OrderTypes
						),
				},
				{
					name: i18n.translate('support'),
					path: 'support',
					visible: hasSupportDetails,
				},
			];

			return tabs;
		}}
	/>
);

export {BaseOutlet};

export default AppOutlet;
