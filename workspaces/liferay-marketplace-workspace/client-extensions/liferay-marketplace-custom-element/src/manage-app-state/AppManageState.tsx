import React, {ReactNode, createContext, useContext, useReducer} from 'react';

import {UploadedFile} from '../components/FileList/FileList';
import {TAction, appReducer} from './reducer';

type Categories = {
	externalReferenceCode: string;
	id: number;
	name: string;
	vocabulary: string;
};

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
}

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
