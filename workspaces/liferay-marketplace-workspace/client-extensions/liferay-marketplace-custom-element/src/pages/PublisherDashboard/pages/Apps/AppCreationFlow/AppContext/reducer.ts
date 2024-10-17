/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LicenseTier} from '../../../../../../enums/licenseTier';
import {InitialStateProps, LicensePrice} from './AppManageState';
import {TYPES} from './actionTypes';

export type TAction = {
	payload?: any;
	type: TYPES;
};

const sortLicenses = (
	firstPriceTier: LicensePrice,
	secondPriceTier: LicensePrice
) => {
	return firstPriceTier.key - secondPriceTier.key;
};

export function appReducer(state: InitialStateProps, action: TAction) {
	switch (action.type) {
		case TYPES.SUBMIT_APP: {
			return state;
		}
		case TYPES.SUBMIT_APP_PROFILE: {
			const {
				appERC,
				appId,
				appProductId,
				appWorkflowStatusInfo,
				virtualSettingId,
			} = action.payload.value;

			return {
				...state,
				appERC,
				appId,
				appProductId,
				appWorkflowStatusInfo,
				virtualSettingId,
			};
		}

		case TYPES.UPDATE_APP_BUILD: {
			const appBuild = action.payload.value;

			return {...state, appBuild};
		}

		case TYPES.UPDATE_APP_CATEGORIES: {
			const appCategories = action.payload.value;

			return {...state, appCategories};
		}

		case TYPES.UPDATE_APP_DESCRIPTION: {
			const appDescription = action.payload.value;

			return {...state, appDescription};
		}

		case TYPES.UPDATE_APP_DOCUMENTATION_URL: {
			const {id, value} = action.payload;

			return {
				...state,
				appDocumentationURL: {
					id,
					value,
				},
			};
		}

		case TYPES.UPDATE_APP_INSTALLATION_AND_UNINSTALLATION_GUIDE_URL: {
			const {id, value} = action.payload;

			return {...state, appInstallationGuideURL: {id, value}};
		}

		case TYPES.UPDATE_APP_LICENSE: {
			const {id, value} = action.payload;

			return {
				...state,
				appLicense: {
					id,
					value,
				},
			};
		}

		case TYPES.ADD_APP_LICENSE_PRICE: {
			const licenseTier: LicenseTier = action.payload.licenseTier;
			const sortedOldLicensePrice = {
				developer: state.appLicensePrice.developer.sort(sortLicenses),
				standard: state.appLicensePrice.standard.sort(sortLicenses),
			};

			if (!sortedOldLicensePrice[licenseTier].length) {
				return {
					...state,
					appLicensePrice: {
						...sortedOldLicensePrice,
						[licenseTier]: [{key: 1, value: 0}],
					},
				};
			}

			const newKey =
				sortedOldLicensePrice[licenseTier].slice(-1)[0].key + 1;
			const newPriceTier = {key: newKey, value: 0};

			return {
				...state,
				appLicensePrice: {
					...sortedOldLicensePrice,
					[licenseTier]: [
						...sortedOldLicensePrice[licenseTier],
						newPriceTier,
					],
				},
			};
		}

		case TYPES.DELETE_APP_LICENSE_PRICE: {
			const licenseTier: LicenseTier = action.payload.licenseTier;
			const sortedOldLicensePrice = {
				developer: state.appLicensePrice.developer.sort(sortLicenses),
				standard: state.appLicensePrice.standard.sort(sortLicenses),
			};

			const filteredLicensePriceTier = sortedOldLicensePrice[
				licenseTier
			].filter(
				(priceTier: {key: any}) => priceTier.key !== action.payload.key
			);

			return {
				...state,
				appLicensePrice: {
					...sortedOldLicensePrice,
					[licenseTier]: filteredLicensePriceTier,
				},
			};
		}

		case TYPES.UPDATE_APP_LICENSE_PRICES: {
			const licenseTier: LicenseTier = action.payload.licenseTier;
			const oldLicensePrice = state.appLicensePrice;

			const newLicensePrices = [...(oldLicensePrice[licenseTier] ?? [])];
			newLicensePrices[action.payload.index] = action.payload.price;

			return {
				...state,
				appLicensePrice: {
					...oldLicensePrice,
					[licenseTier]: newLicensePrices,
				},
			};
		}

		case TYPES.UPDATE_APP_LOGO: {
			const appLogo = action.payload.file;

			return {...state, appLogo};
		}

		case TYPES.UPDATE_CATALOG_ID: {
			const catalogId = action.payload.value;

			return {...state, catalogId};
		}

		case TYPES.UPDATE_BUILD_PACKAGE_FILES: {
			return {
				...state,
				buildAppPackages: action.payload,
			};
		}

		case TYPES.UPLOAD_BUILD_PACKAGE_FILES: {
			const {files, isRemoved, versionName} = action.payload;

			if (isRemoved) {
				const newPackage = {...state};
				delete newPackage.buildAppPackages[versionName];

				return newPackage;
			}

			return {
				...state,
				buildAppPackages: {
					...state.buildAppPackages,
					[versionName]: files,
				},
			};
		}

		case TYPES.UPDATE_APP_LXC_COMPATIBILITY: {
			const {id, value} = action.payload;

			return {
				...state,
				appType: {
					id,
					value,
				},
			};
		}

		case TYPES.UPDATE_APP_NAME: {
			const appName = action.payload.value;

			return {...state, appName};
		}

		case TYPES.UPDATE_APP_NOTES: {
			const {value} = action.payload;

			return {
				...state,
				appNotes: value,
			};
		}

		case TYPES.UPDATE_APP_PRICE_MODEL: {
			const {id, value} = action.payload;

			return {
				...state,
				priceModel: {
					id,
					value,
				},
			};
		}

		case TYPES.UPDATE_APP_PUBLISHER_WEBSITE_URL: {
			const {id, value} = action.payload;

			return {
				...state,
				publisherWebsiteURL: {
					id,
					value,
				},
			};
		}

		case TYPES.UPLOAD_APP_STOREFRONT_IMAGES: {
			const appStorefrontImages = action.payload.files;

			return {...state, appStorefrontImages};
		}

		case TYPES.UPDATE_APP_SUPPORT_EMAIL: {
			const {id, value} = action.payload;

			return {
				...state,
				supportEmail: {
					id,
					value,
				},
			};
		}

		case TYPES.UPDATE_APP_SUPPORT_PHONE: {
			const {id, value} = action.payload;

			return {
				...state,
				supportPhone: {
					id,
					value,
				},
			};
		}

		case TYPES.UPDATE_APP_SUPPORT_URL: {
			const {id, value} = action.payload;

			return {
				...state,
				supportURL: {
					id,
					value,
				},
			};
		}

		case TYPES.UPDATE_APP_TAGS: {
			const appTags = action.payload.value;

			return {...state, appTags};
		}

		case TYPES.UPDATE_APP_TRIAL_INFO: {
			const dayTrial = action.payload.value;

			return {...state, dayTrial};
		}

		case TYPES.UPDATE_APP_USAGE_TERMS_URL: {
			const {id, value} = action.payload;

			return {
				...state,
				appUsageTermsURL: {
					id,
					value,
				},
			};
		}

		case TYPES.UPDATE_APP_VERSION: {
			const {value} = action.payload;

			return {
				...state,
				appVersion: value,
			};
		}

		case TYPES.UPDATE_OPTION_ID: {
			const optionId = action.payload.value;

			return {...state, optionId};
		}

		case TYPES.UPDATE_PRODUCT_OPTION_ID: {
			const productOptionId = action.payload.value;

			return {...state, productOptionId};
		}

		case TYPES.UPDATE_PRODUCT_OPTION_VALUES_ID: {
			return {...state, optionValuesId: action.payload};
		}

		case TYPES.UPDATE_SKU_TRIAL_ID: {
			const skuTrialId = action.payload.value;

			return {...state, skuTrialId};
		}

		case TYPES.UPDATE_SKU_VERSION_ID: {
			const skuVersionId = action.payload.value;

			return {...state, skuVersionId};
		}

		case TYPES.UPDATE_RESOURCE_REQUIREMENTS: {
			const {key, value} = action.payload;

			return {
				...state,
				resourceRequirements: {
					...state.resourceRequirements,
					[key]: value,
				},
			};
		}

		case TYPES.RESET_APP_PACKAGES: {
			return {
				...state,
				buildAppPackages: {},
			};
		}

		default:
			return state;
	}
}
