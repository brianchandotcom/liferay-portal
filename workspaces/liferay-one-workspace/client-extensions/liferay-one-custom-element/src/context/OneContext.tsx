/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactNode, createContext, useContext} from 'react';
import useSWR, {KeyedMutator} from 'swr';

import {MarketplaceUserAccount} from '../entity/MarketplaceUserAccount';
import {Liferay} from '../liferay/liferay';
import HeadlessAdminUser from '../services/rest/HeadlessAdminUser';
import {Properties} from '../utils/attributes';

type Context = {
	channel: Channel;
	marketplaceUserAccount: MarketplaceUserAccount;
	mutateMyUserAccount: KeyedMutator<UserAccount | undefined>;
	myUserAccount: UserAccount;
	properties: Properties;
};

type OneContextProviderProps = {
	children: ReactNode;
	properties: Properties;
};

const channel = {
	channelId: Number(Liferay.CommerceContext?.commerceChannelId),
	currencyCode: Liferay.CommerceContext?.currency?.currencyCode,
	externalReferenceCode: 'MARKETPLACE',
	id: Number(Liferay.CommerceContext?.commerceChannelId),
} as Channel;

const OneContext = createContext<Context>({} as Context);

const OneContextProvider: React.FC<OneContextProviderProps> = ({
	children,
	properties,
}) => {
	const {data: myUserAccount, mutate} = useSWR(
		Liferay.ThemeDisplay.isSignedIn() ? '/one/my-user-account' : null,
		HeadlessAdminUser.getMyUserAccount
	);

	return (
		<OneContext.Provider
			value={
				{
					channel,
					marketplaceUserAccount: new MarketplaceUserAccount(
						myUserAccount as UserAccount
					),
					mutateMyUserAccount: mutate as KeyedMutator<UserAccount>,
					myUserAccount,
					properties,
				} as Context
			}
		>
			{children}
		</OneContext.Provider>
	);
};

const useOneContext = () => {
	return useContext(OneContext);
};

export {OneContext, useOneContext};

export default OneContextProvider;
