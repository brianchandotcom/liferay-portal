/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApolloError} from '@apollo/client';

export function formatItem<T extends object>(item: T): Record<string, unknown>;

interface IMapListResultsInput<TData = any> {
	data?: TData | null;
	error?: ApolloError | unknown;
	loading: boolean;
	refetch?: (...args: any[]) => any;
}

interface IMapListResultsOutput<TItem = any> {
	empty: boolean;
	error: ApolloError | unknown;
	items: TItem[];
	loading: boolean;
	refetch?: (...args: any[]) => any;
	total: number;
}

export function mapListResultsToProps<TData = any, TItem = any>(
	response: IMapListResultsInput<TData>,
	mapperFn?: (data: TData) => {items?: TItem[]; total?: number}
): IMapListResultsOutput<TItem>;

export function safeResultToProps<TData = any>(
	mapper: (data: TData, context?: any, ownProps?: any) => Record<string, any>
): (props: {data: any; ownProps: any}, context?: any) => Record<string, any>;

export function getVariables(args: {
	assetId?: string;
	experienceId?: string;
	filters?: any;
	interval?: string;
	params: Record<string, string | undefined>;
	rangeSelectors?: any;
}): {variables: Record<string, any>};
