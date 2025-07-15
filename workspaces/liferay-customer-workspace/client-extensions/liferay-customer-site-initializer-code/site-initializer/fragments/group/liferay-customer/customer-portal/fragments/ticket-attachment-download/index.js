const currentURL = window.location.href;
const ticketAttachmentID = currentURL.split("/#/")[1]


const redirectURL = await Liferay.OAuth2Client.FromUserAgentApplication(
	'liferay-customer-etc-spring-boot-oaua'
).fetch(
	`/ticket-attachments/${ticketAttachmentID}/download`
).then(
	(response) => response.text()
);

window.open(redirectURL, '_blank');
