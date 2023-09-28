<div class="animate__animated animate__faster hide menu" id="avatarmenu">
	<#if entries?has_content>
		<#list entries as navigationEntry>
			<div class="menu-item">
				<a class="font-weight-bold text-neutral-8 text-paragraph-sm" href="${navigationEntry.getURL()}">${navigationEntry.getName()}</a>
			</div>
		</#list>
	</#if>
</div>