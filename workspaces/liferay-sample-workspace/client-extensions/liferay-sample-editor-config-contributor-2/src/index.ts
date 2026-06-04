/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// The version of this plugin must match the version DXP ships (see
// frontend-editor-ckeditor-web/package.json). Keeping them in sync provides
// correct types at dev time and avoids runtime mismatches.
//
// The import path must match the key DXP exposes in its CKEditor 5 import
// map so that the bundler resolves this to the shared copy at runtime instead
// of packaging a duplicate. The build marks it external for the same reason.

import {WordCount} from '@ckeditor/ckeditor5-word-count/dist/index.js';
import {
	EditorConfigTransformer,
	EditorTransformer,
} from '@liferay/js-api/editor';

const editorConfigTransformer: EditorConfigTransformer<any> = (config) => {
	let displayEl: HTMLElement | null = null;

	return {
		...config,
		extraPlugins: [...(config.extraPlugins ?? []), WordCount],
		wordCount: {
			onUpdate: ({
				characters,
				words,
			}: {
				characters: number;
				words: number;
			}) => {
				if (!displayEl) {
					displayEl = document.createElement('div');
					displayEl.className = 'mt-1 text-secondary';

					document
						.querySelector('.ck-editor__editable')
						?.closest('.ck-editor')
						?.parentElement?.appendChild(displayEl);
				}

				displayEl.textContent = `Words: ${words} | Characters: ${characters}`;
			},
		},
	};
};

const editorTransformer: EditorTransformer<any> = {
	editorConfigTransformer,
};

export default editorTransformer;
