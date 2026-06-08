/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import {noop} from 'lodash';
import React from 'react';
import {Link} from 'react-router-dom';
import TextTruncate from '~/shared/components/TextTruncate';

interface INameProps {
	className?: string;
	data: {
		id?: string;
		name: string;
		[key: string]: any;
	};
	disabled?: boolean;
	maxWidth?: number;
	nameKey?: string;
	renderIcon?: Function;
	renderSecondaryInfo?: Function;
	routeFn?: Function;
	tooltip?: boolean;
}

const Name: React.FC<INameProps> = ({
	className,
	data,
	disabled = false,
	maxWidth,
	nameKey = 'name',
	renderIcon,
	renderSecondaryInfo,
	routeFn = noop,
	tooltip = false,
}) => {
	const getSecondaryInfo = () =>
		!!renderSecondaryInfo && (
			<div className="secondary-info text-truncate">
				{renderSecondaryInfo(data) || '-'}
			</div>
		);

	const displayName = data[nameKey] || '-';

	const url = routeFn({data});

	const titleContent = tooltip ? (
		<TextTruncate title={displayName} />
	) : (
		displayName
	);

	return (
		<td className={getCN('name-cell-root', className)}>
			<div
				className="content-container"
				style={maxWidth ? {maxWidth: `${maxWidth}px`} : undefined}
			>
				{!!renderIcon && (
					<div className="icon-container">{renderIcon(data)}</div>
				)}

				<div className="text-truncate">
					<div className="table-title text-truncate">
						{disabled || !url ? (
							titleContent
						) : (
							<Link className="text-truncate" to={url}>
								{titleContent}
							</Link>
						)}
					</div>

					{getSecondaryInfo()}
				</div>
			</div>
		</td>
	);
};

export default Name;
