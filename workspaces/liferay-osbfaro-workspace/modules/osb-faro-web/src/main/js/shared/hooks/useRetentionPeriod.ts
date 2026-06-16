import PreferenceQuery from 'shared/queries/PreferenceQuery';
import {ApolloError, useQuery} from '@apollo/client';

import {convertMillisecondsToMonths} from 'shared/util/date';
import {DATA_RETENTION_PERIOD_KEY} from 'shared/util/constants';

export const useRetentionPeriod = () => {
	const {data, error, loading} = useQuery(PreferenceQuery, {
		variables: {
			key: DATA_RETENTION_PERIOD_KEY
		}
	});

	if (error) {
		throw new ApolloError(error);
	}

	if (loading) return null;

	return convertMillisecondsToMonths(parseInt(data.preference.value));
};
