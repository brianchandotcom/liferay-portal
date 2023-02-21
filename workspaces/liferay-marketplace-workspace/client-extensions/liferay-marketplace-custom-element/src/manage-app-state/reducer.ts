import { InitialStateProps } from './AppManageState';
import { TYPES } from './actionTypes';

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
			const { appId, appProductId, appERC, appWorkflowStatusInfo } =
				action.payload.value;

			return {
				...state,
				appId,
				appProductId,
				appERC,
				appWorkflowStatusInfo,
			};
		}
		case TYPES.UPDATE_APP_BUILD: {
			const appBuild = action.payload.value;

			return { ...state, appBuild };
		}
		case TYPES.UPDATE_APP_CATEGORIES: {
			const appCategories = action.payload.value;

			return { ...state, appCategories };
		}
		case TYPES.UPDATE_APP_DESCRIPTION: {
			const appDescription = action.payload.value;

			return { ...state, appDescription };
		}
		case TYPES.UPDATE_APP_DOCUMENTATION_URL: {
			const appDocumentationURL = action.payload.value;

			return { ...state, appDocumentationURL };
		}
		case TYPES.UPDATE_APP_INSTALLATION_AND_UNINSTALLATION_GUIDE_URL: {
			const appInstallationGuideURL = action.payload.value;

			return { ...state, appInstallationGuideURL };
		}
		case TYPES.UPDATE_APP_LICENSE: {
			const appLicense = action.payload.value;

      		return { ...state, appLicense };
		}
		case TYPES.UPDATE_APP_LICENSE_PRICE: {
			const appLicensePrice = action.payload.value;

      		return { ...state, appLicensePrice };
		}
		case TYPES.UPDATE_APP_LOGO: {
			const appLogo = action.payload.file;

			return { ...state, appLogo };
		}
		case TYPES.UPDATE_CATALOG_ID: {
			const catalogId = action.payload.value;

			return { ...state, catalogId };
		}
		case TYPES.UPLOAD_BUILD_ZIP_FILES: {
			const buildZIPFiles = action.payload.files;

			return { ...state, buildZIPFiles };
		}
		case TYPES.UPDATE_APP_LXC_COMPATIBILITY: {
			const LXC_Compatibility = action.payload.value;

			return { ...state, appType: LXC_Compatibility };
		}
		case TYPES.UPDATE_APP_NAME: {
			const appName = action.payload.value;

			return { ...state, appName };
		}
		case TYPES.UPDATE_APP_NOTES: {
			const appNotes = action.payload.value;

			return { ...state, appNotes };
		}
		case TYPES.UPDATE_APP_PRICE_MODEL: {
			const priceModel = action.payload.value;

			return { ...state, priceModel };
		}
		case TYPES.UPDATE_APP_PUBLISHER_WEBSITE_URL: {
			const publisherWebsiteURL = action.payload.value;

			return { ...state, publisherWebsiteURL };
		}
		case TYPES.UPLOAD_APP_STOREFRONT_IMAGES: {
			const appStorefrontImages = action.payload.files;

      		return {...state, appStorefrontImages};
		}
		case TYPES.UPDATE_APP_SUPPORT_URL: {
			const supportURL = action.payload.value;

			return { ...state, supportURL };
		}
		case TYPES.UPDATE_APP_TAGS: {
			return state;
		}
		case TYPES.UPDATE_APP_TRIAL_INFO: {
			const dayTrial = action.payload.value;

      		return { ...state, dayTrial };
		}
		case TYPES.UPDATE_APP_USAGE_TERMS_URL: {
			const appUsageTermsURL = action.payload.value;

			return { ...state, appUsageTermsURL };
		}
		case TYPES.UPDATE_APP_VERSION: {
			const appVersion = action.payload.value;

			return { ...state, appVersion };
		}
		default:
			return state;
	}
}
