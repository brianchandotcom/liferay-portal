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
