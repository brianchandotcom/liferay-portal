/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import autobind from 'autobind-decorator';
import {isNull} from 'lodash';
import React from 'react';

/**
 * HOC for tracking a selecting point.
 * @param {function} WrappedComponent
 * @returns {function} - The WrappedComponent.
 */
export default function WithSelectedPoint(WrappedComponent) {
	class WithSelectedPoint extends React.Component {
		state = {
			selectedPoint: null,
		};

		@autobind
		handlePointSelect({index}) {
			this.setState({
				selectedPoint: index,
			});
		}

		render() {
			const {selectedPoint} = this.state;

			return (
				<WrappedComponent
					hasSelectedPoint={
						!isNull(selectedPoint) && isFinite(selectedPoint)
					}
					onPointSelect={this.handlePointSelect}
					selectedPoint={selectedPoint}
					{...this.props}
				/>
			);
		}
	}

	return WithSelectedPoint;
}
