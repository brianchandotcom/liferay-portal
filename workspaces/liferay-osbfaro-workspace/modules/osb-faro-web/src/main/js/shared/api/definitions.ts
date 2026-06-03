import sendRequest from 'shared/util/request';
import {RESTParams} from 'shared/types';

export function searchIndividualAttributes({groupId, query}: RESTParams) {
	return sendRequest({
		data: {
			displayName: query
		},
		method: 'GET',
		path: `main/${groupId}/definitions/individual_attributes`
	});
}
