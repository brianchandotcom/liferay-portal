/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useQuery} from '@apollo/client';
import {useEffect} from 'react';
import {useOutletContext} from 'react-router-dom';
import {useAppPropertiesContext} from '~/contexts/AppPropertiesContext';
import SearchBuilder from '~/lib/SearchBuilder';
import IncidentContactCard from '~/features/project/containers/IncidentContactCard';
import i18n from '~/utils/I18n';
import useCurrentKoroneikiAccount from '~/hooks/useCurrentKoroneikiAccount';
import {getAccountSubscriptionGroups} from '~/services/liferay/graphql/queries';
import ManageProductUsers from './components/ManageProductUsers/ManageProductUsers';
import TeamMembersTable from './components/TeamMembersTable/TeamMembersTable';

const targetProducts = [
	'Analytics Cloud',
	'Liferay Cloud'
];

const TeamMembers = () => {
	const {setHasSideMenu} = useOutletContext();
	const {data: dataCurrentKoroneikiAccount, loading: loadingCurrentKoroneikiAccount} = useCurrentKoroneikiAccount();
	const koroneikiAccount = dataCurrentKoroneikiAccount?.koroneikiAccountByExternalReferenceCode;
	const {featureFlags} = useAppPropertiesContext();

	const {data: dataSubscriptionGroups, loading: loadingSubscriptionGroups} = useQuery(
		getAccountSubscriptionGroups,
		{
			skip: loadingCurrentKoroneikiAccount,
			variables: {
				filter: new SearchBuilder()
					.eq('accountKey', koroneikiAccount?.accountKey)
					.and()
					.eq('hasActivation', true)
					.build(),
			},
		}
	);

	const accountSubscriptionGroups =
		dataSubscriptionGroups?.c?.accountSubscriptionGroups?.items;

	const accountSubscriptionGroupsNames = accountSubscriptionGroups?.map(
		(group) => group?.name
	);

	const hasActiveProduct = accountSubscriptionGroups?.some(
		(item) =>
			targetProducts?.includes(item?.name) &&
			item?.hasActivation &&
			item?.activationStatus === 'Active'
	);

	const loading = loadingCurrentKoroneikiAccount || loadingSubscriptionGroups;

	useEffect(() => {
		setHasSideMenu(true);
	}, [setHasSideMenu]);

	return (
		<>
			<h1>{i18n.translate('team-members')}</h1>

			<p className="text-neutral-7 text-paragraph-sm">
				{i18n.translate(
					'team-members-have-access-to-this-project-in-customer-portal'
				)}
			</p>

			<div className="mt-4">
				<TeamMembersTable
					koroneikiAccount={koroneikiAccount}
					loading={loading}
				/>

				<ManageProductUsers
					koroneikiAccount={koroneikiAccount}
					loading={loading}
				/>

				{featureFlags.includes('LPS-159127') &&
					hasActiveProduct && (
						<IncidentContactCard
							accountSubscriptionGroupsNames={accountSubscriptionGroupsNames}
							hasActiveProduct={hasActiveProduct}
							koroneikiAccount={koroneikiAccount}
							loading={loading}
						/>
					)}
			</div>
		</>
	);
};

export default TeamMembers;
