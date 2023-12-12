/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useNavigate} from 'react-router-dom';

import Container from '../../../components/Layout/Container';
import ListView, {ListViewProps} from '../../../components/ListView';
import {TableProps} from '../../../components/Table';
import {ListViewContextProviderProps} from '../../../context/ListViewContext';
import {FormModal} from '../../../hooks/useFormModal';
import useHeader from '../../../hooks/useHeader';
import i18n from '../../../i18n';
import {UserGroup} from '../../../services/rest';
import {Action} from '../../../types';

type UserListViewProps = {
	actions?: Action[];
	formModal?: FormModal;
	variables?: any;
} & {
	listViewProps?: Partial<
		ListViewProps & {initialContext?: Partial<ListViewContextProviderProps>}
	>;
	tableProps?: Partial<TableProps>;
};

const UserListView: React.FC<UserListViewProps> = ({
	actions,
	formModal,
	listViewProps,
	tableProps,
	variables,
}) => {
	const navigate = useNavigate();

	return (
		<ListView
			forceRefetch={formModal?.forceRefetch}
			managementToolbarProps={{
				addButton: () => navigate('create'),
				filterSchema: 'user',
				title: i18n.translate('users'),
			}}
			resource="/user-accounts"
			tableProps={{
				actions,
				columns: [
					{
						clickable: true,
						key: 'givenName',
						render: (givenName, {familyName}) =>
							`${givenName} ${familyName}`,
						value: i18n.translate('name'),
					},
					{
						clickable: true,
						key: 'alternateName',
						value: i18n.translate('screen-name'),
					},
					{
						clickable: true,
						key: 'emailAddress',
						value: i18n.translate('email-address'),
					},
					{
						clickable: true,
						key: 'userGroupBriefs',
						render: (userGroups: UserGroup[] = []) =>
							userGroups.map(({name}) => name).join(', '),
						value: i18n.translate('user-group'),
					},
				],
				...tableProps,
			}}
			transformData={(response) => ({
				...response,
				actions: {
					...response.actions,
					create: response.actions['post-user-account'],
				},
			})}
			variables={variables}
			{...listViewProps}
		/>
	);
};

const Users = () => {
	const navigate = useNavigate();

	useHeader({
		dropdown: [],
		heading: [
			{
				title: i18n.translate('manage-users'),
			},
		],
		icon: 'cog',
	});

	return (
		<Container>
			<UserListView
				tableProps={{
					onClickRow: (user) => navigate(`${user.id}/update`),
				}}
			/>
		</Container>
	);
};

export {UserListView};

export default Users;
