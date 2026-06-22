import React from 'react';
import {render, waitFor} from '@testing-library/react';
import {useRequest} from 'shared/hooks/useRequest';

jest.unmock('react-dom');

const mockRequest = jest.fn(() => Promise.resolve('passed'));
const mockFailedRequest = jest.fn(() => Promise.reject('failed'));

describe('withRequest', () => {
	it('it should return a loading state until the the request completes', async () => {
		let result = null;

		const Component = () => {
			result = useRequest({dataSourceFn: mockRequest});

			return null;
		};

		render(<Component />);

		expect(result.loading).toBeTrue();

		await waitFor(() => {
			expect(result.loading).toBe(false);
		});
	});

	it('it should return the data when the request completes', async () => {
		let result = null;

		const Component = () => {
			result = useRequest({dataSourceFn: mockRequest});

			return null;
		};

		render(<Component />);

		await waitFor(() => {
			expect(result.data).toEqual('passed');
		});
	});

	it('it should return an error if the request failed', async () => {
		let result = null;

		const Component = () => {
			result = useRequest({dataSourceFn: mockFailedRequest});

			return null;
		};

		render(<Component />);

		await waitFor(() => {
			expect(result.error).toBeTrue();
		});
	});

	it('it should return a refetch function to refire the request', async () => {
		const spy = jest.fn(() => Promise.resolve('passed'));

		let result = null;

		const Component = () => {
			result = useRequest({dataSourceFn: spy});

			return null;
		};

		render(<Component />);

		await waitFor(() => {
			result.refetch();

			expect(spy).toHaveBeenCalledTimes(2);
		});
	});
});
