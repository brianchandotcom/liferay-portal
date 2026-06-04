/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SearchSubscription, subscribeSearch} from '@liferay/js-api/data-set';
import React, {useEffect, useState} from 'react';

interface AppProps {
	fdsName: string;
}

type SearchAPI = {
	setSearch: (query: string) => void;
};

function App({fdsName}: AppProps) {
	const [query, setQuery] = useState('');
	const [searchApi, setSearchApi] = useState<SearchAPI | null>(null);

	useEffect(() => {
		let disposed = false;
		let searchSubscription : SearchSubscription | null = null;

		const handleQueryValue = (query: string) => {
			console.log("Search query handler for", fdsName, ". New query: ", query);
			setQuery(query);
		}

		console.log("Effect running for", fdsName);
		subscribeSearch(
			fdsName, handleQueryValue, {timeout: 10000}
		)
			.then((subscription: SearchSubscription) => {
				console.log("Search subscription handler for", fdsName);
				if (disposed) {
					console.log("Preexisting Search subscription exists");
					subscription.dispose()
					return;
				}
				console.log("Search subscription is ready for", fdsName, ", with query", subscription.getSearch());

				searchSubscription = subscription;
				setSearchApi({setSearch: subscription.setSearch});
				handleQueryValue(subscription.getSearch());
			})
			.catch((error: Error) => {
				console.warn(
					`[liferay-sample-custom-element-7] ${error.message}`
				);
			});

		return () => {
			console.log("Effect cleanup for", fdsName);
			disposed = true;
			if (searchSubscription) {
				console.log("Subscription disposal for", fdsName, "subscription: " + searchSubscription);
				
				searchSubscription.dispose();
				searchSubscription = null;
			}
			setSearchApi(null);
		};
	}, [fdsName]);

	const disabled = !searchApi;

	const handleSearch = () => {
		console.log("Search triggered from CX for", fdsName, "with query", query);
		searchApi?.setSearch(query);
	};

	return (
		<div style={{display: 'flex', gap: '0.5rem', padding: '1rem'}}>
			<input
				className="form-control"
				disabled={disabled}
				onChange={(event) => setQuery(event.target.value)}
				onKeyDown={(event) => {
					if (event.key === 'Enter') {
						handleSearch();
					}
				}}
				placeholder={
					disabled
						? `Waiting for FDS "${fdsName}"...`
						: `Search in ${fdsName}`
				}
				style={{flex: 1}}
				type="text"
				value={query}
			/>

			<button
				className="btn btn-primary"
				disabled={disabled}
				onClick={handleSearch}
				type="button"
			>
				Search
			</button>
		</div>
	);
}

export default App;