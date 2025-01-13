/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useMemo} from 'react';
import {useForm} from 'react-hook-form';
import {Outlet, useOutletContext} from 'react-router-dom';

import {useMarketplaceContext} from '../../context/MarketplaceContext';
import zodSchema, {z, zodResolver} from '../../schema/zod';
import {ConsoleUserProject} from '../../services/oauth/types';
import useGetResourceInfo from '../GetApp/hooks/useGetResourceInfo';
import useAccounts from '../ProductPurchase/hooks/useAccounts';

type Schema = z.infer<typeof zodSchema.installProductSchema>;

const OAuth2AuthorizeOutlet = () => {
	const {isLoading, resourceRequest} = useGetResourceInfo({
		selectedProject: undefined,
		shouldFetch: true,
	});

	const {myUserAccount} = useMarketplaceContext();
	const {selectedAccount, setSelectedAccount} = useAccounts();

	const projects = useMemo(
		() => resourceRequest?.userProjects || [],
		[resourceRequest]
	);

	const {setValue, watch} = useForm<Schema>({
		resolver: zodResolver(zodSchema.installProductSchema),
	});

	const {environment, project} = watch();

	const singleProject = projects?.length === 1;
	const singleAccount = myUserAccount?.accountBriefs?.length === 1;

	useEffect(() => {
		if (singleProject) {
			setValue('project', projects[0] as any);
		}
	}, [projects, setValue, singleProject]);

	return (
		<div className="container mt-5">
			<Outlet
				context={{
					environment,
					isLoading,
					myUserAccount,
					project,
					projects,
					selectedAccount,
					setSelectedAccount,
					setValue,
					singleAccount,
					singleProject,
				}}
			/>
		</div>
	);
};

const useOAuth2OutletContext = () =>
	useOutletContext<{
		environment: Schema['environment'];
		isLoading: boolean;
		myUserAccount: UserAccount;
		project: ConsoleUserProject;
		projects: ConsoleUserProject[];
		selectedAccount?: Account;
		setSelectedAccount: React.Dispatch<Account>;
		setValue: any;
		singleAccount: boolean;
		singleProject: boolean;
	}>();

export {useOAuth2OutletContext};

export default OAuth2AuthorizeOutlet;
