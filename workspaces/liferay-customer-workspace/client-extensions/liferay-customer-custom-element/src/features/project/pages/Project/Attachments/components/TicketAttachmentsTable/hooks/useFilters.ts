/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useMemo, useState} from 'react';
import i18n from '~/utils/I18n';
import {IKoroneikiAccount} from '~/utils/types';

const DEFAULT_SORT_CONFIG = {
	attribute: 'dateCreated',
	direction: 'desc',
};

export interface ILabel {
	label: number;
}

export interface IPaginationConfig {
	activePage: number;
	itemsPerPage: number;
	labels: {
		paginationResults: string;
		perPageItems: string;
		selectPerPageItems: string;
	};
	listItemsPerPage: ILabel[];
	setActivePage: (value: number) => void;
	setItemsPerPage: (value: number) => void;
	showDeltasDropDown: boolean;
}

export interface ISortConfig {
	attribute: string;
	direction: string;
}

export default function useFilters(koroneikiAccount?: IKoroneikiAccount): {
	filterQuery: string;
	handleSortChange: (value: string) => void;
	paginationConfig: IPaginationConfig;
} {
	const [activePage, setActivePage] = useState(1);
	const [itemsPerPage, setItemsPerPage] = useState(5);
	const [sortConfig, setSortConfig] = useState(DEFAULT_SORT_CONFIG);

	const handleSortChange = useCallback((attribute: string) => {
		setSortConfig((prevSortConfig: ISortConfig) => {
			return {
				attribute,
				direction:
					attribute === prevSortConfig.attribute
						? prevSortConfig.direction === 'desc'
							? 'asc'
							: 'desc'
						: 'desc',
			};
		});
	}, []);

	const paginationConfig = useMemo(
		() => ({
			activePage,
			itemsPerPage,
			labels: {
				paginationResults: i18n.translate('showing-x-to-x-of-x'),
				perPageItems: i18n.translate('show-x-items'),
				selectPerPageItems: i18n.translate('x-items'),
			},
			listItemsPerPage: [
				{label: 5},
				{label: 10},
				{label: 20},
				{label: 50},
			],
			setActivePage,
			setItemsPerPage,
			showDeltasDropDown: true,
		}),
		[activePage, itemsPerPage]
	);

	const generateFilterQuery = useCallback(() => {
		if (!koroneikiAccount) {
			return '';
		}

		return `accountKey eq '${koroneikiAccount.accountKey}' and (state eq 0 or state eq null) and status/any(s:s eq 0)&page=${paginationConfig.activePage}&pageSize=${paginationConfig.itemsPerPage}&sort=${sortConfig.attribute}:${sortConfig.direction}`;
	}, [koroneikiAccount, paginationConfig, sortConfig]);

	const filterQuery = useMemo(
		() => generateFilterQuery(),
		[generateFilterQuery]
	);

	return {filterQuery, handleSortChange, paginationConfig};
}
