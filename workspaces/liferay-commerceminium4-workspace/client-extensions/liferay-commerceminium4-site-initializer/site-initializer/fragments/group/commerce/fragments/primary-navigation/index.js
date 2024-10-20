/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MiniumPrimaryNavigation from 'minium-primary-navigation';
import React from 'react';
import ReactDOM from 'react-dom';

const navigationConfigurationContent = fragmentElement.querySelector(
	'#minium-primary-navigation-data'
).innerHTML;

ReactDOM.render(
	React.createElement(MiniumPrimaryNavigation, {
		entries: JSON.parse(navigationConfigurationContent),
		spritemap: Liferay.Icons.spritemap,
	}),
	fragmentElement
);
