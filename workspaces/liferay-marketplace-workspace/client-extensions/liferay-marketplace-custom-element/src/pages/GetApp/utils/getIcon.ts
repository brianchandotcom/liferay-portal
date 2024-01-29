/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getThumbnailByProductAttachment} from '../../../utils/util';

const getIconUrl = (product?: DeliveryProduct) => {
	const iconURL = product
		? getThumbnailByProductAttachment(product.images)?.split('/o/')
		: '';

	return iconURL ? `/o/${iconURL[1]}` : '';
};

export {getIconUrl};
