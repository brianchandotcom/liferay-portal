import type {FDSCellRenderer} from '@liferay/js-api/data-set';

const fdsCellRenderer: FDSCellRenderer = ({value}) => {
	const element = document.createElement('div');

	element.innerHTML = value === 'Green' ? '🍏' : value.toString();

	return element;
};

export default fdsCellRenderer;
