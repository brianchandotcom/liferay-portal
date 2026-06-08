/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import React, {Suspense, lazy, useContext} from 'react';
import {Switch, withRouter} from 'react-router-dom';
import BundleRouter from '~/route-middleware/BundleRouter';
import * as API from '~/shared/api';
import Loading from '~/shared/components/Loading';
import RouteNotFound from '~/shared/components/RouteNotFound';
import BasePage from '~/shared/components/base-page';
import DownloadCSVReport from '~/shared/components/download-report/DownloadCSVReport';
import {CSVType} from '~/shared/components/download-report/utils';
import {ChannelContext} from '~/shared/context/channel';
import {useDataSources} from '~/shared/context/dataSources';
import {compose, withIndividual} from '~/shared/hoc';
import {useLDPEnabled} from '~/shared/hooks/useLDPEnabled';
import {useRequest} from '~/shared/hooks/useRequest';
import * as breadcrumbs from '~/shared/util/breadcrumbs';
import {Routes, getMatchedRoute} from '~/shared/util/router';

import {buildHeaderSubtitle} from './utils/utils';

const AssociatedSegments = lazy(
	() =>
		import(

			/* webpackChunkName: "IndividualAssociatedSegments" */ './AssociatedSegments'
		)
);

const InterestDetails = lazy(
	() =>
		import(

			/* webpackChunkName: "IndividualInterestDetails" */ './InterestDetails'
		)
);
const Interests = lazy(
	() => import(/* webpackChunkName: "IndividualInterests" */ './Interests')
);

const OverviewCDP = lazy(
	() => import(/* webpackChunkName: "IndividualOverview" */ './OverviewCDP')
);

const IndividualProfileCDP = lazy(
	() =>
		import(

			/* webpackChunkName: "IndividualProfileCDP" */ './IndividualProfileCDP'
		)
);

const NAV_ITEMS_CDP = [
	{
		exact: true,
		label: Liferay.Language.get('activities'),
		route: Routes.CONTACTS_INDIVIDUAL,
	},
	{
		exact: true,
		label: Liferay.Language.get('profile'),
		route: Routes.CONTACTS_INDIVIDUAL_DETAILS,
	},
	{
		exact: false,
		label: Liferay.Language.get('interests'),
		route: Routes.CONTACTS_INDIVIDUAL_INTERESTS,
	},
	{
		exact: true,
		label: Liferay.Language.get('segments'),
		route: Routes.CONTACTS_INDIVIDUAL_SEGMENTS,
	},
];

interface IIndividualProfileRoutesCDPProps {
	channelId: string;
	className?: string;
	groupId: string;
	id: string;
	individual: {
		id: string;
		name?: string;
		toJS: () => {
			accountName: string;
			lastSessionCountry: string;
			properties: {email: string};
		};
	};
}

export const IndividualProfileRoutesCDP = function IndividualProfileRoutesCDP({
	channelId,
	className,
	groupId,
	id,
	individual,
}: IIndividualProfileRoutesCDPProps) {
	const dataSourceStates = useDataSources();

	const LDPEnabled = useLDPEnabled({groupId});

	const {selectedChannel} = useContext(ChannelContext);

	const matchedRoute = getMatchedRoute(NAV_ITEMS_CDP);

	const componentProps = {individual};

	const entityName = individual.name || Liferay.Language.get('unknown');

	const {data: dataSourceData} = useRequest({
		dataSourceFn: API.dataSource.search,
		variables: {
			delta: 1,
			groupId,
		},
	});

	return (
		<BasePage
			className={
				matchedRoute === Routes.CONTACTS_INDIVIDUAL
					? getCN('overview-root', className)
					: className
			}
			documentTitle={`${entityName} - ${Liferay.Language.get(
				'individuals'
			)}`}
		>
			<BasePage.Header
				breadcrumbs={[
					breadcrumbs.getHome({
						channelId,
						groupId,
						label: selectedChannel && selectedChannel.name,
					}),
					breadcrumbs.getIndividuals({
						channelId,
						groupId,

						LDPEnabled,
					}),
					breadcrumbs.getEntityName({label: entityName}),
				]}
				groupId={groupId}
			>
				<BasePage.Header.TitleSection
					subtitle={buildHeaderSubtitle(individual.toJS())}
					title={entityName}
				/>

				<BasePage.Header.NavBar
					items={NAV_ITEMS_CDP}
					routeParams={{channelId, groupId, id}}
				/>
			</BasePage.Header>
			{getMatchedRoute(NAV_ITEMS_CDP) === Routes.CONTACTS_INDIVIDUAL &&
				dataSourceData?.total > 0 && (
					<BasePage.SubHeader>
						<div className="d-flex justify-content-end w-100">
							<DownloadCSVReport
								disabled={!!dataSourceStates.empty}
								individualId={individual.id}
								type={CSVType.Event}
								typeLang={Liferay.Language.get('events')}
							/>
						</div>
					</BasePage.SubHeader>
				)}
			<BasePage.Body>
				<Suspense fallback={<Loading />}>
					<Switch>
						<BundleRouter
							componentProps={componentProps}
							data={AssociatedSegments}
							exact
							path={Routes.CONTACTS_INDIVIDUAL_SEGMENTS}
						/>

						<BundleRouter
							componentProps={componentProps}
							data={IndividualProfileCDP}
							exact
							path={Routes.CONTACTS_INDIVIDUAL_DETAILS}
						/>

						<BundleRouter
							componentProps={componentProps}
							data={InterestDetails}
							exact
							path={Routes.CONTACTS_INDIVIDUAL_INTEREST_DETAILS}
						/>

						<BundleRouter
							componentProps={componentProps}
							data={Interests}
							exact
							path={Routes.CONTACTS_INDIVIDUAL_INTERESTS}
						/>

						<BundleRouter
							componentProps={componentProps}
							data={OverviewCDP}
							exact
							path={Routes.CONTACTS_INDIVIDUAL}
						/>

						<RouteNotFound />
					</Switch>
				</Suspense>
			</BasePage.Body>
		</BasePage>
	);
};

export default compose(withRouter, withIndividual)(IndividualProfileRoutesCDP);
