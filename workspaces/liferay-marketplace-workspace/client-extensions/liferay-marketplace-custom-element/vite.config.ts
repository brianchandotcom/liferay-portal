/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import react from '@vitejs/plugin-react';
import {defineConfig} from 'vite';


export default defineConfig({
	build: {
		outDir: 'build/vite',
		rollupOptions: {
			output: {
				assetFileNames: 'assets/[name][extname]',
				chunkFileNames: '[name]-[hash].js',
				entryFileNames: '[name]-[hash].js',
			},
		},
	},
	experimental: {
		renderBuiltUrl(filename: string) {
			if (
				filename.endsWith('.css') ||
				filename.endsWith('.png') ||
				filename.endsWith('.svg')
			) {
				return `/o/liferay-marketplace-custom-element/${filename}`;
			}

			return filename;
		},
	},
	plugins: [react()]
});
