/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MDFRequestDTO from '../../../common/interfaces/dto/mdfRequestDTO';
import LiferayPicklist from '../../../common/interfaces/liferayPicklist';
import {Liferay} from '../../../common/services/liferay';
import {LiferayAPIs} from '../../../common/services/liferay/common/enums/apis';
import liferayFetcher from '../../../common/services/liferay/common/utils/fetcher';
import {ResourceName} from '../../../common/services/liferay/object/enum/resourceName';
import patchObjectEntry from '../../../common/services/liferay/object/patchObjectEntry/patchObjectEntry';
import {Status} from '../../../common/utils/constants/status';

const patchRequestStatus = async (
	mdfRequestStatus: LiferayPicklist,
	mdfRequestId: string
) => {
	try {
		await patchObjectEntry<MDFRequestDTO>(
			ResourceName.MDF_REQUEST_DXP,
			mdfRequestId,
			{
				mdfRequestStatus,
			}
		);

		const mdfRequestDTO = (await liferayFetcher(
			`/o/${LiferayAPIs.OBJECT}/${ResourceName.MDF_REQUEST_DXP}/${mdfRequestId}?nestedFields=mdfReqToMDFClms`,
			Liferay.authToken
		)) as MDFRequestDTO;

		if (mdfRequestDTO) {
			if (
				mdfRequestDTO.mdfRequestStatus.key === Status.APPROVED.key &&
				mdfRequestDTO.mdfReqToMDFClms?.length
			) {
				for (const claim of mdfRequestDTO.mdfReqToMDFClms) {
					if (
						claim.id &&
						claim.mdfClaimStatus?.key === Status.CANCELED.key
					) {
						await patchObjectEntry(
							ResourceName.MDF_CLAIM_DXP,
							claim.id,
							{
								mdfClaimStatus: Status.APPROVED,
							}
						);
					}
				}
				location.reload();

				return mdfRequestDTO.mdfRequestStatus;
			}
			else if (
				mdfRequestDTO.mdfRequestStatus.key === Status.CANCELED.key &&
				mdfRequestDTO.mdfReqToMDFClms?.length
			) {
				for (const claim of mdfRequestDTO.mdfReqToMDFClms) {
					if (
						claim.id &&
						(claim.mdfClaimStatus?.key === Status.APPROVED.key ||
							claim.mdfClaimStatus?.key === Status.DRAFT.key)
					) {
						await patchObjectEntry(
							ResourceName.MDF_CLAIM_DXP,
							claim.id,
							{
								mdfClaimStatus: Status.CANCELED,
							}
						);
					}
				}

				return mdfRequestDTO.mdfRequestStatus;
			}

			return mdfRequestDTO.mdfRequestStatus;
		}

		return;
	}
	catch (error: unknown) {
		Liferay.Util.openToast({
			message: 'The MDF Request Status cannot be changed.',
			type: 'danger',
		});

		return;
	}
};

export default patchRequestStatus;
