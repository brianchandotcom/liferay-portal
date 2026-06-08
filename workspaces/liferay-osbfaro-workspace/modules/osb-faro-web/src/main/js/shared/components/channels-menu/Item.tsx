/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDropDown from '@clayui/drop-down';
import ClayLink from '@clayui/link';
import getCN from 'classnames';
import {noop} from 'lodash';
import React from 'react';
import {toRoute} from '~/shared/util/router';

import TextTruncate from '../TextTruncate';
import {Channel} from './index';

interface ISitesDropdownItem
	extends React.ComponentProps<typeof ClayDropDown.Item> {
	active?: boolean;
	channel: Channel;
	groupId: string;
	onClick?: typeof noop;
	route: string;
}

const ChannelsMenuDropdownItem: React.FC<ISitesDropdownItem> = ({
	active = false,
	channel: {id, name},
	className,
	groupId,
	onClick,
	route,
}) => {
	const classes = getCN('sites-dropdown-item', className, {
		active,
	});

	return (
		<ClayDropDown.Item className={classes}>
			<ClayLink
				className="link"
				href={toRoute(route, {channelId: id, groupId})}
				onClick={onClick}
			>
				<TextTruncate
					className="link-content-wrapper"
					inline
					title={name}
				/>
			</ClayLink>
		</ClayDropDown.Item>
	);
};
export default ChannelsMenuDropdownItem;
