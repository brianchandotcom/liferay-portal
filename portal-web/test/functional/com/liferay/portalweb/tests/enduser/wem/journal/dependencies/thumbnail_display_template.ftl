<#if contents.getSiblings()?has_content>
	<#assign VOID = freeMarkerPortletPreferences.setValue("view", "thumbnailListView") />
<div class="row">
<ul class="list-unstyled">
	<#list contents.getSiblings() as cur_contents>
		<#assign article = cur_contents.getData()?eval />
<li class="col-lg-4 col-md-4 col-xs-6">
		<@liferay_asset["asset-display"]
			className=article.className
			classPK=getterUtil.getLong(article.classPK, 0)
			template="full_content"
		/>
		</li>
	</#list>
	</ul>
</div>
	<#assign VOID = freeMarkerPortletPreferences.reset() />
</#if>