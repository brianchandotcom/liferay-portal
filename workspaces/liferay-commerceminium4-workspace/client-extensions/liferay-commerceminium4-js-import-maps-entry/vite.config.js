/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import react from '@vitejs/plugin-react';
import {resolve} from 'path';

/* eslint-disable no-undef */

import {defineConfig} from 'vite';

// https://vitejs.dev/config/

export default defineConfig({
	base: '/o/vite-project',
	build: {
		lib: {
			entry: resolve(__dirname, 'src/index.js'),
			formats: ['es'],
		},
		outDir: 'build/static',

		rollupOptions: {
			external: [/@clayui\/*/, 'react', 'react-dom'],

			output: {
				assetFileNames: '[name].[ext]',
				chunkFileNames: '[name].js',
				entryFileNames: '[name].js',
			},
		},
	},
	plugins: [react({jsxRuntime: 'classic'})],
});
