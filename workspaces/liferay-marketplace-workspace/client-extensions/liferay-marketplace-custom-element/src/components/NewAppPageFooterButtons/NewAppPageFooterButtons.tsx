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
