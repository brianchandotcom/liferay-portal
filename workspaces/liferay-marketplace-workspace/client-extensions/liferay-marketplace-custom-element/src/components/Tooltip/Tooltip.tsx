/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {ClayTooltipProvider} from '@clayui/tooltip';
import classNames from 'classnames';

import helpFillIcon from '../../assets/icons/help_fill_icon.svg';

import './Tooltip.scss';

interface TooltipProps {
	tooltip?: string;
	tooltipText?: string;
}

export function Tooltip({tooltip, tooltipText}: TooltipProps) {
	return (
		<ClayTooltipProvider>
			<div
				className={
					'tooltip-base ' +
					classNames({
						'tooltip-base-auto': tooltipText,
						'tooltip-base-container': !tooltipText,
					})
				}
			>
				<div
					className="tooltip-container"
					data-title-set-as-html
					data-tooltip-align="top"
					title={tooltip}
				>
					<span className="tooltip-optional-text">{tooltipText}</span>

					<img className="tooltip-icon" src={helpFillIcon} />
				</div>
			</div>
		</ClayTooltipProvider>
	);
}
