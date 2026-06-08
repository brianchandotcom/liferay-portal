/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {Suspense, lazy} from 'react';
import {Switch} from 'react-router-dom';
import BundleRouter from '~/route-middleware/BundleRouter';
import Loading from '~/shared/components/Loading';
import RouteNotFound from '~/shared/components/RouteNotFound';
import {Routes} from '~/shared/util/router';

const Overview = lazy(
	() => import(/* webpackChunkName: "DataPrivacyOverview" */ './Overview')
);

const RequestLog = lazy(
	() => import(/* webpackChunkName: "RequestLog" */ './RequestLog')
);

const SuppressedUsers = lazy(
	() => import(/* webpackChunkName: "SupressedUsers" */ './SuppressedUsers')
);

interface IDataPrivacyProps extends React.HTMLAttributes<HTMLDivElement> {
	groupId: string;
}

const DataPrivacy: React.FC<IDataPrivacyProps> = ({groupId}) => (
	<Suspense fallback={<Loading />}>
		<Switch>
			<BundleRouter
				data={Overview}
				exact
				path={Routes.SETTINGS_DATA_PRIVACY}
			/>

			<BundleRouter
				componentProps={{groupId}}
				data={SuppressedUsers}
				destructured={false}
				exact
				path={Routes.SETTINGS_DATA_PRIVACY_SUPPRESSED_USERS}
			/>

			<BundleRouter
				componentProps={{groupId}}
				data={RequestLog}
				destructured={false}
				path={Routes.SETTINGS_DATA_PRIVACY_REQUEST_LOG}
			/>

			<RouteNotFound />
		</Switch>
	</Suspense>
);

export default DataPrivacy;
