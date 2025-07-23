/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR, {SWRConfiguration} from 'swr';

import SearchBuilder from '../../core/SearchBuilder';
import HeadlessSSATrialsExtend from '../../services/rest/HeadlessSSATrialsExtend';

type Props = {
	accountId: number;
	refreshInterval?: number;
	swrConfig?: SWRConfiguration;
};

const useSSATRialsExtend = ({accountId, swrConfig}: Props) => {
	const swr = useSWR(
		`/`,
		async () => {
			const ssaTrialsExtend =
				await HeadlessSSATrialsExtend.getSSATrialsExtend(
					new URLSearchParams({
						filter: SearchBuilder.eq(
							'r_accountToSSATrialExtend_accountEntryId',
							accountId
						),
						page: '1',
						pageSize: '-1',
						sort: 'dateCreated:desc',
					})
				);

			return ssaTrialsExtend;
		},
		swrConfig
	);

	return {...swr};
};

export {useSSATRialsExtend};
