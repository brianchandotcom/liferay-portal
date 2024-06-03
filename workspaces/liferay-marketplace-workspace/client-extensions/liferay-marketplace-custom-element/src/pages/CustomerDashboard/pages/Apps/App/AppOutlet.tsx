/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {
	NavLink,
	Outlet,
	useLocation,
	useNavigate,
	useParams,
} from 'react-router-dom';

import useGetProductByOrderId from '../../../../../hooks/useGetProductByOrderId';
import i18n from '../../../../../i18n';
import OrderDetailsHeader from '../../../components/OrderDetailsHeader';

import './App.scss';
import {PageRenderer} from '../../../../../components/Page';
import {useMarketplaceContext} from '../../../../../context/MarketplaceContext';
import {ORDER_WORKFLOW_STATUS_CODE} from '../../../../../enums/Order';
import {isTrialSKU} from '../../../../../utils/productUtils';
import getProductPriceModel from '../../../../GetApp/utils/getProductPriceModel';

type AppNavbarProps = {
	showDownloadTab: boolean;
	showLicenseTab: boolean;
};

const AppNavbar: React.FC<AppNavbarProps> = ({
	showDownloadTab,
	showLicenseTab,
}) => {
	const location = useLocation();
	const {properties} = useMarketplaceContext();

	const routeParams = location.pathname.split('/').filter(Boolean);

	return (
		<div className="navbar navbar-expand-md navbar-underline navigation-bar navigation-bar-light">
			<ul className="navbar-nav">
				<NavLink
					className={({isActive}) =>
						classNames('nav-link', {
							active: isActive && routeParams.length === 2,
						})
					}
					to=""
				>
					Details
				</NavLink>

				{properties.featureFlags?.includes('LPD-21582') &&
					showDownloadTab && (
						<NavLink
							className={({isActive}) =>
								classNames('nav-link', {
									active: isActive,
								})
							}
							to="download"
						>
							Download
						</NavLink>
					)}

				{showLicenseTab && (
					<NavLink
						className={({isActive}) =>
							classNames('nav-link', {
								active: isActive,
							})
						}
						to="licenses"
					>
						Licenses
					</NavLink>
				)}
			</ul>
		</div>
	);
};

const AppOutlet = () => {
	const {orderId} = useParams();
	const {data, error, isLoading} = useGetProductByOrderId(orderId as string);
	const product = data?.product;
	const {isFreeApp} = getProductPriceModel(product);
	const navigate = useNavigate();

	const placedOrderItems = data?.placedOrder.placedOrderItems ?? [];
	const productCreatorAccountName = data?.product?.catalogName || '';

	return (
		<PageRenderer error={error} isLoading={isLoading}>
			<div className="app-details-header d-flex flex-column w-100">
				<ClayButton
					className="align-items-center d-flex"
					displayType="unstyled"
					onClick={() => navigate('..')}
				>
					<ClayIcon className="mr-2" symbol="order-arrow-left" />
					<h5 className="mt-1">
						{i18n.translate('back-to-my-apps')}
					</h5>
				</ClayButton>

				<OrderDetailsHeader
					className="d-flex flex-row justify-content-between pb-3 pt-5"
					hasOrderDetails
					image={placedOrderItems[0]?.thumbnail}
					name={data?.product?.name}
					order={data?.placedOrder}
					productOwner={productCreatorAccountName}
				/>

				<AppNavbar
					showDownloadTab={
						data?.placedOrder.workflowStatusInfo.code ===
							ORDER_WORKFLOW_STATUS_CODE.COMPLETED &&
						placedOrderItems.some(
							(item: PlacedOrderItems) => item.virtualItems.length
						)
					}
					showLicenseTab={
						!(
							isFreeApp ||
							(placedOrderItems[0]?.price?.price === 0 &&
								product?.skus?.some((sku) =>
									isTrialSKU((sku as unknown) as SKU)
								))
						)
					}
				/>

				<Outlet context={data} />
			</div>
		</PageRenderer>
	);
};

export default AppOutlet;
