/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {useModal} from '@clayui/modal';
import {useState} from 'react';
import {useNavigate} from 'react-router-dom';
import {KeyedMutator} from 'swr';

import Modal from '../../../../components/Modal';
import OrderStatus from '../../../../components/OrderStatus';
import Table from '../../../../components/Table/Table';
import TableKebabButton from '../../../../components/Table/TableButtons/TableKebabButton';
import {
	PRODUCT_WORKFLOW_STATUS_CODE,
	PRODUCT_WORKFLOW_STATUS_LABEL,
} from '../../../../enums/Product';
import i18n from '../../../../i18n';
import {Liferay} from '../../../../liferay/liferay';
import HeadlessCommerceAdminCatalogImpl from '../../../../services/rest/HeadlessCommerceAdminCatalog';
import {formatDate} from '../../PublisherDashboardPageUtil';

type PublishedSolutionsTableProps = {
	items: Order[];
	mutate: KeyedMutator<any>;
};

const PublishedSolutionsTable: React.FC<PublishedSolutionsTableProps> = ({
	items,
	mutate,
}) => {
	const [loading, setLoading] = useState(false);
	const [selectedApp, setSelectedApp] = useState<Product>({} as Product);

	const modal = useModal();
	const navigate = useNavigate();

	const handleDeleteSolution = async (product: Product) => {
		setLoading(true);

		try {
			await HeadlessCommerceAdminCatalogImpl.deleteProduct(
				product.productId
			);

			mutate(items);

			Liferay.Util.openToast({
				message: i18n.translate('request-sent-successfully'),
				type: 'success',
			});

			modal.onClose();

			setSelectedApp({} as Product);
		}
		catch (error) {
			Liferay.Util.openToast({
				message: i18n.translate('an-unexpected-error-occurred'),
				type: 'danger',
			});
		}

		setLoading(false);
	};

	return (
		<>
			<Table
				Actions={({row}) => (
					<TableKebabButton
						items={[
							{
								disabled:
									row.workflowStatusInfo.code ===
									PRODUCT_WORKFLOW_STATUS_CODE.PENDING,
								label: i18n.translate('edit'),
								onClick: () =>
									navigate(
										`${row.productId}/publisher/profile`
									),
							},
							{
								disabled:
									row.workflowStatusInfo.code ===
									PRODUCT_WORKFLOW_STATUS_CODE.PENDING,
								label: i18n.translate('delete'),
								onClick: () => {
									setSelectedApp(row);

									modal.onOpenChange(true);
								},
							},
						]}
					/>
				)}
				columns={[
					{
						key: 'name',
						render: (name, {thumbnail}) => (
							<div style={{width: 200}}>
								<img
									alt="App Image"
									className="app-details-page-table-icon"
									src={thumbnail}
								/>

								<span className="font-weight-semi-bold ml-2">
									{name?.en_US}
								</span>
							</div>
						),
						title: i18n.translate('name'),
					},
					{
						key: 'solutionType',
						render: () => 'Page',
						title: 'Solution Type',
					},
					{
						key: 'modifiedDate',
						render: (modifiedDate) => (
							<b>{formatDate(modifiedDate)}</b>
						),
						title: 'Last Updated',
					},
					{
						key: 'workflowStatusInfo',
						render: (workflowStatusInfo) => (
							<OrderStatus orderStatus={workflowStatusInfo.label}>
								{
									PRODUCT_WORKFLOW_STATUS_LABEL[
										workflowStatusInfo.code as keyof typeof PRODUCT_WORKFLOW_STATUS_LABEL
									]
								}
							</OrderStatus>
						),
						title: i18n.translate('status'),
					},
				]}
				hasKebabButton
				onClickRow={({productId}) => navigate(`${productId}`)}
				rows={items}
			/>

			<Modal
				last={
					<>
						<ClayButton
							displayType="secondary"
							onClick={modal.onClose}
							size="sm"
						>
							{i18n.translate('cancel')}
						</ClayButton>

						<ClayButton
							className="ml-2"
							disabled={loading}
							displayType="danger"
							onClick={() => handleDeleteSolution(selectedApp)}
							size="sm"
						>
							{i18n.translate('delete')}
						</ClayButton>
					</>
				}
				observer={modal.observer}
				size={'md' as any}
				status="danger"
				title={`${i18n.translate('deleting')} ${
					selectedApp.name?.en_US
				}`}
				visible={modal.open}
			>
				{i18n.sub(
					'x-will-be-deleted-and-this-action-cant-be-undone-are-you-sure-you-want-to-delete-it',
					selectedApp.name?.en_US
				)}
			</Modal>
		</>
	);
};

export default PublishedSolutionsTable;
