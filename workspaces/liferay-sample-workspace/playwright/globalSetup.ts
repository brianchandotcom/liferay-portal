/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {execFileSync} from 'child_process';
import {join} from 'path';

import {liferayConfig} from './core/liferayConfig';

const COMPOSE_PROJECT_NAME = 'liferay-playwright-stack';

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
	execFileSync(
		'docker',
		[
			'compose',
			'--project-name',
			COMPOSE_PROJECT_NAME,
			'down',
			'--volumes',
		],
		{stdio: 'inherit'}
	);
}

export default async function globalSetup() {
	if (await isLiferayRunning()) {
		return;
	}

	const containerIds = execFileSync(
		'docker',
		[
			'ps',
			'--all',
			'--filter',
			'name=^database$',
			'--filter',
			'name=^liferay$',
			'--quiet',
		],
		{encoding: 'utf8'}
	);

	if (containerIds.trim()) {
		throw new Error(
			'Liferay is not running, and an existing "database" or "liferay" container prevents the tests from starting their own. Set "PORTAL_URL" to that Liferay, or remove the containers with "docker rm --force database liferay".'
		);
	}

	try {
		execFileSync(join(__dirname, '..', 'scripts', 'bootstrap.sh'), {
			env: {
				...process.env,
				COMPOSE_PROJECT_NAME,
				LIFERAY_COMPOSE_OVERRIDES: 'playwright',
				LIFERAY_HTTP_PORT: port,
				LIFERAY_IMAGE: 'liferay:playwright',
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
