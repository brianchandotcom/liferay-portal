/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fromJS} from 'immutable';

export function loadState() {
	try {
		return fromJS({
			maintenanceSeen: JSON.parse(
				atob(localStorage.getItem('maintenanceSeen'))
			),
			sidebar: JSON.parse(atob(localStorage.getItem('sidebar'))),
		});
	}
	catch (error) {
		return undefined;
	}
}

export function saveState(state) {
	try {
		localStorage.setItem(
			'maintenanceSeen',
			btoa(JSON.stringify(state.get('maintenanceSeen')))
		);

		localStorage.setItem(
			'sidebar',
			btoa(JSON.stringify(state.get('sidebar')))
		);
	}
	catch (error) {}
}
