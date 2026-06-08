/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import {isNil} from 'lodash';
import {PropTypes} from 'prop-types';
import React from 'react';
import {Link} from 'react-router-dom';
import {Routes, toRoute} from '~/shared/util/router';
import {getSafeDisplayValue} from '~/shared/util/util';

export default class SourceCell extends React.Component {
	static propTypes = {
		data: PropTypes.shape({
			dataSourceId: PropTypes.string,
			dataSourceName: PropTypes.string,
		}),
		groupId: PropTypes.string,
	};

	render() {
		const {
			className,
			data: {dataSourceId, dataSourceName},
			groupId,
		} = this.props;

		const label = getSafeDisplayValue(dataSourceName);

		return (
			<td className={getCN('table-cell-expand', className)}>
				{!isNil(dataSourceId) ? (
					<Link
						className="text-truncate"
						to={toRoute(Routes.SETTINGS_DATA_SOURCE, {
							groupId,
							id: dataSourceId,
						})}
					>
						{label}
					</Link>
				) : (
					label
				)}
			</td>
		);
	}
}
