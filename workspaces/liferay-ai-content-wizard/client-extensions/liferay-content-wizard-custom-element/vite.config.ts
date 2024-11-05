import path from 'path';
import {defineConfig, splitVendorChunkPlugin} from 'vite';
import react from '@vitejs/plugin-react';

export default defineConfig({
	build: {
		outDir: 'build/vite',
		rollupOptions: {
			output: {
				assetFileNames: 'assets/[name][extname]',
				chunkFileNames: '[name]-[hash].js',
				entryFileNames: 'main.js',
			},
		},
	},
	experimental: {
		renderBuiltUrl(filename: string) {
			return `/o/liferay-content-wizard-custom-element/${filename}`;
		},
	},
	plugins: [react(), splitVendorChunkPlugin()],
	resolve: {
		alias: {
			'~': path.resolve(__dirname, './src/'),
		},
	},
	server: {
		origin: 'http://localhost:5173',
	},
});
