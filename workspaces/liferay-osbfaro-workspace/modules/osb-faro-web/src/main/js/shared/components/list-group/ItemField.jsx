/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import {PropTypes} from 'prop-types';
import React from 'react';
import omitDefinedProps from '~/shared/util/omitDefinedProps';

export default class ItemField extends React.Component {
	static defaultProps = {
		expand: false,
	};

	static propTypes = {
		expand: PropTypes.bool,
	};

	render() {
		const {children, className, expand, ...otherProps} = this.props;

		const classes = getCN('autofit-col', className, {
			'autofit-col-expand': expand,
		});

		return (
			<div
				{...omitDefinedProps(otherProps, ItemField.propTypes)}
				className={classes}
			>
				{children}
			</div>
		);
	}
}
