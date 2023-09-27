<#assign
	myTicket=""
	myID=""
/>

<#if (ObjectEntry_objectEntryId.getData())??>
<#assign
	myID = ObjectEntry_objectEntryId.getData()
	myTicket = restClient.get("/c/tickets/" + myID)
/>

	<#if (myTicket.attachment??)>
	<#assign
		attachmentId = myTicket.attachment.id
		ticketAttachment = restClient.get("/headless-delivery/v1.0/documents/" + attachmentId)
	/>

	${ticketAttachment.title}
</#if>
</#if>