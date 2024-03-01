/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const path = require('path');
const webpack = require('webpack');

const DEVELOPMENT = process.env.NODE_ENV === 'development';

module.exports = {
	devtool: DEVELOPMENT ? 'source-map' : false,
	entry: {
		global: './assets/global.css',
	},
	mode: DEVELOPMENT ? 'development' : 'production',
	module: {
		rules: [
			{
				test: /\.css$/i,
				use: [MiniCssExtractPlugin.loader, 'css-loader'],
			},
		],
	},
	optimization: {
		minimize: !DEVELOPMENT,
	},
	output: {
		clean: true,
		path: path.resolve('static'),
	},
	plugins: [
		new MiniCssExtractPlugin({
			filename: '[name].[contenthash].css',
		}),
		new webpack.optimize.LimitChunkCountPlugin({
			maxChunks: 1,
		}),
	],
};
