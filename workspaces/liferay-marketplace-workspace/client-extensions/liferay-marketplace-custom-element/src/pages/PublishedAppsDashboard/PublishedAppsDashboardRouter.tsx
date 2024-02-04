/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect} from 'react';
import {HashRouter, Route, Routes} from 'react-router-dom';

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

	const {
		data: catalogs = [],
		isValidating: isCatalogsValidating,
	} = useCatalogs();
	const {
		data: supplierAccounts = [],
		isValidating: isSupplierAccountsValidating,
	} = useSupplierAccounts();

	const catalogId = catalogs.find(
		(catalog) => catalog.accountId === accountId
	)?.id;

	if (!isCatalogsValidating && !isSupplierAccountsValidating) {
		if (!supplierAccounts.length) {
			window.location.href = Liferay.ThemeDisplay.getCanonicalURL().replace(
				'/publisher-dashboard',
				'/home'
			);
		}
	}

	useEffect(() => {
		const selectDefaultAccount = async () => {
			if (
				(!accountId ||
					!supplierAccounts.find(
						(supplier) => supplier.id === accountId
					)) &&
				supplierAccounts.length
			) {
				const newAccountId = supplierAccounts[0].id;

				await CommerceSelectAccountImpl.selectAccount(newAccountId);

				Liferay.CommerceContext.account = {
					accountId: newAccountId,
				};

				window.location.reload();
			}
		};

		selectDefaultAccount();
	}, [accountId, supplierAccounts]);

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
							accountId={accountId}
							catalogId={catalogId}
							catalogs={catalogs}
							supplierAccounts={supplierAccounts.filter(
								(supplierAccount) =>
									catalogs.some(
										({accountId}) =>
											supplierAccount.id === accountId
									)
							)}
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
