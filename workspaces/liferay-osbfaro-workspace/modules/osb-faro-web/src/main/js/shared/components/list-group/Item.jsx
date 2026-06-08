/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import {PropTypes} from 'prop-types';
import React from 'react';
import omitDefinedProps from '~/shared/util/omitDefinedProps';

const itemPropTypes = {
	accentColor: PropTypes.string,
	action: PropTypes.bool,
	active: PropTypes.bool,
	disabled: PropTypes.bool,
	flex: PropTypes.bool,
	header: PropTypes.bool,
};

export default class Item extends React.Component {
	static defaultProps = {
		action: false,
		active: false,
		disabled: false,
		flex: false,
		header: false,
	};

	static propTypes = itemPropTypes;

	render() {
		const {
			accentColor,
			action,
			active,
			children,
			className,
			disabled,
			flex,
			header,
			...otherProps
		} = this.props;

		const classes = getCN(className, {
			active,
			'list-group-header': header,
			'list-group-item': !header,
			'list-group-item-action': action && !disabled,
			'list-group-item-disabled': disabled,
			'list-group-item-flex': flex,
		});

		const style = {
			borderLeft: `4px solid ${accentColor}`,
		};

		return (
			<li
				{...omitDefinedProps(otherProps, itemPropTypes)}
				className={classes}
				style={accentColor ? style : undefined}
			>
				{children}
			</li>
		);
	}
}
