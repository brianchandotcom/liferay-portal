import InterestTopicsModal from '../InterestTopicsModal';
import React from 'react';
import {noop} from 'lodash';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('InterestTopicsModal', () => {
	it('should render', () => {
		const {container} = render(<InterestTopicsModal onClose={noop} />);

		expect(container).toMatchSnapshot();
	});
});
