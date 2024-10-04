/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import react from '@vitejs/plugin-react-swc';
import path from 'path';
import {UserConfigExport, defineConfig} from 'vite';

export default defineConfig({
	build: {
		assetsDir: 'static',
		outDir: 'build',
		rollupOptions: {
			output: {
				assetFileNames: 'static/[name].[hash][extname]',
				chunkFileNames: 'static/[name].[hash].js',
				entryFileNames: 'static/[name].[hash].js',
			},
		},
	},
	optimizeDeps: {
		esbuildOptions: {
			define: {
				global: 'globalThis',
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
} as UserConfigExport);
