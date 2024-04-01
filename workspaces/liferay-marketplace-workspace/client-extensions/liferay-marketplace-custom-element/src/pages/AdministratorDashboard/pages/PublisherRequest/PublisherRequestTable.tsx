/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLabel from '@clayui/label';
import {useModal} from '@clayui/modal';
import {Status} from '@clayui/modal/lib/types';
import {format} from 'date-fns';
import {useState} from 'react';

import {DashboardEmptyTable} from '../../../../components/DashboardTable/DashboardEmptyTable';
import Modal from '../../../../components/Modal';
import Table from '../../../../components/Table/Table';
import i18n from '../../../../i18n';
import {Liferay} from '../../../../liferay/liferay';
import fetcher from '../../../../services/fetcher';
import PublisherSummaryContent from '../../../PublisherGate/components/PublisherSummaryContent';

type AppsTableProps = {
	items: PublisherRequestInfo[];
	mutate: any;
};

const STATUS = {
	completed: 'success',
	inProgress: 'info',
	open: 'secondary',
	rejected: 'danger',
};

const PublisherRequestTable: React.FC<AppsTableProps> = ({items, mutate}) => {
	const {observer, onOpenChange, open} = useModal();
	const [selectedRequest, setSelectedRequest] =
		useState<PublisherRequestInfo>();

	const showModalButtons =
		(selectedRequest?.requestStatus?.key ?? 'open') === 'open';

	const selectedRequestStatus = STATUS[
		selectedRequest?.requestStatus?.key as keyof typeof STATUS
	] as Status;

	const onUpdateRequestStatus = async (status: 'completed' | 'rejected') => {
		await fetcher.patch(
			`o/c/requestpublisheraccounts/${Number(selectedRequest?.id)}`,
			{
				requestStatus: status,
			}
		);

		mutate(items);

		Liferay.Util.openToast({
			message: i18n.translate('your-request-completed-successfully'),
			type: 'success',
		});

		onOpenChange(false);
	};

	if (!items?.length) {
		return (
			<DashboardEmptyTable
				icon="grid"
				title={i18n.translate('no-become-a-publisher-request')}
			/>
		);
	}

	return (
		<div>
			<Table
				columns={[
					{
						key: 'firstName',
						render: (name, {lastName}) => (
							<span className="text-capitalize text-nowrap">{`${name}  ${lastName}`}</span>
						),
						title: i18n.translate('name'),
					},
					{
						key: 'emailAddress',
						title: i18n.translate('email'),
					},
					{
						key: 'requestDescription',
						render: (requestDescription) => (
							<span
								className="text-truncate"
								title={requestDescription}
							>
								{requestDescription}
							</span>
						),
						title: i18n.translate('description'),
						truncate: true,
					},
					{
						key: 'creator',
						render: (creator) => (
							<div>{creator?.name ?? 'Guest'}</div>
						),
						title: i18n.translate('requester'),
					},
					{
						key: 'dateCreated',
						render: (dateCreated) => (
							<span className="ml-2 text-capitalize text-nowrap">
								{format(new Date(dateCreated), 'MMM dd, yyyy')}
							</span>
						),
						title: i18n.translate('created-at'),
					},
					{
						key: 'requestStatus',
						render: (
							requestStatus = {key: 'open', name: 'Open'}
						) => (
							<ClayLabel
								className="text-nowrap"
								displayType={
									STATUS[
										requestStatus?.key as keyof typeof STATUS
									] as Status
								}
							>
								{requestStatus?.name}
							</ClayLabel>
						),
						title: i18n.translate('status'),
					},
				]}
				onClickRow={(item: PublisherRequestInfo) => {
					setSelectedRequest(item);
					onOpenChange(true);
				}}
				rows={items}
			/>

			<Modal
				last={
					showModalButtons ? (
						<div>
							<ClayButton
								className="mr-3"
								displayType="danger"
								onClick={() =>
									onUpdateRequestStatus('rejected')
								}
							>
								{i18n.translate('decline')}
							</ClayButton>

							<ClayButton
								displayType="primary"
								onClick={() =>
									onUpdateRequestStatus('completed')
								}
							>
								{i18n.translate('aprove')}
							</ClayButton>
						</div>
					) : undefined
				}
				observer={observer}
				size="lg"
				status={selectedRequestStatus}
				title={`Review Request - ${
					selectedRequest?.requestStatus?.name ?? 'Open'
				}`}
				visible={open}
			>
				<PublisherSummaryContent userInfo={selectedRequest} />
			</Modal>
		</div>
	);
};

export default PublisherRequestTable;
