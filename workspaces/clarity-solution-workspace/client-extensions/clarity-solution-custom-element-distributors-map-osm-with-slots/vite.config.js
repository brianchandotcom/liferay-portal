/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import react from '@vitejs/plugin-react';
import {resolve} from 'path';
import {defineConfig} from 'vite';

// https://vitejs.dev/config/

// Builds a single ES module, index.js, which the import map entry points to.
// React stays external because Liferay's import map already provides it.

export default defineConfig({
	build: {
		lib: {
			entry: {
				index: resolve(import.meta.dirname, 'src/main.jsx'),
			},
			formats: ['es'],
		},
		outDir: 'build/vite',
		rollupOptions: {
			external: ['react', 'react-dom'],
			output: {
				entryFileNames: '[name].js',
				format: 'es',
			},
		},
	},

	// The bundled JSX runtime reads process.env.NODE_ENV, which a library build
	// leaves as is

	define: {
		'process.env.NODE_ENV': '"production"',
	},
	plugins: [react()],
	server: {
		origin: 'http://localhost:5173',
	},
});
