<#if !entries?has_content>
	<div class="taglib-empty-result-message">
			<#if searchContainer.getTotal() == 0>
			   Custom No Response for
			<strong>${htmlUtil.escape(searchResultsPortletDisplayContext.getKeywords())}</strong>
			 <br>
			   My results came up empty!
		</#if>
	</div>
<#else>
	<div class="c-mb-4 c-mt-4 search-total-label">
		<#if searchContainer.getTotal() == 1>
			${languageUtil.format(locale, "x-result-for-x", [searchContainer.getTotal(), "<strong>" + htmlUtil.escape(searchResultsPortletDisplayContext.getKeywords()) + "</strong>"], false)}
		<#else>
			${languageUtil.format(locale, "x-results-for-x", [searchContainer.getTotal(), "<strong>" + htmlUtil.escape(searchResultsPortletDisplayContext.getKeywords()) + "</strong>"], false)}
		</#if>
	</div>

	<div class="display-compact">
		<ul class="list-unstyled">
			<#list entries as entry>
				<li class="c-mb-3 c-mt-3">
					<a class="link-primary single-link" href="${entry.getViewURL()}">
						${entry.getHighlightedTitle()}
					</a>
				</li>
			</#list>
		</ul>
	</div>

	<@liferay_aui.form useNamespace=false>
		<@liferay_ui["search-paginator"]
			id="${namespace + 'searchContainerTag'}"
			searchContainer=searchContainer
			type="approximate"
		/>
	</@liferay_aui.form>
</#if>