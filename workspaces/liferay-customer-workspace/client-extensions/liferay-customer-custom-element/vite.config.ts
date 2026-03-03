/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import react from '@vitejs/plugin-react-swc';
import path from 'path';
import {defineConfig} from 'vite';

export default defineConfig(({command}) => ({
	build: {
		assetsDir: 'static',
		outDir: 'build',
		rollupOptions: {
			external: ['@liferay/oauth2-provider-web/client'],
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
		exclude: ['@liferay/oauth2-provider-web/client'],
	},
	plugins: [react()],
	resolve: {
		alias: {
			...(command === 'serve'
				? {
						'@liferay/oauth2-provider-web/client': path.resolve(
							__dirname,
							'./dev-stubs/oauth2-stub.ts'
						),
					}
				: {}),
			'~': path.resolve(__dirname, './src/'),
		},
	},
	server: {
		port: 3000,
	},
}));
