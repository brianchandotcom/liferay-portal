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
