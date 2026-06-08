/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect} from 'react';
import {matchPath} from 'react-router-dom';
import {Channel, getDefaultChannel} from '~/shared/components/channels-menu';
import ErrorPage from '~/shared/pages/ErrorPage';
import {Routes, toRoute} from '~/shared/util/router';

type History = {
	replace: (path: string) => void;
};

type Location = {
	pathname: string;
};

interface IWrappedComponentProps {
	channelId: string;
	channels: Array<Channel>;
	defaultChannelId: string;
	groupId: string;
	history: History;
	location: Location;
}

const checkValidChannel =
	(WrappedComponent: React.ComponentType<IWrappedComponentProps>) =>
	({
		channelId,
		channels,
		defaultChannelId,
		groupId,
		history,
		location,
		...otherProps
	}: IWrappedComponentProps & {[key: string]: any}) => {
		useEffect(() => {
			const isHome = matchPath(location.pathname, {
				exact: true,
				path: Routes.WORKSPACE_WITH_ID,
			});

			if (isHome) {
				const channel = getDefaultChannel(defaultChannelId, channels);

				history.replace(
					toRoute(Routes.SITES, {
						...(channel && {channelId: channel.id}),
						groupId,
					})
				);
			}

			// eslint-disable-next-line react-hooks/exhaustive-deps
		}, []);

		if (
			channelId &&
			!!channels.length &&
			!channels.some(({id}) => id === channelId)
		) {
			return <ErrorPage />;
		}

		return (
			<WrappedComponent
				{...otherProps}
				channelId={channelId}
				channels={channels}
				defaultChannelId={channelId}
				groupId={groupId}
				history={history}
				location={location}
			/>
		);
	};

export default checkValidChannel;
