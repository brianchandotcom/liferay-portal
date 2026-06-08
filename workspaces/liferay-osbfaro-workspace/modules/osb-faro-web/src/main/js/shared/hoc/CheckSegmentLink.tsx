/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect, useState} from 'react';
import {matchPath} from 'react-router-dom';
import * as API from '~/shared/api';
import {WrapSafeResults} from '~/shared/hoc/util';
import {Routes, toRoute} from '~/shared/util/router';

type History = {
	replace: (path: string) => void;
};

type Location = {
	pathname: string;
};

interface IWrappedComponentProps {
	groupId: string;
	history: History;
	location: Location;
}

const checkSegmentLink =
	(WrappedComponent: React.ComponentType<IWrappedComponentProps>) =>
	({
		groupId,
		history,
		location,
		...otherProps
	}: IWrappedComponentProps & {[key: string]: any}) => {
		const [error, setError] = useState();
		const [loading, setLoading] = useState(false);

		useEffect(() => {
			const segment = matchPath<{channelId: string; id: string}>(
				location.pathname,
				{
					exact: true,
					path: Routes.CONTACTS_SEGMENT,
				}
			);

			if (segment && !segment.params.channelId) {
				setLoading(true);

				API.individualSegment
					.fetch({groupId, segmentId: segment.params.id})
					.then(({channelId, id}) => {
						setLoading(false);

						history.replace(
							toRoute(Routes.CONTACTS_SEGMENT, {
								channelId,
								groupId,
								id,
							})
						);
					})
					.catch((err) => {
						setLoading(false);
						setError(err);
					});
			}

			// eslint-disable-next-line react-hooks/exhaustive-deps
		}, []);

		return (
			<WrapSafeResults error={error} loading={loading} page pageDisplay>
				<WrappedComponent
					{...otherProps}
					groupId={groupId}
					history={history}
					location={location}
				/>
			</WrapSafeResults>
		);
	};

export default checkSegmentLink;
