<style ${nonceAttribute}>
	.app-search-count-text {
		color: #282934;
	}
</style>

<#if entries?has_content>
	<span class="app-search-count-text">
		<strong class="app-search-count-text mr-2">
			${entries?size}
		</strong>
		Applications Available
	</span>
</#if>