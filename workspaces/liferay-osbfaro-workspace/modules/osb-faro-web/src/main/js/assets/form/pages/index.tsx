/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import {pickBy} from 'lodash';
import React, {Suspense, lazy, useState} from 'react';
import {Switch} from 'react-router-dom';
import BundleRouter from '~/route-middleware/BundleRouter';
import Loading from '~/shared/components/Loading';
import RouteNotFound from '~/shared/components/RouteNotFound';
import BasePage from '~/shared/components/base-page';
import DownloadCSVReport from '~/shared/components/download-report/DownloadCSVReport';
import DownloadPDFReport from '~/shared/components/download-report/DownloadPDFReport';
import {CSVType} from '~/shared/components/download-report/utils';
import {useChannelContext} from '~/shared/context/channel';
import {useDataSources} from '~/shared/context/dataSources';
import {useQueryRangeSelectors} from '~/shared/hooks/useQueryRangeSelectors';
import {Router} from '~/shared/types';
import * as breadcrumbs from '~/shared/util/breadcrumbs';
import {ENABLE_GLOBAL_FILTER} from '~/shared/util/constants';
import {sub} from '~/shared/util/lang';
import {Routes, getMatchedRoute} from '~/shared/util/router';
import {getSafeDecodedURIComponent} from '~/shared/util/util';

import Filter from '../hocs/Filter';

const Overview = lazy(
	() => import(/* webpackChunkName: "FormsOverview" */ './Overview')
);
const KnownIndividuals = lazy(
	() =>
		import(

			/* webpackChunkName: "FormsKnownIndividuals" */ './KnownIndividuals'
		)
);

const NAV_ITEMS = [
	{
		exact: true,
		label: Liferay.Language.get('overview'),
		route: Routes.ASSETS_FORMS_OVERVIEW,
	},
	{
		exact: true,
		label: Liferay.Language.get('known-individuals'),
		route: Routes.ASSETS_FORMS_KNOWN_INDIVIDUALS,
	},
];

const Form: React.FC<{
	className: string;
	router: Router;
}> = ({className, router}) => {
	const {
		params: {
			assetId,
			channelId = '',
			groupId = '',
			title = '',
			touchpoint,
			type = '',
		},
	} = router;

	const [filters, setFilters] = useState({});

	const dataSourceStates = useDataSources();

	const decodedTitle = getSafeDecodedURIComponent(title);
	const decodedType = getSafeDecodedURIComponent(type);

	const rangeSelectorsFromQuery = useQueryRangeSelectors();

	const {selectedChannel} = useChannelContext();

	return (
		<BasePage
			className={getCN(className)}
			documentTitle={Liferay.Language.get('assets')}
		>
			<BasePage.Header
				breadcrumbs={[
					breadcrumbs.getHome({
						channelId,
						groupId,
						label: selectedChannel?.name,
					}),
					breadcrumbs.getAssets({channelId, groupId}),
					breadcrumbs.getEntityName({label: decodedTitle}),
				]}
				groupId={groupId}
			>
				{type && (
					<BasePage.Header.TitleSection
						label
						subtitle={decodedType}
						title={decodedTitle}
					/>
				)}

				<BasePage.Header.NavBar
					items={NAV_ITEMS}
					routeParams={{
						assetId,
						channelId,
						groupId,
						title,
						touchpoint,
						type,
					}}
					routeQueries={pickBy(rangeSelectorsFromQuery)}
				/>
			</BasePage.Header>

			{getMatchedRoute(NAV_ITEMS) === Routes.ASSETS_FORMS_OVERVIEW && (
				<BasePage.SubHeader>
					<div className="d-flex justify-content-end w-100">
						<DownloadPDFReport
							disabled={!!dataSourceStates.empty}
							subtitle={selectedChannel?.name}
							title={
								sub(Liferay.Language.get('x-dashboard'), [
									decodedTitle,
								]) as string
							}
						/>
					</div>
				</BasePage.SubHeader>
			)}

			{getMatchedRoute(NAV_ITEMS) ===
				Routes.ASSETS_FORMS_KNOWN_INDIVIDUALS && (
				<BasePage.SubHeader>
					<div className="d-flex justify-content-end w-100">
						<DownloadCSVReport
							assetId={assetId}
							assetType="form"
							disabled={!!dataSourceStates.empty}
							type={CSVType.Individual}
							typeLang={Liferay.Language.get('known-individuals')}
						/>
					</div>
				</BasePage.SubHeader>
			)}

			<BasePage.Context.Provider value={{filters, router}}>
				{ENABLE_GLOBAL_FILTER && (
					<BasePage.SubHeader>
						<Filter onChange={setFilters} />
					</BasePage.SubHeader>
				)}

				<BasePage.Body>
					<Suspense fallback={<Loading />}>
						<Switch>
							<BundleRouter
								data={Overview}
								destructured={false}
								exact
								path={Routes.ASSETS_FORMS_OVERVIEW}
							/>

							<BundleRouter
								data={KnownIndividuals}
								destructured={false}
								exact
								path={Routes.ASSETS_FORMS_KNOWN_INDIVIDUALS}
							/>

							<RouteNotFound />
						</Switch>
					</Suspense>
				</BasePage.Body>
			</BasePage.Context.Provider>
		</BasePage>
	);
};

export default Form;
