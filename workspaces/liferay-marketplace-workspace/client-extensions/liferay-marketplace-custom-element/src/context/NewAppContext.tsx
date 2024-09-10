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
import {LicenseTier} from '../enums/licenseTier';
import {useGetVocabulariesAndCategories} from '../hooks/data/useGetVocabulariesAndCategories';

export type LicensePrice = {key: number; value: number};
export type LicenseType = 'Perpetual' | 'Subscription';

type LicensingPrices = {
	developer: {key: number; value: number}[];
	standard: {key: number; value: number}[];
};

export enum NewAppTypes {
	SET_BUILD = 'SET_BUILD',
	SET_CLEANUP = 'SET_CLEANUP',
	SET_CONTEXT = 'SET_CONTEXT',
	SET_DELETE_IMAGE = 'SET_DELETE_IMAGE',
	SET_LICENSING = 'SET_LICENSING',
	SET_LICENSING_ADD_PRICE = 'SET_LICENSING_ADD_PRICE',
	SET_LICENSING_DELETE_PRICE = 'SET_LICENSING_DELETE_PRICE',
	SET_LICENSING_UPDATE_PRICES = 'SET_LICENSING_UPDATE_PRICES',
	SET_LOADING = 'SET_LOADING',
	SET_PRICING = 'SET_PRICING',
	SET_PRODUCT = 'SET_PRODUCT',
	SET_PRODUCT_ID = 'SET_PRODUCT_ID',
	SET_PROFILE = 'SET_PROFILE',
	SET_STOREFRONT = 'SET_STOREFRONT',
	SET_SUPPORT = 'SET_SUPPORT',
	SET_TERMS_AND_CONDITIONS = 'SET_TERMS_AND_CONDITIONS',
	SET_VERSION = 'SET_VERSION',
}

export type NewAppInitialState = {
	_product?: Product;
	build: {
		cloudCompatible: boolean;
		compatibleOffering: string[];
		liferayPackages: {
			file: any[];
			version: string;
		}[];
		resourceRequirements: {
			cpu?: string;
			ram?: string;
		};
	};
	catalogId: number;
	licensing: {
		licenseType: LicenseType;
		prices: LicensingPrices;
		trial30Day: boolean;
	};
	loading: boolean;
	pricing: {
		priceModel: 'Free' | 'Paid';
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
		imagesToDelete: string[];
		vocabulariesAndCategories: any;
	};
	storefront: {
		images: UploadedFile[];
	};
	support: {
		appUsageTermsURL: string;
		documentationURL: string;
		email: string;
		installationGuideURL: string;
		phone: string;
		publisherWebsiteURL: string;
		url: string;
	};
	termsAndConditions: boolean;
	version: {
		notes: string;
		version: string;
	};
};

type NewAppPayload = {
	[NewAppTypes.SET_BUILD]: Partial<NewAppInitialState['build']>;
	[NewAppTypes.SET_CLEANUP]: undefined;
	[NewAppTypes.SET_CONTEXT]: Product;
	[NewAppTypes.SET_DELETE_IMAGE]: string;
	[NewAppTypes.SET_LICENSING]: Partial<NewAppInitialState['licensing']>;
	[NewAppTypes.SET_LICENSING_ADD_PRICE]: {licenseTier: LicenseTier};
	[NewAppTypes.SET_LICENSING_DELETE_PRICE]: {
		key: number;
		licenseTier: LicenseTier;
	};
	[NewAppTypes.SET_LICENSING_UPDATE_PRICES]: {
		index: number;
		licenseTier: LicenseTier;
		price: LicensePrice;
	};
	[NewAppTypes.SET_LOADING]: boolean;
	[NewAppTypes.SET_PRICING]: Partial<NewAppInitialState['pricing']>;
	[NewAppTypes.SET_PRODUCT]: Product;
	[NewAppTypes.SET_PRODUCT_ID]: number;
	[NewAppTypes.SET_PROFILE]: Partial<NewAppInitialState['profile']>;
	[NewAppTypes.SET_STOREFRONT]: Partial<NewAppInitialState['storefront']>;
	[NewAppTypes.SET_SUPPORT]: Partial<NewAppInitialState['support']>;
	[NewAppTypes.SET_TERMS_AND_CONDITIONS]: boolean;
	[NewAppTypes.SET_VERSION]: Partial<NewAppInitialState['version']>;
};

