import { merge } from 'webpack-merge';
import common from './webpack.common.js';

export default () =>
	merge(common, {
		mode: 'production',
		externals: {
			"react": '/o/frontend-js-react-web/__liferay__/exports/react.js',
			"react-dom": '/o/frontend-js-react-web/__liferay__/exports/react-dom.js'
		}
	});
