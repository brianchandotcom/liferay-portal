/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	EditorConfigTransformer,
	EditorTransformer,
} from '@liferay/js-api/editor';

const STYLE_ELEMENT_ID = 'liferay-sample-editor-config-contributor-4-styles';

const editorConfigTransformer: EditorConfigTransformer<any> = (config) => {
	if (!document.getElementById(STYLE_ELEMENT_ID)) {
		const styleEl = document.createElement('style');

		styleEl.id = STYLE_ELEMENT_ID;
		styleEl.textContent = `
			.ck-content .featured-content {
				background-color: #e8f4fd;
				border-left: 4px solid #2196f3;
				border-radius: 0 4px 4px 0;
				padding: 8px 16px;
			}
		`;

		document.head.appendChild(styleEl);
	}

	const toolbar = config.toolbar as any;
	const existingItems: string[] = Array.isArray(toolbar)
		? toolbar
		: toolbar?.items ?? [];

	return {
		...config,
		style: {
			definitions: [
				...((config as any).style?.definitions ?? []),
				{
					classes: ['featured-content'],
					element: 'p',
					name: 'Featured Content',
				},
			],
		},
		toolbar: {
			items: existingItems.includes('style')
				? existingItems
				: [...existingItems, '|', 'style'],
		},
	};
};

const editorTransformer: EditorTransformer<any> = {
	editorConfigTransformer,
};

export default editorTransformer;
