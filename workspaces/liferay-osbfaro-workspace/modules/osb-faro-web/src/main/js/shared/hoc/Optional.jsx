/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isNil} from 'lodash';
import React from 'react';

const defaultOptions = {
	idPropName: 'id',
};

/**
 * Takes an HOC and conditionally renders it based on a particular prop.
 * This can be used to change a `.required()` hoc prop into an optional
 * one. See the segment edit page for an example of this usage with the
 * segment prop.
 * @param {Function} hoc - The HOC to apply to the component.
 * @returns {Function} - The newly wrapped component.
 */
export default (hoc, options = {}) =>
	(WrappedComponent) => {
		const OptionalHOC = hoc(WrappedComponent);
		const {idPropName} = {...defaultOptions, ...options};

		return class Optional extends React.Component {
			render() {
				const idProp = this.props[idPropName];

				if (isNil(idProp)) {
					return <WrappedComponent {...this.props} />;
				}
				else {
					return <OptionalHOC {...this.props} />;
				}
			}
		};
	};
