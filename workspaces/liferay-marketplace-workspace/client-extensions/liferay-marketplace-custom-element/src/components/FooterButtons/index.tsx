/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DisplayType} from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';

export type ButtonProps = {
	appendIcon?: string;
	className?: string;
	disabled?: boolean;
	displayType: DisplayType | string;
	onClick?: () => void;
	show: boolean;
	text?: string;
	tooltip?: string;
};

export type FooterButtonsProps = {
	appendIcon?: string;
	className?: string;
	dataButtons: {
		cancelButton: ButtonProps;
		customizedButton: ButtonProps;
		nextButton: ButtonProps;
	};
};

const FooterButtons = ({className, dataButtons}: FooterButtonsProps) => {
	const {cancelButton, customizedButton, nextButton} = dataButtons;

	return (
		<ClayTooltipProvider>
			<div className={className}>
				{cancelButton?.show && (
					<ClayButton
						className={cancelButton?.className}
						displayType={cancelButton?.displayType as DisplayType}
						onClick={() =>
							cancelButton.onClick && cancelButton.onClick()
						}
					>
						{cancelButton?.text ?? 'Cancel'}
					</ClayButton>
				)}

				<div className="d-flex justify-content-end w-100">
					{customizedButton?.show && (
						<ClayButton
							className={customizedButton?.className}
							displayType={
								customizedButton?.displayType as DisplayType
							}
							onClick={() =>
								customizedButton.onClick &&
								customizedButton.onClick()
							}
						>
							{customizedButton?.text}
						</ClayButton>
					)}

					{nextButton?.show && (
						<ClayButton
							className={nextButton?.className}
							data-title-set-as-html
							data-tooltip-align="top-right"
							disabled={nextButton?.disabled}
							displayType={nextButton?.displayType as DisplayType}
							onClick={() =>
								nextButton.onClick && nextButton.onClick()
							}
							title={nextButton.tooltip}
						>
							{nextButton?.appendIcon && (
								<span className="inline-item inline-item-after mx-2">
									<ClayIcon
										aria-label={`Icon ${nextButton?.appendIcon}}`}
										symbol={nextButton?.appendIcon}
									/>
								</span>
							)}
							{customizedButton?.show
								? nextButton?.text
								: 'Continue'}
						</ClayButton>
					)}
				</div>
			</div>
		</ClayTooltipProvider>
	);
};

export default FooterButtons;
