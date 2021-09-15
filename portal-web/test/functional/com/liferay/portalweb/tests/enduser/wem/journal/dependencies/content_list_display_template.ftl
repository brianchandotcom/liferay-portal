<#if contents.getSiblings()?has_content>
	<#list contents.getSiblings() as cur_contents>
		${cur_contents.getData()}
	</#list>
</#if>