/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {StaticRouter} from 'react-router';

import {SafeResults, withEmpty, withError, withLoading} from '../util';

jest.unmock('react-dom');

describe('HOC Util', () => {
	afterEach(cleanup);

	describe('SafeResults', () => {
		it('passes the children when error and loading are false', () => {
			const {getByText} = render(
				<SafeResults error={false} loading={false}>
					{() => 'foo'}
				</SafeResults>
			);

			expect(getByText('foo')).toBeTruthy();
		});

		it('returns a loading screen when loading is true', () => {
			const {container} = render(
				<SafeResults error={false} loading>
					{() => 'foo'}
				</SafeResults>
			);

			expect(container.querySelector('.loading-root')).toBeTruthy();
		});

		it('returns an error screen when error is true', () => {
			const {container} = render(
				<StaticRouter>
					<SafeResults error>{() => 'foo'}</SafeResults>
				</StaticRouter>
			);

			expect(container.querySelector('.error-page-root')).toBeTruthy();
		});
	});

	describe('withEmpty', () => {
		it('renders with an empty state', () => {
			const WrappedComponent = withEmpty()(() => 'test');

			const {container} = render(<WrappedComponent data={{total: 0}} />);

			expect(container.querySelector('.no-results-root')).toBeTruthy();
		});

		it('returns the passed component', () => {
			const componentSpy = jest.fn(() => 'test');

			const WrappedComponent = withEmpty()(componentSpy);

			render(<WrappedComponent data={{total: 1}} />);

			expect(componentSpy).toHaveBeenCalled();
		});
	});

	describe('withError', () => {
		it('renders with an error page', () => {
			const WrappedComponent = withError()(jest.fn(() => 'test'));

			const {container} = render(
				<StaticRouter>
					<WrappedComponent error />
				</StaticRouter>
			);

			expect(container.querySelector('.error-page-root')).toBeTruthy();
		});

		it('renders with an error display', () => {
			const WrappedComponent = withError({page: false})(
				jest.fn(() => 'test')
			);

			const {container} = render(<WrappedComponent error />);

			expect(container.querySelector('.error-display-root')).toBeTruthy();
		});

		it('returns the passed component', () => {
			const componentSpy = jest.fn(() => 'test');

			const WrappedComponent = withError()(componentSpy);

			render(<WrappedComponent error={false} />);

			expect(componentSpy).toHaveBeenCalled();
		});

		it('renders a custom error message', () => {
			const customMessage = 'my fancy message, oh so fancy';

			const WrappedComponent = withError({message: customMessage})(
				jest.fn(() => 'test')
			);

			const {getByText} = render(
				<StaticRouter>
					<WrappedComponent error />
				</StaticRouter>
			);

			expect(getByText(customMessage)).toBeTruthy();
		});
	});

	describe('withLoading', () => {
		it('renders with a loading page', () => {
			const WrappedComponent = withLoading()(jest.fn(() => 'test'));

			const {container} = render(<WrappedComponent loading />);

			expect(container.querySelector('.loading-root')).toBeTruthy();
		});

		it('renders w/ loading', () => {
			const WrappedComponent = withLoading()(jest.fn(() => 'test'));

			const {container} = render(<WrappedComponent loading />);

			expect(container.querySelector('.loading-root')).toBeTruthy();
		});

		it('returns the passed component', () => {
			const componentSpy = jest.fn(() => 'test');

			const WrappedComponent = withLoading()(componentSpy);

			render(<WrappedComponent loading={false} />);

			expect(componentSpy).toHaveBeenCalled();
		});
	});
});
