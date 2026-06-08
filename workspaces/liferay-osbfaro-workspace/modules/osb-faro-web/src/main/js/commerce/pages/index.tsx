/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import React, {Suspense, lazy} from 'react';
import {Switch, useParams} from 'react-router-dom';
import BundleRouter from '~/route-middleware/BundleRouter';
import Loading from '~/shared/components/Loading';
import RouteNotFound from '~/shared/components/RouteNotFound';
import BasePage from '~/shared/components/base-page';
import StatesRenderer from '~/shared/components/states-renderer/StatesRenderer';
import {useChannelContext} from '~/shared/context/channel';
import {useDataSources} from '~/shared/context/dataSources';
import {useCurrentUser} from '~/shared/hooks/useCurrentUser';
import * as breadcrumbs from '~/shared/util/breadcrumbs';
import {Routes, toRoute} from '~/shared/util/router';
import URLConstants from '~/shared/util/url-constants';

const Overview = lazy(
	() =>
		import(/* webpackChunkName: "CommerceDashboardOverview" */ './Overview')
);

const NAV_ITEMS = [
	{
		exact: true,
		label: Liferay.Language.get('overview'),
		route: Routes.COMMERCE,
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

interface ICommerceDashboardProps extends React.HTMLAttributes<HTMLDivElement> {
	router: Router;
}

const CommerceDashboard: React.FC<ICommerceDashboardProps> = ({router}) => {
	const {channelId, groupId} = useParams<RouterParams>();
	const dataSourceStates = useDataSources();
	const {selectedChannel} = useChannelContext();
	const currentUser = useCurrentUser();

	const authorized = currentUser.isAdmin();

	const selectedChannelName = selectedChannel && selectedChannel.name;

	return (
		<BasePage
			className="commerce-dashboard-root"
			documentTitle={Liferay.Language.get('commerce')}
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
					title={Liferay.Language.get('commerce')}
				/>

				<BasePage.Header.NavBar
					items={NAV_ITEMS}
					routeParams={{channelId, groupId}}
				/>
			</BasePage.Header>

			<BasePage.Context.Provider
				value={{
					filters: {},
					router,
				}}
			>
				<BasePage.Body>
					<Suspense fallback={<Loading />}>
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
										componentProps={{
											channelName: selectedChannelName,
										}}
										data={Overview}
										destructured={false}
										exact
										path={Routes.COMMERCE}
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

export default CommerceDashboard;
