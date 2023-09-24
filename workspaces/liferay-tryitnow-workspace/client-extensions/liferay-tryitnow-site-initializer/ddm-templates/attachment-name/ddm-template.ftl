<#assign myTicket=""/>
<#assign myID=""/>
<#if (ObjectEntry_objectEntryId.getData())??>
  <#assign myID = ObjectEntry_objectEntryId.getData()/>
  <#assign myTicket = restClient.get("/c/tickets/" + myID)/>
	<#if (myTicket.attachment??) >
	<#assign attachmentId = myTicket.attachment.id />
	<#assign ticketAttachment = restClient.get("/headless-delivery/v1.0/documents/" + attachmentId)/>
	${ticketAttachment.title}
  </#if>
</#if>