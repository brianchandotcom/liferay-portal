<style>
	.pagination-bar {
		align-items: flex-start;
		display: flex;
		gap: 1rem;
	}

	.pagination-bar .dropdown.pagination-items-per-page {
		align-items: flex-start;
		display: flex;
	}

	.pagination-bar .dropdown.pagination-items-per-page .dropdown-toggle.page-link {
		align-items: center;
		border-width: 0px;
		color: var(--color-action-neutral-default, #2B3A4B);
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
		color: var(--color-action-neutral-default, #2B3A4B);
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
		background: var(--color-action-neutral-active-20, #D5D8DB);
	}

	.pagination-bar .pagination-results {
		align-items: flex-start;
		color: var(--color-neutral-10, #282934);
		display: flex;
		font-family: 'Source Sans Pro', sans-serif;
		font-size: 0.8125rem;
		font-style: normal;
		font-weight: 400;
		gap: 0.25rem;
		line-height: 1rem;
	}

	.search-results .search-results-entry {
		align-items: flex-start;
		display: flex;
		flex-direction: column;
		gap: 0.25rem;
	}

	.search-results .search-results-entry .search-results-entry-title {
		border-radius: 0.625rem;
		color: #0B5FFF;
		font-family: 'Source Sans Pro', sans-serif;
		font-size: 1rem;
		font-style: normal;
		font-weight: 600;
		line-height: 1.5rem;
		padding: 1rem;
	}

	.search-results .search-results-entry .search-results-entry-title .published-date {
		color: var(--color-state-neutral-darken-1, #6C6C75);
		font-family: 'Source Sans Pro', sans-serif;
		font-size: 0.8125rem;
		font-style: normal;
		font-weight: 400;
		line-height: 1rem;
	}

	.search-results .search-results-entry .search-results-entry-title .search-results-entry-content {
		color: var(--color-neutral-10, #282934);
		font-family: 'Source Sans Pro', sans-serif;
		font-size: 1rem;
		font-style: normal;
		font-weight: 400;
		line-height: 1.5rem;
	}

	.search-results .search-results-entry .search-results-entry-title:hover {
		background: var(--color-action-primary-hover-10, #EDF3FE);
	}

	.search-results .solid {
		border-top: 1px solid var(--color-neutral-2, #E2E2E4);
	}
</style>

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

						<div class="published-date pt-2">
							${languageUtil.get(locale, "published-date")}: ${searchEntry.getPublishedDateString()}
						</div>
					</a>
				</div>
			</#if>
		</#list>
	</#if>

	<hr class="solid">
</div>