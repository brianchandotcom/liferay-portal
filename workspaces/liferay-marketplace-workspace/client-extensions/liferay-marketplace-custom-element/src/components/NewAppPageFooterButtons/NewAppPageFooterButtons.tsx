/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';

import './NewAppPageFooterButtons.scss';

type NewAppPageFooterButtonsProps = {
	backButtonText?: string;
	continueButtonText?: string;
	disableContinueButton?: boolean;
	isLoading?: boolean;
	loadingButtonText?: string;
	onClickBack?: () => void;
	onClickContinue: () => void;
	showBackButton?: boolean;
	showContinueButton?: boolean;
};

export function NewAppPageFooterButtons({
	backButtonText,
	continueButtonText = 'Continue',
	disableContinueButton,
	isLoading = false,
	loadingButtonText = 'Continue',
	onClickBack,
	onClickContinue,
	showBackButton = true,
	showContinueButton = true,
}: NewAppPageFooterButtonsProps) {
	return (
		<div className="new-app-page-footer-button-container">
			{showBackButton && (
				<button
					className="disabled new-app-page-footer-button-back"
					disabled={isLoading}
					onClick={() => {
						if (onClickBack) {
							onClickBack();
						}
					}}
				>
					{backButtonText ?? 'Back'}
				</button>
			)}

			{showContinueButton && (
				<button
					className="new-app-page-footer-button-continue"
					disabled={disableContinueButton}
					onClick={() => onClickContinue()}
				>
					<span className="align-items-center d-flex">
						{isLoading && (
							<ClayLoadingIndicator
								className="m-0 mr-2"
								displayType="light"
								size="sm"
							/>
						)}
						{isLoading && loadingButtonText}
						{!isLoading && continueButtonText}
					</span>
				</button>
			)}
		</div>
	);
}
