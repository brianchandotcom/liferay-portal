/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import react from '@vitejs/plugin-react-swc';
import path from 'path';
import {defineConfig} from 'vite';
import {UserConfigExport} from 'vitest/config';

export default defineConfig({
	build: {
		assetsDir: 'static',
		outDir: 'build',
		rollupOptions: {
			output: {
				assetFileNames: 'static/[name].[hash][extname]',
				chunkFileNames: 'static/[name].js',
				entryFileNames: 'static/[name].js',
			},
		},
	},
	plugins: [react()],
	resolve: {
		alias: {
			'~': path.resolve(__dirname, './src/'),
		},
	},
	server: {
		port: 3000,
	},
	test: {
		coverage: {
			all: true,
			include: [path.resolve(__dirname), 'src'],
		},
		environment: 'jsdom',
		exclude: ['node_modules', 'build'],
		globals: true,
		include: ['**/(*.)?{test,spec}.{ts,tsx}'],
		setupFiles: ['./src/setupTests.ts'],
	},
} as UserConfigExport);
