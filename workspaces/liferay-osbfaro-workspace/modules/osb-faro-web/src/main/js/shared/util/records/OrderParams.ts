/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Record} from 'immutable';

import {OrderByDirections} from '../constants';

interface IOrderParams {
	field: string;
	sortOrder: OrderByDirections;
}

export default class OrderParams
	extends Record({
		field: null,
		sortOrder: null,
	})
	implements IOrderParams
{
	declare field: string;
	declare sortOrder: OrderByDirections;

	constructor(props = {}) {
		super(props);
	}
}
