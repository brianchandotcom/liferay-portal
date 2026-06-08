/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import {PropTypes} from 'prop-types';
import React from 'react';
import omitDefinedProps from '~/shared/util/omitDefinedProps';

class SelectItem extends React.Component {
	render() {
		const {children, className, ...otherProps} = this.props;

		return (
			<option className={className} {...otherProps}>
				{children}
			</option>
		);
	}
}

const selectPropTypes = {
	showBlankOption: PropTypes.bool,
};

class Select extends React.Component {
	static defaultProps = {
		showBlankOption: false,
	};

	static propTypes = selectPropTypes;

	render() {
		const {children, className, showBlankOption, ...otherProps} =
			this.props;

		return (
			<select
				className={getCN('form-control select-root', className)}
				{...omitDefinedProps(otherProps, selectPropTypes)}
			>
				{showBlankOption && <SelectItem />}
				{children}
			</select>
		);
	}
}

Select.Item = SelectItem;
export default Select;
