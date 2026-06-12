/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';
import {Navigate, useNavigate, useParams} from 'react-router-dom';

import {useMarketplaceContext} from '../../../../context/MarketplaceContext';
import SearchBuilder from '../../../../core/SearchBuilder';
import {OrderTypes} from '../../../../enums/Order';
import {Liferay} from '../../../../liferay/liferay';
import HeadlessAdminUser from '../../../../services/rest/HeadlessAdminUser';
import HeadlessCommerceDeliveryOrder from '../../../../services/rest/HeadlessCommerceDeliveryOrder';

export default function AiHubRedirect() {
	const [loading, setLoading] = useState(false);
	const {accountErc, tokens} = useParams();
	const navigate = useNavigate();
	const {myUserAccount} = useMarketplaceContext();

	useEffect(() => {
		async function fetchAIHub() {
			const accountBriefs = myUserAccount.accountBriefs ?? [];

			let selectedAccount = accountBriefs?.find(
				(accountBrief) =>
					accountBrief.id ===
					Liferay.CommerceContext.account?.accountId
			);

			if (selectedAccount?.externalReferenceCode !== accountErc) {
				selectedAccount =
					(await HeadlessAdminUser.getAccountByExternalReferenceCode(
						accountErc as string
					)) as unknown as AccountBrief;
			}

			if (!selectedAccount) {
				return console.error('Account not found.');
			}

			const {items} = await HeadlessCommerceDeliveryOrder.getPlacedOrders(
				Liferay.CommerceContext.commerceChannelId,
				selectedAccount!.id,
				new URLSearchParams({
					filter: SearchBuilder.eq(
						'orderTypeExternalReferenceCode',
						OrderTypes.AI_HUB
					),
				})
			);

			if (!items.length) {
				return;
			}

			navigate(
				`/products/${items[0].id}/${tokens ? 'buy-liferay-tokens' : ''}`
			);
		}

		setLoading(true);

		fetchAIHub();

		setLoading(false);
	}, [accountErc, myUserAccount, navigate, tokens]);

	if (loading) {
		return 'Loading...';
	}

	return <Navigate to="/" />;
}
