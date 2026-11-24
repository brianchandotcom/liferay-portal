import React from 'react';
import withQuery from '../WithQuery';
import {cleanup, render} from '@testing-library/react';

const mockData = {test: 'pass'};
const mockFailData = {test: 'fail'};

const request = jest.fn(() => Promise.resolve(mockData));
const rejectRequest = jest.fn(() => Promise.reject(mockFailData));

describe('WithQuery', () => {
	afterEach(cleanup);

	it('should pass result props to the wrapped component', async () => {
		const WrappedComponent = withQuery(
			request,
			val => val
		)(({data}) => <div>{data && data.test}</div>);

		const {findByText} = render(<WrappedComponent />);

		expect(await findByText('pass')).toBeInTheDocument();
	});

	it('should return an error', async () => {
		const WrappedComponent = withQuery(
			rejectRequest,
			val => val
		)(({error}) => <div>{error && 'error'}</div>);

		const {findByText} = render(<WrappedComponent />);

		expect(await findByText('error')).toBeInTheDocument();
	});

	it('should return the result mapped to props', async () => {
		const WrappedComponent = withQuery(
			request,
			val => val,
			({data}) => ({fooProp: data})
		)(({fooProp}) => <div>{fooProp && fooProp.test}</div>);

		const {findByText} = render(<WrappedComponent />);

		expect(await findByText('pass')).toBeInTheDocument();
	});
});
