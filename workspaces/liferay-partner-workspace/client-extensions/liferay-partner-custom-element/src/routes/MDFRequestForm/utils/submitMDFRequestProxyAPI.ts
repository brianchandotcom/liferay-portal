/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MDFRequestDTO from '../../../common/interfaces/dto/mdfRequestDTO';
import MDFRequest from '../../../common/interfaces/mdfRequest';
import {ResourceName} from '../../../common/services/liferay/object/enum/resourceName';
import createMDFRequest from '../../../common/services/liferay/object/mdf-requests/createMDFRequest';
import updateMDFRequest from '../../../common/services/liferay/object/mdf-requests/updateMDFRequest';

export default async function createMDFRequestProxyAPI(mdfRequest: MDFRequest) {
	let dtoMDFRequestSFResponse: MDFRequestDTO | undefined = undefined;

	if (mdfRequest.externalReferenceCode && mdfRequest.submitted) {
		dtoMDFRequestSFResponse = await updateMDFRequest(
			ResourceName.MDF_REQUEST_SALESFORCE,
			mdfRequest
		);
	}
	else {
		dtoMDFRequestSFResponse = await createMDFRequest(
			ResourceName.MDF_REQUEST_SALESFORCE,
			mdfRequest
		);
	}

	let dtoMDFRequestResponse: MDFRequestDTO | undefined = undefined;

	if (dtoMDFRequestSFResponse.externalReferenceCode) {
		if (mdfRequest.id) {
			mdfRequest.submitted = true;

			dtoMDFRequestResponse = await updateMDFRequest(
				ResourceName.MDF_REQUEST_DXP,
				mdfRequest,
				dtoMDFRequestSFResponse.externalReferenceCode
			);
		}
		else {
			mdfRequest.submitted = true;

			dtoMDFRequestResponse = await createMDFRequest(
				ResourceName.MDF_REQUEST_DXP,
				mdfRequest,
				dtoMDFRequestSFResponse.externalReferenceCode
			);
		}

		return dtoMDFRequestResponse;
	}
}
