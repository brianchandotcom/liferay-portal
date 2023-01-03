import {useState} from 'react';

import {getUser} from '../utils/Requests';

function User({token}) {
	const [url, setUrl] = useState([]);
	const [user, setUser] = useState([]);

	const handleGetUser = async () => {
		const data = await getUser(props)
			.then(response => response.json())
			.then(data => setUser(data));
	};

	const props = {
		token: token.access_token,
		url: url,
	}

	return (
		<div>
			<h2>Get User</h2>

			<input
				onChange={client => setUrl(client.target.value)}
				placeholder='URL for get-current-user'
				style={{width: "500px"}}
				type='text'
				value={url}
			/>
			(e.g. http://localhost:8080/api/jsonws/user/get-current-user)
			<br />

			<button onClick={handleGetUser}>Get User</button>

			<pre>{
			JSON.stringify(user, null, 2)
			}</pre>

		</div>
	);
}

export default User;
