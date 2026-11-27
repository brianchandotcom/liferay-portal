import React from 'react';
import Welcome from '../Welcome';
import {cleanup, fireEvent, render} from '@testing-library/react';
import {noop} from 'lodash';
import {useLDPEnabled} from 'shared/hooks/useLDPEnabled';

jest.mock('shared/hooks/useLDPEnabled', () => ({
	useLDPEnabled: jest.fn(),
}));

describe('Welcome', () => {
	afterEach(cleanup);

	beforeEach(() => {
		useLDPEnabled.mockReturnValue(false);
	});

	it('renders', () => {
		const {container} = render(<Welcome onClose={noop} onNext={noop} />);

		expect(container).toMatchSnapshot();
	});

	it('calls onNext when "Next" is clicked', () => {
		const spy = jest.fn();

		const {queryByText} = render(<Welcome onClose={noop} onNext={spy} />);

		expect(spy).not.toBeCalled();

		fireEvent.click(queryByText(/^next$/i));

		expect(spy).toBeCalled();
	});

	it('shows the Analytics Cloud welcome message for a non-LDP workspace', () => {
		const {getByText} = render(<Welcome onClose={noop} onNext={noop} />);

		expect(getByText(/^welcome to analytics cloud$/i)).toBeInTheDocument();
	});

	it('shows the Liferay Data Platform welcome message for an LDP workspace', () => {
		useLDPEnabled.mockReturnValue(true);

		const {getByText} = render(<Welcome onClose={noop} onNext={noop} />);

		expect(
			getByText(/^welcome to liferay data platform$/i)
		).toBeInTheDocument();
	});
});
