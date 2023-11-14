/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useModal} from '@clayui/modal';
import {useCallback} from 'react';
import {KeyedMutator} from 'swr';

import i18n from '../../../../i18n';
import {Liferay} from '../../../../liferay/liferay';
import ProvisioningKoroneikiOAuth2, {
	LicenseKey,
} from '../../../../services/oauth/ProvisioningKoroneikiOAuth2';

type Props = {
	deactivateLicenseModal: ReturnType<typeof useModal>;
	keyType: string;
	licenseKeyModal: ReturnType<typeof useModal>;
	mutate: KeyedMutator<any>;
	provisioningKoroneikiOAuth2: ProvisioningKoroneikiOAuth2;
	setModal: (data: any) => void;
};

const useLicenseActions = ({
	deactivateLicenseModal,
	keyType,
	licenseKeyModal,
	mutate,
	provisioningKoroneikiOAuth2,
	setModal,
}: Props) => {
	const onDeativateLicenseKey = (row: LicenseKey) =>
		provisioningKoroneikiOAuth2
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
		(licenseKey: LicenseKey) => {
			if (licenseKey?.id) {
				return provisioningKoroneikiOAuth2.downloadLicenseKey(
					licenseKey?.id as number
				);
			}

			Liferay.Util.openToast({
				message: i18n.translate(
					'unable-to-download-your-license-file-please-try-again-and-or-contact-support-via-the-manage-menu-on-the-dashboard'
				),
				type: 'danger',
			});
		},
		[provisioningKoroneikiOAuth2]
	);

	return {
		onDeativateLicenseKey,
		onDownload,
		onViewLicenseKey,
	};
};

export default useLicenseActions;
