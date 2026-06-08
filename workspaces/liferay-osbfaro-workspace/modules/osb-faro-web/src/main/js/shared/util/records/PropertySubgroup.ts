/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {List, Record} from 'immutable';

import Property from './Property';

interface IPropertySubgroup {
	label?: string;
	properties: List<Property>;
}

export default class PropertySubgroup
	extends Record({
		label: '',
		properties: List(),
	})
	implements IPropertySubgroup
{
	declare label?: string;
	declare properties: List<Property>;

	constructor(props: IPropertySubgroup) {
		super(props);
	}
}
