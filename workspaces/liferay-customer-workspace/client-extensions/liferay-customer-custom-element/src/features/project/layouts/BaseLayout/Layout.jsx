/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useRef, useState} from 'react';
import {Outlet, useLocation, useParams} from 'react-router-dom';
import ProjectBreadcrumb from '../../components/ProjectBreadcrumb/ProjectBreadcrumb';
import ProjectErrorMessage from '../../components/ProjectErrorMessage';
import SideMenu from '../../containers/SideMenu';
import InformationBanner from '~/components/InformationBanner';
import {useAppPropertiesContext} from '~/contexts/AppPropertiesContext';
import {useCustomerPortal} from '~/features/project/context';
import i18n from '~/utils/I18n';

import './Layout.css';

const Layout = () => {
	const {featureFlags} = useAppPropertiesContext();
	const [{subscriptions, userProjectAccess}] = useCustomerPortal();

	const [hasSideMenu, setHasSideMenu] = useState(true);
	const [showBanner, setShowBanner] = useState(true);

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

	const hasBusinessEnterpriseOrProSubscription = subscriptions?.some(
		(subscription) =>
			subscription.accountSubscriptionGroupERC?.includes('saas') &&
			(subscription.name?.includes('Business Plan') ||
			subscription.name?.includes('Enterprise Plan') ||
			subscription.name?.includes('Pro Plan'))
	);

	useEffect(() => {
		const bannerState = !sessionStorage.getItem('@liferayCP:showSaaSProjectBanner');

		setShowBanner(bannerState);
	}, []);

	const handleBannerDismiss = () => {
		sessionStorage.setItem('@liferayCP:showSaaSProjectBanner', 'false');

		setShowBanner(false);
	};

	if (userProjectAccess) {
		if (
			userProjectAccess.denyAccess ||
			!userProjectAccess.hasProjectAccess
		) {
			return <ProjectErrorMessage />;
		}
	}

	return (
		<div className="position-relative w-100">
			{showBanner && featureFlags.includes('LRSD-8459') &&
				hasBusinessEnterpriseOrProSubscription && (
					<InformationBanner
						content={i18n.sub(
							'visit-the-new-project-usage-page-to-see-your-project-consumption-for-liferay-saas-for-more-information-please-feel-free-to-visit-this-page',
							[
								`<a href="${Liferay.currentURL}#/${accountKey}/project-usage">`,
								'</a>',
								'<a href="https://help.liferay.com/hc/articles/13068602483853-Liferay-SaaS-Plans">',
								'</a>',
							]
						)}
						icon="exclamation-circle"
						onDismiss={handleBannerDismiss}
					/>
				)}

			<div className="d-flex">
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
		</div>
	);
};

export default Layout;
