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
import {
	ProductSpecificationKey,
	ProductTags,
	ProductType,
	ProductVocabulary,
} from '../enums/Product';
import {LicenseTier} from '../enums/licenseTier';
import {useGetVocabulariesAndCategories} from '../hooks/data/useGetVocabulariesAndCategories';
import HeadlessCommerceAdminCatalogImpl from '../services/rest/HeadlessCommerceAdminCatalog';

export type LicensePrice = {key: number; value: number};
export type LicenseType = 'Perpetual' | 'Subscription';

type LicensingPrices = {
	[currency: string]: {
		developer: {
			[key: number]: number;
		};
		standard: {
			[key: number]: number;
		};
	};
};

export type PriceEntry = {
	hasTierPrice: boolean;
	id: number;
	price: number;
	priceEntryId?: number;
	priceListId: number;
	sku: string;
	skuExternalReferenceCode: string;
	skuId: number;
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
		appType: ProductType;
		compatibleOffering: string[];
		liferayPackages: {
			files: any[];
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
		areas: {
			label: string;
			value: string;
		}[];
		categories: {
			label: string;
			value: string;
		};
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
	[NewAppTypes.SET_LICENSING_ADD_PRICE]: {
		currency: string;
		licenseTier: LicenseTier;
	};
	[NewAppTypes.SET_LICENSING_DELETE_PRICE]: {
		currency: string;
		deleteCurrency?: boolean;
		key: number;
		licenseTier: LicenseTier;
	};
	[NewAppTypes.SET_LICENSING_UPDATE_PRICES]: {
		currency: string;
		index: number;
		licenseTier: LicenseTier;
		price: number;
		quantity?: number;
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
		appType: null as unknown as ProductType,
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
			USD: {
				developer: {},
				standard: {
					1: 0,
				},
			},
		},
		trial30Day: false,
	},
	loading: false,
	pricing: {
		priceModel: '' as 'Free',
	},
	productId: 0,
	profile: {
		areas: [],
		categories: {label: '', value: ''},
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

			// Reset the Liferay Packages if the App Type is changed

			if (
				action.payload.appType &&
				action.payload.appType !== state.build.appType
			) {
				state.build.liferayPackages = [];
			}

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
						...(state?.references?.imagesToDelete || []),
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

			const storeFrontImages = (_product.images ?? []).filter(
				({tags}) => !tags?.includes('app icon')
			);

			const appIcon = (_product.images ?? []).find(({tags}) =>
				tags?.includes(ProductTags.SOLUTION_PROFILE_APP_ICON)
			);

			return {
				...state,
				...newState,
				_product,
				build: {
					appType: specificationsMap.get(
						ProductSpecificationKey.APP_TYPE
					),
					compatibleOffering: [],
					liferayPackages: [],
					resourceRequirements: {
						cpu: specificationsMap.get(
							ProductSpecificationKey.APP_BUILD_NUMBER_OF_CPUS
						),
						ram: specificationsMap.get(
							ProductSpecificationKey.APP_BUILD_RAM_IN_GBS
						),
					},
				} as NewAppInitialState['build'],
				pricing: {
					priceModel: specificationsMap.get(
						ProductSpecificationKey.APP_PRICING_MODEL
					),
				} as NewAppInitialState['pricing'],
				profile: {
					areas: filterProductVocabularies(
						_product,
						ProductVocabulary.APP_CATEGORY
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
						ProductVocabulary.APP_TAGS
					),
				} as NewAppInitialState['profile'],
				storefront: {
					images: storeFrontImages.map(
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
				} as NewAppInitialState['storefront'],
				support: {
					appUsageTermsURL: specificationsMap.get(
						ProductSpecificationKey.APP_SUPPORT_USAGE_TERMS_URL
					),
					documentationURL: specificationsMap.get(
						ProductSpecificationKey.APP_SUPPORT_DOCUMENTATION_URL
					),
					email: specificationsMap.get(
						ProductSpecificationKey.APP_SUPPORT_EMAIL
					),
					installationGuideURL: specificationsMap.get(
						ProductSpecificationKey.APP_SUPPORT_INSTALLATION_GUIDE_URL
					),
					phone: specificationsMap.get(
						ProductSpecificationKey.APP_SUPPORT_PHONE
					),
					publisherWebsiteURL: specificationsMap.get(
						ProductSpecificationKey.APP_SUPPORT_PUBLISHER_WEBSITE_URL
					),
					url: specificationsMap.get(
						ProductSpecificationKey.APP_SUPPORT_URL
					),
				} as NewAppInitialState['support'],
				version: {
					notes: specificationsMap.get(
						ProductSpecificationKey.APP_VERSION
					),
					version: specificationsMap.get(
						ProductSpecificationKey.APP_VERSION_NOTES
					),
				} as NewAppInitialState['version'],
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
			const currency = action.payload.currency;
			const licenseTier: LicenseTier = action.payload.licenseTier;

			const oldPrices = state.licensing.prices;

			if (!oldPrices[currency]) {
				oldPrices[currency] = {};
			}
			if (!oldPrices[currency][licenseTier]) {
				oldPrices[currency][licenseTier] = {};
			}

			const currentPrices = oldPrices[currency][licenseTier];
			const newKey = Object.keys(currentPrices).length
				? Math.max(...Object.keys(currentPrices).map(Number)) + 1
				: 1;

			oldPrices[currency][licenseTier][newKey] = 0;

			return {
				...state,
				licensing: {
					...state.licensing,
					prices: {
						...oldPrices,
					},
				},
			};
		}

		case NewAppTypes.SET_LICENSING_DELETE_PRICE: {
			const {currency, deleteCurrency, key, licenseTier} = action.payload;

			const oldPrices = state.licensing.prices;

			if (key && licenseTier && !deleteCurrency) {
				if (!oldPrices[currency] || !oldPrices[currency][licenseTier]) {
					return state;
				}

				const updatedLicenseTierPrices = {
					...oldPrices[currency][licenseTier],
				};

				if (key in updatedLicenseTierPrices) {
					delete updatedLicenseTierPrices[key];
				}

				return {
					...state,
					licensing: {
						...state.licensing,
						prices: {
							...oldPrices,
							[currency]: {
								...oldPrices[currency],
								[licenseTier]: updatedLicenseTierPrices,
							},
						},
					},
				};
			}

			if (currency && deleteCurrency) {
				const updatedPrices = {...oldPrices};

				delete updatedPrices[currency];

				return {
					...state,
					licensing: {
						...state.licensing,
						prices: updatedPrices,
					},
				};
			}

			return state;
		}

		case NewAppTypes.SET_LICENSING_UPDATE_PRICES: {
			const {currency, index, licenseTier, price, quantity} =
				action.payload;

			const oldLicensePrice = state.licensing.prices;

			const updatedPrices = {
				...(oldLicensePrice[currency]?.[licenseTier] || {}),
			};

			const newKey = quantity ?? index;

			if (quantity && quantity !== index) {
				delete updatedPrices[index];
			}

			updatedPrices[newKey] = price;

			return {
				...state,
				licensing: {
					...state.licensing,
					prices: {
						...oldLicensePrice,
						[currency]: {
							...oldLicensePrice[currency],
							[licenseTier]: updatedPrices,
						},
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
	[NewAppInitialState, React.Dispatch<AppActions>]
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
		ProductVocabulary.APP_AREA,
		ProductVocabulary.APP_CATEGORY,
		ProductVocabulary.APP_TAGS,
		ProductVocabulary.LIFERAY_PLATFORM_OFFERING,
		ProductVocabulary.PRODUCT_TYPE,
	]);

	useEffect(() => {
		if (!productId) {
			return;
		}

		HeadlessCommerceAdminCatalogImpl.getProduct(
			productId as string,
			new URLSearchParams({
				nestedFields:
					'attachments,images,productSpecifications,productOptions,productVirtualSettings',
			})
		)
			.then((response) =>
				dispatch({
					payload: response,
					type: NewAppTypes.SET_CONTEXT,
				})
			)
			.catch(console.error);
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
