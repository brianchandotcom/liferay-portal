/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

interface IConfig {
	[key: string]: any;
}

interface IEditorConfigTransformer {
	(args: IConfig): IConfig;
}

const editorConfigTransformer: IEditorConfigTransformer = (config) => {
	const toolbar: [string[]] = config.toolbar_liferay;

	toolbar.push(['ImageSelector']);

	return {
		...config,
		extraPlugins: 'itemselector',
		toolbar_liferay: toolbar,
	};
};

export default editorConfigTransformer;
