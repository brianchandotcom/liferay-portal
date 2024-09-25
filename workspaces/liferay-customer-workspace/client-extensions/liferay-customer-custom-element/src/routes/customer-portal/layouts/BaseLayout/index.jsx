/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useRef, useState} from 'react';
import {Outlet, useLocation, useParams} from 'react-router-dom';
import ProjectBreadcrumb from '../../components/ProjectBreadcrumb/ProjectBreadcrumb';
import ProjectErrorMessage from '../../components/ProjectErrorMessage';
import SideMenu from '../../containers/SideMenu';
import {useCustomerPortal} from '../../context';

const Layout = () => {
	const [{userProjectAccess}] = useCustomerPortal();

	const [hasSideMenu, setHasSideMenu] = useState(true);

	const {accountKey} = useParams();
	const firstAccountKeyRef = useRef(accountKey);

	const location = useLocation();
	const routeParams = location.pathname;

	const isRenewOrDeactivatePage =
		routeParams?.endsWith('dxp-renew') ||
		routeParams?.endsWith('portal-renew') ||
		routeParams?.endsWith('deactivate');

	useEffect(() => {
		if (accountKey !== firstAccountKeyRef.current) {
			window.location.reload();
		}
	}, [accountKey]);

	if (userProjectAccess) {
		if (
			userProjectAccess.denyAccess ||
			!userProjectAccess.hasProjectAccess
		) {
			return <ProjectErrorMessage />;
		}
	}

	return (
		<div className="d-flex position-relative w-100">
			{!isRenewOrDeactivatePage && (
				<div>
					<div className="align-items-center cp-layout-header d-flex justify-content-between ml-4 mt-4">
						<ProjectBreadcrumb />
					</div>

					{hasSideMenu && <SideMenu />}
				</div>
			)}

			<div className="mx-4 px-2 w-100">
				<div className="mx-4 px-2 w-100">
					<Outlet
						context={{
							setHasSideMenu,
						}}
					/>
				</div>
			</div>
		</div>
	);
};

export default Layout;
