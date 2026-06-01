/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useMemo, useState} from 'react';

import EmptyState from '../../../../components/EmptyState';
import Table, {
	TableProps,
} from '../../../../components/ListView/components/Table';
import {Sort} from '../../../../components/ListView/hooks/ListViewContext';
import {OrderTypes, orderTypeLabel} from '../../../../enums/Order';
import i18n from '../../../../i18n';
import {SortOption} from '../../../../utils/constants';

export type PurchasedItem = {
	orderTypeExternalReferenceCode: string;
	productName: string;
	purchaseCount: number;
	thumbnail: string;
};

type AdministratorMostPurchasedListViewProps = {
	items: PurchasedItem[];
	showAppType?: boolean;
};

function getAppTypeLabel(erc: string) {
	return (orderTypeLabel as any)[erc as OrderTypes] ?? erc;
}

const AdministratorMostPurchasedListView: React.FC<
	AdministratorMostPurchasedListViewProps
> = ({items, showAppType = false}) => {
	const [sort, setSort] = useState<Sort>({
		direction: SortOption.DESC,
		key: 'purchaseCount',
	});

	const sortedItems = useMemo(() => {
		const compare = (a: PurchasedItem, b: PurchasedItem) => {
			if (sort.key === 'purchaseCount') {
				return a.purchaseCount - b.purchaseCount;
			}

			if (sort.key === 'orderTypeExternalReferenceCode') {
				return getAppTypeLabel(
					a.orderTypeExternalReferenceCode
				).localeCompare(
					getAppTypeLabel(b.orderTypeExternalReferenceCode)
				);
			}

			return a.productName.localeCompare(b.productName);
		};

		const sorted = [...items].sort(compare);

		return sort.direction === SortOption.DESC ? sorted.reverse() : sorted;
	}, [items, sort]);

	const columns: TableProps<PurchasedItem>['columns'] = [
		{
			id: 'productName',
			name: i18n.translate('name'),
			render: (productName, {thumbnail}) => (
				<div className="align-items-center d-flex">
					<img
						alt={productName}
						className="app-details-page-table-icon"
						src={thumbnail}
					/>

					<span className="font-weight-semi-bold ml-3 text-nowrap">
						{productName}
					</span>
				</div>
			),
			sortable: true,
		},
		...(showAppType
			? ([
					{
						id: 'orderTypeExternalReferenceCode',
						name: i18n.translate('app-type'),
						render: (erc) => getAppTypeLabel(erc),
						sortable: true,
					},
				] as TableProps<PurchasedItem>['columns'])
			: []),
		{
			id: 'purchaseCount',
			name: i18n.translate('number-of-purchases'),
			sortable: true,
		},
	];

	return (
		<>
			{!items.length && (
				<EmptyState title={i18n.translate('no-results-found')} />
			)}

			{!!items.length && (
				<Table<PurchasedItem>
					columns={columns}
					items={sortedItems}
					mutate={() => Promise.resolve(undefined)}
					onSort={(key, direction) => setSort({direction, key})}
					sort={sort}
				/>
			)}
		</>
	);
};

export default AdministratorMostPurchasedListView;
