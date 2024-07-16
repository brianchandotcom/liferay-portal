/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useMemo, useState} from 'react';
import i18n from '../../../../../common/I18n';
import {getLicenseKeyPermanentStatus} from '../../GenerateNewKey/utils/licenseKeyPermanentStatus';
import {ACTIVATION_KEYS_LICENSE_FILTER_TYPES} from '../utils/constants';

export default function usePagination(
	activationKeys,
	isRenewTable,
	setAllActivationKeys,
	statusFilter
) {
	const [activePage, setActivePage] = useState(1);
	const [itemsPerPage, setItemsPerPage] = useState(5);
	const [currentTotalCount, setCurrentTotalCount] = useState(0);

	useEffect(() => {
		if (statusFilter) {
			setActivePage(1);
		}
	}, [statusFilter]);

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
			totalCount: currentTotalCount,
		}),
		[activePage, currentTotalCount, itemsPerPage]
	);

	const activationKeysByStatusPaginated = useMemo(() => {
		const activationKeysFilteredByStatus = activationKeys?.filter(
			(activationKey) =>
				ACTIVATION_KEYS_LICENSE_FILTER_TYPES[statusFilter](
					activationKey
				)
		);

		setAllActivationKeys(activationKeysFilteredByStatus);

		if (activationKeysFilteredByStatus) {
			if (isRenewTable) {
				const activationKeysFilteredbyRenewable =
					activationKeysFilteredByStatus?.filter((activationKey) => {
						const isPermanentLicenseKey =
							getLicenseKeyPermanentStatus(
								activationKey?.startDate,
								activationKey?.expirationDate
							);

						if (!isPermanentLicenseKey) {
							return activationKey;
						}
					});

				setCurrentTotalCount(activationKeysFilteredbyRenewable.length);

				const activationKeysFilteredByRenewablePerPage =
					activationKeysFilteredbyRenewable.slice(
						itemsPerPage * activePage - itemsPerPage,
						itemsPerPage * activePage
					);

				return activationKeysFilteredByRenewablePerPage?.length
					? activationKeysFilteredByRenewablePerPage
					: activationKeysFilteredbyRenewable;
			}

			setCurrentTotalCount(activationKeysFilteredByStatus.length);

			const activationKeysFilteredByStatusPerPage =
				activationKeysFilteredByStatus.slice(
					itemsPerPage * activePage - itemsPerPage,
					itemsPerPage * activePage
				);

			return activationKeysFilteredByStatusPerPage?.length
				? activationKeysFilteredByStatusPerPage
				: activationKeysFilteredByStatus;
		}

		return [];
	}, [
		activationKeys,
		activePage,
		isRenewTable,
		itemsPerPage,
		setAllActivationKeys,
		statusFilter,
	]);

	return {activationKeysByStatusPaginated, paginationConfig};
}
