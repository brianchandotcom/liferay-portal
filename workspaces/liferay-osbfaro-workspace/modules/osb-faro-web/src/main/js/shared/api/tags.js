import sendRequest from 'shared/util/request';

export function search({
	channelId,
	groupId,
	keywords = '',
	page = 1,
	pageSize = 12
}) {
	return sendRequest({
		data: {channelId, keywords, page, pageSize},
		method: 'GET',
		path: `contacts/${groupId}/asset-summary-tags`
	});
}

export function fetchAccountTopTags({
	accountId,
	channelId,
	groupId,
	selectedMetric
}) {
	return sendRequest({
		data: {
			accountId,
			channelId,
			pageSize: 5,
			selectedMetric,
			sort: `${selectedMetric},desc`
		},
		method: 'GET',
		path: `contacts/${groupId}/asset-summary-tags`
	});
}
