/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactNode, createContext, useContext} from 'react';
import useSWR, {KeyedMutator} from 'swr';

import SearchBuilder from '../core/SearchBuilder';
import {Liferay} from '../liferay/liferay';
import HeadlessAdminUserImpl from '../services/rest/HeadlessAdminUser';
import HeadlessCommerceDeliveryCatalogImpl from '../services/rest/HeadlessCommerceDeliveryCatalog';

type ContextType = {
	channel: Channel;
	mutateMyUserAccount: KeyedMutator<UserAccount | undefined>;
	myUserAccount: UserAccount;
	properties: DefaultProperties;
};

const MarketplaceContext = createContext<ContextType>({
	channel: {} as Channel,
	mutateMyUserAccount: (() => null) as unknown as KeyedMutator<
		UserAccount | undefined
	>,
	myUserAccount: {} as UserAccount,
	properties: {} as DefaultProperties,
});

type MarketplaceContextProviderProps = {
	children: ReactNode;
	properties: DefaultProperties;
};

const urlSearchParams = new URLSearchParams();

urlSearchParams.set(
	'filter',
	SearchBuilder.contains('name', 'Marketplace Channel')
);

const MarketplaceContextProvider: React.FC<MarketplaceContextProviderProps> = ({
	children,
	properties,
}) => {
	const {data: marketplaceChannel} = useSWR(
		'/marketplace/channel',
		async () => {
			const channelResponse =
				await HeadlessCommerceDeliveryCatalogImpl.getChannels(
					urlSearchParams
				);

			return (channelResponse?.items ?? [])[0];
		}
	);

	const {data: myUserAccount, mutate} = useSWR(
		Liferay.ThemeDisplay.isSignedIn()
			? '/marketplace/my-user-account'
			: null,
		() => HeadlessAdminUserImpl.getMyUserAccount()
	);

	return (
		<MarketplaceContext.Provider
			value={
				{
					channel: marketplaceChannel,
					mutateMyUserAccount: mutate,
					myUserAccount,
					properties,
				} as ContextType
			}
		>
			{children}
		</MarketplaceContext.Provider>
	);
};

const useMarketplaceContext = () => {
	return useContext(MarketplaceContext);
};

export {useMarketplaceContext, MarketplaceContext};

export default MarketplaceContextProvider;