const newAppInitialState: NewAppInitialState = {
	build: {
		cloudCompatible: null as unknown as boolean,
		compatibleOffering: [],
		liferayPackages: [],
		resourceRequirements: {
			cpu: '',
			ram: '',
		},
	},
	catalogId: 0,
	licensing: {
		licenseType: 'Perpetual',
		prices: {
			developer: [],
			standard: [{key: 1, value: 0}],
		},
		trial30Day: false,
	},
	loading: false,
	pricing: {
		priceModel: '' as 'Free',
	},
	productId: 0,
	profile: {
		categories: [],
		description: '',
		file: {} as UploadedFile,
		name: '',
		tags: [],
	},
	references: {imagesToDelete: [], vocabulariesAndCategories: {}},
	storefront: {images: []},
	support: {
		appUsageTermsURL: '',
		documentationURL: '',
		email: '',
		installationGuideURL: '',
		phone: '',
		publisherWebsiteURL: '',
		url: '',
	},
	termsAndConditions: false,
	version: {
		notes: '',
		version: '',
	},
};

export type AppActions =
	ActionMap<NewAppPayload>[keyof ActionMap<NewAppPayload>];

const sortLicenses = (
	firstPriceTier: LicensePrice,
	secondPriceTier: LicensePrice
) => {
	return firstPriceTier.key - secondPriceTier.key;
};

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

		case NewAppTypes.SET_VERSION: {
			return {
				...state,
				version: {
					...state.version,
					...action.payload,
				},
			};
		}

		case NewAppTypes.SET_PRICING: {
			return {
				...state,
				pricing: {
					...state.pricing,
					...action.payload,
				},
			};
		}

		case NewAppTypes.SET_SUPPORT: {
			return {
				...state,
				support: {
					...state.support,
					...action.payload,
				},
			};
		}

		case NewAppTypes.SET_STOREFRONT: {
			return {
				...state,
				storefront: {
					...state.storefront,
					...action.payload,
				},
			};
		}

		case NewAppTypes.SET_LICENSING: {
			return {
				...state,
				licensing: {
					...state.licensing,
					...action.payload,
				},
			};
		}

		case NewAppTypes.SET_LICENSING_ADD_PRICE: {
			const licenseTier: LicenseTier = action.payload.licenseTier;

			const sortedOldLicensePrice = {
				developer: state.licensing.prices.developer.sort(sortLicenses),
				standard: state.licensing.prices.standard.sort(sortLicenses),
			};

			if (!sortedOldLicensePrice[licenseTier].length) {
				return {
					...state,
					licensing: {
						...state.licensing,
						prices: {
							...state.licensing.prices,
							...sortedOldLicensePrice,
							[licenseTier]: [{key: 1, value: 0}],
						},
					},
				};
			}

			const newKey =
				sortedOldLicensePrice[licenseTier].slice(-1)[0].key + 1;
			const newPriceTier = {key: newKey, value: 0};

			return {
				...state,
				licensing: {
					...state.licensing,
					prices: {
						...state.licensing.prices,
						...sortedOldLicensePrice,
						[licenseTier]: [
							...sortedOldLicensePrice[licenseTier],
							newPriceTier,
						],
					},
				},
			};
		}

		case NewAppTypes.SET_LICENSING_DELETE_PRICE: {
			const licenseTier: LicenseTier = action.payload.licenseTier;

			const sortedOldLicensePrice = {
				developer: state.licensing.prices.developer.sort(sortLicenses),
				standard: state.licensing.prices.standard.sort(sortLicenses),
			};

			const filteredLicensePriceTier = sortedOldLicensePrice[
				licenseTier
			].filter(
				(priceTier: {key: any}) => priceTier.key !== action.payload.key
			);

			return {
				...state,
				licensing: {
					...state.licensing,
					prices: {
						...state.licensing.prices,
						...sortedOldLicensePrice,
						[licenseTier]: filteredLicensePriceTier,
					},
				},
			};
		}

		case NewAppTypes.SET_LICENSING_UPDATE_PRICES: {
			const licenseTier: LicenseTier = action.payload.licenseTier;
			const oldLicensePrice = state.licensing.prices;

			const newLicensePrices = [...(oldLicensePrice[licenseTier] ?? [])];
			newLicensePrices[action.payload.index] = action.payload.price;

			return {
				...state,
				licensing: {
					...state.licensing,
					prices: {
						...state.licensing.prices,
						...oldLicensePrice,
						[licenseTier]: newLicensePrices,
					},
				},
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
	[NewAppInitialState, (_param: AppActions) => void]
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
		ProductVocabulary.APP_CATEGORY,
		ProductVocabulary.APP_TAGS,
		ProductVocabulary.LIFERAY_PLATFORM_OFFERING,
		ProductVocabulary.PRODUCT_TYPE,
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
