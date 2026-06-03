import OnboardingModal from '../index';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {noop} from 'lodash';

jest.unmock('react-dom');

describe('OnboardingModal', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<OnboardingModal onClose={noop} />);

		expect(container).toMatchSnapshot();
	});
});
