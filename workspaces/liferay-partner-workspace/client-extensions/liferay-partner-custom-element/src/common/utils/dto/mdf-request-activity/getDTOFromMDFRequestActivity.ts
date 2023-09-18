/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MDFRequestActivityDTO from '../../../interfaces/dto/mdfRequestActivityDTO';
import MDFRequestDTO from '../../../interfaces/dto/mdfRequestDTO';
import MDFRequest from '../../../interfaces/mdfRequest';
import MDFRequestActivity from '../../../interfaces/mdfRequestActivity';

export default function getDTOFromMDFRequestActivity(
	mdfRequestActivity: MDFRequestActivity,
	mdfRequest: MDFRequest,
	mdfRequestDTO?: MDFRequestDTO,
	externalReferenceCodeFromSF?: string
): MDFRequestActivityDTO {
	const {activityDescription, ...newMDFRequestActivity} = mdfRequestActivity;

	delete activityDescription?.creator;
	delete activityDescription?.externalReferenceCode;
	delete activityDescription?.status;

	return {
		...activityDescription,
		activityStatus: mdfRequestActivity.activityStatus,
		currency: mdfRequestActivity.currency,
		...newMDFRequestActivity,
		externalReferenceCode: externalReferenceCodeFromSF,
		leadFollowUpStrategies: activityDescription?.leadFollowUpStrategies?.join(
			', '
		),
		mdfRequestExternalReferenceCode: mdfRequestDTO?.externalReferenceCode,
		r_accToActs_accountEntryERC: mdfRequest.company?.externalReferenceCode,
		r_accToActs_accountEntryId: mdfRequest.company?.id,
		r_mdfReqToActs_c_mdfRequestERC: mdfRequestDTO?.externalReferenceCode,
		r_mdfReqToActs_c_mdfRequestId: mdfRequestDTO?.id,
	};
}
