<div class="adt-navigation">
	<#if entries?has_content>
		<#list entries as navPrimaryItem>
			<a class="adt-nav-item w-100" href="${navPrimaryItem.getURL()}">
				<div class="adt-nav-text d-flex pr-3" tabindex="4">
					<span class="adt-nav-title text-truncate">
						${navPrimaryItem.getName()}
					</span>
				</div>
			</a>
		</#list>
	</#if>
</div>