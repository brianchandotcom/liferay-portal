/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import react from '@vitejs/plugin-react';
import {defineConfig} from 'vite';

export default defineConfig({
	build: {

		// Atlas CSS is inlined into the bundle (injected into the shadow root),
		// which exceeds the default 500 kB chunk warning.

		chunkSizeWarningLimit: 2000,
		cssCodeSplit: false,
		outDir: 'build/vite',
		rollupOptions: {
			output: {
				assetFileNames: (assetInfo) => {
					const name = assetInfo.names?.[0] ?? '';

					return name.endsWith('.css')
						? 'index.css'
						: '[name][extname]';
				},
				banner:
					'/*!\n' +
					' * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com\n' +
					' * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06\n' +
					' */',
				chunkFileNames: '[name]-[hash].js',
				entryFileNames: 'index.js',
				format: 'iife',
				inlineDynamicImports: true,
				name: 'liferayAIHubChatbot',
			},
		},
	},
	experimental: {
		renderBuiltUrl(filename: string) {
			return `/o/liferay-ai-hub-custom-element/${filename}`;
		},
	},
	plugins: [react()],
	server: {
		origin: 'http://localhost:5173',
	},
});
