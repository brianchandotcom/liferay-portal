import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
	plugins: [react()],
	build: {
		outDir: 'build/vite',
		rollupOptions: {
			external: [
				'react',
				'react-dom',
				'clarity-distributors-api'
			],
		}
	},
	server: {
		origin: 'http://localhost:5173',
	},
})
