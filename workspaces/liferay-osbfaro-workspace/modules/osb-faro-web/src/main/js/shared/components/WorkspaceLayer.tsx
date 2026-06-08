/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {Suspense, lazy} from 'react';
import {connect} from 'react-redux';
import {matchPath} from 'react-router';
import {Switch} from 'react-router-dom';
import {compose} from 'redux';
import BundleRouter from '~/route-middleware/BundleRouter';
import {close, open} from '~/shared/actions/modals';
import Loading from '~/shared/components/Loading';
import {withHelpWidget} from '~/shared/hoc';
import {useModalNotifications} from '~/shared/hooks/useModalNotifications';
import {RootState} from '~/shared/store';
import {Project} from '~/shared/util/records';
import {Routes} from '~/shared/util/router';

import RouteNotFound from './RouteNotFound';

// App Routes with Sidebar

const AppSidebarRoutes = lazy(
	() =>
		import(

			/* webpackChunkName: "AppSidebarRoutes" */ '~/shared/pages/AppSidebarRoutes'
		)
);

// Settings

const Settings = lazy(
	() => import(/* webpackChunkName: "Settings" */ '~/settings/pages/Settings')
);

const connector = connect(
	(store: RootState, {location: {pathname}}: {location: Location}) => {
		const path = matchPath<any>(pathname, {
			path: Routes.WORKSPACE_WITH_ID,
		});

		const groupId = path?.params?.groupId ?? '0';

		const project =
			store.getIn(['projects', groupId, 'data'], new Project()) ||
			new Project();

		const faroSubscriptionIMap = project.get('faroSubscription');

		return {
			currentUserId: String(store.getIn(['currentUser', 'data'])),
			groupId,
			serverLocation: project.get('serverLocation'),
			subscriptionName: faroSubscriptionIMap.get('name'),
			workspaceName: project.get('name'),
		};
	},
	{close, open}
);

const WorkspaceLayer = ({
	close,
	groupId,
	open,
}: {
	close: any;
	groupId: string;
	open: any;
}) => {
	useModalNotifications(close, groupId, open);

	return (
		<Suspense fallback={<Loading />}>
			<Switch>
				<BundleRouter data={Settings} path={Routes.SETTINGS} />

				<BundleRouter data={AppSidebarRoutes} path={Routes.CHANNEL} />

				<RouteNotFound />
			</Switch>
		</Suspense>
	);
};

export default compose<any>(connector, withHelpWidget)(WorkspaceLayer);
