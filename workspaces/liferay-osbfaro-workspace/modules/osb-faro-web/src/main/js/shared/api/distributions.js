import sendRequest from 'shared/util/request';
import {FieldContexts} from 'shared/util/constants';

export function fetch(params) {
	const {
		context = FieldContexts.Demographics,
		groupId,
		...otherParams
	} = params;

	const entityType =
		context === FieldContexts.Demographics ? 'individual' : 'account';

	return sendRequest({
		data: {
			...otherParams
		},
		method: 'GET',
		path: `contacts/${groupId}/${entityType}/distribution`
	});
}
