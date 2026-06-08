/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {createContext, useContext, useReducer} from 'react';

import {ReportContainer} from './DownloadPDFReport';

export const Context = createContext<{
	reportContainers: ReportContainer[];
	setReportContainer: (reportContainer: ReportContainer) => void;
	clearReportContainers: () => void;
}>({
	clearReportContainers: () => {},
	reportContainers: [],
	setReportContainer: () => {},
});

enum ActionTypes {
	ClearReportContainers = 'CLEAR_CONTEXT',
	SetReportContainer = 'SET_REPORT_CONTAINER',
}

type DownloadReportAction = {
	payload?: ReportContainer;
	type: ActionTypes;
};

const downloadReportReducer = (
	state: {reportContainers: ReportContainer[]},
	action: DownloadReportAction
) => {
	switch (action.type) {
		case ActionTypes.SetReportContainer:
			return {
				...state,
				reportContainers: [
					...state.reportContainers,
					action.payload as ReportContainer,
				],
			};
		case ActionTypes.ClearReportContainers:
			return {
				...state,
				reportContainers: [],
			};
		default: {
			throw new Error(
				'Unhandled action type for Download Report Reducer'
			);
		}
	}
};

export const DownloadReportProvider = function DownloadReportProvider({
	children,
}: {
	children: React.ReactNode;
}) {
	const [{reportContainers}, dispatch] = useReducer(downloadReportReducer, {
		reportContainers: [],
	});

	const setReportContainer = (reportContainer: ReportContainer) => {
		dispatch({
			payload: reportContainer,
			type: ActionTypes.SetReportContainer,
		});
	};

	const clearReportContainers = () => {
		dispatch({
			type: ActionTypes.ClearReportContainers,
		});
	};

	return (
		<Context.Provider
			value={{
				clearReportContainers,
				reportContainers,
				setReportContainer,
			}}
		>
			{children}
		</Context.Provider>
	);
};

Context.displayName = 'DownloadReportContext';

export const useDownloadReportContext = function useDownloadReportContext() {
	return useContext(Context);
};
