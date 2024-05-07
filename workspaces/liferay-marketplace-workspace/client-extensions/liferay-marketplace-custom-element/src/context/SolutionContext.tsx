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
import {PRODUCT_SPECIFICATION_KEY} from '../enums/Product';
import {ProductVocabulary} from '../enums/ProductVocabulary';
import {useGetVocabulariesAndCategories} from '../hooks/data/useGetVocabulariesAndCategories';
import HeadlessCommerceAdminCatalogImpl from '../services/rest/HeadlessCommerceAdminCatalog';

export type ContentBlock =
	| {
			content: {
				description: string;
				title: string;
			};
			type: 'text-block';
	  }
	| {
			content: {
				description: string;
				files: UploadedFile[];
				title: string;
			};
			type: 'text-images-block';
	  }
	| {
			content: {
				description: string;
				title: string;
				video: string;
			};
			type: 'text-video-block';
	  };

export enum SolutionTypes {
	SET_CLEANUP = 'SET_CLEANUP',
	SET_COMPANY = 'SET_COMPANY',
	SET_CONTACT_US = 'SET_CONTACT_US',
	SET_DETAILS = 'SET_DETAILS',
	SET_HEADER = 'SET_HEADER',
	SET_NEW_BLOCK = 'SET_NEW_BLOCK',
	SET_PRODUCT = 'SET_PRODUCT',
	SET_PRODUCT_ID = 'SET_PRODUCT_ID',
	SET_PROFILE = 'SET_PROFILE',
	SET_UPDATE_BLOCK = 'SET_UPDATE_BLOCK',
}

type SolutionPayload = {
	[SolutionTypes.SET_CLEANUP]: undefined;
	[SolutionTypes.SET_COMPANY]: Partial<{
		description: string;
		email: string;
		phone: string;
		website: string;
	}>;
	[SolutionTypes.SET_CONTACT_US]: string;
	[SolutionTypes.SET_DETAILS]: ContentBlock[];
	[SolutionTypes.SET_HEADER]: Partial<{
		description: string;
		headerImages: UploadedFile[];
		headerVideo: string;
		radioValue: 'embed-video-url' | 'upload-images';
		title: string;
	}>;
	[SolutionTypes.SET_NEW_BLOCK]: ContentBlock;
	[SolutionTypes.SET_PRODUCT]: Product;
	[SolutionTypes.SET_PRODUCT_ID]: number;
	[SolutionTypes.SET_PROFILE]: Partial<{
		categories: any[];
		description: string;
		file: UploadedFile;
		name: string;
		tags: any[];
	}>;
	[SolutionTypes.SET_UPDATE_BLOCK]: {block: ContentBlock; index: number};
};

export type SolutionInitialState = {
	_product?: Product;
	catalogId: number;
	company: {
		description: string;
		email: string;
		phone: string;
		website: string;
	};
	contactUs: string;
	details: ContentBlock[];
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
	references: {
		vocabulariesAndCategories: any;
	};
};

const solutionInitialState: SolutionInitialState = {
	catalogId: 0,
	company: {
		description: '',
		email: '',
		phone: '',
		website: '',
	},
	contactUs: '',
	details: [],
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
	references: {vocabulariesAndCategories: {}},
};

export type AppActions = ActionMap<SolutionPayload>[keyof ActionMap<
	SolutionPayload
>];

const filterProductVocabularies = (product: Product, vocabulary: string) =>
	product.categories
		.filter(
			(category) =>
				category.vocabulary.toLowerCase() === vocabulary.toLowerCase()
		)
		.map(({id, name}) => ({label: name, value: `${id}`}));

const reducer = (state: SolutionInitialState, action: AppActions) => {
	switch (action.type) {
		case SolutionTypes.SET_COMPANY: {
			return {
				...state,
				company: {
					...state.company,
					...action.payload,
				},
			};
		}

		case SolutionTypes.SET_CONTACT_US: {
			return {
				...state,
				contactUs: action.payload,
			};
		}

		case SolutionTypes.SET_PRODUCT_ID: {
			return {
				...state,
				productId: action.payload,
			};
		}

		case SolutionTypes.SET_PRODUCT: {
			const _product = action.payload;

			const productSpecifications = _product.productSpecifications || [];

			const getSpecificationValue = (
				specificationKey: PRODUCT_SPECIFICATION_KEY
			) =>
				productSpecifications.find(
					(productSpecification) =>
						productSpecification.specificationKey ===
						specificationKey
				)?.value?.en_US;

			return {
				...state,
				_product,
				header: ({
					description: getSpecificationValue(
						PRODUCT_SPECIFICATION_KEY.SOLUTION_HEADER_DESCRIPTION
					),
					title: getSpecificationValue(
						PRODUCT_SPECIFICATION_KEY.SOLUTION_HEADER_TITLE
					),
				} as unknown) as SolutionInitialState['header'],
				profile: {
					categories: filterProductVocabularies(
						_product,
						ProductVocabulary.SOLUTION_CATEGORY
					),
					description: _product.description.en_US,
					file: {
						preview: _product.thumbnail,
					},
					name: _product.name.en_US,
					tags: filterProductVocabularies(
						_product,
						ProductVocabulary.SOLUTION_TAGS
					),
				} as SolutionInitialState['profile'],
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

		case SolutionTypes.SET_NEW_BLOCK: {
			return {
				...state,
				details: [...state.details, action.payload],
			};
		}

		case SolutionTypes.SET_UPDATE_BLOCK: {
			const details = state.details;

			const newDetails = details.map((detail, index) => {
				if (index === action.payload.index) {
					return action.payload.block;
				}

				return detail;
			});

			return {
				...state,
				details: newDetails,
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
	const [state, dispatch] = useReducer(reducer, solutionInitialState);
	const {id: productId} = useParams();

	const {data = {}} = useGetVocabulariesAndCategories([
		ProductVocabulary.PRODUCT_TYPE,
		ProductVocabulary.SOLUTION_CATEGORY,
		ProductVocabulary.SOLUTION_TAGS,
	]);

	useEffect(() => {
		if (!productId) {
			return;
		}

		HeadlessCommerceAdminCatalogImpl.getProduct(
			productId as string,
			new URLSearchParams({nestedFields: 'productSpecifications'})
		)
			.then((response) =>
				dispatch({payload: response, type: SolutionTypes.SET_PRODUCT})
			)
			.catch(console.error);
	}, [productId]);

	return (
		<SolutionContext.Provider
			value={[
				{
					...state,
					catalogId,
					references: {
						vocabulariesAndCategories: data,
					},
				},
				dispatch,
			]}
		>
			{children}
		</SolutionContext.Provider>
	);
}

export function useSolutionContext() {
	return useContext(SolutionContext);
}
