/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {List, Map, Record} from 'immutable';

import {EntityTypes} from '../constants';

interface IIndividual {
	accountName: string | null;
	accounts: List<Map<string, any>>;
	activitiesCount: number;
	context: Map<string, any>;
	dateCreated: string;
	demographics: Map<string, any>;
	firstActivityDate: string;
	id: string;
	lastActivityDate: string;
	lastSessionCountry: string | null;
	name: string;
	properties: Map<string, any>;
	type: EntityTypes.Individual;
}

export default class Individual
	extends Record({
		accountName: null,
		accounts: List(),
		activitiesCount: 0,
		context: Map(),
		dateCreated: null,
		demographics: Map(),
		firstActivityDate: null,
		id: null,
		lastActivityDate: null,
		lastSessionCountry: null,
		name: '',
		properties: Map(),
		type: EntityTypes.Individual,
	})
	implements IIndividual
{
	declare accountName: string | null;
	declare accounts: List<Map<string, any>>;
	declare activitiesCount: number;
	declare context: Map<string, any>;
	declare dateCreated: string;
	declare demographics: Map<string, any>;
	declare firstActivityDate: string;
	declare id: string;
	declare lastActivityDate: string;
	declare lastSessionCountry: string | null;
	declare name: string;
	declare properties: Map<string, any>;
	declare type: EntityTypes.Individual;

	constructor(props = {}) {
		super(props);
	}
}
