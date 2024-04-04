/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactNode, createContext, useContext, useReducer} from 'react';

import {solutionReducer} from './reducerSolution';

type SolutionContextProviderProps = {
	children: ReactNode;
};

export type SolutionInitialStateProps = {
	solution: any;
};

const initialState = ({
	solution: {},
} as unknown) as any;

const SolutionContext = createContext({} as any);

const SolutionContextProvider: React.FC<SolutionContextProviderProps> = ({
	children,
}) => {
	const [state, dispatch] = useReducer<
		React.Reducer<SolutionInitialStateProps, any>
	>(solutionReducer, {...initialState});

	return (
		<SolutionContext.Provider value={[state, dispatch]}>
			{children}
		</SolutionContext.Provider>
	);
};

export function useSolutionContext() {
	return useContext(SolutionContext);
}

export default SolutionContextProvider;
