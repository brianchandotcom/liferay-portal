/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import getCN from 'classnames';
import React, {Suspense, lazy} from 'react';
import {Switch, useParams} from 'react-router-dom';
import BundleRouter from '~/route-middleware/BundleRouter';
import Loading from '~/shared/components/Loading';
import RouteNotFound from '~/shared/components/RouteNotFound';
import BasePage from '~/shared/components/base-page';
import DownloadCSVReport from '~/shared/components/download-report/DownloadCSVReport';
import DownloadPDFReport from '~/shared/components/download-report/DownloadPDFReport';
import {CSVType} from '~/shared/components/download-report/utils';
import StatesRenderer from '~/shared/components/states-renderer/StatesRenderer';
import {useChannelContext} from '~/shared/context/channel';
import {useDataSources} from '~/shared/context/dataSources';
import {useCurrentUser} from '~/shared/hooks/useCurrentUser';
import * as breadcrumbs from '~/shared/util/breadcrumbs';
import {Routes, getMatchedRoute, toRoute} from '~/shared/util/router';
import URLConstants from '~/shared/util/url-constants';

const InterestDetails = lazy(
	() =>
		import(

			/* webpackChunkName: "SitesDashboardInterestDetails" */ './InterestDetails'
		)
);
const Interests = lazy(
	() =>
		import(/* webpackChunkName: "SitesDashboardInterests" */ './Interests')
);
const Overview = lazy(
	() => import(/* webpackChunkName: "SitesDashboardOverview" */ './Overview')
);
const SearchTermsPage = lazy(
	() =>
		import(

			/* webpackChunkName: "SitesDashboardSearchTerms" */ './SearchTermsPage'
		)
);
const Touchpoints = lazy(
	() =>
		import(

			/* webpackChunkName: "SitesDashboardTouchpoints" */ './Touchpoints'
		)
);

const NAV_ITEMS = [
	{
		exact: true,
		label: Liferay.Language.get('overview'),
		route: Routes.SITES,
	},
	{
		exact: true,
		label: Liferay.Language.get('pages'),
		route: Routes.SITES_TOUCHPOINTS,
	},
	{
		exact: false,
		label: Liferay.Language.get('interests'),
		route: Routes.SITES_INTERESTS,
	},
	{
		exact: true,
		label: Liferay.Language.get('search-terms'),
		route: Routes.SITES_SEARCH_TERMS,
	},
];

type RouterParams = {
	channelId: string;
	groupId: string;
};

type Router = {
	params: RouterParams;
	query: object;
};

interface IDashboardProps extends React.HTMLAttributes<HTMLDivElement> {
	router: Router;
}

export const Dashboard = function Dashboard({router}: IDashboardProps) {
	const {channelId, groupId = ''} = useParams<{
		channelId: string;
		groupId: string;
	}>();
	const dataSourceStates = useDataSources();
	const {selectedChannel} = useChannelContext();
	const currentUser = useCurrentUser();

	if (!channelId) {
		return null;
	}

	const authorized = currentUser.isAdmin();
	const selectedChannelName = selectedChannel && selectedChannel.name;
	const matchedRoute = getMatchedRoute(NAV_ITEMS);

	return (
		<BasePage
			className="sites-dashboard-root"
			documentTitle={Liferay.Language.get('sites')}
		>
			<BasePage.Header
				breadcrumbs={[
					breadcrumbs.getHome({
						channelId,
						groupId,
						label: selectedChannelName,
					}),
				]}
				groupId={groupId}
			>
				<BasePage.Header.TitleSection
					className={getCN({'no-sites-connected': !selectedChannel})}
					title={
						selectedChannel
							? Liferay.Language.get('sites')
							: Liferay.Language.get('no-sites-connected')
					}
				/>

				<BasePage.Header.NavBar
					items={NAV_ITEMS}
					routeParams={{channelId, groupId}}
				/>
			</BasePage.Header>

			{matchedRoute !== Routes.SITES_INTERESTS && (
				<BasePage.SubHeader>
					<div className="d-flex justify-content-end w-100">
						{matchedRoute === Routes.SITES && (
							<DownloadPDFReport
								disabled={!!dataSourceStates.empty}
								subtitle={selectedChannelName ?? undefined}
								title={Liferay.Language.get('sites-dashboard')}
							/>
						)}

						{matchedRoute === Routes.SITES_SEARCH_TERMS && (
							<DownloadCSVReport
								disabled={!!dataSourceStates.empty}
								type={CSVType.SearchTerms}
								typeLang={Liferay.Language.get('search-terms')}
							/>
						)}

						{matchedRoute === Routes.SITES_TOUCHPOINTS && (
							<DownloadCSVReport
								disabled={!!dataSourceStates.empty}
								type={CSVType.Page}
								typeLang={Liferay.Language.get('pages')}
							/>
						)}
					</div>
				</BasePage.SubHeader>
			)}

			<BasePage.Context.Provider
				value={{
					filters: {},
					router,
				}}
			>
				<BasePage.Body>
					<Suspense fallback={<Loading center />}>
						<StatesRenderer {...dataSourceStates}>
							<StatesRenderer.Empty
								description={
									<>
										{authorized
											? Liferay.Language.get(
													'connect-a-data-source-with-sites-data'
												)
											: Liferay.Language.get(
													'please-contact-your-workspace-administrator-to-add-data-sources'
												)}

										<ClayLink
											className="d-block mb-3"
											href={
												URLConstants.DataSourceConnection
											}
											key="DOCUMENTATION"
											target="_blank"
										>
											{Liferay.Language.get(
												'access-our-documentation-to-learn-more'
											)}
										</ClayLink>

										{authorized && (
											<ClayLink
												button
												className="button-root"
												displayType="primary"
												href={toRoute(
													Routes.SETTINGS_DATA_SOURCE_LIST,
													{
														groupId,
													}
												)}
											>
												{Liferay.Language.get(
													'connect-data-source'
												)}
											</ClayLink>
										)}
									</>
								}
								displayCard
								title={Liferay.Language.get(
									'no-sites-synced-from-data-sources'
								)}
							/>

							<StatesRenderer.Success>
								<Switch>
									<BundleRouter
										data={InterestDetails}
										destructured={false}
										exact
										path={Routes.SITES_INTEREST_DETAILS}
									/>

									<BundleRouter
										data={Interests}
										destructured={false}
										exact
										path={Routes.SITES_INTERESTS}
									/>

									<BundleRouter
										data={Touchpoints}
										destructured={false}
										exact
										path={Routes.SITES_TOUCHPOINTS}
									/>

									<BundleRouter
										componentProps={{
											channelName: selectedChannelName,
										}}
										data={Overview}
										destructured={false}
										exact
										path={Routes.SITES}
									/>

									<BundleRouter
										data={SearchTermsPage}
										destructured={false}
										exact
										path={Routes.SITES_SEARCH_TERMS}
									/>

									<RouteNotFound />
								</Switch>
							</StatesRenderer.Success>
						</StatesRenderer>
					</Suspense>
				</BasePage.Body>
			</BasePage.Context.Provider>
		</BasePage>
	);
};

export default Dashboard;
