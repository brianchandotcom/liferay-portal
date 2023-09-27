/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {ReactNode, createContext, useContext, useReducer} from 'react';

import {UploadedFile} from '../components/FileList/FileList';
import {TAction, appReducer} from './reducer';

type Specification = {
	id: number;
	value: string;
};

export interface InitialStateProps {
	appBuild: string;
	appCategories: Categories[];
	appDescription: string;
	appDocumentationURL: Specification;
	appERC: string;
	appId: string;
	appInstallationGuideURL: Specification;
	appLicense: Specification;
	appLicensePrice: string;
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
	buildZIPFiles: UploadedFile[];
	catalogId: number;
	dayTrial: string;
	gravatarAPI: string;
	optionId: number;
	optionValuesId: {noOptionId: number; yesOptionId: number};
	priceModel: Specification;
	productOptionId: number;
	publisherWebsiteURL: Specification;
	skuTrialId: number;
	skuVersionId: number;
	supportURL: Specification;
}

const initialState = {
	appBuild: 'upload',
	appLicense: {value: 'Perpetual'},
	appType: {value: 'cloud'},
	dayTrial: 'no',
	priceModel: {value: 'Paid'},
} as InitialStateProps;

interface AppContextProps extends Array<InitialStateProps | Function> {
	0: typeof initialState;
	1: React.Dispatch<
		React.ReducerAction<React.Reducer<InitialStateProps, TAction>>
	>;
}

const AppContext = createContext({} as AppContextProps);

interface AppContextProviderProps {
	children: ReactNode;
	gravatarAPI: string;
}

export function AppContextProvider({
	children,
	gravatarAPI,
}: AppContextProviderProps) {
	const [state, dispatch] = useReducer<
		React.Reducer<InitialStateProps, TAction>
	>(appReducer, {...initialState, gravatarAPI});

	return (
		<AppContext.Provider value={[state, dispatch]}>
			{children}
		</AppContext.Provider>
	);
}

export function useAppContext() {
	return useContext(AppContext);
}
