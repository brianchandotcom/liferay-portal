/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import {get} from 'lodash';
import React from 'react';

import {EntityTypes} from '../util/constants';
import Sticker, {getDisplayForId, getSymbol} from './Sticker';

const getInitials = (first: string, last: string): string => {
	let retVal = first ? first.substring(0, 1) : '';

	if (last) {
		retVal += last.substring(0, 1);
	}

	return retVal.toUpperCase();
};

interface IAvatarProps extends React.HTMLAttributes<HTMLDivElement> {
	entity: {
		id: string;
		properties?: {
			familyName: string;
			givenName: string;
		};
		type: EntityTypes;
	};
}

const Avatar: React.FC<IAvatarProps> = ({
	className,
	entity: {id, properties, type},
	...otherProps
}) => {
	const image = get(properties, 'image');

	return (
		<Sticker
			className={getCN('avatar-root', className)}
			display={getDisplayForId(id)}
			style={
				!image
					? undefined
					: {
							backgroundImage: `url(${image})`,
						}
			}
			symbol={type !== EntityTypes.Individual ? getSymbol(type) : null}
			{...otherProps}
		>
			{type === EntityTypes.Individual &&
				!image &&
				getInitials(
					properties?.givenName ?? '',
					properties?.familyName ?? ''
				)}
		</Sticker>
	);
};

export default Avatar;
