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

import {UploadedFile} from '../components/FileList/FileList';
import HeadlessCommerceAdminCatalogImpl from '../services/rest/HeadlessCommerceAdminCatalog';

export type SolutionInitialState = {
	_product?: Product;
	catalogId: number;
	header: {
		description: any;
		headerImages: UploadedFile[];
		headerVideo: string;
		radioValue: string;
		title: string;
	};
	productId: number;
	profile: {
		categories: {
			label: string;
			value: string;
		}[];
		description: string;
		file: UploadedFile;
		name: string;
		tags: {
			label: string;
			value: string;
		}[];
	};
};

export enum SolutionTypes {
	SET_HEADER = 'SET_HEADER',
	SET_PRODUCT_ID = 'SET_PRODUCT_ID',
	SET_PRODUCT = 'SET_PRODUCT',
	SET_PROFILE = 'SET_PROFILE',
}

const solutionInitialState: SolutionInitialState = {
	catalogId: 0,
	header: {
		description: '',
		headerImages: [],
		headerVideo: '',
		radioValue: '',
		title: '',
	},
	productId: 0,
	profile: {
		categories: [],
		description: '',
		file: {} as UploadedFile,
		name: '',
		tags: [],
	},
};

type SolutionPayload = {
	[SolutionTypes.SET_HEADER]: Partial<{
		description: '';
		headerImages: UploadedFile[];
		headerVideo: '';
		radioValue: '';
		title: '';
	}>;
	[SolutionTypes.SET_PRODUCT]: Product;
	[SolutionTypes.SET_PRODUCT_ID]: number;
	[SolutionTypes.SET_PROFILE]: Partial<{
		categories: [];
		description: '';
		file: UploadedFile;
		name: '';
		tags: [];
	}>;
};

export type AppActions = ActionMap<SolutionPayload>[keyof ActionMap<
	SolutionPayload
>];

const reducer = (state: SolutionInitialState, action: AppActions) => {
	switch (action.type) {
		case SolutionTypes.SET_PRODUCT_ID: {
			return {
				...state,
				productId: action.payload,
			};
		}

		case SolutionTypes.SET_PRODUCT: {
			return {
				...state,
				_product: action.payload,
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

		case SolutionTypes.SET_HEADER: {
			return {
				...state,
				header: {
					...state.header,
					...action.payload,
				},
			};
		}

		default:
			return state;
	}
};

export const SolutionContext = createContext<
	[SolutionInitialState, (param: AppActions) => void]
>([solutionInitialState, () => null]);

type SolutionContextProviderProps = {
	catalogId: number;
	children: ReactNode;
};

export default function SolutionContextProvider({
	catalogId,
	children,
}: SolutionContextProviderProps) {
	const {appId} = useParams();

	const [state, dispatch] = useReducer(reducer, solutionInitialState);

	useEffect(() => {
		if (!appId) {
			return;
		}

		HeadlessCommerceAdminCatalogImpl.getProduct(appId as string)
			.then((response) =>
				dispatch({payload: response, type: SolutionTypes.SET_PRODUCT})
			)
			.catch(console.error);
	}, [appId]);

	return (
		<SolutionContext.Provider value={[{...state, catalogId}, dispatch]}>
			{children}
		</SolutionContext.Provider>
	);
}

export function useSolutionContext() {
	return useContext(SolutionContext);
}
