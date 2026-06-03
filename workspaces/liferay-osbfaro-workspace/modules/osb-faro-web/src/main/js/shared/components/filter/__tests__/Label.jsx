import React from 'react';
import {Label} from '../Label';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('Label', () => {
	it('should render', () => {
		const {container} = render(<Label label='Label' />);

		expect(container).toMatchSnapshot();
	});
});
