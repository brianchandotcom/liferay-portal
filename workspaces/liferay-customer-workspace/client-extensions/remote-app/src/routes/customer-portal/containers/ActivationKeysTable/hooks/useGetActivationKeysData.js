/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';
import {useGetActivationKeys} from '../../../../../common/services/liferay/graphql/activation-keys';

const MAX_ITEMS = 9999;
const PAGE = 1;

export default function useGetActivationKeysData(project, initialFilter) {
	const [activationKeys, setActivationKeys] = useState([]);
	const [filterTerm, setFilterTerm] = useState(
		`active eq true and ${initialFilter}`
	);

	const {data, loading} = useGetActivationKeys(
		project?.accountKey,
		encodeURI(filterTerm),
		PAGE,
		MAX_ITEMS
	);

	useEffect(() => {
		if (!loading && data?.getActivationKeys) {
			setActivationKeys(data.getActivationKeys.items);
		}
	}, [data, loading]);

	return {
		activationKeysState: [activationKeys, setActivationKeys],
		loading,
		setFilterTerm,
	};
}
