/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import {useModal} from '@clayui/modal';

import Modal from '../../../../../../components/Modal';
import OrderStatus from '../../../../../../components/OrderStatus';
import Table from '../../../../../../components/Table/Table';
import useGetProductByOrderId from '../../../../../../hooks/useGetProductByOrderId';
import i18n from '../../../../../../i18n';

type ProvisioningTableProps = {
	orderInfo: ReturnType<typeof useGetProductByOrderId>;
};

const ProvisioningTable: React.FC<ProvisioningTableProps> = ({orderInfo}) => {
	const order = orderInfo.data?.placedOrder;

	const modal = useModal();

	const provisioningRows = [
		{
			environment: '',
			expirationDate: new Date().toDateString(),
			host: '',
			id: 0,
			project: '',
			startDate: order?.createDate,
			status: 'installed',
			type: 'Standard License',
		},
		{
			environment: 'UAT',
			expirationDate: new Date().toDateString(),
			host: 'PLPRIWS318.Hourglass-portal.com',
			id: 1,
			project: 'Klabindw',
			startDate: new Date().toDateString(),
			status: 'installed',
			type: 'Standard License',
		},
		{
			environment: 'UAT',
			expirationDate: new Date().toDateString(),
			host: 'PLPRIWS318.Hourglass-portal.com',
			id: 2,
			project: 'Klabindw',
			startDate: new Date().toDateString(),
			status: 'installed',
			type: 'Standard License',
		},
		{
			environment: 'UAT',
			expirationDate: new Date().toDateString(),
			host: 'PLPRIWS318.Hourglass-portal.com',
			id: 3,
			project: 'Klabindw',
			startDate: new Date().toDateString(),
			status: 'installed',
			type: 'Standard License',
		},
		{
			environment: 'UAT',
			expirationDate: new Date().toDateString(),
			host: 'PLPRIWS318.Hourglass-portal.com',
			id: 4,
			project: 'Klabindw',
			startDate: new Date().toDateString(),
			status: 'installed',
			type: 'Standard License',
		},
	];

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
									{provisioning.host || 'Not Installed'}
								</div>
							</>
						),
						title: (
							<>
								<div className="text-dark">Type</div>
								<div className="text-black-50">Host Name</div>
							</>
						),
						width: '500px',
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
								<div className="text-dark">Start Date -</div>
								<div className="text-dark">Exp. Date</div>
							</>
						),
					},
					{
						key: 'status',
						render: (status: any) => (
							<OrderStatus orderStatus="pending">
								{status}
							</OrderStatus>
						),
						title: <div className="text-dark">Status</div>,
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
								<div className="text-dark">Project</div>
								<div className="text-black-50">Exp. Date</div>
							</>
						),
					},
					{
						key: 'project',
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
										<ClayDropDown.Item onClick={() => {}}>
											{i18n.translate('view-details')}
										</ClayDropDown.Item>
										<ClayDropDown.Item onClick={() => {}}>
											{i18n.translate('install')}
										</ClayDropDown.Item>
									</ClayDropDown.ItemList>
								</ClayDropDown>
							</div>
						),
					},
				]}
				rows={provisioningRows}
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
						className="ml-2"
						displayType="primary"
						onClick={() => {}}
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
