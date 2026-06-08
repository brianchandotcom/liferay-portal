/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

class ModalInfoBar extends React.Component {
	render() {
		return (
			<div
				className={`modal-info-bar-root${
					this.props.className ? ` ${this.props.className}` : ''
				}`}
			>
				{this.props.children}
			</div>
		);
	}
}

export default ModalInfoBar;
