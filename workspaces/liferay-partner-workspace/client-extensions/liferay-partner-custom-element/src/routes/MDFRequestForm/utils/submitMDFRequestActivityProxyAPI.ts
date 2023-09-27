/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MDFRequestActivityDTO from '../../../common/interfaces/dto/mdfRequestActivityDTO';
import MDFRequestDTO from '../../../common/interfaces/dto/mdfRequestDTO';
import MDFRequest from '../../../common/interfaces/mdfRequest';
import MDFRequestActivity from '../../../common/interfaces/mdfRequestActivity';
import createMDFRequestActivities from '../../../common/services/liferay/object/activity/createMDFRequestActivity';
import updateMDFRequestActivities from '../../../common/services/liferay/object/activity/updateMDFRequestActivity';
import {ResourceName} from '../../../common/services/liferay/object/enum/resourceName';

export default async function submitMDFRequestActivityProxyAPI(
	mdfRequestActivity: MDFRequestActivity,
	mdfRequestDTO: MDFRequestDTO,
	mdfRequest: MDFRequest
) {
	let dtoMDFRequestActivitySFResponse:
		| MDFRequestActivityDTO
		| undefined = undefined;

	if (
		mdfRequestActivity.externalReferenceCode &&
		mdfRequestActivity.submitted
	) {
		dtoMDFRequestActivitySFResponse = await updateMDFRequestActivities(
			ResourceName.ACTIVITY_SALESFORCE,
			mdfRequestActivity,
			mdfRequest
		);
	}
	else {
		dtoMDFRequestActivitySFResponse = await createMDFRequestActivities(
			ResourceName.ACTIVITY_SALESFORCE,
			mdfRequestActivity,
			mdfRequest,
			mdfRequestDTO
		);
	}

	let dtoMDFRequestResponse: MDFRequestActivityDTO | undefined = undefined;

	if (dtoMDFRequestActivitySFResponse?.externalReferenceCode) {
		if (mdfRequestActivity.id && mdfRequestActivity.externalReferenceCode) {
			mdfRequestActivity.submitted = true;

			dtoMDFRequestResponse = await updateMDFRequestActivities(
				ResourceName.ACTIVITY_DXP,
				mdfRequestActivity,
				mdfRequest,
				mdfRequestDTO,
				dtoMDFRequestActivitySFResponse.externalReferenceCode
			);
		}
		else {
			mdfRequestActivity.submitted = true;

			dtoMDFRequestResponse = await createMDFRequestActivities(
				ResourceName.ACTIVITY_DXP,
				mdfRequestActivity,
				mdfRequest,
				mdfRequestDTO,
				dtoMDFRequestActivitySFResponse.externalReferenceCode
			);
		}
	}

	return dtoMDFRequestResponse;
}
