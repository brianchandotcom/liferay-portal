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
import {PRODUCT_SPECIFICATION_KEY, PRODUCT_TAGS} from '../enums/Product';
import {ProductVocabulary} from '../enums/ProductVocabulary';
import {useGetVocabulariesAndCategories} from '../hooks/data/useGetVocabulariesAndCategories';
import HeadlessCommerceAdminCatalogImpl from '../services/rest/HeadlessCommerceAdminCatalog';
import {safeJSONParse} from '../utils/util';

export enum BLOCK_DIRECTIONS {
	DELETE,
	MOVE_DOWN,
	MOVE_TO_BOTTOM,
	MOVE_TO_TOP,
	MOVE_UP,
}

export type HeaderContentTypeEmbeded = {
	content: {
		headerVideoDescription?: string;
		headerVideoUrl: string;
	};
	type: 'embed-video-url';
};

export type HeaderContentTypeImages = {
	content: {
		headerImages: UploadedFile[];
	};
	type: 'upload-images';
};

export type TextBlock = {
	content: {
		description: string;
		title: string;
	};
	type: 'text-block';
};

export type TextImageBlock = {
	content: {
		description: string;
		files: UploadedFile[];
		title: string;
	};
	type: 'text-images-block';
};

export type TextVideoBlock = {
	content: {
		description: string;
		title: string;
		videoDescription: string;
		videoUrl: string;
	};
	type: 'text-video-block';
};

export type ContentBlock = TextBlock | TextImageBlock | TextVideoBlock;

export type HeaderContentType =
	| HeaderContentTypeEmbeded
	| HeaderContentTypeImages;

export enum SolutionTypes {
	SET_BLOCK_MOVE = 'SET_BLOCK_MOVE',
	SET_CLEANUP = 'SET_CLEANUP',
	SET_COMPANY = 'SET_COMPANY',
	SET_CONTACT_US = 'SET_CONTACT_US',
	SET_CONTEXT = 'SET_CONTEXT',
	SET_DELETE_IMAGE = 'SET_DELETE_IMAGE',
	SET_DETAILS = 'SET_DETAILS',
	SET_HEADER = 'SET_HEADER',
	SET_LOADING = 'SET_LOADING',
	SET_NEW_BLOCK = 'SET_NEW_BLOCK',
	SET_PRODUCT = 'SET_PRODUCT',
	SET_PRODUCT_ID = 'SET_PRODUCT_ID',
	SET_PROFILE = 'SET_PROFILE',
	SET_TERMS_AND_CONDITIONS = 'SET_TERMS_AND_CONDITIONS',
	SET_UPDATE_BLOCK = 'SET_UPDATE_BLOCK',
}

