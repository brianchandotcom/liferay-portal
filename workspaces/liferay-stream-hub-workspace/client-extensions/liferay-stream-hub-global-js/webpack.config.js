const path = require('path');
const webpack = require('webpack');

const DEVELOPMENT = process.env.NODE_ENV === 'development';

module.exports = {
    devtool: DEVELOPMENT ? 'source-map' : false,
    entry: {
        global: './assets/global.js',
    },
    mode: DEVELOPMENT ? 'development' : 'production',
    optimization: {
        minimize: !DEVELOPMENT,
    },
    output: {
        filename: '[name].[contenthash].js',
        path: path.resolve('build', 'static'),
    },
    plugins: [
        new webpack.optimize.LimitChunkCountPlugin({
            maxChunks: 1,
        }),
    ],
};
