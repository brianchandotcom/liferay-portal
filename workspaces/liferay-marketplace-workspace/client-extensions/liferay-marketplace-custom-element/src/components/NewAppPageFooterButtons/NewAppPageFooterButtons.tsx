import './NewAppPageFooterButtons.scss';

interface NewAppPageFooterButtonsProps {
    disableContinueButton?: boolean;
    continueButtonText?: string;
    onClickBack?: () => void;
    onClickContinue: () => void;
    showBackButton?: boolean;
}

export function NewAppPageFooterButtons({
    continueButtonText = "Continue",
    disableContinueButton,
    onClickBack,
    onClickContinue,
    showBackButton = true,
}: NewAppPageFooterButtonsProps) {
    return (
        <div className='new-app-page-footer-button-container'>
            {showBackButton && (
                <button
                    className='new-app-page-footer-button-back'
                    onClick={() => onClickBack && onClickBack()}
                >
                    Back
                </button>
            )}

            <button
                disabled={disableContinueButton}
                className='new-app-page-footer-button-continue'
                onClick={() => onClickContinue()}
            >
                {continueButtonText}
            </button>
        </div>
    );
}