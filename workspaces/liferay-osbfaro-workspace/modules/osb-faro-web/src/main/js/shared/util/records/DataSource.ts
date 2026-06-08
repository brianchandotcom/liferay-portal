/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Map, Record, fromJS} from 'immutable';

interface IDataSource {
	createDate?: number;
	credentials?: Map<string, any>;
	disabled?: boolean;
	event?: string;
	fileName?: string;
	id?: string;
	name?: string;
	properties?: Map<string, any>;
	provider?: Map<string, any>;
	providerType?: string;
	state?: string;
	status?: string;
	type?: number;
	url?: string;
}

export default class DataSource
	extends Record({
		contactsSelected: false,
		createDate: 0,
		credentials: Map(),
		disabled: false,
		event: null,
		fileName: null,
		id: null,
		name: '',
		properties: null,
		provider: null,
		providerType: '',
		sitesSelected: false,
		state: null,
		status: null,
		type: 1,
		url: null,
	})
	implements IDataSource
{
	declare contactsSelected: boolean;
	declare createDate?: number;
	declare credentials?: Map<string, any>;
	declare disabled?: boolean;
	declare event?: string;
	declare fileName?: string;
	declare id?: string;
	declare name?: string;
	declare properties?: Map<string, any>;
	declare provider?: Map<string, any>;
	declare providerType?: string;
	declare sitesSelected: boolean;
	declare state?: string;
	declare status?: string;
	declare type?: number;
	declare url?: string;

	constructor(props: IDataSource) {
		super(fromJS(props));
	}
}
