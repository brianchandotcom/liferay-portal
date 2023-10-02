/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MDFRequestDTO from '../../../common/interfaces/dto/mdfRequestDTO';
import MDFClaim from '../../../common/interfaces/mdfClaim';
import {ResourceName} from '../../../common/services/liferay/object/enum/resourceName';
import createMDFClaim from '../../../common/services/liferay/object/mdf-claim/createMDFClaim';
import updateMDFClaim from '../../../common/services/liferay/object/mdf-claim/updateMDFClaim';

const submitMDFClaim = async (
	mdfClaim: MDFClaim,
	mdfRequest: MDFRequestDTO
) => {
	const dtoMDFClaim = mdfClaim.id
		? await updateMDFClaim(ResourceName.MDF_CLAIM_DXP, mdfClaim, mdfRequest)
		: await createMDFClaim(
				ResourceName.MDF_CLAIM_DXP,
				mdfClaim,
				mdfRequest
		  );

	return dtoMDFClaim;
};

export default submitMDFClaim;
