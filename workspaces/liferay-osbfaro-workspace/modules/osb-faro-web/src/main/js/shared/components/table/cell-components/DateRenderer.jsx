/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {get} from 'lodash';
import moment from 'moment';
import {PropTypes} from 'prop-types';
import React from 'react';

export default class DateRenderer extends React.Component {
	static defaultProps = {
		dateFormatter: (date) => moment(date).format('ll'),
		datePath: 'dateCreated',
	};

	static propTypes = {
		data: PropTypes.object.isRequired,
		dateFormatter: PropTypes.func,
		datePath: PropTypes.oneOfType([PropTypes.array, PropTypes.string]),
	};

	render() {
		const {className, data, dateFormatter, datePath} = this.props;

		const date = get(data, datePath);

		const formattedDate = date ? dateFormatter(date) : '-';

		return <td className={className}>{formattedDate}</td>;
	}
}
