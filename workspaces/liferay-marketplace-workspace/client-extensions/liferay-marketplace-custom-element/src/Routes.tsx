import {AppCreationFlow} from './pages/AppCreationFlow/AppCreationFlow';
import GetAppPage from './pages/GetAppPage/GetAppPage';
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
	else if (route === 'purchased-apps') {
		return <PurchasedAppsDashboardPage />;
	}

	return <PublishedAppsDashboardPage />;
}
