/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useMemo, useState} from 'react';
import {useOutletContext} from 'react-router-dom';
import Skeleton from '~/components/Skeleton';
import i18n from '~/utils/I18n';

import AccountSubscriptionsList from './components/AccountSubscriptionsList/AccountSubscriptionsList';
import SubscriptionsNavbar from './components/SubscriptionsNavbar/SubscriptionsNavbar';
import useAccountSubscriptionGroups from './hooks/useAccountSubscriptionGroups';
import useAccountSubscriptions from './hooks/useAccountSubscriptions';

const SubscriptionsOverview = ({koroneikiAccount, loading}) => {
	const [selectedItemIndex, setSelectedItemIndex] = useState(0);

	const {setHasSideMenu} = useOutletContext();
	const [
		{lastAccountSubcriptionGroup, setLastAccountSubscriptionGroup},
		{
			data: accountSubscriptionGroupsData,
			loading: accountSubscriptionGroupsLoading,
		},
	] = useAccountSubscriptionGroups(koroneikiAccount?.accountKey, loading);

	const accountSubscriptionGroups =
		accountSubscriptionGroupsData?.c.accountSubscriptionGroups;

	const translatedSubscriptionGroups = useMemo(() => {
		const items = accountSubscriptionGroups?.items;
	
		if (!items?.length) {
			return null;
		}
	
		const legacyNames = ['Liferay SaaS', 'Liferay PaaS'];
	
		return items.map((group) => {
			if (legacyNames.includes(group.name)) {
				return {
					...group,
					name: 'Liferay Cloud',
				};
			}
	
			return group;
		});
	}, [accountSubscriptionGroups]);

	const [
		setLastSubscriptionStatus,
		{data: accountSubscriptionsData, loading: accountSubscriptionsLoading},
	] = useAccountSubscriptions(
		lastAccountSubcriptionGroup,
		accountSubscriptionGroupsLoading
	);

	const accountSubscriptions =
		accountSubscriptionsData?.c.accountSubscriptions.items;

	useEffect(() => {
		setHasSideMenu(true);
	}, [setHasSideMenu]);

	const handleDropdownOnClick = (selectedStatus) =>
		setLastSubscriptionStatus(selectedStatus);

	const subscriptionsGroupSelected =
		translatedSubscriptionGroups?.[selectedItemIndex]?.name;

	const portalOrDXPSubscriptions = ['Portal', 'Liferay Self-Hosted'];

	return (
		<div>
			{accountSubscriptionGroupsLoading ? (
				<Skeleton className="mb-4 pb-2" height={35} width={200} />
			) : (
				!accountSubscriptionGroups?.hasPartnership && (
					<h3 className="mb-4 pb-2">
						{i18n.translate('subscriptions')}
					</h3>
				)
			)}

			{!!lastAccountSubcriptionGroup && (
				<>
					<SubscriptionsNavbar
						accountSubscriptionGroups={translatedSubscriptionGroups}
						disabled={accountSubscriptionsLoading}
						loading={accountSubscriptionGroupsLoading}
						onClickDropdownItem={handleDropdownOnClick}
						onSelectNavItem={setLastAccountSubscriptionGroup}
						selectedItemIndex={selectedItemIndex}
						setSelectedItemIndex={setSelectedItemIndex}
					/>

					<AccountSubscriptionsList
						IsPortalOrDXP={portalOrDXPSubscriptions.includes(
							subscriptionsGroupSelected
						)}
						accountKey={koroneikiAccount?.accountKey}
						accountSubscriptionGroup={lastAccountSubcriptionGroup}
						accountSubscriptions={accountSubscriptions}
						loading={accountSubscriptionsLoading}
						selectedAccountSubscriptionGroup={
							lastAccountSubcriptionGroup
						}
					/>
				</>
			)}
		</div>
	);
};

export default SubscriptionsOverview;
