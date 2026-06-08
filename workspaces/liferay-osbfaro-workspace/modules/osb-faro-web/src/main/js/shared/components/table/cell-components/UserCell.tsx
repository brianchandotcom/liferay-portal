/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Sticker from '@clayui/sticker';
import React from 'react';
import {getInitials} from '~/shared/util/util';

const UserCell = ({data}: {data: any}) => (
	<td className="table-cell-expand">
		<Sticker displayType="secondary" shape="user-icon" size="sm">
			{getInitials(data.userName)}
		</Sticker>

		<span className="ml-2">{data.userName}</span>
	</td>
);

export default UserCell;
