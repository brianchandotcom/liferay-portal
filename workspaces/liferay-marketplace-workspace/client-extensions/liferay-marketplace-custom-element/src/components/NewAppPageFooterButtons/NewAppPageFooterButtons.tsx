import './NewAppPageFooterButtons.scss';

interface NewAppPageFooterButtonsProps {
	continueButtonText?: string;
	disableContinueButton?: boolean;
	onClickBack?: () => void;
	onClickContinue: () => void;
	showBackButton?: boolean;
}

export function NewAppPageFooterButtons({
	continueButtonText = 'Continue',
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
					Back
				</button>
			)}

			<button
				className="new-app-page-footer-button-continue"
				disabled={disableContinueButton}
				onClick={() => onClickContinue()}
			>
				{continueButtonText}
			</button>
		</div>
	);
}
