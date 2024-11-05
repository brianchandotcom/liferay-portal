/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {ClayButtonWithIcon} from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {useNavigate} from 'react-router-dom';
import useSWR from 'swr';

import Container from '../../components/Container';
import NoSettingsEmptyState from '../../components/NoSettingsEmptyState';
import Table from '../../components/Table';
import useAIWizardContentOAuth2 from '../../hooks/useAIWizardOAuth2';

const PAD_LIMIT = 15;

const maskApiKey = (apiKey: string) =>
	apiKey
		.substring(0, PAD_LIMIT)
		.padEnd(apiKey.length - PAD_LIMIT, '*****************');

const renderPickList = ({name}: {key: string; name: string}) => name;

export const providerStatuses = {
	false: {
		'aria-label': 'Deactivated provider',
		'color': 'red',
		'symbol': 'times-circle',
	},
	true: {
		'aria-label': 'Active provider',
		'color': 'green',
		'symbol': 'check-circle',
	},
};

const columns = [
	{
		key: 'provider',
		name: 'Provider',
		render: (data: any, {active}: any) => (
			<>
				<ClayIcon {...(providerStatuses as any)[active]} />{' '}
				{renderPickList(data)}
			</>
		),
	},
	{
		key: 'apiKey',
		name: 'API Key',
		render: (apiKey: string) => <b>{maskApiKey(apiKey)}</b>,
	},
	{key: 'model', name: 'Model', render: renderPickList},
	{key: 'imageModel', name: 'Image Model', render: renderPickList},
	{key: 'description', name: 'Description'},
];

export default function Settings() {
	const aiWizardOAuth2 = useAIWizardContentOAuth2();
	const navigate = useNavigate();

	const {data: settings, mutate} = useSWR('/settings', () =>
		aiWizardOAuth2.getSettings()
	);

	const onDelete = async ({id}: any) => {
		if (!confirm('Are you sure you want to delete this setting?')) {
			return;
		}

		await aiWizardOAuth2.deleteSetting(id);

		await mutate((data: any) => data, {revalidate: true});
	};

	const items = settings?.items || [];
	const hasItems = !!items.length;

	return (
		<Container className="p-4">
			<div className="d-flex justify-content-between mb-4">
				<h1>AI Wizard Settings</h1>

				{hasItems && (
					<ClayButtonWithIcon
						aria-label="Add Config"
						onClick={() => navigate('create')}
						symbol="plus"
					/>
				)}
			</div>

			<Table
				actions={[
					{
						label: 'Details',
						onClick: ({id}: any) => navigate(`${id}`),
					},
					{
						label: 'Edit',
						onClick: ({id}: any) => navigate(`${id}/update`),
					},
					{
						label: 'Delete',
						onClick: onDelete,
					},
				]}
				columns={columns}
				emptyState={
					<NoSettingsEmptyState
						buttonProps={{onClick: () => navigate('create')}}
					/>
				}
				rows={items}
			/>

			{hasItems && (
				<ClayAlert>
					You can have multiple settings, but only one can be active
					at a time.
				</ClayAlert>
			)}
		</Container>
	);
}

export {maskApiKey};
