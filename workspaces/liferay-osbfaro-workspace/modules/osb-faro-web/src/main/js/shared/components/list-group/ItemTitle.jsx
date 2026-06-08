/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

export default class ItemTitle extends React.Component {
	render() {
		const {children, className, ...otherProps} = this.props;

		return (
			<div
				{...otherProps}
				className={`h4 list-group-title${
					className ? ` ${className}` : ''
				}`}
			>
				{children}
			</div>
		);
	}
}
