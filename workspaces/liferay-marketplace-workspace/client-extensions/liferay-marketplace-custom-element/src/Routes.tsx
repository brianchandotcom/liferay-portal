import {AppCreationFlow} from './pages/AppCreationFlow/AppCreationFlow';
import GetAppPage from './pages/GetAppPage/GetAppPage';
import {PublishedAppsDashboardPage} from './pages/PublishedAppsDashboardPage/PublishedAppsDashboardPage';
import {PurchasedAppsDashboardPage} from './pages/PurchasedAppsDashboardPage/PurchasedAppsDashboardPage';

interface AppRoutesProps {
	route: string;
}
export default function AppRoutes({route}: AppRoutesProps) {
	if (route === 'create-new-app') {
		return <AppCreationFlow />;
	}
	else if (route === 'purchased-apps-dashboard') {
		return <PurchasedAppsDashboardPage />;
	}
	else if (route === 'get-app') {
		return <GetAppPage />;
	}

	return <PublishedAppsDashboardPage />;
}
