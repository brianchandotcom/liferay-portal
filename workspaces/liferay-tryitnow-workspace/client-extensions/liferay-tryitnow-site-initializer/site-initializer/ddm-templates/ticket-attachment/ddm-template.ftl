<#assign
	myID = ""
	myTicket = ""
/>

<#if (ObjectEntry_objectEntryId.getData())??>
	<#assign
		myID = ObjectEntry_objectEntryId.getData()
		myTicket = restClient.get("/c/j3y7tickets/" + myID)
	/>

	<#if (myTicket.attachment??)>
		<#assign
			attachmentId = myTicket.attachment.id
			ticketAttachment = myTicket.attachment.link
		/>

		<a href="${ticketAttachment.href}">${ticketAttachment.label}</a>
	</#if>
</#if>