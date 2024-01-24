/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useModal} from '@clayui/modal';
import {useCallback} from 'react';
import {KeyedMutator} from 'swr';

import i18n from '../../../../../i18n';
import {Liferay} from '../../../../../liferay/liferay';
import MarketplaceSpringBootOAuth2, {
	LicenseKey,
} from '../../../../../services/oauth/MarketplaceSpringBootOAuth2';

type Props = {
	deactivateLicenseModal: ReturnType<typeof useModal>;
	keyType: string;
	licenseKeyModal: ReturnType<typeof useModal>;
	marketplaceSpringBootOAuth2: MarketplaceSpringBootOAuth2;
	mutate: KeyedMutator<any>;
	setModal: (data: any) => void;
};

const useLicenseActions = ({
	deactivateLicenseModal,
	keyType,
	licenseKeyModal,
	marketplaceSpringBootOAuth2,
	mutate,
	setModal,
}: Props) => {
	const onDeativateLicenseKey = (row: LicenseKey) =>
		marketplaceSpringBootOAuth2
			.deactivateLicenseKey(row?.id as number)
			.then(() => {
				mutate((data: any) => data, {revalidate: true});

				Liferay.Util.openToast({
					message: i18n.translate(
						'key-deactivation-requested-succesfully'
					),
				});

				deactivateLicenseModal.onClose();
			});

	const onViewLicenseKey = (licenseKey: LicenseKey) => {
		licenseKeyModal.onOpenChange(true);

		setModal({...licenseKey, keyType});
	};

	const onDownload = useCallback(
		async (licenseKey: LicenseKey) => {
			if (!licenseKey?.id) {
				return;
			}

			try {
				await marketplaceSpringBootOAuth2.downloadLicenseKey(
					licenseKey?.id as number
				);
			}
			catch {
				Liferay.Util.openToast({
					message: i18n.translate(
						'unable-to-download-your-license-file-please-try-again-and-or-contact-support-via-the-manage-menu-on-the-dashboard'
					),
					type: 'danger',
				});
			}
		},
		[marketplaceSpringBootOAuth2]
	);

	return {
		onDeativateLicenseKey,
		onDownload,
		onViewLicenseKey,
	};
};

export default useLicenseActions;
