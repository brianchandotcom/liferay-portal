/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import moment from 'moment';
import * as date from '~/shared/util/date';

export default function () {
	return jest.spyOn(Date, 'now').mockImplementation(() => 0);
}

export const mockGetDateNow = function mockGetDateNow(mockDate) {
	date.getDateNow = jest.fn(() => moment(mockDate).utc());
};
