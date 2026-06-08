/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import {OrderedMap} from 'immutable';
import React from 'react';
import {ConnectedProps, connect} from 'react-redux';
import {close, modalTypes, open} from '~/shared/actions/modals';
import Input from '~/shared/components/Input';
import Form from '~/shared/components/form';
import {Columns} from '~/shared/types';
import {NAME, createOrderIOMap} from '~/shared/util/pagination';
import {OrderParams} from '~/shared/util/records';
import {detailsListColumns} from '~/shared/util/table-columns';

const connector = connect(null, {close, open});

type PropsFromRedux = ConnectedProps<typeof connector>;

interface ISelectEntityFromModalProps extends PropsFromRedux {
	columns: Columns;
	dataSourceFn?: (params: {[key: string]: any}) => Promise<any>;
	entity?: {dataSourceName?: string; [key: string]: any};
	error: boolean;
	graphqlProps?: {[key: string]: any};
	groupId?: string;
	initialDelta?: number;
	initialOrderIOMap: OrderedMap<string, OrderParams>;
	noResultsIcon?: string;
	noResultsProps?: {[key: string]: any};
	onSubmit: (items: OrderedMap<string, object>) => void;
	orderByOptions?: {label: string; value: any}[];
	renderEntity: (entity: any) => React.ReactNode;
	submitMessage?: string;
	title: string;
}

const SelectEntityFromModal: React.FC<ISelectEntityFromModalProps> = ({
	close,
	columns,
	dataSourceFn,
	entity,
	error,
	graphqlProps,
	groupId,
	initialDelta = 10,
	initialOrderIOMap = createOrderIOMap(NAME),
	onSubmit,
	open,
	renderEntity,
	submitMessage = Liferay.Language.get('add'),
	title,
	...otherProps
}) => {
	const handleModal = () => {
		const dataSourceOptions = graphqlProps || {dataSourceFn};
		const modalType = graphqlProps
			? modalTypes.SEARCHABLE_TABLE_MODAL_GRAPHQL
			: modalTypes.SEARCHABLE_TABLE_MODAL;

		open(modalType, {
			...dataSourceOptions,
			columns: [
				...columns,
				{
					...detailsListColumns.getDataSourceName(groupId),
					className: 'table-cell-expand',
					sortable: false,
				},
			],
			dataSourceParams: {groupId},
			initialDelta,
			initialOrderIOMap,
			initialSelectedItems: entity?.id ? [entity] : undefined,
			onClose: close,
			onSubmit: (items: OrderedMap<string, object>) => {
				onSubmit(items);

				close();
			},
			submitMessage,
			title,
			...otherProps,
		});
	};

	return (
		<Form.GroupItem>
			<Input.Group className="select-entity-group">
				<Input.GroupItem
					className={getCN({
						'has-error': error,
					})}
				>
					<Input.Group className="select-input-root">
						<Input.GroupItem>
							<Input />
						</Input.GroupItem>

						<div className="selected-item-container">
							{renderEntity(entity)}
						</div>
					</Input.Group>
				</Input.GroupItem>

				<Input.Button
					displayType="secondary"
					onClick={handleModal}
					position="append"
				>
					{Liferay.Language.get('select')}
				</Input.Button>
			</Input.Group>
		</Form.GroupItem>
	);
};

export default connector(SelectEntityFromModal);
