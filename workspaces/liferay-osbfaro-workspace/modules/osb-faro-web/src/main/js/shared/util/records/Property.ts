/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Record} from 'immutable';
import {PropertyTypes} from '~/segment/segment-editor/dynamic/utils/constants';

interface IProperty {
	entityName: string;
	entityType?: string;
	id?: string;
	label: string;
	name: string;
	options?: {label: string; value: string | boolean}[];
	propertyKey: string;
	type: PropertyTypes;
}

export default class Property
	extends Record({
		entityName: '',
		entityType: '',
		id: null,
		label: '',
		name: '',
		options: [],
		propertyKey: '',
		type: null,
	})
	implements IProperty
{
	declare entityName: string;
	declare entityType: string;
	declare id: string;
	declare label: string;
	declare name: string;
	declare options?: {label: string; value: string | boolean}[];
	declare propertyKey: string;
	declare type: PropertyTypes;

	constructor(props: IProperty) {
		super(props);
	}
}
