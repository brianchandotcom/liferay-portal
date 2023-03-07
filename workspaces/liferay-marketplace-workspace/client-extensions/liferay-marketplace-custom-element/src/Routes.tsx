import {AppCreationFlow} from './pages/AppCreationFlow/AppCreationFlow';
import {PublishedAppsDashboardPage} from './pages/PublishedAppsDashboardPage/PublishedAppsDashboardPage';

interface AppRoutesProps {
	route: string;
}
export default function AppRoutes({route}: AppRoutesProps) {
	if (route === 'create-new-app') {
		return <AppCreationFlow />;
	}

	return <PublishedAppsDashboardPage />;
}
