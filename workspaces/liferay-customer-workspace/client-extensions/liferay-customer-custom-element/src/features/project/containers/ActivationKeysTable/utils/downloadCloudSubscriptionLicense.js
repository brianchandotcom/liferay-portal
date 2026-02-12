/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	EXTENSION_FILE_TYPES,
	STATUS_CODE,
} from '~/features/project/utils/constants';
import {getCloudSubscriptionLicenseKey} from '~/services/liferay/rest/raysource/LicenseKeys';
import downloadFromBlob from '~/utils/downloadFromBlob';

export async function downloadCloudSubscriptionLicense(
	oAuthToken,
	provisioningServerAPI,
	subscriptionUuid,
	projectName
) {
	const license = await getCloudSubscriptionLicenseKey(
		oAuthToken,
		provisioningServerAPI,
		subscriptionUuid
	);

	if (license.status === STATUS_CODE.success) {
		const contentType = license.headers.get('content-type');
		const extensionFile = EXTENSION_FILE_TYPES[contentType] || '.txt';
		const licenseBlob = await license.blob();

		const projectFileName = projectName.replaceAll(' ', '').toLowerCase();
		const fileName = `cloud-license-${subscriptionUuid}-${projectFileName}${extensionFile}`;

		return downloadFromBlob(licenseBlob, fileName);
	}
}
