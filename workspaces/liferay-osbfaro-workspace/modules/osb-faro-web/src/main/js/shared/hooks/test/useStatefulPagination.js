/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, render} from '@testing-library/react';
import {Map, Set} from 'immutable';
import React from 'react';
import {useStatefulPagination} from '~/shared/hooks/useStatefulPagination';
import Constants from '~/shared/util/constants';
import {createOrderIOMap} from '~/shared/util/pagination';

const {cur: DEFAULT_PAGE, delta: DEFAULT_DELTA} = Constants.pagination;

jest.unmock('react-dom');

describe('useStatefulPagination', () => {
	it('returns default values', () => {
		let result = null;

		const Component = () => {
			result = useStatefulPagination();

			return null;
		};

		render(<Component />);

		expect(result).toMatchObject({
			delta: DEFAULT_DELTA,
			filterBy: expect.anything(),
			page: DEFAULT_PAGE,
			query: '',
		});
		expect(typeof result.onDeltaChange).toBe('function');
		expect(typeof result.onPageChange).toBe('function');
		expect(typeof result.onQueryChange).toBe('function');
		expect(typeof result.onFilterByChange).toBe('function');
		expect(typeof result.onOrderIOMapChange).toBe('function');
		expect(typeof result.resetPage).toBe('function');
	});

	it('sets delta value on onDeltaChange and reset page', () => {
		let result = null;

		const Component = () => {
			result = useStatefulPagination();

			return null;
		};

		const {rerender} = render(<Component />);

		const newDelta = 10;
		const newPage = 2;

		jest.runAllTimers();

		expect(result.delta).toBe(DEFAULT_DELTA);

		act(() => {
			result.onPageChange(newPage);
		});

		rerender(<Component />);

		expect(result.page).toBe(newPage);

		act(() => {
			result.onDeltaChange(newDelta);
		});

		rerender(<Component />);

		expect(result.delta).toBe(newDelta);
		expect(result.page).toBe(DEFAULT_PAGE);
	});

	it('sets page value on onPageChange and page be reseted', () => {
		let result = null;

		const Component = () => {
			result = useStatefulPagination();

			return null;
		};

		const {rerender} = render(<Component />);

		const newPage = 2;

		jest.runAllTimers();

		expect(result.page).toBe(DEFAULT_PAGE);

		act(() => {
			result.onPageChange(newPage);
		});

		rerender(<Component />);

		expect(result.page).toBe(newPage);

		act(() => {
			result.resetPage();
		});

		rerender(<Component />);

		expect(result.page).toBe(DEFAULT_PAGE);
	});

	it('sets orderIOMap value on onOrderIOMapChange and page be reseted', () => {
		let result = null;

		const Component = () => {
			result = useStatefulPagination(null, {
				initialOrderIOMap: createOrderIOMap('name'),
			});

			return null;
		};

		const {rerender} = render(<Component />);

		const newPage = 2;

		jest.runAllTimers();

		expect(result.orderIOMap.size).toBe(1);

		act(() => {
			result.onPageChange(newPage);
		});

		rerender(<Component />);

		expect(result.page).toBe(newPage);

		act(() => {
			result.onOrderIOMapChange(createOrderIOMap('dateModified', 'ASC'));
		});

		rerender(<Component />);

		expect(result.orderIOMap.size).toBe(1);
		expect(result.page).toBe(DEFAULT_PAGE);
	});

	it('sets query value on onQueryChange and reset page', () => {
		let result = null;

		const Component = () => {
			result = useStatefulPagination();

			return null;
		};

		const {rerender} = render(<Component />);

		const newQuery = 'test';
		const newPage = 2;

		jest.runAllTimers();

		expect(result.query).toBe('');

		act(() => {
			result.onPageChange(newPage);
		});

		rerender(<Component />);

		expect(result.page).toBe(newPage);

		act(() => {
			result.onQueryChange(newQuery);
		});

		rerender(<Component />);

		expect(result.query).toBe(newQuery);
		expect(result.page).toBe(DEFAULT_PAGE);
	});

	it('sets filterBy value on onFilterByChange and reset page', () => {
		let result = null;

		const Component = () => {
			result = useStatefulPagination();

			return null;
		};

		const {rerender} = render(<Component />);

		const newPage = 2;

		jest.runAllTimers();

		expect(result.filterBy.size).toBe(0);

		act(() => {
			result.onPageChange(newPage);
		});

		rerender(<Component />);

		expect(result.page).toBe(newPage);

		act(() => {
			result.onFilterByChange(
				Map({
					biz: Set(['buz']),
				})
			);
		});

		rerender(<Component />);

		expect(result.filterBy.size).toBe(1);
		expect(result.page).toBe(DEFAULT_PAGE);
	});
});
