/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayManagementToolbar from '@clayui/management-toolbar';
import {useParams} from 'react-router-dom';
import useFormModal from '~/hooks/useFormModal';
import {testrayRequirementsImpl} from '~/services/rest';

import Button from '../../../components/Button';
import Container from '../../../components/Layout/Container';
import ListView, {ListViewProps} from '../../../components/ListView';
import {TableProps} from '../../../components/Table';
import {ListViewContextProviderProps} from '../../../context/ListViewContext';
import SearchBuilder from '../../../core/SearchBuilder';
import i18n from '../../../i18n';
import {Action} from '../../../types';
import useRequirementActions from './useRequirementActions';

type RequirementListViewProps = {
	actions?: Action[];
	formModal?: any;
	projectId?: number | string;
	variables?: any;
} & {
	listViewProps?: Partial<
		ListViewProps & {initialContext?: Partial<ListViewContextProviderProps>}
	>;
	tableProps?: Partial<TableProps>;
};

const RequirementListView: React.FC<RequirementListViewProps> = ({
	actions,
	formModal,
	listViewProps,
	tableProps,
	variables,
}) => {
	return (
		<ListView
			forceRefetch={formModal?.forceRefetch}
			managementToolbarProps={{
				applyFilters: true,
				buttons: (actions) =>
					actions?.create && (
						<>
							<Button
								displayType="secondary"
								onClick={() => formModal.modal.open()}
								symbol="redo"
								toolbar
							>
								{i18n.translate('import-jira-issues')}
							</Button>

							<ClayManagementToolbar.Item className="ml-2">
								<Button displayType="secondary" symbol="upload">
									{i18n.translate('upload-csv')}
								</Button>
							</ClayManagementToolbar.Item>
						</>
					),
				filterSchema: 'requirements',
				title: i18n.translate('requirements'),
			}}
			resource={testrayRequirementsImpl.resource}
			tableProps={{
				actions,
				columns: [
					{
						clickable: true,
						key: 'key',
						value: 'Key',
					},
					{
						key: 'linkTitle',
						render: (
							linkTitle: string,
							{linkURL}: {linkURL: string}
						) => (
							<a
								href={linkURL}
								rel="noopener noreferrer"
								target="_blank"
							>
								{linkTitle}

								<ClayIcon className="ml-2" symbol="shortcut" />
							</a>
						),
						value: 'Link',
					},
					{
						clickable: true,
						key: 'team',
						render: (_, {component}) => component?.team?.name,
						value: i18n.translate('team'),
					},
					{
						clickable: true,
						key: 'component',
						render: (component) => component?.name,
						value: i18n.translate('component'),
					},
					{
						clickable: true,
						key: 'components',
						value: i18n.translate('jira-components'),
					},
					{
						clickable: true,
						key: 'summary',
						size: 'md',
						value: i18n.translate('summary'),
					},
				],
				navigateTo: ({id}) => id?.toString(),
				...tableProps,
			}}
			transformData={(response) =>
				testrayRequirementsImpl.transformDataFromList(response)
			}
			variables={variables}
			{...listViewProps}
		/>
	);
};

const Requirements = () => {
	const {actions, forceRefetch} = useRequirementActions();
	const {projectId} = useParams();
	const formModal = useFormModal();

	return (
		<Container>
			<RequirementListView
				actions={actions}
				formModal={formModal}
				listViewProps={{forceRefetch}}
				variables={{
					filter: SearchBuilder.eq('projectId', projectId as string),
				}}
			/>
		</Container>
	);
};

export {RequirementListView};

export default Requirements;
