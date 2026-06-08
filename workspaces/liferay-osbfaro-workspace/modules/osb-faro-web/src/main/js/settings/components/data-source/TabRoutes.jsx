/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PropTypes} from 'prop-types';
import React from 'react';
import {Switch} from 'react-router-dom';
import BundleRouter from '~/route-middleware/BundleRouter';
import RouteNotFound from '~/shared/components/RouteNotFound';
import omitDefinedProps from '~/shared/util/omitDefinedProps';

const tabRoutesPropTypes = {
	routes: PropTypes.arrayOf(
		PropTypes.shape({
			component: PropTypes.func,
			path: PropTypes.string,
		})
	),
};

export default class TabRoutes extends React.Component {
	static propTypes = tabRoutesPropTypes;

	render() {
		const {routes, ...otherProps} = this.props;

		return (
			<Switch>
				{routes.map(({component: Component, path}) => (
					<BundleRouter
						componentProps={omitDefinedProps(
							otherProps,
							tabRoutesPropTypes
						)}
						data={Component}
						exact
						key={path}
						path={path}
					/>
				))}
				<RouteNotFound />
			</Switch>
		);
	}
}
