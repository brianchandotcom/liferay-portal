import react from '@vitejs/plugin-react';
import {defineConfig} from 'vite';

// https://vitejs.dev/config/

export default defineConfig({
	build: {
		outDir: 'build',
	},
	plugins: [react()],
	server: {
		origin: 'http://localhost:5173',
	},
});
