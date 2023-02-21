import React, {ReactNode, createContext, useContext, useReducer} from 'react';

import {UploadedFile} from '../components/FileList/FileList';
import {TAction, appReducer} from './reducer';

type Categories = {
	checked: boolean;
	label: string;
	value: string;
};

export interface InitialStateProps {
	appBuild: string;
	appCategories: Categories[];
	appDescription: string;
	appDocumentationURL: string;
	appERC: string;
	appId: string;
	appInstallationGuideURL: string;
	appLicense: string;
	appLicensePrice: string;
	appLogo: UploadedFile;
	appName: string;
	appNotes: string;
	appProductId: number;
	appStorefrontImages: UploadedFile[];
	appType: string;
	appUsageTermsURL: string;
	appVersion: string;
	appWorkflowStatusInfo: string;
	buildZIPFiles: UploadedFile[];
	catalogId: number;
	dayTrial: string;
	priceModel: string;
	publisherWebsiteURL: string;
	supportURL: string;
}

const initialState = {
	appBuild: 'upload',
	appLicense: 'perpetual',
	appType: 'saas',
	dayTrial: 'no',
	priceModel: 'paid',
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
