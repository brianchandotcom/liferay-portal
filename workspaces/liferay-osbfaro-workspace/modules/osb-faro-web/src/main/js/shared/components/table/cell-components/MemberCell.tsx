/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Text} from '@clayui/core';
import Link from '@clayui/link';
import getCN from 'classnames';
import React from 'react';
import {isBlank} from '~/shared/util/util';

interface IMemberCellProps {
	className?: string;
	data: {
		id: string;
		name: string;
		properties: {
			email?: string;
			emailAddress?: string;
		};
	};
	routeFn?: Function;
}

const MemberCell: React.FC<IMemberCellProps> = ({className, data, routeFn}) => {
	const {name, properties: {email, emailAddress} = {}} = data;

	const resolvedEmail = email || emailAddress;

	const anonymous = isBlank(resolvedEmail || '');

	return (
		<td className={getCN('name-cell-root', className)}>
			<div className="text-dark">
				<Text size={3} weight="semi-bold">
					{routeFn ? (
						<Link className="text-dark" href={routeFn({data})}>
							{name}
						</Link>
					) : (
						name
					)}
				</Text>
			</div>
			{!anonymous && (
				<Text color="secondary" size={3}>
					{resolvedEmail}
				</Text>
			)}
		</td>
	);
};

export default MemberCell;
