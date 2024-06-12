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
import {UserAccount} from '../services/rest';
import {ActionMap} from '../types';

export type BuildId = number | null;
export type RunId = number | null;

export type AutofillBuild = {
	buildA?: BuildId;
	buildB?: BuildId;
};

export type CompareRuns = {
	runA?: RunId;
	runB?: RunId;
};

type InitialState = {
	autofillBuild: AutofillBuild;
	compareRuns: CompareRuns;
	myUserAccount?: UserAccount;
	runNumber: number;
};

const initialState: InitialState = {
	autofillBuild: {
		buildA: null,
		buildB: null,
	},
	compareRuns: {
		runA: null,
		runB: null,
	},
	myUserAccount: undefined,
	runNumber: 0,
};

export const enum TestrayTypes {
	SET_BUILD_A = 'SET_BUILD_A',
	SET_BUILD_B = 'SET_BUILD_B',
	SET_MY_USER_ACCOUNT = 'SET_MY_USER_ACCOUNT',

	SET_RUN_A = 'SET_RUN_A',
	SET_RUN_B = 'SET_RUN_B',
}

type TestrayPayload = {
	[TestrayTypes.SET_BUILD_A]: BuildId;
	[TestrayTypes.SET_BUILD_B]: BuildId;
	[TestrayTypes.SET_MY_USER_ACCOUNT]: {
		account: UserAccount;
	};

	[TestrayTypes.SET_RUN_A]: RunId;
	[TestrayTypes.SET_RUN_B]: RunId;
};

type AppActions = ActionMap<TestrayPayload>[keyof ActionMap<TestrayPayload>];

export const TestrayContext = createContext<
	[
		InitialState,
		(param: AppActions) => void,
		KeyedMutator<UserAccount> | null,
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

		case TestrayTypes.SET_BUILD_A:
			return {
				...state,
				autofillBuild: {...state.autofillBuild, buildA: action.payload},
			};

		case TestrayTypes.SET_BUILD_B:
			return {
				...state,
				autofillBuild: {...state.autofillBuild, buildB: action.payload},
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

		default:
			return state;
	}
};

const TestrayContextProvider: React.FC<{
	children: ReactNode;
}> = ({children}) => {
	const [autofillBuildValue, setAutofillBuildValue] = useStorage<{
		autofillBuild: AutofillBuild;
	}>(STORAGE_KEYS.AUTO_FILL, {
		initialValue: initialState,
		storageType: 'temporary',
	});

	const [compareRunsValue, setcompareRunsValue] = useStorage<{
		compareRuns: CompareRuns;
	}>(STORAGE_KEYS.COMPARE_RUNS, {
		initialValue: initialState,
		storageType: 'temporary',
	});

	const [state, dispatch] = useReducer(reducer, {
		...initialState,
		autofillBuild: autofillBuildValue?.autofillBuild,
		compareRuns: compareRunsValue?.compareRuns,
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
			name: user.name,
			roleBriefs: user?.roleBriefs,
			userGroupBriefs: user?.userGroupBriefs,
			uuid: user?.uuid,
		}),
	});

	const compareRuns = useMemo(() => state.compareRuns, [state.compareRuns]);

	const autofillBuild = useMemo(
		() => state.autofillBuild,
		[state.autofillBuild]
	);

	useEffect(() => {
		if (compareRuns) {
			setcompareRunsValue({compareRuns});
		}

		if (autofillBuild) {
			setAutofillBuildValue({autofillBuild});
		}
	}, [
		autofillBuild,
		compareRuns,
		setAutofillBuildValue,
		setcompareRunsValue,
	]);

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
