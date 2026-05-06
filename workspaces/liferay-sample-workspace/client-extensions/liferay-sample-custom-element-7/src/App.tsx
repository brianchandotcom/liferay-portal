/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FDSState, getFDSAtom} from '@liferay/js-api/data-set';
import {Atom, readAtom, subscribeAtom, writeAtom} from '@liferay/js-api/state';
import React, {useEffect, useState} from 'react';

interface AppProps {
	fdsName: string;
}

function App({fdsName}: AppProps) {
	const [atom, setAtom] = useState<Atom<FDSState> | null>(null);
	const [query, setQuery] = useState('');

	useEffect(() => {
		let disposed = false;
		let subscription: {dispose: () => void} | undefined;

		getFDSAtom(fdsName)
			.then((resolvedAtom) => {
				if (disposed) {
					return;
				}

				setAtom(resolvedAtom);
				setQuery(readAtom(resolvedAtom).search?.query ?? '');

				subscription = subscribeAtom(resolvedAtom, (next) => {
					setQuery(next?.search?.query ?? '');
				});
			})
			.catch((error: Error) => {
				console.warn(
					`[liferay-sample-custom-element-7] ${error.message}`
				);
			});

		return () => {
			disposed = true;
			subscription?.dispose();
		};
	}, [fdsName]);

	const handleSearch = () => {
		if (!atom) {
			return;
		}

		writeAtom(atom, {...readAtom(atom), search: {query}});
	};

	return (
		<div className="liferay-sample-custom-element-7-search">
			<input
				className="form-control"
				disabled={!atom}
				onChange={(event) => setQuery(event.target.value)}
				onKeyDown={(event) => {
					if (event.key === 'Enter') {
						handleSearch();
					}
				}}
				placeholder={
					atom
						? `Search in ${fdsName}`
						: `Waiting for FDS "${fdsName}"...`
				}
				type="text"
				value={query}
			/>

			<button
				className="btn btn-primary"
				disabled={!atom}
				onClick={handleSearch}
				type="button"
			>
				Search
			</button>
		</div>
	);
}

export default App;
