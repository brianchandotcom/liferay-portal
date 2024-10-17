/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {ReactNode, createContext, useContext, useReducer} from 'react';

import {UploadedFile} from '../../../../../../components/FileList/FileList';
import {TAction, appReducer} from './reducer';

type Specification = {
	id: number;
	value: string;
};

type LicenceTiersPrices = {
	developer: LicensePrice[];
	standard: LicensePrice[];
};

export type LicensePrice = {key: number; value: number};

export type InitialStateProps = {
	appBuild: string;
	appCategories: Categories[];
	appDescription: string;
	appDocumentationURL: Specification;
	appERC: string;
	appId: string;
	appInstallationGuideURL: Specification;
	appLicense: Specification;
	appLicensePrice: LicenceTiersPrices;
	appLogo: UploadedFile;
	appName: string;
	appNotes: string;
	appProductId: number;
	appStorefrontImages: UploadedFile[];
	appTags: Categories[];
	appType: Specification;
	appUsageTermsURL: Specification;
	appVersion: string;
	appWorkflowStatusInfo: string;
	buildAppPackages: {[key: string]: UploadedFile[]};
	catalogId: number;
	dayTrial: string;
	optionId: number;
	optionValuesId: {noOptionId: number; yesOptionId: number};
	priceModel: Specification;
	productOptionId: number;
	publisherWebsiteURL: Specification;
	resourceRequirements: {
		cpu: number | string;
		ram: number | string;
	};
	skuTrialId: number;
	skuVersionId: number;
	supportEmail: Specification;
	supportPhone: Specification;
	supportURL: Specification;
	versionName?: string;
	virtualSettingId: string;
};

export type Sku = {id: number; sku: string};

export type PriceEntry = {priceEntryId: number; sku: {name: string}};

const initialState = {
	appBuild: 'upload',
	appCategories: [],
	appDescription: '',
	appLicense: {value: 'Perpetual'},
	appLicensePrice: {
		developer: [],
		standard: [{key: 1, value: 0}],
	},
	appName: '',
	appTags: [],
	appType: {value: ''},
	appUsageTermsURL: {value: ''},
	appVersion: '1.0',
	buildAppPackages: {},
	dayTrial: 'no',
	optionValuesId: {},
	priceModel: {value: 'Free'},
	publisherWebsiteURL: {value: ''},
	resourceRequirements: {
		cpu: '',
		ram: '',
	},
	supportEmail: {value: ''},
	supportPhone: {value: ''},
	supportURL: {value: ''},
} as unknown as InitialStateProps;

interface AppContextProps extends Array<InitialStateProps | Function> {
	0: typeof initialState;
	1: React.Dispatch<
		React.ReducerAction<React.Reducer<InitialStateProps, TAction>>
	>;
}

const AppContext = createContext({} as AppContextProps);

type AppContextProviderProps = {
	children: ReactNode;
};

export function AppContextProvider({children}: AppContextProviderProps) {
	const [state, dispatch] = useReducer<
		React.Reducer<InitialStateProps, TAction>
	>(appReducer, {...initialState});

	return (
		<AppContext.Provider value={[state, dispatch]}>
			{children}
		</AppContext.Provider>
	);
}

export function useAppContext() {
	return useContext(AppContext);
}
