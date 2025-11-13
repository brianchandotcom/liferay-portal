import { resolve } from 'path';
import { defineConfig } from 'vite';

// https://vitejs.dev/config/
export default defineConfig({
	build: {
		lib: {
			entry: {
				'index': resolve(__dirname, 'src/index.js'),
			},
			formats: ['es'],
		},
		outDir: 'build/vite',
		rollupOptions: {

			output: {
				entryFileNames: '[name].js',
				format: 'es',
			},
		}
	},
	define: {
		'process.env.NODE_ENV': '"production"',
	}
})