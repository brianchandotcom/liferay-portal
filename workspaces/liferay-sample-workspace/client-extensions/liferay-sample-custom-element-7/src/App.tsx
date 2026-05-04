/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect, useState} from 'react';

import {IFDSState, Liferay} from './services/liferay';

interface AppProps {
	fdsName: string;
}

const ATOM_POLL_INTERVAL_MS = 100;

const getAtomKey = (fdsName: string) => `${fdsName}_fdsState`;

function App({fdsName}: AppProps) {
	const atomKey = getAtomKey(fdsName);

	const [query, setQuery] = useState('');
	const [ready, setReady] = useState(
		() => Liferay.State.__unsafe__.getAtomOrSelectorKey(atomKey) !== null
	);

	useEffect(() => {
		if (ready) {
			return;
		}

		const intervalId = window.setInterval(() => {
			if (Liferay.State.__unsafe__.getAtomOrSelectorKey(atomKey)) {
				setReady(true);
			}
		}, ATOM_POLL_INTERVAL_MS);

		return () => window.clearInterval(intervalId);
	}, [atomKey, ready]);

	useEffect(() => {
		if (!ready) {
			return;
		}

		const initialState = Liferay.State.__unsafe__.readKey<IFDSState>(
			atomKey
		);

		setQuery(initialState?.search?.query ?? '');

		const subscription = Liferay.State.__unsafe__.subscribeKey<IFDSState>(
			atomKey,
			(nextState) => setQuery(nextState?.search?.query ?? '')
		);

		return () => subscription.dispose();
	}, [atomKey, ready]);

	const handleSearch = () => {
		const currentState = Liferay.State.__unsafe__.readKey<IFDSState>(
			atomKey
		);

		if (!currentState) {
			return;
		}

		Liferay.State.__unsafe__.writeKey<IFDSState>(atomKey, {
			...currentState,
			search: {query},
		});
	};

	return (
		<div className="liferay-sample-custom-element-7-search">
			<input
				className="form-control"
				disabled={!ready}
				onChange={(event) => setQuery(event.target.value)}
				onKeyDown={(event) => {
					if (event.key === 'Enter') {
						handleSearch();
					}
				}}
				placeholder={
					ready
						? `Search in ${fdsName}`
						: `Waiting for FDS "${fdsName}"...`
				}
				type="text"
				value={query}
			/>

			<button
				className="btn btn-primary"
				disabled={!ready}
				onClick={handleSearch}
				type="button"
			>
				Search
			</button>
		</div>
	);
}

export default App;
