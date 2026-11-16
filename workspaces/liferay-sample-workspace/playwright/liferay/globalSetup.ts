/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {execFileSync} from 'child_process';
import {join} from 'path';

import {liferayConfig} from './core/liferayConfig';

const {baseUrl, port} = liferayConfig.environment;

async function isLiferayRunning() {
	try {
		const response = await fetch(`${baseUrl}/c/portal/status`);

		return response.ok;
	}
	catch {
		return false;
	}
}

function stopLiferay() {
	execFileSync(join(__dirname, 'stop.sh'), {stdio: 'inherit'});
}

export default async function globalSetup() {
	if (await isLiferayRunning()) {
		return;
	}

	try {
		execFileSync(join(__dirname, 'start.sh'), {
			env: {
				...process.env,
				LIFERAY_HTTP_PORT: port,
			},
			stdio: 'inherit',
		});
	}
	catch (error) {
		stopLiferay();

		throw error;
	}

	return stopLiferay;
}
