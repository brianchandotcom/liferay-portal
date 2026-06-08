/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {List, Record} from 'immutable';

import PropertySubgroup from './PropertySubgroup';

interface IPropertyGroup {
	entityName?: string;
	label: string;
	name?: string;
	propertyKey: string;
	propertySubgroups: List<PropertySubgroup>;
}

export default class PropertyGroup
	extends Record({
		entityName: '',
		label: '',
		name: '',
		propertyKey: '',
		propertySubgroups: List(),
	})
	implements IPropertyGroup
{
	declare entityName?: string;
	declare label: string;
	declare name?: string;
	declare propertyKey: string;
	declare propertySubgroups: List<PropertySubgroup>;

	constructor(props: IPropertyGroup) {
		super(props);
	}
}
