import React from 'react';
import {StaticRouter} from 'react-router';

export default {
	params: {},
	query: {}
};

export function withStaticRouter(Component) {
	return props => (
		<StaticRouter>
			<Component {...props} />
		</StaticRouter>
	);
}
