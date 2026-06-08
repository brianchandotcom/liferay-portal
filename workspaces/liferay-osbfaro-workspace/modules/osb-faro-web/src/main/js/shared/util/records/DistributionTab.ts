/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Record} from 'immutable';

interface IDistributionTab {
	context: string;
	id: string;
	numberOfBins: number;
	propertyId: string;
	propertyType: string;
	title: string;
}

export default class DistributionTab
	extends Record({
		context: null,
		id: null,
		numberOfBins: null,
		propertyId: null,
		propertyType: null,
		title: '',
	})
	implements IDistributionTab
{
	declare context: string;
	declare id: string;
	declare numberOfBins: number;
	declare propertyId: string;
	declare propertyType: string;
	declare title: string;

	constructor(props: IDistributionTab) {
		super(props);
	}
}
