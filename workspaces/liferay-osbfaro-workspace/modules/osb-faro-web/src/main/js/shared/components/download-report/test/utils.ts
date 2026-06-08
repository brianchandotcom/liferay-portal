/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import moment from 'moment';

import {formatDate} from '../utils';

describe('formatDate', () => {
	it('returns formatted date for PDF document', () => {
		expect(formatDate(moment(0) as unknown as Date)).toBe('1970-01-01');
	});
});
