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

import useGetProductByOrderId from '../../../../hooks/useGetProductByOrderId';
import i18n from '../../../../i18n';
import {getThumbnailByProductAttachment} from '../../../../utils/util';
import useGetProductCreatorAccount from '../../../GetAppPage/hooks/useGetProductCreatorAccount';
import OrderDetailsHeader from '../components/OrderDetailsHeader';

import './App.scss';

import ClayLoadingIndicator from '@clayui/loading-indicator';

const AppNavbar = () => {
	const location = useLocation();

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
			</ul>
		</div>
	);
};

const AppOutlet = () => {
	const navigate = useNavigate();

	const {orderId} = useParams();

	const {data, error, isLoading} = useGetProductByOrderId(orderId as string);

	const appImage = getThumbnailByProductAttachment(
		data?.product?.attachments
	);

	const productCreatorAccount = useGetProductCreatorAccount(data?.product);

	if (isLoading) {
		return <ClayLoadingIndicator />;
	}

	if (error) {
		return <div>Error: {error.message}</div>;
	}

	return (
		<div className="app-details-header d-flex flex-column w-100">
			<ClayButton
				className="align-items-center d-flex"
				displayType="unstyled"
				onClick={() => navigate('..')}
			>
				<ClayIcon className="mr-2" symbol="order-arrow-left" />
				<h5 className="mt-1">{i18n.translate('back-to-my-apps')}</h5>
			</ClayButton>

			<OrderDetailsHeader
				className="d-flex flex-row justify-content-between pb-3 pt-5"
				hasOrderDetails
				image={appImage}
				name={data?.product?.name?.en_US}
				order={data?.placedOrder}
				productOwner={productCreatorAccount?.name}
			/>

			<AppNavbar />

			<Outlet context={data} />
		</div>
	);
};

export default AppOutlet;
