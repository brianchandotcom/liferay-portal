/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect} from 'react';
import {useNavigate} from 'react-router-dom';

import ListView from '../../../components/ListView';
import Page from '../../../components/Page';
import {useMarketplaceContext} from '../../../context/MarketplaceContext';
import i18n from '../../../i18n';
import {formatDate} from '../../../utils/date';
import {ssaRoles} from '../constants';
import useManageUserActions from '../hooks/useManageUserActions';

export default function ManageUsers() {
	const {marketplaceUserAccount} = useMarketplaceContext();
	const {properties} = useMarketplaceContext();
	const actions = useManageUserActions();
	const navigate = useNavigate();

	useEffect(() => {
		if (!marketplaceUserAccount.isSSAAdmin) {
			navigate('/');
		}
	}, [marketplaceUserAccount.isSSAAdmin, navigate]);

	return (
		<Page
			description={i18n.translate(
				'manage-members-and-access-for-ssa-accounts'
			)}
			pageRendererProps={{className: 'border py-2 rounded'}}
			title={i18n.translate('manage-users')}
		>
			<ListView<UserAccount>
				id="manage-ssa-users"
				managementToolbarProps={{
					searchVisible: true,
					visible: true,
				}}
				resource={`o/headless-admin-user/v1.0/accounts/by-external-reference-code/${properties.accountExternalReferenceCode}/user-accounts?sort=name:asc`}
				tableProps={{
					actions,
					columns: [
						{
							id: 'name',
							name: i18n.translate('name'),
							sortable: true,
						},
						{
							id: 'emailAddress',
							name: i18n.translate('email-address'),
						},
						{
							id: 'accountBriefs',
							name: i18n.translate('roles'),
							render: (accountBriefs) => {
								const ssaAccount = accountBriefs.find(
									(accountBrief) =>
										accountBrief.externalReferenceCode ===
										properties.accountExternalReferenceCode
								);

								const filteredRoles =
									ssaAccount?.roleBriefs.filter((item2) =>
										ssaRoles.some(
											(item1) =>
												item1.value === item2.name
										)
									);

								return (
									<div className="d-flex flex-column">
										{filteredRoles?.map((role, index) => {
											return (
												<p className="m-0" key={index}>
													{role.name}
												</p>
											);
										})}
									</div>
								);
							},
						},
						{
							id: 'lastLoginDate',
							name: i18n.translate('last-login'),
							render: (lastLoginDate) =>
								formatDate(lastLoginDate),
						},
					],
				}}
			/>
		</Page>
	);
}
