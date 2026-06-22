import {gql} from '@apollo/client';

export default gql`
	query CohortHeatMap($channelId: String, $interval: String!) {
		cohort(channelId: $channelId, interval: $interval) {
			anonymousCohortHeatMapMetrics {
				retention
				rowKey
				rowDimension
				colDimension
				value
			}
			knownCohortHeatMapMetrics {
				retention
				rowKey
				rowDimension
				colDimension
				value
			}
			visitorsCohortHeatMapMetrics {
				retention
				rowKey
				rowDimension
				colDimension
				value
			}
		}
	}
`;
