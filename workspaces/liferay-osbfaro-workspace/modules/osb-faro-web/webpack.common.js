/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const AutoprefixerPlugin = require('autoprefixer');
const ForkTsCheckerWebpackPlugin = require('fork-ts-checker-webpack-plugin');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const path = require('path');
const NormalizeCharsetPlugin = require('postcss-normalize-charset');
const SpriteLoaderPlugin = require('svg-sprite-loader/plugin');
const webpack = require('webpack');

const PUBLIC_PATH = '/o/osb-faro-web/dist/';

function resolveModule(name = '') {
	return path.resolve(__dirname, 'src', 'main', 'js', name);
}

const include = [resolveModule()];

const config = {
	entry: [
		'core-js/fn/array/fill',
		'core-js/fn/string/code-point-at',
		'core-js/fn/typed',
		'custom-event-polyfill',
		'unorm',
		'whatwg-fetch',
		resolveModule('main.jsx'),
	],
	experiments: {
		outputModule: true,
	},
	externals: [
		({request}, callback) => {
			if (
				request?.startsWith('@clayui/') &&
				!request?.startsWith('@clayui/css')
			) {
				return callback(null, request);
			}
			callback();
		},
		{
			'@liferay/frontend-data-set-web':
				'/o/frontend-data-set-web/__liferay__/index.js',
			'react': 'react',
			'react-dom': 'react-dom',
		},
	],
	externalsType: 'module',
	module: {
		rules: [
			{
				include,
				loader: 'ts-loader',
				options: {
					transpileOnly: true,
				},
				resolve: {
					alias: {
						'~': resolveModule(),
					},
					extensions: ['.js', '.jsx', '.ts', '.tsx'],
				},
				test: /\.(js|ts)x?$/,
			},
			{
				loader: 'graphql-tag/loader',
				test: /\.graphql$/,
			},
			{
				test: /\.css$/i,
				use: [
					MiniCssExtractPlugin.loader,
					'css-loader',
					{
						loader: 'postcss-loader',
						options: {
							postcssOptions: {
								ident: 'postcss',
								plugins: () => [
									NormalizeCharsetPlugin,
									AutoprefixerPlugin,
								],
								sourceMap: true,
							},
						},
					},
				],
			},
			{
				include: path.resolve(__dirname, 'src', 'main', 'css'),
				test: /\.scss$/,
				use: [
					MiniCssExtractPlugin.loader,
					{
						loader: 'css-loader',
						options: {
							importLoaders: 2,
						},
					},
					{
						loader: 'postcss-loader',
						options: {
							postcssOptions: {
								ident: 'postcss',
								plugins: () => [AutoprefixerPlugin],
								sourceMap: true,
							},
						},
					},
					{
						loader: 'sass-loader',
						options: {
							api: 'modern',
							implementation: require('sass'),
							sassOptions: {
								loadPaths: [
									path.resolve(__dirname, 'node_modules'),
								],
								quietDeps: true,
								silenceDeprecations: [
									'import',
									'global-builtin',
								],
							},
							sourceMap: true,
						},
					},
				],
			},
			{
				test: /\.svg$/,
				use: [
					{
						loader: 'svg-sprite-loader',
						options: {
							extract: true,
							spriteFilename: 'sprite.svg',
						},
					},
					{
						loader: 'svgo-loader',
						options: {
							plugins: [
								{removeDimensions: true},
								{removeUselessStrokeAndFill: false},
								{removeViewBox: false},
							],
						},
					},
				],
			},
			{
				test: /\.(eot|ttf|woff|woff2)(\?v=\d+\.\d+\.\d+)?$/,
				use: 'file-loader',
			},
		],
	},
	output: {
		filename: 'main.js',
		module: true,
		path: path.resolve('src/main/resources/META-INF/resources/dist'),
		pathinfo: false,
		publicPath: PUBLIC_PATH,
	},
	plugins: [
		new MiniCssExtractPlugin({
			filename: 'main.css',
		}),
		new ForkTsCheckerWebpackPlugin({
			issue: {
				include: [{file: '**/src/main/js/**/*'}],
			},
			logger: 'webpack-infrastructure',
		}),
		new SpriteLoaderPlugin(),
		new webpack.DefinePlugin({
			FARO_ENV: JSON.stringify(process.env.FARO_ENVIRONMENT_NAME || ''),
		}),
		new webpack.DefinePlugin({
			FARO_PENDO_API_KEY: JSON.stringify(
				process.env.FARO_PENDO_API_KEY || ''
			),
		}),
		new webpack.IgnorePlugin({
			contextRegExp: /moment$/,
			resourceRegExp: /^\.\/locale$/,
		}),
	],
	target: 'web',
};

module.exports = {
	config,
	include,
	publicPath: PUBLIC_PATH,
	resolve: {
		extensions: ['', '.js', '.jsx', '.ts', '.tsx'],
		root: [resolveModule()],
	},
};
