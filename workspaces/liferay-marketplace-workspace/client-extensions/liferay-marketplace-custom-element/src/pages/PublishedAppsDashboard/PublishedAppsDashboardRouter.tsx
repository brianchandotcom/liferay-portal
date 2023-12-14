/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect} from 'react';
import {HashRouter, Route, Routes} from 'react-router-dom';

import SearchBuilder from '../../core/SearchBuilder';
import {Liferay} from '../../liferay/liferay';
import CommerceSelectAccountImpl from '../../services/rest/CommerceSelectAccount';
import HeadlessAdminUserImpl from '../../services/rest/HeadlessAdminUser';
import HeadlessCommerceAdminCatalogImpl from '../../services/rest/HeadlessCommerceAdminCatalog';
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

	useEffect(() => {
		const loadData = async () => {
			const [
				supplierAccountResponse,
				catalogResponse,
			] = await Promise.all([
				HeadlessAdminUserImpl.getAccounts(
					new URLSearchParams({
						filter: SearchBuilder.eq('type', 'supplier'),
					})
				),
				HeadlessCommerceAdminCatalogImpl.getCatalogs(
					new URLSearchParams({
						fields: 'accountId,id',
						pageSize: '-1',
					})
				),
			]);

			const {items: catalogs = []} = catalogResponse;
			const {items: supplierAccounts = []} = supplierAccountResponse;

			const suppliers = supplierAccounts.filter((supplierAccount) =>
				catalogs.some(
					(catalog) => catalog.accountId === supplierAccount.id
				)
			);

			if (!suppliers.length) {
				window.location.href = Liferay.ThemeDisplay.getCanonicalURL().replace(
					'/publisher-dashboard',
					'/home'
				);
			}

			if (
				!accountId ||
				!suppliers.find((supplier) => supplier.id === accountId)
			) {
				await CommerceSelectAccountImpl.selectAccount(suppliers[0].id);

				Liferay.CommerceContext.account = {
					accountId: suppliers[0].id,
				};

				// window.location.reload();
			}
		};

		loadData();
	}, [accountId]);

	return (
		<HashRouter>
			<Routes>
				<Route element={<AppCreationFlow />} path="app/create" />

				<Route element={<PublishedAppsDashboardOutlet />}>
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
