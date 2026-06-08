/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {List} from 'immutable';
import {Property} from '~/shared/util/records';

import {PropertyTypes} from '../constants';

export const DEVICE_OPTIONS = [
	{label: Liferay.Language.get('desktop'), value: 'Desktop'},
	{
		label: Liferay.Language.get('smartphone'),
		value: 'Smartphone',
	},
	{label: Liferay.Language.get('tablet'), value: 'Tablet'},
];

const createSessionProperty = ({
	label,
	name,
	type,
}: {
	label: string;
	name: string;
	type: PropertyTypes;
}) =>
	new Property({
		entityName: Liferay.Language.get('session'),
		label,
		name,
		propertyKey: 'session',
		type,
	});

const SESSION_PROPERTIES = List(
	[
		{
			label: Liferay.Language.get('browser'),
			name: 'context/browserName',
			type: PropertyTypes.SessionText,
		},
		{
			label: Liferay.Language.get('device'),
			name: 'context/deviceType',
			options: DEVICE_OPTIONS,
			type: PropertyTypes.SessionText,
		},
		{
			label: Liferay.Language.get('geolocation'),
			name: 'context/country',
			type: PropertyTypes.SessionGeolocation,
		},
		{
			label: Liferay.Language.get('referrer'),
			name: 'context/referrer',
			type: PropertyTypes.SessionText,
		},
		{
			label: Liferay.Language.get('date-&-time'),
			name: 'completeDate',
			type: PropertyTypes.SessionDateTime,
		},
		{
			label: Liferay.Language.get('url'),
			name: 'context/url',
			type: PropertyTypes.SessionText,
		},
	].map(createSessionProperty)
);

export default SESSION_PROPERTIES;
