/**
 * SPDX-FileCopyrightText: © 2020 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

import React from 'react';
import {BrowserRouter, Link, Route, Routes} from 'react-router-dom';

import AuthorizationCodeFlow from './routes/authorization-code-flow/pages/AuthorizationCodeFlow';
import ClientCredentialsFlow from './routes/client-credentials-flow/pages/ClientCredentialsFlow';
import ResourceOwnerPasswordCredentialsFlow from './routes/resource-owner-password-credentials-flow/pages/ResourceOwnerPasswordCredentialsFlow';

function App() {
	return (
		<BrowserRouter>
			<div className="App">
				<nav>
					<ul>
						<li>
							<Link to="/authorization-code-flow">
								Authorization Code Flow
							</Link>
						</li>

						<li>
							<Link to="/client-credentials-flow">
								Client Credentials Flow
							</Link>
						</li>

						<li>
							<Link to="/resource-owner-password-credentials-flow">
								Resource Owner Password Credentials Flow
							</Link>
						</li>
					</ul>
				</nav>

				<Routes>
					<Route
						element={<AuthorizationCodeFlow />}
						path="/authorization-code-flow"
					/>

					<Route
						element={<ClientCredentialsFlow />}
						path="/client-credentials-flow"
					/>

					<Route
						element={<ResourceOwnerPasswordCredentialsFlow />}
						path="/resource-owner-password-credentials-flow"
					/>
				</Routes>
			</div>
		</BrowserRouter>
	);
}

export default App;
