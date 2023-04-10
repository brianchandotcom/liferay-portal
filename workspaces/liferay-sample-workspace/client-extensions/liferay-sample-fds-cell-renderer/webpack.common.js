import path from 'path';
import { fileURLToPath } from 'url';

const __dirname = path.dirname(fileURLToPath(import.meta.url));

export default {
	entry: "./src/index.tsx",
	module: {
		rules: [
			{
				test: /\.js$/,
				exclude: /node_modules/,
				use: {
					loader: 'babel-loader',
				},
			},
			{
				test: /\.tsx?$/,
				exclude: [/node_modules/],
				use: {
					loader: 'ts-loader'
				},
			},
		],
	},
	experiments: {
		outputModule: true,
	},
	output: {
		filename: 'index.js',
		environment: {
			dynamicImport: true,
			module: true,
		},
		library: {
			type: 'module',
		},
		path: path.resolve(__dirname, 'build'),
		module: true,
	},
};