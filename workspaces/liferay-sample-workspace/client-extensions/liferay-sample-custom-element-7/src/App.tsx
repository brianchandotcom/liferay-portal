/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SearchSubscription, subscribeSearch} from '@liferay/js-api/data-set';
import React, {useEffect, useRef, useState} from 'react';

interface AppProps {
	fdsName: string;
}

function App({fdsName}: AppProps) {
	const [disabled, setDisabled] = useState<boolean>(true);
	const [query, setQuery] = useState('');
	const [setSearch, setSetSearch] = useState<Function>(() => () => {});

	useEffect(() => {
		let disposed = false;
		let searchSubscription : SearchSubscription | null = null;

		const handleQueryValue = 	(queryString: string) => {
			//console.log("Search query handler for", fdsName, ". New query: ", queryString);
			setQuery(queryString);
			setDisabled(false);
		}

		//console.log("Effect running for", fdsName);
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
				searchSubscription = subscription;
				setSetSearch(() => subscription.setSearch);
				handleQueryValue(subscription.getSearch());
				//console.log("Search subscription is ready for", fdsName, ". I received query", subscription.getSearch());
			})
			.catch((error: Error) => {
				console.warn(
					`[liferay-sample-custom-element-7] ${error.message}`
				);
			});

		return () => {
			//console.log("Effect disposal for", fdsName, "subscription: " + searchSubscription);
			disposed = true;
			if (searchSubscription) {
				searchSubscription.dispose();
				searchSubscription = null;
			}
			setDisabled(true);
		};
	}, [fdsName]);

	const handleSearch = () => {
		console.log("Search triggered from CX for", fdsName, "with", query);
		setSearch(query);
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