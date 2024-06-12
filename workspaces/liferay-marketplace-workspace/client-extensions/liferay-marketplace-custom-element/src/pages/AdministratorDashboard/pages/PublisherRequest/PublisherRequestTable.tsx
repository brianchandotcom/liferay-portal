/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLabel from '@clayui/label';
import {useModal} from '@clayui/modal';
import {Status} from '@clayui/modal/lib/types';
import {format} from 'date-fns';
import {useState} from 'react';

import {DashboardEmptyTable} from '../../../../components/DashboardTable/DashboardEmptyTable';
import Table from '../../../../components/Table/Table';
import i18n from '../../../../i18n';
import PublisherRequestModal, {STATUS} from './PublisherRequestModal';

type AppsTableProps = {
	items: PublisherRequestInfo[];
	mutate: any;
};

const PublisherRequestTable: React.FC<AppsTableProps> = ({items, mutate}) => {
	const modal = useModal();
	const [selectedRequest, setSelectedRequest] =
		useState<PublisherRequestInfo>();

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
					modal.onOpenChange(true);
				}}
				rows={items}
			/>

			<PublisherRequestModal
				{...modal}
				mutate={mutate}
				selectedRequest={selectedRequest}
			/>
		</div>
	);
};

export default PublisherRequestTable;
