import {upperCase} from './shared-utils.js';

const fdsCellRenderer = ({value}) => {
	const element = document.createElement('div');

	element.innerHTML = upperCase(value);

	return element;
};

export default fdsCellRenderer;
