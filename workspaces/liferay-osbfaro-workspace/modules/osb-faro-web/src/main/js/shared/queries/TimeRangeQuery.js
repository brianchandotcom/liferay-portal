import {gql} from '@apollo/client';

export default gql`
	query TimeRange {
		timeRange {
			default
			endDate: endLocalDateTime
			rangeKey
			startDate: startLocalDateTime
		}
	}
`;
