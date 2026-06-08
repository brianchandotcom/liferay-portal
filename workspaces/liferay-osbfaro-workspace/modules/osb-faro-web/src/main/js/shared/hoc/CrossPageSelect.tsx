/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {OrderedMap} from 'immutable';
import {get} from 'lodash';
import React, {useEffect, useState} from 'react';
import {ACTION_TYPES, useSelectionContext} from '~/shared/context/selection';
import ListComponent from '~/shared/hoc/ListComponent';
import {useStatefulPagination} from '~/shared/hooks/useStatefulPagination';
import {FilterByType, IPagination} from '~/shared/types';
import {OrderByDirections} from '~/shared/util/constants';
import {sub} from '~/shared/util/lang';
import {getDisplayName} from '~/shared/util/react';
import {OrderParams} from '~/shared/util/records';
import {getSafeDisplayValue} from '~/shared/util/util';

type SearchArgs = {
	filterBy?: FilterByType;
	items: OrderedMap<any, any>;
	query: string;
};

export type SearchFnType = ({items, query}: SearchArgs) => OrderedMap<any, any>;

/**
 * Function for local search on items.
 */
export const defaultSearch: SearchFnType = function defaultSearch({
	items,
	query,
}: SearchArgs) {
	return items.filter(
		(item) =>
			Object.values(get(item, 'properties', {})).some((value: any) =>
				String(getSafeDisplayValue(value, ''))
					.toLowerCase()
					.match(query.toLowerCase())
			) ||
			(item.name || item.emailAddress || '')
				.toLowerCase()
				.match(query.toLowerCase())
	) as OrderedMap<any, any>;
};

/**
 * Function for local sort on items.
 */
export const defaultSort = function defaultSort(
	items: OrderedMap<any, any>,
	orderIOMap: OrderedMap<string, OrderParams>
): OrderedMap<any, any> {
	const first = orderIOMap.first() as OrderParams | undefined;
	const field = first?.field ?? '';
	const sortOrder = first?.sortOrder;

	const sorted = items.sortBy((item) => {
		if (item[field]) {
			return item[field];
		}
		else if (get(item, ['properties', field])) {
			return item.properties[field];
		}
		else {
			return item;
		}
	});

	return sortOrder === OrderByDirections.Ascending
		? (sorted as OrderedMap<any, any>)
		: (sorted.reverse() as OrderedMap<any, any>);
};

export const fetchLocalData = function fetchLocalData({
	delta,
	filterBy,
	items,
	orderIOMap,
	page,
	query,
	searchSelectedFn = defaultSearch,
}: {
	delta: number;
	filterBy?: FilterByType;
	items: OrderedMap<any, any>;
	orderIOMap: OrderedMap<string, OrderParams>;
	page: number;
	query?: string;
	searchSelectedFn?: ({
		filterBy,
		items,
		query,
	}: SearchArgs) => OrderedMap<any, any>;
}) {
	const start = (page - 1) * delta;

	const end = start + delta;

	const result =
		query || filterBy
			? defaultSort(
					searchSelectedFn({filterBy, items, query: query ?? ''}),
					orderIOMap
				)
			: defaultSort(items, orderIOMap);

	return {
		empty: !result.size,
		items: result.slice(start, end).toArray(),
		total: result.size,
	};
};

export const withLocalData = function withLocalData() {
	return <P extends {[key: string]: any}>(
			WrappedComponent: React.ComponentType<P>
		) =>
		(props: P) => {
			const {delta, filterBy, orderIOMap, page, query, searchSelectedFn} =
				props;

			const {selectedItems} = useSelectionContext();

			return (
				<WrappedComponent
					{...props}
					{...fetchLocalData({
						delta,
						filterBy,
						items: selectedItems,
						orderIOMap,
						page,
						query,
						searchSelectedFn,
					})}
				/>
			);
		};
};

interface IwithSelectionProps {
	checkDisabled?: (item?: object) => boolean;
	items: {id: string}[];
	[key: string]: any;
}

/**
 * HOC for mapping information about items
 * and SelectionContext into props for Toolbar and Table
 * @param WrappedComponent
 * @returns {Function}
 */
