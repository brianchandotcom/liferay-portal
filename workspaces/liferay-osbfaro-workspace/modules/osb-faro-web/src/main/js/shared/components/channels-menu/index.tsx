/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import React, {useRef, useState} from 'react';
import {connect} from 'react-redux';
import {Link} from 'react-router-dom';
import {updateDefaultChannelId} from '~/shared/actions/preferences';
import {Routes, toRoute} from '~/shared/util/router';

import Item from './Item';

export type Channel = {
	createTime: number;
	groupIdCount: number;
	id: string;
	name: string;
	permissionType: number;
	tokenAuth: boolean;
};

interface IChannelsMenuProps extends React.HTMLAttributes<HTMLElement> {
	channels: Channel[];
	defaultChannelId?: string;
	groupId: string;
	updateDefaultChannelId: ({
		defaultChannelId,
		groupId,
	}: {
		defaultChannelId: string;
		groupId: string;
	}) => void;
}

interface IChannelsButtonProps
	extends React.ButtonHTMLAttributes<HTMLButtonElement> {
	channel?: Channel | null;
}

export const getDefaultChannel = function getDefaultChannel(
	defaultChannelId: string | undefined,
	channels: Channel[]
) {
	if (channels && !!channels.length) {
		return channels.find(({id}) => id === defaultChannelId) || channels[0];
	}

	return null;
};

const ChannelsButton = React.forwardRef<
	HTMLButtonElement,
	IChannelsButtonProps
>(({channel, className, ...otherProps}, ref) => (
	<button
		className={getCN(
			'channels-menu button-root btn btn-unstyled',
			className
		)}
		ref={ref}
		{...otherProps}
	>
		<div className="channels-menu-icon">
			<ClayIcon className="icon-root" symbol="sites" />
		</div>

		<div className="channels-menu-label">
			{channel ? channel.name : Liferay.Language.get('no-properties')}
		</div>

		<div className="channels-menu-caret">
			<ClayIcon className="icon-root" symbol="caret-right" />
		</div>
	</button>
));

export const ChannelsMenu = function ChannelsMenu({
	channels,
	className,
	defaultChannelId,
	groupId,
	updateDefaultChannelId,
}: IChannelsMenuProps) {
	const [active, setActive] = useState(false);
	const [searchTerm, setSearchTerm] = useState('');
	const triggerElementRef = useRef(null);
	const menuElementRef = useRef(null);

	const handleActive = () => {
		setActive(!active);
	};

	const channel = getDefaultChannel(defaultChannelId, channels);

	return (
		<>
			<ChannelsButton
				channel={channel}
				className={className}
				onClick={channel ? handleActive : undefined}
				ref={triggerElementRef}
			/>
			{channel && (
				<ClayDropDown.Menu
					active={active}
					alignElementRef={triggerElementRef}
					alignmentPosition={Align.RightTop}
					className="channels-menu-dropdown"
					offsetFn={() => [12, 0]}
					onSetActive={setActive}
					ref={menuElementRef}
				>
					<>
						<div className="channels-menu-dropdown-header">
							<div className="title">
								<div>{Liferay.Language.get('properties')}</div>
								<Link
									className="text-white"
									to={toRoute(Routes.SETTINGS_CHANNELS, {
										groupId,
									})}
								>
									<ClayIcon
										className="icon-root"
										symbol="cog"
									/>
								</Link>
							</div>

							<ClayDropDown.Search
								className="header-search"
								formProps={{
									onSubmit: (event) => event.preventDefault(),
								}}
								onChange={setSearchTerm}
								placeholder={Liferay.Language.get('search')}
								value={searchTerm}
							/>
						</div>
						<div className="channels-menu-dropdown-body">
							<ClayDropDown.ItemList>
								{channels.map((channel, i) => {
									if (
										!searchTerm ||
										channel.name
											.toLowerCase()
											.includes(searchTerm.toLowerCase())
									) {
										return (
											<Item
												active={
													defaultChannelId ===
													channel.id
												}
												channel={channel}
												groupId={groupId}
												key={i}
												onClick={() => {
													updateDefaultChannelId({
														defaultChannelId:
															channel.id,
														groupId,
													});
													handleActive();
												}}
												route={Routes.SITES}
											/>
										);
									}
								})}
							</ClayDropDown.ItemList>
						</div>
					</>
				</ClayDropDown.Menu>
			)}
		</>
	);
};

export default connect(null, {updateDefaultChannelId})(ChannelsMenu);
