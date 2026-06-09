/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const webpack = require('webpack');
const {merge} = require('webpack-merge');

const common = require('./webpack.common');
const {createOnProxyReq, createOnProxyRes} = require('./webpack.dev.proxy');

require('dotenv').config();

const TARGET = (process.env.FARO_URL || 'http://0.0.0.0:8080').replace(
	/\/$/,
	''
);

const COOKIE = process.env.FARO_COOKIE || '';

module.exports = merge(common.config, {
	devServer: {
		client: {
			overlay: false,
		},
		host: '0.0.0.0',
		port: 3000,
		proxy: {
			'**': {
				changeOrigin: true,
				onProxyReq: createOnProxyReq(COOKIE),
				onProxyRes: createOnProxyRes(TARGET),
				selfHandleResponse: true,
				target: TARGET,
			},
		},
	},
	devtool: 'eval-source-map',
	mode: 'development',
	module: {
		rules: [
			{
				include: common.include,
				loader: 'liferay-lang-key-dev-loader',
				test: /\.(js|ts)x?$/,
			},
		],
	},
	output: {
		chunkFilename: '[name].[chunkhash:8].js',
		publicPath: common.PUBLIC_PATH,
	},
	plugins: [
		new webpack.DefinePlugin({
			FARO_DEV_MODE: true,
		}),
	],
});
