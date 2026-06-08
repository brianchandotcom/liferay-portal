/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isNil} from 'lodash';
import React, {useReducer} from 'react';
import {Segment} from '~/shared/util/records';

export enum ActionType {
	setSegments = 'setSegments',
	updateShowAlert = 'updateShowAlert',
}

type Action = {
	payload?: any;
	type: ActionType;
};
type Dispatch = (action: Action) => void;

type State = {
	showUnassignedAlert: boolean;
	unassignedSegments: Array<Segment>;
};

type unassignedSegmentsProviderProps = {
	children: React.ReactNode;
	unassignedSegments?: Array<Segment>;
};

export const UnassignedSegmentsContext = React.createContext<{
	showUnassignedAlert: boolean;
	unassignedSegments: Array<Segment>;
	unassignedSegmentsDispatch?: Dispatch;
}>({
	showUnassignedAlert: true,
	unassignedSegments: [],
});

export const unassignedSegmentsReducer = function unassignedSegmentsReducer(
	state: State,
	{payload, type}: Action
) {
	switch (type) {
		case 'setSegments': {
			return {
				...state,
				unassignedSegments: payload,
			};
		}
		case 'updateShowAlert': {
			return {
				...state,
				showUnassignedAlert: false,
			};
		}
		default:
			return state;
	}
};

export const UnassignedSegmentsProvider = function UnassignedSegmentsProvider({
	children,
	unassignedSegments: initialSegments,
}: unassignedSegmentsProviderProps) {
	const [
		{showUnassignedAlert, unassignedSegments},
		unassignedSegmentsDispatch,
	] = useReducer(unassignedSegmentsReducer, {
		showUnassignedAlert: true,
		unassignedSegments: initialSegments || [],
	});

	return (
		<UnassignedSegmentsContext.Provider
			value={{
				showUnassignedAlert,
				unassignedSegments,
				unassignedSegmentsDispatch,
			}}
		>
			{children}
		</UnassignedSegmentsContext.Provider>
	);
};

export const useUnassignedSegmentsContext =
	function useUnassignedSegmentsContext() {
		const context = React.useContext(UnassignedSegmentsContext);
		if (isNil(context)) {
			throw new Error(
				'UnassignedSegmentsContext must be used within a UnassignedSegmentsProvider'
			);
		}

		return context;
	};

export const withUnassignedSegmentsProvider =
	function withUnassignedSegmentsProvider<P extends object>(
		WrappedComponent: React.ComponentType<P>
	) {
		return (props: P) => (
			<UnassignedSegmentsProvider>
				<WrappedComponent {...props} />
			</UnassignedSegmentsProvider>
		);
	};

export default UnassignedSegmentsProvider;
