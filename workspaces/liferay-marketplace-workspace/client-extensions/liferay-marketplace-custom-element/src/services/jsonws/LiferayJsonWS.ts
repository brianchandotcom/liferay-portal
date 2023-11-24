/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Liferay} from '../../liferay/liferay';

class LiferayJsonWS {
	async addExpandoValue({
		attributeValues,
		className,
		classPK,
		companyId,
		tableName,
	}: {
		attributeValues: Object;
		className: string;
		classPK: number;
		companyId: string;
		tableName: string;
	}) {
		await Liferay.Service('/expandovalue/add-values', {
			attributeValues,
			className,
			classPK,
			companyId,
			tableName,
		});
	}
}

export {LiferayJsonWS};
