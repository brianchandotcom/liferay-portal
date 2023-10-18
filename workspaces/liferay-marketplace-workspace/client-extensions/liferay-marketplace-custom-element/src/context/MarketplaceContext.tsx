/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactNode, createContext, useContext} from 'react';
import useSWR from 'swr';

import SearchBuilder from '../core/SearchBuilder';
import HeadlessAdminUserImpl from '../services/rest/HeadlessAdminUser';
import HeadlessCommerceDeliveryCatalogImpl from '../services/rest/HeadlessCommerceDeliveryCatalog';

type ContextType = {
	channel: Channel;
	myUserAccount: UserAccount;
};

const MarketplaceContext = createContext<ContextType>({
	channel: {} as Channel,
	myUserAccount: {} as UserAccount,
});

type MarketplaceContextProviderProps = {
	children: ReactNode;
};

const MarketplaceContextProvider: React.FC<MarketplaceContextProviderProps> = ({
	children,
}) => {
	const {data: marketplaceChannel} = useSWR(
		'/marketplace/channel',
		async () => {
			const urlSearchParams = new URLSearchParams();

			urlSearchParams.set(
				'filter',
				SearchBuilder.contains('name', 'Marketplace Channel')
			);

			const channelResponse = await HeadlessCommerceDeliveryCatalogImpl.getChannels(
				urlSearchParams
			);

			return (channelResponse?.items ?? [])[0];
		}
	);

	const {data: myUserAccount} = useSWR('/marketplace/my-user-account', () => {
		return HeadlessAdminUserImpl.getMyUserAccount();
	});

	return (
		<MarketplaceContext.Provider
			value={
				{
					channel: marketplaceChannel,
					myUserAccount,
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
