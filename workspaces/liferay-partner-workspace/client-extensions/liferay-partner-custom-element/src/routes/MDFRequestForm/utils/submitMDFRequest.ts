/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MDFRequest from '../../../common/interfaces/mdfRequest';
import {ResourceName} from '../../../common/services/liferay/object/enum/resourceName';
import createMDFRequest from '../../../common/services/liferay/object/mdf-requests/createMDFRequest';
import updateMDFRequest from '../../../common/services/liferay/object/mdf-requests/updateMDFRequest';

const submitMDFRequest = async (mdfRequest: MDFRequest) => {
	const dtoMDFRequest = mdfRequest.id
		? await updateMDFRequest(ResourceName.MDF_REQUEST_DXP, mdfRequest)
		: await createMDFRequest(ResourceName.MDF_REQUEST_DXP, mdfRequest);

	return dtoMDFRequest;
};

export default submitMDFRequest;
