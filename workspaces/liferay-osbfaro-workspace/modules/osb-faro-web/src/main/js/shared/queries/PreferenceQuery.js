import {gql} from '@apollo/client';

export default gql`
	query Preference($key: String!) {
		preference(key: $key) {
			key
			value
		}
	}
`;
