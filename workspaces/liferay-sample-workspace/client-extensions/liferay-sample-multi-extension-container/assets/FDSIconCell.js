const fdsCellRenderer = ({ value }) => {
	const element = document.createElement('div');

	element.innerHTML = value.toString().toUpperCase();

	return element;
};

export default fdsCellRenderer;
