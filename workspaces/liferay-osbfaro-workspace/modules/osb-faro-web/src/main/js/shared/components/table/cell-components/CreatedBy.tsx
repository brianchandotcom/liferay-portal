/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {formatDateToTimeZone} from '~/shared/util/date';
import {sub} from '~/shared/util/lang';

interface ICreatedByProps {
	className?: string;
	data: {
		dateModified: number;
		userName: string;
	};
	timeZoneId: string;
}

const CreatedByCell: React.FC<ICreatedByProps> = ({
	className,
	data: {dateModified, userName},
	timeZoneId,
}) => (
	<td className={className}>
		<b>{userName}</b>
		<div className="text-secondary">
			<em>
				{sub(Liferay.Language.get('last-edited-x'), [
					formatDateToTimeZone(dateModified, 'M/D/YY', timeZoneId),
				])}
			</em>
		</div>
	</td>
);

export default CreatedByCell;
