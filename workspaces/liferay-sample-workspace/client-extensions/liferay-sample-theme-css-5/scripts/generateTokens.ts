/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as fs from 'fs/promises';
import * as path from 'path';

const INPUT_PATH = path.resolve('./exported-design-tokens.json');
const OUTPUT_PATH = path.resolve(
	'./build/generateTokens/frontend-token-definition.json'
);
const OUTPUT_PATH_SASS = path.resolve('./src/css/_exported-design-tokens.scss');

type IFigmaToken = {
	type: 'dimension' | 'color';
	value: string;
};

type IFigmaTokenMap = {
	[key: string]: IFigmaToken | IFigmaTokenMap;
};

type IFrontendToken = {
	defaultValue: string;
	editorType?: string;
	label: string;
	mappings: [
		{
			type: 'cssVariable';
			value: string;
		},
	];
	name: string;
	type: 'String';
};

type IFrontendTokenCategory = {
	frontendTokenSets: IFrontendTokenSet[];
	label: string;
	name: string;
};

type IFrontendTokenDefinition = {
	frontendTokenCategories: IFrontendTokenCategory[];
};

type IFrontendTokenSet = {
	frontendTokens: IFrontendToken[];
	label: string;
	name: string;
};

function getFrontendToken(key: string, token: IFigmaToken): IFrontendToken {
	const {type, value} = token;

	let editorType;

	if (type === 'color') {
		editorType = 'ColorPicker';
	}
	else if (type === 'dimension') {
		editorType = 'Length';
	}

	return {
		defaultValue: type === 'dimension' ? `${value}px` : value,
		...(editorType && {editorType}),
		label: key,
		mappings: [
			{
				type: 'cssVariable',
				value: key,
			},
		],
		name: key,
		type: 'String',
	};
}

function getFrontendTokens(
	frontendTokenSet,
	frontendTokens: IFrontendToken[] = []
): IFrontendToken[] {
	if (nextLevelHasTokens(frontendTokenSet)) {
		Object.keys(frontendTokenSet).forEach((key) => {
			const token: IFigmaToken = frontendTokenSet[key];

			frontendTokens.push(getFrontendToken(key, token));
		});
	}
	else {
		Object.keys(frontendTokenSet).forEach((key) => {
			frontendTokens = [
				...frontendTokens,
				...getFrontendTokens(frontendTokenSet[key]),
			];
		});
	}

	return frontendTokens;
}

function getFrontendTokenSets(
	frontendTokenCategory: IFigmaTokenMap,
	key: string
): IFrontendTokenSet[] {
	const frontendTokenSets = [];

	if (nextLevelHasTokens(frontendTokenCategory)) {
		frontendTokenSets.push({
			frontendTokens: getFrontendTokens(frontendTokenCategory),
			label: key,
			name: `${key}Set`,
		});
	}
	else {
		Object.keys(frontendTokenCategory).forEach((key) => {
			const frontendTokenSet = frontendTokenCategory[key];

			frontendTokenSets.push({
				frontendTokens: getFrontendTokens(frontendTokenSet),
				label: key,
				name: key,
			});
		});
	}

	return frontendTokenSets;
}

function getFrontendTokenCategories(
	data: IFigmaTokenMap
): IFrontendTokenCategory[] {
	const frontendTokenCategories = [];

	Object.keys(data).forEach((key) => {
		const frontendTokenCategory = data[key] as IFigmaTokenMap;

		frontendTokenCategories.push({
			frontendTokenSets: getFrontendTokenSets(frontendTokenCategory, key),
			label: key,
			name: key,
		});
	});

	return frontendTokenCategories;
}

function getFrontendTokenDefinition(
	data: IFigmaTokenMap
): IFrontendTokenDefinition {
	return {
		frontendTokenCategories: getFrontendTokenCategories(
			data['lexicon foundations'] as IFigmaTokenMap
		),
	};
}

function getSassVariables(frontendTokenDefinition: IFrontendTokenDefinition) {
	let sassVariables = '';

	frontendTokenDefinition.frontendTokenCategories.forEach(
		(frontendTokenCategory, categoryIndex) => {
			sassVariables = sassVariables.concat(
				`// Category: ${frontendTokenCategory.label}\n\n`
			);

			frontendTokenCategory.frontendTokenSets.forEach(
				(frontendTokenSet) => {
					sassVariables = sassVariables.concat(
						`// Set: ${frontendTokenSet.label}\n\n`
					);

					frontendTokenSet.frontendTokens.forEach(
						(frontendToken, index) => {
							sassVariables = sassVariables.concat(
								`$${frontendToken.name}: var(--${frontendToken.mappings[0].value});\n`
							);

							if (
								frontendTokenSet.frontendTokens.length - 1 ===
									index &&
								frontendTokenDefinition.frontendTokenCategories
									.length -
									1 !==
									categoryIndex
							) {
								sassVariables = sassVariables.concat('\n');
							}
						}
					);
				}
			);
		}
	);

	return sassVariables;
}

function nextLevelHasTokens(object: IFigmaTokenMap) {
	const objectKeys = Object.keys(object);

	return (
		!!objectKeys.length &&
		Object.keys(object[objectKeys[0]]).includes('value')
	);
}

async function main() {
	try {
		const raw = await fs.readFile(INPUT_PATH, 'utf8');
		const data: IFigmaTokenMap = JSON.parse(raw);

		const frontendTokenDefinition = getFrontendTokenDefinition(data);

		await fs.writeFile(
			OUTPUT_PATH,
			JSON.stringify(frontendTokenDefinition, null, '\t'),
			'utf8'
		);

		// eslint-disable-next-line no-console
		console.log(`Wrote: ${OUTPUT_PATH}`);

		const sassVariables = getSassVariables(frontendTokenDefinition);

		await fs.writeFile(OUTPUT_PATH_SASS, sassVariables, 'utf8');

		// eslint-disable-next-line no-console
		console.log(`Wrote: ${OUTPUT_PATH_SASS}`);
	}
	catch (error) {
		console.error('Error:', error);
		process.exitCode = 1;
	}
}

main();
