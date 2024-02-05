/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MDFRequestActivityDTO from '../../../common/interfaces/dto/mdfRequestActivityDTO';
import MDFRequestDTO from '../../../common/interfaces/dto/mdfRequestDTO';
import LiferayPicklist from '../../../common/interfaces/liferayPicklist';
import {Liferay} from '../../../common/services/liferay';
import {ResourceName} from '../../../common/services/liferay/object/enum/resourceName';
import patchObjectEntry from '../../../common/services/liferay/object/patchObjectEntry/patchObjectEntry';
import {Status} from '../../../common/utils/constants/status';

const patchRequestStatus = async (
	mdfRequestStatus: LiferayPicklist,
	mdfRequestId: string,
	mdfReqToActs?: MDFRequestActivityDTO[]
) => {
	try {
		const mdfRequestDTO = await patchObjectEntry<MDFRequestDTO>(
			ResourceName.MDF_REQUEST_DXP,
			mdfRequestId,
			{
				mdfRequestStatus,
			}
		);

		if (mdfRequestDTO) {
			if (
				mdfRequestDTO.mdfRequestStatus.key === Status.APPROVED.key &&
				mdfReqToActs?.length
			) {
				for (const activity of mdfReqToActs) {
					activity.id &&
						activity.activityStatus?.key === Status.SUBMITTED.key &&
						(await patchObjectEntry(
							ResourceName.ACTIVITY_DXP,
							activity.id,
							{
								activityStatus: Status.APPROVED,
							}
						));
				}
				location.reload();

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
