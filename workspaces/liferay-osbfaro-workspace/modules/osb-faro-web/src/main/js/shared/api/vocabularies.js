import sendRequest from 'shared/util/request';

export function search({
	channelId,
	groupId,
	keywords = '',
	page = 1,
	pageSize = 12,
}) {
	return sendRequest({
		data: {channelId, keywords, page, pageSize},
		method: 'GET',
		path: `contacts/${groupId}/asset-summary-vocabularies`,
	});
}
