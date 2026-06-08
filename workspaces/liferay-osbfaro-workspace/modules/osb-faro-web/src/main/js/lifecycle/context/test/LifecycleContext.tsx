/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, renderHook} from '@testing-library/react';
import React from 'react';
import {LifecycleStages} from '~/contacts/pages/account/utils/constants';

import {LifecycleContextProvider, useLifecycle} from '../LifecycleContext';

jest.unmock('react-dom');

const wrapper = ({children}: {children: React.ReactNode}) => (
	<LifecycleContextProvider lifecycleId="1">
		{children}
	</LifecycleContextProvider>
);

describe('LifecycleContext', () => {
	it('defaults lifecycleStageFilter to AT_RISK and leave other filters empty', () => {
		const {result} = renderHook(() => useLifecycle(), {wrapper});

		expect(result.current.filters).toEqual({
			countryFilter: '',
			filterString: '',
			industryFilter: '',
			lifecycleStageFilter: LifecycleStages.AT_RISK,
		});
	});

	it('updates a single filter and recompute filterString', () => {
		const {result} = renderHook(() => useLifecycle(), {wrapper});

		act(() => result.current.updateFilters({industryFilter: 'Tech'}));

		expect(result.current.filters).toEqual({
			countryFilter: '',
			filterString: "industry eq 'Tech'",
			industryFilter: 'Tech',
			lifecycleStageFilter: LifecycleStages.AT_RISK,
		});
	});

	it('merges partial updates without clearing untouched filters', () => {
		const {result} = renderHook(() => useLifecycle(), {wrapper});

		act(() => result.current.updateFilters({industryFilter: 'Tech'}));
		act(() => result.current.updateFilters({countryFilter: 'US'}));

		expect(result.current.filters.filterString).toBe(
			"country eq 'US' and industry eq 'Tech'"
		);
	});

	it('swaps lifecycleStageFilter to the value provided', () => {
		const {result} = renderHook(() => useLifecycle(), {wrapper});

		act(() =>
			result.current.updateFilters({
				lifecycleStageFilter: LifecycleStages.AWARE,
			})
		);

		expect(result.current.filters.lifecycleStageFilter).toBe(
			LifecycleStages.AWARE
		);
	});

	it('resets filters to their initial values', () => {
		const {result} = renderHook(() => useLifecycle(), {wrapper});

		act(() =>
			result.current.updateFilters({
				countryFilter: 'US',
				industryFilter: 'Tech',
				lifecycleStageFilter: LifecycleStages.AWARE,
			})
		);
		act(() => result.current.resetFilters());

		expect(result.current.filters).toEqual({
			countryFilter: '',
			filterString: '',
			industryFilter: '',
			lifecycleStageFilter: LifecycleStages.AT_RISK,
		});
	});
});
