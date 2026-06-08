/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DocumentNode} from '@apollo/client';
import React, {createContext, useContext, useReducer} from 'react';
import Card from '~/shared/components/Card';
import BaseCard from '~/shared/components/base-card';
import {ICommonVariables, Interval, RangeSelectors} from '~/shared/types';
import {RawFilters} from '~/shared/util/filter';

import {ReportContainer} from '../download-report/DownloadPDFReport';
import MetricChart from './MetricChart';
import MetricTabs from './MetricTabs';
import {Metric} from './metrics';
import {getMetricsChartData} from './util';

const initialState = {
	activeItemIndex: 0,
	chartDataMapFn: null,
	compareToPrevious: false,
	metrics: [],
	queries: {
		MetricQuery: null,
		TabsQuery: null,

		name: '',
	},
	variables: () => ({}),
};

const MetricContext = createContext(initialState as any);

const MetricContextActions = createContext({
	changeActiveItemIndex: () => {},
	changeCompareToPrevious: () => {},
} as any);

export interface ICommonMetricProps {
	emptyDescription?: React.ReactNode;
	emptyTitle?: string;
	experienceId?: string;
	filters: RawFilters;
	interval: Interval;
	rangeSelectors: RangeSelectors;
}

export interface IGenericMetricBaseCardProps {
	emptyDescription?: React.ReactNode;
	emptyTitle?: string;
	label: string;
	legacyDropdownRangeKey?: boolean;
	reportContainer?: ReportContainer;
	showIntervals?: boolean;
}

interface IMetricBaseCardProps<TChartData>
	extends IGenericMetricBaseCardProps,
		React.HTMLAttributes<HTMLElement> {
	chartDataMapFn?: TChartData | typeof getMetricsChartData;
	metrics: Metric[];
	queries: {
		MetricQuery: (metricName: string) => DocumentNode;
		TabsQuery: DocumentNode;
		name: string;
	};
	variables: (commonVariables: ICommonVariables) => any;
}

type TMetricState = {
	activeItemIndex: number;
	chartDataMapFn: unknown;
	compareToPrevious: boolean;
	metrics: Metric[];
	queries: {
		MetricQuery: ((metricName: string) => DocumentNode) | null;
		TabsQuery: DocumentNode | null;
		name: string;
	};
	variables: (commonVariables: ICommonVariables) => any;
};

type TMetricAction = {
	payload: any;
	type: Actions;
};

enum Actions {
	UpdateActiveItemIndex = 'UPDATE_ACTIVE_ITEM_INDEX',
	UpdateCompareToPrevious = 'UPDATE_COMPARE_TO_PREVIOUS',
}

const actionHandlers: Record<
	Actions,
	(state: TMetricState, action: TMetricAction) => TMetricState
> = {
	[Actions.UpdateActiveItemIndex]: (state, {payload}) => ({
		...state,
		activeItemIndex: payload,
	}),
	[Actions.UpdateCompareToPrevious]: (state, {payload}) => ({
		...state,
		compareToPrevious: payload,
	}),
};

export const reducer = function reducer(
	state: TMetricState,
	action: TMetricAction
) {
	const handlerFn = actionHandlers[action.type];

	if (handlerFn) {
		return handlerFn(state, action);
	}

	throw new Error('Unhandled action type: ${type}');
};

function MetricBaseCard<TChartData>({
	chartDataMapFn = getMetricsChartData,
	emptyDescription,
	emptyTitle,
	id,
	label,
	legacyDropdownRangeKey = false,
	metrics,
	queries,
	reportContainer,
	showIntervals = false,
	variables,
}: IMetricBaseCardProps<TChartData>): React.ReactElement {
	const [state, dispatch] = useReducer(reducer, initialState);

	const actions = {
		changeActiveItemIndex: (activeItemIndex: number) => {
			dispatch({
				payload: activeItemIndex,
				type: Actions.UpdateActiveItemIndex,
			});
		},
		changeCompareToPrevious: (compareToPrevious: boolean) => {
			dispatch({
				payload: compareToPrevious,
				type: Actions.UpdateCompareToPrevious,
			});
		},
	};

	return (
		<MetricContext.Provider
			value={{
				...state,
				chartDataMapFn,
				metrics,
				queries,
				variables,
			}}
		>
			<MetricContextActions.Provider value={actions}>
				<BaseCard
					className="analytics-metrics-card"
					id={id}
					label={label}
					legacyDropdownRangeKey={legacyDropdownRangeKey}
					minHeight={605}
					reportContainer={reportContainer}
					showInterval={showIntervals}
				>
					{({experienceId, filters, interval, rangeSelectors}) => {
						const sharedProps: ICommonMetricProps = {
							emptyDescription,
							emptyTitle,
							experienceId,
							filters,
							interval,
							rangeSelectors,
						};

						return (
							<Card.Body className="analytics-metrics">
								<MetricTabs {...sharedProps} />

								<MetricChart {...sharedProps} />
							</Card.Body>
						);
					}}
				</BaseCard>
			</MetricContextActions.Provider>
		</MetricContext.Provider>
	);
}

export const useData = function useData() {
	return useContext(MetricContext);
};
export const useActions = function useActions() {
	return useContext(MetricContextActions);
};

export default MetricBaseCard;
