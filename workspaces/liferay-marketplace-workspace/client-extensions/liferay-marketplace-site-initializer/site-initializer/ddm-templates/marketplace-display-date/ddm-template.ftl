<#setting date_format="MMMMM d, yyyy">

<#if (CPDefinition_displayDate.getData())??>
	${CPDefinition_displayDate.getData()?datetime("MM/dd/yy HH:mm")?date}
</#if>