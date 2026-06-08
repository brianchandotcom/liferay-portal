/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApolloError, useQuery} from '@apollo/client';
import PreferenceQuery from '~/shared/queries/PreferenceQuery';
import {DATA_RETENTION_PERIOD_KEY} from '~/shared/util/constants';
import {convertMillisecondsToMonths} from '~/shared/util/date';

export const useRetentionPeriod = function useRetentionPeriod() {
	const {data, error, loading} = useQuery(PreferenceQuery, {
		variables: {
			key: DATA_RETENTION_PERIOD_KEY,
		},
	});

	if (error) {
		throw new ApolloError(error);
	}

	if (loading) {
		return null;
	}

	return convertMillisecondsToMonths(parseInt(data.preference.value, 10));
};
