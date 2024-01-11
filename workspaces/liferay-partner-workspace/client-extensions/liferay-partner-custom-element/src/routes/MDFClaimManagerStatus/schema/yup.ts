/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {date, number, object, string} from 'yup';

const claimPaidSchema = object({
	checkNumber: string()
		.trim()
		.max(255, 'Reached Max Characters')
		.required('Required'),
	claimPaid: number()
		.max(999999999, 'The value cannot be greater than 9,999,999.99')
		.moreThan(0, 'Required')
		.required('Required'),
	paymentDate: date()
		.required('Required')
		.test(
			'format',
			'Date is invalid',
			(date) => (date?.getFullYear() ?? 0) <= 9999
		),
});

export default claimPaidSchema;
