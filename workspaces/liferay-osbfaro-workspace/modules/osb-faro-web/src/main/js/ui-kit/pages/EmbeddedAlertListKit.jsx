/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import EmbeddedAlertList from '~/shared/components/EmbeddedAlertList';

export default class EmbeddedAlertListKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<EmbeddedAlertList
					alerts={[
						{
							iconSymbol: 'exclamation-full',
							message: 'foo bar',
							title: 'Test Title',
							type: 'danger',
						},
						{
							iconSymbol: 'exclamation-full',
							message: 'foo bar 2',
							title: 'Test Title 2',
							type: 'danger',
						},
					]}
				/>
			</div>
		);
	}
}
