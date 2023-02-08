const path = require('path');

module.exports = {
	entry: './src/index.js',
	output: {
		filename: '[name]-[hash].js',
		globalObject: 'this',
		library: {
			name: 'navigation',
			type: 'umd',
		},
		path: path.resolve(__dirname, 'build/'),
	},
};
