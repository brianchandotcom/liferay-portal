/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {Route, RouteProps, matchPath} from 'react-router-dom';
import {useQueryParams} from '~/shared/hooks/useQueryParams';

interface BundleRouterProps extends RouteProps {
	componentProps?: Record<string, unknown>;
	data: React.ComponentType<any>;
	destructured?: boolean;
}

const BundleRouter = ({
	componentProps = {},
	data: Component,
	destructured = true,
	...otherRouteProps
}: BundleRouterProps) => {
	const query = useQueryParams();

	return (
		<Route
			{...otherRouteProps}
			render={({history, match: {params, path}}) => {
				if (destructured) {
					return (
						<Component
							history={history}
							{...query}
							{...params}
							{...componentProps}
						/>
					);
				}

				const matchedPath = matchPath<{touchpoint?: string}>(
					window.location.pathname,
					{path}
				);

				return (
					<Component
						history={history}
						router={{
							params: {
								...params,
								touchpoint: matchedPath?.params.touchpoint,
							},
							query,
						}}
						{...componentProps}
					/>
				);
			}}
		/>
	);
};

export default BundleRouter;
