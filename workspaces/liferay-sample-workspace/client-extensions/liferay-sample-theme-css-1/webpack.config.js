/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const fs = require('fs');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const path = require('path');
const webpack = require('webpack');
const WebpackRTLPlugin = require('webpack-rtl-plugin');

const buildCSSDir = path.join(__dirname, 'build', 'buildTheme', 'css');
const DEVELOPMENT = process.env.NODE_ENV === 'development';

const includeReferencedAsset = (assetUrl) => {
	const urlTokens = ['@base_url@', '@portal_ctx@', '@theme_image_path@'];

	if (
		!fs.existsSync(path.join(__dirname, 'src', 'css', assetUrl)) ||
		assetUrl.startsWith('data:image') ||
		urlTokens.some((token) => assetUrl.includes(token))
	) {
		return false;
	}

	return true;
};

module.exports = {
	context: buildCSSDir,
	entry: {
		clay: './clay.css',
		main: './main.css',
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
						loader: 'css-loader',
						options: {
							url: {
								filter: includeReferencedAsset,
							},
						},
					},
				],
			},
			{
				generator: {
					filename: '../assets/[name].[contenthash][ext]',
				},
				test: /\.(png|jpe?g|gif|svg|eot|ttf|woff|woff2)$/i,
				type: 'asset/resource',
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
			},
		},
		path: buildCSSDir,
	},
	plugins: [
		new MiniCssExtractPlugin({
			filename: '[name].[contenthash].css',
		}),
		new WebpackRTLPlugin({
			filename: '[name]_rtl.[contenthash].css',
		}),
		new webpack.optimize.LimitChunkCountPlugin({
			maxChunks: 1,
		}),
	],
};
