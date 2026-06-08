/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Map, Record} from 'immutable';

interface ICustomValue {
	criterionGroup?: Map<string, any>;
	operator?: string;
	value?: string | number;
}

export default class CustomValue
	extends Record({
		criterionGroup: Map(),
		operator: null,
		value: null,
	})
	implements ICustomValue
{
	criterionGroup?: Map<string, any>;
	operator?: string;
	value?: string | number;

	constructor(props = {}) {
		super(props);
	}
}
