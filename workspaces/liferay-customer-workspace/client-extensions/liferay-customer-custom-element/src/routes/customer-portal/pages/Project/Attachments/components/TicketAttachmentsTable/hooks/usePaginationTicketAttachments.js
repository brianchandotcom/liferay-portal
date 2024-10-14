/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo, useState} from 'react';
import i18n from '~/common/I18n';

import getSortedTicketAttachments from '../utils/getSortedTicketAttachments';

export default function usePagination(sortConfig, ticketAttachments) {
	const [activePage, setActivePage] = useState(1);
	const [itemsPerPage, setItemsPerPage] = useState(5);
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
			totalCount: ticketAttachments?.length,
		}),
		[activePage, itemsPerPage, ticketAttachments?.length]
	);

	const sortedTicketAttachmentsFilteredPerPage = useMemo(() => {
		const sortedTicketAttachments = getSortedTicketAttachments(
			ticketAttachments,
			sortConfig
		);

		if (sortedTicketAttachments) {
			const sortedTicketAttachmentsFilteredPerPage =
				sortedTicketAttachments.slice(
					itemsPerPage * activePage - itemsPerPage,
					itemsPerPage * activePage
				);

			return sortedTicketAttachmentsFilteredPerPage?.length
				? sortedTicketAttachmentsFilteredPerPage
				: sortedTicketAttachments;
		}

		return [];
	}, [activePage, itemsPerPage, sortConfig, ticketAttachments]);

	return {paginationConfig, sortedTicketAttachmentsFilteredPerPage};
}
