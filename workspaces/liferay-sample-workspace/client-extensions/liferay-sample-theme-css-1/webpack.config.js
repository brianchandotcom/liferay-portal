/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const fs = require('fs');
const path = require('path');
const webpack = require('webpack');

const buildCSSDir = path.join(__dirname, 'build', 'buildTheme', 'css');
const DEVELOPMENT = process.env.NODE_ENV === 'development';

module.exports = {
	context: buildCSSDir,
	entry: {
		clay: './clay.css',
		main: './main.css'
	},
	mode: DEVELOPMENT ? 'development' : 'production',
	module: {
		rules: [
			{
				test: /\.css$/i,
				use: [
					{
						loader: MiniCssExtractPlugin.loader,
					},
					{
						loader: "css-loader",
					}
				]
			},
		],
	},
	optimization: {
		minimize: !DEVELOPMENT,
	},
	output: {
		clean: {
			keep(asset) {
				return !['main', 'clay'].some((file) => asset.includes(file));
			}
		},
		path: buildCSSDir
	},
	plugins: [
		new MiniCssExtractPlugin({
			filename: "[name].[contenthash].css"
		}),
		new webpack.optimize.LimitChunkCountPlugin({
			maxChunks: 1,
		})
	]
};