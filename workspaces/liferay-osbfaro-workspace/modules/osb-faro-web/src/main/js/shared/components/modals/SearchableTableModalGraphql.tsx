/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DocumentNode, QueryHookOptions, useQuery} from '@apollo/client';
import ClayButton from '@clayui/button';
import {OrderedMap} from 'immutable';
import {noop} from 'lodash';
import React, {useEffect} from 'react';
import Modal from '~/shared/components/modal';
import {
	ACTION_TYPES,
	useSelectionContext,
	withSelectionProvider,
} from '~/shared/context/selection';
import CrossPageSelect from '~/shared/hoc/CrossPageSelect';
import {useStatefulPagination} from '~/shared/hooks/useStatefulPagination';
import {Columns, IPagination} from '~/shared/types';
import {safeResultToProps} from '~/shared/util/mappers';
import {OrderParams} from '~/shared/util/records';

interface ISearchableTableModalGraphQLProps extends IPagination {
	className: string;
	columns: Columns;
	graphqlQuery: DocumentNode;
	initialDelta?: number;
	initialOrderIOMap: OrderedMap<string, OrderParams>;
	instruction?: string;
	mapPropsToOptions: (props: {[key: string]: any}) => QueryHookOptions;
	mapResultToProps: (result: {[key: string]: any}) => {
		items: any[];
		total: number;
	};
	onClose?: () => void;
	onSubmit: (selectedItems: OrderedMap<string, any>) => void;
	requireSelection?: boolean;
	selectedItems?: any[];
	submitMessage?: string;
	title?: string;
}

const SearchableTableModalGraphql: React.FC<
	ISearchableTableModalGraphQLProps
> = ({
	className,
	columns,
	graphqlQuery,
	initialDelta = 5,
	initialOrderIOMap,
	instruction = '',
	mapPropsToOptions,
	mapResultToProps,
	onClose = noop,
	onSubmit,
	requireSelection = true,
	selectedItems = [],
	submitMessage = Liferay.Language.get('submit'),
	title = Liferay.Language.get('select-items'),
	...otherProps
}) => {
	const {
		delta,
		onDeltaChange,
		onOrderIOMapChange,
		onPageChange,
		onQueryChange,
		orderIOMap,
		page,
		query,
	} = useStatefulPagination(undefined, {initialDelta, initialOrderIOMap});

	const {data, error, loading} = useQuery(
		graphqlQuery,
		mapPropsToOptions({...otherProps, delta, orderIOMap, page, query})
	);

	const {selectedItems: contextSelectedItems, selectionDispatch} =
		useSelectionContext();

	useEffect(() => {
		if (selectedItems.length) {
			selectionDispatch?.({type: ACTION_TYPES.clearAll});
			selectionDispatch?.({
				payload: {items: selectedItems},
				type: ACTION_TYPES.add,
			});
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const handleSubmit = () => onSubmit(contextSelectedItems);

	const {empty, items, total} = safeResultToProps(mapResultToProps)({
		data: {error, loading, ...data},
		ownProps: {},
	});

	return (
		<Modal className={className} size="lg">
			<Modal.Header onClose={onClose} title={title} />

			<Modal.Body className="p-0">
				<div className="text-secondary">{instruction}</div>

				<CrossPageSelect
					{...otherProps}
					autoFocus
					columns={columns}
					delta={delta}
					empty={empty}
					items={items}
					loading={loading}
					onDeltaChange={onDeltaChange}
					onOrderIOMapChange={onOrderIOMapChange}
					onPageChange={onPageChange}
					onQueryChange={onQueryChange}
					orderIOMap={orderIOMap}
					page={page}
					pageDisplay={false}
					query={query}
					total={total}
				/>
			</Modal.Body>

			<Modal.Footer>
				<ClayButton
					className="button-root"
					displayType="secondary"
					onClick={onClose}
				>
					{Liferay.Language.get('cancel')}
				</ClayButton>

				<ClayButton
					className="button-root"
					disabled={requireSelection && !contextSelectedItems.size}
					displayType="primary"
					onClick={handleSubmit}
				>
					{submitMessage}
				</ClayButton>
			</Modal.Footer>
		</Modal>
	);
};

export default withSelectionProvider(SearchableTableModalGraphql);
