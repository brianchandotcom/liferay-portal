/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Pendo} from '~/shared/util/pendo';

const pendo = new Pendo();

const scripts = [

	/* Pendo */
	{
		innerHTML: pendo.script,
	},
];

/**
 * Runtime logic for adding external scripts to the page.
 */
function appendScript(options) {
	const script = document.createElement('script');

	if (options.src) {
		script.async = true;
	}

	for (const [name, value] of Object.entries(options)) {
		script[name] = value;
	}

	document.body.appendChild(script);
}

scripts.filter(({innerHTML, src}) => src || innerHTML).forEach(appendScript);