type SolutionPayload = {
	[SolutionTypes.SET_BLOCK_MOVE]: {
		direction: BLOCK_DIRECTIONS;
		index: number;
	};
	[SolutionTypes.SET_CLEANUP]: undefined;
	[SolutionTypes.SET_COMPANY]: Partial<{
		description: string;
		email: string;
		phone: string;
		website: string;
	}>;
	[SolutionTypes.SET_CONTACT_US]: string;
	[SolutionTypes.SET_CONTEXT]: Product;
	[SolutionTypes.SET_DELETE_IMAGE]: string;
	[SolutionTypes.SET_DETAILS]: ContentBlock[];
	[SolutionTypes.SET_HEADER]: Partial<{
		contentType: HeaderContentType;
		description: string;
		title: string;
	}>;
	[SolutionTypes.SET_LOADING]: boolean;
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
	[SolutionTypes.SET_TERMS_AND_CONDITIONS]: boolean;
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
		contentType: HeaderContentType;
		description: string;
		title: string;
	};
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
		contentType: {
			content: {
				headerImages: [] as UploadedFile[],
			},
			type: 'upload-images',
		},
		description: '',
		title: '',
	},
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
	ActionMap<SolutionPayload>[keyof ActionMap<SolutionPayload>];

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

		case SolutionTypes.SET_DELETE_IMAGE: {
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

		case SolutionTypes.SET_LOADING: {
			return {...state, loading: action.payload};
		}

		case SolutionTypes.SET_PRODUCT_ID: {
			return {
				...state,
				productId: action.payload,
			};
		}

		case SolutionTypes.SET_CONTEXT: {
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

			const solutionHeaderImages = (_product.images ?? []).filter(
				({tags}) => tags?.includes(PRODUCT_TAGS.SOLUTION_HEADER)
			);

			let contentType = {
				content: {
					headerImages: solutionHeaderImages.map(
						({externalReferenceCode, src, title}) => ({
							changed: false,
							fileName: title.en_US,
							id: externalReferenceCode,
							imageDescription: title.en_US,
							preview: new URL(src).pathname,
							progress: 100,
							uploaded: true,
						})
					),
				},
				type: 'upload-images',
			} as HeaderContentType;

			const headerVideoUrl = specificationsMap.get(
				PRODUCT_SPECIFICATION_KEY.SOLUTION_HEADER_VIDEO_URL
			);

			if (headerVideoUrl) {
				contentType = {
					content: {
						headerVideoDescription: specificationsMap.get(
							PRODUCT_SPECIFICATION_KEY.SOLUTION_HEADER_VIDEO_DESCRIPTION
						),
						headerVideoUrl,
					},
					type: 'embed-video-url',
				} as HeaderContentTypeEmbeded;
			}

			const solutionCompanyEmail = specificationsMap.get(
				PRODUCT_SPECIFICATION_KEY.SOLUTION_COMPANY_EMAIL
			);

			const company = {...solutionInitialState.company};

			if (solutionCompanyEmail) {
				company.email = solutionCompanyEmail;

				company.description =
					specificationsMap.get(
						PRODUCT_SPECIFICATION_KEY.SOLUTION_COMPANY_DESCRIPTION
					) || '';

				company.phone =
					specificationsMap.get(
						PRODUCT_SPECIFICATION_KEY.SOLUTION_COMPANY_PHONE
					) || '';

				company.website =
					specificationsMap.get(
						PRODUCT_SPECIFICATION_KEY.SOLUTION_COMPANY_WEBSITE
					) || '';
			}

			const blockDetails = specificationsMap.get(
				PRODUCT_SPECIFICATION_KEY.SOLUTION_DETAILS_BLOCKS
			);

			if (blockDetails) {
				const solutionDetailsImages = _product.images.filter(({tags}) =>
					tags?.includes(PRODUCT_TAGS.SOLUTION_DETAILS)
				);

				const blocks = safeJSONParse(
					blockDetails,
					solutionInitialState.details
				) as ContentBlock[];

				const newBlocks = blocks.map((block) => {
					if (block.type === 'text-images-block') {
						return {
							...block,
							content: {
								...block.content,
								files: block.content.files?.map((file) => {
									const image = solutionDetailsImages.find(
										({externalReferenceCode}) =>
											externalReferenceCode ===
											(file as unknown as string)
									);

									const newFile = {
										changed: false,
										fileName: image?.title?.en_US,
										id: image?.externalReferenceCode,
										imageDescription: image?.title?.en_US,
										preview: image?.src
											? new URL(image.src).pathname
											: '',
										progress: 100,
										uploaded: true,
									};

									return newFile as any;
								}),
							},
						};
					}

					return block;
				});

				solutionDetailsImages;

				newState.details = newBlocks;
			}

			newState.contactUs =
				specificationsMap.get(
					PRODUCT_SPECIFICATION_KEY.SOLUTION_CONTACT_EMAIL
				) || '';

			return {
				...state,
				...newState,
				_product,
				company,
				header: {
					contentType,
					description: specificationsMap.get(
						PRODUCT_SPECIFICATION_KEY.SOLUTION_HEADER_DESCRIPTION
					),
					title: specificationsMap.get(
						PRODUCT_SPECIFICATION_KEY.SOLUTION_HEADER_TITLE
					),
				} as unknown as SolutionInitialState['header'],
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

		case SolutionTypes.SET_PRODUCT: {
			return {
				...state,
				_product: action.payload,
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

		case SolutionTypes.SET_BLOCK_MOVE: {
			const {direction, index} = action.payload;
			const blocks = [...state.details];

			const blockToMove = blocks[index];

			const moveActions = {
				[BLOCK_DIRECTIONS.MOVE_TO_TOP]: () => {
					blocks.splice(index, 1);
					blocks.unshift(blockToMove);
				},
				[BLOCK_DIRECTIONS.MOVE_TO_BOTTOM]: () => {
					blocks.splice(index, 1);
					blocks.push(blockToMove);
				},
				[BLOCK_DIRECTIONS.MOVE_UP]: () => {
					const newIndex = index - 1;

					blocks[index] = blocks[newIndex];
					blocks[newIndex] = blockToMove;
				},
				[BLOCK_DIRECTIONS.MOVE_DOWN]: () => {
					const newIndex = index + 1;

					blocks[index] = blocks[newIndex];
					blocks[newIndex] = blockToMove;
				},
				[BLOCK_DIRECTIONS.DELETE]: () => {
					blocks.splice(index, 1);
				},
			};

			moveActions[direction]();

			return {
				...state,
				details: blocks,
			};
		}

		case SolutionTypes.SET_TERMS_AND_CONDITIONS: {
			return {...state, termsAndConditions: action.payload};
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
	const {productId} = useParams();
	const {data = {}, isLoading} = useGetVocabulariesAndCategories([
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
			new URLSearchParams({
				nestedFields: 'attachments,images,productSpecifications',
			})
		)
			.then((response) =>
				dispatch({payload: response, type: SolutionTypes.SET_CONTEXT})
			)
			.catch(console.error);
	}, [productId]);

	if (isLoading) {
		return <Loading />;
	}

	return (
		<SolutionContext.Provider
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
		</SolutionContext.Provider>
	);
}

export function useSolutionContext() {
	return useContext(SolutionContext);
}
