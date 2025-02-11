/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useGetKoroneikiAccounts} from '../services/liferay/graphql/koroneiki-accounts';

interface IProps {
	filter: string;
	pageSize?: number;
}

export default function useKoroneikiAccounts({filter, pageSize}: IProps) {
	const {data, fetchMore, networkStatus, refetch} = useGetKoroneikiAccounts({
		filter,
		notifyOnNetworkStatusChange: true,
		pageSize: pageSize ?? 20,
	});

	return {
		data,
		fetchMore,
		networkStatus,
		refetch,
	};
}
