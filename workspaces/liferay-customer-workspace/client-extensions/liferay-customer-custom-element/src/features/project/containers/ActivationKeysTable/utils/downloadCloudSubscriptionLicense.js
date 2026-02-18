/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CONTENT_TYPES, STATUS_CODE} from '~/features/project/utils/constants';
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
		const {license: base64License} = await license.json();

		const byteCharacters = atob(base64License);
		const byteNumbers = new Array(byteCharacters.length);

		for (let i = 0; i < byteCharacters.length; i++) {
			byteNumbers[i] = byteCharacters.charCodeAt(i);
		}

		const byteArray = new Uint8Array(byteNumbers);

		const licenseBlob = new Blob([byteArray], {
			type: CONTENT_TYPES.xmlText,
		});

		const projectFileName = projectName.replaceAll(' ', '').toLowerCase();

		const fileName = `${projectFileName}-${subscriptionUuid}-license.xml`;

		return downloadFromBlob(licenseBlob, fileName);
	}
}
