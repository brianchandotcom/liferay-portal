/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayIconSpriteContext} from '@clayui/icon';
import {Root, createRoot} from 'react-dom/client';
import {SWRConfig} from 'swr';

import {WebDAV} from './common/context/WebDAV';
import {AppRouteType} from './common/enums/appRouteType';
import getIconSpriteMap from './common/utils/getIconSpriteMap';
import DealRegistrationForm from './routes/DealRegistrationForm';
import DealRegistrationList from './routes/DealRegistrationList';
import MDFClaimForm from './routes/MDFClaimForm';
import MDFClaimList from './routes/MDFClaimList';
import MDFClaimManagerStatus from './routes/MDFClaimManagerStatus/MDFClaimManagerStatus';
import MDFRequestForm from './routes/MDFRequestForm';
import MDFRequestList from './routes/MDFRequestList';
import MDFRequestManagerStatus from './routes/MDFRequestManagerStatus';
import PartnerOpportunitiesList from './routes/PartnerOpportunitiesList';
import DealsChart from './routes/dashboard/DealsChart';
import LevelChart from './routes/dashboard/LevelChart';
import MDFRequestChart from './routes/dashboard/MDFRequestChart';
import RenewalsChart from './routes/dashboard/RenewalsChart';
import RevenueChart from './routes/dashboard/RevenueChart';

interface IProps {
	liferayWebDAV: string;
	route: AppRouteType;
}

type AppRouteComponent = {
	[key in AppRouteType]?: JSX.Element;
};

const appRoutes: AppRouteComponent = {
	[AppRouteType.MDF_REQUEST_FORM]: <MDFRequestForm />,
	[AppRouteType.MDF_REQUEST_LIST]: <MDFRequestList />,
	[AppRouteType.MDF_CLAIM_FORM]: <MDFClaimForm />,
	[AppRouteType.MDF_CLAIM_LIST]: <MDFClaimList />,
	[AppRouteType.MDF_REQUEST_MANAGER_STATUS]: <MDFRequestManagerStatus />,
	[AppRouteType.MDF_CLAIM_MANAGER_STATUS]: <MDFClaimManagerStatus />,
	[AppRouteType.DEAL_REGISTRATION_FORM]: <DealRegistrationForm />,
	[AppRouteType.DEAL_REGISTRATION_LIST]: <DealRegistrationList />,
	[AppRouteType.PARTNER_OPPORTUNITIES_LIST]: (
		<PartnerOpportunitiesList name="Partner Opportunities" />
	),
	[AppRouteType.RENEWALS_OPPORTUNITIES_LIST]: (
		<PartnerOpportunitiesList
			isRenewalListing={true}
			name="Renewal Opportunities"
		/>
	),
	[AppRouteType.DASHBOARD_DEALS_CHART]: <DealsChart />,
	[AppRouteType.DASHBOARD_LEVEL_CHART]: <LevelChart />,
	[AppRouteType.DASHBOARD_MDF_REQUEST_CHART]: <MDFRequestChart />,
	[AppRouteType.DASHBOARD_RENEWALS_CHART]: <RenewalsChart />,
	[AppRouteType.DASHBOARD_REVENUE_CHART]: <RevenueChart />,
};

const PartnerPortalApp = ({liferayWebDAV, route}: IProps) => {
	return (
		<SWRConfig
			value={{
				revalidateOnFocus: false,
				revalidateOnReconnect: false,
				shouldRetryOnError: true,
			}}
		>
			<WebDAV value={liferayWebDAV}>
				<ClayIconSpriteContext.Provider value={getIconSpriteMap()}>
					{appRoutes[route]}
				</ClayIconSpriteContext.Provider>
			</WebDAV>
		</SWRConfig>
	);
};

class PartnerPortalRemoteAppComponent extends HTMLElement {
	private root: Root | undefined;

	connectedCallback() {
		if (!this.root) {
			this.root = createRoot(this);

			this.root.render(
				<PartnerPortalApp
					liferayWebDAV={
						super.getAttribute('liferaywebdavurl') as string
					}
					route={super.getAttribute('path') as AppRouteType}
				/>
			);
		}
	}
}

const ELEMENT_NAME = 'liferay-partner-custom-element';

if (!customElements.get(ELEMENT_NAME)) {
	customElements.define(ELEMENT_NAME, PartnerPortalRemoteAppComponent);
}
