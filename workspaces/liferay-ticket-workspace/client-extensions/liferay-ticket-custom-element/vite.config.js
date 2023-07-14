import eslint from 'vite-plugin-eslint';
import react from '@vitejs/plugin-react';
import {defineConfig} from 'vite';

// https://vitejs.dev/config/

export default defineConfig({
	build: {
		outDir: 'build',
	},
	plugins: [eslint(), react()],
	server: {
		origin: 'http://localhost:5173',
	},
});
