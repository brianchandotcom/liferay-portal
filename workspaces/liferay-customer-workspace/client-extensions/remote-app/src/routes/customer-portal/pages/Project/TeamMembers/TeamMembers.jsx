/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useQuery} from '@apollo/client';
import {useEffect} from 'react';
import {useOutletContext} from 'react-router-dom';
import {useAppPropertiesContext} from '~/common/contexts/AppPropertiesContext';
import SearchBuilder from '~/common/core/SearchBuilder';
import IncidentContactCard from '~/routes/customer-portal/components/IncidentContactCard';
import i18n from '../../../../../common/I18n';
import useCurrentKoroneikiAccount from '../../../../../common/hooks/useCurrentKoroneikiAccount';
import {getAccountSubscriptionGroups} from '../../../../../common/services/liferay/graphql/queries';
import ManageProductUsers from './components/ManageProductUsers/ManageProductUsers';
import TeamMembersTable from './components/TeamMembersTable/TeamMembersTable';

const targetProducts = [
	'Analytics Cloud',
	'Liferay PaaS',
	'Liferay SaaS'
];

const TeamMembers = () => {
	const {setHasSideMenu} = useOutletContext();
	const {data, loading} = useCurrentKoroneikiAccount();
	const koroneikiAccount = data?.koroneikiAccountByExternalReferenceCode;

	const {featureFlags} = useAppPropertiesContext();

	const {data: dataSubscriptionGroups} = useQuery(
		getAccountSubscriptionGroups,
		{
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
		dataSubscriptionGroups?.c.accountSubscriptionGroups?.items;

	const accountSubscriptionGroupsNames = accountSubscriptionGroups?.map(
		(group) => group.name
	);

	const hasActiveProduct = accountSubscriptionGroups?.some(
		(item) =>
			targetProducts.includes(item.name) &&
			item.hasActivation &&
			item.activationStatus === 'Active'
	);

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
					accountSubscriptionGroupsNames?.some((groupName) =>
						targetProducts.includes(groupName)
					) && (
						<IncidentContactCard
							accountSubscriptionGroupsNames={
								accountSubscriptionGroupsNames
							}
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
