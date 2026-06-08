/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {Suspense, lazy, useContext} from 'react';
import {Switch, useParams} from 'react-router-dom';
import BundleRouter from '~/route-middleware/BundleRouter';
import * as API from '~/shared/api';
import Loading from '~/shared/components/Loading';
import RouteNotFound from '~/shared/components/RouteNotFound';
import BasePage from '~/shared/components/base-page';
import {ChannelContext} from '~/shared/context/channel';
import {useRequest} from '~/shared/hooks/useRequest';
import * as breadcrumbs from '~/shared/util/breadcrumbs';
import {Routes} from '~/shared/util/router';

const Activities = lazy(
	() => import(/* webpackChunkName: "AccountActivities" */ './Activities')
);
const Profile = lazy(
	() => import(/* webpackChunkName: "AccountProfile" */ './Profile')
);

const NAV_ITEMS = [
	{
		exact: true,
		label: Liferay.Language.get('activities'),
		route: Routes.CONTACTS_ACCOUNT,
	},
	{
		exact: true,
		label: Liferay.Language.get('profile'),
		route: Routes.CONTACTS_ACCOUNT_PROFILE,
	},
];

const AccountProfileRoutes = () => {
	const {selectedChannel} = useContext(ChannelContext);

	const {channelId, groupId, id} = useParams();

	const {data, loading} = useRequest({
		dataSourceFn: API.accounts.fetch,
		variables: {accountId: id!, channelId: channelId!, groupId: groupId!},
	});

	const accountName: string =
		data?.accountName || Liferay.Language.get('account');

	return (
		<BasePage
			documentTitle={`${accountName} - ${Liferay.Language.get(
				'account'
			)}`}
			fluid
		>
			<BasePage.Header
				breadcrumbs={[
					breadcrumbs.getHome({
						channelId: channelId!,
						groupId: groupId!,
						label: selectedChannel?.name,
					}),
					breadcrumbs.getAccounts({
						channelId: channelId!,
						groupId: groupId!,
					}),
					breadcrumbs.getEntityName({label: accountName}),
				]}
				groupId={groupId!}
			>
				<BasePage.Row>
					<BasePage.Header.TitleSection title={accountName} />
				</BasePage.Row>

				<BasePage.Header.NavBar
					items={NAV_ITEMS}
					routeParams={{channelId, groupId, id}}
				/>
			</BasePage.Header>

			<BasePage.Body>
				<Suspense fallback={<Loading />}>
					<Switch>
						<BundleRouter
							componentProps={{account: data, loading}}
							data={Profile}
							exact
							path={Routes.CONTACTS_ACCOUNT_PROFILE}
						/>

						<BundleRouter
							data={Activities}
							exact
							path={Routes.CONTACTS_ACCOUNT}
						/>

						<RouteNotFound />
					</Switch>
				</Suspense>
			</BasePage.Body>
		</BasePage>
	);
};

export default AccountProfileRoutes;
