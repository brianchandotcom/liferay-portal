/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {toThousands} from '~/shared/util/numbers';

const ToThousandsCell = ({data}: {data: any}) => {
	const formattedCount = toThousands(data.individualCount);

	return (
		<td className="table-cell-expand">
			<div className="text-right text-truncate">{formattedCount}</div>
		</td>
	);
};

export default ToThousandsCell;
