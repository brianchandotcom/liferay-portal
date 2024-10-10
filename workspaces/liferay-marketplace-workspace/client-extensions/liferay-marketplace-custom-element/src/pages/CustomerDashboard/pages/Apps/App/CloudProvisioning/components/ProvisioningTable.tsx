/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import {useOutletContext} from 'react-router-dom';

import Loading from '../../../../../../../components/Loading';
import Table from '../../../../../../../components/Table/Table';
import i18n from '../../../../../../../i18n';
import useProvisioningActions from '../hooks/useProvisioningActions';
import useProvisioningData from '../hooks/useProvisioningData';
import {InstallStatus} from '../types';
import InstallationStatus from './InstallStatus';
import UninstallModal from './UninstallModal';

type ProvisioningTableProps = ReturnType<typeof useProvisioningData>;

const ProvisioningTable: React.FC<ProvisioningTableProps> = ({
	mutateOrder,
	order,
	provisioningTableData,
	resourceRequirements,
}) => {
	const {selectedAccount} = useOutletContext<{selectedAccount: Account}>();
	const {
		actions,
		loading,
		onOpenDetailsModal,
		selectedOrderItem,
		uninstall,
		uninstallModal,
	} = useProvisioningActions({
		mutateOrder,
		order,
		resourceRequirements,
		selectedAccount,
	});

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
						render: (status: string, provisioning) => (
							<div className="align-items-center d-flex">
								<InstallationStatus status={status}>
									{status}
								</InstallationStatus>

								{provisioning.status ===
									InstallStatus.IN_PROGRESS && (
									<Loading
										displayType="primary"
										shape="circle"
										size="sm"
									/>
								)}
							</div>
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
						render: (_, orderItem) => (
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
										{actions
											.filter((action) =>
												action.show(orderItem)
											)
											.map((action, index) => (
												<ClayDropDown.Item
													disabled={false}
													key={index}
													onClick={() =>
														action.action(orderItem)
													}
												>
													{action?.title}
												</ClayDropDown.Item>
											))}
									</ClayDropDown.ItemList>
								</ClayDropDown>
							</div>
						),
					},
				]}
				onClickRow={(row) => onOpenDetailsModal(row)}
				rows={provisioningTableData}
			/>
			{selectedOrderItem && (
				<UninstallModal
					loading={loading}
					modal={uninstallModal}
					orderItem={selectedOrderItem}
					uninstall={uninstall}
				/>
			)}
		</>
	);
};

export default ProvisioningTable;
