/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {createContext, useContext, useMemo} from 'react';
import * as API from '~/shared/api';
import {IStatesRendererContextProps} from '~/shared/components/states-renderer/StatesRenderer';
import {useRequest} from '~/shared/hooks/useRequest';
import {DataSourceStatuses, DataSourceTypes} from '~/shared/util/constants';
import {NAME, createOrderIOMap} from '~/shared/util/pagination';

interface IDataSourceProps {
	contactsSelected: boolean;
	dateCreated: string;
	name: string;
	providerType: DataSourceTypes;
	sitesSelected: boolean;
	status: DataSourceStatuses;
}

interface IDataSourcesContext extends IStatesRendererContextProps {
	items: IDataSourceProps[];
	refetch: () => void;
}

const DataSourcesContext = createContext<IDataSourcesContext | null>(null);

export const DataSourcesProvider = function DataSourcesProvider({
	children,
	groupId,
	skip = false,
}: {
	children: React.ReactNode;
	groupId: string;
	skip?: boolean;
}) {
	const variables = useMemo(
		() => ({
			delta: 1,
			groupId,
			orderIOMap: createOrderIOMap(NAME),
			page: 1,
			query: '',
		}),
		[groupId]
	);

	const {
		data = {items: []},
		error,
		loading,
		refetch,
	} = useRequest<any, {items: IDataSourceProps[]}>({
		dataSourceFn: API.dataSource.search,
		skipRequest: skip || !groupId || groupId === '0',
		variables,
	});

	const value = useMemo<IDataSourcesContext>(
		() => ({
			empty: !data?.items.length && !error && !loading,
			error,
			items: data?.items ?? [],
			loading,
			refetch,
		}),
		[data, error, loading, refetch]
	);

	return (
		<DataSourcesContext.Provider value={value}>
			{children}
		</DataSourcesContext.Provider>
	);
};

export const useDataSources = function useDataSources() {
	const context = useContext(DataSourcesContext);

	if (!context) {
		throw new Error(
			'useDataSources must be used within a DataSourcesProvider'
		);
	}

	return context;
};

export default DataSourcesProvider;
