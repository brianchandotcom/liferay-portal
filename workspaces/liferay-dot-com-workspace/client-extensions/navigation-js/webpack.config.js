const path = require('path');

module.exports = {
	entry: './src/index.js',
	output: {
		filename: 'index.js',
		globalObject: 'this',
		library: {
			name: 'navigation',
			type: 'umd'
		},
		path: path.resolve(__dirname, 'build/static')
	}
};
