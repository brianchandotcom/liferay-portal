/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import './NewAppPageFooterButtons.scss';

interface NewAppPageFooterButtonsProps {
	backButtonText?: string;
	continueButtonText?: string;
	disableContinueButton?: boolean;
	onClickBack?: () => void;
	onClickContinue: () => void;
	showBackButton?: boolean;
}

export function NewAppPageFooterButtons({
	backButtonText,
	continueButtonText,
	disableContinueButton,
	onClickBack,
	onClickContinue,
	showBackButton = true,
}: NewAppPageFooterButtonsProps) {
	return (
		<div className="new-app-page-footer-button-container">
			{showBackButton && (
				<button
					className="new-app-page-footer-button-back"
					onClick={() => onClickBack && onClickBack()}
				>
					{backButtonText ?? 'Back'}
				</button>
			)}

			<button
				className="new-app-page-footer-button-continue"
				disabled={disableContinueButton}
				onClick={() => onClickContinue()}
			>
				{continueButtonText ?? 'Continue'}
			</button>
		</div>
	);
}
