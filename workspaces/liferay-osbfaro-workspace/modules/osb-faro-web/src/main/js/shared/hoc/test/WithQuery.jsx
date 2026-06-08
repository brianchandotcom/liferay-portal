/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render, waitFor} from '@testing-library/react';
import React from 'react';

import withQuery from '../WithQuery';

jest.unmock('react-dom');

const mockData = {test: 'pass'};
const mockFailData = {test: 'fail'};

const request = jest.fn(() => Promise.resolve(mockData));
const rejectRequest = jest.fn(() => Promise.reject(mockFailData));

describe('WithQuery', () => {
	afterEach(cleanup);

	it('passes result props to the wrapped component', async () => {
		const WrappedComponent = withQuery(
			request,
			(val) => val
		)(({data}) => <div>{data && data.test}</div>);

		const {queryByText} = render(<WrappedComponent />);

		await waitFor(() => expect(queryByText('pass')).toBeTruthy());
	});

	it('returns an error', async () => {
		const WrappedComponent = withQuery(
			rejectRequest,
			(val) => val
		)(({error}) => <div>{error && 'error'}</div>);

		const {queryByText} = render(<WrappedComponent />);

		await waitFor(() => expect(queryByText('error')).toBeTruthy());
	});

	it('returns the result mapped to props', async () => {
		const WrappedComponent = withQuery(
			request,
			(val) => val,
			({data}) => ({fooProp: data})
		)(({fooProp}) => <div>{fooProp && fooProp.test}</div>);

		const {queryByText} = render(<WrappedComponent />);

		await waitFor(() => expect(queryByText('pass')).toBeTruthy());
	});
});
