/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MDFRequestDTO from '../../../common/interfaces/dto/mdfRequestDTO';
import MDFRequest from '../../../common/interfaces/mdfRequest';
import MDFRequestActivity from '../../../common/interfaces/mdfRequestActivity';
import createMDFRequestActivity from '../../../common/services/liferay/object/activity/createMDFRequestActivity';
import updateMDFRequestActivity from '../../../common/services/liferay/object/activity/updateMDFRequestActivity';
import {ResourceName} from '../../../common/services/liferay/object/enum/resourceName';

const submitMDFRequestActivity = async (
	mdfRequestActivity: MDFRequestActivity,
	mdfRequestDTO: MDFRequestDTO,
	mdfRequest: MDFRequest
) => {
	const dtoMDFClaimActivity = mdfRequestActivity.id
		? await updateMDFRequestActivity(
				ResourceName.ACTIVITY_DXP,
				mdfRequestActivity,
				mdfRequest,
				mdfRequestDTO
		  )
		: await createMDFRequestActivity(
				ResourceName.ACTIVITY_DXP,
				mdfRequestActivity,
				mdfRequest,
				mdfRequestDTO
		  );

	return dtoMDFClaimActivity;
};

export default submitMDFRequestActivity;
