import ClayLoadingIndicator from '@clayui/loading-indicator';

import './LoadingPage.scss';

interface LoadingPageProps {
    appTitle: string;
    appVersion: string;
}

export function LoadingPage({
    appTitle,
    appVersion
}: LoadingPageProps) {
    return (
        <div className='loading-page-container'>
            <ClayLoadingIndicator 
                displayType='primary' 
                shape="squares" 
                size='lg'
            />

            <div className='loading-page-text-container'>
                <span 
                    className='loading-page-text'
                >
                    Hang tight, the submission of <strong>{appTitle}</strong>
                </span>
                <span 
                    className='loading-page-text'
                >
                    <strong>{appVersion}</strong> is being sent to <strong>Liferay</strong>
                </span>
            </div>
        </div>
    );
}