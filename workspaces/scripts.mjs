/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* eslint-disable no-console */

import {execa} from 'execa';

const jsGlobs = '**/*.{js,jsx,mjs,ts,tsx}';
const cssGlobs = '**/*.{css,scss}';

const options = {stderr: process.stdout, stdout: process.stdout};

switch (process.argv[2]) {
	case 'checkFormat': {
		await execa(options)`prettier ${jsGlobs} ${cssGlobs} --check`;

		console.log('Running eslint and stylelint...');

		const [eslintResult, stylelintResult] = await Promise.all([
			execa`eslint ${jsGlobs}`,
			execa`stylelint ${cssGlobs}`,
		]);

		console.log(eslintResult.stdout);
		console.log(stylelintResult.stdout);

		break;
	}
	case 'format': {
		await execa(options)`prettier ${jsGlobs} ${cssGlobs} --write`;

		console.log('Running eslint and stylelint...');

		const [eslintResult, stylelintResult] = await Promise.all([
			execa`eslint ${jsGlobs} --fix`,
			execa`stylelint ${cssGlobs} --fix`,
		]);

		console.log(eslintResult.stdout);
		console.log(stylelintResult.stdout);

		break;
	}
	default: {
		console.log(
			'Expected either "format" or "checkFormat" as the first argument.'
		);
	}
}
