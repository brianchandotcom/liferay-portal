/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayTooltipProvider} from '@clayui/tooltip';
import classNames from 'classnames';

import './Tooltip.scss';

import ClayIcon from '@clayui/icon';

interface TooltipProps {
	showTooltipBackground?: boolean;
	tooltip?: string;
	tooltipText?: string;
}

export function Tooltip({
	showTooltipBackground = true,
	tooltip,
	tooltipText,
}: TooltipProps) {
	return (
		<ClayTooltipProvider>
			<div
				className={classNames({
					'tooltip-base': showTooltipBackground,
					'tooltip-base-auto': tooltipText,
					'tooltip-base-container': !tooltipText,
				})}
			>
				<div
					className="tooltip-container"
					data-title-set-as-html
					data-tooltip-align="top"
					title={tooltip}
				>
					{tooltipText && (
						<span className="tooltip-optional-text">
							{tooltipText}
						</span>
					)}

					<ClayIcon
						aria-label="tooltip"
						className="tooltip-icon"
						symbol="question-circle"
					/>
				</div>
			</div>
		</ClayTooltipProvider>
	);
}
