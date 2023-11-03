/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {useMemo} from 'react';
import {NavLink, Outlet, useLocation, useParams} from 'react-router-dom';
import useSWR from 'swr';

import circleFullIcon from '../../../../assets/icons/circle_fill_icon.svg';
import i18n from '../../../../i18n';
import HeadlessCommerceAdminCatalogImpl from '../../../../services/rest/HeadlessCommerceAdminCatalog';
import {
	getProductVersionFromSpecifications,
	getThumbnailByProductAttachment,
	showAppImage,
} from '../../../../utils/util';

const AppNavbar = () => {
	const location = useLocation();

	const routeParams = location.pathname.split('/').filter(Boolean);

	return (
		<div className="mb-4 navbar navbar-expand-md navbar-underline navigation-bar navigation-bar-light">
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

const AppHeader = ({productSpecifications, selectedApp}: any) => {
	const appVersion = useMemo(
		() => getProductVersionFromSpecifications(productSpecifications as []),
		[productSpecifications]
	);

	const thumbnail = getThumbnailByProductAttachment(selectedApp?.attachments);

	return (
		<div className="d-flex justify-content-between my-4 w-100">
			<div className="d-flex">
				<img
					alt="App Logo"
					className="app-details-page-app-info-logo"
					src={showAppImage(thumbnail)}
				/>

				<div className="ml-2">
					<span className="app-details-page-app-info-title">
						{selectedApp.name?.en_US}
					</span>

					<div className="app-details-page-app-info-subtitle-container">
						<span className="app-details-page-app-info-subtitle-text">
							{appVersion}
						</span>

						<img
							alt="status icon"
							className={classNames(
								'app-details-page-app-info-subtitle-icon',
								{
									'app-details-page-app-info-subtitle-icon-hidden':
										selectedApp.status === 'Draft',
									'app-details-page-app-info-subtitle-icon-pending':
										selectedApp.status === 'Pending',
									'app-details-page-app-info-subtitle-icon-published':
										selectedApp.status === 'Approved',
								}
							)}
							src={circleFullIcon}
						/>

						<span className="app-details-page-app-info-subtitle-text">
							{selectedApp.status}
						</span>
					</div>
				</div>
			</div>

			<div>
				<ClayButton className="ml-4" displayType="secondary">
					Manage App
					<ClayIcon className="ml-2" symbol="caret-bottom-l" />
				</ClayButton>
			</div>
		</div>
	);
};

const AppOutlet = () => {
	const {appId: productId} = useParams();

	const {data = []} = useSWR(`/apps/app/${productId}`, () =>
		Promise.all([
			HeadlessCommerceAdminCatalogImpl.getProduct(productId as string),
			HeadlessCommerceAdminCatalogImpl.getProductSpecifications(
				productId as string
			),
		])
	);

	const [selectedApp, productSpecifications = []] = data ?? [];

	return (
		<div className="d-flex flex-column w-100">
			<NavLink className="font-weight-bold small text-dark" to="..">
				<ClayIcon className="mr-2" symbol="order-arrow-left" />
				{i18n.translate('back-to-my-apps')}
			</NavLink>

			<AppHeader
				productSpecifications={productSpecifications}
				selectedApp={selectedApp}
			/>
			<AppNavbar />

			<hr />

			<Outlet context={{productSpecifications, selectedApp}} />
		</div>
	);
};

export default AppOutlet;
