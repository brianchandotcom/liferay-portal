/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {Suspense, lazy} from 'react';
import {Switch} from 'react-router-dom';
import BundleRouter from '~/route-middleware/BundleRouter';
import Loading from '~/shared/components/Loading';
import RouteNotFound from '~/shared/components/RouteNotFound';
import {
	DEVELOPER_MODE,
	ENABLE_BLOCKLIST_KEYWORDS,
} from '~/shared/util/constants';
import {Routes} from '~/shared/util/router';

const EventBlockList = lazy(
	() =>
		import(/* webpackChunkName: "BlockList" */ '../events/pages/BlockList')
);

const Overview = lazy(
	() => import(/* webpackChunkName: "DefinitionsOverview" */ './Overview')
);

const IndividualAttributes = lazy(
	() =>
		import(

			/* webpackChunkName: "DefinitionsIndividualAttributes" */ './IndividualAttributes'
		)
);

const InterestTopics = lazy(
	() =>
		import(

			/* webpackChunkName: "DefinitionsInterestTopics" */ './InterestTopics'
		)
);

const TrackedBehaviors = lazy(
	() =>
		import(/* webpackChunkName: "TrackedBehaviors" */ './TrackedBehaviors')
);

const Search = lazy(
	() => import(/* webpackChunkName: "DefinitionsSearch" */ './search/Search')
);

const Events = lazy(
	() =>
		import(

			/* webpackChunkName: "DefinitionsEvents" */ '../events/pages/Events'
		)
);

const EventAttributes = lazy(
	() =>
		import(

			/* webpackChunkName: "DefinitionsEvents" */ '../event-attributes/pages/EventAttributes'
		)
);

const EventView = lazy(
	() =>
		import(

			/* webpackChunkName: "DefinitionsEventView" */ '../events/pages/View'
		)
);

const AttributeView = lazy(
	() =>
		import(

			/* webpackChunkName: "DefinitionsEventAttributesView" */ '../event-attributes/pages/AttributeView'
		)
);

interface IDefinitionsProps extends React.HTMLAttributes<HTMLDivElement> {}

const Definitions: React.FC<IDefinitionsProps> = () => (
	<Suspense fallback={<Loading />}>
		<Switch>
			<BundleRouter
				data={Overview}
				exact
				path={Routes.SETTINGS_DEFINITIONS}
			/>

			{ENABLE_BLOCKLIST_KEYWORDS && (
				<BundleRouter
					data={InterestTopics}
					exact
					path={Routes.SETTINGS_DEFINITIONS_INTEREST_TOPICS}
				/>
			)}

			<BundleRouter
				data={IndividualAttributes}
				exact
				path={Routes.SETTINGS_DEFINITIONS_INDIVIDUAL_ATTRIBUTES}
			/>

			<BundleRouter
				data={Search}
				exact
				path={Routes.SETTINGS_DEFINITIONS_SEARCH}
			/>

			{DEVELOPER_MODE && (

				// TODO: LRAC-4511 Remove when new TrackedBehavior page exists

				<BundleRouter
					data={TrackedBehaviors}
					exact
					path={Routes.SETTINGS_DEFINITIONS_BEHAVIORS}
				/>
			)}

			<BundleRouter
				data={AttributeView}
				exact
				path={Routes.SETTINGS_DEFINITIONS_EVENT_ATTRIBUTES_VIEW}
			/>

			<BundleRouter
				data={Events}
				path={[
					Routes.SETTINGS_DEFINITIONS_EVENTS_CUSTOM,
					Routes.SETTINGS_DEFINITIONS_EVENTS_DEFAULT,
				]}
			/>

			<BundleRouter
				data={EventAttributes}
				path={[
					Routes.SETTINGS_DEFINITIONS_EVENT_ATTRIBUTES_LOCAL,
					Routes.SETTINGS_DEFINITIONS_EVENT_ATTRIBUTES_GLOBAL,
				]}
			/>

			<BundleRouter
				data={EventBlockList}
				path={Routes.SETTINGS_DEFINITIONS_EVENTS_BLOCK_LIST}
			/>

			<BundleRouter
				data={EventView}
				exact
				path={Routes.SETTINGS_DEFINITIONS_EVENTS_VIEW}
			/>

			<RouteNotFound />
		</Switch>
	</Suspense>
);

export default Definitions;
