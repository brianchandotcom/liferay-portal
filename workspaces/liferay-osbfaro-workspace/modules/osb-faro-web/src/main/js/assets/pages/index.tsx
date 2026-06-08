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
import DownloadCSVReport from '~/shared/components/download-report/DownloadCSVReport';
import {CSVType} from '~/shared/components/download-report/utils';
import StatesRenderer from '~/shared/components/states-renderer/StatesRenderer';
import {useChannelContext} from '~/shared/context/channel';
import {useDataSources} from '~/shared/context/dataSources';
import {useCurrentUser} from '~/shared/hooks/useCurrentUser';
import {Router} from '~/shared/types';
import * as breadcrumbs from '~/shared/util/breadcrumbs';
import {User} from '~/shared/util/records';
import {Routes, getMatchedRoute, toRoute} from '~/shared/util/router';
import URLConstants from '~/shared/util/url-constants';

const BlogsList = lazy(
	() => import(/* webpackChunkName: "BlogsList" */ './BlogsList')
);

const DocumentsAndMediaList = lazy(
	() =>
		import(

			/* webpackChunkName: "DocumentsAndMediaList" */ './DocumentsAndMediaList'
		)
);

const FormsList = lazy(
	() => import(/* webpackChunkName: "FormsList" */ './FormsList')
);

const WebContentList = lazy(
	() => import(/* webpackChunkName: "WebContentList" */ './WebContentList')
);

const NAV_ITEMS = [
	{
		exact: true,
		label: Liferay.Language.get('blogs'),
		route: Routes.ASSETS_BLOGS,
	},
	{
		exact: true,
		label: Liferay.Language.get('documents-and-media'),
		route: Routes.ASSETS_DOCUMENTS_AND_MEDIA,
	},
	{
		exact: true,
		label: Liferay.Language.get('forms'),
		route: Routes.ASSETS_FORMS,
	},
	{
		exact: true,
		label: Liferay.Language.get('web-content'),
		route: Routes.ASSETS_WEB_CONTENT,
	},
];

interface IAssetsProps extends React.HTMLAttributes<HTMLElement> {
	currentUser: User;
	router: Router;
}

const Assets: React.FC<IAssetsProps> = ({className, router}) => {
	const {channelId, groupId} = useParams<{
		channelId: string;
		groupId: string;
	}>();
	const dataSourceStates = useDataSources();
	const {selectedChannel} = useChannelContext();
	const currentUser = useCurrentUser();

	const authorized = currentUser.isAdmin();

	return (
		<BasePage
			className={className}
			documentTitle={Liferay.Language.get('assets')}
		>
			<BasePage.Header
				breadcrumbs={[
					breadcrumbs.getHome({
						channelId,
						groupId,
						label: selectedChannel?.name,
					}),
				]}
				groupId={groupId}
			>
				<BasePage.Header.TitleSection
					title={Liferay.Language.get('assets')}
				/>

				<BasePage.Header.NavBar
					items={NAV_ITEMS}
					routeParams={{channelId, groupId}}
				/>
			</BasePage.Header>
			{getMatchedRoute(NAV_ITEMS) === Routes.ASSETS_BLOGS && (
				<BasePage.SubHeader>
					<div className="d-flex justify-content-end w-100">
						<DownloadCSVReport
							disabled={!!dataSourceStates.empty}
							type={CSVType.Blog}
							typeLang={Liferay.Language.get('blogs')}
						/>
					</div>
				</BasePage.SubHeader>
			)}
			{getMatchedRoute(NAV_ITEMS) ===
				Routes.ASSETS_DOCUMENTS_AND_MEDIA && (
				<BasePage.SubHeader>
					<div className="d-flex justify-content-end w-100">
						<DownloadCSVReport
							disabled={!!dataSourceStates.empty}
							type={CSVType.Document}
							typeLang={Liferay.Language.get(
								'documents-and-media'
							)}
						/>
					</div>
				</BasePage.SubHeader>
			)}
			{getMatchedRoute(NAV_ITEMS) === Routes.ASSETS_FORMS && (
				<BasePage.SubHeader>
					<div className="d-flex justify-content-end w-100">
						<DownloadCSVReport
							disabled={!!dataSourceStates.empty}
							type={CSVType.Form}
							typeLang={Liferay.Language.get('forms')}
						/>
					</div>
				</BasePage.SubHeader>
			)}
			{getMatchedRoute(NAV_ITEMS) === Routes.ASSETS_WEB_CONTENT && (
				<BasePage.SubHeader>
					<div className="d-flex justify-content-end w-100">
						<DownloadCSVReport
							disabled={!!dataSourceStates.empty}
							type={CSVType.Journal}
							typeLang={Liferay.Language.get('web-content')}
						/>
					</div>
				</BasePage.SubHeader>
			)}
			<BasePage.Body>
				<BasePage.Context.Provider
					value={{
						filters: {},
						router,
					}}
				>
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
										data={BlogsList}
										destructured={false}
										exact
										path={Routes.ASSETS_BLOGS}
									/>

									<BundleRouter
										data={DocumentsAndMediaList}
										destructured={false}
										exact
										path={Routes.ASSETS_DOCUMENTS_AND_MEDIA}
									/>

									<BundleRouter
										data={FormsList}
										destructured={false}
										exact
										path={Routes.ASSETS_FORMS}
									/>

									<BundleRouter
										data={WebContentList}
										destructured={false}
										exact
										path={Routes.ASSETS_WEB_CONTENT}
									/>

									<RouteNotFound />
								</Switch>
							</StatesRenderer.Success>
						</StatesRenderer>
					</Suspense>
				</BasePage.Context.Provider>
			</BasePage.Body>
		</BasePage>
	);
};

export default Assets;
