/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import React from 'react';

import Item from '../components/Item';
import Row from '../components/Row';

const SIZES = ['sm', 'md', 'lg', 'xl', 'xxl', 'xxxl'];

const IconKit = () => (
	<div>
		<Row>
			{SIZES.map((size, index) => (
				<Item key={index}>
					<ClayIcon
						className={`icon-root icon-size-${size}`}
						symbol="ac_star"
					/>
				</Item>
			))}
		</Row>

		<Row>
			{SIZES.map((size, index) => (
				<Item key={index}>
					<ClayIcon
						className={`icon-root icon-size-${size}`}
						symbol="ac_star"
					/>
				</Item>
			))}
		</Row>
	</div>
);

export default IconKit;
