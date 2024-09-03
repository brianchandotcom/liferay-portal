/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect} from 'react';
import {HashRouter, Outlet, Route, Routes} from 'react-router-dom';

import SolutionContextProvider from '../../context/SolutionContext';
import withProviders from '../../hoc/withProviders';
import {useAccount} from '../../hooks/data/useAccounts';
import {useCatalogs} from '../../hooks/data/useCatalogs';
import {useSupplierAccounts} from '../../hooks/data/useSupplierAccounts';
import {Liferay} from '../../liferay/liferay';
import CommerceSelectAccountImpl from '../../services/rest/CommerceSelectAccount';
import PublishedDashboardOutlet from './PublisherDashboardOutlet';
import Accounts from './pages/Accounts/Accounts';
import Apps from './pages/Apps';
import App from './pages/Apps/App';
import {AppContextProvider} from './pages/Apps/AppCreationFlow/AppContext/AppManageState';
import {AppCreationFlow} from './pages/Apps/AppCreationFlow/AppCreationFlow';
import Members from './pages/Members';
import Projects from './pages/Projects';
import Solutions from './pages/Solutions';
import PublishSolutionOutlet from './pages/Solutions/NewSolutionFlow/PublishSolutionOutlet';
import {
	CompanyProfile,
	ContactUs,
	Create,
	Details,
	Header,
	Profile,
	Submit,
} from './pages/Solutions/NewSolutionFlow/pages';
import SolutionsDetails from './pages/Solutions/Solution';

const PublisherDashboardRouter = () => {
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
					element={
						<AppContextProvider>
							<AppCreationFlow catalogId={String(catalogId)} />
						</AppContextProvider>
					}
					path="app/create"
				/>

				<Route
					element={
						<PublishedDashboardOutlet
							accountsSearch={accountsSearch}
							catalogId={catalogId}
						/>
					}
				>
					<Route path="/">
						<Route element={<Apps />} index />
						<Route element={<App />} path="app/:appId" />
					</Route>

					<Route element={<Accounts />} path="accounts" />
					<Route element={<Members />} path="members" />
					<Route element={<Projects />} path="projects" />

					<Route path="solutions">
						<Route element={<Solutions />} index />
					</Route>
				</Route>

				<Route path="solutions">
					<Route
						element={
							<SolutionContextProvider
								catalogId={catalogId as number}
							>
								<Outlet />
							</SolutionContextProvider>
						}
						path=":productId?"
					>
						<Route
							element={
								<PublishedDashboardOutlet
									accountsSearch={accountsSearch}
									catalogId={catalogId}
								/>
							}
						>
							<Route element={<SolutionsDetails />} index />
						</Route>

						<Route
							element={<PublishSolutionOutlet />}
							path="publisher"
						>
							<Route element={<Create />} path="" />
							<Route
								element={<CompanyProfile />}
								path="company"
							/>
							<Route element={<ContactUs />} path="contact" />
							<Route element={<Details />} path="details" />
							<Route element={<Header />} path="header" />
							<Route element={<Profile />} path="profile" />
							<Route element={<Submit />} path="submit" />
						</Route>
					</Route>
				</Route>
			</Routes>
		</HashRouter>
	);
};

export default withProviders(PublisherDashboardRouter);
