/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import {useModal} from '@clayui/modal';
import {useNavigate} from 'react-router-dom';

import Modal from '../../../../../../../components/Modal';
import Table from '../../../../../../../components/Table/Table';
import i18n from '../../../../../../../i18n';
import {Liferay} from '../../../../../../../liferay/liferay';

type ProvisioningTableProps = ReturnType<typeof useProvisioningData>;

const ProvisioningTable: React.FC<ProvisioningTableProps> = ({
	order,
	provisioningTableData,
	resourceRequirements,
}) => {
	const navigate = useNavigate();
	const modal = useModal();

	const install = (requirements: any) => {
		if (!requirements.resourceRequest?.userProjects?.length) {
			return modal.onOpenChange(true);
		}

		navigate(`/order/${order?.id}/install`);
	};

	return (
		<>
			<Table
				className="mt-4"
				columns={[
					{
						key: 'type',
						render: (type, provisioning) => (
							<>
								<div className="dashboard-table-row-type font-weight-bold">
									{type}
								</div>
								<div className="dashboard-table-row-type">
									{provisioning.host}
								</div>
							</>
						),
						title: (
							<>
								<div className="text-dark">
									{i18n.translate('type')}
								</div>
								<div className="text-black-50">
									{i18n.translate('host-name')}
								</div>
							</>
						),
					},
					{
						key: 'startDate',
						render: (startDate, provisioning) => (
							<>
								<div className="dashboard-table-row-type">
									{startDate}
								</div>
								<div className="dashboard-table-row-type">
									{provisioning.expirationDate}
								</div>
							</>
						),
						title: (
							<>
								<div className="text-dark">
									{i18n.translate('start-date')}
								</div>
								<div className="text-dark">
									{i18n.translate('exp-date')}
								</div>
							</>
						),
					},
					{
						key: 'status',
						render: (status: string) => (
							<InstallationStatus status={status}>
								{status}
							</InstallationStatus>
						),
						title: (
							<div className="text-dark">
								{i18n.translate('status')}
							</div>
						),
					},
					{
						key: 'project',
						render: (project, provisioning) => {
							const environment = provisioning.environment;

							return (
								<>
									<div className="dashboard-table-row-type font-weight-bold">
										{project || 'Not Installed'}
									</div>
									<div className="dashboard-table-row-type">
										{environment || 'Not Installed'}
									</div>
								</>
							);
						},
						title: (
							<>
								<div className="text-dark">
									{i18n.translate('project')}
								</div>
								<div className="text-black-50">
									{i18n.translate('environment')}
								</div>
							</>
						),
					},
					{
						key: 'dropdown',
						render: () => (
							<div
								className="d-flex justify-content-end"
								onClick={(event) => event.stopPropagation()}
							>
								<ClayDropDown
									trigger={
										<ClayButtonWithIcon
											aria-label="Kebab Button"
											displayType={null}
											symbol="ellipsis-v"
											title="Kebab Button"
										/>
									}
								>
									<ClayDropDown.ItemList>
										<ClayDropDown.Item
											disabled
											onClick={() => {}}
										>
											{i18n.translate('view-details')}
										</ClayDropDown.Item>

										<ClayDropDown.Item
											onClick={() =>
												install(resourceRequirements)
											}
										>
											{i18n.translate('install')}
										</ClayDropDown.Item>
									</ClayDropDown.ItemList>
								</ClayDropDown>
							</div>
						),
					},
				]}
				rows={provisioningTableData}
			/>

			<Modal
				first={
					<ClayButton
						displayType="secondary"
						onClick={modal.onClose}
						size="sm"
					>
						{i18n.translate('cancel')}
					</ClayButton>
				}
				last={
					<ClayButton
						className="ml-2 rounded-lg"
						displayType="primary"
						onClick={() =>
							Liferay.Util.navigate('/c/portal/logout')
						}
						size="sm"
					>
						{i18n.translate('sign-in-with-a-different-account')}
					</ClayButton>
				}
				observer={modal.observer}
				size={'md' as any}
				title={i18n.translate('no-cloud-projects-available')}
				visible={modal.open}
			>
				{i18n.translate(
					'you-currently-do-not-have-access-to-any-cloud-projects-please-login-as-a-user-that-has-access-to-a-project-or-contact-your-project-administrator-to-add-you-to-a-project'
				)}
			</Modal>
		</>
	);
};

export default ProvisioningTable;
