/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {
	ReactNode,
	createContext,
	useCallback,
	useContext,
	useMemo,
	useState,
} from 'react';
import {LifecycleStages} from '~/contacts/pages/account/utils/constants';

import {
	ILifecycleFilterValues,
	buildQueryString,
} from '../utils/buildQueryString';

interface ILifecycleFilters extends ILifecycleFilterValues {
	filterString: string;
}

interface ILifecycleContext {
	filters: ILifecycleFilters;
	lifecycleId: string;
	resetFilters: () => void;
	updateFilters: (newFilters: Partial<ILifecycleFilterValues>) => void;
}

const LifecycleContext = createContext<ILifecycleContext>({
	filters: {
		countryFilter: '',
		filterString: '',
		industryFilter: '',
		lifecycleStageFilter: LifecycleStages.AT_RISK,
	},
	lifecycleId: '',
	resetFilters: () => {},
	updateFilters: () => {},
});

export const useLifecycle = function useLifecycle(): ILifecycleContext {
	return useContext(LifecycleContext);
};

const initialValues: ILifecycleFilterValues = {
	countryFilter: '',
	industryFilter: '',
	lifecycleStageFilter: LifecycleStages.AT_RISK,
};

interface ILifecycleContextProviderProps {
	children: ReactNode;
	lifecycleId: string;
}

export const LifecycleContextProvider = function LifecycleContextProvider({
	children,
	lifecycleId,
}: ILifecycleContextProviderProps) {
	const [filterValues, setFilterValues] =
		useState<ILifecycleFilterValues>(initialValues);

	const filters = useMemo<ILifecycleFilters>(
		() => ({
			...filterValues,
			filterString: buildQueryString(filterValues),
		}),
		[filterValues]
	);

	const updateFilters = useCallback(
		(newValues: Partial<ILifecycleFilterValues>) =>
			setFilterValues((prev) => ({...prev, ...newValues})),
		[]
	);

	const resetFilters = useCallback(() => setFilterValues(initialValues), []);

	const value = useMemo(
		() => ({filters, lifecycleId, resetFilters, updateFilters}),
		[filters, lifecycleId, resetFilters, updateFilters]
	);

	return (
		<LifecycleContext.Provider value={value}>
			{children}
		</LifecycleContext.Provider>
	);
};
