/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import axios from 'axios';

import config from '../util/configTreePath.js';

const lxcDXPMainDomain =
	config['com.liferay.lxc.dxp.mainDomain'] ||
	config['com.liferay.sh.dxp.mainDomain'];
const lxcDXPServerProtocol =
	config['com.liferay.lxc.dxp.server.protocol'] ||
	config['com.liferay.sh.dxp.server.protocol'];
const oauth2JWKSURI = `${lxcDXPServerProtocol}://${lxcDXPMainDomain}`;

export function headless_admin_userService(_token) {
	const token = _token;
	function getUserAccountsPage(filter = '', page = 0) {
		const prom = new Promise((resolve, reject) => {
			filter = filter === '' ? '' : `&${filter}`;
			const config = {
				headers: {
					Authorization: `${token}`,
				},
				maxBodyLength: Infinity,
				method: 'get',
				url: `${oauth2JWKSURI}/o/headless-admin-user/v1.0/user-accounts?page=${page}${filter}`,
			};
			axios
				.request(config)
				.then((response) => {
					resolve(response.data);
				})
				.catch((error) => {
					reject(error.message);
				});
		});

		return prom;
	}

	return Object.freeze({
		getUserAccountsPage,
	});
}
