/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* eslint-disable no-console */

import {execa} from 'execa';

const jsGlobs = '**/*.{js,jsx,mjs,ts,tsx}';
const cssGlobs = '**/*.{css,scss}';

const options = {
	all: true,
	reject: false,
};

switch (process.argv[2]) {
	case 'checkFormat': {
		console.log('Running prettier...');

		const result = await execa({
			all: true,
			reject: false,
		})`prettier ${jsGlobs} ${cssGlobs} --check`;

		if (result.failed) {
			throw new Error(result.all);
		}

		console.log('Running eslint and stylelint...');

		const [eslintResult, stylelintResult] = await Promise.all([
			execa(options)`eslint ${jsGlobs}`,
			execa(options)`stylelint ${cssGlobs}`,
		]);

		if (eslintResult.failed) {
			throw new Error(eslintResult.all);
		}

		if (stylelintResult.failed) {
			throw new Error(stylelintResult.all);
		}

		break;
	}
	case 'format': {
		await execa({
			stdio: 'inherit',
		})`prettier ${jsGlobs} ${cssGlobs} --write`;

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
