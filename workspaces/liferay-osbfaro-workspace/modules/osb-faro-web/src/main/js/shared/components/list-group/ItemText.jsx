/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import {PropTypes} from 'prop-types';
import React from 'react';
import omitDefinedProps from '~/shared/util/omitDefinedProps';

export default class ItemText extends React.Component {
	static defaultProps = {
		subtext: false,
	};

	static propTypes = {
		subtext: PropTypes.bool,
	};

	render() {
		const {children, className, subtext, ...otherProps} = this.props;

		const classes = getCN(className, {
			'list-group-subtext': subtext,
			'list-group-text': !subtext,
		});

		return (
			<p
				{...omitDefinedProps(otherProps, ItemText.propTypes)}
				className={classes}
			>
				{children}
			</p>
		);
	}
}
