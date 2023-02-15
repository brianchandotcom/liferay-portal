import {DashboardPage} from './pages/DashboardPage/DashboardPage';
import {AppCreationFlow} from './pages/AppCreationFlow/AppCreationFlow';

interface AppRoutesProps {
	route: string;
}
export default function AppRoutes({route}: AppRoutesProps) {
	if (route === 'create-new-app') {
		return <AppCreationFlow />;
	}

	return <DashboardPage />;
}
