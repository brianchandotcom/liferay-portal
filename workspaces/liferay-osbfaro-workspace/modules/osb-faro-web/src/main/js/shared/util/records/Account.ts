/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Map, Record} from 'immutable';

import {EntityTypes} from '../constants';

interface IAccount {
	activitiesCount: number;
	createTime?: string;
	id?: string;
	individualCount: number;
	name: string;
	photoURL: string;
	properties: Map<string, any>;
	status?: string;
	type: EntityTypes.Account;
}

export default class Account
	extends Record({
		activitiesCount: 0,
		createTime: null,
		id: null,
		individualCount: 0,
		name: '',
		photoURL: '',
		properties: Map(),
		status: null,
		type: EntityTypes.Account,
	})
	implements IAccount
{
	declare activitiesCount: number;
	declare createTime?: string;
	declare id?: string;
	declare individualCount: number;
	declare name: string;
	declare photoURL: string;
	declare properties: Map<string, any>;
	declare status?: string;
	declare type: EntityTypes.Account;

	constructor(props = {}) {
		super(props);
	}
}
