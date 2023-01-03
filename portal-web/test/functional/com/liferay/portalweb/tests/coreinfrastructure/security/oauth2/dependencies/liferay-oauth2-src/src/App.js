import React, {useState} from 'react';

import Authorize from './components/Authorize';
import Token from './components/Token';
import User from './components/User';

function App() {
	const [code, setCode] = useState('');
	const [token, setToken] = useState({});

	function handleCode(code) {
		setCode(code);
	}

	function handleToken(token) {
		setToken(token);
	}

	return (
		<div>
			<h1>Authorization Code Flow</h1>
			Callback URI: http://localhost:3000 (or wherever the React app is running)
			<br />
			Scope: read your personal user data (liferay-json-web-services.everything.read.userprofile)

			<Authorize handleCode={handleCode} />

			<Token handleToken={handleToken} code={code} />
			<br />
			Authorization token: {token.access_token}

			<User token={token} />
		</div>
	);
}

export default App;
