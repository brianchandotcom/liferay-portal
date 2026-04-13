/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import EmptyState from '../../../../components/EmptyState';
import Table from '../../../../components/ListView/components/Table';
import i18n from '../../../../i18n';

export type PurchasedItem = {
	productName: string;
	purchaseCount: number;
	thumbnail: string;
};

type AdministratorMostPurchasedListViewProps = {
	items: PurchasedItem[];
};

const AdministratorMostPurchasedListView: React.FC<
	AdministratorMostPurchasedListViewProps
> = ({items}) => (
	<>
		{!items.length && (
			<EmptyState title={i18n.translate('no-results-found')} />
		)}

		{!!items.length && (
			<Table<PurchasedItem>
				columns={[
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
					},
					{
						id: 'purchaseCount',
						name: i18n.translate('number-of-purchases'),
					},
				]}
				items={items}
				mutate={() => Promise.resolve(undefined)}
				onSort={() => {}}
			/>
		)}
	</>
);

export default AdministratorMostPurchasedListView;
