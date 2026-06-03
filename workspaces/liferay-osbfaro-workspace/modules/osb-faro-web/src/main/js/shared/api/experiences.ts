import sendRequest from 'shared/util/request';

export function fetchPageExperience({
	canonicalUrl,
	channelId,
	groupId,
	pageTitle
}: {
	canonicalUrl: string;
	channelId: string;
	groupId: string;
	pageTitle: string;
}) {
	return sendRequest({
		method: 'GET',
		path: `main/${groupId}/page-experiences?canonicalUrl=${canonicalUrl}&pageTitle=${pageTitle}&channelId=${channelId}`
	});
}
