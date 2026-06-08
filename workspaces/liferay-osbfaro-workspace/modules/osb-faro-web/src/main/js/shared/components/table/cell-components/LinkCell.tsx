/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {Link} from 'react-router-dom';
import TextTruncate from '~/shared/components/TextTruncate';

interface ILinkCellProps {
	data: {id?: string; name: string};
	hrefFormatter: (data: any) => string;
}

const LinkCell = ({data, hrefFormatter}: ILinkCellProps) => (
	<td className="table-cell-expand" key={data.id}>
		<div className="table-title text-truncate">
			<Link to={hrefFormatter(data)}>
				<TextTruncate title={data.name} />
			</Link>
		</div>
	</td>
);

export default LinkCell;
