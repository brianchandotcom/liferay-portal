/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import {PropTypes} from 'prop-types';
import React, {Component} from 'react';

export default class DatePickerSelect extends Component {
	static propTypes = {
		className: PropTypes.string,
		onChange: PropTypes.func.isRequired,
		options: PropTypes.arrayOf(
			PropTypes.shape({
				label: PropTypes.oneOfType([
					PropTypes.number,
					PropTypes.string,
				]),
				value: PropTypes.oneOfType([PropTypes.number, PropTypes.string])
					.isRequired,
			})
		).isRequired,
		selected: PropTypes.string,
	};

	render() {
		const {className, options, selected, ...otherProps} = this.props;

		const classes = getCN(
			'date-picker-select-root',
			'form-control',
			className
		);

		return (
			<select className={classes} {...otherProps} value={selected}>
				{options.map(({label, value}, index) => (
					<option key={index} value={value}>
						{label ? label : value}
					</option>
				))}
			</select>
		);
	}
}
