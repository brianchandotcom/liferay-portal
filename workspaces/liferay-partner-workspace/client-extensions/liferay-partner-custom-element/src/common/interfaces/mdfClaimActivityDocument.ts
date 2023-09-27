/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import LiferayFile from './liferayFile';
import LiferayObject from './liferayObject';
import LiferayPicklist from './liferayPicklist';

export default interface MDFClaimActivityDocument
	extends Partial<LiferayObject> {
	allContents?: LiferayFile[] & number[];
	eventCollaterals?: LiferayFile[] & number[];
	eventInvitations?: LiferayFile[] & number[];
	eventPhotos?: LiferayFile[] & number[];
	images?: LiferayFile[] & number[];
	proofOfPerformanceType?: LiferayPicklist;
}
