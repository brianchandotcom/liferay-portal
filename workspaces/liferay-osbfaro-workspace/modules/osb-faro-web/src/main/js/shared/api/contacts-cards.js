import sendRequest from 'shared/util/request';

export function preview(params) {
	const {
		contactsCardTemplateSettings = {},
		contactsCardTemplateType,
		contactsEntityId,
		groupId
	} = params;

	return sendRequest({
		data: {
			contactsCardTemplateSettings,
			contactsCardTemplateType,
			contactsEntityId
		},
		method: 'GET',
		path: `contacts/${groupId}/contacts_card/preview`
	});
}
