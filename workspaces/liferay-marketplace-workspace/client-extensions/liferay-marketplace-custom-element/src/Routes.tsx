import {AppCreationFlow} from './pages/AppCreationFlow/AppCreationFlow';
import GetAppPage from './pages/GetAppPage/GetAppPage';
import {NextStepPage} from './pages/NextStepPage/NextStepPage';
import {PublishedAppsDashboardPage} from './pages/PublishedAppsDashboardPage/PublishedAppsDashboardPage';
import {PurchasedAppsDashboardPage} from './pages/PurchasedAppsDashboardPage/PurchasedAppsDashboardPage';

interface AppRoutesProps {
	route: string;
}

export default function AppRoutes({route}: AppRoutesProps) {
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
		return <PublishedAppsDashboardPage />;
	}

	return <></>;
}
