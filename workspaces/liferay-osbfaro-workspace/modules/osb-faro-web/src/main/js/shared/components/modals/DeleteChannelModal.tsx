/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import * as API from '~/shared/api';
import {SafeResults} from '~/shared/hoc/util';
import {useRequest} from '~/shared/hooks/useRequest';
import {sub} from '~/shared/util/lang';
import {DataSource} from '~/shared/util/records';

import DeleteConfirmationModal from './DeleteConfirmationModal';

interface IDeleteChannelModalProps extends React.HTMLAttributes<HTMLElement> {
	channelIds: Array<string>;
	channelName: string;
	groupId: string;
	onClose: () => void;
	onSubmit: () => void;
}

const renderDataSourceMessage = (items: Array<DataSource>, total: number) => {
	if (!total) {
		return null;
	}

	const dataSourceNames = items.map(({name}) => name).join(', ');

	return (
		<p>
			{sub(
				Liferay.Language.get(
					'to-reconnect-to-analytics-cloud-with-x,-please-make-sure-x-has-been-updated-with-the-latest-fixpack'
				),
				[dataSourceNames]
			)}
		</p>
	);
};

const DeleteChannelModal: React.FC<IDeleteChannelModalProps> = ({
	channelIds,
	channelName,
	className,
	groupId,
	onClose,
	onSubmit,
}) => {
	const {data, error, loading, refetch} = useRequest({
		dataSourceFn: API.dataSource.fetchChannels,
		variables: {
			channelIds,
			groupId,
		},
	});

	return (
		<DeleteConfirmationModal
			className={className}
			deleteConfirmationText={
				sub(Liferay.Language.get('delete-x'), [channelName]) as string
			}
			disabled={error || loading}
			onClose={onClose}
			onSubmit={onSubmit}
			title={
				sub(Liferay.Language.get('delete-x?'), [channelName]) as string
			}
		>
			<SafeResults
				data={data}
				error={error}
				loading={loading}
				page={false}
				pageDisplay={false}
				refetch={refetch}
				spacer
			>
				{({items, total}: {items: DataSource[]; total: number}) => (
					<>
						<div className="text-secondary">
							<p>
								<strong>
									{sub(
										Liferay.Language.get(
											'to-delete-x,-copy-the-sentence-below-to-confirm-your-intention-to-delete-property'
										),
										[channelName]
									)}
								</strong>
							</p>

							<p>
								{Liferay.Language.get(
									'this-will-result-in-the-complete-removal-of-this-propertys-historical-events.-you-will-not-be-able-to-undo-this-operation'
								)}
							</p>

							{renderDataSourceMessage(items, total)}
						</div>
					</>
				)}
			</SafeResults>
		</DeleteConfirmationModal>
	);
};

export default DeleteChannelModal;
