/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Runs the portal source formatter (node-scripts: prettier + eslint + stylelint)
 * over this module's files. Delegates to node-scripts' own `formatSourceFiles`,
 * so it is byte-consistent with the CI `packageRunCheckFormat` check, but skips
 * the project-mode TypeScript compile pass (which CI also excludes for workspace
 * files and which is incompatible with this module's TS 5 setup).
 *
 * Usage: node format.mjs [--check]
 */

import {execFileSync} from 'child_process';
import path from 'path';
import url from 'url';

const __dirname = path.dirname(url.fileURLToPath(import.meta.url));
const PORTAL_DIR = path.resolve(__dirname, '..', '..', '..', '..');

const formatSourceFiles = (
	await import(
		url.pathToFileURL(
			path.join(
				PORTAL_DIR,
				'modules/frontend-sdk/node-scripts/util/format/formatters/formatSourceFiles.mjs'
			)
		)
	)
).default;

const check = process.argv.includes('--check');

// Tracked files plus untracked files that are not gitignored, so newly added
// source files are formatted before they are committed.

const trackedFiles = execFileSync(
	'git',
	['ls-files', '--cached', '--others', '--exclude-standard'],
	{
		cwd: __dirname,
		encoding: 'utf-8',
		maxBuffer: 1024 * 1024 * 64,
	}
)
	.trim()
	.split('\n')
	.filter(Boolean)
	.map((file) => path.relative(PORTAL_DIR, path.resolve(__dirname, file)));

// formatSourceFiles filters to the formatter's own extension allow-list
// (graphql/js/jsp/jspf/mjs/scss/ts/tsx), so .jsx and assets are dropped exactly
// as the CI formatter drops them.

const checksPassed = await formatSourceFiles(check, trackedFiles);

process.exit(checksPassed ? 0 : 1);
