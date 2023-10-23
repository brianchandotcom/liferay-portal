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
		cancelButton: ButtonProps;
		customizedButton: ButtonProps;
		nextButton: ButtonProps;
	};
	onClickCancel?: () => void;
	onClickCustomizedButton: () => void;
	onClickNext: () => void;
};

const FooterButtons = ({
	className,
	dataButtons,
	onClickCancel,
	onClickCustomizedButton,
	onClickNext,
}: FooterButtonsProps) => {
	const {cancelButton, customizedButton, nextButton} = dataButtons;

	return (
		<div className={className}>
			{cancelButton?.show && (
				<ClayButton
					className={cancelButton?.className}
					displayType={cancelButton?.displayType as DisplayType}
					onClick={() => onClickCancel && onClickCancel()}
				>
					{cancelButton?.text ?? 'Cancel'}
				</ClayButton>
			)}

			<div className="d-flex justify-content-end">
				{customizedButton?.show && (
					<ClayButton
						displayType={
							customizedButton?.displayType as DisplayType
						}
						onClick={() => onClickCustomizedButton()}
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
};

export default FooterButtons;
