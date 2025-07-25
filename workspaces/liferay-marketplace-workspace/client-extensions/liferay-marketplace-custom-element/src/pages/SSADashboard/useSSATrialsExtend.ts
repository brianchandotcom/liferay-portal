/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR, {SWRConfiguration} from 'swr';

import SearchBuilder from '../../core/SearchBuilder';
import HeadlessTrialExtensionRequest from '../../services/rest/HeadlessTrialExtensionRequest';

type Props = {
	accountId?: number;
	refreshInterval?: number;
	swrConfig?: SWRConfiguration;
};

const useSSATRialsExtend = ({accountId}: Props) => {
	return useSWR(
		accountId ? '/o/c/trialextensionrequests' : null,
		async () =>
			await HeadlessTrialExtensionRequest.getTrialExtensionRequest(
				new URLSearchParams({
					filter: SearchBuilder.eq(
						'r_accountToTrialExtensionRequest_accountEntryId',
						accountId ?? 0
					),
					page: '1',
					pageSize: '-1',
					sort: 'dateCreated:desc',
				})
			)
	);
};

export {useSSATRialsExtend};
