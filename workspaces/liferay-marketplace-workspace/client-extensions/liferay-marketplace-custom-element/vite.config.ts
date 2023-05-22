import react from "@vitejs/plugin-react";
import { defineConfig } from "vite";

// https://vitejs.dev/config/

export default defineConfig({
  build: {
    outDir: "build/vite",
    rollupOptions: {
      output: {
        assetFileNames: "marketplace-custom-element-assets/[name][extname]",
        chunkFileNames: "[name]-[hash].js",
        entryFileNames: "[name]-[hash].js",
      },
    },
  },
  plugins: [react()],
  server: {
    port: 3000,
  },
});
