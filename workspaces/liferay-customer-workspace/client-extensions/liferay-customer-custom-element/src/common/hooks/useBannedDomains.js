/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useLazyQuery} from '@apollo/client';
import {useEffect, useState} from 'react';
import {getBannedEmailDomains} from '../services/liferay/graphql/queries';
import useDebounce from './useDebounce';

const FETCH_DELAY_AFTER_TYPING = 500;

export default function useBannedDomains(value) {
	const debouncedValue = useDebounce(value, FETCH_DELAY_AFTER_TYPING);
	const [bannedDomains, setBannedDomains] = useState([]);

	const [fetchBannedDomain, {data}] = useLazyQuery(getBannedEmailDomains);
	const bannedDomainsItems = data?.c?.bannedEmailDomains?.items;

	useEffect(() => {
		let filterDomains = '';
		const splittedDomains = debouncedValue.split(',');

		if (splittedDomains.length > 1) {
			filterDomains = splittedDomains.reduce(
				(accumulatorFilter, domain, index) => {
					return `${accumulatorFilter}${
						index > 0 ? ' or ' : ''
					}domain eq '${domain.replace('@', '').trim()}'`;
				},
				''
			);
		}
		else {
			const [, emailDomain] = debouncedValue?.split('@');

			if (emailDomain) {
				filterDomains = `domain eq '${emailDomain}'`;
			}
		}

		if (filterDomains) {
			fetchBannedDomain({
				variables: {
					filter: filterDomains,
				},
			});
		}
		else {
			setBannedDomains([]);
		}
	}, [debouncedValue, fetchBannedDomain]);

	useEffect(
		() =>
			setBannedDomains(
				bannedDomainsItems?.map((item) => item.domain) || []
			),
		[bannedDomainsItems]
	);

	return bannedDomains;
}
