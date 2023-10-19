/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DisplayType} from '@clayui/alert';
import ClayButton from '@clayui/button';

type ButtonProps = {
	className?: string;
	disabled?: boolean;
	displayType: DisplayType | string;
	show: boolean;
	text?: string;
};

type FooterButtonsProps = {
	className?: string;
	dataButtons: {
		backButton: ButtonProps;
		customizedButton: ButtonProps;
		nextButton: ButtonProps;
	};
	onClickCancel?: () => void;
	onClickCustomized: () => void;
	onClickNext: () => void;
};

export function FooterButtons({
	className,
	dataButtons,
	onClickCancel,
	onClickCustomized,
	onClickNext,
}: FooterButtonsProps) {
	const {backButton, customizedButton, nextButton} = dataButtons;

	return (
		<div className={className}>
			{backButton?.show && (
				<ClayButton
					className={backButton?.className}
					displayType={backButton?.displayType as DisplayType}
					onClick={() => onClickCancel && onClickCancel()}
				>
					{backButton?.text ?? 'Cancel'}
				</ClayButton>
			)}

			<div className="d-flex justify-content-end">
				{customizedButton?.show && (
					<ClayButton
						displayType={
							customizedButton?.displayType as DisplayType
						}
						onClick={() => onClickCustomized()}
					>
						{customizedButton?.text}
					</ClayButton>
				)}

				{nextButton?.show && (
					<ClayButton
						className={nextButton?.className}
						disabled={nextButton?.disabled}
						displayType={nextButton?.displayType as DisplayType}
						onClick={() => onClickNext()}
					>
						{customizedButton?.show ? nextButton?.text : 'Continue'}
					</ClayButton>
				)}
			</div>
		</div>
	);
}
