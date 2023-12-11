<style>
	.pagination-bar {
		align-items: flex-start;
		display: flex;
		gap: 1rem;
		width: 47.5rem;
	}

	.pagination-bar .dropdown.pagination-items-per-page {
		align-items: flex-start;
		display: flex;
	}

	.pagination-bar .dropdown.pagination-items-per-page .dropdown-toggle.page-link {
		align-items: center;
		border-width: 0px;
		color: var(--action-neutral-default, #2B3A4B);
		display: flex;
		font-family: 'Source Sans Pro', sans-serif;
		font-size: 0.875rem;
		font-style: normal;
		font-weight: 600;
		gap: 0.25rem;
		justify-content: center;
		line-height: 1rem;
		text-align: center;
	}

	.pagination-bar .dropdown.pagination-items-per-page .dropdown-toggle.page-link .c-inner {
		align-items: center;
		display: flex;
		height: 1rem;
		justify-content: center;
	}

	.pagination-bar .dropdown.pagination-items-per-page .dropdown-toggle.page-link .c-inner .lexicon-icon.lexicon-icon-caret-double-l {
		align-items: center;
		display: flex;
		flex-shrink: 0;
		height: 1rem;
		justify-content: center;
		width: 1rem;
	}

	.pagination-bar .pagination-results {
		align-items: flex-start;
		color: var(--neutral-10, #282934);
		display: flex;
		font-family: 'Source Sans Pro', sans-serif;
		font-size: 0.8125rem;
		font-style: normal;
		font-weight: 400;
		gap: 0.25rem;
		line-height: 1rem;
	}

	.pagination-bar .pagination {
		align-items: flex-start;
		display: flex;
		gap: 0.5rem;
		justify-content: center;
	}

	.pagination-bar .pagination .page-item .page-link {
		align-items: center;
		border-radius: 0.375rem;
		border-width: 0px;
		color: var(--action-neutral-default, #2B3A4B);
		display: flex;
		font-family: 'Source Sans Pro', sans-serif;
		font-size: 0.875rem;
		font-style: normal;
		font-weight: 600;
		gap: 0.25rem;
		justify-content: center;
		line-height: 1rem;
		min-width: 2rem;
		padding: 0.5rem;
		text-align: center;
	}

	.pagination-bar .pagination .page-item.active .page-link {
		background: var(--action-neutral-active-lighten, #D5D8DB);
	}

	.search-results .search-results-entry {
		align-items: flex-start;
		display: flex;
		flex-direction: column;
		gap: 0.25rem;
		padding: 1rem;
		width: 49.75rem;
	}

	.search-results .search-results-entry .search-results-entry-title {
		border-radius: 0.625rem;
		color: #0B5FFF;
		font-family: 'Source Sans Pro', sans-serif;
		font-size: 1rem;
		font-style: normal;
		font-weight: 600;
		height: 8rem;
		line-height: 1.5rem;
		padding: 1rem;
		width: 49.75rem;
	}

	.search-results .search-results-entry .search-results-entry-title:hover {
		background: var(--action-primary-hover-lighten, #EDF3FE);
	}

	.search-results .search-results-entry .search-results-entry-title .search-results-entry-content {
		color: var(--neutral-10, #282934);
		font-family: 'Source Sans Pro', sans-serif;
		font-size: 1rem;
		font-style: normal;
		font-weight: 400;
		line-height: 1.5rem;
	}

	.search-results .search-results-entry .search-results-entry-title .modified-date {
		color: var(--neutral-07, #6C6C75);
		font-family: 'Source Sans Pro', sans-serif;
		font-size: 0.8125rem;
		font-style: normal;
		font-weight: 400;
		line-height: 1rem;
	}

	.search-results .solid {
		border-top: 1px solid #E2E2E4;
	}
</style>

<div class="search-results" id="searchResults">
	<#if entries?has_content>
		<#list entries as entry>
			<#assign
				searchEntryContent = entry.getContent()!languageUtil.get(locale, "no-content-preview", "No content preview")
				searchEntryTitle = entry.getTitle()!""
			/>

			<#if searchEntryTitle?has_content>
				<div class="pb-4 search-results-entry">
					<a class="font-weight-bold search-results-entry-title text-decoration-none unstyled" href="${entry.getViewURL()}&highlight=${htmlUtil.escape(searchResultsPortletDisplayContext.getKeywords()?url('ISO-8859-1'))}">
						${searchEntryTitle}
						<div class="description search-results-entry-content">
							${searchEntryContent}
						</div>

						<div class="modified-date pt-2">
							<#assign
								modifiedDateStringParts = entry.getModifiedDateString()?split(":")
								finalModifiedDateString = ""
							/>

							<#if modifiedDateStringParts[0]?length gt modifiedDateStringParts[1]?length>
								<#assign finalModifiedDateString = modifiedDateStringParts[0] + ":" />
							<#else>
								<#assign finalModifiedDateString = ":" + modifiedDateStringParts[1] />
							</#if>

							${languageUtil.get(locale, "last-modified")}: ${finalModifiedDateString?replace("[0-9]*:[0-9]*", "", "r")}
						</div>
					</a>
				</div>
			</#if>
		</#list>
	</#if>

	<hr class="solid">
</div>