// Imports the @clayui/css package CSS

import '@clayui/css/lib/css/atlas.css';

import './App.scss';

import AppRoutes from './Routes';

interface AppProps {
	route: string;
}
function App({route}: AppProps) {
	return <AppRoutes route={route} />;
}

export default App;
