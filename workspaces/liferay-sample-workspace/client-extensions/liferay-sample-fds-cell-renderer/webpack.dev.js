import { merge } from 'webpack-merge';
import common from './webpack.common.js';

const port = 3000;

export default merge(common, {
	mode: 'development',
	devtool: 'inline-source-map',
	devServer: {
		allowedHosts:'all',
		port: port,
		hot: true,
		headers: {
			'Access-Control-Allow-Origin': '*',
			'Access-Control-Allow-Methods': '*',
			'Access-Control-Allow-Headers':
				'X-Requested-With, content-type, Authorization',
		},
		liveReload: true,
		watchFiles: [
			'src/**/*.tsx',
			'src/**/*.ts',
			'src/**/*.js',
			'src/**/*.jsx',
		],
		open: false,
	},
	output: {
		publicPath: `http://localhost:${port}/`,
	},
});
