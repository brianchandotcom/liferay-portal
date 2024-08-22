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
import Loading from '../components/Loading';
import {PRODUCT_TAGS} from '../enums/Product';
import {ProductVocabulary} from '../enums/ProductVocabulary';
import {useGetVocabulariesAndCategories} from '../hooks/data/useGetVocabulariesAndCategories';

export enum NewAppTypes {
	SET_BUILD = 'SET_BUILD',
	SET_CLEANUP = 'SET_CLEANUP',
	SET_CONTEXT = 'SET_CONTEXT',
	SET_DELETE_IMAGE = 'SET_DELETE_IMAGE',
	SET_LOADING = 'SET_LOADING',
	SET_PRODUCT = 'SET_PRODUCT',
	SET_PRODUCT_ID = 'SET_PRODUCT_ID',
	SET_PROFILE = 'SET_PROFILE',
	SET_TERMS_AND_CONDITIONS = 'SET_TERMS_AND_CONDITIONS',
}

type NewAppPayload = {
	[NewAppTypes.SET_BUILD]: Partial<{
		appType: {
			id: number;
			value: string;
		};
		compatibleOffering: any[];
		liferayPackages: {
			version: string;
			file: any[];
		}[];
		resourceRequirements: {
			cpu?: string;
			ram?: string;
		};
	}>;
	[NewAppTypes.SET_CLEANUP]: undefined;
	[NewAppTypes.SET_CONTEXT]: Product;
	[NewAppTypes.SET_DELETE_IMAGE]: string;
	[NewAppTypes.SET_LOADING]: boolean;
	[NewAppTypes.SET_PRODUCT]: Product;
	[NewAppTypes.SET_PRODUCT_ID]: number;
	[NewAppTypes.SET_PROFILE]: Partial<{
		categories: any[];
		description: string;
		file: UploadedFile;
		name: string;
		tags: any[];
	}>;
	[NewAppTypes.SET_TERMS_AND_CONDITIONS]: boolean;
};

export type NewAppInitialState = {
	_product?: Product;
	build: {
		appType: {
			id: number;
			value: string;
		};
		compatibleOffering: string[];
		liferayPackages: {
			version: string;
			file: any[];
		}[];
		resourceRequirements: {
			cpu?: string;
			ram?: string;
		};
	};
	catalogId: number;
	loading: boolean;
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
	references: {
		imagesToDelete: string[];
		vocabulariesAndCategories: any;
	};
	termsAndConditions: boolean;
};

const newAppInitialState: NewAppInitialState = {
	build: {
		appType: {
			id: 0,
			value: '',
		},
		compatibleOffering: [],
		liferayPackages: [],
		resourceRequirements: {
			cpu: '',
			ram: '',
		},
	},
	catalogId: 0,
	loading: false,
	productId: 0,
	profile: {
		categories: [],
		description: '',
		file: {} as UploadedFile,
		name: '',
		tags: [],
	},
	references: {imagesToDelete: [], vocabulariesAndCategories: {}},
	termsAndConditions: false,
};

export type AppActions =
	ActionMap<NewAppPayload>[keyof ActionMap<NewAppPayload>];

const filterProductVocabularies = (product: Product, vocabulary: string) =>
	product.categories
		.filter(
			(category) =>
				category.vocabulary.toLowerCase() === vocabulary.toLowerCase()
		)
		.map(({id, name}) => ({label: name, value: `${id}`}));

const reducer = (state: NewAppInitialState, action: AppActions) => {
	switch (action.type) {
		case NewAppTypes.SET_BUILD: {
			return {
				...state,
				build: {
					...state.build,
					...action.payload,
				},
			};
		}

		case NewAppTypes.SET_DELETE_IMAGE: {
			return {
				...state,
				references: {
					...state.references,
					imagesToDelete: [
						...state.references.imagesToDelete,
						action.payload,
					],
				},
			};
		}

		case NewAppTypes.SET_LOADING: {
			return {...state, loading: action.payload};
		}

		case NewAppTypes.SET_PRODUCT_ID: {
			return {
				...state,
				productId: action.payload,
			};
		}

		case NewAppTypes.SET_CONTEXT: {
			const newState = {...state};
			const _product = action.payload;
			const productSpecifications = _product.productSpecifications || [];

			const specificationsMap = new Map<string, string>();

			for (const productSpecification of productSpecifications) {
				specificationsMap.set(
					productSpecification.specificationKey,
					productSpecification.value.en_US || ''
				);
			}

			const appIcon = (_product.images ?? []).find(({tags}) =>
				tags?.includes(PRODUCT_TAGS.SOLUTION_PROFILE_APP_ICON)
			);

			return {
				...state,
				...newState,
				_product,
				profile: {
					categories: filterProductVocabularies(
						_product,
						ProductVocabulary.SOLUTION_CATEGORY
					),
					description: _product.description.en_US,
					file: {
						changed: false,
						fileName: appIcon?.title?.en_US as string,
						id: appIcon?.externalReferenceCode as unknown as string,
						preview: _product.thumbnail,
						progress: 100,
						uploaded: true,
					},
					name: _product.name.en_US,
					tags: filterProductVocabularies(
						_product,
						ProductVocabulary.SOLUTION_TAGS
					),
				} as NewAppInitialState['profile'],
			};
		}

		case NewAppTypes.SET_PROFILE: {
			return {
				...state,
				profile: {
					...state.profile,
					...action.payload,
				},
			};
		}

		case NewAppTypes.SET_PRODUCT: {
			return {
				...state,
				_product: action.payload,
			};
		}

		case NewAppTypes.SET_TERMS_AND_CONDITIONS: {
			return {...state, termsAndConditions: action.payload};
		}

		default:
			return state;
	}
};

export const NewAppContext = createContext<
	[NewAppInitialState, (param: AppActions) => void]
>([newAppInitialState, () => null]);

type NewAppContextProviderProps = {
	catalogId: number;
	children: ReactNode;
};

export default function NewAppContextProvider({
	catalogId,
	children,
}: NewAppContextProviderProps) {
	const [state, dispatch] = useReducer(reducer, newAppInitialState);
	const {productId} = useParams();
	const {data = {}, isLoading} = useGetVocabulariesAndCategories([
		ProductVocabulary.PRODUCT_TYPE,
		ProductVocabulary.APP_CATEGORY,
		ProductVocabulary.APP_TAGS,
	]);

	useEffect(() => {
		if (!productId) {
			return;
		}

		// TO DO - GET PRODUCT
	}, [productId]);

	if (isLoading) {
		return <Loading />;
	}

	return (
		<NewAppContext.Provider
			value={[
				{
					...state,
					catalogId,
					references: {
						...state.references,
						vocabulariesAndCategories: data,
					},
				},
				dispatch,
			]}
		>
			{state.loading && (
				<Loading.FullScreen>
					Hang tight, the submission of <b>{state.profile.name}</b> is
					being sent to <b>Liferay</b>
				</Loading.FullScreen>
			)}

			{children}
		</NewAppContext.Provider>
	);
}

export function useNewAppContext() {
	return useContext(NewAppContext);
}
