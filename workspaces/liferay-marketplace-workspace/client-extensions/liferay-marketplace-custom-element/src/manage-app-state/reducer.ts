import {InitialStateProps} from './AppManageState';
import {TYPES} from './actionTypes';

export type TAction = {
	payload?: any;
	type: TYPES;
};

export function appReducer(state: InitialStateProps, action: TAction) {
	switch (action.type) {
		case TYPES.SUBMIT_APP: {
			return state;
		}
		case TYPES.SUBMIT_APP_PROFILE: {
			const {appERC, appId, appProductId, appWorkflowStatusInfo} =
				action.payload.value;

			return {
				...state,
				appERC,
				appId,
				appProductId,
				appWorkflowStatusInfo,
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
		case TYPES.UPDATE_APP_LICENSE_PRICE: {
			const appLicensePrice = action.payload.value;

			return {...state, appLicensePrice};
		}
		case TYPES.UPDATE_APP_LOGO: {
			const appLogo = action.payload.file;

			return {...state, appLogo};
		}
		case TYPES.UPDATE_CATALOG_ID: {
			const catalogId = action.payload.value;

			return {...state, catalogId};
		}
		case TYPES.UPLOAD_BUILD_ZIP_FILES: {
			const buildZIPFiles = action.payload.files;

			return {...state, buildZIPFiles};
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
			const noOptionId = action.payload.noOptionId;
			const yesOptionId = action.payload.yesOptionId;

			const optionValuesId = {noOptionId, yesOptionId};

			return {...state, optionValuesId};
		}

		case TYPES.UPDATE_SKU_TRIAL_ID: {
			const skuTrialId = action.payload.value;

			return {...state, skuTrialId};
		}

		case TYPES.UPDATE_SKU_VERSION_ID: {
			const skuVersionId = action.payload.value;

			return {...state, skuVersionId};
		}

		default:
			return state;
	}
}
