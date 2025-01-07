/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';
import {useForm} from 'react-hook-form';
import {HashRouter, Route, Routes} from 'react-router-dom';

import {useMarketplaceContext} from '../../context/MarketplaceContext';
import withProviders from '../../hoc/withProviders';
import zodSchema, {z, zodResolver} from '../../schema/zod';
import {ConsoleUserProjectWithExtension} from '../CustomerDashboard/pages/Apps/App/CloudProvisioning/pages/CloudProvisioningOutlet';
import useGetResourceInfo from '../GetApp/hooks/useGetResourceInfo';
import useAccounts from '../ProductPurchase/hooks/useAccounts';
import AccountSelectionStep from './pages/AccountSelectionStep';
import Congratulations from './pages/Congratulations';
import EnvironmentSelectionStep from './pages/EnvironmentSelectionStep';
import ProjectSelectionStep from './pages/ProjectSelectionStep';

const OAuth2AuthorizeRouter = () => {
	const resourceResponse = useGetResourceInfo({
		selectedProject: undefined,
		shouldFetch: true,
	});

	const {myUserAccount} = useMarketplaceContext();
	const {selectedAccount, setSelectedAccount} = useAccounts();
	const projects = useMemo(
		() => resourceResponse?.resourceRequest?.userProjects || [],
		[resourceResponse]
	);

	const {setValue, watch} = useForm<
		z.infer<typeof zodSchema.installProductSchema>
	>({
		resolver: zodResolver(zodSchema.installProductSchema),
	});
	const {environment, project} = watch();

	return (
		<div className="container mt-5">
			<HashRouter>
				<Routes>
					<Route
						element={
							<AccountSelectionStep
								myUserAccount={myUserAccount}
								selectedAccount={selectedAccount}
								setSelectedAccount={setSelectedAccount}
							/>
						}
						path="/*"
					/>
					<Route
						element={
							<ProjectSelectionStep
								myUserAccount={myUserAccount}
								projects={
									projects as ConsoleUserProjectWithExtension[]
								}
								selProject={project}
								selectedAccount={selectedAccount}
								setValue={setValue}
							/>
						}
						path="project-selection"
					/>
					<Route
						element={
							<EnvironmentSelectionStep
								environment={environment}
								myUserAccount={myUserAccount}
								project={project}
								selectedAccount={selectedAccount}
								setValue={setValue}
							/>
						}
						path="environment-selection"
					/>
					<Route
						element={
							<Congratulations
								myUserAccount={myUserAccount}
								selectedAccount={selectedAccount}
							/>
						}
						path="congratulations"
					/>
				</Routes>
			</HashRouter>
		</div>
	);
};

export default withProviders(OAuth2AuthorizeRouter);
