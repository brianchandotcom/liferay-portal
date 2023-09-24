<div class="menu hide animate__animated animate__faster" id="avatarmenu">
<#if entries?has_content>
	<#list entries as navigationEntry>
		<div class="menu-item">
    	<a class="text-paragraph-sm text-neutral-8 font-weight-bold" href="${navigationEntry.getURL()}">${navigationEntry.getName()}</a>
    </div>
	</#list>
</#if>
</div>