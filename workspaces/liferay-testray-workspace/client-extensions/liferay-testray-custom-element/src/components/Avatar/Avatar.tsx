/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClaySticker from '@clayui/sticker';
import {ClayTooltipProvider} from '@clayui/tooltip';
import classNames from 'classnames';
import React from 'react';

import {ALIGN_POSITIONS} from '../Tooltip';

type AvatarProps = {
	className?: string;
	displayName?: boolean;
	displayTooltip?: boolean;
	expanded?: boolean;
	name?: string;
	size?: 'lg' | 'sm' | 'xl';
	tooltipPosition?: ALIGN_POSITIONS;
	url?: string;
};

type AvatarGroupProps = {
	assignedUsers: AvatarProps[];
	className?: string;
	groupSize: number;
};

const backgroundAccentColorsRegex = {
	'bg-accent-1': /^[A-Fa-f]/g,
	'bg-accent-2': /^[G-Lg-l]/g,
	'bg-accent-3': /^[M-Tm-t]/g,
	'bg-accent-4': /^[U-Zu-z]/g,
};

const getInitials = (name: string) =>
	name
		.split(' ')
		.map((value) => value.charAt(0))
		.join('')
		.toLocaleUpperCase();

const getRandomColor = (name: string) => {
	for (const bgAccent in backgroundAccentColorsRegex) {
		const value = (backgroundAccentColorsRegex as any)[bgAccent];

		if (new RegExp(value).test(name)) {
			return bgAccent;
		}
	}
};

const Avatar: React.FC<AvatarProps> & {Group: React.FC<AvatarGroupProps>} = ({
	className,
	displayName = false,
	displayTooltip = true,
	expanded,
	name = '',
	size = 'lg',
	tooltipPosition = 'bottom',
	url,
}) => {
	const TooltipWrapper = displayTooltip
		? ClayTooltipProvider
		: React.Fragment;

	return (
		<div className="tr-avatar">
			<TooltipWrapper>
				<ClaySticker
					className={classNames(
						className,
						'text-brand-secondary-lighten-6',
						'tr-avatar__sticker',
						getRandomColor(getInitials(name))
					)}
					shape="circle"
					size={size}
					{...(displayTooltip && {
						'data-tooltip-align': tooltipPosition,
						'title': name,
					})}
				>
					{url ? (
						<ClaySticker.Image
							alt={name}
							className="tr-avatar__sticker__image"
							loading="lazy"
							src={url}
						/>
					) : (
						getInitials(name)
					)}
				</ClaySticker>
			</TooltipWrapper>

			{displayName && (
				<span
					className={classNames(className, 'tr-avatar__text ml-2', {
						'tr-avatar__text--expanded': expanded,
					})}
				>
					{name}
				</span>
			)}
		</div>
	);
};

Avatar.Group = ({assignedUsers, groupSize}) => {
	const totalAssignedUsers = assignedUsers.length;

	return (
		<div className="d-flex">
			<div className="tr-avatar-group">
				{assignedUsers
					.filter((_, index) => index < groupSize)
					.map((user, index) => (
						<div className="tr-avatar-group__item" key={index}>
							<Avatar
								className="tr-avatar-group__item__avatar"
								name={user.name}
								url={user.url}
							/>
						</div>
					))}
			</div>

			<ClayTooltipProvider>
				<div
					className="align-items-center d-flex justify-content-center p-0 pl-4 pr-2 text-nowrap"
					data-tooltip-align="bottom"
					title={assignedUsers
						.filter((_, index) => index >= groupSize)
						.map(({name}) => name)
						.toString()}
				>
					{totalAssignedUsers > groupSize &&
						`+ ${totalAssignedUsers - groupSize}`}
				</div>
			</ClayTooltipProvider>
		</div>
	);
};

export default Avatar;
