/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import react from '@vitejs/plugin-react';
import {defineConfig} from 'vite';

const SERVER_PORT = 3000;

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
	plugins: [react()],
	server: {
		origin: `http://localhost:${SERVER_PORT}`,
		port: SERVER_PORT,
	},
});
