/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {downloadActivationLicenseKey} from './downloadActivationLicenseKey';

export async function getActivationKeyDownload(
	provisioningServerAPI,
	sessionId,
	handleAlertStatus,
	activationKey,
	projectName
) {
	const downloadedKey = await downloadActivationLicenseKey(
		activationKey?.id,
		provisioningServerAPI,
		sessionId,
		activationKey?.productName,
		activationKey?.productVersion,
		projectName
	);

	return handleAlertStatus(downloadedKey);
}
