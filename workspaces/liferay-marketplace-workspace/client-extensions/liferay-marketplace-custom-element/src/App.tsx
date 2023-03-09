import './App.scss';

import {ClayIconSpriteContext} from '@clayui/icon';

import AppRoutes from './Routes';
import {getIconSpriteMap} from './liferay/constants';

interface AppProps {
	route: string;
}

function App({route}: AppProps) {
	return (
		<ClayIconSpriteContext.Provider value={getIconSpriteMap()}>
			<AppRoutes route={route} />
		</ClayIconSpriteContext.Provider>
	);
}

export default App;
