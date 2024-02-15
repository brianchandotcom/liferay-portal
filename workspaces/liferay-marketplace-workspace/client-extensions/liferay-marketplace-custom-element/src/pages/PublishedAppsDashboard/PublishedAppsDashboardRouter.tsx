/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect} from 'react';
import {HashRouter, Route, Routes} from 'react-router-dom';

import {useAccount} from '../../hooks/data/useAccounts';
import {useCatalogs} from '../../hooks/data/useCatalogs';
import {useSupplierAccounts} from '../../hooks/data/useSupplierAccounts';
import {Liferay} from '../../liferay/liferay';
import CommerceSelectAccountImpl from '../../services/rest/CommerceSelectAccount';
import Accounts from './Accounts/Accounts';
import Apps from './Apps';
import App from './Apps/App';
import {AppCreationFlow} from './Apps/AppCreationFlow/AppCreationFlow';
import Members from './Members';
import Projects from './Projects';
import PublishedAppsDashboardOutlet from './PublishedAppsDashboardOutlet';
import Solutions from './Solutions';

const PublishedAppsDashboardRouter = () => {
	const {accountId} = Liferay.CommerceContext.account || {};
	const {data: catalogs = []} = useCatalogs();
	const accountsSearch = useSupplierAccounts();
	const {data, isValidating} = useAccount();

	useEffect(() => {
		const checkAccount = async (accountId: number) => {
			await CommerceSelectAccountImpl.selectAccount(accountId);

			Liferay.CommerceContext.account = {
				accountId,
			};

			window.location.reload();
		};

		const newAccountId = accountsSearch.items.at(0)?.id;

		if (!isValidating && data?.type !== 'supplier' && newAccountId) {
			checkAccount(newAccountId);
		}
	}, [isValidating, data?.type, accountsSearch.items]);

	const catalogId = catalogs.find(
		(catalog) => catalog.accountId === accountId
	)?.id;

	return (
		<HashRouter>
			<Routes>
				<Route
					element={<AppCreationFlow catalogId={String(catalogId)} />}
					path="app/create"
				/>

				<Route
					element={
						<PublishedAppsDashboardOutlet
							accountsSearch={accountsSearch}
							catalogId={catalogId}
						/>
					}
				>
					<Route element={<Apps />} index />
					<Route path="app/:appId">
						<Route element={<App />} index />
					</Route>
					<Route element={<Accounts />} path="accounts" />
					<Route element={<Members />} path="members" />
					<Route element={<Projects />} path="projects" />
					<Route element={<Solutions />} path="solutions" />
				</Route>
			</Routes>
		</HashRouter>
	);
};

export default PublishedAppsDashboardRouter;
