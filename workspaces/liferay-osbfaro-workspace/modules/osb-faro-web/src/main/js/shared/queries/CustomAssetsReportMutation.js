import {gql} from '@apollo/client';

export default gql`
	mutation CustomAssetsReportMutation(
		$dashboardId: String!
		$definition: String!
		$modifiedByUserName: String!
		$modifiedByUserId: String!
	) {
		dashboard(
			dashboardId: $dashboardId
			definition: $definition
			modifiedByUserName: $modifiedByUserName
			modifiedByUserId: $modifiedByUserId
		) {
			assetId
			assetTitle
			definition
			id
		}
	}
`;
