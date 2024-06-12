/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MDFClaimDTO from '../../../common/interfaces/dto/mdfClaimDTO';
import LiferayPicklist from '../../../common/interfaces/liferayPicklist';
import MDFClaim from '../../../common/interfaces/mdfClaim';
import {Liferay} from '../../../common/services/liferay';
import {ResourceName} from '../../../common/services/liferay/object/enum/resourceName';
import patchObjectEntry from '../../../common/services/liferay/object/patchObjectEntry/patchObjectEntry';

const patchClaimStatus = async (
	mdfClaimStatus: LiferayPicklist,
	mdfClaimId: string,
	values?: Partial<MDFClaim>
) => {
	try {
		const mdfClaimDTO = await patchObjectEntry<MDFClaimDTO>(
			ResourceName.MDF_CLAIM_DXP,
			mdfClaimId,
			values
				? values
				: {
						mdfClaimStatus,
					}
		);

		if (mdfClaimDTO) {
			return mdfClaimDTO.mdfClaimStatus;
		}

		return;
	}
	catch (error: unknown) {
		Liferay.Util.openToast({
			message: 'The MDF Claim Status cannot be changed.',
			type: 'danger',
		});

		return;
	}
};

export default patchClaimStatus;
