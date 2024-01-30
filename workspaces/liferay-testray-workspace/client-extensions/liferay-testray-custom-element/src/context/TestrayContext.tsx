/* eslint-disable no-case-declarations */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactNode, createContext, useEffect, useMemo, useReducer} from 'react';
import {KeyedMutator} from 'swr';
import {STORAGE_KEYS} from '~/core/Storage';

import {useFetch} from '../hooks/useFetch';
import useStorage from '../hooks/useStorage';
import {
	APIResponse,
	TestrayDispatchTrigger,
	UserAccount,
} from '../services/rest';
import {testrayDispatchTriggerImpl} from '../services/rest/TestrayDispatchTrigger';
import {ActionMap} from '../types';

export type RunId = number | null;

export type CompareRuns = {
	runA?: RunId;
	runB?: RunId;
	runId?: RunId;
};

type InitialState = {
	compareRuns: CompareRuns;
	myUserAccount?: UserAccount;
	runNumber: number;
	testrayDispatchTriggers: APIResponse<TestrayDispatchTrigger>;
};

const initialState: InitialState = {
	compareRuns: {
		runA: null,
		runB: null,
	},
	myUserAccount: undefined,
	runNumber: 0,
	testrayDispatchTriggers: {
		actions: {},
		facets: [],
		items: [],
		lastPage: 1,
		page: 1,
		pageSize: 1,
		totalCount: 1,
	},
};

export const enum TestrayTypes {
	SET_MY_USER_ACCOUNT = 'SET_MY_USER_ACCOUNT',
	SET_RUN = 'SET_RUN',
	SET_RUN_A = 'SET_RUN_A',
	SET_RUN_B = 'SET_RUN_B',
}

type TestrayPayload = {
	[TestrayTypes.SET_MY_USER_ACCOUNT]: {
		account: UserAccount;
	};
	[TestrayTypes.SET_RUN]: number;
	[TestrayTypes.SET_RUN_A]: RunId;
	[TestrayTypes.SET_RUN_B]: RunId;
};

type AppActions = ActionMap<TestrayPayload>[keyof ActionMap<TestrayPayload>];

export const TestrayContext = createContext<
	[
		InitialState,
		(param: AppActions) => void,
		KeyedMutator<UserAccount> | null
	]
>([initialState, () => null, null]);

const reducer = (state: InitialState, action: AppActions) => {
	switch (action.type) {
		case TestrayTypes.SET_MY_USER_ACCOUNT:
			const {account} = action.payload;

			return {
				...state,
				myUserAccount: account,
			};

		case TestrayTypes.SET_RUN_A:
			return {
				...state,
				compareRuns: {...state.compareRuns, runA: action.payload},
			};

		case TestrayTypes.SET_RUN_B:
			return {
				...state,
				compareRuns: {...state.compareRuns, runB: action.payload},
			};

		case TestrayTypes.SET_RUN: {
			const runNumber = action.payload;

			return {
				...state,
				runNumber,
			};
		}

		default:
			return state;
	}
};

const TestrayContextProvider: React.FC<{
	children: ReactNode;
}> = ({children}) => {
	const [storageValue, setStorageValue] = useStorage<{
		compareRuns: CompareRuns;
	}>(STORAGE_KEYS.COMPARE_RUNS, {
		initialValue: initialState,
		storageType: 'temporary',
	});

	const [state, dispatch] = useReducer(reducer, {
		...initialState,
		compareRuns: storageValue?.compareRuns,
	});

	const {data: testrayDispatchTriggers} = useFetch<
		APIResponse<TestrayDispatchTrigger>
	>(testrayDispatchTriggerImpl.resource, {
		params: {
			aggregationTerms: 'dueStatus',
			pageSize: 10,
			sort: 'dateCreated:asc',
		},
	});

	const {data: myUserAccount, mutate} = useFetch('/my-user-account', {
		transformData: (user: UserAccount) => ({
			actions: user?.actions,
			additionalName: user?.additionalName,
			alternateName: user?.alternateName,
			emailAddress: user?.emailAddress,
			familyName: user?.familyName,
			givenName: user?.givenName,
			id: user?.id,
			image: user.image,
			jiraAuthorization: user?.jiraAuthorization,
			name: user.name,
			roleBriefs: user?.roleBriefs,
			userGroupBriefs: user?.userGroupBriefs,
			uuid: user?.uuid,
		}),
	});

	const compareRuns = useMemo(() => state.compareRuns, [state.compareRuns]);

	useEffect(() => {
		if (compareRuns) {
			setStorageValue({compareRuns});
		}
	}, [setStorageValue, compareRuns]);

	useEffect(() => {
		if (myUserAccount) {
			dispatch({
				payload: {
					account: myUserAccount,
				},
				type: TestrayTypes.SET_MY_USER_ACCOUNT,
			});
		}
	}, [myUserAccount]);

	return (
		<TestrayContext.Provider
			value={[
				{
					...state,
					testrayDispatchTriggers: testrayDispatchTriggers as APIResponse<
						TestrayDispatchTrigger
					>,
				},
				dispatch,
				mutate,
			]}
		>
			{children}
		</TestrayContext.Provider>
	);
};

export default TestrayContextProvider;
