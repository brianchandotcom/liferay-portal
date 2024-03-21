/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import react from '@vitejs/plugin-react';
import vitePluginRequire from "vite-plugin-require";
import {defineConfig} from 'vite';

export default defineConfig({
	build: {
		commonjsOptions: {transformMixedEsModules: true},
		outDir: 'build/vite',
		rollupOptions: {
			external: [/@clayui\/*/, 'react', 'react-dom'],
			output: {
				assetFileNames: 'assets/[name][extname]',
				chunkFileNames: '[name]-[hash].js',
				entryFileNames: '[name]-[hash].js',
			},
		},

		target: 'esnext',
	},

	plugins: [
		react({
			babel: {
				presets: [
					['@babel/preset-env', {targets: 'defaults'}],
					'@babel/preset-react','@babel/preset-typescript'
				],
				plugins: [
					[
						'@babel/plugin-transform-react-jsx',
						{runtime: 'automatic'}
					],
				],
			},
		})
	],
	server: {
		origin: 'http://localhost:5173',
	},
});
