/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import React from 'react';
import Label from '~/shared/components/Label';

import {isExpired} from '../pages/AccessTokenList';
import {AccessToken} from '../types';

const TokenCell: React.FC<
	{data: AccessToken} & React.HTMLAttributes<HTMLElement>
> = ({className, data: {expirationDate, token}}) => {
	const expired = isExpired(expirationDate);

	return (
		<td
			className={getCN(className)}
			data-testid={`row-token-${token.slice(-4)}`}
		>
			<span className="mr-1 text-secondary">
				{Liferay.Language.get('token-ending-in')}
			</span>

			<strong className="font-weight-bold">{token.slice(-4)}</strong>

			{expired && (
				<Label className="ml-2" display="danger" size="lg" uppercase>
					{Liferay.Language.get('expired')}
				</Label>
			)}
		</td>
	);
};

export default TokenCell;
