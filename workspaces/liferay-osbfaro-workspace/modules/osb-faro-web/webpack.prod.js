/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const webpack = require('webpack');
const {merge} = require('webpack-merge');

const common = require('./webpack.common');

module.exports = merge(common.config, {
	mode: 'production',
	output: {
		chunkFilename: '[id].[chunkhash].js',
	},
	performance: {
		hints: false,
	},
	plugins: [
		new webpack.DefinePlugin({
			FARO_DEV_MODE: false,
		}),
	],
});
