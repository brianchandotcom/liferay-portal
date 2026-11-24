import ItemTitle from '../ItemTitle';
import React from 'react';
import {render} from '@testing-library/react';

describe('ItemTitle', () => {
	it('should render', () => {
		const {container} = render(<ItemTitle />);

		expect(container).toMatchSnapshot();
	});

	it('should render with children', () => {
		const stringToMatch = 'Item title text';

		const {getByText} = render(
			<ItemTitle>
				<span>{stringToMatch}</span>
			</ItemTitle>
		);

		expect(getByText(stringToMatch)).toBeInTheDocument();
	});
});
