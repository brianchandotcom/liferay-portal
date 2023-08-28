/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

module.exports = class SocketClient {
	constructor(id, name, avatar) {
		this.clientId = id;
		this.clientName = name;
		this.clientAvatar = avatar;
	}
};
