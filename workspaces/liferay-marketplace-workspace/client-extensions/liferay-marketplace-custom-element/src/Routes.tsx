import {useEffect, useState} from 'react';

import {AppCreationFlow} from './pages/AppCreationFlow/AppCreationFlow';
import GetAppPage from './pages/GetAppPage/GetAppPage';
import {NextStepPage} from './pages/NextStepPage/NextStepPage';
import {PublishedAppsDashboardPage} from './pages/PublishedAppsDashboardPage/PublishedAppsDashboardPage';
import {PublisherGatePage} from './pages/PublisherGatePage/PublisherGatePage';
import {PurchasedAppsDashboardPage} from './pages/PurchasedAppsDashboardPage/PurchasedAppsDashboardPage';
import {publisherUserChecker} from './utils/util';

import './Routes.scss';
import {Liferay} from './liferay/liferay';

interface AppRoutesProps {
	route: string;
}

export default function AppRoutes({route}: AppRoutesProps) {
	const [userPublisherChecker, setUserPublisherChecker] = useState(false);
	const [isLoading, setIsLoading] = useState(true);

	useEffect(() => {
		const makePublisherUserChecker = async () => {
			setUserPublisherChecker(await publisherUserChecker());
			setIsLoading(false);
		};

		makePublisherUserChecker();
	}, []);

	if (route === 'create-app') {
		return <AppCreationFlow />;
	}
	else if (route === 'get-app') {
		return <GetAppPage />;
	}
	else if (route === 'next-steps') {
		return <NextStepPage />;
	}
	else if (route === 'purchased-apps') {
		return <PurchasedAppsDashboardPage />;
	}
	else if (route === 'published-apps') {
		if (isLoading) {
			return (
				<div className="spinner-container">
					<div className="spinner"></div>
				</div>
			);
		}
		else if (userPublisherChecker && Liferay.ThemeDisplay.isSignedIn()) {
			return <PublishedAppsDashboardPage />;
		}
		else {
			return <PublisherGatePage />;
		}
	}

	return <></>;
}
