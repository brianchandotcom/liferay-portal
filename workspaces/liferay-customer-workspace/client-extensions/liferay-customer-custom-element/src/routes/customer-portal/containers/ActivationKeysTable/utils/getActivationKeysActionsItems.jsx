/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import i18n from '../../../../../common/I18n';
import {downloadAllKeysDetails} from './downloadActivationLicenseKey';

export function getActivationKeysActionsItems(
	accountKey,
	oAuthToken,
	provisioningServerAPI,
	handleAlertStatus,
	handleRedirectPage,
	handleDeactivatePage,
	productName,
	allowSelfProvisioning,
	hasRenewalSubscription,
	handleRedirectRenewPage
) {
	const dropdownItems = [];

	if (allowSelfProvisioning) {
		dropdownItems.push({
			icon: (
				<ClayIcon
					className="mr-1 rounded text-neutral-4"
					symbol="plus"
				/>
			),
			label: i18n.translate('generate-new'),
			onClick: handleRedirectPage,
		});

		if (hasRenewalSubscription) {
			dropdownItems.push({
				icon: (
					<ClayIcon
						className="mr-1 rounded text-neutral-4"
						symbol="reload"
					/>
				),
				label: i18n.translate('renew'),
				onClick: handleRedirectRenewPage,
			});
		}

		dropdownItems.push({
			icon: (
				<ClayIcon
					className="mr-1 text-neutral-4"
					symbol="minus-circle"
				/>
			),
			label: i18n.translate('deactivate'),
			onClick: handleDeactivatePage,
		});
	}

	dropdownItems.push({
		icon: <ClayIcon className="mr-1 text-neutral-4" symbol="download" />,
		label: i18n.translate('export-all-key-details-csv'),
		onClick: async () => {
			const downloadedAggregated = await downloadAllKeysDetails(
				accountKey,
				oAuthToken,
				provisioningServerAPI,
				productName
			);

			return handleAlertStatus(downloadedAggregated);
		},
	});

	return dropdownItems;
}
