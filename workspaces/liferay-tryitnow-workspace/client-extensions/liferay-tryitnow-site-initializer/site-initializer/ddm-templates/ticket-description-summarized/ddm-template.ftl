<#if (ObjectField_description.getData())??>
	<#assign myDescription = ObjectField_description.getData()?replace('<[^>]+>','','r') />

	${myDescription[0..*50]}...
</#if>