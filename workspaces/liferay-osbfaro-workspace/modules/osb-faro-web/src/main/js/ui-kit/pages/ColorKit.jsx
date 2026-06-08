/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import Item from '../components/Item';
import Row from '../components/Row';

const COLORS = [
	'main',
	'mainLighten4',
	'mainLighten8',
	'mainLighten28',
	'mainLighten52',
	'mainLighten65',
	'mainLighten74',
	'primaryLighten23',
	'primaryLighten33',
	'primaryLighten45',
	'primary',
	'primaryDarken5',
	'primaryDarken10',
	'white',
	'lightBackground',
	'error',
	'errorLighten28',
	'errorLighten50',
	'warning',
	'warningLighten25',
	'warningLighten60',
	'info',
	'infoLighten28',
	'infoLighten53',
	'limegreen',
	'seagreen',
];

class ColorKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Row>
					{COLORS.map((color, index) => (
						<Item key={index}>
							<div className={`color-swatch ${color}`}>
								<div className="color-display" />
								<p className="color-label">{color}</p>
							</div>
						</Item>
					))}
				</Row>
			</div>
		);
	}
}

export default ColorKit;
