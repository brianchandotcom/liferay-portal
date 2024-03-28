<div class="search-results" id="searchResults">
	<#if entries?has_content>
		<#list entries as searchEntry>
			<#assign
				searchEntryContent = searchEntry.getContent()!languageUtil.get(locale, "no-content-preview", "No content preview")
				searchEntryTitle = searchEntry.getTitle()!""
			/>

			<#if searchEntryTitle?has_content>
				<div class="align-items-stretch pb-4 search-results-entry">
					<a class="font-weight-bold search-results-entry-title text-decoration-none unstyled" href="${searchEntry.getViewURL()}&highlight=${htmlUtil.escape(searchResultsPortletDisplayContext.getKeywords()?url('ISO-8859-1'))}">
						${searchEntryTitle}
						<div class="description search-results-entry-content">
							${searchEntryContent}
						</div>

						<#if searchEntry.getPublishedDateString()?has_content>
							<div class="pt-2 published-date">
								${languageUtil.get(locale, "published-date")}: ${searchEntry.getPublishedDateString()}
							</div>
						</#if>
					</a>
				</div>
			</#if>
		</#list>
	</#if>

	<hr class="solid">
</div>