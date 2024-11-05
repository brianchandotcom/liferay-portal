/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactNode, createContext, useContext} from 'react';

import {useMyUserAccount} from '../hooks/useMyUserAccount';

const AppContext = createContext<{myUserAccount: any}>({
	myUserAccount: null,
});

type AppContextProviderProps = {
	children: ReactNode;
};

const AppContextProvider: React.FC<AppContextProviderProps> = ({children}) => {
	const {data: myUserAccount} = useMyUserAccount();

	return (
		<AppContext.Provider value={{myUserAccount}}>
			{children}
		</AppContext.Provider>
	);
};

const useAppContext = () => useContext(AppContext);

export {useAppContext};

export default AppContextProvider;
