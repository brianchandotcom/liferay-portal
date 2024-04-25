/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ReactNode,
	createContext,
	useContext,
	useEffect,
	useReducer,
} from 'react';
import {useParams} from 'react-router-dom';

import HeadlessCommerceAdminCatalogImpl from '../services/rest/HeadlessCommerceAdminCatalog';

export type InitialState = {
	productId: number;
	profile: {
		categories: {
			label: string;
			value: string;
		}[];
		description: string;
		name: string;
		tags: {
			label: string;
			value: string;
		}[];
	};
};

export enum SolutionTypes {
	SET_PRODUCT_ID = 'SET_PRODUCT_ID',
	SET_PROFILE = 'SET_PROFILE',
}

const solutionInitialState: InitialState = {
	productId: 0,
	profile: {
		categories: [],
		description: '',
		name: '',
		tags: [],
	},
};

type SolutionPayload = {
	[SolutionTypes.SET_PRODUCT_ID]: number;
	[SolutionTypes.SET_PROFILE]: Partial<{
		categories: [];
		description: '';
		name: '';
		tags: [];
	}>;
};

export type AppActions = ActionMap<SolutionPayload>[keyof ActionMap<
	SolutionPayload
>];
const reducer = (state: InitialState, action: AppActions) => {
	switch (action.type) {
		case SolutionTypes.SET_PRODUCT_ID: {
			return {
				...state,
				productId: action.payload,
			};
		}

		case SolutionTypes.SET_PROFILE: {
			return {
				...state,
				profile: {
					...state.profile,
					...action.payload,
				},
			};
		}

		default:
			return state;
	}
};

export const SolutionContext = createContext<
	[InitialState, (param: AppActions) => void]
>([solutionInitialState, () => null]);

type SolutionContextProviderProps = {
	children: ReactNode;
};

export default function SolutionContextProvider({
	children,
}: SolutionContextProviderProps) {
	const [state, dispatch] = useReducer(reducer, solutionInitialState);

	const {id: productId} = useParams();

	useEffect(() => {
		if (productId) {
			HeadlessCommerceAdminCatalogImpl.getProduct(productId as string)
				.then(console.log)
				.catch(console.error);
		}
	}, [productId]);

	return (
		<SolutionContext.Provider value={[state, dispatch]}>
			{children}
		</SolutionContext.Provider>
	);
}

export function useSolutionContext() {
	return useContext(SolutionContext);
}
