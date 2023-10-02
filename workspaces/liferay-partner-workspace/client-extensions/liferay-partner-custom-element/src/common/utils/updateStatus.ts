/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import LiferayPicklist from '../interfaces/liferayPicklist';
import {Status} from './constants/status';

const updateStatus = (
	status: LiferayPicklist,
	currentRequestStatus?: LiferayPicklist,
	changeStatus?: boolean,
	id?: number,
	totalMDFRequestAmount?: number
) => {
	if (!id && currentRequestStatus) {
		status = currentRequestStatus;
	}

	if (!currentRequestStatus && id) {
		status = Status.PENDING;
	}
	else {
		if (changeStatus && currentRequestStatus?.key !== Status.DRAFT.key) {
			status = Status.PENDING;
		}

		if (
			id &&
			changeStatus &&
			currentRequestStatus?.key === Status.DRAFT.key
		) {
			status = currentRequestStatus;
		}

		if (
			changeStatus &&
			totalMDFRequestAmount &&
			totalMDFRequestAmount >= 15000 &&
			status.key !== Status.DRAFT.key
		) {
			status = Status.MARKETING_DIRECTOR_REVIEW;
		}
	}

	return status;
};
export default updateStatus;