export const withSelection: (
	WrappedComponent: React.ComponentType<any>
) => React.FC<IwithSelectionProps> = function withSelection(WrappedComponent) {
	const WithSelection: React.FC<IwithSelectionProps> = ({
		checkDisabled = () => false,
		items = [],
		showCheckbox = true,
		...otherProps
	}) => {
		const {selectedItems, selectionDispatch} = useSelectionContext();

		const allChecked =
			!selectedItems.isEmpty() &&
			items.every(
				(item) => selectedItems.has(item.id) || checkDisabled(item)
			);

		const selectionProps = {
			alwaysShowSearch: true,
			onSelectEntirePage: (checked: boolean) => {
				selectionDispatch?.({
					payload: {
						items: items.filter((item) => !checkDisabled(item)),
					},
					type: checked ? ACTION_TYPES.add : ACTION_TYPES.remove,
				});
			},
			onSelectItemsChange: (item: {id: string}) =>
				selectionDispatch?.({
					payload: {item},
					type: ACTION_TYPES.toggle,
				}),
			selectedItemsIOMap: selectedItems,

			selectEntirePage: allChecked,
			selectEntirePageIndeterminate:
				!allChecked && !selectedItems.isEmpty(),
			showCheckbox,
		};

		return (
			<WrappedComponent
				{...otherProps}
				{...selectionProps}
				checkDisabled={checkDisabled}
				items={items}
			/>
		);
	};

	WithSelection.displayName = `WithSelection(${getDisplayName(
		WrappedComponent
	)})`;

	return WithSelection;
};

export const ViewSelectedToggle = function ViewSelectedToggle({
	onClick,
	selectedItemsCount,
	showSelected,
}: {
	onClick: () => void;
	selectedItemsCount: number;
	showSelected: boolean;
}) {
	return (
		<ClayButton
			className="button-root"
			data-testid="view-selected"
			displayType="unstyled"
			onClick={onClick}
			small
		>
			<b>
				{showSelected
					? Liferay.Language.get('return-to-list')
					: sub(Liferay.Language.get('view-selected-x'), [
							selectedItemsCount,
						])}
			</b>
		</ClayButton>
	);
};

interface ICrossPageSelectProps extends IPagination {
	children: (val: any) => React.ReactElement;
	onDeltaChange: (delta: number) => void;
	onFilterByChange: (filterBy: FilterByType) => void;
	onOrderIOMapChange: (orderIOMap: OrderedMap<string, OrderParams>) => void;
	onPageChange: (page: number) => void;
	onQueryChange: (query: string) => void;
	searchSelectedFn: SearchFnType;
	showCheckbox?: boolean;
}

const CrossPageSelect: React.FC<ICrossPageSelectProps> = ({
	children,
	delta,
	filterBy,
	onDeltaChange,
	onFilterByChange,
	onOrderIOMapChange,
	onPageChange,
	onQueryChange,
	orderIOMap,
	page,
	query,
	searchSelectedFn,
	showCheckbox,
	...otherProps
}) => {
	const {
		filterBy: stagedFilterBy,
		onFilterByChange: onStagedFilterByChange,
		onPageChange: onStagedPageChange,
		onQueryChange: onStagedQueryChange,
		page: stagedPage,
		query: stagedQuery,
	} = useStatefulPagination(undefined, {
		initialDelta: delta,
		initialOrderIOMap: orderIOMap,
	});
	const {selectedItems, selectionDispatch} = useSelectionContext();
	const [showSelected, setShowSelected] = useState(false);

	// eslint-disable-next-line react-hooks/exhaustive-deps
	useEffect(() => {
		if (selectedItems.isEmpty() && showSelected) {
			setShowSelected(false);
		}

		if (
			stagedPage > 1 &&
			selectedItems.size <= delta * stagedPage - delta
		) {
			onStagedPageChange(stagedPage - 1);
		}
	});

	const renderViewSelectedToggle = () => (
		<ViewSelectedToggle
			onClick={() => setShowSelected(!showSelected)}
			selectedItemsCount={selectedItems.size}
			showSelected={showSelected}
		/>
	);

	const localData = fetchLocalData({
		delta,
		filterBy: stagedFilterBy,
		items: selectedItems,
		orderIOMap,
		page: stagedPage,
		query: stagedQuery,
		searchSelectedFn,
	});

	if (showSelected) {
		return (
			<ListComponent
				{...otherProps}
				delta={delta}
				filterBy={stagedFilterBy}
				onDeltaChange={onDeltaChange}
				onFilterByChange={onStagedFilterByChange}
				onOrderIOMapChange={onOrderIOMapChange}
				onPageChange={onStagedPageChange}
				onQueryChange={onStagedQueryChange}
				orderIOMap={orderIOMap}
				page={stagedPage}
				query={stagedQuery}
				renderViewSelectedToggle={renderViewSelectedToggle}
				selectedItems={selectedItems}
				selectionDispatch={selectionDispatch}
				showCheckbox
				{...localData}
			/>
		);
	}
	else {
		const sharedProps = {
			...otherProps,
			delta,
			filterBy,
			onDeltaChange,
			onFilterByChange,
			onOrderIOMapChange,
			onPageChange,
			onQueryChange,
			orderIOMap,
			page,
			query,
			renderViewSelectedToggle,
			selectedItems,
			selectionDispatch,
			showCheckbox,
		};

		if (children) {
			return children(sharedProps);
		}
		else {
			return <ListComponent {...sharedProps} />;
		}
	}
};

export default withSelection(CrossPageSelect);
