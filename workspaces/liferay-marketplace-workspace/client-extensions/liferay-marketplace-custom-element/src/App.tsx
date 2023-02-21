import './App.scss';
import AppRoutes from './Routes';

interface AppProps {
	route: string;
}
function App({route}: AppProps) {
	return <AppRoutes route={route} />;
}

export default App;
